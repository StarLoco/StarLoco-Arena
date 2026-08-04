/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Vu
 */
public class vu_1
implements atG {
    private static vu_1 bSz = new vu_1();
    private static apk_0 auy;
    private static final add_1 auz;

    public static vu_1 aip() {
        return bSz;
    }

    public static void a(apk_0 apk_02) {
        auy = apk_02;
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    private static void cE(String string) {
        if (auy != null) {
            auy.log(string);
            auy = null;
        }
    }

    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        switch (pr_02.getId()) {
            case 23102: {
                ada_1 ada_12 = (ada_1)pr_02;
                boolean bl3 = ada_12.eY();
                vu_1.cE("Changement de statut \u00e0 non pr\u00eat " + (bl3 ? "accept\u00e9" : "rejet\u00e9") + ".");
                if (bl3 && auz.kR("classicSearchStatusDialog")) {
                    auz.kO("classicSearchStatusDialog");
                    auz.kG("dofusarena.classicSearchStatus");
                    apN.aDK().b(vu_1.aip());
                }
                bl2 = false;
                break;
            }
            case 23104: {
                aLi aLi2 = (aLi)pr_02;
                boolean bl4 = aLi2.eY();
                boolean bl5 = auz.kR("classicSearchStatusDialog");
                vu_1.cE("Changement de statut \u00e0 pr\u00eat " + (bl4 ? "accept\u00e9" : "rejet\u00e9") + ".");
                apN.aDK().b(hu_2.li());
                apN.aDK().b(wp_0.CH());
                apN.aDK().b(ds_2.LP());
                if (bl4 && !bl5) {
                    auz.a("classicSearchStatusDialog", oh_2.bq("classicSearchStatusDialog"), (short)10000);
                    auz.l("dofusarena.classicSearchStatus", avl_0.class);
                }
                bl2 = false;
                break;
            }
            case 23106: {
                vu_1.cE("Lancement du combat.");
                apN apN2 = apN.aDK();
                if (auz.kR("classicSearchStatusDialog")) {
                    auz.kO("classicSearchStatusDialog");
                    auz.kG("dofusarena.classicSearchStatus");
                    apN.aDK().b(vu_1.aip());
                }
                apN2.a(wg_2.CC());
                apN2.a(do_2.Mm());
                bl2 = false;
                break;
            }
            case 23108: {
                M m = (M)pr_02;
                byte by = m.an();
                String string = "";
                if (by == 1) {
                    string = "matchfinder.impossibleToStartOpponentsSearch";
                } else if (by == 2) {
                    string = "matchfinder.badTeam";
                } else if (by == 3) {
                    string = "matchfinder.canceledByCoach";
                } else if (by == 4) {
                    string = "matchfinder.opponentNotFound";
                }
                if ((by == 3 || by == 4 || by == 5) && auz.kR("classicSearchStatusDialog")) {
                    auz.kO("classicSearchStatusDialog");
                    auz.kG("dofusarena.classicSearchStatus");
                    apN.aDK().b(vu_1.aip());
                    apN.aDK().b(wp_0.CH());
                }
                if (by != 5) {
                    auz.f(aon_0.aYc().getString(string), 102, 1);
                }
                bl2 = false;
                break;
            }
        }
        return bl2;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    static {
        auz = add_1.aOG();
    }
}

