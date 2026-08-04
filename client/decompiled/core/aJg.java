/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

public enum aJg {
    dRh(XugglerJNI.ICodec_CAP_DRAW_HORIZ_BAND_get()),
    dRi(XugglerJNI.ICodec_CAP_DR1_get()),
    dRj(XugglerJNI.ICodec_CAP_PARSE_ONLY_get()),
    dRk(XugglerJNI.ICodec_CAP_TRUNCATED_get()),
    dRl(XugglerJNI.ICodec_CAP_HWACCEL_get()),
    dRm(XugglerJNI.ICodec_CAP_DELAY_get()),
    dRn(XugglerJNI.ICodec_CAP_SMALL_LAST_FRAME_get()),
    dRo(XugglerJNI.ICodec_CAP_HWACCEL_VDPAU_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static aJg oQ(int n2) {
        aJg[] aJgArray = (aJg[])aJg.class.getEnumConstants();
        if (n2 < aJgArray.length && n2 >= 0 && aJgArray[n2].hU == n2) {
            return aJgArray[n2];
        }
        for (aJg aJg2 : aJgArray) {
            if (aJg2.hU != n2) continue;
            return aJg2;
        }
        throw new IllegalArgumentException("No enum " + aJg.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aJg() {
        this.hU = rN.xN();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aJg() {
        void var3_1;
        this.hU = var3_1;
        rN.dv((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aJg() {
        void var3_1;
        this.hU = var3_1.hU;
        rN.dv(this.hU + 1);
    }
}

