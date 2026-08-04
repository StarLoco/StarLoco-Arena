/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

/*
 * Renamed from aLJ
 */
public class alj_0 {
    private static alj_0 cuI = new alj_0();
    private static boolean DEBUG = false;
    public static int dWb = 770;
    public static int dWc = 771;
    private GL go;
    private GLU gp = new GLU();
    private GLUquadric dWd = this.gp.gluNewQuadric();
    private int fb;
    private int fc;
    private Point dWe = new Point();
    private nm_0 dWf;
    private final ArrayList dWg = new ArrayList();
    private GLDrawable dWh;

    public static alj_0 aWw() {
        return cuI;
    }

    private alj_0() {
    }

    public void a(GL gL, GLDrawable gLDrawable) {
        this.go = gL;
        this.dWh = gLDrawable;
        this.fb = this.dWh.getWidth();
        this.fc = this.dWh.getHeight();
    }

    public final Point aWx() {
        return this.dWe;
    }

    public boolean g(nm_0 nm_02) {
        return nm_02.d(this.dWf);
    }

    public nm_0 aWy() {
        return this.dWf;
    }

    public void h(nm_0 nm_02) {
        if (!this.dWg.isEmpty()) {
            nm_0 nm_03 = (nm_0)this.dWg.get(this.dWg.size() - 1);
            if (nm_03.d(nm_02)) {
                nm_02.a(nm_03, nm_02);
            } else {
                nm_02.setBounds(0, 0, 0, 0);
            }
        }
        this.dWg.add(nm_02);
        this.dWf = nm_02;
    }

    public void aWz() {
        if (!this.dWg.isEmpty()) {
            ((nm_0)this.dWg.remove(this.dWg.size() - 1)).release();
            this.dWf = this.aWA();
        }
    }

    public nm_0 aWA() {
        if (!this.dWg.isEmpty()) {
            return (nm_0)this.dWg.get(this.dWg.size() - 1);
        }
        return null;
    }

    public void cm(int n2, int n3) {
        this.fb = n2;
        this.fc = n3;
        this.dWf = nm_0.k(0, 0, n2, n3);
        this.dWe.setLocation(-n2 / 2, -n3 / 2);
    }

    public agj_1 aWB() {
        return new agj_1(this.fb, this.fc);
    }

    public void drawRect(int n2, int n3, int n4, int n5) {
        this.go.glBegin(2);
        this.go.glVertex2i(n2, n3);
        this.go.glVertex2i(n2 + n4, n3);
        this.go.glVertex2i(n2 + n4, n3 + n5);
        this.go.glVertex2i(n2, n3 + n5);
        this.go.glEnd();
    }

    public void B(int n2, int n3, int n4, int n5) {
        this.go.glBegin(7);
        this.go.glVertex2i(n2, n3);
        this.go.glVertex2i(n2 + n4, n3);
        this.go.glVertex2i(n2 + n4, n3 + n5);
        this.go.glVertex2i(n2, n3 + n5);
        this.go.glEnd();
    }

    public void a(vg_2 vg_22, String string, int n2, int n3) {
        vg_22.beginRendering(this.fb, this.fc);
        vg_22.draw(string, n2, n3);
        vg_22.endRendering();
    }

    public void P(int n2, int n3, int n4) {
        this.gp.gluQuadricTexture(this.dWd, true);
        this.go.glTranslatef(n2, n3, 0.0f);
        this.gp.gluDisk(this.dWd, 0.0, n4, n4 > 50 ? 32 : 16, 1);
        this.go.glTranslatef(-n2, -n3, 0.0f);
    }

    public void setColor(vP vP2) {
        this.go.glColor4f(vP2.Cp() / 255.0f, vP2.Cq() / 255.0f, vP2.Cr() / 255.0f, vP2.getAlpha() / 255.0f);
    }

    public void translate(int n2, int n3) {
        this.go.glTranslatef(n2, n3, 0.0f);
    }
}

