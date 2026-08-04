/*
 * Decompiled with CFR 0.152.
 */
public class asA
implements atG {
    private static asA cSm = new asA();

    public static asA aFA() {
        return cSm;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 21000: {
                if (!add_1.aOG().kR("tooltipDialog")) {
                    azs_0.aLV().g("tooltip.content", aon_0.aYc().a(29, this.aFB(), new Object[0]));
                    add_1.aOG().a("tooltipDialog", oh_2.bq("tooltipDialog"), 1L, (short)10000);
                } else {
                    add_1.aOG().kO("tooltipDialog");
                }
                return false;
            }
            case 21001: {
                if (!add_1.aOG().kR("tooltipTutoDialog")) {
                    anp_0 anp_02 = new anp_0();
                    apN.aDK().vJ().b(anp_02);
                } else {
                    add_1.aOG().kO("tooltipTutoDialog");
                }
                return false;
            }
            case 22002: {
                ls_0 ls_02 = (ls_0)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                sj_12.b(ls_02.qI());
                if (!sj_12.c(qy_2.ce((short)275))) {
                    aea_1 aea_12 = new aea_1(qy_2.ce((short)275));
                    azs_0.aLV().g("tooltip.content", aea_12);
                } else if (!sj_12.c(qy_2.ce((short)276))) {
                    aea_1 aea_13 = new aea_1(qy_2.ce((short)276));
                    azs_0.aLV().g("tooltip.content", aea_13);
                } else if (!sj_12.c(qy_2.ce((short)284))) {
                    aea_1 aea_14 = new aea_1(qy_2.ce((short)284));
                    azs_0.aLV().g("tooltip.content", aea_14);
                } else if (!sj_12.c(qy_2.ce((short)278))) {
                    aea_1 aea_15 = new aea_1(qy_2.ce((short)278));
                    azs_0.aLV().g("tooltip.content", aea_15);
                } else if (!sj_12.c(qy_2.ce((short)277))) {
                    aea_1 aea_16 = new aea_1(qy_2.ce((short)277));
                    azs_0.aLV().g("tooltip.content", aea_16);
                } else if (!sj_12.qI().contains(or_0.YV.tI())) {
                    aea_1 aea_17 = new aea_1(qy_2.ce((short)279));
                    azs_0.aLV().g("tooltip.content", aea_17);
                }
                add_1.aOG().a("tooltipTutoDialog", oh_2.bq("tooltipTutoDialog"), 1L, (short)10000);
                return false;
            }
        }
        return true;
    }

    public int aFB() {
        if (add_1.aOG().kR("reportBugDialog")) {
            return 125;
        }
        if (add_1.aOG().kR("optionsDialog")) {
            return 126;
        }
        if (apN.aDK().aDL() != null) {
            if (add_1.aOG().kR("fighterInformationsDialog")) {
                return 132;
            }
            if (add_1.aOG().kR("fightObservationDialog")) {
                return 142;
            }
            if (add_1.aOG().kR("fightPlacementDialog")) {
                return 136;
            }
            if (add_1.aOG().kR("fightPresentationDialog")) {
                return 135;
            }
            return 119;
        }
        if (add_1.aOG().kR("challengeDialog")) {
            return 143;
        }
        if (add_1.aOG().kR("ladderInformationDialog")) {
            return 120;
        }
        if (add_1.aOG().kR("fighterCreationDialog")) {
            return 138;
        }
        if (add_1.aOG().kR("fighterEquipmentDialog")) {
            return 139;
        }
        if (add_1.aOG().kR("teamNameDialog")) {
            return 140;
        }
        if (add_1.aOG().kR("team2vs2NameDialog")) {
            return 141;
        }
        if (add_1.aOG().kR("newTeamManagementDialog")) {
            return 124;
        }
        if (add_1.aOG().kR("cardBookDialog")) {
            return 123;
        }
        if (add_1.aOG().kR("zaapDialog")) {
            return 130;
        }
        if (add_1.aOG().kR("coachStatisticsDialog")) {
            return 121;
        }
        if (add_1.aOG().kR("socialDialog")) {
            return 122;
        }
        if (add_1.aOG().kR("fightResultDialog")) {
            return 134;
        }
        if (add_1.aOG().kR("exchangeDialog")) {
            return 137;
        }
        if (add_1.aOG().kR("newMailDialog")) {
            return 128;
        }
        if (add_1.aOG().kR("mailboxDialog")) {
            return 127;
        }
        if (add_1.aOG().kR("cardMasterDialog")) {
            return 129;
        }
        return 118;
    }

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

