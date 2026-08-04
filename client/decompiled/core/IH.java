/*
 * Decompiled with CFR 0.152.
 */
class IH
implements aLR {
    final /* synthetic */ iq_0 bhP;
    final /* synthetic */ axL bfg;

    IH(axL axL2, iq_0 iq_02) {
        this.bfg = axL2;
        this.bhP = iq_02;
    }

    public boolean eG(int n2) {
        aBp aBp2 = axL.a(this.bfg).mh(n2);
        if (aBp2 != null) {
            qk qk2 = aBp2.aNm();
            while (qk2.hasNext()) {
                int n3 = qk2.next();
                this.bhP.o(asu_0.mi(n3), asu_0.mj(n3));
            }
        }
        return true;
    }
}

