/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from Ro
 */
public class ro_0
extends acy_1 {
    private float bIF;
    private float bIG;
    private float bIH;
    private float bII;
    private float bIJ = 1.0f;
    private float bIK = 1.0f;
    private float bIL = 1.0f;
    private float bsE = 1.0f;

    public ro_0(float f, float f2, float f3, float f4) {
        this.bIF = f;
        this.bIG = f2;
        this.bIH = f3;
        this.bII = f4;
    }

    public ro_0() {
    }

    public void z(float f, float f2) {
        this.bIH = f;
        this.bII = f2;
    }

    public void A(float f, float f2) {
        this.bIF = f;
        this.bIG = f2;
    }

    public void c(float f, float f2, float f3) {
        this.bIJ = f;
        this.bIK = f2;
        this.bIL = f3;
    }

    public void setWidth(float f) {
        this.bsE = f;
    }

    public void b(GL gL) {
        db_2 db_22 = arX.cQT.iE();
        vo_1 vo_12 = vo_1.aik();
        vo_12.cu(false);
        gL.glDisable(34037);
        gL.glLineWidth(this.bsE);
        vo_12.n(db_22);
        gL.glBegin(2);
        gL.glColor3f(this.bIJ, this.bIK, this.bIL);
        gL.glVertex3f(this.bIF, this.bIG, this.clx);
        gL.glVertex3f(this.bIH, this.bII, this.clx);
        gL.glEnd();
        vo_12.cu(true);
        vo_12.n(db_22);
    }
}

