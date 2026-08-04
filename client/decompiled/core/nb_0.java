/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from NB
 */
public class nb_0
implements atG {
    private static final Logger a = Logger.getLogger(nb_0.class);
    private static final nb_0 bAb = new nb_0();

    public static nb_0 aaI() {
        return bAb;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 23051: {
                if (nb_0.lj()) {
                    nb_0.aaJ();
                }
                return false;
            }
            case 23052: {
                sb_0 sb_02 = (sb_0)pr_02;
                wy_2 wy_22 = (wy_2)apN.aDK().Ln().aQn().bW(sb_02.getIntValue());
                apN.aDK().Ln().a(wy_22, wy_22.tj().aXg()[0]);
                return false;
            }
            case 23053: {
                sb_0 sb_03 = (sb_0)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                sj_12.d((wy_2)sj_12.aQn().z(sb_03.ak()));
                return false;
            }
            case 16631: {
                sb_0 sb_04 = (sb_0)pr_02;
                azs_0.aLV().g("teamManagement.selectedItemCardListType", sb_04.aj());
                azs_0.aLV().a((aho_0)adY.atu().Ol(), "availableFighterCards");
                return false;
            }
            case 23055: {
                ayd_0 ayd_02 = (ayd_0)pr_02;
                ee_2 ee_22 = ayd_02.tG();
                if (ee_22 != null) {
                    int n2;
                    long[] lArray = xz_0.amc().afE().eJ();
                    int n3 = 0;
                    if (ee_22.NB() == 1) {
                        for (n2 = 0; n2 < lArray.length; ++n2) {
                            ee_2 ee_23 = adY.atu().dz(lArray[n2]);
                            if (ee_23 == null || ee_23.NB() != 0 && ee_23.NB() != 2) continue;
                            n3 = (short)(n3 + 1);
                        }
                        if (n3 >= 6) {
                            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.fightersCountExploded"), 1090L, 102, 1);
                            return false;
                        }
                        ee_22.V((byte)0);
                        azs_0.aLV().a((aho_0)xz_0.amc(), "teamLeague");
                    } else if (ee_22.NB() == 0) {
                        for (n2 = 0; n2 < lArray.length; ++n2) {
                            ee_2 ee_24 = adY.atu().dz(lArray[n2]);
                            if (ee_24 == null || ee_24.NB() != 1) continue;
                            n3 = (short)(n3 + 1);
                        }
                        if (n3 >= 7) {
                            add_1.aOG().a(aon_0.aYc().getString("error.evolution.tooManyFightersOnBench"), 1090L, 102, 1);
                            return false;
                        }
                        ee_22.V((byte)1);
                        azs_0.aLV().a((aho_0)xz_0.amc(), "teamLeague");
                    } else if (ee_22.NB() == 2) {
                        for (n2 = 0; n2 < lArray.length; ++n2) {
                            ee_2 ee_25 = adY.atu().dz(lArray[n2]);
                            if (ee_25 == null || ee_25.NB() != 3) continue;
                            n3 = (short)(n3 + 1);
                        }
                        if (n3 >= 5) {
                            add_1.aOG().a(aon_0.aYc().getString("error.evolution.graveyardFull"), 1090L, 102, 1);
                            return false;
                        }
                        ee_22.V((byte)3);
                    }
                    azs_0.aLV().a((aho_0)xz_0.amc(), "fighters");
                    azs_0.aLV().a((aho_0)xz_0.amc(), "fightersOnBench");
                    azs_0.aLV().a((aho_0)xz_0.amc(), "value");
                    Jc jc = new Jc();
                    jc.j(ee_22.getId());
                    apN.aDK().vJ().b(jc);
                }
                return false;
            }
            case 23056: {
                ayd_0 ayd_03 = (ayd_0)pr_02;
                mb_0.Yl().hide();
                ee_2 ee_26 = ayd_03.tG();
                wy_2 wy_23 = (wy_2)apN.aDK().Ln().aQn().bW(ayd_03.getIntValue());
                if (wy_23 == null) {
                    wy_23 = (wy_2)apN.aDK().Ln().aQn().bW(-ayd_03.getIntValue());
                }
                if (ee_26 != null && wy_23 != null) {
                    this.a((xj)wy_23.NR(), ee_26);
                }
                return false;
            }
            case 16605: {
                if (apN.aDK().Ln().c(avq_0.ce((short)275))) {
                    if (adY.atu().amq().size() <= 100) {
                        if (!add_1.aOG().kR("fighterCreationDialog")) {
                            abv_1 abv_12 = adY.atu().atv();
                            abv_12.b((byte)-1, (byte)1, (byte)0);
                            abv_12.NZ();
                            adY.atu().a(abv_12);
                            add_1.aOG().a("fighterCreationDialog", oh_2.bq("fighterCreationDialog"), 257L, (short)10003);
                        } else {
                            add_1.aOG().kO("fighterCreationDialog");
                        }
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.noMoreRoom"), 1090L, 102, 1);
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("mustTalkToDemonIII"), 1090L, 102, 1);
                }
                return false;
            }
            case 16650: {
                byte by;
                ayd_0 ayd_04 = (ayd_0)pr_02;
                abv_1 abv_13 = (abv_1)ayd_04.tG();
                if (abv_13 != null && (by = ayd_04.aj()) != abv_13.lY()) {
                    abv_13.P(by);
                    azs_0.aLV().a((aho_0)abv_13, "actorDescriptorLibrary");
                }
                return false;
            }
            case 16651: {
                byte by;
                ayd_0 ayd_05 = (ayd_0)pr_02;
                abv_1 abv_14 = (abv_1)ayd_05.tG();
                if (abv_14 != null && (by = ayd_05.aj()) != abv_14.lX()) {
                    abv_14.Q(by);
                    azs_0.aLV().a((aho_0)abv_14, "actorDescriptorLibrary");
                }
                return false;
            }
            case 16652: {
                byte by;
                ayd_0 ayd_06 = (ayd_0)pr_02;
                abv_1 abv_15 = (abv_1)ayd_06.tG();
                if (abv_15 != null && (by = ayd_06.aj()) != abv_15.Ns()) {
                    abv_15.R(by);
                    azs_0.aLV().a((aho_0)abv_15, "actorDescriptorLibrary");
                }
                return false;
            }
            case 23057: {
                sb_0 sb_05 = (sb_0)pr_02;
                if (adY.atu().amq().size() <= 100) {
                    if (!add_1.aOG().kR("fighterCreationTutorialDialog")) {
                        abv_1 abv_16 = adY.atu().atv();
                        abv_16.b((byte)-1, sb_05.aj(), (byte)0);
                        adY.atu().a(abv_16);
                        add_1.aOG().a("fighterCreationTutorialDialog", oh_2.bq("fighterCreationTutorialDialog"), 257L, (short)10003);
                    } else {
                        add_1.aOG().kO("fighterCreationTutorialDialog");
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.noMoreRoom"), 1090L, 102, 1);
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
            case 16627: {
                ayd_0 ayd_07 = (ayd_0)pr_02;
                abv_1 abv_18 = (abv_1)ayd_07.tG();
                if (abv_18 != null) {
                    abv_18.aqf();
                }
                return false;
            }
            case 16626: {
                ayd_0 ayd_08 = (ayd_0)pr_02;
                abv_1 abv_19 = (abv_1)ayd_08.tG();
                if (abv_19 != null) {
                    abv_19.aqg();
                }
                return false;
            }
            case 16606: {
                adY.atu().atx();
                add_1.aOG().kO("fighterCreationDialog");
                return false;
            }
            case 23058: {
                adY.atu().atx();
                add_1.aOG().kO("fighterCreationTutorialDialog");
                return false;
            }
            case 16609: {
                dv_0 dv_02 = (dv_0)pr_02;
                abv_1 abv_110 = (abv_1)dv_02.tG();
                if (abv_110 != null && abv_110.NY().lV() != dv_02.cu()) {
                    abv_110.W(dv_02.cu());
                    abv_110.NZ();
                }
                return false;
            }
            case 16611: {
                abv_1 abv_111 = adY.atu().Ol();
                if (abv_111 != null) {
                    aNb aNb2 = new aNb();
                    et_2 et_22 = abv_111.Om();
                    et_22.setType((byte)2);
                    aNb2.h(et_22);
                    aNb2.fn(false);
                    apN.aDK().vJ().b(aNb2);
                }
                return false;
            }
            case 16612: {
                ayd_0 ayd_09 = (ayd_0)pr_02;
                ee_2 ee_27 = ayd_09.tG();
                if (ee_27 != null) {
                    r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("question.removeFighter", ee_27.getName()), 1177L, 102, 1);
                    r_02.a(new aEp(this, ee_27));
                }
                return false;
            }
            case 23060: {
                ayd_0 ayd_010 = (ayd_0)pr_02;
                if (apN.aDK().c(afb_1.auN())) {
                    apN.aDK().b(afb_1.auN());
                    return false;
                }
                ee_2 ee_28 = ayd_010.tG();
                int n4 = ee_28.NH();
                Ei ei = (Ei)akp_1.aVO().aW(n4);
                if (ei == null) {
                    a.error((Object)("Il n'existe pas de board d'id " + n4));
                    return false;
                }
                ei.fi(ee_28.NC() - 1);
                ei.fj(ee_28.ND() - 1);
                azs_0.aLV().g("sphereboard.fighter", ee_28);
                afb_1.auN().j(ee_28.getId());
                afb_1.auN().setSphereBoard(ei);
                apN.aDK().a(afb_1.auN());
                return false;
            }
            case 23062: {
                ayd_0 ayd_011 = (ayd_0)pr_02;
                ee_2 ee_29 = ayd_011.tG();
                if (ee_29 != null) {
                    adY.atu().a(ee_29.Ol());
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
            case 16616: {
                ayd_0 ayd_012 = (ayd_0)pr_02;
                ee_2 ee_210 = ayd_012.tG();
                if (ee_210 != null) {
                    bp_1 bp_12 = new bp_1();
                    bp_12.b(ee_210);
                    bp_12.e(xz_0.amc().tI());
                    apN.aDK().vJ().b(bp_12);
                    adY.atu().dz(ee_210.getId()).Ob();
                } else {
                    a.error((Object)"on tente de sauvegarder un fighter null");
                }
                azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
                return false;
            }
            case 16618: {
                da_1 da_12 = (da_1)pr_02;
                abv_1 abv_112 = (abv_1)da_12.tG();
                yp_2 yp_22 = da_12.fw();
                if (abv_112 != null && yp_22 != null) {
                    abv_112.c(yp_22);
                }
                return false;
            }
            case 16619: {
                da_1 da_13 = (da_1)pr_02;
                abv_1 abv_113 = (abv_1)da_13.tG();
                yp_2 yp_23 = da_13.fw();
                if (abv_113 != null && yp_23 != null) {
                    abv_113.d(yp_23);
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
                abv_1 abv_114 = (abv_1)pd_22.tG();
                ve_0 ve_02 = pd_22.abQ();
                if (abv_114 != null && ve_02 != null) {
                    short s = pd_22.ha();
                    if (s == -1) {
                        s = ve_02.Vk().aiJ();
                    }
                    abv_114.a(ve_02, s);
                }
                return false;
            }
            case 16621: {
                pd_2 pd_23 = (pd_2)pr_02;
                abv_1 abv_115 = (abv_1)pd_23.tG();
                ve_0 ve_03 = pd_23.abQ();
                if (abv_115 != null && ve_03 != null) {
                    abv_115.e(ve_03);
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
            case 16700: {
                ia_2 ia_22 = (ia_2)pr_02;
                wy_2 wy_24 = ia_22.lm();
                if (wy_24 != null) {
                    azs_0.aLV().g("coachManagement.selectedCard", wy_24);
                }
                return false;
            }
            case 16701: {
                azs_0.aLV().g("coachManagement.selectedCard", (Object)null);
                return false;
            }
            case 16637: {
                sb_0 sb_06 = (sb_0)pr_02;
                azs_0.aLV().g("teamManagement.help", aon_0.aYc().getString("help." + sb_06.getStringValue()));
                azs_0.aLV().g("teamManagement.helpIcon", sb_06.getStringValue() + "Icon");
                return false;
            }
        }
        return true;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            po_0.abV().abW();
            apN.aDK().a(ce_1.IU());
            apN.aDK().a(wp_0.CH());
            add_1.aOG().l("dofusarena.evolution", aio_1.class);
            add_1.aOG().l("dofusarena.teamManagement", acx_2.class);
            aij_0.aUF().c(null);
            apN.aDK().Ln().yH();
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        add_1.aOG().kG("dofusarena.evolution");
        add_1.aOG().kG("dofusarena.teamManagement");
        apN.aDK().Ln().yG();
        apN.aDK().b(ce_1.IU());
        apN.aDK().Ln().yI();
        this.W();
    }

    protected void W() {
        sj_1 sj_12 = apN.aDK().Ln();
        if (sj_12.c(avq_0.ce((short)284))) {
            if (sj_12.qI().contains(or_0.YV.tI())) {
                add_1.aOG().kO("evolutionDialog");
            } else {
                add_1.aOG().kO("evolutionTeamManagementTuto2Dialog");
            }
        } else {
            add_1.aOG().kO("evolutionTeamManagementTutoDialog");
        }
        if (add_1.aOG().kR("fighterEvolutionEquipmentDialog")) {
            add_1.aOG().kO("fighterEvolutionEquipmentDialog");
        }
        if (add_1.aOG().kR("fighterCreationTutorialDialog")) {
            add_1.aOG().kO("fighterCreationTutorialDialog");
        }
        if (add_1.aOG().kR("fighterCreationDialog")) {
            add_1.aOG().kO("fighterCreationDialog");
        }
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public static boolean lj() {
        xz_0 xz_02 = xz_0.amc();
        zy_0 zy_02 = new zy_0();
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = true;
        long[] lArray = xz_02.afE().eJ();
        for (int j = 0; j < lArray.length; ++j) {
            ee_2 ee_22 = adY.atu().dz(lArray[j]);
            if (ee_22 != null && ee_22.NB() == 2) {
                bl4 = true;
                break;
            }
            if (ee_22 == null || ee_22.NB() != 0) continue;
            bl5 = false;
            if (!zy_02.K(ee_22.NY().lV())) {
                zy_02.e(ee_22.NY().lV(), (byte)1);
            } else {
                byte by = zy_02.H(ee_22.NY().lV());
                if ((by = (byte)(by + 1)) == 3) {
                    bl3 = true;
                    break;
                }
                zy_02.e(ee_22.NY().lV(), by);
            }
            bl2 |= ee_22.Nz() == nr_0.Pq;
        }
        if (bl4) {
            add_1.aOG().f(aon_0.aYc().getString("error.evolution.deadFighterInTeam"), 102, 1);
        }
        if (!bl3) {
            return nb_0.a(bl2, bl5, xz_02);
        }
        add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.sameBreedFightersCountExploded"), 102, 1);
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
        if (bl2) {
            add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.sameBreedFightersCountExploded"), 102, 1);
        }
        return nb_0.a(true, bl3, xz_02);
    }

    private static boolean a(boolean bl2, boolean bl3, xz_0 xz_02) {
        if (xz_02 == sw_1.bMq) {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
        } else if (bl3) {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
        } else if (!xz_02.afH()) {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmptyForACoach"), 1090L, 102, 1);
        } else if (xz_02.getValue() > 6000) {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.budgetExploded", xz_02.getValue(), 6000), 1090L, 102, 1);
        } else {
            if (bl2) {
                r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("warningFighterExhausted"), 24L, 102, 0);
                r_02.a(new aEn());
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean a(xj xj2, ee_2 ee_22) {
        boolean bl2 = true;
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
            r_02.a(new aEj(this, xj2, ee_22));
        } else {
            r_0 r_03 = add_1.aOG().a(aon_0.aYc().getString("questionUseItemOnFighter", xj2.getName()), 24L, 102, 1);
            r_03.a(new aei_0(this, ee_22, xj2));
        }
        return true;
    }

    private static void aaJ() {
        sj_1 sj_12 = apN.aDK().Ln();
        en_1 en_12 = sj_12.aQn().pH();
        String string = "";
        for (wy_2 wy_22 : en_12) {
            if (((xj)wy_22.NR()).tr() >= sj_12.getLevel() || sj_12.aQm().contains(Math.abs(wy_22.jf()))) continue;
            string = string + wy_22.getName() + ", ";
        }
        if (!string.equals("")) {
            r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("warningEquipmentWillBeAddedToTome", string), 24L, 102, 0);
            r_02.a(new aeh_0());
        } else {
            nb_0.aaK();
        }
    }

    private static void aaK() {
        apN.aDK().a(do_2.Mm());
        ajw_0 ajw_02 = new ajw_0();
        ajw_02.aj(apN.aDK().Ln().getId());
        ajw_02.C((short)99);
        apN.aDK().vJ().b(ajw_02);
        apN.aDK().b(nb_0.aaI());
    }

    static /* synthetic */ void aaL() {
        nb_0.aaJ();
    }

    static /* synthetic */ void aaM() {
        nb_0.aaK();
    }
}

