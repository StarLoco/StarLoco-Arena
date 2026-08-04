/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from avx
 */
public enum avx_0 implements aak_2
{
    det(0),
    deu(1),
    dev(2),
    dew(3),
    dex(4),
    dey(5),
    dez(6),
    deA(7),
    deB(8),
    deC(9),
    deD(10),
    deE(11),
    deF(12),
    deG(13),
    deH(14),
    deI(15),
    deJ(16);

    private final byte deK;
    private static final afj_0 ayv;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private avx_0() {
        void var3_1;
        this.deK = (byte)var3_1;
    }

    public byte aps() {
        return 0;
    }

    public byte lV() {
        return this.deK;
    }

    public static avx_0 a(Byte by) {
        return (avx_0)ayv.bk(by);
    }

    static {
        ayv = new afj_0();
        for (avx_0 avx_02 : avx_0.values()) {
            ayv.b(avx_02.lV(), avx_02);
        }
    }
}

