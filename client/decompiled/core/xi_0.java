/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from XI
 */
class xi_0
implements aoU {
    final /* synthetic */ jt_2 abx;
    final /* synthetic */ long aby;
    final /* synthetic */ ce_1 bZm;

    xi_0(ce_1 ce_12, jt_2 jt_22, long l2) {
        this.bZm = ce_12;
        this.abx = jt_22;
        this.aby = l2;
    }

    public boolean b(long l2, byte[] byArray) {
        et_2 et_22 = new et_2();
        et_22.b(byArray, false);
        et_22.T(et_2.a(et_22.Nz(), (System.currentTimeMillis() / 1000L - this.abx.Wm()) / 3600L));
        ee_2 ee_22 = new ee_2();
        ee_22.c(l2);
        ee_22.f(et_22);
        adY.atu().j(ee_22);
        if (et_22.NK()) {
            xz_0.amc().j(ee_22.getId(), this.aby);
        }
        return true;
    }
}

