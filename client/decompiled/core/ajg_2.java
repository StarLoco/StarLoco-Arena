/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aJG
 */
public class ajg_2
extends acm_1 {
    private final short[] dRS = new short[16];
    private final byte[] dRT = new byte[4];
    private final byte[] dRU = new byte[324];
    public static final int dRV = 7;
    public static final int dRW = 6;
    public static final int dRJ = 4;
    public static final int dRK = 0;
    public static final byte dRX = -128;
    public static final byte dRY = 64;
    public static final byte dRZ = 48;
    public static final byte dSa = 15;
    public static final byte dSb = 16;
    public static final byte dSc = 4;

    public int a(int n2, int n3, akd_0[] akd_0Array, int n4) {
        assert (this.a(n2, n3, akd_0Array));
        akd_0 akd_02 = akd_0Array[n4];
        akd_02.aG = n2;
        akd_02.aH = n3;
        akd_02.cCI = false;
        akd_02.aba = 0;
        int n5 = n2 - this.aG;
        int n6 = n3 - this.aH;
        int n7 = n6 * 18 + n5;
        byte by = this.dRU[n7];
        akd_02.wp = this.dRS[(by & 0xF) >>> 0];
        akd_02.cCJ = this.dRT[(by & 0x30) >>> 4];
        akd_02.cCK = 0;
        return 1;
    }

    public int a(int n2, int n3, sl_1[] sl_1Array, int n4) {
        assert (this.a(n2, n3, sl_1Array));
        sl_1 sl_12 = sl_1Array[n4];
        sl_12.aG = n2;
        sl_12.aH = n3;
        sl_12.aiT = false;
        sl_12.aba = 0;
        int n5 = n2 - this.aG;
        int n6 = n3 - this.aH;
        int n7 = n6 * 18 + n5;
        byte by = this.dRU[n7];
        sl_12.wp = this.dRS[(by & 0xF) >>> 0];
        return 1;
    }

    public void b(acf acf2) {
        int n2;
        super.b(acf2);
        for (n2 = 0; n2 < this.dRS.length; ++n2) {
            this.dRS[n2] = (short)(this.wp + acf2.readShort());
        }
        for (n2 = 0; n2 < this.dRT.length; ++n2) {
            this.dRT[n2] = acf2.readByte();
        }
        for (n2 = 0; n2 < this.dRU.length; ++n2) {
            this.dRU[n2] = acf2.readByte();
        }
    }
}

