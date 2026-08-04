/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from IG
 */
class ig_0
implements aLR {
    final /* synthetic */ tf_0 bhO;
    final /* synthetic */ axL bfg;

    ig_0(axL axL2, tf_0 tf_02) {
        this.bfg = axL2;
        this.bhO = tf_02;
    }

    public boolean eG(int n2) {
        aBp aBp2 = axL.a(this.bfg).mh(n2);
        if (aBp2 != null) {
            qk qk2 = aBp2.aNm();
            while (qk2.hasNext()) {
                this.bhO.gg(qk2.next());
            }
        }
        return true;
    }
}

