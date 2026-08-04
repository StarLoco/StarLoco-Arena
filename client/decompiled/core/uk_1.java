/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from uK
 */
public class uk_1 {
    public static final String PACKAGE = "dofusarena.social";

    public static void removeFromFriendList(ke ke2, axa_0 axa_02) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21050);
        sb_02.b(axa_02.getName());
        acu_1.ara().c(sb_02);
    }

    public static void addToFriendList(ke ke2, UV uV) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21051);
        sb_02.b(uV.getText());
        acu_1.ara().c(sb_02);
    }

    public static void removeFromIgnoreList(ke ke2, axa_0 axa_02) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21052);
        sb_02.b(axa_02.getName());
        acu_1.ara().c(sb_02);
    }

    public static void addToIgnoreList(ke ke2, UV uV) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21053);
        sb_02.b(uV.getText());
        acu_1.ara().c(sb_02);
    }

    public static void inviteToGuild(ke ke2, UV uV) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(21054);
        sb_02.b(uV.getText());
        acu_1.ara().c(sb_02);
    }

    public static void switchSocialTab(ke ke2) {
        na_1 na_12 = ke2.oF();
        if (na_12 instanceof dl_1) {
            int n2;
            ca_0[] ca_0Array;
            afl_0 afl_02;
            if (((dl_1)na_12).getText().equals(aon_0.aYc().getString("guild")) && (afl_02 = azs_0.aLV().getProperty("guild")) != null && (ca_0Array = ((KI)afl_02.getValue()).WV()) != null && (n2 = ca_0Array.length) < 5) {
                add_1.aOG().a(aon_0.aYc().getString("guild.notEnoughGuildMembersToBeActive", 5 - n2), 34L, 102, 1);
            }
            add_1.aOG().kO("guildManagementDialog");
        }
    }
}

