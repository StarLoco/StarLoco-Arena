/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from UM
 */
public class um_0
extends auw_0 {
    public static final um_0 bRD = new um_0();

    private um_0() {
    }

    protected byte lV() {
        return 1;
    }

    protected void a(aij_1 aij_12, md_2 md_22, md_2 md_23) {
        boolean bl2 = !md_22.equals(md_23);
        aij_12.fe(bl2);
        aij_12.aVj();
        um_0.a(aij_12, bl2, md_22.buk, md_23.buk);
        um_0.a(aij_12, bl2, md_22.bul, md_23.bul);
    }

    public md_2 m(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = um_0.b(acf2, bl2, f);
        float f3 = um_0.b(acf2, bl2, f);
        return new md_2(f2, f3);
    }

    protected boolean a(md_2 md_22, md_2 md_23) {
        return md_22.bul == md_23.bul && md_22.buk == md_23.buk;
    }
}

