/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from TL
 */
public class tl_0
extends acy_1 {
    private float bIF;
    private float bIG;
    private float bIH;
    private float bII;
    private float IT = 0.75f;

    public tl_0() {
        this.a(ahp_2.awV());
        this.b(un_0.ahN());
    }

    public void b(GL gL) {
        if (!this.aQv) {
            return;
        }
        gL.glColor4f(this.IT, this.IT, this.IT, this.IT);
        gL.glLineWidth(4.0f);
        gL.glBegin(1);
        gL.glVertex3f(this.bIF, this.bIG, this.arN());
        gL.glVertex3f(this.bIH, this.bII, this.arN());
        gL.glEnd();
    }

    public void A(float f, float f2) {
        this.bIF = f;
        this.bIG = f2;
    }

    public void z(float f, float f2) {
        this.bIH = f;
        this.bII = f2;
    }

    public void W(float f) {
        this.IT = f;
    }
}

