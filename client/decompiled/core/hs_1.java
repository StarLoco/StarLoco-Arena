/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.opengl.FullscreenUtils;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import org.apache.log4j.Logger;

/*
 * Renamed from hS
 */
public abstract class hs_1 {
    public static final Logger a = Logger.getLogger(hs_1.class);
    public static final boolean wH = true;
    private static final int wI = 16;
    private static final int wJ = 800;
    private static final int wK = 600;
    private static final int wL = 1024;
    private static final int wM = 768;
    private static final int wN = 32;
    private static final int wO = 0;
    private static final bm_2 wP = bm_2.aJo;
    public static final GraphicsEnvironment wQ = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private JFrame wR = null;
    private arQ wS = null;
    private pg_2 wT;
    private bx_2 wU;
    private String wV = null;
    private bm_2 wW;

    protected abstract JFrame kS();

    protected abstract URL kT();

    public abstract void a(avV var1);

    public abstract void kU();

    public void initialize() {
        this.wT = new pg_2(new ath_0());
        this.wT.setFocusable(true);
        this.wU = new bx_2();
        this.wU.a(new mi_0(this));
        this.wU.l(true);
        this.wT.a(this.wU);
        this.wR = this.kS();
        this.wR.setVisible(false);
        this.wR.setTitle(this.wV);
        URL uRL = this.kT();
        if (uRL != null) {
            this.wR.setIconImage(Toolkit.getDefaultToolkit().getImage(uRL));
        }
        this.wR.setDefaultCloseOperation(2);
        this.wR.addWindowListener(new mj_0(this));
    }

    public void close() {
        if (this.wS != null) {
            this.wS.Zh();
        }
        if (this.wR != null) {
            this.wR.dispose();
        } else {
            System.exit(0);
        }
    }

    public pg_2 kV() {
        return this.wT;
    }

    public bx_2 kW() {
        return this.wU;
    }

    public void kX() {
        this.setVisible(true);
        if (!this.wT.un().isAnimating()) {
            this.wT.un().start();
        }
    }

    public void setTitle(String string) {
        this.wV = string;
        if (this.wR != null) {
            this.wR.setTitle(this.wV);
        }
    }

    public void setCursor(Cursor cursor) {
        this.wR.setCursor(cursor);
    }

    public int kY() {
        return this.wR.getX();
    }

    public int kZ() {
        return this.wR.getY();
    }

    public Dimension getSize() {
        return this.wR.getSize();
    }

    public int getWidth() {
        return this.wR.getWidth();
    }

    public int getHeight() {
        return this.wR.getHeight();
    }

    public void setVisible(boolean bl2) {
        if (this.wR == null) {
            return;
        }
        this.wR.setVisible(bl2);
    }

    public void a(arQ arQ2) {
        this.wS = arQ2;
    }

    protected arQ la() {
        return this.wS;
    }

    public void a(asn_0 asn_02) {
        if (asn_02.aFL()) {
            asn_02 = this.lf();
        }
        a.info((Object)("Applying resolution : " + asn_02));
        switch (asn_02.aFK()) {
            case aJq: {
                boolean bl2 = this.f(asn_02.getWidth(), asn_02.getHeight(), asn_02.aFJ(), asn_02.getFrequency());
                if (bl2) break;
                this.lc();
                break;
            }
            case aJp: {
                this.lc();
                break;
            }
            case aJo: {
                this.q(asn_02.getWidth(), asn_02.getHeight());
                Rectangle rectangle = this.le();
                if (rectangle.contains(this.wR.getLocation())) break;
                this.wR.setLocation(rectangle.x, rectangle.y);
            }
        }
        asn_0 asn_03 = this.lb();
        if (!asn_03.equals(asn_02)) {
            a.info((Object)("Resolution applied : " + asn_03));
        }
        if (this.wS != null) {
            this.wS.b(asn_03);
        }
    }

    public asn_0 lb() {
        GraphicsDevice graphicsDevice = wQ.getDefaultScreenDevice();
        DisplayMode displayMode = graphicsDevice.getDisplayMode();
        int n2 = displayMode.getBitDepth();
        return new asn_0(this.getWidth(), this.getHeight(), n2, displayMode.getRefreshRate(), this.wW);
    }

    protected void lc() {
        this.wW = bm_2.aJp;
        GraphicsDevice graphicsDevice = wQ.getDefaultScreenDevice();
        if (graphicsDevice.getFullScreenWindow() == this.wR) {
            graphicsDevice.setFullScreenWindow(null);
        }
        if (atY.aHa()) {
            FullscreenUtils.aUN.showMenuAndDock(false);
        }
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.wR.setBounds(0, 0, dimension.width, dimension.height);
        this.wR.setExtendedState(6);
        this.wR.setVisible(true);
        this.wR.setResizable(false);
    }

