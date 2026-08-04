/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBs
 */
public class abs_2
extends ahz_0 {
    private static adz_1 drp = new adz_1();
    private static ef_1 tl = null;
    private static final String aKD = "auraRange";
    private static final abs_2 drq;

    public static abs_2 aNo() {
        return drq;
    }

    public abs_2() {
        super(aKD, mx_0.Ky, tl, drp, fa_0.ry);
    }

    public void c(Iterable iterable) {
        this.clear();
        aoq_0 aoq_02 = apN.aDK().aDL().gV();
        for (int[] nArray : iterable) {
            short s;
            int n2;
            int n3 = nArray[0];
            if (!aoq_02.bG(n3, n2 = nArray[1]) || (s = aoq_02.bL(n3, n2)) == Short.MIN_VALUE || aoq_02.bK(n3, n2) != null) continue;
            this.y(n3, n2, s);
        }
    }

    static {
        try {
            String string = mu_1.rM().getString("highLightGfxFile") + "mauvaisOeil.tga";
            tl = ahz_0.a(string, drp);
        }
        catch (aih_2 aih_22) {
            aih_22.printStackTrace();
        }
        drq = new abs_2();
    }
}

