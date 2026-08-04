/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aAP
 */
abstract class aap_1
extends Enum {
    public static final /* enum */ aap_1 dqd = new MB(0, afj_1.class);
    public static final /* enum */ aap_1 dqe = new my_0(1, tU.class);
    public static final /* enum */ aap_1 dqf = new Mz(2, aup_0.class);
    byte axW;
    Class dqg;
    private static final /* synthetic */ aap_1[] dqh;

    public static final aap_1[] values() {
        return (aap_1[])dqh.clone();
    }

    public static aap_1 valueOf(String string) {
        return Enum.valueOf(aap_1.class, string);
    }

    /*
     * WARNING - void declaration
     */
    private aap_1() {
        void var4_2;
        void var3_1;
        void var2_-1;
        void var1_-1;
        this.axW = var3_1;
        this.dqg = var4_2;
    }

    static aap_1 I(Class clazz) {
        for (aap_1 aap_12 : aap_1.values()) {
            if (aap_12.dqg != clazz) continue;
            return aap_12;
        }
        throw new IllegalArgumentException("Pas s\u00e9rialisable: " + clazz.getSimpleName());
    }

    static aap_1 bd(byte by) {
        for (aap_1 aap_12 : aap_1.values()) {
            if (aap_12.axW != by) continue;
            return aap_12;
        }
        throw new IllegalArgumentException("Pas s\u00e9rialisable: id " + by);
    }

    public void a(atD atD2, ByteBuffer byteBuffer) {
        if (atD2.getClass() != this.dqg) {
            throw new IllegalArgumentException();
        }
        byteBuffer.put(this.axW);
        atD2.A(byteBuffer);
    }

    public int b(atD atD2) {
        return 1 + atD2.TI();
    }

    abstract atD YH();

    public static atD L(ByteBuffer byteBuffer) {
        return aap_1.bd(byteBuffer.get()).YH();
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    /* synthetic */ aap_1(byte by, Class clazz, MB mB) {
        this((String)var1_-1, (int)clazz, (byte)mB, (Class)var4_3);
        void var4_3;
        void var1_-1;
    }

    static {
        dqh = new aap_1[]{dqd, dqe, dqf};
    }
}

