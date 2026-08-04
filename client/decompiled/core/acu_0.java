/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCU
 */
public enum acu_0 {
    dva,
    dvb,
    dvc,
    dvd,
    dve,
    dvf,
    dvg;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static acu_0 np(int n2) {
        acu_0[] acu_0Array = (acu_0[])acu_0.class.getEnumConstants();
        if (n2 < acu_0Array.length && n2 >= 0 && acu_0Array[n2].hU == n2) {
            return acu_0Array[n2];
        }
        for (acu_0 acu_02 : acu_0Array) {
            if (acu_02.hU != n2) continue;
            return acu_02;
        }
        throw new IllegalArgumentException("No enum " + acu_0.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private acu_0() {
        this.hU = atP.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private acu_0() {
        void var3_1;
        this.hU = var3_1;
        atP.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private acu_0() {
        void var3_1;
        this.hU = var3_1.hU;
        atP.aT(this.hU + 1);
    }
}

