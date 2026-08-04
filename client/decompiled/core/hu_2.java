/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from hU
 */
public class hu_2
implements atG {
    protected static final Logger a = Logger.getLogger(hu_2.class);
    private static hu_2 wX = new hu_2();
    private static String wY = aon_0.aYc().getString("defaultFightProfile");

    public static hu_2 li() {
        return wX;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16605: {
                zK zK2 = (zK)azs_0.aLV().getProperty("teamManagement.editableTeamPreset").getValue();
                if (zK2 == null) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.noTeamSelected"), 1091L, 102, 1);
                } else if (adY.atu().amq().size() <= 100) {
                    if (!add_1.aOG().kR("fighterCreationDialog")) {
                        sb_0 sb_02 = (sb_0)pr_02;
                        abv_1 abv_12 = adY.atu().atv();
                        abv_12.b((byte)-1, (byte)1, (byte)0);
                        abv_12.NZ();
                        abv_12.bs(sb_02.getBooleanValue());
                        adY.atu().a(abv_12);
                        add_1.aOG().a("fighterCreationDialog", oh_2.bq("fighterCreationDialog"), 257L, (short)10003);
                    } else {
                        add_1.aOG().kO("fighterCreationDialog");
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.noMoreRoom"), 1090L, 102, 1);
                }
                return false;
            }
            case 16650: {
                byte by;
                ayd_0 ayd_02 = (ayd_0)pr_02;
                abv_1 abv_13 = (abv_1)ayd_02.tG();
                if (abv_13 != null && (by = ayd_02.aj()) != abv_13.lY()) {
                    abv_13.P(by);
                    azs_0.aLV().a((aho_0)abv_13, "actorDescriptorLibrary");
                }
                return false;
            }
            case 16651: {
                byte by;
                ayd_0 ayd_03 = (ayd_0)pr_02;
                abv_1 abv_14 = (abv_1)ayd_03.tG();
                if (abv_14 != null && (by = ayd_03.aj()) != abv_14.lX()) {
                    abv_14.Q(by);
                    azs_0.aLV().a((aho_0)abv_14, "actorDescriptorLibrary");
                }
                return false;
            }
            case 16652: {
                byte by;
                ayd_0 ayd_04 = (ayd_0)pr_02;
                abv_1 abv_15 = (abv_1)ayd_04.tG();
                if (abv_15 != null && (by = ayd_04.aj()) != abv_15.Ns()) {
                    abv_15.R(by);
                    azs_0.aLV().a((aho_0)abv_15, "actorDescriptorLibrary");
                }
                return false;
            }
            case 16609: {
                dv_0 dv_02 = (dv_0)pr_02;
                abv_1 abv_16 = (abv_1)dv_02.tG();
                if (abv_16 != null && abv_16.NY().lV() != dv_02.cu()) {
                    abv_16.S((byte)1);
                    azs_0.aLV().a((aho_0)abv_16, "sex");
                    abv_16.W(dv_02.cu());
                    abv_16.NZ();
                }
                return false;
            }
            case 16610: {
                abb_2 abb_22 = (abb_2)pr_02;
                abv_1 abv_17 = (abv_1)abb_22.tG();
                if (abv_17 != null && abv_17.lZ() != abb_22.lZ()) {
                    abv_17.S(abb_22.lZ());
                    abv_17.NZ();
                }
                return false;
            }
            case 16640: {
                ayd_0 ayd_05 = (ayd_0)pr_02;
                abv_1 abv_18 = (abv_1)ayd_05.tG();
                if (abv_18 != null && abv_18.cc() != ayd_05.aj()) {
                    abv_18.b(ayd_05.aj());
                    abv_18.NZ();
                    azs_0.aLV().a((aho_0)abv_18, "actorDescriptorLibrary");
                }
                return false;
            }
            case 16627: {
                ayd_0 ayd_06 = (ayd_0)pr_02;
                abv_1 abv_19 = (abv_1)ayd_06.tG();
                if (abv_19 != null) {
                    abv_19.aqf();
                }
                return false;
            }
            case 16626: {
                ayd_0 ayd_07 = (ayd_0)pr_02;
                abv_1 abv_110 = (abv_1)ayd_07.tG();
                if (abv_110 != null) {
                    abv_110.aqg();
                }
                return false;
            }
            case 16606: {
                adY.atu().atx();
                add_1.aOG().kO("fighterCreationDialog");
                return false;
            }
            case 16631: {
                sb_0 sb_03 = (sb_0)pr_02;
                azs_0.aLV().g("teamManagement.selectedItemCardList", aca_0.aOq().b(vi_1.ap(sb_03.aj())).toArray());
                azs_0.aLV().g("teamManagement.selectedItemCardListType", sb_03.aj());
                return false;
            }
            case 16614: {
                ayd_0 ayd_08 = (ayd_0)pr_02;
                ee_2 ee_22 = ayd_08.tG();
                if (ee_22 != null) {
                    if (ee_22.isEditable()) {
                        adY.atu().a(ee_22.Ol());
                        azs_0.aLV().g("teamManagement.selectedItemCardList", aca_0.aOq().b(vi_1.bSW).toArray());
                        azs_0.aLV().g("teamManagement.selectedItemCardListType", vi_1.bSW.aiK());
                        add_1.aOG().a("fighterEquipmentDialog", oh_2.bq("fighterEquipmentDialog"), 257L, (short)10000);
                        azs_0.aLV().g("teamManagement.fighterEditionOpen", true);
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamNotEditable"), 1090L, 102, 1);
                    }
                }
                return false;
            }
            case 16615: {
                add_1.aOG().kO("fighterEquipmentDialog");
                azs_0.aLV().g("teamManagement.selectedCard", (Object)null);
                azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
                return false;
            }
            case 16616: {
                ayd_0 ayd_09 = (ayd_0)pr_02;
                ee_2 ee_23 = ayd_09.tG();
                if (ee_23 != null) {
                    bp_1 bp_12 = new bp_1();
                    bp_12.b(ee_23);
                    short s = bs_0.IF().bd(ee_23.getId());
                    if (s != -1) {
                        zK zK3 = bs_0.IF().at(s);
                        if (zK3 != null && zK3.afF().size() > 1) {
                            bp_12.e(zK3.tI());
                        } else {
                            bp_12.e((short)-1);
                        }
                    } else {
                        bp_12.e((short)-1);
                    }
                    apN.aDK().vJ().b(bp_12);
                    adY.atu().dz(ee_23.getId()).Ob();
                } else {
                    a.error((Object)"on tente de sauvegarder un fighter null");
                }
                azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
                return false;
            }
            case 16618: {
                da_1 da_12 = (da_1)pr_02;
                abv_1 abv_111 = (abv_1)da_12.tG();
                yp_2 yp_22 = da_12.fw();
                if (abv_111 != null && yp_22 != null) {
                    abv_111.c(yp_22);
                }
                return false;
            }
            case 16619: {
                da_1 da_13 = (da_1)pr_02;
                abv_1 abv_112 = (abv_1)da_13.tG();
                yp_2 yp_23 = da_13.fw();
                if (abv_112 != null && yp_23 != null) {
                    abv_112.d(yp_23);
                }
                return false;
            }
            case 16624: {
                da_1 da_14 = (da_1)pr_02;
                yp_2 yp_24 = da_14.fw();
                if (yp_24 != null) {
                    azs_0.aLV().g("teamManagement.selectedCard", yp_24);
                }
                return false;
            }
            case 16620: {
                pd_2 pd_22 = (pd_2)pr_02;
                abv_1 abv_113 = (abv_1)pd_22.tG();
                ve_0 ve_02 = pd_22.abQ();
                if (abv_113 != null && ve_02 != null) {
                    short s = pd_22.ha();
                    if (s == -1) {
                        s = ve_02.Vk().aiJ();
                    }
                    abv_113.a(ve_02, s);
                }
                return false;
            }
            case 16621: {
                pd_2 pd_23 = (pd_2)pr_02;
                abv_1 abv_114 = (abv_1)pd_23.tG();
                ve_0 ve_03 = pd_23.abQ();
                if (abv_114 != null && ve_03 != null) {
                    abv_114.e(ve_03);
                }
                return false;
            }
            case 16622: {
                pd_2 pd_24 = (pd_2)pr_02;
                ve_0 ve_04 = pd_24.abQ();
                if (ve_04 != null) {
                    azs_0.aLV().g("teamManagement.selectedCard", ve_04);
                }
                return false;
            }
            case 16629: {
                ayd_0 ayd_010 = (ayd_0)pr_02;
                ee_2 ee_24 = ayd_010.tG();
                if (ee_24 != null) {
                    azs_0.aLV().g("teamManagement.selectedCard", ee_24);
                }
                return false;
            }
            case 16630: {
                azs_0.aLV().g("teamManagement.selectedCard", (Object)null);
                return false;
            }
            case 16611: {
                abv_1 abv_115 = adY.atu().Ol();
                if (abv_115 != null) {
                    if (abv_115.NK() && abv_115.NY() == xq.axR || abv_115.NY() == xq.axS) {
                        add_1.aOG().a(aon_0.aYc().getString("errorFighterVersionNotAvailable"), 1090L, 102, 1);
                    } else {
                        aNb aNb2 = new aNb();
                        et_2 et_22 = abv_115.Om();
                        if (abv_115.NK()) {
                            aNb2.e((short)99);
                            et_22.setType((byte)2);
                        } else {
                            zK zK4 = (zK)azs_0.aLV().getProperty("teamManagement.editableTeamPreset").getValue();
                            if (zK4 != null) {
                                aNb2.e(zK4.tI());
                                et_22.setType((byte)1);
                            } else {
                                add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.noTeamSelected"), 1091L, 102, 1);
                            }
                        }
                        aNb2.h(et_22);
                        aNb2.fn(false);
                        apN.aDK().vJ().b(aNb2);
                    }
                }
                return false;
            }
            case 16612: {
                ayd_0 ayd_011 = (ayd_0)pr_02;
                ee_2 ee_25 = ayd_011.tG();
                if (ee_25 != null) {
                    if (ee_25.isEditable()) {
                        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("question.removeFighter", ee_25.getName()), 1177L, 102, 1);
                        r_02.a(new aok_2(this, ee_25));
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamNotEditable"), 1090L, 102, 1);
                    }
                }
                return false;
            }
            case 16617: {
                kd_0 kd_02 = (kd_0)pr_02;
                zK zK5 = bs_0.IF().at(kd_02.qY());
                zK zK6 = bs_0.IF().II();
                if (zK5 != null && (zK6 == null || kd_02.qY() != zK6.tI())) {
                    bs_0.IF().d(zK.a(zK5));
                    azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset1vs1List");
                    azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset2vs2List");
                }
                return false;
            }
            case 16602: {
                add_1.aOG().a("teamNameDialog", oh_2.bq("teamNameDialog"), 257L, (short)20000);
                return false;
            }
            case 16613: {
                add_1.aOG().a("team2vs2NameDialog", oh_2.bq("team2vs2NameDialog"), 257L, (short)20000);
                return false;
            }
            case 16661: {
                add_1.aOG().a("newTeamTournamentDialog", oh_2.bq("newTeamTournamentDialog"), 257L, (short)20000);
                return false;
            }
            case 16632: {
                Object object;
                xw_2 xw_22 = (xw_2)pr_02;
                Object[] objectArray = bs_0.IF().IH().getValues();
                int n2 = objectArray.length;
                for (int j = 0; j < n2; ++j) {
                    object = (sw_1)objectArray[j];
                    if (!xw_22.hX().trim().equals(((sw_1)object).getName().trim())) continue;
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamNameExist"), 1091L, 102, 1);
                    return false;
                }
                if (!xw_22.hX().equals("")) {
                    zK zK7 = new zK();
                    zK7.setName(xw_22.hX());
                    zK7.cG(apN.aDK().Ln().getId());
                    zK7.M((short)1);
                    zK7.setType((short)-6);
                    asV asV2 = (asV)azs_0.aLV().getProperty("selectedTeamIcon").getValue();
                    object = (asV)azs_0.aLV().getProperty("selectedTeamBackground").getValue();
                    zK7.ah(asV2.lV());
                    zK7.ai(asV2.aFS());
                    zK7.aj(((asV)object).lV());
                    zK7.ak(((asV)object).aFS());
                    bs_0.IF().d(zK7);
                    add_1.aOG().kO("teamNameDialog");
                    aqH aqH2 = new aqH();
                    aqH2.c(bs_0.IF().II());
                    apN.aDK().vJ().b(aqH2);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamNameEmpty"), 1091L, 102, 1);
                }
                return false;
            }
            case 16660: {
                xw_2 xw_23 = (xw_2)pr_02;
                Object[] objectArray = bs_0.IF().IH().getValues();
                int n3 = objectArray.length;
                for (int j = 0; j < n3; ++j) {
                    sw_1 sw_12 = (sw_1)objectArray[j];
                    if (!xw_23.hX().trim().equals(sw_12.getName().trim())) continue;
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamNameExist"), 1091L, 102, 1);
                    return false;
                }
                if (!xw_23.hX().equals("")) {
                    zK zK8 = new zK();
                    zK8.setName(xw_23.hX());
                    zK8.cG(apN.aDK().Ln().getId());
                    zK8.M((short)1);
                    zK8.setType((short)-5);
                    bs_0.IF().d(zK8);
                    add_1.aOG().kO("newTeamTournamentDialog");
                    aqH aqH3 = new aqH();
                    aqH3.c(bs_0.IF().II());
                    apN.aDK().vJ().b(aqH3);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamNameEmpty"), 1091L, 102, 1);
                }
                return false;
            }
            case 16633: {
                if (add_1.aOG().kR("teamNameDialog")) {
                    azs_0.aLV().kb("selectedTeamIcon");
                    azs_0.aLV().kb("selectedTeamBackground");
                    add_1.aOG().kO("teamNameDialog");
                } else {
                    azs_0.aLV().g("selectedTeamIcon", new asV(0, 0, 0));
                    azs_0.aLV().g("selectedTeamBackground", new asV(1, 0, 0));
                    add_1.aOG().a("teamNameDialog", oh_2.bq("teamNameDialog"), 257L, (short)20000);
                }
                return false;
            }
            case 16662: {
                if (add_1.aOG().kR("newTeamTournamentDialog")) {
                    azs_0.aLV().kb("selectedTeamIcon");
                    azs_0.aLV().kb("selectedTeamBackground");
                    add_1.aOG().kO("newTeamTournamentDialog");
                } else {
                    azs_0.aLV().g("selectedTeamIcon", new asV(0, 0, 0));
                    azs_0.aLV().g("selectedTeamBackground", new asV(1, 0, 0));
                    add_1.aOG().a("newTeamTournamentDialog", oh_2.bq("newTeamTournamentDialog"), 257L, (short)20000);
                }
                return false;
            }
            case 16634: {
                if (add_1.aOG().kR("team2vs2NameDialog")) {
                    azs_0.aLV().kb("selectedTeamIcon");
                    azs_0.aLV().kb("selectedTeamBackground");
                    add_1.aOG().kO("team2vs2NameDialog");
                } else {
                    azs_0.aLV().g("selectedTeamIcon", new asV(0, 0, 0));
                    azs_0.aLV().g("selectedTeamBackground", new asV(1, 0, 0));
                    add_1.aOG().a("team2vs2NameDialog", oh_2.bq("team2vs2NameDialog"), 257L, (short)20000);
                }
                return false;
            }
            case 16635: {
                awC awC2 = add_1.aOG().aOT();
                awC2.a(aon_0.aYc().getString("chat.friendList"), (akq_1)null);
                HashMap hashMap = mc_1.qM().qN();
                for (axa_0 axa_02 : hashMap.values()) {
                    awC2.a(axa_02.getName(), null, new aol_2(this, axa_02), true);
                }
                add_1.aOG().e(awC2);
                return false;
            }
            case 16636: {
                ev_0 ev_02 = (ev_0)pr_02;
                ir_0 ir_02 = new ir_0();
                afl_0 afl_02 = azs_0.aLV().getProperty("teamManagement.teammateName");
                axa_0 axa_03 = (axa_0)afl_02.getValue();
                ir_02.U(axa_03.getId());
                ir_02.T(apN.aDK().Ln().getId());
                ir_02.setName(ev_02.hX());
                apN.aDK().vJ().b(ir_02);
                add_1.aOG().kO("team2vs2NameDialog");
                return false;
            }
            case 16604: {
                Db db = (Db)pr_02;
                zK zK9 = db.GJ();
                if (zK9 != null && zK9.isEditable()) {
                    r_0 r_03 = add_1.aOG().a(aon_0.aYc().getString("question.removeTeamPreset", zK9.getName()), 1177L, 102, 1);
                    r_03.a(new aos_2(this, zK9));
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamNotEditable"), 1090L, 102, 1);
                }
                return false;
            }
            case 24000: {
                Object object;
                Co co = (Co)pr_02;
                zK zK10 = co.GJ();
                if (zK10 == null) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                if (zK10.isEmpty()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                if (zK10.afr() && ((xz_0)zK10).ame().size() == 0) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                int n4 = zK10.getValue();
                if (n4 > 6000) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.budgetExploded", n4, 6000), 1090L, 102, 1);
                }
                if (zK10.afr()) {
                    object = new int[]{0};
                    zK10.afE().a(new aot_2(this, (int[])object));
                    if (6 < object[0]) {
                        add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.fightersCountExploded"), 1090L, 102, 1);
                        return false;
                    }
                } else if (6 < zK10.afE().size()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.fightersCountExploded"), 1090L, 102, 1);
                    return false;
                }
                apN.aDK().a(do_2.Mm());
                apN.aDK().a(wg_2.CC());
                apN.aDK().b(this);
                apN.aDK().b(vu_1.aip());
                apN.aDK().b(wp_0.CH());
                apN.aDK().b(ds_2.LP());
                object = new alv_1();
                object.fH(12);
                object.bM(zK10.tI());
                apN.aDK().vJ().b((pr_0)object);
                return false;
            }
            case 24001: {
                xz_0 xz_02 = xz_0.amc();
                if (xz_02.amg().size() == 0) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                int n5 = xz_02.amj();
                if (n5 > 6000) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.budgetExploded", n5, 6000), 1090L, 102, 1);
                }
                if (xz_02.amg().size() > 6) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.fightersCountExploded"), 1090L, 102, 1);
                    return false;
                }
                apN.aDK().a(do_2.Mm());
                apN.aDK().a(wg_2.CC());
                apN.aDK().b(this);
                alv_1 alv_12 = new alv_1();
                alv_12.fH(12);
                alv_12.bM((short)9999);
                apN.aDK().vJ().b(alv_12);
                return false;
            }
            case 16601: {
                agY agY2 = (agY)pr_02;
                apN.aDK().b(this);
                return false;
            }
            case 23111: {
                zi_0 zi_02 = (zi_0)pr_02;
                zK zK11 = zi_02.GJ();
                if (zK11 == sw_1.bMq) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                } else if (zK11.isEmpty()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                } else if (!zK11.afH()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmptyForACoach"), 1090L, 102, 1);
                } else {
                    int n6 = zK11.getValue();
                    if (6000 < n6) {
                        add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.budgetExploded", n6, 6000), 1090L, 102, 1);
                    } else if (6 < zK11.afE().size()) {
                        add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.fightersCountExploded"), 1090L, 102, 1);
                    } else if (!wY.equals(aon_0.aYc().getString("defaultFightProfile"))) {
                        r_0 r_04 = add_1.aOG().a(aon_0.aYc().getString("warningDifferentProfileSelected"), 24L, 102, 0);
                        r_04.a(new aoq_1(this, zK11));
                    } else {
                        this.a(zK11);
                    }
                }
                return false;
            }
            case 16637: {
                sb_0 sb_04 = (sb_0)pr_02;
                azs_0.aLV().g("teamManagement.help", aon_0.aYc().getString("help." + sb_04.getStringValue()));
                azs_0.aLV().g("teamManagement.helpIcon", sb_04.getStringValue() + "Icon");
                return false;
            }
            case 23051: {
                if (hu_2.lj()) {
                    Object object;
                    sj_1 sj_12 = apN.aDK().Ln();
                    en_1 en_12 = sj_12.aQn().pH();
                    String string = "";
                    for (wy_2 wy_22 : en_12) {
                        if (((xj)wy_22.NR()).tr() >= sj_12.getLevel() || sj_12.aQm().contains(Math.abs(wy_22.jf()))) continue;
                        string = string + wy_22.getName() + ", ";
                    }
                    if (!string.equals("")) {
                        object = add_1.aOG().a(aon_0.aYc().getString("warningEquipmentWillBeAddedToTome", string), 24L, 102, 0);
                        ((r_0)object).a(new aor_2(this));
                    } else {
                        apN.aDK().a(do_2.Mm());
                        object = new ajw_0();
                        ((ajw_0)object).aj(apN.aDK().Ln().getId());
                        ((ajw_0)object).C((short)99);
                        apN.aDK().vJ().b((pr_0)object);
                        apN.aDK().b(nb_0.aaI());
                    }
                }
                return false;
            }
            case 23052: {
                sb_0 sb_05 = (sb_0)pr_02;
                wy_2 wy_23 = (wy_2)apN.aDK().Ln().aQn().bW(sb_05.getIntValue());
                apN.aDK().Ln().a(wy_23, wy_23.tj().aXg()[0]);
                return false;
            }
            case 23053: {
                sb_0 sb_06 = (sb_0)pr_02;
                sj_1 sj_13 = apN.aDK().Ln();
                sj_13.d((wy_2)sj_13.aQn().z(sb_06.ak()));
                return false;
            }
            case 16638: {
                sb_0 sb_07 = (sb_0)pr_02;
                azs_0.aLV().g("teamManagement.selectedItemCardListType", sb_07.aj());
                azs_0.aLV().a((aho_0)adY.atu().Ol(), "availableFighterCards");
                return false;
            }
            case 23055: {
                ayd_0 ayd_012 = (ayd_0)pr_02;
                ee_2 ee_26 = ayd_012.tG();
                if (ee_26 != null) {
                    int n7;
                    long[] lArray = xz_0.amc().afE().eJ();
                    int n8 = 0;
                    if (ee_26.NB() == 1) {
                        for (n7 = 0; n7 < lArray.length; ++n7) {
                            ee_2 ee_27 = adY.atu().dz(lArray[n7]);
                            if (ee_27 == null || ee_27.NB() != 0 && ee_27.NB() != 2) continue;
                            n8 = (short)(n8 + 1);
                        }
                        if (n8 >= 6) {
                            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.fightersCountExploded"), 1090L, 102, 1);
                            return false;
                        }
                        ee_26.V((byte)0);
                        azs_0.aLV().a((aho_0)xz_0.amc(), "fighters");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "fightersOnBench");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "value");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "teamLeague");
                    } else if (ee_26.NB() == 0) {
                        for (n7 = 0; n7 < lArray.length; ++n7) {
                            ee_2 ee_28 = adY.atu().dz(lArray[n7]);
                            if (ee_28 == null || ee_28.NB() != 1) continue;
                            n8 = (short)(n8 + 1);
                        }
                        if (n8 >= 7) {
                            add_1.aOG().a(aon_0.aYc().getString("error.evolution.tooManyFightersOnBench"), 1090L, 102, 1);
                            return false;
                        }
                        ee_26.V((byte)1);
                        azs_0.aLV().a((aho_0)xz_0.amc(), "fighters");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "fightersOnBench");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "value");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "teamLeague");
                    } else if (ee_26.NB() == 2) {
                        for (n7 = 0; n7 < lArray.length; ++n7) {
                            ee_2 ee_29 = adY.atu().dz(lArray[n7]);
                            if (ee_29 == null || ee_29.NB() != 3) continue;
                            n8 = (short)(n8 + 1);
                        }
                        if (n8 >= 5) {
                            add_1.aOG().a(aon_0.aYc().getString("error.evolution.graveyardFull"), 1090L, 102, 1);
                            return false;
                        }
                        ee_26.V((byte)3);
                        azs_0.aLV().a((aho_0)xz_0.amc(), "fighters");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "fightersOnBench");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "value");
                    } else if (ee_26.NB() == 4) {
                        for (n7 = 0; n7 < lArray.length; ++n7) {
                            ee_2 ee_210 = adY.atu().dz(lArray[n7]);
                            if (ee_210 == null || ee_210.NB() != 5) continue;
                            n8 = (short)(n8 + 1);
                        }
                        if (n8 >= 9) {
                            add_1.aOG().a(aon_0.aYc().getString("error.evolution.tooManyFightersOnBench"), 1090L, 102, 1);
                            return false;
                        }
                        ee_26.V((byte)5);
                        azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryFighters");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryFightersOnBench");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryValue");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "teamLeague");
                    } else if (ee_26.NB() == 5) {
                        for (n7 = 0; n7 < lArray.length; ++n7) {
                            ee_2 ee_211 = adY.atu().dz(lArray[n7]);
                            if (ee_211 == null || ee_211.NB() != 4) continue;
                            n8 = (short)(n8 + 1);
                        }
                        if (n8 >= 6) {
                            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.fightersCountExploded"), 1090L, 102, 1);
                            return false;
                        }
                        ee_26.V((byte)4);
                        azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryFighters");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryFightersOnBench");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryValue");
                        azs_0.aLV().a((aho_0)xz_0.amc(), "teamLeague");
                    }
                    Jc jc = new Jc();
                    jc.j(ee_26.getId());
                    apN.aDK().vJ().b(jc);
                }
                return false;
            }
            case 23068: {
                ayd_0 ayd_013 = (ayd_0)pr_02;
                ee_2 ee_212 = ayd_013.tG();
                if (ee_212 != null) {
                    long[] lArray = xz_0.amc().afE().eJ();
                    int n9 = 0;
                    int n10 = 0;
                    for (int j = 0; j < lArray.length; ++j) {
                        ee_2 ee_213 = adY.atu().dz(lArray[j]);
                        if (ee_213 != null && ee_213.NB() == 4) {
                            n9 = (short)(n9 + 1);
                            continue;
                        }
                        if (ee_213 == null || ee_213.NB() != 5) continue;
                        n10 = (short)(n10 + 1);
                    }
                    if (n9 >= 6) {
                        if (n10 >= 9) {
                            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.fightersCountExploded"), 1090L, 102, 1);
                            return false;
                        }
                        ee_212.V((byte)5);
                    } else {
                        ee_212.V((byte)4);
                    }
                    azs_0.aLV().a((aho_0)xz_0.amc(), "teamLeague");
                    azs_0.aLV().a((aho_0)xz_0.amc(), "fighters");
                    azs_0.aLV().a((aho_0)xz_0.amc(), "fightersOnBench");
                    azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryFighters");
                    azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryFightersOnBench");
                    azs_0.aLV().a((aho_0)xz_0.amc(), "legendaryValue");
                    azs_0.aLV().a((aho_0)xz_0.amc(), "value");
                    Jc jc = new Jc();
                    jc.bI(true);
                    jc.j(ee_212.getId());
                    apN.aDK().vJ().b(jc);
                }
                return false;
            }
            case 23056: {
                avP avP2 = (avP)pr_02;
                mb_0.Yl().hide();
                ee_2 ee_214 = avP2.tG();
                wy_2 wy_24 = avP2.hF();
                if (ee_214 != null && wy_24 != null) {
                    this.a(wy_24, ee_214);
                }
                return false;
            }
            case 23060: {
                ayd_0 ayd_014 = (ayd_0)pr_02;
                if (apN.aDK().c(afb_1.auN())) {
                    apN.aDK().b(afb_1.auN());
                    return false;
                }
                ee_2 ee_215 = ayd_014.tG();
                int n11 = ee_215.NH();
                Ei ei = (Ei)akp_1.aVO().aW(n11);
                if (ei == null) {
                    a.error((Object)("Il n'existe pas de board d'id " + n11));
                    return false;
                }
                ei.fi(ee_215.NC() - 1);
                ei.fj(ee_215.ND() - 1);
                azs_0.aLV().g("sphereboard.fighter", ee_215);
                afb_1.auN().j(ee_215.getId());
                afb_1.auN().setSphereBoard(ei);
                apN.aDK().a(afb_1.auN());
                return false;
            }
            case 23062: {
                ayd_0 ayd_015 = (ayd_0)pr_02;
                ee_2 ee_216 = ayd_015.tG();
                if (ee_216 != null) {
                    adY.atu().a(ee_216.Ol());
                    azs_0.aLV().g("teamManagement.selectedItemCardList", aca_0.aOq().b(vi_1.bSW).toArray());
                    azs_0.aLV().g("teamManagement.selectedItemCardListType", vi_1.bSW.aiK());
                    add_1.aOG().a("fighterEvolutionEquipmentDialog", oh_2.bq("fighterEvolutionEquipmentDialog"), 257L, (short)10000);
                    azs_0.aLV().g("teamManagement.fighterEditionOpen", true);
                }
                return false;
            }
            case 23063: {
                add_1.aOG().kO("fighterEvolutionEquipmentDialog");
                azs_0.aLV().g("teamManagement.selectedCard", (Object)null);
                azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
                return false;
            }
            case 20125: {
                if (!add_1.aOG().kR("teamLoadDialog")) {
                    add_1.aOG().a("teamLoadDialog", oh_2.bq("teamLoadDialog"), 256L, (short)10000);
                } else {
                    add_1.aOG().kO("teamLoadDialog");
                }
                return false;
            }
            case 20126: {
                sb_0 sb_08 = (sb_0)pr_02;
                zK zK12 = bs_0.IF().II();
                if (zK12.getType() != -21) {
                    mk_2 mk_22 = new mk_2(sb_08.getStringValue());
                    if (br.a(mk_22)) {
                        r_0 r_05 = add_1.aOG().a(aon_0.aYc().getString("question.removeAllFighter", sb_08.getStringValue()), 1177L, 102, 1);
                        r_05.a(new aOW(this, zK12, mk_22));
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("errorLoadingFile"), 1091L, 102, 1);
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("cannotLoadOnTeamPreco"), 1091L, 102, 1);
                }
                return false;
            }
            case 20127: {
                abt_0 abt_02 = (abt_0)pr_02;
                zK zK13 = abt_02.GJ();
                mk_2 mk_23 = new mk_2(zK13.getName() + "." + "atd");
                long[] lArray = zK13.afE().eJ();
                if (lArray.length > 0) {
                    et_2[] et_2Array = new et_2[lArray.length];
                    for (int j = 0; j < lArray.length; ++j) {
                        et_2Array[j] = adY.atu().dz(lArray[j]).Om();
                    }
                    mk_23.a(et_2Array);
                    if (!br.b(mk_23)) {
                        add_1.aOG().a(aon_0.aYc().getString("errorSavingFile"), 1091L, 102, 1);
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("fileSaved"), 1091L, 102, 1);
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("noFightersToSave"), 1091L, 102, 1);
                }
                return false;
            }
            case 23101: {
                amo_0 amo_02 = (amo_0)pr_02;
                zK zK14 = amo_02.GJ();
                if (zK14 == sw_1.bMq) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                } else if (zK14.isEmpty()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                } else if (!zK14.afH()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmptyForACoach"), 1090L, 102, 1);
                } else if (vk_1.fx() == 0L) {
                    add_1.aOG().a(aon_0.aYc().getString("error.noTournamentSelected"), 1090L, 102, 1);
                } else {
                    long l2;
                    int n12 = zK14.tI();
                    zK zK15 = bs_0.IF().at((short)n12);
                    if (zK15 == sw_1.bMq) {
                        a.error((Object)("Impossible d'acc\u00e9der \u00e0 l'\u00e9quipe d'id " + n12 + " : TeamPreset \u00e9gal  \u00e0 " + sw_1.bMq + "."));
                        l2 = 0L;
                        n12 = -1;
                    } else {
                        l2 = zK15.afG();
                        if (l2 == -1L) {
                            sj_1 sj_14 = apN.aDK().Ln();
                            if (sj_14 == null) {
                                a.error((Object)"Impossible d'acc\u00e9der au coach local !");
                                l2 = 0L;
                                n12 = -1;
                            } else {
                                l2 = apN.aDK().Ln().getId();
                            }
                        }
                    }
                    vk_1.aj(l2);
                    vk_1.C((short)n12);
                    apN.aDK().a(do_2.Mm());
                    ly_1 ly_12 = new ly_1();
                    ly_12.ad(vk_1.fx());
                    ly_12.aj(vk_1.qX());
                    ly_12.C(vk_1.qY());
                    apN.aDK().vJ().b(ly_12);
                    apN.aDK().b(this);
                }
                return false;
            }
            case 23201: {
                xz_0 xz_03 = xz_0.amc();
                if (xz_03.amg().isEmpty()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                } else {
                    short s = 9999;
                    long l3 = apN.aDK().Ln().getId();
                    vk_1.aj(l3);
                    vk_1.C(s);
                    apN.aDK().a(do_2.Mm());
                    ly_1 ly_13 = new ly_1();
                    ly_13.ad(vk_1.fx());
                    ly_13.aj(vk_1.qX());
                    ly_13.C(vk_1.qY());
                    apN.aDK().vJ().b(ly_13);
                    apN.aDK().b(this);
                }
                return false;
            }
            case 20130: {
                if (add_1.aOG().kR("unlockedColorsDialog")) {
                    add_1.aOG().kO("unlockedColorsDialog");
                } else {
                    sb_0 sb_09 = (sb_0)pr_02;
                    apN.aDK().Ln().A(sb_09.aj());
                    add_1.aOG().a("unlockedColorsDialog", oh_2.bq("unlockedColorsDialog"), 256L, (short)10000);
                }
                return false;
            }
            case 16700: {
                ia_2 ia_22 = (ia_2)pr_02;
                wy_2 wy_25 = ia_22.lm();
                if (wy_25 != null) {
                    azs_0.aLV().g("coachManagement.selectedCard", wy_25);
                }
                return false;
            }
            case 16701: {
                azs_0.aLV().g("coachManagement.selectedCard", (Object)null);
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
            po_0.abV().abW();
            azs_0.aLV().g("fight.budget", 6000);
            ArrayList arrayList = (ArrayList)iz_1.Vg().getFieldValue("tournamentsList");
            if (arrayList.size() > 0) {
                afl_0 afl_02 = azs_0.aLV().getProperty("onlyTabEnabledId");
                if (afl_02 != null && afl_02.getValue() != null && (Byte)afl_02.getValue() != 3) {
                    azs_0.aLV().g("selectedTournamentClientInfos", arrayList.get(0));
                    vk_1.ad(((td_0)arrayList.get(0)).fx());
                }
            } else {
                azs_0.aLV().g("selectedTournamentClientInfos", (Object)null);
            }
            apN.aDK().a(dx_2.MD());
            apN.aDK().a(vu_1.aip());
            apN.aDK().a(wp_0.CH());
            apN.aDK().a(ds_2.LP());
            add_1.aOG().l("dofusarena.teamManagement", acx_2.class);
            add_1.aOG().l("dofusarena.fightCreation", pi_0.class);
            add_1.aOG().l("dofusarena.evolution", aio_1.class);
            hc_2.kI().k("world", false);
            aij_0.aUF().c(null);
            apN.aDK().Ln().yH();
            this.X();
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            azs_0.aLV().kb("fight.budget");
            azs_0.aLV().g("teamManagement.editableFighter", (Object)null);
            azs_0.aLV().g("onlyTabEnabledId", (byte)-1);
            adY.atu().atx();
            apN.aDK().b(dx_2.MD());
            add_1.aOG().kG("dofusarena.teamManagement");
            add_1.aOG().kG("dofusarena.fightCreation");
            add_1.aOG().kG("dofusarena.evolution");
            hc_2.kI().k("world", apN.aDK().aDL() == null);
            jk_1.mf().mc();
            hu_2.ay(aon_0.aYc().getString("defaultFightProfile"));
            apN.aDK().Ln().yI();
            this.W();
        }
    }

    protected void X() {
        azs_0.aLV().g("teamManagementOpen", true);
        azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
    }

    protected void W() {
        adY.atu().atx();
        add_1.aOG().kO("fighterCreationDialog");
        add_1.aOG().kO("fighterEquipmentDialog");
        add_1.aOG().kO("teamNameDialog");
        add_1.aOG().kO("fighterCreationDialog");
        add_1.aOG().kO("teamManagementFighterListDialog");
        add_1.aOG().kO("teamManagementSelectedFighterDialog");
        add_1.aOG().aOU();
        azs_0.aLV().g("teamManagementOpen", false);
        azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
        pm_0.ur().done();
    }

    protected void a(zK zK2) {
        long l2;
        int n2 = zK2.tI();
        zK zK3 = bs_0.IF().at((short)n2);
        if (zK3 == sw_1.bMq) {
            a.error((Object)("Impossible d'acc\u00e9der \u00e0 l'\u00e9quipe d'id " + n2 + " : TeamPreset \u00e9gal  \u00e0 " + sw_1.bMq + "."));
            l2 = 0L;
            n2 = -1;
        } else {
            l2 = zK3.afG();
            if (l2 == -1L) {
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 == null) {
                    a.error((Object)"Impossible d'acc\u00e9der au coach local !");
                    l2 = 0L;
                    n2 = -1;
                } else {
                    l2 = apN.aDK().Ln().getId();
                }
            }
        }
        mh_1.aj(l2);
        mh_1.C((short)n2);
        apN.aDK().a(do_2.Mm());
        atj_0 atj_02 = new atj_0();
        atj_02.aj(mh_1.qX());
        atj_02.C(mh_1.qY());
        apN.aDK().vJ().b(atj_02);
        apN.aDK().b(hu_2.li());
        if (zK2.cB() == 2) {
            r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("opponentSearchConfirmation.waitingReplyForFight"), 1156L, 102, 1);
            r_02.a(new aox_0(this, zK2));
            avn_0.d(r_02);
        }
    }

    public static void ay(String string) {
        wY = string;
        zK zK2 = bs_0.IF().II();
        azs_0.aLV().a((aho_0)zK2, zK.ce);
    }

    public static boolean lj() {
        xz_0 xz_02 = xz_0.amc();
        zy_0 zy_02 = new zy_0();
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = true;
        long[] lArray = xz_02.afE().eJ();
        for (int j = 0; j < lArray.length; ++j) {
            ee_2 ee_22 = adY.atu().dz(lArray[j]);
            if (ee_22 != null && ee_22.NB() == 2) {
                bl3 = true;
                break;
            }
            if (ee_22 == null || ee_22.NB() != 0) continue;
            bl4 = false;
            if (!zy_02.K(ee_22.NY().lV())) {
                zy_02.e(ee_22.NY().lV(), (byte)1);
                continue;
            }
            byte by = zy_02.H(ee_22.NY().lV());
            if ((by = (byte)(by + 1)) == 3) {
                bl2 = true;
                break;
            }
            zy_02.e(ee_22.NY().lV(), by);
        }
        if (!bl3) {
            return hu_2.a(bl2, bl4, xz_02);
        }
        add_1.aOG().f(aon_0.aYc().getString("error.evolution.deadFighterInTeam"), 102, 1);
        return false;
    }

    public static boolean lk() {
        xz_0 xz_02 = xz_0.amc();
        zy_0 zy_02 = new zy_0();
        boolean bl2 = false;
        boolean bl3 = true;
        long[] lArray = xz_02.afE().eJ();
        for (int j = 0; j < lArray.length; ++j) {
            ee_2 ee_22 = adY.atu().dz(lArray[j]);
            if (ee_22 == null || ee_22.NB() != 3) continue;
            bl3 = false;
            if (!zy_02.K(ee_22.NY().lV())) {
                zy_02.e(ee_22.NY().lV(), (byte)1);
                continue;
            }
            byte by = zy_02.H(ee_22.NY().lV());
            if ((by = (byte)(by + 1)) == 3) {
                bl2 = true;
                break;
            }
            zy_02.e(ee_22.NY().lV(), by);
        }
        return hu_2.a(bl2, bl3, xz_02);
    }

    private static boolean a(boolean bl2, boolean bl3, xz_0 xz_02) {
        if (bl2) {
            add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.sameBreedFightersCountExploded"), 102, 1);
        } else if (xz_02 == sw_1.bMq) {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
        } else if (bl3) {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
        } else if (!xz_02.afH()) {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmptyForACoach"), 1090L, 102, 1);
        } else if (xz_02.afr() && xz_02.getValue() < 5000) {
            add_1.aOG().a(aon_0.aYc().getString("error.fight.invalidMinimalEvolutionTeamBudget", 5000), 1090L, 102, 1);
        } else if (xz_02.getValue() > 6000) {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.budgetExploded", xz_02.getValue(), 6000), 1090L, 102, 1);
        } else {
            return true;
        }
        return false;
    }

    public boolean a(wy_2 wy_22, ee_2 ee_22) {
        boolean bl2 = true;
        xj xj2 = (xj)la_0.XJ().pj(Math.abs(wy_22.jf()));
        akw_0[] akw_0Array = xj2.tu();
        for (int j = 0; j < akw_0Array.length; ++j) {
            int n2;
            Object object;
            if (akw_0Array[j].getType() != AI.aHK.tI()) continue;
            short s = bf_1.df().g((short)akw_0Array[j].rg()[0]).getType();
            if (s == 70) {
                add_1.aOG().a(aon_0.aYc().getString("runesArentUsedThatWay", xj2.getName()), 2L, 102, 1);
                return false;
            }
            if (s == 21) continue;
            long l2 = akw_0Array[j].aAl();
            if (!aap.do(l2)) {
                object = aap.dp(l2) || aap.dq(l2) ? (Object)apN.aDK().Ln().kh().Gj() : (Object)ee_22.kh().Gj();
                for (n2 = 0; n2 < ((Object)object).length; ++n2) {
                    if (bf_1.df().g((short)object[n2]).getType() != s) continue;
                    add_1.aOG().f(aon_0.aYc().getString("conditionAlreadyOnApplicationOnFighter"), 1090, 1);
                    return false;
                }
                continue;
            }
            object = xz_0.amc().amd();
            for (n2 = 0; n2 < ((ArrayList)object).size(); ++n2) {
                short[] sArray = ((ee_2)((ArrayList)object).get(n2)).kh().Gj();
                for (int i2 = 0; i2 < sArray.length; ++i2) {
                    if (bf_1.df().g(sArray[i2]).getType() != s) continue;
                    bl2 = false;
                }
            }
        }
        if (!bl2) {
            r_0 r_02 = add_1.aOG().f(aon_0.aYc().getString("conditionAlreadyOnApplicationOnOneFighter"), 1090, 0);
            r_02.a(new aou_2(this, xj2, ee_22, wy_22));
        } else {
            r_0 r_03 = add_1.aOG().a(aon_0.aYc().getString("questionUseItemOnFighter", xj2.getName()), 24L, 102, 1);
            r_03.a(new akv_1(this, ee_22, xj2, wy_22));
        }
        return true;
    }
}