    protected void q(int n2, int n3) {
        this.wW = bm_2.aJo;
        GraphicsDevice graphicsDevice = wQ.getDefaultScreenDevice();
        if (graphicsDevice.getFullScreenWindow() == this.wR) {
            graphicsDevice.setFullScreenWindow(null);
        }
        if (atY.aHa()) {
            FullscreenUtils.aUN.showMenuAndDock(true);
        }
        Rectangle rectangle = this.le();
        int n4 = Math.max(Math.min(n2, rectangle.width), 800);
        int n5 = Math.max(Math.min(n3, rectangle.height), 600);
        this.wR.setSize(n4, n5);
        if (n4 == rectangle.width && n5 == rectangle.height) {
            this.wR.setExtendedState(6);
        } else {
            this.wR.setExtendedState(0);
        }
        this.wR.setResizable(true);
        this.wR.setVisible(true);
    }

    protected boolean f(int n2, int n3, int n4, int n5) {
        DisplayMode displayMode;
        GraphicsDevice graphicsDevice;
        if (atY.aHa()) {
            FullscreenUtils.aUN.showMenuAndDock(true);
        }
        if (!(graphicsDevice = wQ.getDefaultScreenDevice()).isFullScreenSupported()) {
            a.debug((Object)"Fullscreen mode not supported, defaulting to simulated fullscreen");
        }
        if ((displayMode = hs_1.a(graphicsDevice, n2, n3, n4, n5)) == null) {
            a.warn((Object)("No available displayMode corresponding to " + n2 + "x" + n3 + "x" + n4));
            return false;
        }
        if (graphicsDevice.getFullScreenWindow() != this.wR) {
            graphicsDevice.setFullScreenWindow(this.wR);
        }
        if (graphicsDevice.getDisplayMode() == displayMode) {
            this.wW = bm_2.aJq;
            return true;
        }
        if (!graphicsDevice.isDisplayChangeSupported()) {
            a.warn((Object)"Unable to change display mode. Defaulting to windowed mode");
            graphicsDevice.setFullScreenWindow(null);
            return false;
        }
        try {
            graphicsDevice.setDisplayMode(displayMode);
        }
        catch (Exception exception) {
            a.error((Object)("Unable to set mode " + hs_1.a(displayMode) + ". Defaulting to windowed mode"), (Throwable)exception);
            graphicsDevice.setFullScreenWindow(null);
            return false;
        }
        this.wW = bm_2.aJq;
        return true;
    }

    public Rectangle ld() {
        GraphicsDevice graphicsDevice = wQ.getDefaultScreenDevice();
        return this.a(graphicsDevice);
    }

    public Rectangle le() {
        if (this.wR == null) {
            return this.ld();
        }
        Rectangle rectangle = this.wR.getBounds();
        GraphicsDevice graphicsDevice = null;
        int n2 = -1;
        for (GraphicsDevice graphicsDevice2 : wQ.getScreenDevices()) {
            Rectangle rectangle2 = graphicsDevice2.getDefaultConfiguration().getBounds();
            Rectangle rectangle3 = rectangle2.intersection(rectangle);
            int n3 = rectangle3.width * rectangle3.height;
            if (n3 <= n2) continue;
            graphicsDevice = graphicsDevice2;
            n2 = n3;
        }
        if (graphicsDevice != null) {
            return this.a(graphicsDevice);
        }
        return this.ld();
    }

    private Rectangle a(GraphicsDevice graphicsDevice) {
        GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration);
        Rectangle rectangle = graphicsConfiguration.getBounds();
        rectangle.x += insets.left;
        rectangle.width -= insets.left + insets.right;
        rectangle.y += insets.top;
        rectangle.height -= insets.top + insets.bottom;
        return rectangle;
    }

    private static DisplayMode a(GraphicsDevice graphicsDevice, int n2, int n3, int n4, int n5) {
        if (n4 == -1) {
            DisplayMode displayMode = hs_1.a(graphicsDevice, n2, n3, 32, n5);
            if (displayMode != null) {
                return displayMode;
            }
            return hs_1.a(graphicsDevice, n2, n3, graphicsDevice.getDisplayMode().getBitDepth(), n5);
        }
        boolean bl2 = n5 != 0;
        DisplayMode displayMode = null;
        for (DisplayMode displayMode2 : graphicsDevice.getDisplayModes()) {
            int n6 = displayMode2.getBitDepth();
            if (n6 == -1) {
                n6 = n4;
            }
            if (displayMode2.getWidth() != n2 || displayMode2.getHeight() != n3 || n6 != n4 || bl2 && n5 != displayMode2.getRefreshRate()) continue;
            if (displayMode == null) {
                displayMode = displayMode2;
                continue;
            }
            int n7 = displayMode.getRefreshRate();
            int n8 = displayMode2.getRefreshRate();
            if (n8 < 50 || n8 >= n7) continue;
            displayMode = displayMode2;
        }
        return displayMode;
    }

    public asn_0 lf() {
        return new asn_0(1024, 768, 32, 0, wP);
    }

    public Dimension getMinimumSize() {
        return new Dimension(800, 600);
    }

    public int lg() {
        return 16;
    }

    private static String a(DisplayMode displayMode) {
        return "{Mode " + displayMode.getWidth() + 'x' + displayMode.getHeight() + 'x' + displayMode.getBitDepth() + ' ' + displayMode.getRefreshRate() + "Hz}";
    }

    static /* synthetic */ arQ a(hs_1 hs_12) {
        return hs_12.wS;
    }
}

