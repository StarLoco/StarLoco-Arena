/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

public enum aby {
    cih(XugglerJNI.IAudioSamples_FMT_NONE_get()),
    cii,
    cij,
    cik,
    cil,
    cim;

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static aby jz(int n2) {
        aby[] abyArray = (aby[])aby.class.getEnumConstants();
        if (n2 < abyArray.length && n2 >= 0 && abyArray[n2].hU == n2) {
            return abyArray[n2];
        }
        for (aby aby2 : abyArray) {
            if (aby2.hU != n2) continue;
            return aby2;
        }
        throw new IllegalArgumentException("No enum " + aby.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aby() {
        this.hU = sB.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aby() {
        void var3_1;
        this.hU = var3_1;
        sB.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aby() {
        void var3_1;
        this.hU = var3_1.hU;
        sB.aT(this.hU + 1);
    }
}

