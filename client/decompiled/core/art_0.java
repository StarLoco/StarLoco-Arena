/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from art
 */
enum art_0 {
    cPO(2, "hideObstacle", "!/common/alphaMask"),
    cPP(0, "showMap", "!/world/map"),
    cPQ(0, "mute", "!/world/mute"),
    cPR(0, "bugReport", "!/common/bugReport"),
    cPS(0, "help", "!/common/showHelp"),
    cPT(1, "showGrid", "!/fight/showGrid"),
    cPU(1, "hideFighter", "!/fight/hideFighter"),
    cPV(1, "rule", "!/fight/rule"),
    cPW(1, "hideTimeline", "!/fight/hideTimeline");

    private final byte aIm;
    private final String m_name;
    private final String arg;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private art_0(String string) {
        void var5_3;
        void var4_2;
        this.aIm = (byte)string;
        this.m_name = var4_2;
        this.arg = var5_3;
    }

    static /* synthetic */ byte a(art_0 art_02) {
        return art_02.aIm;
    }

    static /* synthetic */ String b(art_0 art_02) {
        return art_02.m_name;
    }

    static /* synthetic */ String c(art_0 art_02) {
        return art_02.arg;
    }
}

