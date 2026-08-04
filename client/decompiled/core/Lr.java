/*
 * Decompiled with CFR 0.152.
 */
public enum Lr implements aiq_2
{
    bqx(1, 0, Integer.MAX_VALUE, 0, 50, 50),
    bqy(2, 0, Integer.MAX_VALUE, 0, 6, 6),
    bqz(3, 0, Integer.MAX_VALUE, 0, 3, 3),
    bqA(4, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0),
    bqB(5, -1000, 1000, -1000, 1000, 0),
    bqC(6, -1000, 1000, -1000, 1000, 0),
    bqD(7, -1000, 1000, -1000, 1000, 0),
    bqE(8, -1000, 1000, -1000, 1000, 0),
    bqF(9, -1000, 1000, -1000, 1000, 0),
    bqG(10, -1000, 1000, -1000, 1000, 0),
    bqH(11, -1000, 1000, -1000, 1000, 0),
    bqI(12, -1000, 1000, -1000, 1000, 0),
    bqJ(13, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0),
    bqK(14, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0),
    bqL(15, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0),
    bqM(16, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0),
    bqN(17, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 0),
    bqO(18, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    bqP(19, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    bqQ(20, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    bqR(21, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    bqS(22, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    bqT(23, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    bqU(24, 0, 100, 0, 100, 0),
    bqV(25, 0, 100, 0, 100, 0),
    bqW(26, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    bqX(29, -500, 500, -500, 500, 0),
    bqY(30, -100, 100, -100, 100, 0),
    bqZ(31, -100, 100, -100, 100, 0),
    bra(32, -1000, 1000, -1000, 1000, 0),
    brb(33, -1000, 1000, -1000, 1000, 0),
    brc(34, 0, 99, 0, 99, 0),
    brd(35, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 30),
    bre(36, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 100),
    brf(37, -1000, 1000, -1000, 1000, 0),
    brg(38, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    brh(39, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    bri(40, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    brj(41, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    brk(42, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0),
    brl(43, -1000, 1000, -1000, 1000, 0),
    brm(44, 0, Integer.MAX_VALUE, 0, 6, 6),
    brn(45, 0, Integer.MAX_VALUE, 0, 3, 3);

    private byte axW;
    private int bro;
    private int brp;
    private int brq;
    private int brr;
    private int brs;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private Lr(int n3, int n4, int n5, int n6) {
        void var8_6;
        void var7_5;
        this.axW = (byte)n3;
        this.bro = n4;
        this.brp = n5;
        this.brq = n6;
        this.brr = var7_5;
        this.brs = var8_6;
    }

    public byte lV() {
        return this.axW;
    }

    public byte Xy() {
        return 0;
    }

    public int Xz() {
        return this.bro;
    }

    public int XA() {
        return this.brp;
    }

    public int XB() {
        return this.brq;
    }

    public int XC() {
        return this.brr;
    }

    public int getDefaultValue() {
        return this.brs;
    }

    public boolean isExpandable() {
        return false;
    }
}

