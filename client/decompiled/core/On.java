/*
 * Decompiled with CFR 0.152.
 */
public class On
extends gg_0 {
    public static final On bBO = new On();

    private On() {
    }

    protected byte lV() {
        return 3;
    }

    public aof_1 k(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = On.b(acf2, bl2, f);
        return new aof_1(f2);
    }

    protected void a(aij_1 aij_12, aof_1 aof_12, aof_1 aof_13) {
        boolean bl2 = !this.a(aof_12, aof_13);
        aij_12.fe(bl2);
        aij_12.aVj();
        On.a(aij_12, bl2, aof_12.emD, aof_13.emD);
    }

    protected boolean a(aof_1 aof_12, aof_1 aof_13) {
        return aof_12.emD == aof_13.emD;
    }
}

