/*
 * Decompiled with CFR 0.152.
 */
class aoM
implements ja_1 {
    final /* synthetic */ avP cLm;
    final /* synthetic */ wy_2 bLY;
    final /* synthetic */ aks_2 cLn;

    aoM(aks_2 aks_22, avP avP2, wy_2 wy_22) {
        this.cLn = aks_22;
        this.cLm = avP2;
        this.bLY = wy_22;
    }

    public void b(int n2) {
        ee_2 ee_22;
        if (n2 == 8 && (ee_22 = this.cLm.tG()) != null && this.bLY != null) {
            bw bw2 = new bw();
            bw2.j(ee_22.getId());
            bw2.i(this.cLm.getIntValue());
            apN.aDK().vJ().b(bw2);
        }
    }
}

