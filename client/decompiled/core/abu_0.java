/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

/*
 * Renamed from abU
 */
public enum abu_0 {
    ciT(XugglerJNI.IVideoPicture_DEFAULT_TYPE_get()),
    ciU(XugglerJNI.IVideoPicture_I_TYPE_get()),
    ciV(XugglerJNI.IVideoPicture_P_TYPE_get()),
    ciW(XugglerJNI.IVideoPicture_B_TYPE_get()),
    ciX(XugglerJNI.IVideoPicture_S_TYPE_get()),
    ciY(XugglerJNI.IVideoPicture_SI_TYPE_get()),
    ciZ(XugglerJNI.IVideoPicture_SP_TYPE_get()),
    cja(XugglerJNI.IVideoPicture_BI_TYPE_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static abu_0 jA(int n2) {
        abu_0[] abu_0Array = (abu_0[])abu_0.class.getEnumConstants();
        if (n2 < abu_0Array.length && n2 >= 0 && abu_0Array[n2].hU == n2) {
            return abu_0Array[n2];
        }
        for (abu_0 abu_02 : abu_0Array) {
            if (abu_02.hU != n2) continue;
            return abu_02;
        }
        throw new IllegalArgumentException("No enum " + abu_0.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abu_0() {
        this.hU = afv_2.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abu_0() {
        void var3_1;
        this.hU = var3_1;
        afv_2.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private abu_0() {
        void var3_1;
        this.hU = var3_1.hU;
        afv_2.aT(this.hU + 1);
    }
}

