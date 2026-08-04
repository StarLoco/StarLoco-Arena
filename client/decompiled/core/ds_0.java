/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ds
 */
public enum ds_0 {
    lE,
    lF;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static ds_0 Y(int n2) {
        ds_0[] ds_0Array = (ds_0[])ds_0.class.getEnumConstants();
        if (n2 < ds_0Array.length && n2 >= 0 && ds_0Array[n2].hU == n2) {
            return ds_0Array[n2];
        }
        for (ds_0 ds_02 : ds_0Array) {
            if (ds_02.hU != n2) continue;
            return ds_02;
        }
        throw new IllegalArgumentException("No enum " + ds_0.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ds_0() {
        this.hU = IU.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ds_0() {
        void var3_1;
        this.hU = var3_1;
        IU.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ds_0() {
        void var3_1;
        this.hU = var3_1.hU;
        IU.aT(this.hU + 1);
    }
}

