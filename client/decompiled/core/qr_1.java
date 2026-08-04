/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from QR
 */
public enum qr_1 {
    bHP(XugglerJNI.IPixelFormat_YUV_Y_get()),
    bHQ(XugglerJNI.IPixelFormat_YUV_U_get()),
    bHR(XugglerJNI.IPixelFormat_YUV_V_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static qr_1 hr(int n2) {
        qr_1[] qr_1Array = (qr_1[])qr_1.class.getEnumConstants();
        if (n2 < qr_1Array.length && n2 >= 0 && qr_1Array[n2].hU == n2) {
            return qr_1Array[n2];
        }
        for (qr_1 qr_12 : qr_1Array) {
            if (qr_12.hU != n2) continue;
            return qr_12;
        }
        throw new IllegalArgumentException("No enum " + qr_1.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private qr_1() {
        this.hU = BQ.oA();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private qr_1() {
        void var3_1;
        this.hU = var3_1;
        BQ.bF((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private qr_1() {
        void var3_1;
        this.hU = var3_1.hU;
        BQ.bF(this.hU + 1);
    }
}

