/*
 * Decompiled with CFR 0.152.
 */
public class wW
extends gg_0 {
    public static final wW avP = new wW();

    private wW() {
    }

    protected byte lV() {
        return 5;
    }

    public ir_1 h(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = wW.b(acf2, bl2, f);
        float f3 = wW.b(acf2, bl2, f);
        float f4 = wW.b(acf2, bl2, f);
        float f5 = wW.b(acf2, bl2, f);
        float f6 = wW.b(acf2, bl2, f);
        float f7 = wW.b(acf2, bl2, f);
        return new ir_1(f2, f3, f4, f5, f6, f7);
    }

    protected void a(aij_1 aij_12, ir_1 ir_12, ir_1 ir_13) {
        boolean bl2 = !this.a(ir_12, ir_13);
        aij_12.fe(bl2);
        aij_12.aVj();
        wW.a(aij_12, bl2, ir_12.aeO, ir_13.aeO);
        wW.a(aij_12, bl2, ir_12.biy, ir_13.biy);
        wW.a(aij_12, bl2, ir_12.KS, ir_13.KS);
        wW.a(aij_12, bl2, ir_12.biz, ir_13.biz);
        wW.a(aij_12, bl2, ir_12.biA, ir_13.biA);
        wW.a(aij_12, bl2, ir_12.biB, ir_13.biB);
    }

    protected boolean a(ir_1 ir_12, ir_1 ir_13) {
        return ir_12.aeO == ir_13.aeO && ir_12.biy == ir_13.biy && ir_12.KS == ir_13.KS && ir_12.biz == ir_13.biz && ir_12.biA == ir_13.biA && ir_12.biB == ir_13.biB;
    }
}

