/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from kU
 */
public class ku_1
extends gg_0 {
    public static final ku_1 FQ = new ku_1();

    private ku_1() {
    }

    protected byte lV() {
        return 1;
    }

    public af_0 e(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = ku_1.b(acf2, bl2, f);
        float f3 = ku_1.b(acf2, bl2, f);
        float f4 = ku_1.b(acf2, bl2, f);
        float f5 = ku_1.b(acf2, bl2, f);
        byte by = acf2.readByte();
        anm_1 anm_12 = anm_1.values()[by];
        return new af_0(f2, anm_12, f3, f4, f5);
    }

    protected void a(aij_1 aij_12, af_0 af_02, af_0 af_03) {
        boolean bl2 = !this.a(af_02, af_03);
        aij_12.fe(bl2);
        aij_12.aVj();
        ku_1.a(aij_12, bl2, af_02.aHh, af_03.aHh);
        ku_1.a(aij_12, bl2, af_02.Gv, af_03.Gv);
        ku_1.a(aij_12, bl2, af_02.Gw, af_03.Gw);
        ku_1.a(aij_12, bl2, af_02.aHj, af_03.aHj);
        byte by = (byte)af_02.aHi.ordinal();
        aij_12.writeByte(by);
    }

    protected boolean a(af_0 af_02, af_0 af_03) {
        return af_02.aHh == af_03.aHh && af_02.Gv == af_03.Gv && af_02.Gw == af_03.Gw && af_02.aHj == af_03.aHj;
    }
}

