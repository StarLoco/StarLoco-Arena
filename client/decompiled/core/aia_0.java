/*
 * Decompiled with CFR 0.152.
 */
import java.util.HashMap;

/*
 * Renamed from aiA
 */
public class aia_0 {
    public static final String PACKAGE = "dofusarena.guild";

    public static void createGuild(ke ke2, Ur ur) {
        if ((ke2.aV() == qe_1.bFB || ke2.aV() == qe_1.bFm && ((aqG)ke2).getKeyCode() == 10) && ur.isValid()) {
            afl_0 afl_02 = ur.getProperty("guildCreationName");
            String string = (String)afl_02.getValue();
            atM atM2 = new atM(kG.Fi.lV(), string);
            apN.aDK().vJ().b(atM2);
        }
        add_1.aOG().kO("guildCreationDialog");
    }

    public static boolean validateGuildCreationForm(Ur ur) {
        ur.agN();
        afl_0 afl_02 = ur.getProperty("guildCreationName");
        String string = (String)afl_02.getValue();
        if (string != null && !string.equals("") && string.length() >= 5) {
            return true;
        }
        add_1.aOG().a(aon_0.aYc().getString("guild.error.invalidName"), 1091L, 102, 1);
        return false;
    }

    public static void getMemberStats(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof ca_0) {
            ca_0 ca_02 = (ca_0)object;
            sj_1 sj_12 = apN.aDK().Ln();
            ca_0 ca_03 = sj_12.aPY();
            KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
            if (aGJ2.getButton() == 1) {
                if (ca_02.isConnected()) {
                    mL mL2 = new mL();
                    mL2.am(ca_02.Ke());
                    apN.aDK().vJ().b(mL2);
                    short s = ca_03.Kg().aRe();
                    short s2 = ca_02.Kg().aRe();
                    azs_0.aLV().g("guildCanPromote", s < s2 && ca_03.Kg().aRa() && s2 != 2 && kI.WW().size() != 2);
                    azs_0.aLV().g("guildCanDepromote", s < s2 && ca_03.Kg().aRb() && s2 < 10);
                } else {
                    add_1.aOG().f(aon_0.aYc().getString("error.chat.userNotFound", ca_02.getName()), 102, 1);
                }
            } else if (aGJ2.getButton() == 3 && ca_03 != null) {
                awC awC2 = add_1.aOG().aOT();
                awC2.a(ca_02.getName(), (akq_1)null);
                awC2.addSeparator();
                if (ca_02.Ke() != sj_12.getId()) {
                    HashMap hashMap;
                    HashMap hashMap2 = mc_1.qM().qN();
                    if (hashMap2 != null) {
                        if (!hashMap2.containsKey(ca_02.getName().toLowerCase())) {
                            awC2.a(aon_0.aYc().getString("chat.addToFriendList"), null, new gf_2(ca_02), true);
                        } else {
                            awC2.a(aon_0.aYc().getString("chat.removeFromFriendList"), null, new ge_1(ca_02), true);
                        }
                    }
                    if ((hashMap = mc_1.qM().qO()) != null) {
                        if (!hashMap.containsKey(ca_02.getName().toLowerCase())) {
                            awC2.a(aon_0.aYc().getString("chat.addToIgnoreList"), null, new gx_0(ca_02), true);
                            awC2.a(aon_0.aYc().getString("chat.reportIncorrectBehaviour"), null, new gy_1(ca_02), true);
                        } else {
                            awC2.a(aon_0.aYc().getString("chat.removeFromIgnoreList"), null, new gt_1(ca_02), true);
                        }
                    }
                    awC2.addSeparator();
                    if (ca_03 != null && ca_03.Kg().aQY()) {
                        awC2.a(aon_0.aYc().getString("guild.removeGuildMember"), null, new gv_2(ca_03, ca_02, kI), true);
                    }
                }
                add_1.aOG().e(awC2);
            }
        }
    }

    public static void closeMemberStatsDialog(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(16434);
        acu_1.ara().c(sb_02);
    }

    public static void quitGuild(ke ke2) {
        ca_0 ca_02 = apN.aDK().Ln().aPY();
        if (ca_02 != null) {
            long l2 = apN.aDK().Ln().getId();
            r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("guild.confirmQuitGuild", ca_02.hd()), 1176L, 102, 1);
            r_02.a(new gr_0(ca_02, l2));
        }
    }

    public static void destroyGuild(ke ke2) {
        ca_0 ca_02 = apN.aDK().Ln().aPY();
        if (ca_02 != null) {
            r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("guild.confirmDestroyGuild", ca_02.hd()), 1176L, 102, 1);
            r_02.a(new gs_1(ca_02));
        }
    }

    public static void removeGuildMember(ke ke2) {
        aez_0 aez_02 = (aez_0)azs_0.aLV().getProperty("guildCoachStats").getValue();
        ca_0 ca_02 = apN.aDK().Ln().aPY();
        if (ca_02 != null) {
            r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("guild.confirmExcludeMember", aez_02.getName()), 1176L, 102, 1);
            r_02.a(new gp_1(ca_02, aez_02));
        }
    }

    public static void showRanks(ke ke2, ai_2 ai_22) {
        qu_0.popup(ke2, ai_22);
    }

    public static void openCloseGuildManagement(ke ke2) {
        if (!add_1.aOG().kR("guildManagementDialog")) {
            add_1.aOG().a("guildManagementDialog", oh_2.bq("guildManagementDialog"), (short)10000);
        } else {
            add_1.aOG().kO("guildManagementDialog");
        }
    }

    public static void selectRank(aGJ aGJ2, UV uV) {
        KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
        aia_0.a(kI, uV);
        vd_2 vd_22 = (vd_2)aGJ2.getItemValue();
        azs_0.aLV().g("guildSelectedRank", vd_22);
    }

    public static void modifyRank(ke ke2, UV uV) {
        vd_2 vd_22 = (vd_2)azs_0.aLV().getProperty("guildSelectedRank").getValue();
        KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
        Nr nr = new Nr();
        nr.g(kI.getId());
        nr.setName(uV.getText());
        nr.gV(vd_22.aRd());
        nr.aH(vd_22.aRe());
        nr.aM(vd_22.aRe());
        apN.aDK().vJ().b(nr);
        vd_22.lb(uV.getText());
        azs_0.aLV().g("guildSelectedRank", vd_22);
        azs_0.aLV().a((aho_0)kI, "guild.editableRanks");
        add_1.aOG().kO("guildManagementDialog");
    }

    public static void setCanInvite(vY vY2) {
        boolean bl2 = vY2.isSelected();
        vd_2 vd_22 = (vd_2)azs_0.aLV().getProperty("guildSelectedRank").getValue();
        if (!vd_22.aQQ() && vd_22.aQY() != bl2) {
            if (bl2) {
                vd_22.gV(vd_22.aRd() + 2);
            } else {
                vd_22.gV(vd_22.aRd() - 2);
            }
        }
    }

    public static void setCanRemove(vY vY2) {
        boolean bl2 = vY2.isSelected();
        vd_2 vd_22 = (vd_2)azs_0.aLV().getProperty("guildSelectedRank").getValue();
        if (!vd_22.aQQ() && vd_22.aQZ() != bl2) {
            if (bl2) {
                vd_22.gV(vd_22.aRd() + 4);
            } else {
                vd_22.gV(vd_22.aRd() - 4);
            }
        }
    }

    public static void setCanPromote(vY vY2) {
        boolean bl2 = vY2.isSelected();
        vd_2 vd_22 = (vd_2)azs_0.aLV().getProperty("guildSelectedRank").getValue();
        if (!vd_22.aQQ() && vd_22.aRa() != bl2) {
            if (bl2) {
                vd_22.gV(vd_22.aRd() + 8);
            } else {
                vd_22.gV(vd_22.aRd() - 8);
            }
        }
    }

    public static void setCanDepromote(vY vY2) {
        boolean bl2 = vY2.isSelected();
        vd_2 vd_22 = (vd_2)azs_0.aLV().getProperty("guildSelectedRank").getValue();
        if (!vd_22.aQQ() && vd_22.aRb() != bl2) {
            if (bl2) {
                vd_22.gV(vd_22.aRd() + 16);
            } else {
                vd_22.gV(vd_22.aRd() - 16);
            }
        }
    }

    public static void addRankToGuild(ke ke2, UV uV) {
        KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
        aia_0.a(kI, uV);
        if (kI.WW().size() < 10) {
            abo_0 abo_02 = new abo_0();
            abo_02.g(kI.getId());
            abo_02.setName(aon_0.aYc().getString("defaultRankName") + kI.WW().size());
            abo_02.gV(0);
            apN.aDK().vJ().b(abo_02);
            vd_2 vd_22 = new vd_2();
            vd_22.gV(0);
            vd_22.lb(aon_0.aYc().getString("defaultRankName") + kI.WW().size());
            vd_22.aH((short)kI.WW().size());
            kI.a(vd_22);
            azs_0.aLV().g("guildSelectedRank", vd_22);
        } else {
            add_1.aOG().a(aon_0.aYc().getString("guild.error.maxGuildRankNumber"), 1058L, 102, 1);
        }
    }

    public static void removeRankToGuild(ke ke2) {
        KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
        vd_2 vd_22 = (vd_2)azs_0.aLV().getProperty("guildSelectedRank").getValue();
        short s = vd_22.aRe();
        if (s != 1 && s != 10) {
            Ko ko = new Ko();
            ko.g(kI.getId());
            ko.aH(s);
            apN.aDK().vJ().b(ko);
            kI.b(vd_22);
            azs_0.aLV().g("guildSelectedRank", kI.WW().get(0));
            ca_0 ca_02 = apN.aDK().Ln().aPY();
            if (ca_02 != null) {
                add_2 add_22 = new add_2(ca_02.Kd());
                apN.aDK().vJ().b(add_22);
            }
        } else {
            add_1.aOG().a(aon_0.aYc().getString("cannotDeleteGuildRank"), 1058L, 102, 1);
        }
    }

    public static void promote(ke ke2) {
        KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
        aez_0 aez_02 = (aez_0)azs_0.aLV().getProperty("guildCoachStats").getValue();
        byte by = aez_02.aPZ();
        sj_1 sj_12 = apN.aDK().Ln();
        ca_0 ca_02 = sj_12.aPY();
        if (by > 2) {
            byte by2;
            abn_2 abn_22 = new abn_2();
            abn_22.as(kI.getId());
            abn_22.at(aez_02.getId());
            byte by3 = by2 = by == 10 ? (byte)(kI.WW().size() - 1) : (byte)(by - 1);
            if (by2 != 1) {
                abn_22.bx(by2);
                apN.aDK().vJ().b(abn_22);
                aez_02.bf(by2);
                azs_0.aLV().a((aho_0)aez_02, "guildRankIconUrl");
                for (ca_0 ca_03 : kI.WV()) {
                    if (ca_03.Ke() != aez_02.getId()) continue;
                    vd_2 vd_22 = null;
                    for (vd_2 vd_23 : kI.WW()) {
                        if (vd_23.aRe() != by2) continue;
                        vd_22 = vd_23;
                        break;
                    }
                    if (vd_22 != null) {
                        ca_03.a(vd_22);
                    }
                    azs_0.aLV().a((aho_0)kI, "guild.members");
                    break;
                }
                if (by2 == apN.aDK().Ln().aPY().Kg().aRe() || by2 == 2 || kI.WW().size() == 2) {
                    azs_0.aLV().g("guildCanPromote", false);
                }
                if (by == 10) {
                    azs_0.aLV().g("guildCanDepromote", ca_02.Kg().aRe() < by2 && ca_02.Kg().aRb() && by2 < 10);
                }
            }
        }
    }

    public static void depromote(ke ke2) {
        KI kI = (KI)azs_0.aLV().getProperty("guild").getValue();
        aez_0 aez_02 = (aez_0)azs_0.aLV().getProperty("guildCoachStats").getValue();
        sj_1 sj_12 = apN.aDK().Ln();
        ca_0 ca_02 = sj_12.aPY();
        byte by = aez_02.aPZ();
        if (by < 10) {
            abn_2 abn_22 = new abn_2();
            abn_22.as(kI.getId());
            abn_22.at(aez_02.getId());
            short s = (byte)(by == kI.WW().size() - 1 ? (short)10 : (byte)(by + 1));
            abn_22.bx(s);
            apN.aDK().vJ().b(abn_22);
            aez_02.bf((byte)s);
            azs_0.aLV().a((aho_0)aez_02, "guildRankIconUrl");
            for (ca_0 ca_03 : kI.WV()) {
                if (ca_03.Ke() != aez_02.getId()) continue;
                vd_2 vd_22 = null;
                for (vd_2 vd_23 : kI.WW()) {
                    if (vd_23.aRe() != s) continue;
                    vd_22 = vd_23;
                    break;
                }
                if (vd_22 != null) {
                    ca_03.a(vd_22);
                }
                azs_0.aLV().a((aho_0)kI, "guild.members");
                break;
            }
            if (s == 10) {
                azs_0.aLV().g("guildCanDepromote", false);
            }
            if (by == 2) {
                azs_0.aLV().g("guildCanPromote", ca_02.Kg().aRe() < s && ca_02.Kg().aRa() && s != 2 && kI.WW().size() != 2);
            }
        }
    }

    private static void a(KI kI, UV uV) {
        vd_2 vd_22 = (vd_2)azs_0.aLV().getProperty("guildSelectedRank").getValue();
        for (vd_2 vd_23 : kI.WW()) {
            String string;
            if (!vd_22.aRf().equals(vd_23.aRf()) || (string = uV.getText()).equals(vd_23.aRf()) && vd_23.aRd() == vd_22.aRd() && vd_23.aRe() == vd_22.aRe()) continue;
            r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("questionModifyRank", vd_22.aRf()), 1177L, 102, 1);
            r_02.a(new abd_2(kI, string, vd_22, vd_23));
        }
    }
}

