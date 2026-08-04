/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aJC
 */
public class ajc_2
extends acm_1 {
    private int[] dRA;
    private byte[] dRB;
    private byte[] dRC;
    public static final int dRD = -16777216;
    public static final int dRE = 0xF00000;
    public static final int dRF = 1047552;
    public static final int dRG = 992;
    public static final int dRH = 31;
    public static final int dRI = 24;
    public static final int dRJ = 20;
    public static final int dRK = 10;
    public static final int dRL = 5;
    public static final int dRM = 0;
    public static final short dRN = 512;

    public int a(int n2, int n3, akd_0[] akd_0Array, int n4) {
        int n5;
        int n6;
        int n7;
        assert (this.a(n2, n3, akd_0Array));
        int n8 = n2 - this.aG;
        int n9 = n3 - this.aH;
        int n10 = 1;
        int n11 = 0;
        int n12 = this.dRA.length - 1;
        int n13 = -1;
        do {
            assert (n12 != n11);
            int n14 = n12 + n11 >>> 1;
            if (n11 + 1 == n12) {
                n7 = this.dRA[n11];
                n6 = (n7 & 0x3E0) >>> 5;
                n5 = (n7 & 0x1F) >>> 0;
                if (n8 == n5 && n9 == n6) {
                    n13 = n11;
                    continue;
                }
                n13 = n12;
                continue;
            }
            n7 = this.dRA[n14];
            n6 = (n7 & 0x3E0) >>> 5;
            if (n6 > n9) {
                n12 = n14;
                continue;
            }
            if (n6 < n9) {
                n11 = n14;
                continue;
            }
            n5 = (n7 & 0x1F) >>> 0;
            if (n5 > n8) {
                n12 = n14;
                continue;
            }
            if (n5 < n8) {
                n11 = n14;
                continue;
            }
            n13 = n14;
        } while (n13 == -1);
        n11 = n13;
        while (n11 - n10 >= 0 && (n6 = ((n7 = this.dRA[n13 - n10]) & 0x1F) >>> 0) == n8 && (n5 = (n7 & 0x3E0) >>> 5) == n9) {
            ++n10;
        }
        n11 = n11 + 1 - n10;
        while (n13 + 1 < this.dRA.length && (n6 = ((n7 = this.dRA[++n13]) & 0x1F) >>> 0) == n8 && (n5 = (n7 & 0x3E0) >>> 5) == n9) {
            ++n10;
        }
        for (n7 = 0; n7 < n10; ++n7) {
            n13 = n11 + n7;
            n6 = this.dRA[n13];
            akd_0 akd_02 = akd_0Array[n4 + n7];
            akd_02.aG = n2;
            akd_02.aH = n3;
            int n15 = (n6 & 0xFFC00) >>> 10;
            akd_02.wp = (short)(n15 != 0 ? (int)(this.wp - 512 + n15) : Short.MIN_VALUE);
            akd_02.cCJ = (byte)((n6 & 0xF00000) >>> 20);
            if (akd_02.cCJ == 15) {
                akd_02.cCJ = (byte)-1;
            }
            akd_02.aba = (byte)((n6 & 0xFF000000) >>> 24);
            akd_02.cCI = (n13 & 1) == 0 ? (this.dRB[n13 >>> 1] >>> 4 & 1) != 0 : (this.dRB[n13 >>> 1] & 1) != 0;
            akd_02.cCK = this.dRC[n13];
        }
        return n10;
    }

    public int a(int n2, int n3, sl_1[] sl_1Array, int n4) {
        int n5;
        int n6;
        int n7;
        assert (this.a(n2, n3, sl_1Array));
        int n8 = n2 - this.aG;
        int n9 = n3 - this.aH;
        int n10 = 1;
        int n11 = 0;
        int n12 = this.dRA.length - 1;
        int n13 = -1;
        do {
            assert (n12 != n11);
            int n14 = n12 + n11 >>> 1;
            if (n11 + 1 == n12) {
                n7 = this.dRA[n11];
                n6 = (n7 & 0x3E0) >>> 5;
                n5 = (n7 & 0x1F) >>> 0;
                if (n8 == n5 && n9 == n6) {
                    n13 = n11;
                    continue;
                }
                n13 = n12;
                continue;
            }
            n7 = this.dRA[n14];
            n6 = (n7 & 0x3E0) >>> 5;
            if (n6 > n9) {
                n12 = n14;
                continue;
            }
            if (n6 < n9) {
                n11 = n14;
                continue;
            }
            n5 = (n7 & 0x1F) >>> 0;
            if (n5 > n8) {
                n12 = n14;
                continue;
            }
            if (n5 < n8) {
                n11 = n14;
                continue;
            }
            n13 = n14;
        } while (n13 == -1);
        n11 = n13;
        while (n11 - n10 >= 0 && (n6 = ((n7 = this.dRA[n13 - n10]) & 0x1F) >>> 0) == n8 && (n5 = (n7 & 0x3E0) >>> 5) == n9) {
            ++n10;
        }
        n11 = n11 + 1 - n10;
        while (n13 + 1 < this.dRA.length && (n6 = ((n7 = this.dRA[++n13]) & 0x1F) >>> 0) == n8 && (n5 = (n7 & 0x3E0) >>> 5) == n9) {
            ++n10;
        }
        for (n7 = 0; n7 < n10; ++n7) {
            n13 = n11 + n7;
            n6 = this.dRA[n13];
            sl_1 sl_12 = sl_1Array[n4 + n7];
            sl_12.aG = n2;
            sl_12.aH = n3;
            sl_12.wp = (short)(this.wp - 512 + ((n6 & 0xFFC00) >>> 10));
            sl_12.aba = (byte)((n6 & 0xFF000000) >>> 24);
            sl_12.aiT = (n13 & 1) == 0 ? (this.dRB[n13 >>> 1] >>> 4 & 2) != 0 : (this.dRB[n13 >>> 1] & 2) != 0;
        }
        return n10;
    }

    public void b(acf acf2) {
        int n2;
        super.b(acf2);
        short s = acf2.readShort();
        this.dRA = new int[s];
        this.dRB = new byte[s + 1 >>> 1];
        this.dRC = new byte[s];
        for (n2 = 0; n2 < this.dRA.length; ++n2) {
            this.dRA[n2] = acf2.readInt();
        }
        for (n2 = 0; n2 < this.dRB.length; ++n2) {
            this.dRB[n2] = acf2.readByte();
        }
        for (n2 = 0; n2 < this.dRC.length; ++n2) {
            this.dRC[n2] = acf2.readByte();
        }
    }
}

