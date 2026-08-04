/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abp
 */
class abp_1
extends a_0 {
    final /* synthetic */ apc_1 aCu;

    private abp_1(apc_1 apc_12) {
        this.aCu = apc_12;
    }

    public boolean aO() {
        return false;
    }

    public agj_1 getContentMinSize(aht_1 aht_12) {
        return new agj_1(0, 0);
    }

    public agj_1 getContentPreferedSize(aht_1 aht_12) {
        return new agj_1(0, 0);
    }

    public void a(aht_1 aht_12) {
        if (apc_1.b(this.aCu)) {
            if (apc_1.c(this.aCu) != null) {
                while (apc_1.c(this.aCu).length < apc_1.d(this.aCu).size()) {
                    ((azc_0)apc_1.d(this.aCu).remove(apc_1.c(this.aCu).length)).aab();
                }
                if (apc_1.c(this.aCu).length > apc_1.d(this.aCu).size()) {
                    azc_0 azc_02;
                    if (apc_1.d(this.aCu).size() == 0) {
                        azc_02 = new azc_0();
                        azc_02.b();
                        azc_02.setNonBlocking(true);
                        azc_02.setModulationColor(this.aCu.getModulationColor());
                        azc_02.a(apc_1.e(this.aCu).aah());
                        this.a(azc_02);
                        apc_1.d(this.aCu).add(azc_02);
                    }
                    while (apc_1.c(this.aCu).length > apc_1.d(this.aCu).size()) {
                        azc_02 = (azc_0)((azc_0)apc_1.d(this.aCu).get(0)).aah();
                        this.a(azc_02);
                        apc_1.d(this.aCu).add(azc_02);
                    }
                }
            }
            this.apY();
            apc_1.a(this.aCu, false);
        }
    }

    private void apY() {
        if (apc_1.c(this.aCu) == null || apc_1.c(this.aCu).length == 0 || apc_1.d(this.aCu) == null || apc_1.d(this.aCu).size() != apc_1.c(this.aCu).length) {
            return;
        }
        if (apc_1.f(this.aCu)) {
            for (int j = 0; j < apc_1.d(this.aCu).size(); ++j) {
                azc_0 azc_02 = (azc_0)apc_1.d(this.aCu).get(j);
                float f = apc_1.c(this.aCu)[j];
                azc_02.setPosition(Math.round((float)this.aCu.getAppearance().getContentWidth() * f), this.aCu.getAppearance().getContentHeight() / 2 - azc_02.getHeight() / 2);
            }
        } else {
            for (int j = 0; j < apc_1.d(this.aCu).size(); ++j) {
                azc_0 azc_03 = (azc_0)apc_1.d(this.aCu).get(j);
                float f = apc_1.c(this.aCu)[j];
                azc_03.setPosition(this.aCu.getAppearance().getContentWidth() / 2 - azc_03.getWidth() / 2, Math.round((float)this.aCu.getAppearance().getContentHeight() * f));
            }
        }
        apc_1.a(this.aCu, false);
        this.setNeedsToMiddleProcess();
    }

    /* synthetic */ abp_1(apc_1 apc_12, mf_0 mf_02) {
        this(apc_12);
    }
}

