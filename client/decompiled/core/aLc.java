/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

public class aLc
implements ahl_2 {
    public static final long dUL = 1L;
    public static final long dUM = 2L;
    public static final long dUN = 4L;
    public static final long dUO = 8L;
    public static final long dUP = 16L;
    public static final long dUQ = 32L;
    public static final long dUR = 64L;
    public static final long dUS = 128L;
    public static final long dUT = 256L;
    public static final long dUU = 512L;
    public static final long dUV = 1024L;
    public static final long dUW = 2048L;
    public static final long dUX = 4096L;
    public static final long dUY = 8192L;
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
    private static final int dUZ = 48;
    public static final long dVa = 0x1000000000000L;
    public static final long dVb = 0x2000000000000L;
    public static final long dVc = 0x4000000000000L;
    public static final long dVd = 0x8000000000000L;
    public static final long dVe = 0x10000000000000L;
    public static final long dVf = 0x20000000000000L;
    public static final long dVg = 0x40000000000000L;
    public static final long dVh = 0x80000000000000L;
    public static final long dVi = 0x100000000000000L;
    public static final long dVj = 0x200000000000000L;
    public static final long dVk = 0x400000000000000L;
    public static final long dVl = 0x800000000000000L;
    public static final long dVm = 0x1000000000000000L;
    public static final long dVn = 0x2000000000000000L;
    public static final long dVo = 0x4000000000000000L;
    public static final int dVp = 40;
    private final long[] dVq;

    public aLc(long ... lArray) {
        this.dVq = lArray;
    }

    public pf_0 a(aOf aOf2, aOf aOf3) {
        ahf_2 ahf_22 = ahf_2.dMP;
        ArrayList arrayList = new ArrayList();
        for (long l2 : this.dVq) {
            if ((2L & l2) != 0L && aOf2 != aOf3 || (0x100L & l2) != 0L && aOf2 == aOf3 || (0x80L & l2) != 0L && (aOf2 == aOf3 || aOf3 == null || !(aOf2 instanceof alp_0) || !(aOf3 instanceof alp_0) || ((alp_0)aOf2).PH() != ((alp_0)aOf3).PH()) || (4L & l2) != 0L && (aOf3 == null || !(aOf2 instanceof alp_0) || !(aOf3 instanceof alp_0) || ((alp_0)aOf2).PH() != ((alp_0)aOf3).PH()) || (8L & l2) != 0L && (aOf3 == null || aOf2 instanceof alp_0 && aOf3 instanceof alp_0 && ((alp_0)aOf2).PH() == ((alp_0)aOf3).PH()) || (0x10L & l2) != 0L && (!(aOf2 instanceof gn_0) || ((gn_0)aOf2).Dk()) || (0x20L & l2) != 0L && (!(aOf2 instanceof gn_0) || !((gn_0)aOf2).Dk()) || (0x400L & l2) != 0L && (!(aOf2 instanceof gn_0) || ((gn_0)aOf2).NY().lV() == xq.axE.lV()) || (0x200L & l2) != 0L && (!(aOf2 instanceof gn_0) || ((gn_0)aOf2).NY().lV() != xq.axE.lV()) || (0x40L & l2) != 0L && !(aOf2 instanceof ack_1) || (0x800L & l2) != 0L && (!(aOf2 instanceof gn_0) || ((gn_0)aOf2).c(avx_0.deH) <= 0) || (0x1000L & l2) != 0L && (!(aOf2 instanceof gn_0) || ((gn_0)aOf2).c(avx_0.deI) <= 0) || (0x2000L & l2) != 0L && (!(aOf2 instanceof gn_0) || ((gn_0)aOf2).c(avx_0.deJ) <= 0) || (0x10000L & l2) != 0L && !this.a(aOf2, 0x10000L & l2) || (0x20000L & l2) != 0L && !this.a(aOf2, 0x20000L & l2) || (0x40000L & l2) != 0L && !this.a(aOf2, 0x40000L & l2) || (0x80000L & l2) != 0L && !this.a(aOf2, 0x80000L & l2) || (0x100000L & l2) != 0L && !this.a(aOf2, 0x100000L & l2) || (0x200000L & l2) != 0L && !this.a(aOf2, 0x200000L & l2) || (0x400000L & l2) != 0L && !this.a(aOf2, 0x400000L & l2) || (0x800000L & l2) != 0L && !this.a(aOf2, 0x800000L & l2) || (0x1000000L & l2) != 0L && !this.a(aOf2, 0x1000000L & l2) || (0x2000000L & l2) != 0L && !this.a(aOf2, 0x2000000L & l2) || (0x4000000L & l2) != 0L && !this.a(aOf2, 0x4000000L & l2) || (0x8000000L & l2) != 0L && !this.a(aOf2, 0x8000000L & l2) || (0x10000000L & l2) != 0L && !this.a(aOf2, 0x10000000L & l2) || (0x20000000L & l2) != 0L && !this.a(aOf2, 0x20000000L & l2) || (0x100000000L & l2) != 0L && !this.b(aOf2, 0x100000000L & l2) || (0x200000000L & l2) != 0L && !this.b(aOf2, 0x200000000L & l2) || (0x400000000L & l2) != 0L && !this.b(aOf2, 0x400000000L & l2) || (0x800000000L & l2) != 0L && !this.b(aOf2, 0x800000000L & l2) || (0x1000000000L & l2) != 0L && !this.b(aOf2, 0x1000000000L & l2) || (0x2000000000L & l2) != 0L && !this.b(aOf2, 0x2000000000L & l2) || (0x4000000000L & l2) != 0L && !this.b(aOf2, 0x4000000000L & l2) || (0x8000000000L & l2) != 0L && !this.b(aOf2, 0x8000000000L & l2) || (0x10000000000L & l2) != 0L && !this.b(aOf2, 0x10000000000L & l2) || (0x20000000000L & l2) != 0L && !this.b(aOf2, 0x20000000000L & l2) || (0x40000000000L & l2) != 0L && !this.b(aOf2, 0x40000000000L & l2) || (0x80000000000L & l2) != 0L && !this.b(aOf2, 0x80000000000L & l2) || (0x100000000000L & l2) != 0L && !this.b(aOf2, 0x100000000000L & l2) || (0x200000000000L & l2) != 0L && !this.b(aOf2, 0x200000000000L & l2)) continue;
            if ((1L & l2) != 0L) {
                ahf_22 = ahf_2.dMR;
                continue;
            }
            return new pf_0((Object)ahf_2.dMN, arrayList);
        }
        return new pf_0((Object)ahf_22, arrayList);
    }

    public ahf_2 a(gn_0 gn_02, gn_0 gn_03) {
        for (long l2 : this.dVq) {
            if ((0x2000000000000L & l2) != 0L && (gn_02.b(avx_0.deB) || gn_03.b(avx_0.deB))) {
                return ahf_2.dMP;
            }
            if ((0x4000000000000L & l2) != 0L && gn_02.b(avx_0.dev)) {
                return ahf_2.dMP;
            }
            if ((0x8000000000000L & l2) != 0L && gn_02.b(avx_0.deA)) {
                return ahf_2.dMP;
            }
            if ((0x10000000000000L & l2) != 0L && gn_02.a(Lr.bqx).atR() >= gn_02.a(Lr.bqx).max()) {
                return ahf_2.dMP;
            }
            if ((0x20000000000000L & l2) != 0L && (gn_02.a(Lr.bqy).atR() == 0 || gn_02.a(Lr.bqY).atR() >= 100)) {
                return ahf_2.dMP;
            }
            if ((0x40000000000000L & l2) != 0L && (gn_02.a(Lr.bqz).atR() == 0 || gn_02.a(Lr.bqZ).atR() >= 100)) {
                return ahf_2.dMP;
            }
            if ((0x80000000000000L & l2) != 0L && gn_02.b(avx_0.deC)) {
                return ahf_2.dMP;
            }
            if ((0x100000000000000L & l2) != 0L && gn_02.b(avx_0.dex)) {
                return ahf_2.dMP;
            }
            if ((0x200000000000000L & l2) != 0L && gn_02.b(avx_0.dew)) {
                return ahf_2.dMP;
            }
            if ((0x400000000000000L & l2) != 0L && ig_1.a(Lr.brb, Lr.bqH, Lr.bra, Lr.bqD, gn_03, gn_02) < -60) {
                return ahf_2.dMP;
            }
            if ((0x800000000000000L & l2) != 0L && ig_1.a(Lr.brb, Lr.bqI, Lr.bra, Lr.bqE, gn_03, gn_02) < -60) {
                return ahf_2.dMP;
            }
            if ((0x1000000000000000L & l2) != 0L && ig_1.a(Lr.brb, Lr.bqG, Lr.bra, Lr.bqC, gn_03, gn_02) < -60) {
                return ahf_2.dMP;
            }
            if ((0x2000000000000000L & l2) == 0L || ig_1.a(Lr.brb, Lr.bqF, Lr.bra, Lr.bqB, gn_03, gn_02) >= -60) continue;
            return ahf_2.dMP;
        }
        return ahf_2.dMN;
    }

    public ahf_2 n(ack_1 ack_12) {
        for (long l2 : this.dVq) {
            if ((0x4000000000000000L & l2) == 0L) continue;
            if (ack_12 == null) {
                return ahf_2.dMP;
            }
            return ahf_2.dMN;
        }
        return ahf_2.dMQ;
    }

    private boolean a(aOf aOf2, long l2) {
        if (!(aOf2 instanceof gn_0)) {
            return false;
        }
        xq xq2 = ((gn_0)aOf2).NY();
        if (xq2 == null) {
            return false;
        }
        return l2 >> 16 == (long)(1 << xq2.lV() - 1);
    }

    private boolean b(aOf aOf2, long l2) {
        if (!(aOf2 instanceof gn_0)) {
            return false;
        }
        xq xq2 = ((gn_0)aOf2).NY();
        if (xq2 == null) {
            return false;
        }
        return l2 >> 32 != (long)(1 << xq2.lV() - 1);
    }

    public long[] aWk() {
        return this.dVq;
    }
}

