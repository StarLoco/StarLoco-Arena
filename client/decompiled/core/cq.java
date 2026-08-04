/*
 * Decompiled with CFR 0.152.
 */
class cq
implements aje {
    final /* synthetic */ int iO;
    final /* synthetic */ int iP;
    final /* synthetic */ mT iQ;
    final /* synthetic */ boolean iR;
    final /* synthetic */ JX iS;
    final /* synthetic */ String iT;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ aed_1 iV;

    cq(aed_1 aed_12, int n2, int n3, mT mT2, boolean bl2, JX jX, String string, jJ[] jJArray) {
        this.iV = aed_12;
        this.iO = n2;
        this.iP = n3;
        this.iQ = mT2;
        this.iR = bl2;
        this.iS = jX;
        this.iT = string;
        this.iU = jJArray;
    }

    public void a(abm_2 abm_22, int n2, int n3, short s) {
        if (n2 == this.iO && n3 == this.iP) {
            this.iQ.b(this);
            if (this.iR) {
                this.iS.Wu();
            } else {
                this.iS.a(this.iT, this.iU, new amd_0[0]);
            }
        }
    }
}

