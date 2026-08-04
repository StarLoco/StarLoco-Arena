/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from ajp
 */
public class ajp_0
implements atG {
    private static ajp_0 cAs = new ajp_0();
    private static apk_0 auy;
    private static final de_2 cAt;
    private static final vk_1 cAu;
    private static final apN cAv;
    private static final oz_1 cAw;
    private static final zb_1 cAx;

    public static ajp_0 azc() {
        return cAs;
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

    private static void a(long l2, aho_0 aho_02, String string) {
        vg vg2 = cAu.aQ(l2);
        if (vg2 != null) {
            vg2.C((byte)3);
            if (vg2.Bu() != 0) {
                td_0 td_02 = new td_0(vg2.BC());
                td_02.ad(vg2.fx());
                iz_1.Vg().b(td_02);
                azs_0.aLV().a((aho_0)iz_1.Vg(), iz_1.ce);
            }
            azs_0.aLV().a(aho_02, string);
        } else {
            ajp_0.cE("Les informations du tournoi d'id " + l2 + " n'existent pas !");
        }
    }

    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        switch (pr_02.getId()) {
            case 17003: {
                awa_0 awa_02 = (awa_0)pr_02;
                cAt.clear();
                ArrayList arrayList = awa_02.Zz();
                for (int j = 0; j < arrayList.size(); ++j) {
                    cAt.a((iz_0)arrayList.get(j));
                }
                apN.aDK().a(cAw);
                bl2 = false;
                break;
            }
            case 28602: {
                ng_2 ng_22 = (ng_2)pr_02;
                wy_1 wy_12 = ng_22.aao();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Liste des tournois, Id - \"Name\" : \n\n");
                for (int j = 0; j < wy_12.getSize(); ++j) {
                    stringBuilder.append("     ").append(wy_12.iy(j)).append(" - \"").append(wy_12.iE(j)).append("\"\n");
                }
                ajp_0.cE(stringBuilder.toString());
                cAu.a(wy_12);
                bl2 = false;
                break;
            }
            case 28604: {
                auE auE2 = (auE)pr_02;
                if (0L < auE2.fx()) {
                    ajp_0.cE("Tournoi d'id " + auE2.fx() + " cr\u00e9\u00e9.");
                } else {
                    ajp_0.cE("Tournoi invalide.");
                }
                bl2 = false;
                break;
            }
            case 28608: {
                dy_0 dy_02 = (dy_0)pr_02;
                byte by = dy_02.an();
                long l2 = dy_02.fx();
                if (by == 0) {
                    vg vg2;
                    if (cAv.c(cAw)) {
                        ajp_0.a(l2, cAu, "tournamentsOfTheDay");
                        azs_0.aLV().ac("selectedTournamentEvent", qr_0.bGg);
                    }
                    if (cAv.c(cAx)) {
                        ajp_0.a(l2, cAt, "calendar");
                    }
                    if ((vg2 = cAu.aQ(l2)) != null) {
                        if (vg2.BE() == ks_1.bnE.lV()) {
                            add_1.aOG().a(aon_0.aYc().getString("multiPyramidalTournamentAddAccepted"), 1090L, 102, 1);
                        } else {
                            add_1.aOG().a(aon_0.aYc().getString("tournamentAddAccepted"), 1090L, 102, 1);
                        }
                    }
                } else if (by == 2) {
                    add_1.aOG().a(aon_0.aYc().getString("tournamentTooMuchParticipants"), 1090L, 102, 1);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("tournamentAddRefused"), 1090L, 102, 1);
                }
                if (by == 0) {
                    ajp_0.cE("Inscription accept\u00e9e.");
                } else {
                    ajp_0.cE("Inscription rejet\u00e9e : ErrorCode \u00e9gal \u00e0 " + by + ".");
                }
                bl2 = false;
                break;
            }
            case 28606: {
                aik aik2 = (aik)pr_02;
                ajp_0.cE("Destruction " + (aik2.eY() ? "accept\u00e9e" : "rejet\u00e9e") + ".");
                bl2 = false;
                break;
            }
            case 28634: {
                acn_2 acn_22 = (acn_2)pr_02;
                ajp_0.cE("Demande de gestion des inscriptions " + (acn_22.eY() ? "accept\u00e9e" : "rejet\u00e9e") + ".");
                bl2 = false;
                break;
            }
            case 28636: {
                aig_1 aig_12 = (aig_1)pr_02;
                ajp_0.cE("Demande de gestion de recherche des opposants " + (aig_12.eY() ? "accept\u00e9e" : "rejet\u00e9e") + ".");
                bl2 = false;
                break;
            }
            case 28618: {
                ahd_0 ahd_02 = (ahd_0)pr_02;
                long l3 = ahd_02.fx();
                vg vg3 = cAu.aQ(l3);
                if (vg3 != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(aon_0.aYc().getString("tournamentYouDidNotFinished")).append(vg3.BC()).append(".\r\n").append(aon_0.aYc().getString("tournamentReportQuestion"));
                    r_0 r_02 = add_1.aOG().a(stringBuilder.toString(), 1176L, 102, 1);
                    r_02.a(new gs_2(this, l3));
                }
                bl2 = false;
                break;
            }
            case 28650: {
                ah_1 ah_12;
                IL iL = (IL)pr_02;
                int n2 = iL.UN();
                lb_0 lb_02 = iL.UO();
                if (lb_02.isEmpty()) {
                    add_1.aOG().f(aon_0.aYc().getString("tournamentTreeNotAvailable"), 102, 1);
                }
                if ((ah_12 = (ah_1)azs_0.aLV().getProperty("duelTree").getValue()) != null) {
                    ah_12.eE(n2);
                    lb_0 lb_03 = ah_12.Hq();
                    lb_03.clear();
                    lb_02.a(new gt_2(this, lb_02, lb_03));
                    azs_0.aLV().a((aho_0)ah_12, ah_1.ce);
                }
                add_1.aOG().a("tournamentTreeDialog", oh_2.bq("tournamentTreeDialog"), 257L, (short)10000);
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
        cAt = de_2.Mc();
        cAu = vk_1.BZ();
        cAv = apN.aDK();
        cAw = oz_1.tJ();
        cAx = zb_1.GG();
    }
}

