/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;

/*
 * Renamed from aCx
 */
public class acx_2 {
    public static final String PACKAGE = "dofusarena.teamManagement";
    private static boolean duq = false;
    private static ob_1 dur;

    public static void createNewFighter(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.b(false);
        sb_02.f(16605);
        acu_1.ara().c(sb_02);
    }

    public static void createNewEvolutionFighter(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.b(true);
        sb_02.f(16605);
        acu_1.ara().c(sb_02);
    }

    public static void duplicateFighter(ke ke2) {
        abv_1 abv_12 = adY.atu().Ol();
        if (abv_12 != null) {
            aNb aNb2 = new aNb();
            aNb2.h(abv_12.Om());
            aNb2.fn(true);
            apN.aDK().vJ().b(aNb2);
        }
    }

    public static void closeFighterCreationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16606);
        acu_1.ara().c(sb_02);
    }

    public static void setFighterBreedId(ke ke2, abv_1 abv_12, String string) {
        dv_0 dv_02 = new dv_0();
        dv_02.b(abv_12);
        dv_02.c(Byte.valueOf(string));
        dv_02.f(16609);
        acu_1.ara().c(dv_02);
    }

    public static void setFighterSex(ke ke2, abv_1 abv_12, String string) {
        abb_2 abb_22 = new abb_2();
        abb_22.b(abv_12);
        abb_22.S(Byte.valueOf(string));
        abb_22.f(16610);
        acu_1.ara().c(abb_22);
    }

    public static void setFighterVersion(ke ke2, abv_1 abv_12, String string) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(abv_12);
        ayd_02.a(-Byte.valueOf(string).byteValue());
        ayd_02.f(16640);
        acu_1.ara().c(ayd_02);
    }

    public static void createFighter(ke ke2, Ur ur) {
        if (ke2.aV() == qe_1.bFB || ke2.aV() == qe_1.bFm && ((aqG)ke2).getKeyCode() == 10) {
            if (ur.isValid()) {
                sb_0 sb_02 = new sb_0();
                sb_02.f(16611);
                acu_1.ara().c(sb_02);
            } else {
                add_1.aOG().a(aon_0.aYc().getString("error.fighterCreation.invalidName"), 1027L, 102, 1);
            }
        }
    }

    public static boolean validateCreateFighterForm(Ur ur) {
        ur.agN();
        afl_0 afl_02 = ur.getProperty("teamManagement.editableFighter");
        if (afl_02 == null) {
            return false;
        }
        if (!(afl_02.getValue() instanceof ee_2)) {
            return false;
        }
        ee_2 ee_22 = (ee_2)afl_02.getValue();
        String string = ee_22.getName();
        return string != null && string.length() <= 16 && aet_0.dDK.matcher(string).matches() && avQ.jR(string);
    }

    public static void deleteFighter(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.f(16612);
        ayd_02.b(ee_22);
        acu_1.ara().c(ayd_02);
    }

    public static void editFighter(ke ke2, ee_2 ee_22) {
        if (ee_22 != null) {
            ayd_0 ayd_02 = new ayd_0();
            ayd_02.f(16614);
            ayd_02.b(ee_22);
            acu_1.ara().c(ayd_02);
        }
    }

    public static void setNextFighterDirection(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(16627);
        acu_1.ara().c(ayd_02);
    }

    public static void setPreviousFighterDirection(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(16626);
        acu_1.ara().c(ayd_02);
    }

    public static void closeFighterEditionDialog(ke ke2, ee_2 ee_22) {
        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("question.saveEditableFighter", ee_22.getName()), 1176L, 102, 1);
        r_02.a(new aze_0(ee_22));
    }

    public static void saveEditableFighter(ke ke2, abv_1 abv_12) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.f(16616);
        ayd_02.b(abv_12);
        acu_1.ara().c(ayd_02);
    }

    public static void newEditableTeamPreset(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16602);
        acu_1.ara().c(sb_02);
    }

    public static void newXvsXEditableTeamPreset(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16613);
        acu_1.ara().c(sb_02);
    }

    public static void newTournamentTeamPreset(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16661);
        acu_1.ara().c(sb_02);
    }

    public static void deleteEditableTeamPreset(ke ke2, zK zK2) {
        Db db = new Db();
        db.b(zK2);
        acu_1.ara().c(db);
    }

    public static void showTeamPresetList(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16628);
        acu_1.ara().c(sb_02);
    }

    public static void showTeamPresetBudget(ke ke2, aht_1 aht_12) {
        ai_2 ai_22 = (ai_2)aht_12.getElementMap().R("teamPresetBudgetPopUp");
        qu_0.popup(ke2, ai_22);
    }

    public static void hideTeamPresetBudget(ke ke2, aht_1 aht_12) {
        ai_2 ai_22 = (ai_2)aht_12.getElementMap().R("teamPresetBudgetPopUp");
        qu_0.closePopup(ke2, ai_22);
    }

    public static boolean validateSpellDrop(kn_1 kn_12, Object object, kn_1 kn_13, Object object2, Object object3, abv_1 abv_12) {
        if (object3 != null && object3 instanceof yp_2) {
            yp_2 yp_22 = (yp_2)object3;
            if (abv_12 != null) {
                ajv_2 ajv_22 = abv_12.Oh();
                return ajv_22.hp().b(ajv_22, yp_22) == 0;
            }
        }
        return true;
    }

    public static void dropSpell(aiU aiU2, abv_1 abv_12) {
        Object object = aiU2.getValue();
        if (object != null && object instanceof yp_2) {
            yp_2 yp_22 = (yp_2)object;
            da_1 da_12 = new da_1();
            da_12.b(abv_12);
            da_12.a(yp_22);
            da_12.f(16618);
            acu_1.ara().c(da_12);
        }
    }

    public static void dragSpell(aly_2 aly_22, abv_1 abv_12) {
        Object object = aly_22.getValue();
        if (object != null && object instanceof yp_2) {
            yp_2 yp_22 = (yp_2)object;
            da_1 da_12 = new da_1();
            da_12.b(abv_12);
            da_12.a(yp_22);
            da_12.f(16619);
            acu_1.ara().c(da_12);
        }
    }

    public static boolean validateEquipmentDrop(kn_1 kn_12, Object object, kn_1 kn_13, Object object2, Object object3, abv_1 abv_12, String string) {
        if (object3 != null && object3 instanceof ve_0) {
            ve_0 ve_02 = (ve_0)object3;
            if (abv_12 != null) {
                en_1 en_12 = abv_12.Oi();
                return en_12.hp().a((mi_2)en_12, (uh_1)ve_02, Short.valueOf(string)) == 0;
            }
        }
        return true;
    }

    public static void dropEquipment(aiU aiU2, abv_1 abv_12, String string) {
        Object object = aiU2.getValue();
        if (object != null && object instanceof ve_0) {
            ve_0 ve_02 = (ve_0)object;
            pd_2 pd_22 = new pd_2();
            pd_22.b(abv_12);
            pd_22.d(ve_02);
            pd_22.k(Short.valueOf(string));
            pd_22.f(16620);
            acu_1.ara().c(pd_22);
        }
    }

    public static void dragEquipment(aly_2 aly_22, ee_2 ee_22, String string) {
        Object object = aly_22.getValue();
        if (object != null && object instanceof ve_0) {
            ve_0 ve_02 = (ve_0)object;
            if (aly_22.oF() != null) {
                pd_2 pd_22 = new pd_2();
                pd_22.b(ee_22);
                pd_22.d(ve_02);
                pd_22.k(Short.valueOf(string));
                pd_22.f(16621);
                acu_1.ara().c(pd_22);
            }
        }
    }

    public static void addEquipment(aGJ aGJ2, abv_1 abv_12) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof ve_0) {
            ve_0 ve_02 = (ve_0)object;
            pd_2 pd_22 = new pd_2();
            pd_22.b(abv_12);
            pd_22.d(ve_02);
            pd_22.k(Short.valueOf("-1"));
            pd_22.f(16620);
            acu_1.ara().c(pd_22);
        }
    }

    public static void removeEquipment(abd_1 abd_12, abv_1 abv_12, String string) {
        if (abd_12.oF() != null) {
            pd_2 pd_22 = new pd_2();
            pd_22.b(abv_12);
            pd_22.d((ve_0)abv_12.Oi().p(Short.valueOf(string)));
            pd_22.k(Short.valueOf(string));
            pd_22.f(16621);
            acu_1.ara().c(pd_22);
        }
    }

    public static void addSpell(aGJ aGJ2, abv_1 abv_12) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof yp_2) {
            yp_2 yp_22 = (yp_2)object;
            da_1 da_12 = new da_1();
            da_12.b(abv_12);
            da_12.a(yp_22);
            da_12.f(16618);
            acu_1.ara().c(da_12);
        }
    }

    public static void removeSpell(aGJ aGJ2, abv_1 abv_12) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof yp_2) {
            yp_2 yp_22 = (yp_2)object;
            da_1 da_12 = new da_1();
            da_12.b(abv_12);
            da_12.a(yp_22);
            da_12.f(16619);
            acu_1.ara().c(da_12);
        }
    }

    public static void showEquipmentInfos(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof ve_0 && !ago_2.getInstance().isDragging()) {
            pd_2 pd_22 = new pd_2();
            pd_22.d((ve_0)object);
            pd_22.f(16622);
            acu_1.ara().c(pd_22);
        }
    }

    public static void showStatisticsInfos(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof ee_2) {
            ayd_0 ayd_02 = new ayd_0();
            ayd_02.b((ee_2)object);
            ayd_02.f(16629);
            acu_1.ara().c(ayd_02);
        }
    }

    public static void hideStatisticsInfos(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16630);
        acu_1.ara().c(sb_02);
    }

    public static void showSpellInfos(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof yp_2 && !ago_2.getInstance().isDragging()) {
            da_1 da_12 = new da_1();
            da_12.a((yp_2)object);
            da_12.f(16624);
            acu_1.ara().c(da_12);
        }
    }

    public static void changeItemCardType(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        byte by = Byte.valueOf(string);
        by = (byte)(by + 1);
        sb_02.a(by);
        sb_02.f(16631);
        acu_1.ara().c(sb_02);
    }

    public static void changeEvolutionItemCardType(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        byte by = Byte.valueOf(string);
        by = (byte)(by + 1);
        sb_02.a(by);
        sb_02.f(16638);
        acu_1.ara().c(sb_02);
    }

    public static void addNewTeam(ke ke2, Ur ur) {
        if (ke2 instanceof aqG && ((aqG)ke2).getKeyCode() != 10) {
            return;
        }
        if (ur.isValid()) {
            String string = ur.getProperty("teamManagement.teamName").getString();
            xw_2 xw_22 = new xw_2();
            xw_22.f(16632);
            xw_22.ae(string);
            acu_1.ara().c(xw_22);
        } else {
            add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.teamNameEmpty"), 102, 1);
        }
    }

    public static void addNewTournamentTeam(ke ke2, Ur ur) {
        if (ke2 instanceof aqG && ((aqG)ke2).getKeyCode() != 10) {
            return;
        }
        if (ur.isValid()) {
            String string = ur.getProperty("teamManagement.teamName").getString();
            xw_2 xw_22 = new xw_2();
            xw_22.f(16660);
            xw_22.ae(string);
            acu_1.ara().c(xw_22);
        } else {
            add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.teamNameEmpty"), 102, 1);
        }
    }

    public static void addNewTeamXvsX(ke ke2, Ur ur) {
        if (ke2 instanceof aqG && ((aqG)ke2).getKeyCode() != 10) {
            return;
        }
        if (ur.isValid()) {
            String string = ur.getProperty("teamManagement.teamName").getString();
            ev_0 ev_02 = new ev_0();
            ev_02.ae(string);
            acu_1.ara().c(ev_02);
        } else {
            add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.teamNameEmpty"), 102, 1);
        }
    }

    public static void openCloseTeamNameDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16633);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseTeamXvsXNameDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16634);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseNewTeamTournamentDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16662);
        acu_1.ara().c(sb_02);
    }

    public static boolean validateTeamNameForm(Ur ur) {
        ur.agN();
        return !ur.getProperty("teamManagement.teamName").isEmpty();
    }

    public static boolean validateTeamXvsXForm(Ur ur) {
        ur.agN();
        return !ur.getProperty("teamManagement.teammateName").isEmpty() && !ur.getProperty("teamManagement.teamName").isEmpty();
    }

    public static void selectTeamPreset(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof zK) {
            kd_0 kd_02 = new kd_0();
            kd_02.C(((zK)object).tI());
            acu_1.ara().c(kd_02);
        }
    }

    public static void selectEditableFighter(aGJ aGJ2) {
        if (duq) {
            add_1.aOG().a(aon_0.aYc().getString("error.fight.creation.badClick"), 1090L, 102, 1);
            return;
        }
        Object object = aGJ2.getItemValue();
        if (object instanceof ee_2) {
            ee_2 ee_22 = (ee_2)object;
            if (aGJ2.getButton() == 1) {
                adY.atu().a(ee_22.Ol());
                azs_0.aLV().a((aho_0)adY.atu(), "teamManagement.filtredFighterList");
                if (!add_1.aOG().kR("teamManagementFighterListDialog")) {
                    add_1.aOG().a("teamManagementFighterListDialog", oh_2.bq("teamManagementFighterListDialog"), (short)10000);
                }
                if (!add_1.aOG().kR("teamManagementSelectedFighterDialog")) {
                    add_1.aOG().a("teamManagementSelectedFighterDialog", oh_2.bq("teamManagementSelectedFighterDialog"), 1L, (short)10001);
                }
            }
        }
    }

    public static final void onFighterDropped(aiU aiU2) {
        Object object;
        Object object2;
        aht_1 aht_12;
        if (duq) {
            add_1.aOG().a(aon_0.aYc().getString("error.fight.creation.badClick"), 1090L, 102, 1);
            return;
        }
        Object object3 = aiU2.getValue();
        boolean bl2 = true;
        short s = -1;
        short s2 = -1;
        rf_0 rf_02 = (rf_0)aiU2.ayA().getParentOfType(rf_0.class);
        if (rf_02 != null && (aht_12 = (qa_1)rf_02.getParentOfType(qa_1.class)) != null && (object2 = ((qa_1)aht_12).getItemValue()) instanceof zK) {
            bl2 = ((zK)object2).isEditable();
            s2 = ((zK)object2).tI();
        }
        if ((aht_12 = (rf_0)aiU2.ayB().getParentOfType(rf_0.class)) != null && (object2 = (qa_1)aht_12.getParentOfType(qa_1.class)) != null && (object = ((qa_1)object2).getItemValue()) instanceof zK) {
            bl2 &= ((zK)object).isEditable();
            s = ((zK)object).tI();
        }
        if (object3 != null && object3 instanceof ee_2 && bl2) {
            object2 = (ee_2)object3;
            object = bs_0.IF().at(s);
            if (object == null || ((sw_1)object).afE().size() < 6) {
                Object object4;
                int n2 = 0;
                if (object != null) {
                    object4 = ((sw_1)object).afE().eJ();
                    for (int j = 0; j < ((long[])object4).length; ++j) {
                        if (adY.atu().dz((long)object4[j]).NY() != ((ee_2)object2).NY()) continue;
                        n2 = (byte)(n2 + 1);
                    }
                }
                if (n2 < 2) {
                    object4 = new qp_1();
                    ((qp_1)object4).j(((gn_0)object2).getId());
                    ((qp_1)object4).aS(s2);
                    ((qp_1)object4).aT(s);
                    ((qp_1)object4).am(apN.aDK().Ln().getId());
                    apN.aDK().vJ().b((pr_0)object4);
                } else {
                    add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.sameBreedFightersCountExploded"), 102, 1);
                }
            } else {
                add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.noMoreRoom"), 102, 1);
            }
        }
    }

    public static final void onFighterDroppedXvsX(aiU aiU2) {
        int n2;
        Object object;
        JG jG;
        JG jG2;
        Object object2;
        qa_1 qa_12;
        rf_0 rf_02;
        if (duq) {
            add_1.aOG().a(aon_0.aYc().getString("error.fight.creation.badClick"), 1090L, 102, 1);
            return;
        }
        Object object3 = aiU2.getValue();
        boolean bl2 = true;
        short s = -1;
        short s2 = -1;
        rf_0 rf_03 = (rf_0)aiU2.ayA().getParentOfType(rf_0.class);
        if (rf_03 != null && (rf_02 = (rf_0)rf_03.getParentOfType(rf_0.class)) != null && (qa_12 = (qa_1)rf_02.getParentOfType(qa_1.class)) != null && (object2 = qa_12.getItemValue()) instanceof zK) {
            bl2 = ((zK)object2).isEditable();
            s2 = ((zK)object2).tI();
        }
        rf_02 = (rf_0)aiU2.ayB().getParentOfType(rf_0.class);
        long l2 = -1L;
        if (rf_02 != null && (jG2 = (qa_1)rf_02.getParentOfType(qa_1.class)) != null) {
            jG = (rf_0)((na_1)jG2).getParentOfType(rf_0.class);
            qa_1 qa_13 = (qa_1)((na_1)jG).getParentOfType(qa_1.class);
            object = qa_13.getItemValue();
            n2 = ((rf_0)jG).getItemIndex(((qa_1)jG2).getItemValue());
            if (object instanceof zK) {
                bl2 &= ((zK)object).isEditable();
                s = ((zK)object).tI();
                l2 = ((zK)object).afF().get(n2);
            }
        }
        if (object3 != null && object3 instanceof ee_2 && bl2 && l2 != -1L) {
            jG2 = (ee_2)object3;
            jG = bs_0.IF().at(s);
            if (jG == null || (((sw_1)jG).afE().size() < 6 || s == s2) && ((sw_1)jG).cF(l2).size() < 5) {
                int n3 = 0;
                if (jG != null) {
                    object = ((sw_1)jG).afE().eJ();
                    for (n2 = 0; n2 < ((Object)object).length; ++n2) {
                        if (adY.atu().dz((long)object[n2]).NY() != ((ee_2)jG2).NY()) continue;
                        n3 = (byte)(n3 + 1);
                    }
                }
                if (n3 < 2 || s == s2) {
                    object = new qp_1();
                    ((qp_1)object).j(((gn_0)jG2).getId());
                    ((qp_1)object).aS(s2);
                    ((qp_1)object).aT(s);
                    ((qp_1)object).am(l2);
                    apN.aDK().vJ().b((pr_0)object);
                } else {
                    add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.sameBreedFightersCountExploded"), 102, 1);
                }
            } else {
                add_1.aOG().f(aon_0.aYc().getString("error.teamManagement.noMoreRoom"), 102, 1);
            }
        }
    }

    public static void onFighterRemoved(aiU aiU2) {
        Object object;
        Object object2;
        JG jG;
        if (duq) {
            add_1.aOG().a(aon_0.aYc().getString("error.fight.creation.badClick"), 1090L, 102, 1);
            return;
        }
        Object object3 = aiU2.getValue();
        boolean bl2 = true;
        short s = -1;
        rf_0 rf_02 = (rf_0)aiU2.ayA().getParentOfType(rf_0.class);
        if (rf_02 != null && (jG = (qa_1)rf_02.getParentOfType(qa_1.class)) != null && (object2 = ((qa_1)jG).getItemValue()) instanceof zK) {
            bl2 = ((zK)object2).isEditable();
            s = ((zK)object2).tI();
        }
        if (s == -1 && rf_02 != null && (jG = (rf_0)rf_02.getParentOfType(rf_0.class)) != null && (object2 = (qa_1)((na_1)jG).getParentOfType(qa_1.class)) != null && (object = ((qa_1)object2).getItemValue()) instanceof zK) {
            bl2 = ((zK)object).isEditable();
            s = ((zK)object).tI();
        }
        if (object3 != null && object3 instanceof ee_2 && bl2) {
            jG = new qp_1();
            ((qp_1)jG).j(((ee_2)object3).getId());
            ((qp_1)jG).aS(s);
            ((qp_1)jG).aT((short)-1);
            ((qp_1)jG).am(apN.aDK().Ln().getId());
            apN.aDK().vJ().b((pr_0)jG);
        }
    }

    public static void showFighterInfos(ke ke2, ie ie2) {
        ai_2 ai_22 = (ai_2)ie2.getElementMap().R("fighterInfosPopup");
        qu_0.popup(ke2, ai_22);
    }

    public static void hideFighterInfos(ke ke2, ie ie2) {
        ai_2 ai_22 = (ai_2)ie2.getElementMap().R("fighterInfosPopup");
        qu_0.closePopup(ke2, ai_22);
    }

    public static void showFriendList(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16635);
        acu_1.ara().c(sb_02);
    }

    public static void showHidePrebuildTeams(ke ke2) {
        boolean bl2 = !DofusArenaClientInstance.yl().aod().a(adc_0.clV);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clV, bl2);
        azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset1vs1List");
        bx_2 bx_22 = DofusArenaClientInstance.yl().kW();
        bx_22.j(bl2);
    }

    public static void openCloseFighterList(ke ke2) {
        if (duq) {
            add_1.aOG().a(aon_0.aYc().getString("error.fight.creation.badClick"), 1090L, 102, 1);
            return;
        }
        if (!add_1.aOG().kR("teamManagementFighterListDialog")) {
            add_1.aOG().a("teamManagementFighterListDialog", oh_2.bq("teamManagementFighterListDialog"), (short)10000);
        } else {
            add_1.aOG().kO("teamManagementFighterListDialog");
            add_1.aOG().kO("teamManagementSelectedFighterDialog");
        }
    }

    public static void showHelp(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16637);
        sb_02.b(string);
        acu_1.ara().c(sb_02);
    }

    public static void increaseList(ke ke2, rf_0 rf_02) {
        rf_02.setOffset(rf_02.getOffset() + 1.0f);
    }

    public static void decreaseList(ke ke2, rf_0 rf_02) {
        rf_02.setOffset(rf_02.getOffset() - 1.0f);
    }

    public static void deactivateInterface() {
        duq = true;
    }

    public static void activateInterface() {
        duq = false;
    }

    public static void loadProfile(hf_0 hf_02) {
        if (hf_02.getSelected()) {
            if (!hf_02.getValue().equals(aon_0.aYc().getString("defaultFightProfile"))) {
                hu_2.ay((String)hf_02.getValue());
                lp_0 lp_02 = new lp_0((String)hf_02.getValue());
                if (br.a(lp_02)) {
                    ArrayList<np_1> arrayList = new ArrayList<np_1>();
                    int[] nArray = lp_02.qi();
                    for (int j = 0; j < nArray.length; ++j) {
                        xj xj2 = (xj)la_0.XJ().pj(nArray[j]);
                        if (xj2 == null) continue;
                        np_1[] np_1Array = xj2.tv();
                        for (int i2 = 0; i2 < np_1Array.length; ++i2) {
                            arrayList.add(np_1Array[i2]);
                        }
                    }
                    np_1[] np_1Array = new np_1[arrayList.size()];
                    for (int j = 0; j < arrayList.size(); ++j) {
                        np_1Array[j] = (np_1)arrayList.get(j);
                    }
                    jk_1.mf().mg().clear();
                    jk_1.mf().mg().a(np_1Array);
                }
            } else {
                jk_1.mf().mg().clear();
                jk_1.mf().mg().a(jn_1.bkb);
                hu_2.ay(aon_0.aYc().getString("defaultFightProfile"));
            }
            azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPresetTournamentList");
        }
    }

    public static void setFighterColorIndex(aGJ aGJ2, abv_1 abv_12) {
        xj xj2 = (xj)aGJ2.getItemValue();
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(abv_12);
        ayd_02.a((byte)xj2.tE());
        switch (xj2.tD()) {
            case 0: {
                ayd_02.f(16650);
                break;
            }
            case 1: {
                ayd_02.f(16651);
                break;
            }
            case 2: {
                ayd_02.f(16652);
            }
        }
        acu_1.ara().c(ayd_02);
    }

    public static void setFighterHairColorIndex(ke ke2, String string, abv_1 abv_12) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(abv_12);
        ayd_02.a(Byte.valueOf(string));
        ayd_02.f(16650);
        acu_1.ara().c(ayd_02);
    }

    public static void setFighterSkinColorIndex(ke ke2, String string, abv_1 abv_12) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(abv_12);
        ayd_02.a(Byte.valueOf(string));
        ayd_02.f(16651);
        acu_1.ara().c(ayd_02);
    }

    public static void setFighterEyeColorIndex(ke ke2, String string, abv_1 abv_12) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(abv_12);
        ayd_02.a(Byte.valueOf(string));
        ayd_02.f(16652);
        acu_1.ara().c(ayd_02);
    }

    public static void changeTeamTab(ke ke2, ud_1 ud_12) {
        na_1 na_12 = ke2.oF();
        if (na_12 instanceof dl_1 && na_12.getParent() != null && na_12.getParent().getParent() != null && na_12.getParent().getParent().getId() != null && na_12.getParent().getParent().getId().equals("teamManagementTabbedContainer")) {
            int n2 = ud_12.getSelectedTabIndex();
            DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.cmb, ud_12.getSelectedTabIndex());
            switch (n2) {
                case 0: 
                case 4: {
                    bs_0.IF().d(xz_0.amc());
                    azs_0.aLV().a((aho_0)xz_0.amc(), xz_0.oT);
                    break;
                }
                case 1: {
                    ArrayList arrayList = (ArrayList)bs_0.IF().getFieldValue("teamManagement.teamPreset1vs1List");
                    if (arrayList.size() > 0) {
                        bs_0.IF().d((zK)arrayList.get(0));
                        break;
                    }
                    bs_0.IF().d(null);
                    break;
                }
                case 2: {
                    ArrayList arrayList = (ArrayList)bs_0.IF().getFieldValue("teamManagement.teamPreset2vs2List");
                    if (arrayList.size() > 0) {
                        bs_0.IF().d((zK)arrayList.get(0));
                        break;
                    }
                    bs_0.IF().d(null);
                    break;
                }
                case 3: {
                    ArrayList arrayList = (ArrayList)bs_0.IF().getFieldValue("teamManagement.teamPresetTournamentList");
                    if (arrayList.size() > 0) {
                        bs_0.IF().d((zK)arrayList.get(0));
                        break;
                    }
                    bs_0.IF().d(null);
                    break;
                }
            }
        }
    }

    public static void setPlayerIndex(aGJ aGJ2) {
        qp_1 qp_12 = new qp_1();
        long l2 = apN.aDK().Ln().getId();
        zK zK2 = bs_0.IF().II();
        short s = zK2.tI();
        ee_2 ee_22 = (ee_2)aGJ2.getItemValue();
        qp_12.j(ee_22.getId());
        qp_12.aS(s);
        qp_12.aT(s);
        if (zK2.afE().du(ee_22.getId()) != apN.aDK().Ln().getId()) {
            qp_12.am(l2);
        } else {
            for (long l3 : zK2.afF().adg()) {
                if (l3 == l2) continue;
                qp_12.am(l3);
            }
        }
        apN.aDK().vJ().b(qp_12);
    }

    public static void selectTournament(aGJ aGJ2) {
        azs_0.aLV().g("selectedTournamentClientInfos", aGJ2.getItemValue());
    }

    public static void selectTeamIcon(aGJ aGJ2) {
        asV asV2 = (asV)azs_0.aLV().getProperty("selectedTeamIcon").getValue();
        asV2.as(((asV)aGJ2.getItemValue()).lV());
        azs_0.aLV().ac("selectedTeamIcon", "textureUrl");
        azs_0.aLV().ac("selectedTeamIcon", "id");
    }

    public static void selectTeamBackground(aGJ aGJ2) {
        asV asV2 = (asV)azs_0.aLV().getProperty("selectedTeamBackground").getValue();
        asV2.as(((asV)aGJ2.getItemValue()).lV());
        azs_0.aLV().ac("selectedTeamBackground", "textureUrl");
        azs_0.aLV().ac("selectedTeamBackground", "id");
    }

    public static void selectTeamIconColorIndex(ke ke2, String string) {
        asV asV2 = (asV)azs_0.aLV().getProperty("selectedTeamIcon").getValue();
        asV2.aT(Byte.parseByte(string));
        azs_0.aLV().ac("selectedTeamIcon", "color");
    }

    public static void selectTeamBackgroundColorIndex(ke ke2, String string) {
        asV asV2 = (asV)azs_0.aLV().getProperty("selectedTeamBackground").getValue();
        asV2.aT(Byte.parseByte(string));
        azs_0.aLV().ac("selectedTeamBackground", "color");
    }

    public static void openCloseTeamLoadDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20125);
        acu_1.ara().c(sb_02);
    }

    public static void loadTeam(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20126);
        sb_02.b((String)aGJ2.getItemValue());
        acu_1.ara().c(sb_02);
    }

    public static void loadTeam(ke ke2, rf_0 rf_02) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20126);
        sb_02.b((String)rf_02.getSelectedValue());
        acu_1.ara().c(sb_02);
    }

    public static void saveTeam(ke ke2, zK zK2) {
        abt_0 abt_02 = new abt_0();
        abt_02.f(20127);
        abt_02.b(zK2);
        acu_1.ara().c(abt_02);
    }

    public static void openCloseUnlockedColors(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20130);
        sb_02.a(Byte.parseByte(string));
        acu_1.ara().c(sb_02);
    }
}

