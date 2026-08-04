/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from wq
 */
public abstract class wq_0
extends Enum {
    public static final /* enum */ wq_0 auA = new aai_0();
    public static final /* enum */ wq_0 auB = new aaj_2();
    private static final /* synthetic */ wq_0[] auC;

    public static final wq_0[] values() {
        return (wq_0[])auC.clone();
    }

    public static wq_0 valueOf(String string) {
        return Enum.valueOf(wq_0.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private wq_0() {
        void var2_-1;
        void var1_-1;
    }

    abstract int[] CI();

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ wq_0(aIs aIs2) {
        this((String)var1_-1, (int)var2_1);
        void var2_1;
        void var1_-1;
    }

    static {
        auC = new wq_0[]{auA, auB};
    }
}

