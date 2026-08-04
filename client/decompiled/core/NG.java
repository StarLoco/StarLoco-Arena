/*
 * Decompiled with CFR 0.152.
 */
public class NG
extends ahz_0 {
    private static final String bAe = "pathDisplayer";
    private static final NG bAf = new NG();

    public static NG aaO() {
        return bAf;
    }

    private NG() {
        super(bAe, mx_0.KA);
    }

    public void b(arh_0 arh_02) {
        this.clear();
        int n2 = arh_02.aEF();
        for (int j = 0; j < n2; ++j) {
            int[] nArray = arh_02.lU(j);
            this.y(nArray[0], nArray[1], (short)nArray[2]);
        }
    }
}

