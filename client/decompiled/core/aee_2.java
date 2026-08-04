/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aeE
 */
public enum aee_2 {
    cpa(0),
    cpb(1),
    cpc(2);

    private static final Logger a;
    public byte lB;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aee_2() {
        void var3_1;
        this.lB = var3_1;
    }

    public static aee_2 kh(int n2) {
        int n3 = aee_2.values().length;
        for (int j = 0; j < n3; ++j) {
            aee_2 aee_22 = aee_2.values()[j];
            if (aee_22.lB != n2) continue;
            return aee_22;
        }
        return null;
    }

    public static el_0 a(aee_2 aee_22) {
        switch (aee_22) {
            case cpa: {
                return new aqt_0();
            }
            case cpb: {
                return new afu_1();
            }
            case cpc: {
                return new Bf();
            }
        }
        a.error((Object)("Impossible de trouver le FireworkType " + (Object)((Object)aee_22)), (Throwable)new IllegalArgumentException());
        return null;
    }

    static {
        a = Logger.getLogger(aee_2.class);
    }
}

