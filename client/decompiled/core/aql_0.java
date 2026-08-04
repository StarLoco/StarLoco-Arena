/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aqL
 */
public enum aql_0 implements rk_0
{
    cOy(0, "Tournoi avec \u00e9quipes de type inconnu."),
    cOz(1, "Tournoi avec \u00e9quipes de type classique 1vs1."),
    cOA(2, "Tournoi avec \u00e9quipes de type \u00e9volution."),
    cOB(3, "Tournoi avec \u00e9quipes de type cimeti\u00e8re."),
    cOC(4, "Tournoi avec \u00e9quipes de type l\u00e9gendaire.");

    public static final byte cOD;
    public static final byte cOE;
    public static final byte cOF;
    public static final byte cOG;
    public static final byte cOH;
    private byte axW;
    private String fM;

    /*
     * WARNING - void declaration
     */
    private aql_0() {
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

    static {
        cOD = cOy.lV();
        cOE = cOz.lV();
        cOF = cOA.lV();
        cOG = cOB.lV();
        cOH = cOC.lV();
    }
}

