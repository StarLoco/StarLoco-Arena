/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.Screenshot;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import org.apache.log4j.Logger;

/*
 * Renamed from bx
 */
public class bx_2
implements FocusListener,
KeyListener,
MouseListener,
MouseMotionListener,
MouseWheelListener,
GLEventListener {
    protected static final Logger a = Logger.getLogger(bx_2.class);
    private static final boolean ge = atY.aGZ() == atY.cVy;
    public static final Color gf = Color.BLACK;
    private float gg = 0.0f;
    private float gh = 0.0f;
    private float gi = 0.0f;
    private float gj = 0.0f;
    private final Object gk = new Object();
    private final ArrayList gl = new ArrayList();
    private final Object gm = new Object();
    private final ArrayList gn = new ArrayList();
    private GL go;
    private static GLU gp = new GLU();
    private static GLUT gq = new GLUT();
    private final ArrayList gr;
    private final ArrayList gs;
    private final ArrayList gt;
    private final ArrayList gu;
    private final ArrayList gv;
    private final HashMap gw = new HashMap();
    private static final boolean gx = false;
    private static final boolean gy = false;
    private boolean gz;
    private boolean gA = true;
    private boolean gB = !this.gA;
    private long gC = System.nanoTime();
    private int gD;
    private float[] gE = new float[180];
    private boolean gF = false;
    private ajr_1 gG;
    private int gH = 0;
    private int gI = 0;
    private ArrayList gJ = null;
    private boolean aK;
    private boolean gK = false;
    private boolean gL = false;
    private Gk gM = null;

    public bx_2() {
        this.a(gf);
        this.gr = new ArrayList();
        this.gs = new ArrayList();
        this.gt = new ArrayList();
        this.gz = true;
        this.gB = true;
        this.gv = new ArrayList();
        this.gu = new ArrayList();
    }

    public void a(float[] fArray) {
        this.gg = fArray[0];
        this.gh = fArray[1];
        this.gi = fArray[2];
        this.gj = fArray[3];
    }

    public void a(Color color) {
        this.gg = (float)color.getRed() / 255.0f;
        this.gh = (float)color.getGreen() / 255.0f;
        this.gi = (float)color.getBlue() / 255.0f;
        this.gj = (float)color.getAlpha() / 255.0f;
    }

    public GL cP() {
        return this.go;
    }

    public static GLU cQ() {
        return gp;
    }

    public static GLUT cR() {
        return gq;
    }

    public void h(boolean bl2) {
        this.gK = bl2;
    }

    public boolean cS() {
        return this.gL;
    }

    public void i(boolean bl2) {
        this.gL = bl2;
    }

    public int cT() {
        return this.gI;
    }

    public int cU() {
        return this.gH;
    }

    private void a(GLAutoDrawable gLAutoDrawable, boolean bl2) {
        assert (this.go != null) : "Unable to call enableVSync if m_gl is not initialised! Did you call it before Renderer::Init ?";
        if (this.gB == bl2) {
            return;
        }
        this.gB = bl2;
        if (this.gB) {
            gLAutoDrawable.setAutoSwapBufferMode(true);
            this.go.setSwapInterval(1);
        } else {
            gLAutoDrawable.setAutoSwapBufferMode(false);
            this.go.setSwapInterval(0);
        }
        for (aMW aMW2 : this.gv) {
            aMW2.m(bl2);
        }
    }

    public void j(boolean bl2) {
        this.gA = bl2;
        this.gB = !this.gA;
    }

    public final boolean cV() {
        return this.gB;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(aoy_1 aoy_12, boolean bl2) {
        Object object = this.gk;
        synchronized (object) {
            Object object2 = this.gm;
            synchronized (object2) {
                if (!this.gl.contains(aoy_12)) {
                    if (!bl2) {
                        this.gl.add(0, aoy_12);
                        this.gn.add(0, aoy_12);
                    } else {
                        this.gl.add(aoy_12);
                        this.gn.add(aoy_12);
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(aoy_1 aoy_12, aoy_1 aoy_13, boolean bl2) {
        Object object = this.gk;
        synchronized (object) {
            Object object2 = this.gm;
            synchronized (object2) {
                if (this.gl.contains(aoy_12)) {
                    return;
                }
                int n2 = this.gl.indexOf(aoy_13);
                if (n2 == -1) {
                    n2 = 0;
                } else if (bl2) {
                    ++n2;
                }
                int n3 = this.gn.indexOf(aoy_13);
                if (n3 == -1) {
                    n3 = 0;
                } else if (bl2) {
                    ++n3;
                }
                this.gl.add(n2, aoy_12);
                this.gn.add(n3, aoy_12);
            }
        }
    }

    public void a(aoy_1 aoy_12) {
        this.gl.remove(aoy_12);
    }

    public void a(jA jA2) {
        if (!this.gu.contains(jA2)) {
            this.gu.add(jA2);
        }
    }

    public void b(jA jA2) {
        this.gu.remove(jA2);
    }

    public void a(aMW aMW2) {
        if (!this.gv.contains(aMW2)) {
            this.gv.add(aMW2);
        }
    }

    public void b(aMW aMW2) {
        this.gv.remove(aMW2);
    }

    public void init(GLAutoDrawable gLAutoDrawable) {
        a.info((Object)"Renderer.init started");
        this.go = gLAutoDrawable.getGL();
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        qp_22.f(this.go);
        zm_0.n(this.go);
        zm_0.a(this.go, this.gw);
        zm_0.aot().m(this.go);
        boolean bl2 = true;
        int n2 = 3;
        if (zm_0.aot().bh(1, 3)) {
            if (this.gM != null) {
                adr adr2 = zm_0.aot().aou();
                this.gM.a(this, new amu_1(1, 3, adr2.getMajor(), adr2.getMinor()));
            }
            return;
        }
        this.gM = null;
        vo_1 vo_12 = vo_1.aik();
        vo_12.ail();
        this.go.glClearColor(this.gg, this.gh, this.gi, this.gj);
        this.go.glClearStencil(0);
        this.go.glClear(17408);
        this.go.glViewport(0, 0, gLAutoDrawable.getWidth(), gLAutoDrawable.getHeight());
        gLAutoDrawable.setSize(gLAutoDrawable.getWidth(), gLAutoDrawable.getHeight());
        this.go.glTexParameterf(3553, 10242, 33071.0f);
        this.go.glTexParameterf(3553, 10243, 33071.0f);
        this.go.glTexParameterf(3553, 10240, 9729.0f);
        this.go.glTexParameterf(3553, 10241, 9728.0f);
        this.go.glHint(3152, 4353);
        this.go.glDisable(2896);
        vo_12.cv(false);
        this.go.glDisable(2929);
        this.go.glAlphaFunc(517, 0.0f);
        this.go.glEnable(3008);
        vo_12.cx(false);
        this.go.glDisable(2884);
        vo_12.cr(false);
        this.go.glShadeModel(7425);
        this.go.glPixelZoom(1.0f, 1.0f);
        this.go.glDepthMask(false);
        vo_12.ir(0);
        this.go.glStencilOp(7680, 7680, 7680);
        this.go.glDisable(34037);
        vo_12.cu(true);
        this.go.glIndexMask(0);
        this.go.glDisable(3024);
        if (this.gz) {
            this.go.glDrawBuffer(1029);
        } else {
            this.go.glDrawBuffer(1028);
        }
        this.a(gLAutoDrawable, this.gA);
        vo_12.n(qp_22);
        for (jA jA2 : this.gu) {
            jA2.a(gLAutoDrawable);
        }
        this.aK = true;
        a.info((Object)"Renderer.init ended");
    }

    public final HashMap cW() {
        return this.gw;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void display(GLAutoDrawable gLAutoDrawable) {
        block25: {
            if (this.gK) {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException interruptedException) {
                    a.error((Object)"Exception", (Throwable)interruptedException);
                }
            }
            if (this.aK) {
                try {
                    int n2;
                    long l2 = System.nanoTime();
                    float f = (float)(l2 - this.gC) / 1000000.0f;
                    this.gE[++this.gD % this.gE.length] = f;
                    float f2 = 0.0f;
                    for (int j = 0; j < this.gE.length; ++j) {
                        f2 += this.gE[j];
                    }
                    float f3 = f2 / (float)this.gE.length;
                    float f4 = f / f3;
                    int n3 = f4 <= 2.0f ? (int)f : (int)f / 2;
                    this.gC = l2;
                    if (this.cS()) {
                        n3 = 0;
                        f = 0;
                    }
                    if (this.gu != null) {
                        for (int j = 0; j < this.gu.size(); ++j) {
                            ((jA)this.gu.get(j)).b(gLAutoDrawable);
                        }
                    }
                    db_2 db_22 = arX.cQT.iE();
                    Object object = this.gk;
                    synchronized (object) {
                        this.a(gLAutoDrawable, this.gA);
                        adg_0.aPh().nq(n3);
                        Object object2 = this.gm;
                        synchronized (object2) {
                            int n4 = gLAutoDrawable.getWidth();
                            n4 += n4 % 2;
                            int n5 = gLAutoDrawable.getHeight();
                            n5 += n5 % 2;
                            for (int j = 0; j < this.gn.size(); ++j) {
                                aoy_1 aoy_12 = (aoy_1)this.gn.get(j);
                                aoy_12.init(gLAutoDrawable);
                                aoy_12.P(n4, n5);
                            }
                            this.gn.clear();
                        }
                        for (n2 = 0; n2 < this.gl.size(); ++n2) {
                            ((aoy_1)this.gl.get(n2)).bI(n3);
                        }
                        xw_1.EB().update(n3);
                        dt_2.MB().update(n3);
                        cx_0.JY().a(arX.cQT.iE(), null);
                    }
                    cW.fd().update();
                    object = this.gk;
                    synchronized (object) {
                        if (this.gJ != null) {
                            this.cX();
                        }
                        this.go.glStencilMask(-1);
                        this.go.glDepthMask(false);
                        this.go.glDisable(2929);
                        this.go.glClear(17408);
                        for (n2 = 0; n2 < this.gl.size(); ++n2) {
                            ((aoy_1)this.gl.get(n2)).h(this.go);
                        }
                        cx_0.JY().Ka();
                        cx_0.JY().Kb();
                        wq_1.Dn().a(db_22);
                        if (this.gF) {
                            this.cY();
                        }
                        if (this.gz && !this.gB) {
                            gLAutoDrawable.swapBuffers();
                        }
                        aL.bH().bM();
                    }
                }
                catch (Throwable throwable) {
                    a.error((Object)"Throwable dans le process du renderer : ", throwable);
                    if (throwable.getCause() == null) break block25;
                    a.error((Object)"Reason : ", throwable.getCause());
                }
            }
        }
    }

    private void cX() {
        boolean bl2 = false;
        if (this.gJ.size() > 0 && !((zz_1)this.gJ.get(0)).anY()) {
            this.gJ.remove(0);
        }
        if (this.gJ.size() == 0) {
            this.gJ = null;
        }
    }

    public boolean a(ajr_1 ajr_12) {
        if (ajr_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/framework/graphics/opengl/Renderer.requestScreenShot must not be null");
        }
        if (this.gF || this.gG != null) {
            return false;
        }
        this.gF = true;
        this.gG = ajr_12;
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void cY() {
        if (!this.gF || this.gG == null) {
            return;
        }
        File file = this.gG.aVs();
        try {
            Screenshot.writeToFile(file, this.gH, this.gI);
            this.gG.aVt();
        }
        catch (Exception exception) {
            this.gG.a(exception);
            a.error((Object)(exception.getMessage() + " Impossible d'enregistrer un ScreenShot dans " + file.getAbsolutePath()));
        }
        finally {
            this.gF = false;
            this.gG = null;
        }
    }

    public void reshape(GLAutoDrawable gLAutoDrawable, int n2, int n3, int n4, int n5) {
        n5 += n5 % 2;
        n4 += n4 % 2;
        this.gH = n4;
        this.gI = n5;
        if (gLAutoDrawable.getWidth() != 0 && gLAutoDrawable.getHeight() != 0) {
            for (Object object : this.gl) {
                object.P(n4, n5);
            }
        }
        for (Object object : this.gu) {
            object.a(gLAutoDrawable, n2, n3, n4, n5);
        }
    }

    public void displayChanged(GLAutoDrawable gLAutoDrawable, boolean bl2, boolean bl3) {
        for (jA jA2 : this.gu) {
            jA2.a(gLAutoDrawable, bl2, bl3);
        }
    }

    public void keyTyped(KeyEvent keyEvent) {
        eo_2 eo_22;
        Iterator iterator = this.gs.iterator();
        while (iterator.hasNext() && !(eo_22 = (eo_2)iterator.next()).a(keyEvent)) {
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        eo_2 eo_22;
        Iterator iterator = this.gs.iterator();
        while (iterator.hasNext() && !(eo_22 = (eo_2)iterator.next()).c(keyEvent)) {
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        eo_2 eo_22;
        Iterator iterator = this.gs.iterator();
        while (iterator.hasNext() && !(eo_22 = (eo_2)iterator.next()).b(keyEvent)) {
        }
    }

    public void a(eo_2 eo_22, boolean bl2) {
        if (!this.gs.contains(eo_22)) {
            if (!bl2) {
                this.gs.add(eo_22);
            } else {
                this.gs.add(0, eo_22);
            }
        }
    }

    public void a(eo_2 eo_22) {
        this.gs.remove(eo_22);
    }

    private MouseEvent a(MouseEvent mouseEvent) {
        if (ge && mouseEvent.getButton() == 1 && mouseEvent.isControlDown()) {
            int n2 = mouseEvent.getModifiersEx() ^ 0x80;
            return new MouseEvent(mouseEvent.getComponent(), mouseEvent.getID(), mouseEvent.getWhen(), n2, mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getClickCount(), mouseEvent.isPopupTrigger(), 3);
        }
        return mouseEvent;
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        wi_1 wi_12;
        mouseEvent = this.a(mouseEvent);
        Iterator iterator = this.gr.iterator();
        while (iterator.hasNext() && !(wi_12 = (wi_1)iterator.next()).b(mouseEvent)) {
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
        wi_1 wi_12;
        mouseEvent = this.a(mouseEvent);
        Iterator iterator = this.gr.iterator();
        while (iterator.hasNext() && !(wi_12 = (wi_1)iterator.next()).mousePressed(mouseEvent)) {
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        wi_1 wi_12;
        mouseEvent = this.a(mouseEvent);
        Iterator iterator = this.gr.iterator();
        while (iterator.hasNext() && !(wi_12 = (wi_1)iterator.next()).c(mouseEvent)) {
        }
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        wi_1 wi_12;
        Iterator iterator = this.gr.iterator();
        while (iterator.hasNext() && !(wi_12 = (wi_1)iterator.next()).d(mouseEvent)) {
        }
    }

    public void mouseExited(MouseEvent mouseEvent) {
        wi_1 wi_12;
        Iterator iterator = this.gr.iterator();
        while (iterator.hasNext() && !(wi_12 = (wi_1)iterator.next()).e(mouseEvent)) {
        }
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        wi_1 wi_12;
        mouseEvent = this.a(mouseEvent);
        Iterator iterator = this.gr.iterator();
        while (iterator.hasNext() && !(wi_12 = (wi_1)iterator.next()).f(mouseEvent)) {
        }
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        wi_1 wi_12;
        Iterator iterator = this.gr.iterator();
        while (iterator.hasNext() && !(wi_12 = (wi_1)iterator.next()).g(mouseEvent)) {
        }
    }

    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        wi_1 wi_12;
        Iterator iterator = this.gr.iterator();
        while (iterator.hasNext() && !(wi_12 = (wi_1)iterator.next()).a(mouseWheelEvent)) {
        }
    }

    public void a(wi_1 wi_12, boolean bl2) {
        if (!this.gr.contains(wi_12)) {
            if (!bl2) {
                this.gr.add(wi_12);
            } else {
                this.gr.add(0, wi_12);
            }
        }
    }

    public void a(wi_1 wi_12) {
        if (!this.gr.contains(wi_12)) {
            this.gr.remove(wi_12);
        }
    }

    public boolean cZ() {
        return this.gB;
    }

    public void k(boolean bl2) {
        this.gB = bl2;
    }

    public boolean da() {
        return this.gz;
    }

    public void l(boolean bl2) {
        this.gz = bl2;
    }

    public void a(Gk gk) {
        this.gM = gk;
    }

    public void a(ala ala2, boolean bl2) {
        if (!this.gt.contains(ala2)) {
            if (!bl2) {
                this.gt.add(ala2);
            } else {
                this.gt.add(0, ala2);
            }
        }
    }

    public void a(ala ala2) {
        this.gt.remove(ala2);
    }

    public void focusGained(FocusEvent focusEvent) {
        ala ala2;
        Iterator iterator = this.gt.iterator();
        while (iterator.hasNext() && !(ala2 = (ala)iterator.next()).a(focusEvent)) {
        }
    }

    public void focusLost(FocusEvent focusEvent) {
        ala ala2;
        Iterator iterator = this.gt.iterator();
        while (iterator.hasNext() && !(ala2 = (ala)iterator.next()).b(focusEvent)) {
        }
    }

    public void a(zz_1 zz_12) {
        if (this.gJ == null) {
            this.gJ = new ArrayList();
        }
        this.gJ.add(zz_12);
    }
}

