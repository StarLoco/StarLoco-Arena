/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aOP
 */
public class aop_2
extends gg_0 {
    public static final aop_2 emQ = new aop_2();

    private aop_2() {
    }

    protected byte lV() {
        return 4;
    }

    public oo_0 w(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = aop_2.b(acf2, bl2, f);
        float f3 = aop_2.b(acf2, bl2, f);
        float f4 = aop_2.b(acf2, bl2, f);
        float f5 = aop_2.b(acf2, bl2, f);
        float f6 = aop_2.b(acf2, bl2, f);
        return new oo_0(f2, f3, f4, f5, f6);
    }

    protected void a(aij_1 aij_12, oo_0 oo_02, oo_0 oo_03) {
        boolean bl2 = !this.a(oo_02, oo_03);
        aij_12.fe(bl2);
        aij_12.aVj();
        aop_2.a(aij_12, bl2, oo_02.IQ, oo_03.IQ);
        aop_2.a(aij_12, bl2, oo_02.IR, oo_03.IR);
        aop_2.a(aij_12, bl2, oo_02.IS, oo_03.IS);
        aop_2.a(aij_12, bl2, oo_02.IT, oo_03.IT);
        aop_2.a(aij_12, bl2, oo_02.aaS, oo_03.aaS);
    }

    protected boolean a(oo_0 oo_02, oo_0 oo_03) {
        return oo_02.IQ == oo_03.IQ && oo_02.IR == oo_03.IR && oo_02.IS == oo_03.IS && oo_02.IT == oo_03.IT && oo_02.aaS == oo_03.aaS;
    }
}

