/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from aBU
 */
public enum abu_2 {
    dtI(XugglerJNI.IContainerFormat_FLAG_NOFILE_get()),
    dtJ(XugglerJNI.IContainerFormat_FLAG_NEEDNUMBER_get()),
    dtK(XugglerJNI.IContainerFormat_FLAG_SHOW_IDS_get()),
    dtL(XugglerJNI.IContainerFormat_FLAG_RAWPICTURE_get()),
    dtM(XugglerJNI.IContainerFormat_FLAG_GLOBALHEADER_get()),
    dtN(XugglerJNI.IContainerFormat_FLAG_NOTIMESTAMPS_get()),
    dtO(XugglerJNI.IContainerFormat_FLAG_GENERIC_INDEX_get()),
    dtP(XugglerJNI.IContainerFormat_FLAG_TS_DISCONT_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static abu_2 nm(int n2) {
        abu_2[] abu_2Array = (abu_2[])abu_2.class.getEnumConstants();
        if (n2 < abu_2Array.length && n2 >= 0 && abu_2Array[n2].hU == n2) {
            return abu_2Array[n2];
        }
        for (abu_2 abu_22 : abu_2Array) {
            if (abu_22.hU != n2) continue;
            return abu_22;
        }
        throw new IllegalArgumentException("No enum " + abu_2.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abu_2() {
        this.hU = aug_0.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abu_2() {
        void var3_1;
        this.hU = var3_1;
        aug_0.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abu_2() {
        void var3_1;
        this.hU = var3_1.hU;
        aug_0.aT(this.hU + 1);
    }
}

