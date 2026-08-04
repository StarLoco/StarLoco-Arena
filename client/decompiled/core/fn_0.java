/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from fn
 */
public class fn_0 {
    public static final String PACKAGE = "dofusarena.tournaments";

    public static void increaseList(ke ke2, rf_0 rf_02) {
        rf_02.setOffset(rf_02.getOffset() + 1.0f);
    }

    public static void decreaseList(ke ke2, rf_0 rf_02) {
        rf_02.setOffset(rf_02.getOffset() - 1.0f);
    }

    public static final void registerTournament(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20071);
        acu_1.ara().c(sb_02);
    }

    public static void selectTournament(aGJ aGJ2) {
        qr_0 qr_02 = (qr_0)aGJ2.getItemValue();
        azs_0.aLV().g("selectedTournamentEvent", qr_02);
    }

    public static void showCoachCardInfosEvolution(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof wy_2) {
            wy_2 wy_22 = (wy_2)object;
            ia_2 ia_22 = new ia_2();
            ia_22.b(wy_22);
            ia_22.f(16700);
            acu_1.ara().c(ia_22);
        }
    }

    public static void hideCoachCardInfosEvolution(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16701);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseCustomFightDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20160);
        acu_1.ara().c(sb_02);
    }

    public static void changeEditableCurrentHour(hf_0 hf_02, String string) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            Integer n2 = (Integer)hf_02.getValue();
            aob_22.g(n2, Integer.parseInt(string), false);
        }
    }

    public static void changeEditableCurrentHourForPlayer(hf_0 hf_02, String string) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            Integer n2 = (Integer)hf_02.getValue();
            aob_22.g(n2, Integer.parseInt(string), true);
        }
    }

    public static void changeEditableCurrentEndHour(hf_0 hf_02, String string) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            Integer n2 = (Integer)hf_02.getValue();
            aob_22.cr(n2, Integer.parseInt(string));
        }
    }

    public static void changeEditableCurrentMinute(hf_0 hf_02, String string) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            Integer n2 = (Integer)hf_02.getValue();
            aob_22.cs(n2, Integer.parseInt(string));
        }
    }

    public static void changeEditableCurrentEndMinute(hf_0 hf_02, String string) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            Integer n2 = (Integer)hf_02.getValue();
            aob_22.ct(n2, Integer.parseInt(string));
        }
    }

    public static void changeEditableCurrentMonth(hf_0 hf_02, String string) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            zw_0 zw_02 = (zw_0)hf_02.getValue();
            aob_22.cp(zw_02.Hb(), Integer.parseInt(string));
        }
    }

    public static void changeEditableCurrentYear(hf_0 hf_02, String string) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            Integer n2 = (Integer)hf_02.getValue();
            aob_22.cq(n2, Integer.parseInt(string));
        }
    }

    public static void changeEditableCurrentDay(hf_0 hf_02, String string) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            Integer n2 = (Integer)hf_02.getValue();
            aob_22.co(n2, Integer.parseInt(string));
        }
    }

    public static void changeEditableCurrentDay(ke ke2, HW hW) {
        aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
        aob_22.co(hW.getDayOver(), 0);
    }

    public static void useGameDate(ke ke2, String string) {
        aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
        aob_22.pB(Integer.parseInt(string));
    }

    public static void selectTournamentDefinition(hf_0 hf_02) {
        if (hf_02.getSelected()) {
            aob_2 aob_22 = (aob_2)azs_0.aLV().kc("defaultCalendar");
            Integer n2 = (Integer)hf_02.getValue();
            aob_22.a(n2);
        }
    }

    public static void openCloseTournamentCreationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20077);
        acu_1.ara().c(sb_02);
    }

    public static void createTournament(ke ke2, Ur ur) {
        if (ur.isValid()) {
            String string = ur.getProperty("tournamentCreationName").getString();
            String string2 = ur.getProperty("tournamentCreationDescription").getString();
            asE asE2 = asE.aFE();
            asE2.setName(string);
            asE2.setDescription(string2);
            acu_1.ara().c(asE2);
        } else {
            add_1.aOG().a(aon_0.aYc().getString("errorNoNameOrDescription"), 1090L, 102, 1);
        }
    }

    public static void showTournamentFightParameter(ke ke2, ai_2 ai_22) {
        qu_0.popup(ke2, ai_22);
    }

    public static void hideTournamentFightParameter(ke ke2, ai_2 ai_22) {
        qu_0.closePopup(ke2, ai_22);
    }

    public static boolean validateTournamentForm(Ur ur) {
        ur.agN();
        return !ur.getProperty("tournamentCreationName").isEmpty() && !ur.getProperty("tournamentCreationDescription").isEmpty();
    }

    public static void openCloseCustomFightProfileDetailsDialog(ke ke2) {
        if (!add_1.aOG().kR("customFightProfileDetailsDialog")) {
            add_1.aOG().a("customFightProfileDetailsDialog", oh_2.bq("customFightProfileDetailsDialog"), 257L, (short)20000);
        } else {
            add_1.aOG().kO("customFightProfileDetailsDialog");
        }
    }

    public static void openCloseTournamentDetailsDialog(ke ke2, qr_0 qr_02) {
        if (!add_1.aOG().kR("customFightProfileDetailsDialog")) {
            azs_0.aLV().g("selectedTournamentEvent", qr_02);
            add_1.aOG().a("customFightProfileDetailsDialog", oh_2.bq("customFightProfileDetailsDialog"), 257L, (short)20000);
        }
    }

    public static void openCloseTournamentTreeDialog(ke ke2, qr_0 qr_02) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20079);
        sb_02.e(qr_02.fx());
        acu_1.ara().c(sb_02);
        azs_0.aLV().g("selectedTournamentEvent", qr_02);
    }

    public static void closeTournamentTreeDialog(ke ke2) {
        add_1.aOG().kO("tournamentTreeDialog");
    }

    public static void showNextTree(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20069);
        sb_02.a((byte)1);
        acu_1.ara().c(sb_02);
    }

    public static void showPreviousTree(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20069);
        sb_02.a((byte)-1);
        acu_1.ara().c(sb_02);
    }

    public static void showCoachTree(ke ke2, UV uV) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20068);
        sb_02.b(uV.getText());
        acu_1.ara().c(sb_02);
    }

    public static void showRuleDetails(ke ke2, ai_2 ai_22) {
        qu_0.popup(ke2, ai_22);
    }

    public static void hideRuleDetails(ke ke2, ai_2 ai_22) {
        qu_0.closePopup(ke2, ai_22);
    }

    public static void openCloseTournamentAdminCreationDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20067);
        acu_1.ara().c(sb_02);
    }

    public static void validateAdminCreation(ke ke2, UV uV) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20067);
        sb_02.g(Integer.parseInt(uV.getText()));
        acu_1.ara().c(sb_02);
    }

    public static void selectProLeagueDefinition(aGJ aGJ2) {
        atk_0 atk_02;
        aob_2 aob_22 = (aob_2)azs_0.aLV().getProperty("defaultCalendar").getValue();
        jg_0 jg_02 = aob_22.aXT();
        boolean bl2 = (atk_02 = (atk_0)aGJ2.getItemValue()).isSelected();
        atk_02.setSelected(!bl2);
        if (!bl2) {
            jg_02.add(atk_02.getId());
        } else {
            jg_02.bv(jg_02.indexOf(atk_02.getId()));
        }
        azs_0.aLV().a((aho_0)atk_02, "isSelected");
    }

    public static void setEventPeriod(ke ke2, String string) {
        aob_2 aob_22 = (aob_2)azs_0.aLV().getProperty("defaultCalendar").getValue();
        aob_22.lN(string);
    }
}

