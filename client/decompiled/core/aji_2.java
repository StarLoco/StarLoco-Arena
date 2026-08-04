/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aJI
 */
public class aji_2
extends acm_1 {
    private final short[] cLr = new short[324];
    public static final short dSd = -4096;
    public static final short dSe = 2048;
    public static final short dSf = 1024;
    public static final short dSg = 1023;
    public static final int dRJ = 12;
    public static final int dSh = 11;
    public static final int dRW = 10;
    public static final int dRK = 0;
    public static final short dRN = 512;

    public int a(int n2, int n3, akd_0[] akd_0Array, int n4) {
        assert (this.a(n2, n3, akd_0Array));
        akd_0 akd_02 = akd_0Array[n4];
        akd_02.aG = n2;
        akd_02.aH = n3;
        akd_02.aba = 0;
        akd_02.cCI = false;
        int n5 = n2 - this.aG;
        int n6 = n3 - this.aH;
        int n7 = n6 * 18 + n5;
        short s = this.cLr[n7];
        akd_02.cCJ = (byte)((s & 0xFFFFF000) >>> 12);
        int n8 = (s & 0x3FF) >>> 0;
        akd_02.wp = (short)(n8 != 0 ? (int)(this.wp - 512 + n8) : Short.MIN_VALUE);
        akd_02.cCK = 0;
        return 1;
    }

    public int a(int n2, int n3, sl_1[] sl_1Array, int n4) {
        assert (this.a(n2, n3, sl_1Array));
        sl_1 sl_12 = sl_1Array[n4];
        sl_12.aG = n2;
        sl_12.aH = n3;
        sl_12.aba = 0;
        int n5 = n2 - this.aG;
        int n6 = n3 - this.aH;
        int n7 = n6 * 18 + n5;
        short s = this.cLr[n7];
        sl_12.wp = (short)(this.wp - 512 + ((s & 0x3FF) >>> 0));
        sl_12.aiT = false;
        return 1;
    }

    public void b(acf acf2) {
        super.b(acf2);
        for (int j = 0; j < this.cLr.length; ++j) {
            this.cLr[j] = acf2.readShort();
        }
    }
}

