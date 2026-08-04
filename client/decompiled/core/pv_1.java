/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from pV
 */
public class pv_1 {
    public static final String PACKAGE = "dofusarena.customFight";

    public static void selectSpellCategory(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof sd_1) {
            azs_0.aLV().g("selectedSpellCategory", object);
        }
    }

    public static void selectBreedCategory(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof sd_1) {
            azs_0.aLV().g("selectedBreedCategory", object);
        }
    }

    public static void selectEquipmentCategory(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof sd_1) {
            azs_0.aLV().g("selectedEquipmentCategory", object);
        }
    }

    public static void selectFightBudgetCategory(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof sd_1) {
            azs_0.aLV().g("selectedFightBudgetCategory", object);
        }
    }

    public static void selectFightTimeCategory(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof sd_1) {
            azs_0.aLV().g("selectedFightTimeCategory", object);
        }
    }

    public static void selectArenaCategory(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof sd_1) {
            azs_0.aLV().g("selectedArenaCategory", object);
        }
    }

    public static void selectFightOption(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object instanceof WN) {
            WN wN = (WN)object;
            boolean bl2 = (Boolean)wN.getFieldValue("forbidden");
            boolean bl3 = (Boolean)wN.getFieldValue("activated");
            if (bl3) {
                if (!bl2) {
                    boolean bl4 = jk_1.mf().me().contains(wN);
                    if (bl4) {
                        jk_1.mf().a(wN);
                    } else {
                        jk_1.mf().b(wN);
                    }
                    sd_1 sd_12 = (sd_1)azs_0.aLV().getProperty("selectedSpellCategory").getValue();
                    sd_1 sd_13 = (sd_1)azs_0.aLV().getProperty("selectedBreedCategory").getValue();
                    sd_1 sd_14 = (sd_1)azs_0.aLV().getProperty("selectedEquipmentCategory").getValue();
                    sd_1 sd_15 = (sd_1)azs_0.aLV().getProperty("selectedFightBudgetCategory").getValue();
                    sd_1 sd_16 = (sd_1)azs_0.aLV().getProperty("selectedFightTimeCategory").getValue();
                    azs_0.aLV().a((aho_0)sd_13, "fightRules");
                    azs_0.aLV().a((aho_0)sd_12, "fightRules");
                    azs_0.aLV().a((aho_0)sd_14, "fightRules");
                    azs_0.aLV().a((aho_0)sd_15, "fightRules");
                    azs_0.aLV().a((aho_0)sd_16, "fightRules");
                    azs_0.aLV().a((aho_0)jk_1.mf(), "summary");
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("errorRuleForbidden"), 1090L, 102, 1);
                }
            } else {
                add_1.aOG().a(aon_0.aYc().getString("errorCardsMissing"), 1090L, 102, 1);
            }
        }
    }

    public static void createTournament(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20072);
        acu_1.ara().c(sb_02);
    }

    public static void showRuleDetails(ke ke2, ai_2 ai_22) {
        qu_0.popup(ke2, ai_22);
    }

    public static void hideRuleDetails(ke ke2, ai_2 ai_22) {
        qu_0.closePopup(ke2, ai_22);
    }

    public static void openCloseCustomFightProfileLoadingDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20073);
        acu_1.ara().c(sb_02);
    }

    public static void openCloseCustomFightProfileSavingDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20075);
        acu_1.ara().c(sb_02);
    }

    public static void loadProfile(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20074);
        sb_02.b((String)aGJ2.getItemValue());
        acu_1.ara().c(sb_02);
    }

    public static void loadSelectedProfile(ke ke2, rf_0 rf_02) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20074);
        sb_02.b((String)rf_02.getSelectedValue());
        acu_1.ara().c(sb_02);
    }

    public static void saveProfile(aGJ aGJ2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20076);
        sb_02.b((String)aGJ2.getItemValue());
        acu_1.ara().c(sb_02);
    }

    public static void deleteProfile(ke ke2, String string) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20078);
        sb_02.b(string);
        acu_1.ara().c(sb_02);
    }

    public static void saveNewProfile(ke ke2, UV uV) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(20076);
        sb_02.b(uV.getText());
        acu_1.ara().c(sb_02);
    }

    public static void setFileName(aGJ aGJ2, UV uV) {
        String string = (String)aGJ2.getItemValue();
        String[] stringArray = string.split("\\.");
        int n2 = stringArray.length;
        if (n2 > 0 && stringArray[n2 - 1].equals("apf")) {
            string = "";
            for (int j = 0; j < n2 - 1; ++j) {
                string = string + stringArray[j];
            }
        }
        uV.setText(string);
    }
}

