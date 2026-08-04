/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afv
 */
public class afv_0
extends cZ {
    public static final afv_0 crf = new afv_0();

    private afv_0() {
    }

    protected byte lV() {
        return 2;
    }

    protected void a(aij_1 aij_12, apI apI2, apI apI3) {
        boolean bl2 = !this.a(apI2, apI3);
        aij_12.fe(bl2);
        aij_12.aVj();
        afv_0.a(aij_12, bl2, apI2.IQ, apI3.IQ);
        afv_0.a(aij_12, bl2, apI2.IR, apI3.IR);
        afv_0.a(aij_12, bl2, apI2.IS, apI3.IS);
        afv_0.a(aij_12, bl2, apI2.aHh, apI3.aHh);
        afv_0.a(aij_12, bl2, apI2.cMH, apI3.cMH);
    }

    public apI p(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = afv_0.b(acf2, bl2, f);
        float f3 = afv_0.b(acf2, bl2, f);
        float f4 = afv_0.b(acf2, bl2, f);
        float f5 = afv_0.b(acf2, bl2, f);
        float f6 = afv_0.b(acf2, bl2, f);
        return new apI(f2, f3, f4, f5, f6);
    }

    protected boolean a(apI apI2, apI apI3) {
        return apI2.IQ == apI3.IQ && apI2.IR == apI3.IR && apI2.IS == apI3.IS && apI2.aHh == apI3.aHh && apI2.cMH == apI3.cMH;
    }
}

