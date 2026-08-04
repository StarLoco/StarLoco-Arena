/*
 * Decompiled with CFR 0.152.
 */
public class aJt
extends asw_0 {
    private int[] bbE = ug_2.bQd;
    private int DB;

    public aJt(int n2, int n3, int n4, int n5, int n6, int[] nArray, int n7, int n8, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, byte by, int n9) {
        super(n2, n3, n4, n5, n6, n7, n8, bl2, bl3, bl4, bl5, bl6, bl7, by);
        this.bbE = nArray;
        this.DB = n9;
    }

    public int[] Qx() {
        return this.bbE;
    }

    public int oz() {
        return this.DB;
    }

    public String getName() {
        return aon_0.aYc().a(10, this.getId(), new Object[0]);
    }

    public String getDescription() {
        return aon_0.aYc().a(11, this.getId(), new Object[0]);
    }
}

