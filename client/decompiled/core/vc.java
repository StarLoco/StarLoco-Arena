/*
 * Decompiled with CFR 0.152.
 */
public class vc
implements tQ {
    public void a(akn_1 akn_12) {
        if (!this.b(akn_12)) {
            akn_1 akn_13 = yi_2.e(akn_12);
            cq_0 cq_02 = new cq_0();
            if (akn_13 == null) {
                akn_12 = cq_02;
            } else {
                akn_13.g(cq_02);
            }
        }
    }

    public boolean b(akn_1 akn_12) {
        for (akn_1 akn_13 = akn_12; akn_13 != null; akn_13 = akn_13.azY()) {
            if (!(akn_13 instanceof dd_2)) continue;
            return true;
        }
        return false;
    }
}

