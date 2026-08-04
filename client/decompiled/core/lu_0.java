/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from LU
 */
public final class lu_0 {
    float Hk;
    float Hl;
    float Hm;
    float IQ;
    float IR;
    float IS;
    float IT;
    float KQ;
    float KR;
    float KM;
    float KN;
    float KO;
    float bsA;
    float bsB;
    float bsC;
    float bsD;
    float bsE;
    float bsF;
    float bsG;
    private static final float bsH = 0.5f;
    private static final float bsI = -2.3f;

    public lu_0() {
        float f = 256.0f;
        float f2 = 128.0f;
        this.bsB = 0.65234375f;
        this.bsC = 0.6875f;
        this.bsA = 0.890625f;
        this.bsD = 0.953125f;
    }

    public final void a(float f) {
        assert (!this.isDead());
        this.KQ -= f;
        if (this.KQ < 0.5f) {
            this.IT = this.KQ / 0.5f;
        }
        if (this.KQ < 1.0f) {
            this.bsE *= 1.0f - 0.5f * f;
            this.bsF *= 1.0f - 0.5f * f;
        }
        if (this.KM < -0.2f || this.KM > 0.2f) {
            this.KM *= 1.0f - 2.8f * f;
        }
        if (this.KN < -0.2f || this.KN > 0.2f) {
            this.KN *= 1.0f - 2.8f * f;
        }
        this.KO *= 1.0f - 2.8f * f;
        this.KO += f * -2.3f;
        this.Hk += f * this.KM;
        this.Hl += f * this.KN;
        this.Hm += f * this.KO;
    }

    public final boolean isDead() {
        return this.KQ < 0.0f;
    }
}

