/*
 * Decompiled with CFR 0.152.
 */
import java.awt.geom.Rectangle2D;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

/*
 * Renamed from Rk
 */
public class rk_2
extends ep_2 {
    protected Pq bIx = null;
    protected boolean bIy = false;
    protected boolean aiC;
    protected float bIz = 1024.0f;
    protected float bIA = 768.0f;
    protected boolean bIB = true;
    protected final Rectangle2D bIC;
    protected boolean bID = false;
    protected final nn_2 bIE = new nn_2(1.0f, 1.0f);

    public rk_2() {
        this.bIC = new Rectangle2D.Float();
        this.b(axj_0.aJT(), 5890);
        this.b(axj_0.aJT(), 5888);
        this.adD();
        this.d(false);
        this.setVisible(false);
    }

    public void ao(float f) {
        this.bIE.x(f);
        this.bIE.y(f);
        this.adD();
    }

    public float adC() {
        return this.bIE.getX();
    }

    protected void adD() {
        this.fo(5889);
        this.b(axj_0.aJT(), 5889);
        this.b(new aeb_2(this.bIz, this.bIA, this.bIB), 5889);
        this.b(this.bIE, 5889);
        this.a(new TV(0.0, 0.0, this.bIz, this.bIA));
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = this.bIz / this.bIE.getX();
        float f4 = this.bIA / this.bIE.getY();
        if (this.bIB) {
            f = -f3 * 0.5f;
            f2 = -f4 * 0.5f;
        }
        this.o(f, f2, f3, f4);
    }

    public void uninitialize() {
        super.uninitialize();
        this.bq();
        this.bIy = false;
        this.aiC = false;
    }

    public void init(GLAutoDrawable gLAutoDrawable) {
        this.cg(true);
        this.ch(true);
    }

    public boolean adE() {
        return this.bIy;
    }

    public void cg(boolean bl2) {
        this.bIy = bl2;
        this.aiC = false;
    }

    public boolean isLoaded() {
        return this.aiC;
    }

    public void ch(boolean bl2) {
        if (this.bIy) {
            this.aiC = bl2;
        }
    }

    public float adF() {
        return this.bIz;
    }

    public float adG() {
        return this.bIA;
    }

    public void P(int n2, int n3) {
        this.bIz = n2;
        this.bIA = n3;
        this.adD();
    }

    public boolean adH() {
        return this.bIB;
    }

    public void ci(boolean bl2) {
        this.bIB = bl2;
        this.adD();
    }

    public boolean adI() {
        return this.bID;
    }

    public void cj(boolean bl2) {
        this.bID = bl2;
    }

    public Pq adJ() {
        return this.bIx;
    }

    public void a(Pq pq) {
        this.bIx = pq;
        this.fo(5888);
        this.b(axj_0.aJT(), 5888);
        this.b(this.bIx, 5888);
    }

    public void h(GL gL) {
        if (this.aiC) {
            TV tV = this.Ni();
            int n2 = (int)tV.getX();
            int n3 = (int)tV.getY();
            int n4 = (int)tV.getWidth();
            int n5 = (int)tV.getHeight();
            int[] nArray = new int[4];
            gL.glGetIntegerv(2978, nArray, 0);
            gL.glViewport(n2, n3, n4, n5);
            gL.glScissor(n2, n3, n4, n5);
            super.h(gL);
            gL.glViewport(nArray[0], nArray[1], nArray[2], nArray[3]);
        }
    }

    public void o(float f, float f2, float f3, float f4) {
        this.bIC.setRect(f, f2, f3, f4);
    }

    public Rectangle2D adK() {
        return this.bIC;
    }

    public String toString() {
        return this.Nj() + " meshs";
    }
}

