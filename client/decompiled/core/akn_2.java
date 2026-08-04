/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aKN
 */
public enum akn_2 implements rk_0
{
    dTY(0, "Toutes", null),
    dTZ(1, "Fran\u00e7ais", aie_0.dOv),
    dUa(2, "Anglais", aie_0.dOw),
    dUb(3, "Allemand", aie_0.dOx),
    dUc(4, "Espagnol", aie_0.dOy),
    dUd(5, "Italien", aie_0.dOz),
    dUe(6, "Portugais", aie_0.dOE);

    private final short fL;
    private final String gb;
    private final aie_0 aCt;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private akn_2(aie_0 aie_02) {
        void var5_3;
        void var4_2;
        this.fL = (short)aie_02;
        this.gb = var4_2;
        this.aCt = var5_3;
    }

    public short tI() {
        return this.fL;
    }

    public String cC() {
        return Integer.toString(this.fL);
    }

    public String cD() {
        return this.gb;
    }

    public aie_0 Fd() {
        return this.aCt;
    }

    public String cE() {
        return null;
    }

    public static akn_2 cw(short s) {
        for (akn_2 akn_22 : akn_2.values()) {
            if (akn_22.tI() != s) continue;
            return akn_22;
        }
        return null;
    }

    public static akn_2 b(aie_0 aie_02) {
        for (akn_2 akn_22 : akn_2.values()) {
            if (akn_22.Fd() != aie_02) continue;
            return akn_22;
        }
        return null;
    }
}

