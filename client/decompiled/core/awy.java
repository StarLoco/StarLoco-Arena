/*
 * Decompiled with CFR 0.152.
 */
class awy
implements aoU {
    final /* synthetic */ jt_2 abx;
    final /* synthetic */ long aby;
    final /* synthetic */ dx_2 dhV;

    awy(dx_2 dx_22, jt_2 jt_22, long l2) {
        this.dhV = dx_22;
        this.abx = jt_22;
        this.aby = l2;
    }

    public boolean b(long l2, byte[] byArray) {
        et_2 et_22 = new et_2();
        et_22.b(byArray, false);
        ee_2 ee_22 = new ee_2();
        ee_22.c(l2);
        if (et_22.NK()) {
            et_22.T(et_2.a(et_22.Nz(), (System.currentTimeMillis() / 1000L - this.abx.Wm()) / 3600L));
        }
        ee_22.f(et_22);
        adY.atu().j(ee_22);
        if (et_22.NK()) {
            xz_0.amc().j(ee_22.getId(), this.aby);
        }
        return true;
    }
}

