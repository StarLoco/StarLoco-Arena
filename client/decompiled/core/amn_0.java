/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from amN
 */
class amn_0
implements ja_1 {
    final /* synthetic */ CG cHY;
    final /* synthetic */ sj_1 cHZ;

    amn_0(sj_1 sj_12, CG cG) {
        this.cHZ = sj_12;
        this.cHY = cG;
    }

    public void b(int n2) {
        if (n2 == 8) {
            if (!(apN.aDK().c(vu_1.aip()) || apN.aDK().c(wp_0.CH()) || apN.aDK().c(ds_2.LP()))) {
                vr_2 vr_22 = vr_2.ain();
                vr_22.d(this.cHY.getId());
                acu_1.ara().c(vr_22);
            } else {
                add_1.aOG().a(aon_0.aYc().getString("cannotOpenUIWhenSearchingFight"), 1058L, 102, 1);
                azS azS2 = azS.aMv();
                azS2.d(this.cHY.getId());
                acu_1.ara().c(azS2);
            }
        } else {
            azS azS3 = azS.aMv();
            azS3.d(this.cHY.getId());
            acu_1.ara().c(azS3);
        }
    }
}

