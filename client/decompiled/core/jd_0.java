/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Jd
 */
public class jd_0 {
    public static final String PACKAGE = "dofusarena.chat";
    private static int bjl = 6;
    private static String[] bjm = new String[]{"/w ", "/whisper ", "/r ", "/reply ", "/resetPosition", "/resetposition"};
    private static final int bjn = 10;
    private static String[] bjo = new String[10];
    private static int bjp = 0;
    private static final long bjq = 5000L;
    private static long[] bjr = new long[10];

    private static boolean eR(String string) {
        int n2;
        for (n2 = 0; n2 < bjm.length && !string.startsWith(bjm[n2]); ++n2) {
        }
        return n2 != bjm.length;
    }

    private static boolean c(String string, long l2) {
        int n2;
        if (string.length() < bjl || jd_0.eR(string)) {
            return false;
        }
        String string2 = new String(string).toLowerCase();
        for (n2 = 0; n2 < 10 && !bjo[n2].toLowerCase().equals(string2); ++n2) {
        }
        boolean bl2 = false;
        if (n2 != 10) {
            if (l2 < bjr[n2]) {
                bl2 = true;
            } else {
                jd_0.bjr[n2] = l2 + 5000L;
            }
        } else {
            jd_0.bjo[jd_0.bjp] = string;
            jd_0.bjr[jd_0.bjp] = l2 + 5000L;
            ++bjp;
            bjp %= 10;
        }
        return bl2;
    }

    public static void processInputKeyEvent(aqG aqG2, Ur ur) {
        afl_0 afl_02 = azs_0.aLV().getProperty("chat.selectedView");
        switch (aqG2.getKeyCode()) {
            case 10: {
                ur.agN();
                String string = afl_02.hV("input");
                if (string.length() <= 0) break;
                if (string.equals("/resetPosition") || string.equals("/resetposition")) {
                    cx_1 cx_12 = new cx_1();
                    cx_12.setMessage(string);
                    acu_1.ara().c(cx_12);
                    auv_0.ek(true);
                } else if (!jd_0.c(string, System.currentTimeMillis())) {
                    cx_1 cx_13 = new cx_1();
                    cx_13.setMessage(string);
                    acu_1.ara().c(cx_13);
                } else {
                    zc_0 zc_02 = new zc_0(aon_0.aYc().getString("error.chat.floodDetected"));
                    zc_02.eD(5);
                    ql_1.acX().a(zc_02);
                }
                afl_02.a("input", (Object)"");
                break;
            }
            case 38: {
                abl_2 abl_22 = ahv_0.aUv().aUx();
                if (abl_22 == null) break;
                afl_02.a("input", (Object)abl_22.acY().abD());
                break;
            }
            case 40: {
                abl_2 abl_23 = ahv_0.aUv().aUx();
                if (abl_23 == null) break;
                afl_02.a("input", (Object)abl_23.acY().abE());
            }
        }
    }

    public static void checkPipe(ke ke2, Xu xu) {
        if (xu != null) {
            asg asg2 = new asg();
            asg2.c(xu);
            asg2.dV(!xu.isOpen());
            acu_1.ara().c(asg2);
        }
    }

    public static void maximizeMinimizeChatWindow(ke ke2) {
        sb_0 sb_02 = new sb_0();
        sb_02.f(19007);
        acu_1.ara().c(sb_02);
    }

    public static void checkNotify(ke ke2, axa_0 axa_02) {
        if (axa_02 != null) {
            um_2 um_22 = new um_2();
            um_22.a(axa_02);
            um_22.f(19005);
            acu_1.ara().c(um_22);
        }
    }

    public static void privateMessage(aGJ aGJ2) {
        Object object = aGJ2.getItemValue();
        if (object != null && object instanceof rV) {
            nk_1 nk_12 = new nk_1();
            nk_12.a((axa_0)object);
            nk_12.f(19006);
            acu_1.ara().c(nk_12);
        }
    }

    public static void selectChatView(aGJ aGJ2, adg_2 adg_22) {
        abl_2 abl_22 = (abl_2)aGJ2.getItemValue();
        azs_0.aLV().g("chat.pipes.list", abl_22.getFilters());
        azs_0.aLV().g("chat.selectedView", abl_22);
        if (aGJ2.getButton() == 3) {
            add_1.aOG().a("chatFilterDialog", oh_2.bq("chatFilterDialog"), null, false, aGJ2.getScreenX() - 108, aGJ2.getScreenY() - abl_22.getFilters().size() * 20, 1L, (short)20000);
        }
    }

    public static void openCloseChatFilter(ke ke2) {
        if (!add_1.aOG().kR("chatFilterDialog")) {
            add_1.aOG().a("chatFilterDialog", oh_2.bq("chatFilterDialog"), 1L, (short)20000);
        } else {
            add_1.aOG().kO("chatFilterDialog");
        }
    }

    public static void closeChatFilter(ke ke2) {
        add_1.aOG().kO("chatFilterDialog");
    }

    public static void selectPipe(abd_1 abd_12) {
        if (abd_12.getButton() == 3) {
            awC awC2 = add_1.aOG().aOT();
            awC2.a(aon_0.aYc().getString("chat.pipeName.vicinity"), null, new sy_1(), true);
            awC2.a(aon_0.aYc().getString("chat.pipeName.guild"), null, new sc_0(), true);
            awC2.a(aon_0.aYc().getString("chat.pipeName.trade"), null, new sd_0(), true);
            awC2.a(aon_0.aYc().getString("chat.pipeName.teammate"), null, new sa_0(), true);
            awC2.br(abd_12.getScreenX(), abd_12.getScreenY() + 60);
        }
    }

    static {
        for (int j = 0; j < 10; ++j) {
            jd_0.bjo[j] = "";
            jd_0.bjr[j] = Long.MIN_VALUE;
        }
    }
}

