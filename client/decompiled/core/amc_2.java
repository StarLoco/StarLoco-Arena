/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from amC
 */
public class amc_2
extends vs_2 {
    public static final amc_2 cHN = new amc_2();

    private amc_2() {
    }

    protected byte lV() {
        return 2;
    }

    public amj_0 r(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        boolean bl3 = acf2.aqE();
        int n2 = acf2.readInt();
        float f2 = amc_2.b(acf2, bl2, f);
        float f3 = amc_2.b(acf2, bl2, f);
        float f4 = amc_2.b(acf2, bl2, f);
        float f5 = amc_2.b(acf2, bl2, f);
        float f6 = amc_2.b(acf2, bl2, f);
        float f7 = amc_2.b(acf2, bl2, f);
        float f8 = amc_2.b(acf2, bl2, f);
        float f9 = amc_2.b(acf2, bl2, f);
        float f10 = amc_2.b(acf2, bl2, f);
        float f11 = amc_2.b(acf2, bl2, f);
        float f12 = amc_2.b(acf2, bl2, f);
        float f13 = amc_2.b(acf2, bl2, f);
        float f14 = amc_2.b(acf2, bl2, f);
        float f15 = amc_2.b(acf2, bl2, f);
        float f16 = amc_2.b(acf2, bl2, f);
        float f17 = amc_2.b(acf2, bl2, f);
        float f18 = amc_2.b(acf2, bl2, f);
        float f19 = amc_2.b(acf2, bl2, f);
        vp_0 vp_02 = vp_0.a(acf2, false);
        float f20 = amc_2.b(acf2, bl2, f);
        int n3 = amc_2.c(acf2, bl2, f);
        amj_0 amj_02 = new amj_0(n2, f2, f3, f4, f5, f6, f7, bl3, f8, f9, f10, f11, f12, f13, f14, f15, f16, f17, vp_02, f20, n3);
        amj_02.v(f18, f19);
        return amj_02;
    }

    protected void a(aij_1 aij_12, amj_0 amj_02, amj_0 amj_03) {
        boolean bl2 = !this.a(amj_02, amj_03);
        aij_12.fe(bl2);
        aij_12.fe(amj_02.aAe);
        aij_12.aVj();
        aij_12.writeInt(amj_02.aAk);
        amc_2.a(aij_12, bl2, amj_02.KX, amj_03.KX);
        amc_2.a(aij_12, bl2, amj_02.KY, amj_03.KY);
        amc_2.a(aij_12, bl2, amj_02.KT, amj_03.KT);
        amc_2.a(aij_12, bl2, amj_02.KU, amj_03.KU);
        amc_2.a(aij_12, bl2, amj_02.aAf, amj_03.aAf);
        amc_2.a(aij_12, bl2, amj_02.aAg, amj_03.aAg);
        amc_2.a(aij_12, bl2, amj_02.aAh, amj_03.aAh);
        amc_2.a(aij_12, bl2, amj_02.aAi, amj_03.aAi);
        amc_2.a(aij_12, bl2, amj_02.aAl, amj_03.aAl);
        amc_2.a(aij_12, bl2, amj_02.aAm, amj_03.aAm);
        amc_2.a(aij_12, bl2, amj_02.aAn, amj_03.aAn);
        amc_2.a(aij_12, bl2, amj_02.aAo, amj_03.aAo);
        amc_2.a(aij_12, bl2, amj_02.aAp, amj_03.aAp);
        amc_2.a(aij_12, bl2, amj_02.aAq, amj_03.aAq);
        amc_2.a(aij_12, bl2, amj_02.aAr, amj_03.aAr);
        amc_2.a(aij_12, bl2, amj_02.aAs, amj_03.aAs);
        amc_2.a(aij_12, bl2, amj_02.KV * 0.5f, amj_03.KV * 0.5f);
        amc_2.a(aij_12, bl2, amj_02.KW * 0.5f, amj_03.KW * 0.5f);
        amj_02.cea.h(aij_12);
        amc_2.a(aij_12, bl2, amj_02.aaS, amj_03.aaS);
        if (amj_02.mN == -1 || amj_03.mN == -1) {
            amc_2.b(aij_12, bl2, -1, -1);
        } else {
            amc_2.b(aij_12, bl2, amj_02.mN, amj_03.mN);
        }
    }

    protected boolean a(amj_0 amj_02, amj_0 amj_03) {
        if (!super.a(amj_02, amj_03)) {
            return false;
        }
        return amj_02.aaS == amj_03.aaS && amj_02.mN == amj_03.mN;
    }
}

