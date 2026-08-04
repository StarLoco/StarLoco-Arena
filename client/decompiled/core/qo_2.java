/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Qo
 */
public abstract class qo_2 {
    private static int[] B(int n2, int n3, int n4) {
        return new int[]{n2, n3, n4};
    }

    public static int[] hl(int n2) {
        return qo_2.B(n2, -1, -1);
    }

    public static int[] aH(int n2, int n3) {
        return qo_2.B(n2, -1, n3);
    }

    public static int[] C(int n2, int n3, int n4) {
        return qo_2.B(n2, n3, n4);
    }

    public static int[] hm(int n2) {
        return new int[]{n2};
    }

    public static void f(int n2, boolean bl2) {
        avE avE2;
        long l2 = atW.aGY().ml(n2);
        if (bl2 && (avE2 = ahz_1.aUa().ex(l2)) != null) {
            if (avE2.ajU()) {
                avE2.aIZ();
            } else {
                avE2.j(0.0f, 1000.0f);
                avE2.et(true);
            }
        }
    }
}

