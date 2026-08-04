/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abo
 */
public class abo_1
extends gg_0 {
    public static final abo_1 chV = new abo_1();

    private abo_1() {
    }

    protected byte lV() {
        return 8;
    }

    public lv o(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        boolean bl3 = acf2.aqE();
        float f2 = abo_1.b(acf2, bl2, f);
        float f3 = abo_1.b(acf2, bl2, f);
        float f4 = abo_1.b(acf2, bl2, f);
        return new lv(f2, f3, f4, bl3);
    }

    protected void a(aij_1 aij_12, lv lv2, lv lv3) {
        boolean bl2 = !this.a(lv2, lv3);
        aij_12.fe(bl2);
        aij_12.fe(lv2.Hn);
        aij_12.aVj();
        abo_1.a(aij_12, bl2, lv2.Hk, lv3.Hk);
        abo_1.a(aij_12, bl2, lv2.Hl, lv3.Hl);
        abo_1.a(aij_12, bl2, lv2.Hm, lv3.Hm);
    }

    protected boolean a(lv lv2, lv lv3) {
        return lv2.Hk == lv3.Hk && lv2.Hl == lv3.Hl && lv2.Hm == lv3.Hm;
    }
}

