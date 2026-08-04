/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from wR
 */
public class wr_2 {
    public static final String PACKAGE = "dofusarena.replay";
    private static boolean avK = false;

    public static void startReplay(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(31000);
        acu_1.ara().c(sb_02);
    }

    public static void playPause(ke ke2) {
        if (azs_0.aLV().getBooleanProperty("replayPaused")) {
            azs_0.aLV().g("replayPaused", false);
            RO.aer().pause();
        } else {
            azs_0.aLV().g("replayPaused", true);
            RO.aer().resume();
        }
    }

    public static void stop(ke ke2) {
        RO.aer().stop();
    }

    public static void quit(ke ke2) {
        System.exit(0);
    }
}

