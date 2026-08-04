/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from eI
 */
public enum ei_0 implements rk_0
{
    pe(1, 300, "vitesse de course normale", "300ms/cell, 7.5 frames \u00e0 25fps"),
    pf(2, 600, "vitesse de marche normale", "600ms/cell, 15 frames \u00e0 25fps"),
    pg(3, 1000, "vitesse de marche lente", "1000ms/cell, 25 frames \u00e0 25fps"),
    ph(4, 1200, "vitesse de marche tr\u00e8s lente", "1200ms/cell, 30 frames \u00e0 25fps");

    private static final lb_0 pi;
    private int aW;
    private String gb;
    private String pj;
    private int pk;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ei_0(String string, String string2) {
        void var6_4;
        void var5_3;
        this.aW = (int)string;
        this.pk = (int)string2;
        this.gb = var5_3;
        this.pj = var6_4;
    }

    public String cC() {
        return Integer.toString(this.aW);
    }

    public String cD() {
        return this.gb;
    }

    public String cE() {
        return this.pj;
    }

    public int getId() {
        return this.aW;
    }

    public int hH() {
        return this.pk;
    }

    public static ei_0 al(int n2) {
        return (ei_0)pi.get(n2);
    }

    static {
        pi = new lb_0();
        for (ei_0 ei_02 : ei_0.values()) {
            pi.c(ei_02.getId(), ei_02);
        }
    }
}

