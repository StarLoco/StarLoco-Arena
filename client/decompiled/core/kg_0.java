/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Kg
 */
class kg_0
implements aji_0 {
    private acv_2 aci;
    final /* synthetic */ hh_2 bni;

    kg_0(hh_2 hh_22) {
        this.bni = hh_22;
    }

    public void a(hh_2 hh_22, boolean bl2) {
        if (hh_22.isVisible() && hh_22.gs()) {
            if (bl2) {
                Object object;
                if (this.bni.mY) {
                    object = ((do_1)hh_22.zp()).getName();
                    String string = this.bni.kE();
                    if (string != null) {
                        object = object + string;
                    }
                    if (object == null) {
                        object = "erreur le texte est null";
                        hh_2.kF().error((Object)"Le text est null, ce qui veut certainement dire que l initialisation de l'objet n a pas eu lieu ou s est mal pass\u00e9.");
                    }
                    this.aci = new acv_2((String)object);
                    this.aci.c(hh_22);
                    wj_2.Df().a(this.aci);
                }
                if (this.bni.vf) {
                    hh_22.c(hh_2.ve);
                    hh_2.b(this.bni).c(hh_2.a(this.bni));
                    object = ((do_1)hh_22.zp()).getCursorType();
                    if (object != null) {
                        apw_1.aDr().a((xy_0)((Object)object));
                    }
                }
            } else {
                if (this.aci != null) {
                    wj_2.Df().b(this.aci);
                }
                apw_1.aDr().a(xy_0.bYl);
                hh_22.aTA();
                hh_2.d(this.bni).c(hh_2.c(this.bni));
            }
        }
    }
}

