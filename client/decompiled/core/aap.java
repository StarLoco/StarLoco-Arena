/*
 * Decompiled with CFR 0.152.
 */
/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class aap {
    public static final long ceY = 1L;
    public static final long ceZ = 2L;
    public static final long cfa = 4L;
    public static final long cfb = 8L;
    public static final long cfc = 16L;
    public static final long cfd = 32L;
    public static final long cfe = 64L;
    public static final long cff = 128L;
    public static final long cfg = 256L;
    public static final long cfh = 512L;
    public static final long cfi = 1024L;
    public static final long cfj = 2048L;
    public static final long cfk = 4096L;
    public static final long cfl = 8192L;
    private static final int cfm = 16;
    public static final long cfn = 65536L;
    public static final long cfo = 131072L;
    public static final long cfp = 262144L;
    public static final long cfq = 524288L;
    public static final long cfr = 0x100000L;
    public static final long cfs = 0x200000L;
    public static final long cft = 0x400000L;
    public static final long cfu = 0x800000L;
    public static final long cfv = 0x1000000L;
    public static final long cfw = 0x2000000L;
    public static final long cfx = 0x4000000L;
    public static final long cfy = 0x8000000L;
    public static final long cfz = 0x10000000L;
    public static final long cfA = 0x20000000L;
    private static final int cfB = 32;
    public static final long cfC = 0x100000000L;
    public static final long cfD = 0x200000000L;
    public static final long cfE = 0x400000000L;
    public static final long cfF = 0x800000000L;
    public static final long cfG = 0x1000000000L;
    public static final long cfH = 0x2000000000L;
    public static final long cfI = 0x4000000000L;
    public static final long cfJ = 0x8000000000L;
    public static final long cfK = 0x10000000000L;
    public static final long cfL = 0x20000000000L;
    public static final long cfM = 0x40000000000L;
    public static final long cfN = 0x80000000000L;
    public static final long cfO = 0x100000000000L;
    public static final long cfP = 0x200000000000L;

    public static boolean a(long l2, int n2, boolean bl2, boolean bl3, boolean bl4, int n3) {
        if ((0x10000L & l2) != 0L && !aap.i(n2, 65536L)) {
            return false;
        }
        if ((0x20000L & l2) != 0L && !aap.i(n2, 131072L)) {
            return false;
        }
        if ((0x40000L & l2) != 0L && !aap.i(n2, 262144L)) {
            return false;
        }
        if ((0x80000L & l2) != 0L && !aap.i(n2, 524288L)) {
            return false;
        }
        if ((0x100000L & l2) != 0L && !aap.i(n2, 0x100000L)) {
            return false;
        }
        if ((0x200000L & l2) != 0L && !aap.i(n2, 0x200000L)) {
            return false;
        }
        if ((0x400000L & l2) != 0L && !aap.i(n2, 0x400000L)) {
            return false;
        }
        if ((0x800000L & l2) != 0L && !aap.i(n2, 0x800000L)) {
            return false;
        }
        if ((0x1000000L & l2) != 0L && !aap.i(n2, 0x1000000L)) {
            return false;
        }
        if ((0x2000000L & l2) != 0L && !aap.i(n2, 0x2000000L)) {
            return false;
        }
        if ((0x4000000L & l2) != 0L && !aap.i(n2, 0x4000000L)) {
            return false;
        }
        if ((0x8000000L & l2) != 0L && !aap.i(n2, 0x8000000L)) {
            return false;
        }
        if ((0x10000000L & l2) != 0L && !aap.i(n2, 0x10000000L)) {
            return false;
        }
        if ((0x20000000L & l2) != 0L && !aap.i(n2, 0x20000000L)) {
            return false;
        }
        if ((0x100000000L & l2) != 0L && aap.i(n2, 65536L)) {
            return false;
        }
        if ((0x200000000L & l2) != 0L && aap.i(n2, 131072L)) {
            return false;
        }
        if ((0x400000000L & l2) != 0L && aap.i(n2, 262144L)) {
            return false;
        }
        if ((0x800000000L & l2) != 0L && aap.i(n2, 524288L)) {
            return false;
        }
        if ((0x1000000000L & l2) != 0L && aap.i(n2, 0x100000L)) {
            return false;
        }
        if ((0x2000000000L & l2) != 0L && aap.i(n2, 0x200000L)) {
            return false;
        }
        if ((0x4000000000L & l2) != 0L && aap.i(n2, 0x400000L)) {
            return false;
        }
        if ((0x8000000000L & l2) != 0L && aap.i(n2, 0x800000L)) {
            return false;
        }
        if ((0x10000000000L & l2) != 0L && aap.i(n2, 0x1000000L)) {
            return false;
        }
        if ((0x20000000000L & l2) != 0L && aap.i(n2, 0x2000000L)) {
            return false;
        }
        if ((0x40000000000L & l2) != 0L && aap.i(n2, 0x4000000L)) {
            return false;
        }
        if ((0x80000000000L & l2) != 0L && aap.i(n2, 0x8000000L)) {
            return false;
        }
        if ((0x100000000000L & l2) != 0L && aap.i(n2, 0x10000000L)) {
            return false;
        }
        if ((0x200000000000L & l2) != 0L && aap.i(n2, 0x20000000L)) {
            return false;
        }
        if ((1L & l2) != 0L && !bl2) {
            return false;
        }
        if ((2L & l2) != 0L && bl2) {
            return false;
        }
        if ((4L & l2) != 0L && !bl3) {
            return false;
        }
        if ((8L & l2) != 0L && bl3) {
            return false;
        }
        if ((0x10L & l2) != 0L && !bl4) {
            return false;
        }
        if ((0x20L & l2) != 0L && bl4) {
            return false;
        }
        if ((0x200L & l2) != 0L && n3 < 2) {
            return false;
        }
        if ((0x400L & l2) != 0L && n3 < 3) {
            return false;
        }
        if ((0x800L & l2) != 0L && n3 < 4) {
            return false;
        }
        if ((0x1000L & l2) != 0L && n3 < 5) {
            return false;
        }
        return (0x2000L & l2) == 0L || n3 >= 6;
    }

    private static boolean i(int n2, long l2) {
        return l2 >> 16 == (long)(1 << n2 - 1);
    }

    public static boolean do(long l2) {
        return (l2 & 0x40L) != 0L;
    }

    public static boolean dp(long l2) {
        return (l2 & 0x80L) != 0L;
    }

    public static boolean dq(long l2) {
        return (l2 & 0x100L) != 0L;
    }
}

