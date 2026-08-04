/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aJQ
 */
public class ajq_2
extends acm_1 {
    private final short[] dRS = new short[8];
    private final byte[] dRU;
    private final byte[] dRT = new byte[2];
    public static final byte dSb = 8;
    public static final byte dSc = 2;

    public ajq_2() {
        this.dRU = new byte[162];
    }

    public int a(int n2, int n3, akd_0[] akd_0Array, int n4) {
        assert (this.a(n2, n3, akd_0Array));
        akd_0 akd_02 = akd_0Array[n4];
        akd_02.aG = n2;
        akd_02.aH = n3;
        akd_02.cCI = false;
        akd_02.aba = 0;
        int n5 = n3 - this.aH;
        int n6 = n2 - this.aG;
        int n7 = n5 * 18 + n6;
        byte by = (n7 & 1) != 0 ? (byte)(this.dRU[n7 >>> 1] & 0xFF & 0xF) : (byte)((this.dRU[n7 >>> 1] & 0xFF) >>> 4 & 0xF);
        akd_02.cCJ = this.dRT[by & 1];
        assert (by >>> 1 < 8);
        akd_02.wp = this.dRS[by >>> 1];
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
        int n5 = n3 - this.aH;
        int n6 = n2 - this.aG;
        int n7 = n5 * 18 + n6;
        byte by = (n7 & 1) != 0 ? (byte)(this.dRU[n7 >>> 1] & 0xF) : (byte)(this.dRU[n7 >>> 1] >>> 4 & 0xF);
        sl_12.wp = this.dRS[by >>> 1];
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

