/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from PI
 */
public class pi_0 {
    public static final String PACKAGE = "dofusarena.fightCreation";

    public static void setReadyForFight(ke ke2, zK zK2, String string) {
        byte by;
        PV pV = new PV();
        pV.d(B.V().Y());
        try {
            by = Byte.valueOf(string);
        }
        catch (NumberFormatException numberFormatException) {
            by = 0;
        }
        pV.M(by);
        pV.b(zK2);
        acu_1.ara().c(pV);
    }

    public static void setClassicReadyForFight(ke ke2) {
        zK zK2 = (zK)azs_0.aLV().getProperty("teamManagement.editableTeamPreset").getValue();
        if (zK2 != null) {
            zi_0 zi_02 = new zi_0();
            zi_02.b(zK2);
            zi_02.d(B.V().Y());
            acu_1.ara().c(zi_02);
        } else {
            add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.noTeamSelected"), 1091L, 102, 1);
        }
    }

    public static void setTournamentReadyForFight(ke ke2) {
        zK zK2 = (zK)azs_0.aLV().getProperty("teamManagement.editableTeamPreset").getValue();
        amo_0 amo_02 = new amo_0();
        amo_02.b(zK2);
        acu_1.ara().c(amo_02);
    }

    public static void setLegendTournamentReadyForFight(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(23201);
        acu_1.ara().c(sb_02);
    }

    public static void launchTeamTest(ke ke2) {
        zK zK2 = (zK)azs_0.aLV().getProperty("teamManagement.editableTeamPreset").getValue();
        if (zK2 != null) {
            Co co = new Co();
            co.b(zK2);
            acu_1.ara().c(co);
        }
    }

    public static void launchLegendTest(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(24001);
        acu_1.ara().c(sb_02);
    }

    public static void cancelFightCreation(ke ke2) {
        agY agY2 = new agY();
        if (apN.aDK().c(B.V())) {
            long l2 = azs_0.aLV().getProperty("fight.id").getLong();
            agY2.cK(l2);
        }
        agY2.f(16601);
        acu_1.ara().c(agY2);
    }
}

