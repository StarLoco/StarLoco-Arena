/*
 * Decompiled with CFR 0.152.
 */
class dP
implements ja_1 {
    final /* synthetic */ lj_1 nh;
    final /* synthetic */ lj_1 ni;
    final /* synthetic */ lj_1 nj;
    final /* synthetic */ lj_1 nk;
    final /* synthetic */ lj_1 nl;
    final /* synthetic */ lj_1 nm;

    dP(lj_1 lj_12, lj_1 lj_13, lj_1 lj_14, lj_1 lj_15, lj_1 lj_16, lj_1 lj_17) {
        this.nh = lj_12;
        this.ni = lj_13;
        this.nj = lj_14;
        this.nk = lj_15;
        this.nl = lj_16;
        this.nm = lj_17;
    }

    public void b(int n2) {
        if (n2 == 8) {
            sb_0 sb_02 = new sb_0();
            sb_02.f(20174);
            acu_1.ara().c(sb_02);
            this.nh.setAnimName("AnimFusion");
            ajt_1 ajt_12 = (ajt_1)azs_0.aLV().getProperty("fusionTrade").getValue();
            int n3 = ajt_12.azw().size();
            this.ni.setAnimName("AnimFusion");
            this.nj.setAnimName("AnimFusion");
            if (n3 > 2) {
                this.nk.setAnimName("AnimFusion");
            }
            if (n3 > 3) {
                this.nl.setAnimName("AnimFusion");
            }
            if (n3 > 4) {
                this.nm.setAnimName("AnimFusion");
            }
        }
    }
}

