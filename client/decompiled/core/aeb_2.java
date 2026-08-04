/*
 * Decompiled with CFR 0.152.
 */
import javax.media.opengl.GL;

/*
 * Renamed from aEb
 */
public class aeb_2
implements Pq {
    private float bsB;
    private float bsC;
    private float bsA;
    private float bsD;
    private static final float doM = -200.0f;
    private static final float doN = 100.0f;

    public aeb_2(float f, float f2, boolean bl2) {
        if (bl2) {
            this.bsC = f * 0.5f;
            this.bsB = -this.bsC;
            this.bsA = f2 * 0.5f;
            this.bsD = -this.bsA;
        } else {
            this.bsC = f;
            this.bsB = 0.0f;
            this.bsA = f2;
            this.bsD = 0.0f;
        }
    }

    public void reset() {
    }

    public void a(GL gL) {
        gL.glOrtho(this.bsB, this.bsC, this.bsD, this.bsA, -200.0, 100.0);
    }
}

