/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aqO
 */
class aqo_0
implements ja_1 {
    final /* synthetic */ tb_2 cOJ;
    final /* synthetic */ ft_1 cOK;

    aqo_0(ft_1 ft_12, tb_2 tb_22) {
        this.cOK = ft_12;
        this.cOJ = tb_22;
    }

    public void b(int n2) {
        if (n2 == 8 || n2 == 16) {
            acz_2 acz_22 = new acz_2();
            acz_22.cl(this.cOJ.zI());
            acz_22.aj(this.cOJ.qX());
            acz_22.C(this.cOJ.qY());
            acz_22.M(this.cOJ.cB());
            acz_22.a(this.cOJ.zK());
            acz_22.cb(n2 == 8);
            apN.aDK().vJ().b(acz_22);
            if (n2 == 8) {
                po_0.abV().abW();
                mh_1.aj(this.cOJ.qX());
                mh_1.C(this.cOJ.qY());
                apN.aDK().a(vu_1.aip());
            }
        }
    }
}

