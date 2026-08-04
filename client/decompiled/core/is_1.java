/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from is
 */
class is_1
implements bz_0 {
    final /* synthetic */ boolean ym;
    final /* synthetic */ int iO;
    final /* synthetic */ int iP;
    final /* synthetic */ boolean iR;
    final /* synthetic */ JX iS;
    final /* synthetic */ String iT;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ ix_0 yn;

    is_1(ix_0 ix_02, boolean bl2, int n2, int n3, boolean bl3, JX jX, String string, jJ[] jJArray) {
        this.yn = ix_02;
        this.ym = bl2;
        this.iO = n2;
        this.iP = n3;
        this.iR = bl3;
        this.iS = jX;
        this.iT = string;
        this.iU = jJArray;
    }

    public void a(ee_2 ee_22, yp_2 yp_22, int n2, int n3) {
        if (!this.ym || n2 == this.iO && n3 == this.iP) {
            ee_22.b(this);
            if (this.iR) {
                this.iS.Wu();
            } else {
                this.iS.a(this.iT, this.iU, new amd_0[0]);
            }
        }
    }
}

