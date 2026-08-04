/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from sD
 */
public enum sd_2 {
    akN(XugglerJNI.IMetaData_METADATA_NONE_get()),
    akO(XugglerJNI.IMetaData_METADATA_MATCH_CASE_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static sd_2 dB(int n2) {
        sd_2[] sd_2Array = (sd_2[])sd_2.class.getEnumConstants();
        if (n2 < sd_2Array.length && n2 >= 0 && sd_2Array[n2].hU == n2) {
            return sd_2Array[n2];
        }
        for (sd_2 sd_22 : sd_2Array) {
            if (sd_22.hU != n2) continue;
            return sd_22;
        }
        throw new IllegalArgumentException("No enum " + sd_2.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private sd_2() {
        this.hU = ayk.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private sd_2() {
        void var3_1;
        this.hU = var3_1;
        ayk.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private sd_2() {
        void var3_1;
        this.hU = var3_1.hU;
        ayk.aT(this.hU + 1);
    }
}

