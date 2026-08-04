/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Po
 */
public class po_0
extends abb_1 {
    private static final Logger a = Logger.getLogger(po_0.class);
    private static po_0 bDz = new po_0();
    private byte[] bDA = null;
    public static final String bDB = "menuBar.teamManagementButton";
    public static final String bDC = "menuBar.coachInventoryButton";
    public static final String bDD = "menuBar.ladderButton";
    public static final String bDE = "menuBar.statisticsButton";
    public static final String bDF = "menuBar.randomFightButton";
    public static final String bDG = "menuBar.tutorialButton";
    public static final String bDH = "menuBar.socialButton";
    public static final String bDI = "menuBar.achievementButton";
    public static final String bDJ = "menuBar.calendarButton";

    public static po_0 abV() {
        return bDz;
    }

    public boolean a(pr_0 pr_02) {
        sj_1 sj_12 = apN.aDK().Ln();
        switch (pr_02.getId()) {
            case 17000: {
                if (add_1.aOG().kR("npcTalkDialog")) {
                    apN.aDK().b(ao_2.HG());
                } else {
                    apN.aDK().a(ao_2.HG());
                }
                return false;
            }
            case 20031: {
                if (add_1.aOG().kR("mapDialog") || !this.dsb) {
                    apN.aDK().b(ju_1.mp());
                } else {
                    apN.aDK().a(ju_1.mp());
                }
                return false;
            }
            case 20013: {
                if (add_1.aOG().kR("fusionLabDialog")) {
                    apN.aDK().b(add.ase());
                } else if (!(apN.aDK().c(vu_1.aip()) || apN.aDK().c(wp_0.CH()) || apN.aDK().c(ds_2.LP()))) {
                    apN.aDK().a(add.ase());
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("cannotOpenUIWhenSearchingFight"), 1058L, 102, 1);
                }
                return false;
            }
            case 20000: {
                add_1.aOG().kO("coachLevelUpDialog");
                return false;
            }
            case 20032: {
                if (apN.aDK().c(zb_1.GG())) {
                    apN.aDK().b(zb_1.GG());
                } else {
                    apN.aDK().a(alv_0.aWM());
                    yq_1 yq_12 = new yq_1();
                    apN.aDK().vJ().b(yq_12);
                    apN.aDK().vJ().b(new wa_2());
                }
                return false;
            }
            case 20070: {
                if (apN.aDK().c(oz_1.tJ())) {
                    apN.aDK().b(oz_1.tJ());
                } else {
                    yq_1 yq_13 = new yq_1();
                    apN.aDK().vJ().b(yq_13);
                    apN.aDK().vJ().b(new wa_2());
                }
                return false;
            }
            case 20011: {
                if (add_1.aOG().kR("coachStatisticsDialog")) {
                    apN.aDK().b(hm_1.Tz());
                } else {
                    apN.aDK().a(hm_1.Tz());
                }
                return false;
            }
            case 20012: {
                apN.aDK().b(hm_1.Tz());
                return false;
            }
            case 20108: {
                zz_0 zz_02 = new zz_0();
                zz_0.a(sj_12, new adk_1(this, zz_02, sj_12));
                return false;
            }
            case 20018: {
                if (add_1.aOG().kR("ladderInformationDialog") || add_1.aOG().kR("ladderInformationEvolutionDialog")) {
                    apN.aDK().b(ahg_1.aTk());
                } else {
                    pc_1 pc_12 = new pc_1();
                    pc_12.M((short)1);
                    apN.aDK().vJ().b(pc_12);
                    ow_2 ow_22 = new ow_2();
                    ow_22.M((short)1);
                    apN.aDK().vJ().b(ow_22);
                    dp_0 dp_02 = new dp_0();
                    apN.aDK().vJ().b(dp_02);
                    vg_1 vg_12 = new vg_1();
                    apN.aDK().vJ().b(vg_12);
                    qk_2 qk_22 = new qk_2();
                    apN.aDK().vJ().b(qk_22);
                    aa_2 aa_22 = new aa_2();
                    apN.aDK().vJ().b(aa_22);
                    ck_2 ck_22 = new ck_2();
                    apN.aDK().vJ().b(ck_22);
                }
                return false;
            }
            case 20019: {
                apN.aDK().b(ahg_1.aTk());
                return false;
            }
            case 20132: {
                apN.aDK().b(aak_0.aME());
                return false;
            }
            case 20006: {
                if (add_1.aOG().kR("cardBookDialog")) {
                    apN.aDK().b(agn_0.awo());
                } else if (!(apN.aDK().c(vu_1.aip()) || apN.aDK().c(wp_0.CH()) || apN.aDK().c(ds_2.LP()))) {
                    if (sj_12.yO()) {
                        apN.aDK().a(agn_0.awo());
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("cannotOpenUIWhenSearchingFight"), 1058L, 102, 1);
                }
                return false;
            }
            case 20120: {
                if (add_1.aOG().kR("zaapDialog")) {
                    apN.aDK().b(aoa_2.aYv());
                } else if (sj_12.c(avq_0.ce((short)448))) {
                    if (sj_12.yO()) {
                        apN.aDK().a(aoa_2.aYv());
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("mustTalkWithBaan"), 1090L, 102, 1);
                }
                return false;
            }
            case 20007: {
                apN.aDK().b(agn_0.awo());
                return false;
            }
            case 20004: {
                if (((Boolean)azs_0.aLV().getProperty("teamManagementOpen").getValue()).booleanValue()) {
                    if (apN.aDK().c(hu_2.li())) {
                        apN.aDK().b(hu_2.li());
                        apN.aDK().b(vu_1.aip());
                        apN.aDK().b(wp_0.CH());
                        apN.aDK().b(ds_2.LP());
                    }
                } else {
                    apN.aDK().a(hu_2.li());
                }
                return false;
            }
            case 16437: {
                if (add_1.aOG().kR("socialDialog")) {
                    apN.aDK().b(qu_2.adx());
                } else {
                    apN.aDK().a(qu_2.adx());
                }
                return false;
            }
            case 16439: {
                if (add_1.aOG().kR("challengeDialog")) {
                    apN.aDK().b(cj_0.La());
                } else {
                    apN.aDK().a(cj_0.La());
                }
                return false;
            }
            case 22050: {
                if (add_1.aOG().kR("achievementDialog")) {
                    apN.aDK().b(yh.EP());
                } else {
                    apN.aDK().a(yh.EP());
                }
                return false;
            }
            case 23050: {
                if (add_1.aOG().kR("evolutionDialog")) {
                    apN.aDK().b(nb_0.aaI());
                } else {
                    apN.aDK().a(nb_0.aaI());
                }
                return false;
            }
            case 23067: {
                if (add_1.aOG().kR("tournamentEvolutionDialog") || add_1.aOG().kR("tournamentGraveyardDialog")) {
                    apN.aDK().b(en_2.Na());
                } else {
                    apN.aDK().a(en_2.Na());
                }
                return false;
            }
            case 23066: {
                if (add_1.aOG().kR("graveyardDialog")) {
                    apN.aDK().b(aks_2.aAh());
                } else {
                    apN.aDK().a(aks_2.aAh());
                }
                return false;
            }
            case 23061: {
                if (add_1.aOG().kR("evolutionTeamManagementTutoDialog")) {
                    apN.aDK().b(nb_0.aaI());
                } else {
                    apN.aDK().a(nb_0.aaI());
                }
                return false;
            }
            case 23065: {
                if (add_1.aOG().kR("evolutionTeamManagementTuto2Dialog")) {
                    apN.aDK().b(nb_0.aaI());
                } else {
                    apN.aDK().a(nb_0.aaI());
                }
                return false;
            }
            case 16438: {
                apN.aDK().b(qu_2.adx());
                return false;
            }
            case 20017: {
                if (apN.aDK().c(hu_2.li())) {
                    apN.aDK().b(hu_2.li());
                    apN.aDK().b(vu_1.aip());
                    apN.aDK().b(wp_0.CH());
                    apN.aDK().b(ds_2.LP());
                }
                return false;
            }
            case 5301: {
                add_1 add_12 = add_1.aOG();
                if (add_12 != null) {
                    if (add_12.kR("consoleDialog")) {
                        add_12.kO("consoleDialog");
                    } else {
                        add_12.a("consoleDialog", oh_2.bq("consoleDialog"), 1025L, (short)30000);
                    }
                }
                return false;
            }
        }
        return super.a(pr_02);
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            azs_0.aLV().g("showToolsInMenuBar", "0");
            sj_1 sj_12 = apN.aDK().Ln();
            add_1.aOG().a("menuBarDialog", oh_2.bq("menuBarDialog"), (short)10000);
            azs_0.aLV().g("chat.isMaximize", false);
            add_1.aOG().a("chatDialog", oh_2.bq("chatDialog"), 1L, (short)19501);
            this.cd(true);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kO("menuBarDialog");
            this.abW();
        }
        super.b(fh_22, bl2);
    }

    public boolean abW() {
        boolean bl2 = this.abX();
        return this.cc(false) || bl2;
    }

    public boolean cc(boolean bl2) {
        boolean bl3 = false;
        if (add_1.aOG().kR("tooltipDialog")) {
            add_1.aOG().kO("tooltipDialog");
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("menuDialog") || bl2 && bl3)) {
            apN.aDK().b(fc_2.ia());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("fightMenuDialog") || bl2 && bl3)) {
            apN.aDK().b(fc_2.ia());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("ladderInformationDialog") && !add_1.aOG().kR("ladderInformationEvolutionDialog") || bl2 && bl3)) {
            apN.aDK().b(ahg_1.aTk());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("demonLadderInformationDialog") || bl2 && bl3)) {
            apN.aDK().b(aak_0.aME());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("cardBookDialog") || bl2 && bl3)) {
            if (add_1.aOG().kR("coachQuickEquipmentDialog")) {
                add_1.aOG().kO("coachQuickEquipmentDialog");
            } else {
                apN.aDK().b(agn_0.awo());
            }
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("coachStatisticsDialog") || bl2 && bl3)) {
            apN.aDK().b(hm_1.Tz());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("guildDialog") || bl2 && bl3)) {
            add_1.aOG().kO("guildDialog");
            bl3 = true;
        }
        if ((add_1.aOG().kR("fightResultDialog") || add_1.aOG().kR("fightResultEvolutionTutoDialog") || add_1.aOG().kR("fightResultEvolutionTimeChallengeDialog") || add_1.aOG().kR("fightResultEvolutionChallengeDialog") || add_1.aOG().kR("fightResultEvolutionDialog")) && (!bl2 || !bl3)) {
            apN.aDK().b(ajo_1.azb());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("exchangeDialog") || bl2 && bl3)) {
            apN.aDK().b(amq_1.aXh());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("socialDialog") || bl2 && bl3)) {
            apN.aDK().b(qu_2.adx());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("mailboxDialog") || bl2 && bl3)) {
            apN.aDK().b(ayf_0.aKO());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("cardMasterDialog") && !add_1.aOG().kR("demonIIDialog") || bl2 && bl3)) {
            apN.aDK().b(ku_2.oU());
            bl3 = true;
        }
        if (add_1.aOG().kR("demonAffiliationDialog")) {
            apN.aDK().b(arb_0.aED());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("challengeDialog") || bl2 && bl3)) {
            apN.aDK().b(cj_0.La());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("reportBugDialog") || bl2 && bl3)) {
            apN.aDK().b(aOG.aYD());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("calendarDialog") || bl2 && bl3)) {
            apN.aDK().b(zb_1.GG());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("mapDialog") || bl2 && bl3)) {
            apN.aDK().b(ju_1.mp());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("achievementDialog") || bl2 && bl3)) {
            apN.aDK().b(yh.EP());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("team2vs2NameDialog") || bl2 && bl3)) {
            add_1.aOG().kO("team2vs2NameDialog");
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("fighterEquipmentDialog") || bl2 && bl3)) {
            add_1.aOG().kO("fighterEquipmentDialog");
            azs_0.aLV().g("teamManagement.fighterEditionOpen", false);
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("fighterEvolutionEquipmentDialog") || bl2 && bl3)) {
            add_1.aOG().kO("fighterEvolutionEquipmentDialog");
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("fighterCreationTutorialDialog") || bl2 && bl3)) {
            add_1.aOG().kO("fighterCreationTutorialDialog");
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("fighterCreationDialog") || bl2 && bl3)) {
            add_1.aOG().kO("fighterCreationDialog");
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("newTeamManagementDialog") || bl2 && bl3)) {
            apN.aDK().b(hu_2.li());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("evolutionDialog") && !add_1.aOG().kR("evolutionTeamManagementTutoDialog") && !add_1.aOG().kR("evolutionTeamManagementTuto2Dialog") || bl2 && bl3)) {
            apN.aDK().b(nb_0.aaI());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("sphereBoardDialog") || bl2 && bl3)) {
            apN.aDK().b(afb_1.auN());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("calendarTournamentDetailsDialog") || bl2 && bl3)) {
            add_1.aOG().kO("calendarTournamentDetailsDialog");
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("zaapDialog") || bl2 && bl3)) {
            apN.aDK().b(aoa_2.aYv());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("graveyardDialog") || bl2 && bl3)) {
            apN.aDK().b(aks_2.aAh());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("demonAffiliationDialog") || bl2 && bl3)) {
            apN.aDK().b(arb_0.aED());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("fireworkDialog") || bl2 && bl3)) {
            apN.aDK().b(mg_0.rq());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("fightResultEvolutionTimeChallengeDialog") || bl2 && bl3)) {
            apN.aDK().b(ajo_1.azb());
            bl3 = true;
        }
        if (!(!add_1.aOG().kR("totemTournamentDialog") || bl2 && bl3)) {
            apN.aDK().b(oz_1.tJ());
            bl3 = true;
        }
        return bl3;
    }

    public boolean abX() {
        boolean bl2 = do_1.gB();
        while (add_1.aOG().aPe()) {
            bl2 = true;
        }
        return bl2;
    }

    public void cd(boolean bl2) {
        hc_2.kI().k("world", bl2);
        this.dsb = bl2;
        azs_0.aLV().g(bDB, bl2);
        azs_0.aLV().g(bDD, bl2);
        azs_0.aLV().g(bDG, bl2);
        azs_0.aLV().g(bDF, bl2);
        azs_0.aLV().g(bDE, bl2);
        azs_0.aLV().g(bDC, bl2);
        azs_0.aLV().g(bDH, bl2);
        azs_0.aLV().g(bDI, bl2);
        azs_0.aLV().g(bDJ, bl2);
    }

    public boolean abY() {
        return add_1.aOG().kR("tooltipDialog") || add_1.aOG().kR("tooltipTutoDialog") || add_1.aOG().kR("menuDialog") || add_1.aOG().kR("optionsDialog") || add_1.aOG().kR("fightMenuDialog") || add_1.aOG().kR("ladderInformationDialog") || add_1.aOG().kR("ladderInformationEvolutionDialog") || add_1.aOG().kR("newTeamManagementDialog") || azs_0.aLV().getProperty("teamManagementOpen") != null && (Boolean)azs_0.aLV().getProperty("teamManagementOpen").getValue() != false || add_1.aOG().kR("cardBookDialog") || add_1.aOG().kR("cardBookTutorialDialog") || add_1.aOG().kR("coachStatisticsDialog") || add_1.aOG().kR("guildDialog") || add_1.aOG().kR("fightResultDialog") || add_1.aOG().kR("fightResultEvolutionTutoDialog") || add_1.aOG().kR("fightResultEvolutionDialog") || add_1.aOG().kR("exchangeDialog") || add_1.aOG().kR("socialDialog") || add_1.aOG().kR("mailboxDialog") || add_1.aOG().kR("cardMasterDialog") || add_1.aOG().kR("demonIIDialog") || add_1.aOG().kR("challengeDialog") || add_1.aOG().kR("reportBugDialog") || add_1.aOG().kR("calendarDialog") || add_1.aOG().kR("mapDialog") || add_1.aOG().kR("achievementDialog") || add_1.aOG().kR("evolutionDialog") || add_1.aOG().kR("evolutionTeamManagementTutoDialog") || add_1.aOG().kR("evolutionTeamManagementTuto2Dialog") || add_1.aOG().kR("sphereBoardDialog") || add_1.aOG().kR("tournamentTeamManagementDialog") || add_1.aOG().kR("calendarTournamentDetailsDialog") || add_1.aOG().kR("totemTournamentDialog") || add_1.aOG().kR("tournamentAdminCreationDialog") || add_1.aOG().kR("tournamentCreationDialog") || add_1.aOG().kR("zaapDialog");
    }

    static /* synthetic */ byte[] a(po_0 po_02, byte[] byArray) {
        po_02.bDA = byArray;
        return byArray;
    }
}

