/*
 * Decompiled with CFR 0.152.
 */
class dd
extends a_0 {
    final /* synthetic */ atg_0 kF;

    private dd(atg_0 atg_02) {
        this.kF = atg_02;
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
        if (atg_0.a(this.kF)) {
            atg_0.a(this.kF, false);
        }
        if (atg_0.b(this.kF)) {
            while (atg_0.c(this.kF) < atg_0.d(this.kF).size()) {
                ((azc_0)atg_0.d(this.kF).remove(atg_0.c(this.kF))).aab();
            }
            if (atg_0.c(this.kF) > atg_0.d(this.kF).size()) {
                azc_0 azc_02;
                if (atg_0.d(this.kF).size() == 0) {
                    azc_02 = new azc_0();
                    azc_02.b();
                    azc_02.setNonBlocking(true);
                    azc_02.setModulationColor(atg_0.e(this.kF));
                    azc_02.a(atg_0.f(this.kF).aah());
                    this.a(azc_02);
                    atg_0.d(this.kF).add(azc_02);
                }
                while (atg_0.c(this.kF) > atg_0.d(this.kF).size()) {
                    azc_02 = (azc_0)((azc_0)atg_0.d(this.kF).get(0)).aah();
                    this.a(azc_02);
                    atg_0.d(this.kF).add(azc_02);
                }
                this.kF.b((na_1)atg_0.g(this.kF));
                this.a((na_1)atg_0.g(this.kF));
            }
            atg_0.h(this.kF);
            atg_0.b(this.kF, false);
        }
        if (atg_0.i(this.kF)) {
            this.kF.aGd();
        }
        if (atg_0.j(this.kF)) {
            this.kF.aGe();
            atg_0.h(this.kF);
        }
    }

    /* synthetic */ dd(atg_0 atg_02, agf_1 agf_12) {
        this(atg_02);
    }
}

