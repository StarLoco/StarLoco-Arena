/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from Lv
 */
public enum lv_1 {
    brx(XugglerJNI.IProperty_FLAG_ENCODING_PARAM_get()),
    bry(XugglerJNI.IProperty_FLAG_DECODING_PARAM_get()),
    brz(XugglerJNI.IProperty_FLAG_METADATA_get()),
    brA(XugglerJNI.IProperty_FLAG_AUDIO_PARAM_get()),
    brB(XugglerJNI.IProperty_FLAG_VIDEO_PARAM_get()),
    brC(XugglerJNI.IProperty_FLAG_SUBTITLE_PARAM_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static lv_1 gF(int n2) {
        lv_1[] lv_1Array = (lv_1[])lv_1.class.getEnumConstants();
        if (n2 < lv_1Array.length && n2 >= 0 && lv_1Array[n2].hU == n2) {
            return lv_1Array[n2];
        }
        for (lv_1 lv_12 : lv_1Array) {
            if (lv_12.hU != n2) continue;
            return lv_12;
        }
        throw new IllegalArgumentException("No enum " + lv_1.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private lv_1() {
        this.hU = aaw_2.oA();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private lv_1() {
        void var3_1;
        this.hU = var3_1;
        aaw_2.bF((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private lv_1() {
        void var3_1;
        this.hU = var3_1.hU;
        aaw_2.bF(this.hU + 1);
    }
}

