/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.keplerproject.luajava.LuaState
 */
import org.keplerproject.luajava.LuaState;

/*
 * Renamed from aOs
 */
public abstract class aos_1
extends Enum {
    public static final /* enum */ aos_1 elQ = new apz();
    public static final /* enum */ aos_1 elR = new apb_0();
    public static final /* enum */ aos_1 elS = new apa_1();
    public static final /* enum */ aos_1 elT = new apr_1();
    public static final /* enum */ aos_1 elU = new apu_1();
    public static final /* enum */ aos_1 elV = new apW();
    public static final /* enum */ aos_1 elW = new apx_0();
    public static final /* enum */ aos_1 elX = new apz_0();
    public static final /* enum */ aos_1 elY = new aqa_0();
    private static final /* synthetic */ aos_1[] elZ;

    public static final aos_1[] values() {
        return (aos_1[])elZ.clone();
    }

    public static aos_1 valueOf(String string) {
        return Enum.valueOf(aos_1.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private aos_1() {
        void var2_-1;
        void var1_-1;
    }

    final boolean b(aos_1 aos_12) {
        if (this == aos_12) {
            return true;
        }
        if (this == elT) {
            return aos_12 == elU;
        }
        if (this == elU) {
            return aos_12 == elT;
        }
        return false;
    }

    public abstract boolean b(LuaState var1, int var2);

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ aos_1(apz apz2) {
        this((String)var1_-1, (int)var2_1);
        void var2_1;
        void var1_-1;
    }

    static {
        elZ = new aos_1[]{elQ, elR, elS, elT, elU, elV, elW, elX, elY};
    }
}

