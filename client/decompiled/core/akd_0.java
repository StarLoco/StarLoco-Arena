/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from akd
 */
public final class akd_0 {
    public int aG;
    public int aH;
    public boolean cCI;
    public short wp;
    public byte cCJ;
    public byte aba;
    public byte cCK = 0;
    public static final int cCL = 128;
    public static final int cCM = 64;
    private static final int cCN = 192;
    public static final int cCO = 48;
    public static final int cCP = 16;
    public static final int cCQ = 48;
    public static final int cCR = -49;
    public static final int cCS = -241;
    public static final int cCT = 0;
    public static final int cCU = 128;
    public static final int cCV = 64;
    public static final int cCW = 192;

    public akd_0() {
    }

    public akd_0(akd_0 akd_02) {
        this.a(akd_02);
    }

    public static akd_0[] azF() {
        akd_0[] akd_0Array = new akd_0[32];
        for (int j = 0; j < akd_0Array.length; ++j) {
            akd_0Array[j] = new akd_0();
        }
        return akd_0Array;
    }

    public static short a(akd_0[] akd_0Array, acm_1 acm_12, int n2, int n3, short s) {
        short s2;
        block2: {
            int n4;
            block1: {
                s2 = -1;
                n4 = acm_12.a(n2, n3, akd_0Array, 0);
                if (n4 != 1) break block1;
                if (akd_0Array[0].wp != s) break block2;
                s2 = 0;
                break block2;
            }
            for (int j = 0; j < n4; ++j) {
                if (akd_0Array[j].wp != s || akd_0Array[j].cCI) continue;
                s2 = (short)j;
                break;
            }
        }
        return s2;
    }

    public void a(akd_0 akd_02) {
        this.aG = akd_02.aG;
        this.aH = akd_02.aH;
        this.cCI = akd_02.cCI;
        this.wp = akd_02.wp;
        this.cCJ = akd_02.cCJ;
        this.aba = akd_02.aba;
        this.cCK = akd_02.cCK;
    }

    public String toString() {
        return new StringBuilder(28).append("CellPathData(").append(this.aG).append(",").append(this.aH).append(",").append(this.wp).append(",").append(this.aba).append(")").toString();
    }

    public final int azG() {
        return this.cCK & 0xC0;
    }

    public final int azH() {
        return this.cCK;
    }

    public static boolean lg(int n2) {
        return (n2 & 0x80) == 128;
    }

    public static boolean lh(int n2) {
        return (n2 & 0x40) == 64;
    }
}

