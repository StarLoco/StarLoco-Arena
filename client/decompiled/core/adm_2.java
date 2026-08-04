/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aDm
 */
class adm_2
implements ja_1 {
    final /* synthetic */ vg bCc;

    adm_2(vg vg2) {
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

