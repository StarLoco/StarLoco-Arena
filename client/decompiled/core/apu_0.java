/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from apu
 */
public class apu_0
extends gg_0 {
    public static final apu_0 cMh = new apu_0();

    private apu_0() {
    }

    protected byte lV() {
        return 2;
    }

    public qP s(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = apu_0.b(acf2, bl2, f);
        float f3 = apu_0.b(acf2, bl2, f);
        float f4 = apu_0.b(acf2, bl2, f);
        return new qP(f2, f3, f4);
    }

    protected void a(aij_1 aij_12, qP qP2, qP qP3) {
        boolean bl2 = !this.a(qP2, qP3);
        aij_12.fe(bl2);
        aij_12.aVj();
        apu_0.a(aij_12, bl2, qP2.Hk, qP3.Hk);
        apu_0.a(aij_12, bl2, qP2.Hl, qP3.Hl);
        apu_0.a(aij_12, bl2, qP2.Hm, qP3.Hm);
    }

    protected boolean a(qP qP2, qP qP3) {
        return qP2.Hk == qP3.Hk && qP2.Hl == qP3.Hl && qP2.Hm == qP3.Hm;
    }
}

