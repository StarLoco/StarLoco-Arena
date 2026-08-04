/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ayO
 */
class ayo_0
implements ov_1 {
    final /* synthetic */ qa_1 dmO;
    final /* synthetic */ kk_2 dmP;

    ayo_0(kk_2 kk_22, qa_1 qa_12) {
        this.dmP = kk_22;
        this.dmO = qa_12;
    }

    public boolean a(ke ke2) {
        int n2 = sx.g(this.dmP.bno).indexOf(this.dmO);
        EV eV = (EV)sx.f(this.dmP.bno).get(n2 + sx.k(this.dmP.bno));
        boolean bl2 = true;
        if (eV.hasChildren()) {
            boolean bl3 = bl2 = !eV.OL();
            if (bl2 || !sx.l(this.dmP.bno)) {
                eV.bo(bl2);
                if (sx.m(this.dmP.bno)) {
                    EV eV2;
                    if (bl2) {
                        EV eV3 = eV2 = !sx.n(this.dmP.bno).isEmpty() ? (EV)sx.n(this.dmP.bno).get(sx.n(this.dmP.bno).size() - 1) : null;
                        if (eV2 != eV.OK()) {
                            do {
                                EV eV4 = eV2 = !sx.n(this.dmP.bno).isEmpty() ? (EV)sx.n(this.dmP.bno).remove(sx.n(this.dmP.bno).size() - 1) : null;
                                if (eV2 == null) continue;
                                eV2.bo(false);
                            } while (eV2 != null && eV2.OK() != eV.OK());
                        }
                        sx.n(this.dmP.bno).add(eV);
                    } else {
                        do {
                            EV eV5 = eV2 = !sx.n(this.dmP.bno).isEmpty() ? (EV)sx.n(this.dmP.bno).remove(sx.n(this.dmP.bno).size() - 1) : null;
                            if (eV2 == null) continue;
                            eV2.bo(false);
                        } while (eV2 != null && eV2 != eV);
                    }
                }
            }
        }
        boolean bl4 = !eV.isSelected() && (bl2 || sx.l(this.dmP.bno)) || sx.o(this.dmP.bno);
        eV.setSelected(bl4);
        if (sx.p(this.dmP.bno) && (!sx.o(this.dmP.bno) || eV != sx.q(this.dmP.bno))) {
            if (bl4) {
                if (sx.q(this.dmP.bno) != null) {
                    sx.q(this.dmP.bno).setSelected(false);
                }
                sx.a(this.dmP.bno, eV);
            } else {
                if (sx.q(this.dmP.bno) != null) {
                    sx.q(this.dmP.bno).setSelected(false);
                }
                sx.a(this.dmP.bno, null);
            }
            this.dmP.bno.f(new gm_0(this.dmP.bno));
        }
        sx.r(this.dmP.bno);
        return false;
    }
}

