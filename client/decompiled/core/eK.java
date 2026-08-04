/*
 * Decompiled with CFR 0.152.
 */
import com.xuggle.xuggler.XugglerJNI;

public enum eK {
    pI,
    pJ,
    pK,
    pL,
    pM,
    pN,
    pO,
    pP,
    pQ(XugglerJNI.IProperty_PROPERTY_CONST_get()),
    pR(XugglerJNI.IProperty_PROPERTY_UNKNOWN_get());

    private final int hU;

    public final int dZ() {
        return this.hU;
    }

    public static eK ar(int n2) {
        eK[] eKArray = (eK[])eK.class.getEnumConstants();
        if (n2 < eKArray.length && n2 >= 0 && eKArray[n2].hU == n2) {
            return eKArray[n2];
        }
        for (eK eK2 : eKArray) {
            if (eK2.hU != n2) continue;
            return eK2;
        }
        throw new IllegalArgumentException("No enum " + eK.class + " with value " + n2);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private eK() {
        this.hU = ot_0.iw();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private eK() {
        void var3_1;
        this.hU = var3_1;
        ot_0.aT((int)(var3_1 + true));
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private eK() {
        void var3_1;
        this.hU = var3_1.hU;
        ot_0.aT(this.hU + 1);
    }
}

