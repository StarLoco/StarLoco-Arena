/*
 * Decompiled with CFR 0.152.
 */
public enum aMK implements rk_0
{
    dYa(0, "Smiley", new short[]{-1}),
    dYb(1, "Emote", new short[]{-1}),
    dYc(2, "Culotte", new short[]{5}),
    dYd(3, "Coiffure", new short[]{2}),
    dYe(4, "Tatouages", new short[]{1}),
    dYf(5, "Brassard", new short[]{4}),
    dYg(6, "Bottes", new short[]{10}),
    dYh(7, "Epaulette", new short[]{3}),
    dYi(8, "Cape", new short[]{8}),
    dYj(9, "Pantalon", new short[]{6}),
    dYk(10, "Chemise", new short[]{11}),
    dYl(11, "Chapeau", new short[]{0}),
    dYm(12, "B\u00e2ton", new short[]{7}),
    dYn(13, "Familier", new short[]{9}),
    dYo(14, "Malediction", new short[]{-1}),
    dYp(15, "Familier - Coeur", new short[]{-1}),
    dYq(16, "Familier - Membre", new short[]{-1}),
    dYr(17, "Familier - T\u00eate", new short[]{-1}),
    dYs(18, "Familier - Tronc", new short[]{-1}),
    dYt(19, "Familier - Accessoire", new short[]{-1}),
    dYu(20, "Zaap", new short[]{-1}),
    dYv(21, "Carte sp\u00e9ciale", new short[]{-1}),
    dYw(22, "Symbole Fond", new short[]{-1}),
    dYx(23, "Symbole Logo", new short[]{-1}),
    dYy(24, "Evolution", new short[]{-1}),
    dYz(25, "Feu d'artifice", new short[]{-1}),
    dYA(26, "Combat", new short[]{-1}),
    dYB(27, "Fusion", new short[]{-1}),
    dYC(28, "Titre", new short[]{-1}),
    dYD(29, "Couleur", new short[]{-1}),
    dYE(30, "Dofus", new short[]{-1});

    private final int aW;
    private final String m_name;
    private final short[] dYF;
    private static final lb_0 cVn;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aMK(short[] sArray) {
        void var5_3;
        void var4_2;
        this.aW = (int)sArray;
        this.m_name = var4_2;
        this.dYF = var5_3;
    }

    public int getId() {
        return this.aW;
    }

    public String getName() {
        return this.m_name;
    }

    public short[] aXg() {
        return this.dYF;
    }

    public static aMK pq(int n2) {
        return (aMK)cVn.get(n2);
    }

    public String cC() {
        return String.valueOf(this.aW);
    }

    public String cD() {
        return this.m_name;
    }

    public String cE() {
        return this.toString();
    }

    static {
        cVn = new lb_0();
        for (aMK aMK2 : aMK.values()) {
            cVn.c(aMK2.getId(), aMK2);
        }
    }
}

