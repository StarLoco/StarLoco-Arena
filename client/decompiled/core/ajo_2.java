/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aJO
 */
public final class ajo_2
extends acm_1 {
    private byte cCJ;

    public final int a(int n2, int n3, akd_0[] akd_0Array, int n4) {
        assert (this.a(n2, n3, akd_0Array));
        akd_0 akd_02 = akd_0Array[n4];
        akd_02.aG = n2;
        akd_02.aH = n3;
        akd_02.wp = this.wp;
        akd_02.cCJ = this.cCJ;
        akd_02.cCI = false;
        akd_02.aba = 0;
        akd_02.cCK = 0;
        return 1;
    }

    public int a(int n2, int n3, sl_1[] sl_1Array, int n4) {
        assert (this.a(n2, n3, sl_1Array));
        sl_1 sl_12 = sl_1Array[n4];
        sl_12.aG = n2;
        sl_12.aH = n3;
        sl_12.wp = this.wp;
        sl_12.aiT = false;
        sl_12.aba = 0;
        return 1;
    }

    public void b(acf acf2) {
        super.b(acf2);
        this.cCJ = acf2.readByte();
    }
}

