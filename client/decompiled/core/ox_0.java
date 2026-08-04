/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ox
 */
class ox_0
implements ja_1 {
    final /* synthetic */ qr_0 bCa;
    final /* synthetic */ int bCb;
    final /* synthetic */ vg bCc;
    final /* synthetic */ oz_1 bCd;

    ox_0(oz_1 oz_12, qr_0 qr_02, int n2, vg vg2) {
        this.bCd = oz_12;
        this.bCa = qr_02;
        this.bCb = n2;
        this.bCc = vg2;
    }

    public void b(int n2) {
        if (n2 == 8) {
            aik_0 aik_02 = new aik_0();
            aik_02.ad(this.bCa.fx());
            aik_02.aj(apN.aDK().Ln().getId());
            aik_02.C((short)-1);
            aik_02.kV(this.bCb);
            apN.aDK().vJ().b(aik_02);
            if (this.bCc.By().length > 0) {
                r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("saveProfileQuestion"), 24L, 102, 0);
                r_02.a(new tu_0(this));
            }
        }
    }
}

