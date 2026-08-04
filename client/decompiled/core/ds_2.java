/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ds
 */
public class ds_2
implements atG {
    private static ds_2 aNF = new ds_2();
    private static apk_0 auy;
    private static final add_1 auz;

    public static ds_2 LP() {
        return aNF;
    }

    public static void a(apk_0 apk_02) {
        auy = apk_02;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            // empty if block
        }
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
            case 28610: {
                de_0 de_02 = (de_0)pr_02;
                boolean bl3 = de_02.eY();
                ds_2.cE("Changement de statut \u00e0 non pr\u00eat " + (bl3 ? "accept\u00e9" : "rejet\u00e9") + ".");
                if (bl3 && auz.kR("tournamentsSearchStatusDialog")) {
                    auz.kO("tournamentsSearchStatusDialog");
                    auz.kG("dofusarena.tournamentsSearchStatus");
                    apN.aDK().b(ds_2.LP());
                }
                bl2 = false;
                break;
            }
            case 28612: {
                DR dR = (DR)pr_02;
                boolean bl4 = dR.eY();
                boolean bl5 = auz.kR("tournamentsSearchStatusDialog");
                ds_2.cE("Changement de statut \u00e0 pr\u00eat " + (bl4 ? "accept\u00e9" : "rejet\u00e9") + ".");
                apN.aDK().b(hu_2.li());
                apN.aDK().b(vu_1.aip());
                apN.aDK().b(wp_0.CH());
                if (bl4 && !bl5) {
                    auz.a("tournamentsSearchStatusDialog", oh_2.bq("tournamentsSearchStatusDialog"), (short)10000);
                    auz.l("dofusarena.tournamentsSearchStatus", alj_2.class);
                }
                bl2 = false;
                break;
            }
            case 28614: {
                azj_0 azj_02 = (azj_0)pr_02;
                ds_2.cE("Lancement du combat.");
                apN apN2 = apN.aDK();
                if (auz.kR("tournamentsSearchStatusDialog")) {
                    auz.kO("tournamentsSearchStatusDialog");
                    auz.kG("dofusarena.tournamentsSearchStatus");
                    apN.aDK().b(ds_2.LP());
                }
                if (vk_1.BZ().aQ(azj_02.fx()).BE() == ks_1.bnE.lV()) {
                    for (aan_1 aan_12 : iz_1.Vg().Vh()) {
                        aan_1 aan_13 = null;
                        if (aan_12 instanceof td_0) {
                            if (((td_0)aan_12).fx() == azj_02.fx()) {
                                aan_13 = aan_12;
                            }
                        } else if (aan_12 instanceof apd && ((apd)aan_12).fx() == azj_02.fx()) {
                            aan_13 = aan_12;
                        }
                        if (aan_13 == null) continue;
                        iz_1.Vg().c(aan_13);
                    }
                }
                apN2.a(wg_2.CC());
                apN2.a(do_2.Mm());
                bl2 = false;
                break;
            }
            case 28616: {
                kw_1 kw_12 = (kw_1)pr_02;
                byte by = kw_12.an();
                String string = "";
                if (by == 1) {
                    string = "matchfinder.impossibleToStartOpponentsSearch";
                } else if (by == 3) {
                    string = "matchfinder.canceledByCoach";
                } else if (by == 4) {
                    string = "matchfinder.opponentNotFound";
                }
                if ((by == 3 || by == 4 || by == 5) && auz.kR("tournamentsSearchStatusDialog")) {
                    auz.kO("tournamentsSearchStatusDialog");
                    auz.kG("dofusarena.tournamentsSearchStatus");
                    apN.aDK().b(ds_2.LP());
                }
                if (by == 2) {
                    zN.M(kw_12.pa());
                } else if (by != 5) {
                    auz.f(aon_0.aYc().getString(string), 102, 1);
                }
                bl2 = false;
                break;
            }
            case 28622: {
                uw_2 uw_22 = (uw_2)pr_02;
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

