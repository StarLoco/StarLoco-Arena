/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Oy
 */
class oy_1
implements ja_1 {
    final /* synthetic */ vg bCc;
    final /* synthetic */ oz_1 bCd;

    oy_1(oz_1 oz_12, vg vg2) {
        this.bCd = oz_12;
        this.bCc = vg2;
    }

    public void b(int n2) {
        if (n2 == 8) {
            lp_0 lp_02 = new lp_0(this.bCc.BC() + "." + "apf");
            lp_02.e(this.bCc.By());
            if (!br.b(lp_02)) {
                add_1.aOG().a(aon_0.aYc().getString("errorSavingFile"), 1091L, 102, 1);
            }
        }
    }
}

