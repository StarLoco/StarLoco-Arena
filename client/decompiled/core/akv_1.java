/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from akv
 */
class akv_1
implements ja_1 {
    final /* synthetic */ ee_2 cDu;
    final /* synthetic */ xj cDv;
    final /* synthetic */ wy_2 bLY;
    final /* synthetic */ hu_2 cDw;

    akv_1(hu_2 hu_22, ee_2 ee_22, xj xj2, wy_2 wy_22) {
        this.cDw = hu_22;
        this.cDu = ee_22;
        this.cDv = xj2;
        this.bLY = wy_22;
    }

    public void b(int n2) {
        if (n2 == 8) {
            bw bw2 = new bw();
            bw2.j(this.cDu.getId());
            bw2.i(this.cDv.jf());
            apN.aDK().vJ().b(bw2);
            this.bLY.w((short)-1);
            azs_0.aLV().a((aho_0)aij_0.aUF(), "evolutionSets");
            fe_1 fe_12 = (fe_1)azs_0.aLV().getProperty("coachManagement.currentSet").getValue();
            azs_0.aLV().a((aho_0)fe_12, fe_1.ce);
        }
    }
}

