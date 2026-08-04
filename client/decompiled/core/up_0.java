/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from uP
 */
public enum up_0 implements rk_0
{
    aqV(57, "AnimEmote-Applaudir", "/clap"),
    aqW(59, "AnimEmote-Lire-Debut", "/read"),
    aqX(60, "AnimEmote-Declaration", "/declare"),
    aqY(62, "AnimEmote-Colere", "/angry"),
    aqZ(63, "AnimEmote-Guitare-Debut", "/music"),
    ara(65, "AnimEmote-Pointer", "/show"),
    arb(66, "AnimEmote-Rire", "/laugh"),
    arc(67, "AnimEmote-Effraye", "/fear"),
    ard(68, "AnimEmote-Defaite", "/cry"),
    are(69, "AnimEmote-Non", "/no");

    private static final zm_1 arf;
    private final short fL;
    private final String dw;
    private final String arg;

    public String cC() {
        return Short.valueOf(this.tI()).toString();
    }

    public String cD() {
        return this.toString();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private up_0(String string) {
        void var5_3;
        void var4_2;
        this.fL = (short)string;
        this.dw = var4_2;
        this.arg = var5_3;
    }

    public static up_0 cz(String string) {
        for (up_0 up_02 : up_0.values()) {
            if (!up_02.getCommand().equals(string)) continue;
            return up_02;
        }
        return null;
    }

    public static up_0 dP(int n2) {
        return (up_0)arf.an((short)n2);
    }

    public short tI() {
        return this.fL;
    }

    public String AU() {
        return this.dw;
    }

    public String getCommand() {
        return this.arg;
    }

    public String cE() {
        return null;
    }

    static {
        arf = new zm_1();
        for (up_0 up_02 : up_0.values()) {
            arf.b(up_02.tI(), up_02);
        }
    }
}

