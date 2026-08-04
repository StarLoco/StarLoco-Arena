/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from akj
 */
public class akj_2 {
    private float[] cDi;
    private vP[] cDj;
    private int[] e;

    public vP[] e() {
        return this.cDj;
    }

    public void a(vP[] vPArray, int[] nArray) {
        this.cDj = vPArray;
        this.e = nArray;
    }

    public float[] yf() {
        return this.cDi;
    }

    public int[] f() {
        return this.e;
    }

    public void y(float[] fArray) {
        this.cDi = fArray;
    }

    public float[] a(int n2) {
        int n3 = this.cDi.length;
        ps_0 ps_02 = new ps_0();
        for (int j = 0; j < n3; ++j) {
            ps_02.add(ux_2.ab(n2, j));
            ps_02.add(this.cDi[j]);
        }
        return ps_02.uD();
    }
}

