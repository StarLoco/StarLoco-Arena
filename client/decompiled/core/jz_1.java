/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Jz
 */
class jz_1
implements ov_1 {
    final /* synthetic */ ana_0 yj;

    jz_1(ana_0 ana_02) {
        this.yj = ana_02;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        int n2 = abd_12.p(this.yj) - this.yj.cwV;
        int n3 = abd_12.q(this.yj) - this.yj.cwW;
        bt_2 bt_22 = null;
        int n4 = ana_0.h(this.yj).size();
        for (int j = 0; j < n4; ++j) {
            bt_2 bt_23 = (bt_2)ana_0.h(this.yj).get(j);
            if (!ana_0.a(this.yj, bt_23, n2, n3)) continue;
            bt_22 = bt_23;
            break;
        }
        if (ana_0.i(this.yj) == bt_22) {
            return false;
        }
        ana_0.a(this.yj, bt_22);
        ana_0.j(this.yj).setVisible(ana_0.i(this.yj) != null);
        if (ana_0.i(this.yj) != null) {
            ana_0.k(this.yj).setText(ana_0.i(this.yj).cN());
        }
        return false;
    }
}

