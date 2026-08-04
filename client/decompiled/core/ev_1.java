/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ev
 */
class ev_1
implements ja_1 {
    final /* synthetic */ aou_2 oQ;

    ev_1(aou_2 aou_22) {
        this.oQ = aou_22;
    }

    public void b(int n2) {
        if (n2 == 8) {
            bw bw2 = new bw();
            bw2.j(this.oQ.cDu.getId());
            bw2.i(this.oQ.cDv.jf());
            apN.aDK().vJ().b(bw2);
            this.oQ.bLY.w((short)-1);
            azs_0.aLV().a((aho_0)aij_0.aUF(), "evolutionSets");
            fe_1 fe_12 = (fe_1)azs_0.aLV().getProperty("coachManagement.currentSet").getValue();
            azs_0.aLV().a((aho_0)fe_12, fe_1.ce);
        }
    }
}

