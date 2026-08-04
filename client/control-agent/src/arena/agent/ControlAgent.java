package arena.agent;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * ControlAgent — a -javaagent injected into the DofusArena 2.70 client JVM that
 * exposes an HTTP control channel so an external process can drive the real
 * client and observe it WITHOUT touching the physical mouse/keyboard or the
 * visible screen.
 *
 * Two design choices make it non-intrusive:
 *   1. Input is delivered by dispatching synthetic AWT MouseEvent/KeyEvent
 *      directly to the client's GLCanvas on the EDT — the OS cursor never moves
 *      and no window focus is stolen.
 *   2. Screenshots read the OpenGL back-buffer directly (via a GLEventListener +
 *      com.sun.opengl.util.Screenshot), so the window can be moved off-screen /
 *      minimized and capture still works.
 *
 * Endpoints (GET http://127.0.0.1:8099):
 *   /health                    -> "ok frames=N canvas=BOOL offscreen=BOOL"
 *   /screenshot                -> PNG (GL back-buffer)
 *   /offscreen?on=1            -> move the window off the visible desktop (or back)
 *   /type?text=hello           -> dispatch KEY_TYPED for each char
 *   /key?name=ENTER            -> dispatch KEY_PRESSED/RELEASED (ENTER/TAB/ESC/SPACE)
 *   /click?x=100&y=200         -> dispatch PRESS/RELEASE/CLICK at canvas coords
 *   /move?x=&y=                -> dispatch MOUSE_MOVED
 *   /login?user=&pass=         -> click account field, type, TAB, type, ENTER
 *   /roster                    -> client fighter-model readout (adY.atu())
 *   /eval?class=&method=&chain=-> generic no-arg reflection probe
 *
 * No client code is modified. Targets Java 1.6 (the client's bundled JRE).
 */
public final class ControlAgent {

    /** Cached GL back-buffer capture, filled by the GLEventListener. */
    private static volatile BufferedImage lastFrame;
    private static volatile boolean captureRequested;
    private static volatile boolean glHooked;

    public static void premain(String args, Instrumentation inst) { start(args); }
    public static void agentmain(String args, Instrumentation inst) { start(args); }

    private static void start(final String args) {
        final int port = parsePort(args, 8099);
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
                    server.createContext("/", new Router());
                    server.setExecutor(null);
                    server.start();
                    System.out.println("[ControlAgent] listening on 127.0.0.1:" + port);
                } catch (Throwable e) {
                    System.out.println("[ControlAgent] FAILED to start: " + e);
                    e.printStackTrace();
                }
            }
        }, "arena-control-agent");
        t.setDaemon(true);
        t.start();
    }

    private static int parsePort(String args, int def) {
        if (args == null) return def;
        for (String kv : args.split(",")) {
            int i = kv.indexOf('=');
            if (i > 0 && kv.substring(0, i).trim().equals("port")) {
                try { return Integer.parseInt(kv.substring(i + 1).trim()); } catch (Exception ignore) {}
            }
        }
        return def;
    }

    // ---- HTTP routing -----------------------------------------------------

    static final class Router implements HttpHandler {
        public void handle(HttpExchange ex) throws IOException {
            String path = ex.getRequestURI().getPath();
            Map<String, String> q = query(ex.getRequestURI());
            try {
                if (path.equals("/health")) {
                    Component canvas = realGLCanvas();
                    Frame f = clientFrame();
                    boolean offscreen = f != null && !onVisibleScreen(f);
                    int keyL = 0;
                    if (canvas != null) { try { keyL = canvas.getKeyListeners().length; } catch (Throwable ignore) {} }
                    // "ready" = the UI has attached its input handler to the
                    // GLCanvas (login screen is interactive). Callers should wait
                    // for ready=true before driving input.
                    boolean ready = canvas != null && keyL > 0;
                    reply(ex, 200, "text/plain",
                            ("ok frames=" + Frame.getFrames().length
                                    + " canvas=" + (canvas != null)
                                    + " keyL=" + keyL
                                    + " ready=" + ready
                                    + " glHooked=" + glHooked
                                    + " offscreen=" + offscreen).getBytes("UTF-8"));
                } else if (path.equals("/screenshot")) {
                    String fmt = q.get("fmt");
                    if (fmt == null) fmt = "jpg";
                    String ctype = fmt.equals("png") ? "image/png" : "image/jpeg";
                    reply(ex, 200, ctype, screenshot(q));
                } else if (path.equals("/offscreen")) {
                    setOffscreen(!"0".equals(q.get("on")));
                    reply(ex, 200, "text/plain", "ok".getBytes("UTF-8"));
                } else if (path.equals("/type")) {
                    typeString(q.get("text"));
                    reply(ex, 200, "text/plain", "typed".getBytes("UTF-8"));
                } else if (path.equals("/key")) {
                    pressNamed(q.get("name"));
                    reply(ex, 200, "text/plain", "key".getBytes("UTF-8"));
                } else if (path.equals("/move")) {
                    ensureNormalized();
                    fireMouse(MouseEvent.MOUSE_MOVED, intOf(q, "x"), intOf(q, "y"), 0);
                    reply(ex, 200, "text/plain", "moved".getBytes("UTF-8"));
                } else if (path.equals("/click")) {
                    click(intOf(q, "x"), intOf(q, "y"), intOrDefault(q, "button", 1));
                    reply(ex, 200, "text/plain", "clicked".getBytes("UTF-8"));
                } else if (path.equals("/drag")) {
                    drag(intOf(q, "x1"), intOf(q, "y1"), intOf(q, "x2"), intOf(q, "y2"),
                            intOrDefault(q, "steps", 12));
                    reply(ex, 200, "text/plain", "dragged".getBytes("UTF-8"));
                } else if (path.equals("/login")) {
                    login(q.get("user"), q.get("pass"));
                    reply(ex, 200, "text/plain", "login-submitted".getBytes("UTF-8"));
                } else if (path.equals("/eval")) {
                    reply(ex, 200, "text/plain", eval(q).getBytes("UTF-8"));
                } else if (path.equals("/roster")) {
                    reply(ex, 200, "text/plain", roster().getBytes("UTF-8"));
                } else if (path.equals("/tree")) {
                    reply(ex, 200, "text/plain", tree().getBytes("UTF-8"));
                } else {
                    reply(ex, 404, "text/plain", ("no route: " + path).getBytes("UTF-8"));
                }
            } catch (Throwable e) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                e.printStackTrace(new java.io.PrintStream(bos));
                reply(ex, 500, "text/plain", ("ERROR " + e + "\n" + bos.toString()).getBytes("UTF-8"));
            }
        }
    }

    // ---- component discovery ---------------------------------------------

    static Frame clientFrame() {
        Frame best = null;
        for (Frame f : Frame.getFrames()) {
            if (!f.isDisplayable()) continue;
            if (best == null || (f.getWidth() * f.getHeight()) > (best.getWidth() * best.getHeight())) {
                best = f;
            }
        }
        return best;
    }

    /**
     * The client's GLCanvas — the render surface AND the input sink (its
     * KeyListeners/MouseListeners are the xulor2 UI handlers). Returns the real
     * GLCanvas or, failing that, the Frame (so screenshots still fall back to
     * frame.paint()). Input must target the GLCanvas, not the Frame.
     */
    static Component glCanvas() {
        Component c = realGLCanvas();
        if (c != null) return c;
        return clientFrame();
    }

    /** The actual GLCanvas component, or null if not found. */
    static Component realGLCanvas() {
        for (Frame f : Frame.getFrames()) {
            if (!f.isDisplayable()) continue;
            Component c = findGL(f);
            if (c != null) return c;
        }
        return null;
    }

    static Component findGL(Component root) {
        if (isGL(root)) return root;
        if (root instanceof Container) {
            for (Component k : ((Container) root).getComponents()) {
                Component hit = findGL(k);
                if (hit != null) return hit;
            }
        }
        return null;
    }

    static boolean isGL(Component c) {
        // Match GLCanvas (and any subclass, e.g. the obfuscated pg_2) or, as a
        // fallback, any heavyweight AWT Canvas that carries key listeners.
        for (Class<?> k = c.getClass(); k != null; k = k.getSuperclass()) {
            String n = k.getName();
            if (n.equals("javax.media.opengl.GLCanvas") || n.equals("java.awt.Canvas")) {
                return true;
            }
        }
        return false;
    }

    /** Debug dump of the AWT component tree with class names + listener counts. */
    static String tree() {
        StringBuilder sb = new StringBuilder();
        for (Frame f : Frame.getFrames()) {
            sb.append("FRAME ").append(f.getClass().getName())
              .append(" showing=").append(f.isShowing())
              .append(" bounds=").append(f.getBounds()).append("\n");
            dumpTree(f, 1, sb);
        }
        Component gl = realGLCanvas();
        sb.append("realGLCanvas=").append(gl == null ? "null" : gl.getClass().getName()).append("\n");
        return sb.toString();
    }

    static void dumpTree(Component c, int depth, StringBuilder sb) {
        for (int i = 0; i < depth; i++) sb.append("  ");
        int kl = 0, ml = 0;
        try { kl = c.getKeyListeners().length; } catch (Throwable ignore) {}
        try { ml = c.getMouseListeners().length; } catch (Throwable ignore) {}
        sb.append(c.getClass().getName())
          .append(" [").append(c.getWidth()).append("x").append(c.getHeight()).append("]")
          .append(" keyL=").append(kl).append(" mouseL=").append(ml).append("\n");
        if (c instanceof Container) {
            for (Component k : ((Container) c).getComponents()) dumpTree(k, depth + 1, sb);
        }
    }

    // ---- screenshots via the GL back-buffer -------------------------------

    /**
     * Capture the client and encode it. Query params:
     *   fmt  = "jpg" (default) | "png"
     *   q    = JPEG quality 1..100 (default 72)
     *   maxw = downscale so width <= maxw (default 800; 0 = native)
     *
     * JPEG + downscale matters: vision-model token cost is resolution-based
     * (~w*h/750), so PNG->JPEG shrinks the payload ~10x but `maxw` is what
     * actually cuts tokens. Defaults (jpg, q72, maxw=800) keep the UI readable
     * while roughly halving tokens and cutting the payload from ~2MB to ~150KB.
     */
    static byte[] screenshot(Map<String, String> q) throws Exception {
        BufferedImage img = capture();
        int maxw = intOrDefault(q, "maxw", 800);
        String fmt = q.get("fmt");
        if (fmt == null) fmt = "jpg";
        boolean png = fmt.equals("png");

        // Downscale (bicubic) if wider than maxw.
        if (maxw > 0 && img.getWidth() > maxw) {
            int nw = maxw;
            int nh = Math.round(img.getHeight() * (maxw / (float) img.getWidth()));
            BufferedImage scaled = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D g = scaled.createGraphics();
            g.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                    java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING,
                    java.awt.RenderingHints.VALUE_RENDER_QUALITY);
            g.drawImage(img, 0, 0, nw, nh, null);
            g.dispose();
            img = scaled;
        } else if (!png && img.getType() != BufferedImage.TYPE_INT_RGB) {
            // JPEG can't carry alpha — flatten to RGB.
            BufferedImage rgb = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D g = rgb.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            img = rgb;
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (png) {
            ImageIO.write(img, "png", bos);
        } else {
            float quality = intOrDefault(q, "q", 72) / 100f;
            javax.imageio.ImageWriter w = ImageIO.getImageWritersByFormatName("jpeg").next();
            javax.imageio.ImageWriteParam p = w.getDefaultWriteParam();
            p.setCompressionMode(javax.imageio.ImageWriteParam.MODE_EXPLICIT);
            p.setCompressionQuality(Math.max(0.05f, Math.min(1f, quality)));
            javax.imageio.stream.ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
            w.setOutput(ios);
            w.write(null, new javax.imageio.IIOImage(img, null, null), p);
            w.dispose();
            ios.close();
        }
        return bos.toByteArray();
    }

    /** Grab the current client frame (GL back-buffer, or paint() fallback). */
    static BufferedImage capture() throws Exception {
        ensureNormalized();
        ensureGLHook();
        Component c = glCanvas();
        lastFrame = null;
        captureRequested = true;
        long deadline = System.currentTimeMillis() + 3000;
        while (lastFrame == null && System.currentTimeMillis() < deadline) {
            Thread.sleep(20);
        }
        BufferedImage img = lastFrame;
        if (img == null) {
            int w = c != null ? Math.max(1, c.getWidth()) : 1024;
            int h = c != null ? Math.max(1, c.getHeight()) : 768;
            img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            if (c != null) c.paint(img.getGraphics());
        }
        return img;
    }

    static int intOrDefault(Map<String, String> q, String k, int def) {
        try { return Integer.parseInt(q.get(k)); } catch (Exception e) { return def; }
    }

    /** Register a GLEventListener once; it captures the back-buffer on request. */
    static void ensureGLHook() {
        if (glHooked) return;
        Component c = glCanvas();
        if (c == null || !isGL(c)) return;
        try {
            Class<?> listenerIface = Class.forName("javax.media.opengl.GLEventListener");
            Object proxy = java.lang.reflect.Proxy.newProxyInstance(
                    listenerIface.getClassLoader(),
                    new Class<?>[] { listenerIface },
                    new GLListener());
            Method add = c.getClass().getMethod("addGLEventListener", listenerIface);
            add.invoke(c, proxy);
            glHooked = true;
        } catch (Throwable e) {
            System.out.println("[ControlAgent] GL hook failed: " + e);
        }
    }

    /** InvocationHandler backing a GLEventListener proxy (display() captures). */
    static final class GLListener implements java.lang.reflect.InvocationHandler {
        public Object invoke(Object proxy, Method m, Object[] a) {
            if (m.getName().equals("display") && captureRequested && a != null && a.length > 0) {
                try {
                    Object drawable = a[0];
                    int w = (Integer) drawable.getClass().getMethod("getWidth").invoke(drawable);
                    int h = (Integer) drawable.getClass().getMethod("getHeight").invoke(drawable);
                    Class<?> shot = Class.forName("com.sun.opengl.util.Screenshot");
                    Method read = shot.getMethod("readToBufferedImage", int.class, int.class);
                    lastFrame = (BufferedImage) read.invoke(null, w, h);
                    captureRequested = false;
                } catch (Throwable e) {
                    captureRequested = false;
                    System.out.println("[ControlAgent] capture failed: " + e);
                }
            }
            // GLEventListener methods return void.
            return null;
        }
    }

    // ---- window placement -------------------------------------------------

    // Fixed window geometry: a 1024x768 frame yields a ~1016x741 GLCanvas
    // (frame borders ~8px wide, title bar ~27px). All hardcoded canvas
    // coordinates in this agent + the docs assume that 1016x741 canvas.
    static final int FRAME_W = 1024, FRAME_H = 768;

    /**
     * Force the client window to the fixed 1024x768 (GLCanvas 1016x741) so every
     * hardcoded canvas coordinate stays valid no matter what resolution the
     * client launched at (it otherwise opens maximized, e.g. 2560x1440 -> a
     * 2552x1413 canvas). Called at the START of EVERY input/screenshot action so
     * a stray resize/maximize can never desync coordinates. Cheap no-op when the
     * window is already normalized; only waits for the GL reshape when it
     * actually had to resize.
     */
    static void ensureNormalized() throws Exception {
        final Frame f = clientFrame();
        if (f == null) return;
        final boolean[] resized = { false };
        onEdt(new Runnable() {
            public void run() {
                try {
                    if (f.getExtendedState() != Frame.NORMAL) {
                        f.setExtendedState(Frame.NORMAL);
                        resized[0] = true;
                    }
                    if (f.getWidth() != FRAME_W || f.getHeight() != FRAME_H) {
                        f.setSize(FRAME_W, FRAME_H);
                        f.validate();
                        resized[0] = true;
                    }
                } catch (Throwable ignore) {}
            }
        });
        if (resized[0]) sleep(250); // let the GL canvas reshape before acting on it
    }

    static void setOffscreen(final boolean off) throws Exception {
        final Frame f = clientFrame();
        if (f == null) return;
        ensureNormalized();
        onEdt(new Runnable() {
            public void run() {
                if (off) {
                    // Park it far to the left of the primary monitor. It stays
                    // "showing" (so GL keeps rendering) but is not visible.
                    f.setLocation(-4000, 0);
                } else {
                    f.setLocation(60, 60);
                    f.toFront();
                }
            }
        });
    }

    static boolean onVisibleScreen(Frame f) {
        try {
            java.awt.Rectangle b = f.getBounds();
            java.awt.Rectangle screen = new java.awt.Rectangle(
                    java.awt.Toolkit.getDefaultToolkit().getScreenSize());
            return b.intersects(screen);
        } catch (Throwable e) { return true; }
    }

    // ---- synthetic AWT input (no physical mouse/keyboard) -----------------

    // Input is delivered by invoking the GLCanvas's registered listeners
    // DIRECTLY (not Robot, not the OS event queue). This bypasses window focus
    // entirely, so it works with the window off-screen and never touches the
    // physical mouse/keyboard.

    static void click(final int x, final int y) throws Exception { click(x, y, 1); }

    /**
     * Click with an explicit AWT button (1=left, 2=middle, 3=right). The move
     * hover (MOUSE_MOVED -> the world-scene path preview that arms the move) is
     * fired first and given a beat to register, then PRESS/RELEASE/CLICK carry
     * the button. In DofusArena 2.70 a fighter MOVE is a RIGHT-click release
     * (inverseMouseControl defaults false -> move on button 3); a spell target
     * and every UI button are left-click (button 1).
     */
    static void click(final int x, final int y, final int button) throws Exception {
        ensureNormalized();
        fireMouse(MouseEvent.MOUSE_MOVED, x, y, 0);
        sleep(120); // let the world-scene hover (path preview) register before the release
        fireMouse(MouseEvent.MOUSE_PRESSED, x, y, 1, button);
        fireMouse(MouseEvent.MOUSE_RELEASED, x, y, 1, button);
        fireMouse(MouseEvent.MOUSE_CLICKED, x, y, 1, button);
    }

    static void fireMouse(final int id, final int x, final int y, final int clicks) throws Exception {
        fireMouse(id, x, y, clicks, (id == MouseEvent.MOUSE_MOVED) ? MouseEvent.NOBUTTON : MouseEvent.BUTTON1);
    }

    static void fireMouse(final int id, final int x, final int y, final int clicks, final int button) throws Exception {
        final Component c = realGLCanvas();
        if (c == null) return;
        // Dragged/pressed/released/clicked carry the pressed button's down
        // modifier; a plain move carries none.
        final int mods;
        if (id == MouseEvent.MOUSE_MOVED) mods = 0;
        else if (button == MouseEvent.BUTTON3) mods = MouseEvent.BUTTON3_DOWN_MASK;
        else if (button == MouseEvent.BUTTON2) mods = MouseEvent.BUTTON2_DOWN_MASK;
        else mods = MouseEvent.BUTTON1_DOWN_MASK;
        onEdt(new Runnable() {
            public void run() {
                MouseEvent e = new MouseEvent(c, id, System.currentTimeMillis(), mods,
                        x, y, clicks, false, button);
                for (java.awt.event.MouseListener ml : c.getMouseListeners()) {
                    switch (id) {
                        case MouseEvent.MOUSE_PRESSED: ml.mousePressed(e); break;
                        case MouseEvent.MOUSE_RELEASED: ml.mouseReleased(e); break;
                        case MouseEvent.MOUSE_CLICKED: ml.mouseClicked(e); break;
                        default: break;
                    }
                }
                if (id == MouseEvent.MOUSE_MOVED || id == MouseEvent.MOUSE_DRAGGED) {
                    for (java.awt.event.MouseMotionListener mm : c.getMouseMotionListeners()) {
                        if (id == MouseEvent.MOUSE_DRAGGED) mm.mouseDragged(e);
                        else mm.mouseMoved(e);
                    }
                }
            }
        });
    }

    /**
     * Synthetic drag: press at (x1,y1), a series of MOUSE_DRAGGED steps to
     * (x2,y2), then release at the target. Used to drag a fighter card from the
     * pool onto a team slot (xulor2 drag-and-drop). All canvas-relative.
     */
    static void drag(int x1, int y1, int x2, int y2, int steps) throws Exception {
        ensureNormalized();
        if (steps < 2) steps = 2;
        fireMouse(MouseEvent.MOUSE_MOVED, x1, y1, 0);
        fireMouse(MouseEvent.MOUSE_PRESSED, x1, y1, 1);
        sleep(60);
        for (int i = 1; i <= steps; i++) {
            int xi = x1 + (x2 - x1) * i / steps;
            int yi = y1 + (y2 - y1) * i / steps;
            fireMouse(MouseEvent.MOUSE_DRAGGED, xi, yi, 0);
            sleep(25);
        }
        sleep(60);
        fireMouse(MouseEvent.MOUSE_RELEASED, x2, y2, 1);
    }

    static void typeString(String s) throws Exception {
        if (s == null) return;
        ensureNormalized();
        for (int i = 0; i < s.length(); i++) typeChar(s.charAt(i));
    }

    static void typeChar(final char ch) throws Exception {
        final Component c = realGLCanvas();
        if (c == null) return;
        final int vk = vkFor(ch);
        onEdt(new Runnable() {
            public void run() {
                long t = System.currentTimeMillis();
                java.awt.event.KeyListener[] kls = c.getKeyListeners();
                for (java.awt.event.KeyListener kl : kls) {
                    if (vk != KeyEvent.VK_UNDEFINED) kl.keyPressed(new KeyEvent(c, KeyEvent.KEY_PRESSED, t, 0, vk, ch));
                    kl.keyTyped(new KeyEvent(c, KeyEvent.KEY_TYPED, t, 0, KeyEvent.VK_UNDEFINED, ch));
                    if (vk != KeyEvent.VK_UNDEFINED) kl.keyReleased(new KeyEvent(c, KeyEvent.KEY_RELEASED, t, 0, vk, ch));
                }
            }
        });
    }

    static void pressNamed(String name) throws Exception {
        if (name == null) return;
        ensureNormalized();
        final int code;
        char ch;
        if (name.equalsIgnoreCase("ENTER")) { code = KeyEvent.VK_ENTER; ch = '\n'; }
        else if (name.equalsIgnoreCase("TAB")) { code = KeyEvent.VK_TAB; ch = '\t'; }
        else if (name.equalsIgnoreCase("ESCAPE") || name.equalsIgnoreCase("ESC")) { code = KeyEvent.VK_ESCAPE; ch = (char) 27; }
        else if (name.equalsIgnoreCase("SPACE")) { code = KeyEvent.VK_SPACE; ch = ' '; }
        else if (name.equalsIgnoreCase("BACKSPACE")) { code = KeyEvent.VK_BACK_SPACE; ch = '\b'; }
        else return;
        final Component c = realGLCanvas();
        if (c == null) return;
        final char fch = ch;
        onEdt(new Runnable() {
            public void run() {
                long t = System.currentTimeMillis();
                for (java.awt.event.KeyListener kl : c.getKeyListeners()) {
                    kl.keyPressed(new KeyEvent(c, KeyEvent.KEY_PRESSED, t, 0, code, fch));
                    kl.keyTyped(new KeyEvent(c, KeyEvent.KEY_TYPED, t, 0, KeyEvent.VK_UNDEFINED, fch));
                    kl.keyReleased(new KeyEvent(c, KeyEvent.KEY_RELEASED, t, 0, code, fch));
                }
            }
        });
    }

    /** Map an ASCII char to a virtual key (best-effort; KEY_TYPED carries the char). */
    static int vkFor(char c) {
        if (c >= 'a' && c <= 'z') return KeyEvent.VK_A + (c - 'a');
        if (c >= 'A' && c <= 'Z') return KeyEvent.VK_A + (c - 'A');
        if (c >= '0' && c <= '9') return KeyEvent.VK_0 + (c - '0');
        switch (c) {
            case '.': return KeyEvent.VK_PERIOD;
            case '-': return KeyEvent.VK_MINUS;
            case ' ': return KeyEvent.VK_SPACE;
            default: return KeyEvent.VK_UNDEFINED;
        }
    }

    static void login(String user, String pass) throws Exception {
        ensureNormalized();
        // Click the account field, type login, TAB, password, ENTER. Coords are
        // canvas-relative (the GLCanvas pG is 1016x741, inset below the title
        // bar): account field ~ (508,353), password field ~ (508,411).
        // The xulor2 field swallows the first keystroke right after it gains
        // focus, so settle ~500ms after each click before typing (a shorter
        // delay drops the first character of the login/password).
        click(508, 353);
        sleep(500);
        if (user != null) typeString(user);
        sleep(150);
        click(508, 411); // password field (direct click is more reliable than TAB)
        sleep(500);
        if (pass != null) typeString(pass);
        sleep(150);
        pressNamed("ENTER");
    }

    static void onEdt(Runnable r) throws Exception {
        if (EventQueue.isDispatchThread()) r.run();
        else EventQueue.invokeAndWait(r);
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (InterruptedException ignore) {} }

    // ---- reflection: read client-side model state -------------------------

    static String eval(Map<String, String> q) {
        try {
            Class<?> c = Class.forName(q.get("class"));
            Method root = c.getDeclaredMethod(q.get("method"));
            root.setAccessible(true);
            Object cur = root.invoke(null);
            String chain = q.get("chain");
            if (chain != null && chain.length() > 0) {
                for (String step : chain.split("\\.")) {
                    if (cur == null) return "null (chain stopped at " + step + ")";
                    Method m = findNoArg(cur.getClass(), step);
                    m.setAccessible(true);
                    cur = m.invoke(cur);
                }
            }
            return String.valueOf(cur);
        } catch (Throwable e) {
            return "ERROR " + e;
        }
    }

    static Method findNoArg(Class<?> c, String name) throws NoSuchMethodException {
        for (Class<?> k = c; k != null; k = k.getSuperclass()) {
            try { return k.getDeclaredMethod(name); } catch (NoSuchMethodException ignore) {}
        }
        throw new NoSuchMethodException(name + " on " + c.getName());
    }

    static String roster() {
        StringBuilder sb = new StringBuilder();
        try {
            Class<?> adY = Class.forName("adY");
            Method atu = adY.getDeclaredMethod("atu");
            atu.setAccessible(true);
            Object model = atu.invoke(null);
            sb.append("adY.atu()=").append(model != null);
            try {
                Method isEmpty = findNoArg(model.getClass(), "isEmpty");
                isEmpty.setAccessible(true);
                sb.append(" isEmpty=").append(isEmpty.invoke(model));
            } catch (Throwable t) { sb.append(" isEmpty=?"); }
            try {
                Field caq = model.getClass().getDeclaredField("caq");
                caq.setAccessible(true);
                Object map = caq.get(model);
                Method size = findNoArg(map.getClass(), "size");
                size.setAccessible(true);
                sb.append(" size=").append(size.invoke(map));
            } catch (Throwable t) { sb.append(" size=?"); }
            // The Elite available-fighters pool binds to
            // getFieldValue("teamManagement.filtredFighterList"); report its
            // length so we can assert the pool actually contains the fighters.
            try {
                Method gfv = model.getClass().getMethod("getFieldValue", String.class);
                Object arr = gfv.invoke(model, "teamManagement.filtredFighterList");
                sb.append(" pool=").append(arr == null ? -1 : java.lang.reflect.Array.getLength(arr));
                Object arr2 = gfv.invoke(model, "teamManagement.fighterList");
                sb.append(" fighterList=").append(arr2 == null ? -1 : java.lang.reflect.Array.getLength(arr2));
            } catch (Throwable t) { sb.append(" pool=?(" + t + ")"); }
        } catch (Throwable e) {
            sb.append("ERROR ").append(e);
        }
        return sb.toString();
    }

    // ---- tiny HTTP plumbing ----------------------------------------------

    static Map<String, String> query(URI uri) {
        Map<String, String> m = new HashMap<String, String>();
        String q = uri.getRawQuery();
        if (q == null) return m;
        for (String pair : q.split("&")) {
            int i = pair.indexOf('=');
            if (i < 0) { m.put(dec(pair), ""); continue; }
            m.put(dec(pair.substring(0, i)), dec(pair.substring(i + 1)));
        }
        return m;
    }

    static String dec(String s) {
        try { return java.net.URLDecoder.decode(s, "UTF-8"); } catch (Exception e) { return s; }
    }

    static int intOf(Map<String, String> q, String k) {
        try { return Integer.parseInt(q.get(k)); } catch (Exception e) { return 0; }
    }

    static void reply(HttpExchange ex, int code, String ctype, byte[] body) throws IOException {
        ex.getResponseHeaders().set("Content-Type", ctype);
        ex.sendResponseHeaders(code, body.length);
        OutputStream os = ex.getResponseBody();
        os.write(body);
        os.close();
    }

    // silence unused import warnings for AWTEvent/Point on some toolchains.
    static final Class<?> _keepAWT = AWTEvent.class;
    static final Class<?> _keepPoint = Point.class;
}
