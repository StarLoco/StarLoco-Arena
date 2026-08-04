/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from wp
 */
public class wp_0
implements atG {
    private static wp_0 aux = new wp_0();
    private static apk_0 auy;
    private static final add_1 auz;

    public static wp_0 CH() {
        return aux;
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
            case 23002: {
                wf_2 wf_22 = (wf_2)pr_02;
                boolean bl3 = wf_22.eY();
                wp_0.cE("Changement de statut \u00e0 non pr\u00eat " + (bl3 ? "accept\u00e9" : "rejet\u00e9") + ".");
                if (bl3 && auz.kR("evolutionSearchStatusDialog")) {
                    auz.kO("evolutionSearchStatusDialog");
                    auz.kG("dofusarena.evolutionSearchStatus");
                    apN.aDK().b(wp_0.CH());
                }
                bl2 = false;
                break;
            }
            case 23004: {
                amh_0 amh_02 = (amh_0)pr_02;
                boolean bl4 = amh_02.eY();
                boolean bl5 = auz.kR("evolutionSearchStatusDialog");
                wp_0.cE("Changement de statut \u00e0 pr\u00eat " + (bl4 ? "accept\u00e9" : "rejet\u00e9") + ".");
                apN.aDK().b(hu_2.li());
                apN.aDK().b(vu_1.aip());
                apN.aDK().b(ds_2.LP());
                if (bl4 && !bl5) {
                    auz.a("evolutionSearchStatusDialog", oh_2.bq("evolutionSearchStatusDialog"), (short)10000);
                    auz.l("dofusarena.evolutionSearchStatus", agi_0.class);
                }
                bl2 = false;
                break;
            }
            case 23006: {
                wp_0.cE("Lancement du combat.");
                apN apN2 = apN.aDK();
                if (auz.kR("evolutionSearchStatusDialog")) {
                    auz.kO("evolutionSearchStatusDialog");
                    auz.kG("dofusarena.evolutionSearchStatus");
                    apN.aDK().b(wp_0.CH());
                }
                apN2.a(wg_2.CC());
                apN2.a(do_2.Mm());
                bl2 = false;
                break;
            }
            case 23008: {
                KL kL = (KL)pr_02;
                byte by = kL.an();
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
                if ((by == 3 || by == 4 || by == 5) && auz.kR("evolutionSearchStatusDialog")) {
                    auz.kO("evolutionSearchStatusDialog");
                    auz.kG("dofusarena.evolutionSearchStatus");
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

