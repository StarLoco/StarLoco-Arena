/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aAq
 */
public class aaq_0 {
    public static final String PACKAGE = "console";

    public static void processInputKeyEvent(aqG aqG2, Ur ur) {
        afl_0 afl_02 = ur.getProperty("debug.console");
        switch (aqG2.getKeyCode()) {
            case 10: {
                ur.agN();
                String string = afl_02.hV("input");
                apk_0.aDz().iS(string);
                afl_02.a("input", (Object)"");
                if (string == null || !string.toLowerCase().startsWith("instance ")) break;
                auv_0.ek(true);
                break;
            }
            case 38: {
                afl_02.a("input", (Object)apk_0.aDz().abD());
                break;
            }
            case 40: {
                afl_02.a("input", (Object)apk_0.aDz().abE());
            }
        }
    }

    public static void clear(ke ke2) {
        azs_0.aLV().a("debug.console", "logs", (Object)"");
    }
}

