/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from lr
 */
public class lr_1
implements atG {
    private static final lr_1 Ha = new lr_1();
    public static final String Hb = "apf";

    public static lr_1 qk() {
        return Ha;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 20072: {
                return false;
            }
            case 20073: {
                if (!add_1.aOG().kR("customFightProfileLoadingDialog")) {
                    add_1.aOG().a("customFightProfileLoadingDialog", oh_2.bq("customFightProfileLoadingDialog"), 256L, (short)10000);
                } else {
                    add_1.aOG().kO("customFightProfileLoadingDialog");
                }
                return false;
            }
            case 20075: {
                if (!add_1.aOG().kR("customFightProfileSavingDialog")) {
                    add_1.aOG().a("customFightProfileSavingDialog", oh_2.bq("customFightProfileSavingDialog"), 256L, (short)10000);
                } else {
                    add_1.aOG().kO("customFightProfileSavingDialog");
                }
                return false;
            }
            case 20074: {
                Object object;
                sb_0 sb_02 = (sb_0)pr_02;
                if (jk_1.mf().getFieldValue("profiles") == null) {
                    add_1.aOG().a(aon_0.aYc().getString("noCustomFightProfileSaved"), 1091L, 102, 1);
                    return false;
                }
                jk_1.mf().clear();
                if (sb_02.getStringValue() != null && !sb_02.getStringValue().equals(aon_0.aYc().getString("defaultFightProfile"))) {
                    object = new lp_0(sb_02.getStringValue());
                    if (br.a((bb_2)object)) {
                        ArrayList arrayList = WN.A(((lp_0)object).qi());
                        jk_1.mf().d(arrayList);
                        add_1.aOG().a(aon_0.aYc().getString("fileLoaded"), 1059L, 102, 1);
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("errorLoadingFile"), 1091L, 102, 1);
                    }
                }
                azs_0.aLV().a((aho_0)jk_1.mf(), "summary");
                object = (sd_1)azs_0.aLV().getProperty("selectedSpellCategory").getValue();
                azs_0.aLV().a((aho_0)object, "fightRules");
                object = (sd_1)azs_0.aLV().getProperty("selectedEquipmentCategory").getValue();
                azs_0.aLV().a((aho_0)object, "fightRules");
                object = (sd_1)azs_0.aLV().getProperty("selectedBreedCategory").getValue();
                azs_0.aLV().a((aho_0)object, "fightRules");
                object = (sd_1)azs_0.aLV().getProperty("selectedFightBudgetCategory").getValue();
                azs_0.aLV().a((aho_0)object, "fightRules");
                return false;
            }
            case 20076: {
                sb_0 sb_03 = (sb_0)pr_02;
                String string = sb_03.getStringValue();
                if (!string.equals("") && !string.equals(aon_0.aYc().getString("defaultFightProfile"))) {
                    String[] stringArray = string.split("\\.");
                    if (stringArray.length > 0 && !stringArray[stringArray.length - 1].equals(Hb)) {
                        string = string + ".apf";
                    }
                    lp_0 lp_02 = new lp_0(string);
                    ArrayList arrayList = jk_1.mf().me();
                    if (arrayList.size() == 0) {
                        add_1.aOG().a(aon_0.aYc().getString("errorNoFightRuleSelected"), 1091L, 102, 1);
                        return false;
                    }
                    jg_0 jg_02 = new jg_0();
                    for (int j = 0; j < arrayList.size(); ++j) {
                        ArrayList arrayList2 = ((WN)arrayList.get(j)).ajp();
                        for (int i2 = 0; i2 < arrayList2.size(); ++i2) {
                            jg_02.add(((np_1)arrayList2.get(i2)).sn());
                        }
                    }
                    lp_02.e(jg_02.nm());
                    if (br.b(lp_02)) {
                        add_1.aOG().a(aon_0.aYc().getString("fileSaved"), 1059L, 102, 1);
                        add_1.aOG().kO("customFightProfileSavingDialog");
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("errorSavingFile"), 1091L, 102, 1);
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("errorProfileInvalidName"), 1091L, 102, 1);
                }
                return false;
            }
            case 20078: {
                sb_0 sb_04 = (sb_0)pr_02;
                jk_1.mf().clear();
                if (!sb_04.getStringValue().equals(aon_0.aYc().getString("defaultFightProfile"))) {
                    lp_0 lp_03 = new lp_0(sb_04.getStringValue());
                    if (br.c(lp_03)) {
                        add_1.aOG().a(aon_0.aYc().getString("fileDeleted"), 1059L, 102, 1);
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("errorDeletingFile"), 1091L, 102, 1);
                    }
                }
                azs_0.aLV().a((aho_0)jk_1.mf(), "profiles");
                return false;
            }
        }
        return true;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            jk_1.mf().clear();
            add_1.aOG().a("customFightDialog", oh_2.bq("customFightDialog"), (short)10000);
            add_1.aOG().l("dofusarena.customFight", pv_1.class);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kO("customFightDialog");
            add_1.aOG().kG("dofusarena.customFight");
        }
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

