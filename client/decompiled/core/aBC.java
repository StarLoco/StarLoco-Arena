/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

public class aBC {
    public static final String PACKAGE = "dofusarena.coachManagement";

    public static void setCoachSex(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        sb_02.a(Byte.valueOf(string));
        sb_02.f(16401);
        acu_1.ara().c(sb_02);
    }

    public static void setCoachHairColorIndex(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        sb_02.a(Byte.valueOf(string));
        sb_02.f(16402);
        acu_1.ara().c(sb_02);
    }

    public static void setCoachSkinColorIndex(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        sb_02.a(Byte.valueOf(string));
        sb_02.f(16403);
        acu_1.ara().c(sb_02);
    }

    public static void setNextCoachDirection(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16405);
        acu_1.ara().c(sb_02);
    }

    public static void setPreviousCoachDirection(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16406);
        acu_1.ara().c(sb_02);
    }

    public static void createRandomCoach(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16404);
        acu_1.ara().c(sb_02);
    }

    public static void setInventoryNextDirection(abd_1 abd_12, lj_1 lj_12) {
        if (abd_12.getButton() != 1) {
            return;
        }
        int n2 = lj_12.getDirection() + 1;
        lj_12.setDirection(n2 %= qc_0.acP().length);
    }

    public static void setInventoryPreviousDirection(abd_1 abd_12, lj_1 lj_12) {
        if (abd_12.getButton() != 1) {
            return;
        }
        int n2 = lj_12.getDirection() - 1;
        if (n2 < 0) {
            n2 = qc_0.acP().length - 1;
        }
        lj_12.setDirection(n2);
    }

    public static boolean validateCoachCreationForm(Ur ur) {
        ur.agN();
        afl_0 afl_02 = ur.getProperty("localCoach");
        if (afl_02 != null) {
            String string = afl_02.hV("name");
            if (string != null && string.length() <= 20 && aet_0.dDM.matcher(string).matches() && avQ.jR(string)) {
                return true;
            }
            add_1.aOG().f(aon_0.aYc().getString("error.coachCreation.invalidName"), 5, 1);
            return false;
        }
        return false;
    }

    public static void createCoach(ke ke2, Ur ur) {
        if (ke2.aV() == qe_1.bFB || ke2.aV() == qe_1.bFm && ((aqG)ke2).getKeyCode() == 10) {
            if (ur.isValid()) {
                Object object;
                afl_0 afl_02 = ur.getProperty("localCoach");
                if (afl_02 != null && (object = afl_02.getValue()) instanceof sj_1) {
                    apm_1 apm_12 = new apm_1();
                    apm_12.b((sj_1)object);
                    acu_1.ara().c(apm_12);
                }
            } else {
                System.out.println("Formulaire invalide");
            }
        }
    }

    public static void showCoachCardInfos(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof wy_2) {
            ia_2 ia_22 = new ia_2();
            ia_22.b((wy_2)object);
            ia_22.f(16700);
            acu_1.ara().c(ia_22);
        }
    }

    public static void hideCoachCardInfos(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16701);
        acu_1.ara().c(sb_02);
    }

    public static void selectEquipmentTypeFilter(vY vY2, sj_1 sj_12, String string) {
        ct_2 ct_22 = new ct_2();
        ct_22.a(sj_12);
        ct_22.g(Integer.valueOf(string));
        ct_22.b(vY2.isSelected());
        ct_22.f(16704);
        acu_1.ara().c(ct_22);
    }

    public static void selectSetFilter(ke ke2, sj_1 sj_12) {
        hf_0 hf_02;
        if (ke2.aV() == qe_1.bFp && (hf_02 = (hf_0)ke2).getSelected() && hf_02.getValue() instanceof fe_1) {
            tc_0 tc_02 = new tc_0();
            tc_02.a(sj_12);
            tc_02.a((fe_1)hf_02.getValue());
            tc_02.f(16711);
            acu_1.ara().c(tc_02);
        }
    }

    public static void selectCostFilter(ke ke2, sj_1 sj_12) {
        hf_0 hf_02;
        if (ke2.aV() == qe_1.bFp && (hf_02 = (hf_0)ke2).getValue() instanceof ij) {
            ct_2 ct_22 = new ct_2();
            ct_22.a(sj_12);
            ct_22.b(hf_02.getValue().toString());
            ct_22.f(16714);
            acu_1.ara().c(ct_22);
        }
    }

    public static void selectPetTypeFilter(ke ke2, sj_1 sj_12, String string) {
        ct_2 ct_22 = new ct_2();
        ct_22.a(sj_12);
        ct_22.g(Integer.valueOf(string));
        ct_22.f(16712);
        acu_1.ara().c(ct_22);
    }

    public static void selectAllEquipmentTypeFilter(ke ke2, sj_1 sj_12) {
        ct_2 ct_22 = new ct_2();
        ct_22.a(sj_12);
        ct_22.f(16705);
        acu_1.ara().c(ct_22);
    }

    public static void selectAllPetTypeFilter(ke ke2, sj_1 sj_12) {
        ct_2 ct_22 = new ct_2();
        ct_22.a(sj_12);
        ct_22.f(16713);
        acu_1.ara().c(ct_22);
    }

    public static boolean validateEquipmentDrop(kn_1 kn_12, Object object, kn_1 kn_13, Object object2, Object object3, sj_1 sj_12, String string) {
        return object3 != null && object3 instanceof wy_2 && ((wy_2)object3).tj() != aMK.dYu;
    }

    public static void dropEquipment(aiU aiU2, sj_1 sj_12, String string) {
        wy_2 wy_22;
        Object object = aiU2.getValue();
        if (object != null && object instanceof wy_2 && (wy_22 = (wy_2)object) != null) {
            ia_2 ia_22 = new ia_2();
            ia_22.a(sj_12);
            ia_22.b(wy_22);
            ia_22.k((short)-1);
            ia_22.f(16702);
            acu_1.ara().c(ia_22);
        }
    }

    public static void dragEquipment(aly_2 aly_22, sj_1 sj_12, String string) {
        Object object = aly_22.getValue();
        if (object != null && object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            if (aly_22.oF() != null) {
                ia_2 ia_22 = new ia_2();
                ia_22.a(sj_12);
                ia_22.b(wy_22);
                ia_22.k(Short.valueOf(string));
                ia_22.f(16703);
                acu_1.ara().c(ia_22);
            }
        }
    }

    public static void equip(aGJ aGJ2, sj_1 sj_12) {
        Object object = aGJ2.getItemValue();
        if (object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            wy_2 wy_23 = (wy_2)sj_12.yD().bW(-wy_22.jf());
            if (sj_12.yD().c(wy_22) || wy_23 != null && wy_23.hG() > 0) {
                if (add_1.aOG().kR("exchangeDialog")) {
                    afl_0 afl_02 = azs_0.aLV().getProperty("exchange.cardTrade");
                    if (afl_02 != null) {
                        rg_0 rg_02 = new rg_0();
                        rg_02.f(16807);
                        CG cG = (CG)afl_02.getValue();
                        rg_02.cm(cG.getId());
                        rg_02.a((wy_2)object);
                        acu_1.ara().c(rg_02);
                    }
                } else {
                    ia_2 ia_22 = new ia_2();
                    ia_22.a(sj_12);
                    ia_22.b(wy_22);
                    ia_22.k((short)-1);
                    ia_22.f(16702);
                    acu_1.ara().c(ia_22);
                }
            }
        }
    }

    public static void changeInstance(aGJ aGJ2) {
        sj_1 sj_12 = apN.aDK().Ln();
        wy_2 wy_22 = (wy_2)aGJ2.getItemValue();
        if (wy_22 != null && wy_22.tj() == aMK.dYu) {
            xj xj2 = (xj)wy_22.NR();
            if (xj2 != null && sj_12.yD().c(wy_22) || sj_12.yD().bW(-wy_22.jf()) != null) {
                Gs gs = new Gs();
                gs.fG(xj2.getId());
                apN.aDK().vJ().b(gs);
                auv_0.ek(true);
                ia_1.TJ().TK();
                if (add_1.aOG().kR("zaapDialog")) {
                    apN.aDK().b(aoa_2.aYv());
                    azs_0.aLV().g("coachManagement.selectedCard", (Object)null);
                }
            }
        } else {
            add_1.aOG().f(aon_0.aYc().getString("error.cardIsNotZaap"), 102, 1);
        }
    }

    public static void equipSet(ke ke2, sj_1 sj_12) {
        ct_2 ct_22 = new ct_2();
        ct_22.a(sj_12);
        ct_22.f(16715);
        acu_1.ara().c(ct_22);
    }

    public static void unequip(aGJ aGJ2, sj_1 sj_12, String string) {
        Object object = aGJ2.getItemValue();
        if (object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            ia_2 ia_22 = new ia_2();
            ia_22.a(sj_12);
            ia_22.b(wy_22);
            ia_22.k(Short.valueOf(string));
            ia_22.f(16703);
            acu_1.ara().c(ia_22);
        }
    }

    public static void deleteAndUpdateEquipment(aiU aiU2, sj_1 sj_12) {
        Object object = aiU2.getValue();
        if (object != null && object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            if (aiU2.oF() != null) {
                ia_2 ia_22 = new ia_2();
                ia_22.a(sj_12);
                ia_22.b(wy_22);
                ia_22.f(16717);
                acu_1.ara().c(ia_22);
            }
        }
    }

    public static void dropEquipment(aiU aiU2, sj_1 sj_12) {
        Object object = aiU2.getValue();
        if (object != null && object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            ia_2 ia_22 = new ia_2();
            ia_22.a(sj_12);
            ia_22.b(wy_22);
            ia_22.f(16702);
            acu_1.ara().c(ia_22);
        }
    }

    public static void dragEquipment(aly_2 aly_22, sj_1 sj_12) {
        Object object = aly_22.getValue();
        if (object != null && object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            if (aly_22.oF() != null) {
                ia_2 ia_22 = new ia_2();
                ia_22.a(sj_12);
                ia_22.b(wy_22);
                ia_22.f(16703);
                acu_1.ara().c(ia_22);
            }
        }
    }

    public static void useSpecialCard(aGJ aGJ2, sj_1 sj_12) {
        Object object = aGJ2.getItemValue();
        if (object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            if (sj_12.aQn().bW(wy_22.jf()) == null && sj_12.aQn().bW(-wy_22.jf()) == null) {
                return;
            }
            if (wy_22.tj() == aMK.dYv) {
                switch (wy_22.jf()) {
                    case 214: {
                        sb_0 sb_02 = new sb_0();
                        sb_02.f(16431);
                        acu_1.ara().c(sb_02);
                        apN.aDK().b(agn_0.awo());
                        break;
                    }
                    default: {
                        add_1.aOG().f(aon_0.aYc().getString("error.actionNotValidForThisCardType"), 5, 1);
                        break;
                    }
                }
            } else if (wy_22.tj() == aMK.dYC) {
                sb_0 sb_03 = new sb_0();
                sb_03.f(16443);
                sb_03.g(wy_22.jf());
                acu_1.ara().c(sb_03);
            } else if (wy_22.tj() == aMK.dYE) {
                sb_0 sb_04 = new sb_0();
                sb_04.f(16442);
                sb_04.g(wy_22.jf());
                acu_1.ara().c(sb_04);
            }
        }
    }

    public static void openCloseFusionLaboratoryDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20013);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseInventoryStatisticsDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20013);
        acu_1.ara().c(sb_02);
    }

    public static void selectNextSet(ke ke2, ud_1 ud_12) {
        aij_0.aUF().t(ud_12);
    }

    public static void selectPreviousSet(ke ke2, ud_1 ud_12) {
        aij_0.aUF().u(ud_12);
    }

    public static void changeTomeTab(vY vY2) {
        int n2 = Integer.parseInt(((dl_1)vY2.oF()).getValue());
        String string = "";
        if (add_1.aOG().kR("cardBookDialog")) {
            string = "cardBookDialog.inventory";
        } else if (add_1.aOG().kR("cardBookTutorialDialog")) {
            string = "cardBookTutorialDialog.inventory";
        } else if (add_1.aOG().kR("cardMasterDialog")) {
            string = "cardMasterDialog.inventory";
        } else if (add_1.aOG().kR("demonIIDialog")) {
            string = "demonIIDialog.inventory";
        } else if (add_1.aOG().kR("demonAffiliationDialog")) {
            string = "demonAffiliationDialog.inventory";
        } else if (add_1.aOG().kR("exchangeDialog")) {
            string = "exchangeDialog.inventory";
        } else if (add_1.aOG().kR("mailboxDialog")) {
            string = "mailboxDialog.inventory";
        } else if (add_1.aOG().kR("fusionLabDialog")) {
            string = "fusionLabDialog.inventory";
        }
        switch (n2) {
            case 1: {
                ((aht_1)add_1.aOG().azj().lh(string).R("cheapList")).setVisible(true);
                ((aht_1)add_1.aOG().azj().lh(string).R("cheapSetDetails")).setVisible(false);
                break;
            }
            case 2: {
                ((aht_1)add_1.aOG().azj().lh(string).R("expensiveList")).setVisible(true);
                ((aht_1)add_1.aOG().azj().lh(string).R("expensiveSetDetails")).setVisible(false);
                break;
            }
            case 3: {
                ((aht_1)add_1.aOG().azj().lh(string).R("specialList")).setVisible(true);
                ((aht_1)add_1.aOG().azj().lh(string).R("specialSetDetails")).setVisible(false);
                break;
            }
            case 4: {
                ((aht_1)add_1.aOG().azj().lh(string).R("evolutionList")).setVisible(true);
                ((aht_1)add_1.aOG().azj().lh(string).R("evolutionSetDetails")).setVisible(false);
                break;
            }
            case 5: {
                ((aht_1)add_1.aOG().azj().lh(string).R("fightList")).setVisible(true);
                ((aht_1)add_1.aOG().azj().lh(string).R("fightSetDetails")).setVisible(false);
            }
        }
        aij_0.aUF().oL(n2);
    }

    public static void addCardToTome(ke ke2, wy_2 wy_22) {
        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("questionAddCardToTome", wy_22.getName()), 24L, 102, 0);
        r_02.a(new ST(wy_22));
    }

    public static void goToSet(aGJ aGJ2, aht_1 aht_12, aht_1 aht_13, String string) {
        int n2 = Integer.parseInt(string);
        aht_12.setVisible(false);
        aht_13.setVisible(true);
        Object object = aGJ2.getItemValue();
        if (object instanceof fe_1) {
            fe_1 fe_12 = (fe_1)object;
            aij_0.aUF().a(fe_12, n2);
        }
    }

    public static void goToSetList(ke ke2, ud_1 ud_12, String string) {
        int n2 = Integer.parseInt(string);
        ud_12.setSelectedTabIndex(n2);
    }

    public static void goToSetList(ke ke2, aht_1 aht_12, aht_1 aht_13) {
        aht_12.setVisible(true);
        aht_13.setVisible(false);
    }

    public static void goToFightList(ke ke2, ud_1 ud_12, String string) {
        int n2 = Integer.parseInt(string);
        ud_12.setSelectedTabIndex(n2);
    }

    public static void goToSetList(ke ke2, ud_1 ud_12, String string, aht_1 aht_12, aht_1 aht_13) {
        aht_12.setVisible(true);
        aht_13.setVisible(false);
        int n2 = Integer.parseInt(string);
        ud_12.setSelectedTabIndex(n2);
    }

    public static void showCoachCardInfosInTome(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            sj_1 sj_12 = apN.aDK().Ln();
            if (sj_12.yD().c(wy_22) || sj_12.aQm().contains(wy_22.jf()) || sj_12.yD().bW(-wy_22.jf()) != null) {
                ia_2 ia_22 = new ia_2();
                ia_22.b(wy_22);
                ia_22.f(16700);
                acu_1.ara().c(ia_22);
            }
        }
    }

    public static void showSpellCardInTome(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof yp_2) {
            yp_2 yp_22 = (yp_2)object;
            da_1 da_12 = new da_1();
            da_12.a(yp_22);
            da_12.f(16720);
            acu_1.ara().c(da_12);
        }
    }

    public static void showItemList(abd_1 abd_12, String string) {
        aMK aMK2 = aMK.pq(Integer.parseInt(string));
        apN.aDK().Ln().a(aMK2);
        if (abd_12.getButton() == 3) {
            add_1.aOG().a("coachQuickEquipmentDialog", oh_2.bq("coachQuickEquipmentDialog"), null, false, abd_12.getScreenX() + 40, abd_12.getScreenY(), 257L, (short)20000);
        }
    }

    public static void closeQuickEquipment(ke ke2) {
        add_1.aOG().kO("coachQuickEquipmentDialog");
    }

    public static void showItemBonus(ke ke2, ai_2 ai_22) {
        if (DofusArenaClientInstance.yl().aod().a(adc_0.clX)) {
            qu_0.popup(ke2, ai_22);
        }
    }

    public static void hideItemBonus(ke ke2, ai_2 ai_22) {
        if (DofusArenaClientInstance.yl().aod().a(adc_0.clX)) {
            qu_0.closePopup(ke2, ai_22);
        }
    }

    public static void equipAndClose(aGJ aGJ2, sj_1 sj_12) {
        Object object = aGJ2.getItemValue();
        if (object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            wy_2 wy_23 = (wy_2)sj_12.yD().bW(-wy_22.jf());
            if (sj_12.yD().c(wy_22) || wy_23 != null && wy_23.hG() > 0) {
                if (add_1.aOG().kR("exchangeDialog")) {
                    afl_0 afl_02 = azs_0.aLV().getProperty("exchange.cardTrade");
                    if (afl_02 != null) {
                        rg_0 rg_02 = new rg_0();
                        rg_02.f(16807);
                        CG cG = (CG)afl_02.getValue();
                        rg_02.cm(cG.getId());
                        rg_02.a((wy_2)object);
                        acu_1.ara().c(rg_02);
                    }
                } else {
                    ia_2 ia_22 = new ia_2();
                    ia_22.a(sj_12);
                    ia_22.b(wy_22);
                    ia_22.k((short)-1);
                    ia_22.f(16702);
                    acu_1.ara().c(ia_22);
                }
            }
            add_1.aOG().kO("coachQuickEquipmentDialog");
        }
    }

    public static void switchCoachAndEffects(ke ke2, aht_1 aht_12, aht_1 aht_13) {
        aht_13.setVisible(!aht_13.getVisible());
        aht_12.setVisible(!aht_12.getVisible());
    }

    public static void showEffectDetails(aGJ aGJ2, ai_2 ai_22) {
        qu_0.popup(aGJ2, ai_22);
    }

    public static void hideEffectDetails(aGJ aGJ2, ai_2 ai_22) {
        qu_0.closePopup(aGJ2, ai_22);
    }

    public static void showBreedDetails(ke ke2, aht_1 aht_12, aht_1 aht_13, String string) {
        aht_12.setVisible(false);
        aht_13.setVisible(true);
        sb_0 sb_02 = new sb_0();
        sb_02.f(16719);
        sb_02.a(Byte.parseByte(string));
        acu_1.ara().c(sb_02);
    }

    public static void showSummonDetails(ke ke2, aht_1 aht_12, aht_1 aht_13, String string) {
        aht_12.setVisible(false);
        aht_13.setVisible(true);
        sb_0 sb_02 = new sb_0();
        sb_02.f(16721);
        sb_02.a(Byte.parseByte(string));
        acu_1.ara().c(sb_02);
    }

    public static void backToBreedList(ke ke2, aht_1 aht_12, aht_1 aht_13) {
        aht_12.setVisible(true);
        aht_13.setVisible(false);
    }

    public static void setFighterSex(ke ke2, abv_1 abv_12, String string) {
        abb_2 abb_22 = new abb_2();
        abb_22.b(abv_12);
        abb_22.S(Byte.valueOf(string));
        abb_22.f(16610);
        acu_1.ara().c(abb_22);
    }

    public static void changeFightTab(vY vY2) {
        dl_1 dl_12 = (dl_1)vY2.oF();
        if (!dl_12.getGroupId().equals("fighterSex")) {
            int n2 = Integer.parseInt(dl_12.getValue());
            String string = "";
            if (add_1.aOG().kR("cardBookDialog")) {
                string = "cardBookDialog.fight";
            } else if (add_1.aOG().kR("cardBookTutorialDialog")) {
                string = "cardBookTutorialDialog.fight";
            }
            switch (n2) {
                case 1: {
                    ((aht_1)add_1.aOG().azj().lh(string).R("breedList")).setVisible(true);
                    ((aht_1)add_1.aOG().azj().lh(string).R("breedDetails")).setVisible(false);
                    break;
                }
                case 2: {
                    ((aht_1)add_1.aOG().azj().lh(string).R("summonList")).setVisible(true);
                    ((aht_1)add_1.aOG().azj().lh(string).R("summonDetails")).setVisible(false);
                    break;
                }
            }
        }
    }

    public static void showHideEvolutionBonus(ke ke2) {
        boolean bl2 = !DofusArenaClientInstance.yl().aod().a(adc_0.clX);
        DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clX, bl2);
    }
}

