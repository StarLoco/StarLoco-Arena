/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aIO
 */
public class aio_1 {
    public static final String PACKAGE = "dofusarena.evolution";

    public static void launchEvolutionFight(ke ke2) {
        ahn ahn2 = new ahn();
        ahn2.b(xz_0.amc());
        ahn2.d(B.V().Y());
        acu_1.ara().c(ahn2);
    }

    public static void addEquipementToPosition(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23052);
        sb_02.g((Integer)aGJ2.getItemValue());
        acu_1.ara().c(sb_02);
    }

    public static void removeEquipementAtPosition(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23053);
        sb_02.bF((Short)aGJ2.getItemValue());
        acu_1.ara().c(sb_02);
    }

    public static void changeItemCardType(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        byte by = Byte.valueOf(string);
        by = (byte)(by + 1);
        sb_02.a(by);
        sb_02.f(16638);
        acu_1.ara().c(sb_02);
    }

    public static void changeFighterStatus(aiU aiU2) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.f(23055);
        ayd_02.b((ee_2)aiU2.getValue());
        acu_1.ara().c(ayd_02);
    }

    public static void becomeALegend(ke ke2) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.f(23068);
        ee_2 ee_22 = (ee_2)azs_0.aLV().getProperty("teamManagement.editableFighter").getValue();
        ayd_02.b(ee_22);
        acu_1.ara().c(ayd_02);
    }

    public static void changeFighterStatus(ke ke2) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.f(23055);
        ee_2 ee_22 = (ee_2)azs_0.aLV().getProperty("teamManagement.editableFighter").getValue();
        ayd_02.b(ee_22);
        acu_1.ara().c(ayd_02);
    }

    public static void useItemOnFighter(aGJ aGJ2) {
    }

    public static void showPopup(abd_1 abd_12, ai_2 ai_22) {
        qu_0.popup(abd_12, ai_22);
    }

    public static void hidePopup(abd_1 abd_12, ai_2 ai_22) {
        qu_0.closePopup(abd_12, ai_22);
    }

    public static void selectFighter(aGJ aGJ2) {
        afl_0 afl_02 = azs_0.aLV().getProperty("selectedConsumableCard");
        if (afl_02 == null) {
            azs_0.aLV().g("teamManagement.editableFighter", aGJ2.getItemValue());
        } else {
            avP avP2 = new avP();
            avP2.f(23056);
            avP2.a((wy_2)afl_02.getValue());
            avP2.b((ee_2)aGJ2.getItemValue());
            acu_1.ara().c(avP2);
            azs_0.aLV().kb("selectedConsumableCard");
        }
    }

    public static void closeFighterCreationTutorialDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23058);
        acu_1.ara().c(sb_02);
    }

    public static void selectPreviousSet(ke ke2) {
        aij_0.aUF().aUH();
    }

    public static void selectNextSet(ke ke2) {
        aij_0.aUF().aUI();
    }

    public static void openCloseSphereBoard(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.b(ee_22);
        ayd_02.f(23060);
        acu_1.ara().c(ayd_02);
    }

    public static void editFighter(ke ke2, ee_2 ee_22) {
        ayd_0 ayd_02 = new ayd_0();
        ayd_02.f(23062);
        ayd_02.b(ee_22);
        acu_1.ara().c(ayd_02);
    }

    public static void closeFighterEvolutionEquipmentDialog(ke ke2, abv_1 abv_12) {
        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("question.saveEditableFighter", abv_12.getName()), 1176L, 102, 1);
        r_02.a(new aJp(abv_12));
    }

    public static void selectConsumableSet(aGJ aGJ2, aht_1 aht_12, aht_1 aht_13) {
        aht_12.setVisible(false);
        aht_13.setVisible(true);
        Object object = aGJ2.getItemValue();
        if (object instanceof fe_1) {
            fe_1 fe_12 = (fe_1)object;
            aij_0.aUF().c(fe_12);
        }
    }

    public static void showCoachCardInfosEvolution(aGJ aGJ2) {
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

    public static void hideCoachCardInfosEvolution(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16701);
        acu_1.ara().c(sb_02);
    }

    public static void goBackToList(ke ke2, aht_1 aht_12, aht_1 aht_13) {
        aht_12.setVisible(true);
        aht_13.setVisible(false);
    }

    public static void selectCard(aGJ aGJ2) {
        wy_2 wy_22 = (wy_2)aGJ2.getItemValue();
        sj_1 sj_12 = apN.aDK().Ln();
        if (wy_22 != null && (sj_12.yD().c(wy_22) || sj_12.aQm().contains(wy_22.jf()) || sj_12.yD().bW(-wy_22.jf()) != null)) {
            azs_0.aLV().g("selectedConsumableCard", wy_22);
            String string = (String)wy_22.getFieldValue("consumableTypeIcon");
            if (string != null) {
                mb_0.Yl().a(string, null, 10, -30, BT.aJT);
            }
        }
    }

    public static void hideMouseImage(ke ke2) {
        mb_0.Yl().hide();
    }

    public static void setTournamentReadyForFight(ke ke2) {
        amo_0 amo_02 = new amo_0();
        amo_02.b(xz_0.amc());
        acu_1.ara().c(amo_02);
    }
}

