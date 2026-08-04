/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from El
 */
public abstract class el_0 {
    float fG;
    float Hk;
    float Hl;
    float Hm;
    float IQ;
    float IR;
    float IS;
    Du Ie;
    lP aQj;
    int auT;
    int auU;
    jg_0 auW;
    final lu_0[] aQk;
    final aua_0 auS;
    final int aQl;
    static final float aQm = 0.25f;
    static int aQn = 0;
    static final float aQo = 256.0f;
    static final float aQp = 128.0f;

    public el_0() {
        this.aQl = 75;
        this.aQk = new lu_0[this.aQl];
        this.auS = new aua_0(this.aQl);
        for (int j = 0; j < this.aQk.length; ++j) {
            this.aQk[j] = new lu_0();
        }
        this.auW = new jg_0(this.aQl);
        this.Ie = new et_0();
        this.aQj = (lP)do_0.aNC.P();
        this.aQj.r(0.0f, 0.0f, 0.0f);
        this.aQj.s(0.0f, 0.0f, 0.0f);
    }

    public abstract void a(float var1);

    public abstract void reset();

    public abstract void a(float var1, float var2, float var3, float var4, float var5, float var6);

    public abstract boolean isDead();
}

