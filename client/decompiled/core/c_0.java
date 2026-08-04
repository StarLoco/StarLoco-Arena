/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from c
 */
public class c_0 {
    private ArrayList c = new ArrayList(2);
    private vP[] d;
    private int[] e;

    public void a(akj_2 akj_22) {
        this.c.add(akj_22);
    }

    public ArrayList d() {
        return this.c;
    }

    public void clear() {
        this.c.clear();
    }

    public void a(vP[] vPArray, int[] nArray) {
        this.d = vPArray;
        this.e = nArray;
    }

    public vP[] e() {
        return this.d;
    }

    public int[] f() {
        return this.e;
    }

    public float[] a(int n2) {
        if (this.c.size() == 0) {
            return new float[0];
        }
        float[] fArray = ((akj_2)this.c.get(0)).yf();
        float[] fArray2 = ((akj_2)this.c.get(1)).yf();
        int n3 = fArray.length;
        ps_0 ps_02 = new ps_0();
        for (int j = 0; j < n3; ++j) {
            ps_02.add(ux_2.ab(n2, j));
            ps_02.add(fArray[j]);
            ps_02.add(ux_2.ab(n2, j));
            ps_02.add(fArray2[j]);
        }
        return ps_02.uD();
    }
}

