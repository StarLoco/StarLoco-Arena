/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Ce
 */
public class ce_1
implements atG {
    private static ce_1 aKG = new ce_1();

    public static ce_1 IU() {
        return aKG;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            pm_0.ur().bD(true).m(aon_0.aYc().getString("loading"), 0);
            apN.aDK().vJ().b(new ys_1());
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        switch (pr_02.getId()) {
            case 6006: {
                jt_2 jt_22 = (jt_2)pr_02;
                cp_2 cp_22 = jt_22.Wl();
                if (!cp_22.isEmpty()) {
                    long l2 = apN.aDK().Ln().getId();
                    cp_22.a(new xi_0(this, jt_22, l2));
                    azs_0.aLV().a((aho_0)xz_0.amc(), xz_0.oT);
                }
                return false;
            }
            case 6030: {
                ar_0 ar_02 = (ar_0)pr_02;
                pm_0.ur().done();
                ArrayList arrayList = ar_02.Hh();
                xz_0.amc().b((sw_1)arrayList.get(0));
                sj_1 sj_12 = apN.aDK().Ln();
                if (apN.aDK().c(nb_0.aaI())) {
                    if (sj_12.c(avq_0.ce((short)284))) {
                        if (sj_12.qI().contains(or_0.YV.tI())) {
                            add_1.aOG().a("evolutionDialog", oh_2.bq("evolutionDialog"), (short)10000);
                        } else {
                            add_1.aOG().a("evolutionTeamManagementTuto2Dialog", oh_2.bq("evolutionTeamManagementTuto2Dialog"), (short)10000);
                        }
                    } else {
                        add_1.aOG().a("evolutionTeamManagementTutoDialog", oh_2.bq("evolutionTeamManagementTutoDialog"), (short)10000);
                    }
                } else if (en_2.Nb() == aql_0.cOF) {
                    add_1.aOG().a("tournamentEvolutionDialog", oh_2.bq("tournamentEvolutionDialog"), (short)10000);
                } else if (en_2.Nb() == aql_0.cOG) {
                    add_1.aOG().a("tournamentGraveyardDialog", oh_2.bq("tournamentGraveyardDialog"), (short)10000);
                }
                return false;
            }
            case 6000: {
                aiy_2 aiy_22 = (aiy_2)pr_02;
                switch (aiy_22.an()) {
                    case 0: {
                        ee_2 ee_22 = aiy_22.tG();
                        adY.atu().j(ee_22);
                        add_1.aOG().kO("fighterCreationTutorialDialog");
                        add_1.aOG().kO("fighterCreationDialog");
                        adY.atu().a(ee_22.Ol());
                        xz_0.amc().j(ee_22.getId(), apN.aDK().Ln().getId());
                        azs_0.aLV().a((aho_0)xz_0.amc(), xz_0.oT);
                        break;
                    }
                    case 20: {
                        add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.noMoreRoom"), 102, 1);
                        break;
                    }
                    default: {
                        add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.fighterCreation", aiy_22.an()), 102, 1);
                    }
                }
                return false;
            }
            case 6002: {
                DQ dQ = (DQ)pr_02;
                if (dQ.an() == 0) {
                    long l3 = dQ.Mn();
                    ee_2 ee_23 = adY.atu().dz(l3);
                    if (adY.atu().Ol() != null && adY.atu().Ol().getId() == l3) {
                        adY.atu().atx();
                        azs_0.aLV().g("teamManagement.editableFighter", (Object)null);
                        if (add_1.aOG().kR("fighterEquipmentDialog")) {
                            add_1.aOG().kO("fighterEquipmentDialog");
                        }
                    }
                    adY.atu().k(ee_23);
                    xz_0.amc().l(l3);
                    ee_23.release();
                    azs_0.aLV().a((aho_0)xz_0.amc(), xz_0.oT);
                    azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.fighterList");
                    azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.filtredFighterList");
                } else {
                    if (dQ.an() == 24) {
                        add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.fighterDeletion"), 102, 1);
                    }
                    add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.fighterDeletion", dQ.an()), 102, 1);
                }
                return false;
            }
            case 6010: {
                nl_1 nl_12 = (nl_1)pr_02;
                if (nl_12.an() == 0) {
                    long l4 = nl_12.K();
                    ee_2 ee_24 = adY.atu().dz(l4);
                    if (ee_24 != null) {
                        ee_24.Oh().d(nl_12.sK());
                        ee_24.Oi().d(nl_12.sJ());
                        ee_24.PI();
                        azs_0.aLV().a((aho_0)xz_0.amc(), xz_0.oT);
                        azs_0.aLV().a((aho_0)ee_24, "value");
                        if (add_1.aOG().kR("fighterEvolutionEquipmentDialog") && adY.atu().Ol().getId() == l4) {
                            add_1.aOG().kO("fighterEvolutionEquipmentDialog");
                        }
                    }
                } else {
                    add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.fighterSave", nl_12.an()), 102, 1);
                }
                return false;
            }
            case 6032: {
                gd_0 gd_02 = (gd_0)pr_02;
                apN.aDK().Ln().a(gd_02.kh());
                return false;
            }
        }
        return bl2;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

