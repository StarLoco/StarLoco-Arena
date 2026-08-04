/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from yi
 */
public enum yi_0 {
    aAM(XugglerJNI.IPixelFormat_NONE_get()),
    aAN,
    aAO,
    aAP,
    aAQ,
    aAR,
    aAS,
    aAT,
    aAU,
    aAV,
    aAW,
    aAX,
    aAY,
    aAZ,
    aBa,
    aBb,
    aBc,
    aBd,
    aBe,
    aBf,
    aBg,
    aBh,
    aBi,
    aBj,
    aBk,
    aBl,
    aBm,
    aBn,
    aBo,
    aBp,
    aBq,
    aBr,
    aBs,
    aBt,
    aBu,
    aBv,
    aBw,
    aBx,
    aBy,
    aBz,
    aBA,
    aBB,
    aBC,
    aBD,
    aBE,
    aBF,
    aBG,
    aBH,
    aBI,
    aBJ,
    aBK,
    aBL,
    aBM,
    aBN,
    aBO,
    aBP,
    aBQ,
    aBR,
    aBS,
    aBT,
    aBU,
    aBV;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static yi_0 ev(int n2) {
        yi_0[] yi_0Array = (yi_0[])yi_0.class.getEnumConstants();
        if (n2 < yi_0Array.length && n2 >= 0 && yi_0Array[n2].hU == n2) {
            return yi_0Array[n2];
        }
        for (yi_0 yi_02 : yi_0Array) {
            if (yi_02.hU != n2) continue;
            return yi_02;
        }
        throw new IllegalArgumentException("No enum " + yi_0.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private yi_0() {
        this.hU = amq_0.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private yi_0() {
        void var3_1;
        this.hU = var3_1;
        amq_0.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private yi_0() {
        void var3_1;
        this.hU = var3_1.hU;
        amq_0.aT(this.hU + 1);
    }
}

