/*
 * Decompiled with CFR 0.152.
 */
public enum axJ {
    dkb,
    dkc,
    dkd,
    dke;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static axJ mI(int n2) {
        axJ[] axJArray = (axJ[])axJ.class.getEnumConstants();
        if (n2 < axJArray.length && n2 >= 0 && axJArray[n2].hU == n2) {
            return axJArray[n2];
        }
        for (axJ axJ2 : axJArray) {
            if (axJ2.hU != n2) continue;
            return axJ2;
        }
        throw new IllegalArgumentException("No enum " + axJ.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private axJ() {
        this.hU = adb_1.oA();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private axJ() {
        void var3_1;
        this.hU = var3_1;
        adb_1.bF((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private axJ() {
        void var3_1;
        this.hU = var3_1.hU;
        adb_1.bF(this.hU + 1);
    }
}

