/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from DX
 */
public class dx_2
implements atG {
    protected static final Logger a = Logger.getLogger(dx_2.class);
    private static dx_2 aPD = new dx_2();

    public static dx_2 MD() {
        return aPD;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 6006: {
                jt_2 jt_22 = (jt_2)pr_02;
                cp_2 cp_22 = jt_22.Wl();
                if (!cp_22.isEmpty()) {
                    long l2 = apN.aDK().Ln().getId();
                    cp_22.a(new awy(this, jt_22, l2));
                    if (bs_0.IF().II() == xz_0.amc()) {
                        azs_0.aLV().a((aho_0)bs_0.IF().II(), xz_0.oT);
                    } else {
                        azs_0.aLV().a((aho_0)bs_0.IF().II(), zK.ce);
                    }
                }
                return false;
            }
            case 6000: {
                aiy_2 aiy_22 = (aiy_2)pr_02;
                switch (aiy_22.an()) {
                    case 0: {
                        JG jG;
                        ee_2 ee_22 = aiy_22.tG();
                        adY.atu().j(ee_22);
                        if (ee_22.NK()) {
                            adY.atu().j(ee_22);
                            add_1.aOG().kO("fighterCreationDialog");
                            adY.atu().a(ee_22.Ol());
                            xz_0.amc().j(ee_22.getId(), apN.aDK().Ln().getId());
                            azs_0.aLV().a((aho_0)xz_0.amc(), xz_0.oT);
                            break;
                        }
                        if (!aiy_22.ayR()) {
                            add_1.aOG().kO("fighterCreationDialog");
                            jG = new ayd_0();
                            ((aed_2)jG).f(16614);
                            ((ayd_0)jG).b(ee_22);
                            acu_1.ara().c((pr_0)jG);
                        } else {
                            if (!add_1.aOG().kR("fighterEquipmentDialog")) {
                                adY.atu().a(ee_22.Ol());
                            }
                            azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.filtredFighterList");
                        }
                        jG = bs_0.IF().at(bs_0.IF().II().tI());
                        zK zK2 = bs_0.IF().II();
                        ((sw_1)jG).j(ee_22.getId(), apN.aDK().Ln().getId());
                        zK2.j(ee_22.getId(), apN.aDK().Ln().getId());
                        azs_0.aLV().a((aho_0)zK2, zK.ce);
                        azs_0.aLV().a((aho_0)((Object)jG), zK.ce);
                        if (aiy_22.ayS() == -1 || bs_0.IF().at(aiy_22.ayS()) == null) break;
                        bs_0.IF().at(aiy_22.ayS()).j(ee_22.getId(), aiy_22.mb());
                        azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset2vs2List");
                        azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.filtredFighterList");
                        break;
                    }
                    default: {
                        zN.M(aiy_22.an());
                    }
                }
                return false;
            }
            case 6002: {
                DQ dQ = (DQ)pr_02;
                byte by = dQ.an();
                if (by == 0) {
                    long l3 = dQ.Mn();
                    ee_2 ee_23 = adY.atu().dz(l3);
                    if (adY.atu().Ol() != null && adY.atu().Ol().getId() == l3) {
                        adY.atu().atx();
                        azs_0.aLV().g("teamManagement.editableFighter", (Object)null);
                        if (add_1.aOG().kR("fighterEquipmentDialog")) {
                            add_1.aOG().kO("fighterEquipmentDialog");
                            azs_0.aLV().g("teamManagement.selectedCard", (Object)null);
                            azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
                        }
                    }
                    adY.atu().k(ee_23);
                    bs_0.IF().bc(l3);
                    ee_23.release();
                    azs_0.aLV().a((aho_0)bs_0.IF(), bs_0.ce);
                    azs_0.aLV().a((aho_0)bs_0.IF().II(), zK.ce);
                    azs_0.aLV().a((aho_0)xz_0.amc(), "fightersOnBench");
                    azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.fighterList");
                    azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.filtredFighterList");
                } else if (by == 24) {
                    long l4 = dQ.Mo();
                    if (l4 != aet_0.dEA) {
                        add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.evolutionFighterDeletion", 4, 7L, rd_1.aF(l4).xo()), 102, 1);
                    } else {
                        add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.fighterDeletion"), 102, 1);
                    }
                }
                return false;
            }
            case 6010: {
                nl_1 nl_12 = (nl_1)pr_02;
                if (nl_12.an() == 0) {
                    long l5 = nl_12.K();
                    ee_2 ee_24 = adY.atu().dz(l5);
                    if (ee_24 != null) {
                        ee_24.Oh().d(nl_12.sK());
                        ee_24.Oi().d(nl_12.sJ());
                        ee_24.PI();
                        azs_0.aLV().a((aho_0)bs_0.IF(), bs_0.ce);
                        azs_0.aLV().a((aho_0)bs_0.IF().II(), zK.ce);
                        azs_0.aLV().a((aho_0)ee_24, "value");
                        if (add_1.aOG().kR("fighterEquipmentDialog") && adY.atu().Ol().getId() == l5) {
                            add_1.aOG().kO("fighterEquipmentDialog");
                        } else if (add_1.aOG().kR("fighterEvolutionEquipmentDialog") && adY.atu().Ol().getId() == l5) {
                            add_1.aOG().kO("fighterEvolutionEquipmentDialog");
                        }
                        azs_0.aLV().g("teamManagement.selectedCard", (Object)null);
                        azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
                    } else {
                        a.error((Object)("Le fighter " + l5 + " est inconnu !"));
                    }
                } else {
                    add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.fighterSave", nl_12.an()), 102, 1);
                }
                return false;
            }
            case 6030: {
                ar_0 ar_02 = (ar_0)pr_02;
                pm_0.ur().done();
                ArrayList arrayList = ar_02.Hh();
                bs_0.IF().IG();
                bs_0.IF().a(arrayList);
                bs_0.IF().a(ar_02.Hj());
                short s = (short)DofusArenaClientInstance.yl().aod().d(adc_0.cma);
                short s2 = (short)DofusArenaClientInstance.yl().aod().d(adc_0.cmb);
                zK zK3 = null;
                zK zK4 = bs_0.IF().at(s);
                if (s2 == 0 || s2 == 4) {
                    zK3 = xz_0.amc();
                } else if (zK4 != null) {
                    short s3 = zK4.getType();
                    switch (s2) {
                        case 1: {
                            if (s3 != -6 && s3 != -21) break;
                            zK3 = zK.a(zK4);
                            break;
                        }
                        case 2: {
                            if (s3 != -7) break;
                            zK3 = zK.a(zK4);
                            break;
                        }
                        case 3: {
                            if (s3 != -5) break;
                            zK3 = zK.a(zK4);
                            break;
                        }
                    }
                }
                bs_0.IF().d(zK3);
                if (zK3 == xz_0.amc()) {
                    azs_0.aLV().a((aho_0)bs_0.IF().II(), xz_0.oT);
                } else {
                    azs_0.aLV().a((aho_0)bs_0.IF().II(), zK.ce);
                }
                return false;
            }
            case 6020: {
                aic_0 aic_02 = (aic_0)pr_02;
                if (aic_02.an() == 0) {
                    zK zK5 = aic_02.GJ();
                    if (zK5 != null) {
                        bs_0.IF().c(zK5);
                        zK zK6 = zK.a(zK5);
                        bs_0.IF().d(zK6);
                        cp_2 cp_23 = bs_0.IF().Hj();
                        if (cp_23 == null) {
                            bs_0.IF().a(aic_02.Hj());
                        } else {
                            for (long l6 : aic_02.Hj().eJ()) {
                                cp_23.a(l6, aic_02.Hj().t(l6));
                            }
                        }
                    }
                } else if (aic_02.an() == 25) {
                    add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.teamNameExist", aic_02.an()), 102, 1);
                } else {
                    add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.teamPresetSave", aic_02.an()), 102, 1);
                }
                return false;
            }
            case 6022: {
                agH agH2 = (agH)pr_02;
                if (agH2.an() == 0) {
                    if (add_1.aOG().kR("fighterEquipmentDialog")) {
                        add_1.aOG().kO("fighterEquipmentDialog");
                        azs_0.aLV().g("teamManagement.selectedCard", (Object)null);
                        azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
                    }
                    bs_0.IF().as(agH2.qY());
                    int n2 = 100;
                    zK zK7 = null;
                    while (zK7 == null) {
                        zK7 = bs_0.IF().at((short)n2);
                        ++n2;
                    }
                    kd_0 kd_02 = new kd_0();
                    kd_02.C(zK7.tI());
                    acu_1.ara().c(kd_02);
                } else {
                    add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.teamPresetDeletion", agH2.an()), 102, 1);
                }
                return false;
            }
            case 6014: {
                aoi aoi2 = (aoi)pr_02;
                if (aoi2.an() == 0) {
                    cn_1 cn_12;
                    zK zK8 = bs_0.IF().at(aoi2.aCE());
                    if (zK8 != null) {
                        zK8.l(aoi2.K());
                    }
                    if (aoi2.K() != aoi2.aCG()) {
                        cn_12 = adY.atu().dz(aoi2.K());
                        adY.atu().k((ee_2)cn_12);
                        ((ee_2)cn_12).c(aoi2.aCG());
                        adY.atu().j((ee_2)cn_12);
                    }
                    if ((cn_12 = bs_0.IF().II()) != null) {
                        boolean bl2 = ((sw_1)cn_12).afE().du(this.getId()) == apN.aDK().Ln().getId();
                        ((sw_1)cn_12).j(aoi2.aCG(), aoi2.mb());
                    }
                    azs_0.aLV().a((aho_0)adY.atu().dz(aoi2.K()), "isLocalCoachFighter");
                    azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset1vs1List");
                    azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset2vs2List");
                    azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.fighterList");
                    azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.filtredFighterList");
                }
                return false;
            }
            case 6029: {
                OJ oJ = (OJ)pr_02;
                add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.coachDisconnected"), 102, 1);
                return false;
            }
            case 6032: {
                gd_0 gd_02 = (gd_0)pr_02;
                apN.aDK().Ln().a(gd_02.kh());
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            if (bs_0.IF().IH().size() <= 1) {
                pm_0.ur().bD(true).m(aon_0.aYc().getString("loading"), 0);
            }
            apN.aDK().vJ().b(new ys_1());
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

