/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Lu
 */
class lu_2
extends apc {
    final /* synthetic */ apN brw;
    final /* synthetic */ yq_2 brv;

    lu_2(yq_2 yq_22, apN apN2) {
        this.brv = yq_22;
        this.brw = apN2;
    }

    public boolean a(ke ke2) {
        if (!(apN.aDK().c(vu_1.aip()) || apN.aDK().c(wp_0.CH()) || apN.aDK().c(ds_2.LP()))) {
            fw_1 fw_12 = new fw_1();
            fw_12.N(yq_2.a(this.brv).getId());
            this.brw.vJ().b(fw_12);
            yq_2.a(this.brv, null);
        } else {
            add_1.aOG().a(aon_0.aYc().getString("cannotOpenUIWhenSearchingFight"), 1058L, 102, 1);
        }
        return false;
    }
}

