/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from anT
 */
public enum ant_1 {
    cKc(XugglerJNI.IRational_ROUND_ZERO_get()),
    cKd(XugglerJNI.IRational_ROUND_INF_get()),
    cKe(XugglerJNI.IRational_ROUND_DOWN_get()),
    cKf(XugglerJNI.IRational_ROUND_UP_get()),
    cKg(XugglerJNI.IRational_ROUND_NEAR_INF_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static ant_1 lA(int n2) {
        ant_1[] ant_1Array = (ant_1[])ant_1.class.getEnumConstants();
        if (n2 < ant_1Array.length && n2 >= 0 && ant_1Array[n2].hU == n2) {
            return ant_1Array[n2];
        }
        for (ant_1 ant_12 : ant_1Array) {
            if (ant_12.hU != n2) continue;
            return ant_12;
        }
        throw new IllegalArgumentException("No enum " + ant_1.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ant_1() {
        this.hU = ts_2.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ant_1() {
        void var3_1;
        this.hU = var3_1;
        ts_2.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private ant_1() {
        void var3_1;
        this.hU = var3_1.hU;
        ts_2.aT(this.hU + 1);
    }
}

