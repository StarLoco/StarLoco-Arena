/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.ferry.FerryJNI;

public enum bX {
    hP(FerryJNI.Logger_LEVEL_ERROR_get()),
    hQ(FerryJNI.Logger_LEVEL_WARN_get()),
    hR(FerryJNI.Logger_LEVEL_INFO_get()),
    hS(FerryJNI.Logger_LEVEL_DEBUG_get()),
    hT(FerryJNI.Logger_LEVEL_TRACE_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static bX G(int n2) {
        bX[] bXArray = (bX[])bX.class.getEnumConstants();
        if (n2 < bXArray.length && n2 >= 0 && bXArray[n2].hU == n2) {
            return bXArray[n2];
        }
        for (bX bX2 : bXArray) {
            if (bX2.hU != n2) continue;
            return bX2;
        }
        throw new IllegalArgumentException("No enum " + bX.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private bX() {
        this.hU = ahs_1.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private bX() {
        void var3_1;
        this.hU = var3_1;
        ahs_1.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private bX() {
        void var3_1;
        this.hU = var3_1.hU;
        ahs_1.aT(this.hU + 1);
    }
}

