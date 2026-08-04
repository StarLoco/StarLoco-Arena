/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ZO
 */
public class zo_0 {
    public float IQ = 1.0f;
    public float IR = 1.0f;
    public float IS = 1.0f;
    public float IT = 1.0f;
    private float IY = 0.0f;
    private float IZ = 0.0f;
    private float Ja = 0.0f;
    private float Jb = 0.0f;
    private float IU = 0.0f;
    private float IV = 0.0f;
    private float IW = 0.0f;
    private float IX = 0.0f;
    private float cej;
    private float cek;

    private zo_0() {
    }

    void a(float f, float f2, float f3, float f4, float f5) {
        this.IU = f;
        this.IV = f2;
        this.IW = f3;
        this.IX = f4;
        this.IY = this.IQ;
        this.IZ = this.IR;
        this.Ja = this.IS;
        this.Jb = this.IT;
        this.cek = this.cej = f5;
    }

    public boolean js(int n2) {
        if (this.cej < 0.0f) {
            if (this.cej == Float.NEGATIVE_INFINITY) {
                return false;
            }
            this.IQ = this.IY = this.IU;
            this.IR = this.IZ = this.IV;
            this.IS = this.Ja = this.IW;
            this.IT = this.Jb = this.IX;
            this.cej = Float.NEGATIVE_INFINITY;
            return this.IQ == 1.0f && this.IR == 1.0f && this.IS == 1.0f && this.IT == 1.0f;
        }
        this.cej -= (float)n2;
        float f = 1.0f - this.cej / this.cek;
        this.IQ = this.IY + (this.IU - this.IY) * f;
        this.IR = this.IZ + (this.IV - this.IZ) * f;
        this.IS = this.Ja + (this.IW - this.Ja) * f;
        this.IT = this.Jb + (this.IX - this.Jb) * f;
        return false;
    }

    /* synthetic */ zo_0(abF abF2) {
        this();
    }
}

