/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aef
 */
public class aef_0
implements atG {
    private static aef_0 coc = new aef_0();

    public static aef_0 atz() {
        return coc;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16429: {
                if (add_1.aOG().kR("guildDialog")) {
                    add_1.aOG().kO("guildDialog");
                } else {
                    ca_0 ca_02 = apN.aDK().Ln().aPY();
                    if (ca_02 != null) {
                        add_2 add_22 = new add_2(ca_02.Kd());
                        apN.aDK().vJ().b(add_22);
                    }
                }
                return false;
            }
            case 16430: {
                add_1.aOG().kO("guildDialog");
                return false;
            }
            case 16431: {
                if (add_1.aOG().kR("guildCreationDialog")) {
                    add_1.aOG().kO("guildCreationDialog");
                } else {
                    ca_0 ca_03 = apN.aDK().Ln().aPY();
                    if (ca_03 == null) {
                        add_1.aOG().a("guildCreationDialog", oh_2.bq("guildCreationDialog"), 1025L, (short)10000);
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("guild.error.alreadyGuildMember"), 1090L, 102, 1);
                    }
                }
                return false;
            }
            case 16432: {
                add_1.aOG().kO("guildCreationDialog");
                return false;
            }
            case 16433: {
                if (add_1.aOG().kR("guildCoachStatsDialog")) {
                    add_1.aOG().kO("guildCoachStatsDialog");
                } else {
                    add_1.aOG().a("guildCoachStatsDialog", oh_2.bq("guildCoachStatsDialog"), 1L, (short)10001);
                }
                return false;
            }
            case 16434: {
                add_1.aOG().kO("guildCoachStatsDialog");
                return false;
            }
        }
        return true;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().l("dofusarena.guild", aia_0.class);
            azs_0.aLV().g("guild", new KI());
            azs_0.aLV().g("guildInviter", false);
            azs_0.aLV().g("guildExcluder", false);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kG("dofusarena.guild");
            azs_0.aLV().kb("guild");
        }
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

