/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from PR
 */
public class pr_2
extends gg_0 {
    public static final pr_2 bEu = new pr_2();

    private pr_2() {
    }

    protected byte lV() {
        return 7;
    }

    public nt l(acf acf2, float f) {
        boolean bl2 = acf2.aqE();
        float f2 = pr_2.b(acf2, bl2, f);
        return new nt(f2);
    }

    protected void a(aij_1 aij_12, nt nt2, nt nt3) {
        boolean bl2 = !this.a(nt2, nt3);
        aij_12.fe(bl2);
        aij_12.aVj();
        pr_2.a(aij_12, bl2, nt2.Ox, nt3.Ox);
    }

    protected boolean a(nt nt2, nt nt3) {
        return nt2.Ox == nt3.Ox;
    }
}

