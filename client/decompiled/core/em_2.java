/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from eM
 */
class em_2
implements aOV {
    final /* synthetic */ boolean iR;
    final /* synthetic */ JX iS;
    final /* synthetic */ String iT;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ amd_2 pT;

    em_2(amd_2 amd_22, boolean bl2, JX jX, String string, jJ[] jJArray) {
        this.pT = amd_22;
        this.iR = bl2;
        this.iS = jX;
        this.iT = string;
        this.iU = jJArray;
    }

    public void b(abm_2 abm_22, int n2, int n3, short s) {
        abm_22.b(this);
        if (this.iR) {
            this.iS.Wu();
        } else {
            this.iS.a(this.iT, this.iU, new amd_0[0]);
        }
    }
}

