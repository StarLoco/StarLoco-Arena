/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBq
 */
public enum abq_0 implements rk_0
{
    drk(0, "Filtre Obs\u00e9nit\u00e9"),
    drl(1, "Banni"),
    drm(2, "Nom prot\u00e9g\u00e9");

    private final short fL;
    private final String gb;

    /*
     * WARNING - void declaration
     */
    private abq_0() {
        void var4_2;
        void var3_1;
        this.fL = var3_1;
        this.gb = var4_2;
    }

    public int getId() {
        return this.fL;
    }

    public String cC() {
        return Integer.toString(this.fL);
    }

    public String cD() {
        return this.gb;
    }

    public String cE() {
        return null;
    }

    public static abq_0 cj(short s) {
        for (abq_0 abq_02 : abq_0.values()) {
            if (abq_02.getId() != s) continue;
            return abq_02;
        }
        return null;
    }
}

