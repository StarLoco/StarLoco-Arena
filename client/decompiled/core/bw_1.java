/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Bw
 */
class bw_1
implements mg_2 {
    final /* synthetic */ no_2 aIR;

    bw_1(no_2 no_22) {
        this.aIR = no_22;
    }

    public void a(ahh_1 ahh_12) {
        apn_0 apn_02 = ((hh_2)ahh_12).zp();
        if (apn_02 != null) {
            for (axu_0 axu_02 : apn_02.aYW()) {
                if (!(axu_02 instanceof hh_2)) continue;
                ((tp_1)axu_02).az(true);
            }
            ajX.azB().d(apn_02);
        } else {
            no_2.a.warn((Object)("Impossible de retirer un \u00e9l\u00e9ment interactif ID=" + apn_02 + ", il n'est r\u00e9f\u00e9renc\u00e9 dans aucune partition."));
        }
        ahh_12.b(this);
    }
}

