/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ks
 */
public enum ks_1 implements rk_0
{
    bnB(0, "Type inconnu."),
    bnC(1, "Type priv\u00e9."),
    bnD(2, "Type multi-instanci\u00e9."),
    bnE(3, "Type multi-pyramidal ou dit d\u00e9terministe.");

    private byte axW;
    private String fM;
    public static final byte bnF;
    public static final byte bnG;
    public static final byte bnH;
    public static final byte bnI;

    /*
     * WARNING - void declaration
     */
    private ks_1() {
        void var4_2;
        void var3_1;
        this.axW = var3_1;
        this.fM = var4_2;
    }

    public byte lV() {
        return this.axW;
    }

    public String cC() {
        return String.valueOf(this.axW);
    }

    public String cD() {
        return this.fM;
    }

    public String cE() {
        return this.toString();
    }

    public static boolean Z(byte by) {
        return by == bnH;
    }

    public static boolean aa(byte by) {
        return by == bnI;
    }

    static {
        bnF = bnB.lV();
        bnG = bnC.lV();
        bnH = bnD.lV();
        bnI = bnE.lV();
    }
}

