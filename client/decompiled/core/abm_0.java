/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from aBm
 */
public enum abm_0 {
    drf(XugglerJNI.IContainer_FLAG_GENPTS_get()),
    drg(XugglerJNI.IContainer_FLAG_IGNIDX_get()),
    drh(XugglerJNI.IContainer_FLAG_NONBLOCK_get()),
    dri(XugglerJNI.IContainer_FLAG_IGNDTS_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static abm_0 nj(int n2) {
        abm_0[] abm_0Array = (abm_0[])abm_0.class.getEnumConstants();
        if (n2 < abm_0Array.length && n2 >= 0 && abm_0Array[n2].hU == n2) {
            return abm_0Array[n2];
        }
        for (abm_0 abm_02 : abm_0Array) {
            if (abm_02.hU != n2) continue;
            return abm_02;
        }
        throw new IllegalArgumentException("No enum " + abm_0.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abm_0() {
        this.hU = apf_1.oA();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abm_0() {
        void var3_1;
        this.hU = var3_1;
        apf_1.bF((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abm_0() {
        void var3_1;
        this.hU = var3_1.hU;
        apf_1.bF(this.hU + 1);
    }
}

