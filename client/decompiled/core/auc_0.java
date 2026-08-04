/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from auc
 */
public enum auc_0 {
    cVN(XugglerJNI.ICodec_CODEC_TYPE_UNKNOWN_get()),
    cVO,
    cVP,
    cVQ,
    cVR,
    cVS;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static auc_0 mq(int n2) {
        auc_0[] auc_0Array = (auc_0[])auc_0.class.getEnumConstants();
        if (n2 < auc_0Array.length && n2 >= 0 && auc_0Array[n2].hU == n2) {
            return auc_0Array[n2];
        }
        for (auc_0 auc_02 : auc_0Array) {
            if (auc_02.hU != n2) continue;
            return auc_02;
        }
        throw new IllegalArgumentException("No enum " + auc_0.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private auc_0() {
        this.hU = avd_0.oA();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private auc_0() {
        void var3_1;
        this.hU = var3_1;
        avd_0.bF((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private auc_0() {
        void var3_1;
        this.hU = var3_1.hU;
        avd_0.bF(this.hU + 1);
    }
}

