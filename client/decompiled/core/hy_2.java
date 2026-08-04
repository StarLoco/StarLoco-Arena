/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from hy
 */
public class hy_2 {
    private static final int vG = 18;
    private static final int vH = 10;
    public static final int vI = 18;
    public static final int vJ = 18;
    public static final int vK = 324;
    public static final int vL = 64;
    public static final int vM = 131071;
    public static final int vN = 131071;
    public static final int vO = 511;
    public static final int vP = -131072;
    public static final int vQ = -131072;
    public static final int vR = -512;
    public static final int vS = 7281;
    public static final int vT = 7281;
    public static final int vU = 511;
    public static final int vV = -7281;
    public static final int vW = -7281;
    public static final int vX = -512;

    public static int aO(int n2) {
        int n3 = n2 / 18;
        if (n2 < 0 && n2 % 18 != 0) {
            --n3;
        }
        assert ((double)n3 == Math.floor((double)n2 / 18.0));
        return n3;
    }

    public static int aP(int n2) {
        int n3 = n2 / 18;
        if (n2 < 0 && n2 % 18 != 0) {
            --n3;
        }
        assert ((double)n3 == Math.floor((double)n2 / 18.0));
        return n3;
    }
}

