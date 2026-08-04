/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aAo
 */
public abstract class aao_2
extends Enum {
    public static final /* enum */ aao_2 dpf = new aaf_0();
    public static final /* enum */ aao_2 dpg = new aas();
    public static final /* enum */ aao_2 dph = new aat();
    public static final /* enum */ aao_2 dpi = new aar_1();
    private static final /* synthetic */ aao_2[] dpj;

    public static final aao_2[] values() {
        return (aao_2[])dpj.clone();
    }

    public static aao_2 valueOf(String string) {
        return Enum.valueOf(aao_2.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aao_2() {
        void var2_-1;
        void var1_-1;
    }

    public abstract vz_1 aoG();

    public static aao_2 ke(String string) {
        aao_2[] aao_2Array;
        for (aao_2 aao_22 : aao_2Array = aao_2.values()) {
            if (!aao_22.name().equals(string.toUpperCase())) continue;
            return aao_22;
        }
        return aao_2Array[0];
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ aao_2(mf_0 mf_02) {
        this((String)var1_-1, (int)var2_1);
        void var2_1;
        void var1_-1;
    }

    static {
        dpj = new aao_2[]{dpf, dpg, dph, dpi};
    }
}

