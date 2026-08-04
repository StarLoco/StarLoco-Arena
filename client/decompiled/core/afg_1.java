/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afG
 */
public class afg_1 {
    private static final String[] crB = new String[]{"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    private static final int[] crC = new int[]{1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    public static final int crD = 1;
    public static final int crE = 3999;
    private static final int crF = 16;
    private static final StringBuilder crG = new StringBuilder(16);

    public static String kn(int n2) {
        if (n2 < 1 || 3999 < n2) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        for (int j = crC.length - 1; 0 <= j; --j) {
            int n4 = crC[j];
            while (n3 >= n4) {
                crG.append(crB[j]);
                n3 -= n4;
            }
        }
        String string = crG.toString();
        crG.delete(0, crG.length());
        return string;
    }
}

