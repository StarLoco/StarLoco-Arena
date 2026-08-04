/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aIH
 */
public class aih_1
extends aai_1 {
    public static final int Ec = -1;
    public static final int dQf = 1001;
    public static final int dQg = 1002;
    public static final int dQh = 1003;
    public static final int dQi = 1004;
    public static final int dQj = 1005;
    public static final int dQk = 1006;
    public static final int dQl = 1007;
    public static final int dQm = 1008;
    public static final int dQn = 1009;
    public static final int dQo = 1010;
    public static final int dQp = 1011;
    public static final int dQq = 1012;
    public static final int dQr = -1;
    public static final int dQs = -1;
    public static final int dQt = -1;
    private String dQu;
    private int[] dQv;
    private int it;

    public aih_1(int n2, ZT zT, nv nv2, int n3, String string, am_2 am_22, int ... nArray) {
        super(n2, zT, nv2);
        zT.f(n2);
        zT.a(am_22);
        this.dQv = nArray;
        this.dQu = string;
        this.it = n3;
    }

    public String aoI() {
        return this.dQu;
    }

    public int eA() {
        return this.it;
    }

    public int[] aVh() {
        return this.dQv;
    }

    public boolean oN(int n2) {
        if (this.dQv == null) {
            return n2 == 0;
        }
        for (int n3 : this.dQv) {
            if (n3 != n2) continue;
            return true;
        }
        return false;
    }
}

