/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from WI
 */
public abstract class wi_2
extends NY {
    public static final int bVb = 18;
    public static final int bVc = 10;
    private static final long bVd = 262143L;
    private static final long bVe = 1023L;
    private static final long bVf = 131071L;
    private static final long bVg = 511L;

    public static long u(int n2, int n3, short s) {
        long l2 = (long)n2 + 131071L & 0x3FFFFL;
        long l3 = (long)n3 + 131071L & 0x3FFFFL;
        long l4 = (long)s + 511L & 0x3FFL;
        if (Math.abs(n2) <= 131072 - (n2 < 0 ? 1 : 0) && Math.abs(n3) <= 131072 - (n3 < 0 ? 1 : 0) && Math.abs(s) <= 512 - (s < 0 ? 1 : 0)) {
            return l2 << 28 | l3 << 10 | l4;
        }
        throw new en_0("Param\u00e8tres d'une position en dehors de la map - position : " + n2 + ", " + n3 + ", " + s);
    }

    public static long x(ry ry2) {
        return wi_2.u(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public static ry dc(long l2) {
        short s = (short)((l2 & 0x3FFL) - 511L);
        int n2 = (int)((l2 >> 10 & 0x3FFFFL) - 131071L);
        int n3 = (int)((l2 >> 28 & 0x3FFFFL) - 131071L);
        return new ry(n3, n2, s);
    }

    public static double v(int n2, int n3, short s) {
        return Double.longBitsToDouble(wi_2.u(n2, n3, s));
    }

    public static ry u(double d) {
        return wi_2.dc(Double.doubleToRawLongBits(d));
    }

    public static int dd(long l2) {
        return (int)((l2 >> 28 & 0x3FFFFL) - 131071L);
    }

    public static int de(long l2) {
        return (int)((l2 >> 10 & 0x3FFFFL) - 131071L);
    }

    public static short df(long l2) {
        return (short)((l2 & 0x3FFL) - 511L);
    }

    public aij_2 Ce() {
        return aij_2.cxJ;
    }

    public abstract boolean eM();

    public void abl() {
    }

    public boolean abk() {
        return false;
    }
}

