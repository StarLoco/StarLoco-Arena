/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from vW
 */
class vw_1
implements mg_2 {
    final /* synthetic */ mT iQ;
    final /* synthetic */ boolean iR;
    final /* synthetic */ JX iS;
    final /* synthetic */ String iT;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ ew_2 aub;

    vw_1(ew_2 ew_22, mT mT2, boolean bl2, JX jX, String string, jJ[] jJArray) {
        this.aub = ew_22;
        this.iQ = mT2;
        this.iR = bl2;
        this.iS = jX;
        this.iT = string;
        this.iU = jJArray;
    }

    public void a(ahh_1 ahh_12) {
        this.iQ.b(this);
        if (this.iR) {
            this.iS.Wu();
        } else {
            this.iS.a(this.iT, this.iU, new amd_0[0]);
        }
    }
}

