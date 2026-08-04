/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ayw
 */
public class ayw_0
extends gg_0 {
    public static final ayw_0 dmv = new ayw_0();

    private ayw_0() {
    }

    protected byte lV() {
        return 10;
    }

    public arx_0 u(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = ayw_0.b(acf2, bl2, f);
        return new arx_0(f2);
    }

    protected void a(aij_1 aij_12, arx_0 arx_02, arx_0 arx_03) {
        boolean bl2 = !this.a(arx_02, arx_03);
        aij_12.fe(bl2);
        aij_12.aVj();
        ayw_0.a(aij_12, bl2, arx_02.aHh, arx_03.aHh);
    }

    protected boolean a(arx_0 arx_02, arx_0 arx_03) {
        return arx_02.aHh == arx_03.aHh;
    }
}

