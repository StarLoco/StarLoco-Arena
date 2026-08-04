/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from jb
 */
public class jb_1
extends gg_0 {
    public static final jb_1 zo = new jb_1();

    private jb_1() {
    }

    protected byte lV() {
        return 11;
    }

    public qc_1 c(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = jb_1.b(acf2, bl2, f);
        return new qc_1(f2);
    }

    protected void a(aij_1 aij_12, qc_1 qc_12, qc_1 qc_13) {
        boolean bl2 = !this.a(qc_12, qc_13);
        aij_12.fe(bl2);
        aij_12.aVj();
        jb_1.a(aij_12, bl2, qc_12.aeO, qc_13.aeO);
    }

    protected boolean a(qc_1 qc_12, qc_1 qc_13) {
        return qc_12.aeO == qc_13.aeO;
    }
}

