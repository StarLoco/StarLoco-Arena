/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from QK
 */
public enum qk_1 implements rk_0
{
    bHA(1, "Poss\u00e9der une position"),
    bHB(2, "Poss\u00e9der un nombre de points de victoire"),
    bHC(3, "Tuer des combattants d'une classe"),
    bHD(4, "Atteindre un tour donn\u00e9"),
    bHE(1000, "Aucune condition sur ce combat");

    private short fL;
    private String fM;

    /*
     * WARNING - void declaration
     */
    private qk_1() {
        void var4_2;
        void var3_1;
        this.fL = var3_1;
        this.fM = var4_2;
    }

    public short tI() {
        return this.fL;
    }

    public String cC() {
        return String.valueOf(this.fL);
    }

    public String cD() {
        return this.fM;
    }

    public String cE() {
        return this.toString();
    }
}

