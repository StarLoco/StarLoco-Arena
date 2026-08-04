/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from QE
 */
public class qe_0
implements cn_1 {
    private static final Logger a = Logger.getLogger(qe_0.class);
    private static final boolean bGS = false;
    private static final Logger bGT = Logger.getLogger((String)"debug");
    public static final int bGU = -1;
    public static final int bGV = 128;
    public static int bGW = 1024;
    public static final int bGX = 3;
    public static int bGY = bGW * 3;
    private static final float bGZ = 1.4f;
    private static final float[] bHa = new float[]{0.0f, 0.0f, 0.5f, 2.5f, 3.5f};
    private static final float bHb = 0.9f;
    private static final float bHc = 10.0f;
    public static final arh_0 bHd = new arh_0();
    private static final int[][] bHe = new int[][]{{1, 0, 1}, {-1, 0, 4}, {0, 1, 2}, {0, -1, 8}, {1, 1, 3}, {-1, 1, 6}, {-1, -1, 12}, {1, -1, 9}};
    private static final int[][] bHf = new int[][]{{0, 1, 2}, {0, -1, 8}, {1, 0, 1}, {-1, 0, 4}, {1, 1, 3}, {-1, 1, 6}, {-1, -1, 12}, {1, -1, 9}};
    private int bDf;
    private short bHg;
    protected byte bDh;
    protected final qa_2 bHh = new qa_2();
    protected long bHi;
    private aja_1 bHj;
    private aen_0 bHk;
    private final pc_0 bHl = new pc_0();
    protected short bHm;
    protected long[] bHn = new long[128];
    private final aqs_0[] bHo;
    private short bHp;
    private final akd_0[] aNf;
    private short bHq;
    private final aqs_0[] bHr = new aqs_0[bGW];
    private short bHs = (short)-1;
    private short bHt = (short)-1;
    private final aLO bHu = new aLO();
    private final pk_0 bHv = new pk_0();
    private final kl_1 bHw = new kl_1();
    private static final acl_0 aU = new ym_0(new eb_2());

    protected qe_0() {
        int n2;
        this.bHo = new aqs_0[bGW];
        for (n2 = 0; n2 < bGW; ++n2) {
            this.bHo[n2] = new aqs_0();
        }
        this.aNf = new akd_0[bGY];
        for (n2 = 0; n2 < bGY; ++n2) {
            this.aNf[n2] = new akd_0();
        }
    }

    public static qe_0 ho(int n2) {
        bGW = n2;
        bGY = bGW * 3;
        return qe_0.adj();
    }

    public static qe_0 adj() {
        try {
            return (qe_0)aU.adr();
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
            return null;
        }
    }

    public void release() {
        try {
            aU.af(this);
        }
        catch (Exception exception) {
            a.error((Object)"Exception", (Throwable)exception);
        }
    }

    public void b() {
    }

    public void j() {
        this.bHj = null;
        this.bHh.reset();
        this.bHj = null;
        this.bHm = (short)-1;
        this.bHi = -1L;
        this.bHl.a(0, (byte)-1, -1);
        this.bHk = null;
    }

    public final void a(aja_1 aja_12) {
        this.bHj = aja_12;
    }

    public void a(aen_0 aen_02) {
        this.bHk = aen_02;
    }

    public void a(int n2, byte by, short s) {
        this.bDf = n2;
        this.bHg = s;
        this.bDh = by;
        this.bHl.a(n2, by, s);
    }

    public void adk() {
        this.bHh.reset();
    }

    public void p(int n2, int n3, short s) {
        this.bHh.ct(qe_0.r(n2, n3, s));
    }

    public void t(ry ry2) {
        this.bHh.ct(qe_0.r(ry2.getX(), ry2.getY(), ry2.wk()));
    }

    public void q(int n2, int n3, short s) {
        this.bHi = qe_0.r(n2, n3, s);
    }

    public void u(ry ry2) {
        assert (ry2 != null) : "can't define a null cell as the destination";
        this.bHi = qe_0.r(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public short adl() {
        return this.bHm;
    }

    public long[] adm() {
        return this.bHn;
    }

    public final arh_0 FJ() {
        if (this.bHm == -1) {
            return bHd;
        }
        arh_0 arh_02 = new arh_0(this.bHm);
        for (int j = 0; j < this.bHm; ++j) {
            long l2 = this.bHn[this.bHm - 1 - j];
            int n2 = qe_0.cy(l2);
            int n3 = qe_0.cz(l2);
            short s = qe_0.cA(l2);
            arh_02.b(j, n2, n3, s);
        }
        return arh_02;
    }

    public final arh_0 adn() {
        if (this.bHm == -1) {
            return bHd;
        }
        arh_0 arh_02 = new arh_0(this.bHm);
        for (int j = 0; j < this.bHm; ++j) {
            long l2 = this.bHn[j];
            int n2 = qe_0.cy(l2);
            int n3 = qe_0.cz(l2);
            short s = qe_0.cA(l2);
            arh_02.b(j, n2, n3, s);
        }
        return arh_02;
    }

    public int ado() {
        int n2;
        assert (this.bHj != null) : "no TopologyMapInstanceSet defined";
        assert (this.bDf > 0) : "invalid m_moverHeight";
        assert (this.bHg >= 0) : "invalid m_moverJumpCapacity";
        assert (this.bDh >= 0) : "invalid m_moverPhysicalRadius : " + this.bDh;
        assert (this.bHk != null) : "no PathFinderParameters defined";
        assert (this.bHi != -1L) : "stop cell not defined";
        assert (!this.bHh.isEmpty()) : "start cells not defined";
        assert (this.bHk.cpI > 0) : "search limit not defined in the path find parameters";
        assert (this.bHk.cpL || !this.bHk.cpM) : "stopping before the end and asking for the end cell to be removed assumes the last cell of the path will be known";
        this.bHw.clear();
        this.bHp = 0;
        this.bHu.clear();
        this.bHq = 0;
        this.bHs = (short)-1;
        this.bHt = (short)-1;
        aqs_0 aqs_02 = this.cB(this.bHi);
        if (aqs_02 == null) {
            return -1;
        }
        if (!mo_1.a(aqs_02.cOP + aqs_02.cOQ, (int)aqs_02.cOP, aqs_02.cOO, this.aNf, this.bDf)) {
            return -1;
        }
        int n3 = qe_0.cy(this.bHi);
        int n4 = qe_0.cz(this.bHi);
        short s = qe_0.cA(this.bHi);
        if (!this.bHk.cpM && this.bHj.bD(n3, n4)) {
            return -1;
        }
        if (this.bDh > 0) {
            for (n2 = -this.bDh; n2 <= this.bDh; ++n2) {
                for (int j = -this.bDh; j <= this.bDh; ++j) {
                    if (!this.bHj.bD(n3 + n2, n4 + j)) continue;
                    return -1;
                }
            }
        }
        for (n2 = this.bHh.size() - 1; n2 >= 0; --n2) {
            long l2 = this.bHh.get(n2);
            if (l2 == this.bHi) {
                this.bHm = 0;
                return this.bHm;
            }
            aqs_0 aqs_03 = this.cB(l2);
            if (aqs_03 == null) {
                a.info((Object)("Invalid start cell for pathfind search : doesn't exist. HASHCODE : " + l2));
                continue;
            }
            int n5 = qe_0.cy(l2);
            int n6 = qe_0.cz(l2);
            short s2 = qe_0.cA(l2);
            if (this.bDh > 0 && s != s2) {
                if (!this.bHk.cpP) continue;
                s2 = s;
            }
            if (!mo_1.a(aqs_03.cOP + aqs_03.cOQ, (int)aqs_03.cOP, aqs_03.cOO, this.aNf, this.bDf)) {
                if (this.bHk.cpP) {
                    if ((s2 = mo_1.a((int)aqs_03.cOP, (int)aqs_03.cOO, this.aNf, (short)(s2 + this.bHg), this.bDf)) == Short.MIN_VALUE) {
                        continue;
                    }
                } else {
                    a.info((Object)"Invalid start cell for pathfind search : not a suitable position for the mover.");
                    continue;
                }
            }
            aqs_03.cOR = this.u(n5, n6, n3, n4);
            if (this.bHk.cpJ > 0 && (float)this.bHk.cpJ < aqs_03.cOR) continue;
            aqs_03.bIK = 0.0f;
            aqs_03.cOS = aqs_03.cOR;
            aqs_03.cmD = true;
            aqs_03.cOU = (byte)-1;
            this.b(aqs_03);
        }
        if (this.bHs == -1) {
            return -1;
        }
        n2 = this.bHk.cpH ? 1 : 0;
        if (this.bDh > 0 && !this.bHk.cpH) {
            this.bHk.cpH = true;
        }
        int n7 = this.bDh == 0 ? this.a(n3, n4, aqs_02) : this.a(n3, n4, qe_0.cA(this.bHi), aqs_02);
        this.bHk.cpH = n2;
        return n7;
    }

    public boolean b(ry ry2, ry ry3) {
        if (ry2.f(ry3) != 1) {
            a.error((Object)("Unable to checkMovementOnNextCell if cells are not adjacent :" + ry2 + ", " + ry3));
            return false;
        }
        int n2 = ry2.getX();
        int n3 = ry2.getY();
        int n4 = ry3.getX();
        int n5 = ry3.getY();
        if (this.bDh == 0) {
            int n6;
            int n7;
            if (this.bHj.bD(n4, n5)) {
                return false;
            }
            int n8 = this.aN(n2, n3);
            if (n8 == 0) {
                return false;
            }
            int n9 = qe_0.hp(n8);
            short s = mo_1.a(n9, n7 = qe_0.hq(n8), this.aNf, ry2.wk());
            if (s == Short.MIN_VALUE) {
                return false;
            }
            int n10 = this.aN(n4, n5);
            if (n10 == 0) {
                return false;
            }
            int n11 = qe_0.hp(n10);
            short s2 = mo_1.a(n11, n6 = qe_0.hq(n10), this.aNf, ry3.wk());
            if (s2 == Short.MIN_VALUE) {
                return false;
            }
            return this.bHl.a(n9 + s, n9, n7, this.aNf, n11 + s2, n11, n6, this.aNf);
        }
        for (int j = -this.bDh; j <= this.bDh; ++j) {
            for (int i2 = -this.bDh; i2 <= this.bDh; ++i2) {
                if (!this.bHj.F(n4 + j, n5 + i2)) {
                    return false;
                }
                if (this.bHj.bD(n4 + j, n5 + i2)) {
                    return false;
                }
                boolean bl2 = this.a(n2 + j, n3 + i2, ry3.wk(), n4 + j, n5 + i2);
                if (bl2) continue;
                return false;
            }
        }
        return true;
    }

    private int a(int n2, int n3, aqs_0 aqs_02) {
        aqs_0 aqs_03;
        int n4 = 0;
        while ((aqs_03 = this.adq()) != null) {
            if (this.bHk.cpI < ++n4) {
                return -1;
            }
            if (aqs_03 == aqs_02) {
                return this.c(aqs_03);
            }
            akd_0 akd_02 = this.aNf[aqs_03.cOP + aqs_03.cOQ];
            int[][] nArray = this.a(akd_02, n2, n3);
            int n5 = 0;
            byte by = this.adp();
            for (byte by2 = 0; by2 < by; by2 = (byte)(by2 + 1)) {
                int n6;
                boolean bl2 = by2 > 3;
                int[] nArray2 = nArray[by2];
                if (bl2 && (n5 & nArray2[2]) != nArray2[2]) continue;
                int n7 = akd_02.aG + nArray2[0];
                int n8 = akd_02.aH + nArray2[1];
                if (this.bHk.cpM && !this.bHk.cpN && n7 == n2 && n8 == n3) {
                    return this.c(aqs_03);
                }
                int n9 = this.aN(n7, n8);
                if (n9 == 0) continue;
                int n10 = qe_0.hp(n9);
                int n11 = qe_0.hq(n9);
                if (this.bHj.bD(n7, n8) && (!this.bHk.cpM || n7 != n2 || n8 != n3) || (n6 = this.bHl.a(aqs_03.cOP + aqs_03.cOQ, aqs_03.cOP, aqs_03.cOO, this.aNf, n10, n11, this.aNf)) == 0) continue;
                for (int j = 0; j < n6; ++j) {
                    short s;
                    akd_0 akd_03 = this.aNf[this.bHl.bDi[j]];
                    assert (akd_03.aG == n7 && akd_03.aH == n8) : "Pathchecker.getValidIndexes returned a CellPathData not corresponding to the given bounds";
                    aqs_0 aqs_04 = this.s(n7, n8, akd_03.wp);
                    if (aqs_04 == null) {
                        System.out.println("Node inexistant ou trop de nodes. nodes testes : " + n4 + "/" + this.bHk.cpI);
                        continue;
                    }
                    if (aqs_04 == aqs_03.cOT) continue;
                    if (this.bHk.cpM && aqs_04 == aqs_02) {
                        return this.c(aqs_03);
                    }
                    if (by2 >= 4) {
                        short s2;
                        if (akd_02.wp >= akd_03.wp) {
                            s = (short)(akd_03.wp + this.bHg);
                            s2 = (short)(akd_02.wp - this.bHg);
                        } else {
                            s = (short)(akd_02.wp + this.bHg);
                            s2 = (short)(akd_03.wp - this.bHg);
                        }
                        int n12 = this.aN(n7, akd_02.aH);
                        short s3 = mo_1.a(qe_0.hp(n12), qe_0.hq(n12), this.aNf, s, this.bDf);
                        if (s3 == Short.MIN_VALUE || s3 < s2 || s3 > s || (s3 = mo_1.a(qe_0.hp(n12 = this.aN(n7, akd_02.aH)), qe_0.hq(n12), this.aNf, s, this.bDf)) == Short.MIN_VALUE || s3 < s2 || s3 > s) continue;
                    }
                    float f = aqs_03.bIK + this.a(aqs_03, akd_02, aqs_04, akd_03, by2);
                    s = (byte)(aqs_03.cOV + 1);
                    if (aqs_04.bIK <= f || this.bHk.cpJ > 0 && s > this.bHk.cpJ) continue;
                    aqs_04.bIK = f;
                    if (aqs_04.cOR == 0.0f) {
                        aqs_04.cOR = this.v(n7, n8, n2, n3);
                    }
                    aqs_04.cOS = aqs_04.bIK + aqs_04.cOR;
                    aqs_04.cOT = aqs_03;
                    aqs_04.cOU = by2;
                    aqs_04.cOV = (byte)s;
                    if (aqs_04.cmD) {
                        this.a(aqs_04);
                    }
                    this.b(aqs_04);
                    aqs_04.cmD = true;
                    n5 = (byte)(n5 | nArray[by2][2]);
                }
            }
            aqs_03.cmD = false;
        }
        return -1;
    }

    private int[][] a(akd_0 akd_02, int n2, int n3) {
        if (this.bHk.cpR) {
            int n4;
            int n5 = Math.abs(n2 - akd_02.aG);
            if (n5 >= (n4 = Math.abs(n3 - akd_02.aH))) {
                return bHe;
            }
            return bHf;
        }
        return bHe;
    }

    private int a(int n2, int n3, short s, aqs_0 aqs_02) {
        aqs_0 aqs_03;
        this.bHv.clear();
        int n4 = 0;
        while ((aqs_03 = this.adq()) != null) {
            if (this.bHk.cpI < ++n4) {
                return -1;
            }
            if (aqs_03 == aqs_02) {
                return this.c(aqs_03);
            }
            akd_0 akd_02 = this.aNf[aqs_03.cOP + aqs_03.cOQ];
            int[][] nArray = this.a(akd_02, n2, n3);
            byte by = this.adp();
            block1: for (byte by2 = 0; by2 < by; by2 = (byte)(by2 + 1)) {
                byte by3;
                int[] nArray2 = nArray[by2];
                int n5 = akd_02.aG + nArray2[0];
                int n6 = akd_02.aH + nArray2[1];
                if (this.bHk.cpM && !this.bHk.cpN && n5 == n2 && n6 == n3) {
                    return this.c(aqs_03);
                }
                for (int j = -this.bDh; j <= this.bDh; ++j) {
                    for (int i2 = -this.bDh; i2 <= this.bDh; ++i2) {
                        if (!this.bHj.F(n5 + j, n6 + i2) || this.bHj.bD(n5 + j, n6 + i2) && (!this.bHk.cpM || n5 + j != n2 || n6 + i2 != n3) || (by3 = (byte)(this.a(akd_02.aG + j, akd_02.aH + i2, s, n5 + j, n6 + i2) ? 1 : 0)) == 0) continue block1;
                    }
                }
                aqs_0 aqs_04 = this.s(n5, n6, s);
                if (aqs_04 == null) {
                    System.out.println("Node inexistant ou trop de nodes. nodes testes : " + n4 + "/" + this.bHk.cpI);
                    continue;
                }
                if (aqs_04 == aqs_03.cOT) continue;
                if (this.bHk.cpM && aqs_04 == aqs_02) {
                    return this.c(aqs_03);
                }
                float f = aqs_03.bIK + this.a(aqs_03, akd_02, aqs_04, this.aNf[aqs_03.cOP + aqs_03.cOQ], by2);
                by3 = (byte)(aqs_03.cOV + 1);
                if (aqs_04.bIK <= f || this.bHk.cpJ > 0 && by3 > this.bHk.cpJ) continue;
                aqs_04.bIK = f;
                if (aqs_04.cOR == 0.0f) {
                    aqs_04.cOR = this.v(n5, n6, n2, n3);
                }
                aqs_04.cOS = aqs_04.bIK + aqs_04.cOR;
                aqs_04.cOT = aqs_03;
                aqs_04.cOU = by2;
                aqs_04.cOV = by3;
                if (aqs_04.cmD) {
                    this.a(aqs_04);
                }
                this.b(aqs_04);
                aqs_04.cmD = true;
            }
            aqs_03.cmD = false;
        }
        return -1;
    }

    private boolean a(int n2, int n3, short s, int n4, int n5) {
        int n6;
        int n7 = this.aN(n2, n3);
        if (n7 == 0) {
            return false;
        }
        int n8 = qe_0.hp(n7);
        short s2 = mo_1.a(n8, n6 = qe_0.hq(n7), this.aNf, s);
        if (s2 == Short.MIN_VALUE) {
            return false;
        }
        int n9 = this.aN(n4, n5);
        if (n9 == 0) {
            return false;
        }
        int n10 = qe_0.hp(n9);
        int n11 = qe_0.hq(n9);
        long l2 = this.aK(n8, n10);
        byte by = this.bHv.cn(l2);
        if (by != 0) {
            return by > 0;
        }
        int n12 = this.bHl.a(n8 + s2, n8, n6, this.aNf, n10, n11, this.aNf);
        if (n12 == 0) {
            this.bHv.a(l2, (byte)-1);
            return false;
        }
        for (int j = 0; j < n12; ++j) {
            akd_0 akd_02 = this.aNf[this.bHl.bDi[j]];
            if (akd_02.wp != s) continue;
            this.bHv.a(l2, (byte)1);
            return true;
        }
        this.bHv.a(l2, (byte)-1);
        return false;
    }

    private long aK(int n2, int n3) {
        if (n2 < n3) {
            return (long)n2 << 32 & 0xFFFFFFFF00000000L | (long)n3 & 0xFFFFFFFFL;
        }
        return (long)n3 << 32 & 0xFFFFFFFF00000000L | (long)n2 & 0xFFFFFFFFL;
    }

    private byte adp() {
        return this.bHk.cpH ? (byte)4 : 8;
    }

    protected float a(aqs_0 aqs_02, akd_0 akd_02, aqs_0 aqs_03, akd_0 akd_03, byte by) {
        float f = by >= 4 ? 1.4f : 1.0f;
        if (this.bHk.cpQ) {
            int n2 = Math.abs(akd_02.wp - akd_03.wp);
            f = n2 >= bHa.length ? (f += bHa[bHa.length - 1]) : (f += bHa[n2]);
        }
        if (!this.bHk.cpH && aqs_02.cOU != -1 && aqs_02.cOU != by) {
            f += 0.9f;
        } else if (this.bHk.cpO && aqs_02.cOU != -1 && aqs_02.cOU != by) {
            f += 10.0f;
        }
        return f;
    }

    private float u(int n2, int n3, int n4, int n5) {
        int n6 = Math.abs(n2 - n4);
        int n7 = Math.abs(n3 - n5);
        if (this.bHk.cpH) {
            return n6 + n7;
        }
        if (n6 < n7) {
            return (float)n6 * 1.4f + (float)Math.abs(n6 - n7);
        }
        return (float)n7 * 1.4f + (float)Math.abs(n6 - n7);
    }

    private float v(int n2, int n3, int n4, int n5) {
        int n6 = Math.abs(n2 - n4);
        int n7 = Math.abs(n3 - n5);
        if (this.bHk.cpH) {
            if (n6 == 0 && n7 == 0) {
                return 0.0f;
            }
            float f = (float)Math.sqrt(n6 * n6 + n7 * n7) * 0.01f;
            return (float)(n6 + n7) + f;
        }
        if (n6 < n7) {
            return (float)n6 * 1.4f + (float)Math.abs(n6 - n7);
        }
        return (float)n7 * 1.4f + (float)Math.abs(n6 - n7);
    }

    private static int aL(int n2, int n3) {
        return (n2 & 0xFFFF) << 16 | n3 & 0xFFFF;
    }

    private static int hp(int n2) {
        return n2 >>> 16 & 0xFFFF;
    }

    private static int hq(int n2) {
        return n2 & 0xFFFF;
    }

    protected static long aM(int n2, int n3) {
        return (long)(n2 + 131071 & 0x3FFFF) << 18 | (long)(n3 + 131071 & 0x3FFFF);
    }

    private static long r(int n2, int n3, short s) {
        return (long)(n2 + 131071 & 0x3FFFF) << 34 | (long)(n3 + 131071 & 0x3FFFF) << 16 | (long)(s + Short.MAX_VALUE & 0xFFFF);
    }

    public static int cy(long l2) {
        return (int)((l2 >>> 34 & 0x3FFFFL) - 131071L);
    }

    public static int cz(long l2) {
        return (int)((l2 >>> 16 & 0x3FFFFL) - 131071L);
    }

    public static short cA(long l2) {
        return (short)((l2 & 0xFFFFL) - 32767L);
    }

    private aqs_0 adq() {
        if (this.bHs < 0) {
            return null;
        }
        aqs_0 aqs_02 = this.bHr[this.bHs];
        this.bHs = (short)(this.bHs + 1);
        if (this.bHs > this.bHt) {
            this.bHt = (short)-1;
            this.bHs = (short)-1;
        }
        return aqs_02;
    }

    private void a(aqs_0 aqs_02) {
        if (aqs_02 == null) {
            return;
        }
        if (this.bHs == -1) {
            return;
        }
        short s = this.bHs;
        short s2 = this.bHt;
        if (this.bHr[s] == aqs_02) {
            this.bHs = (short)(this.bHs + 1);
            if (this.bHs > this.bHt) {
                this.bHt = (short)-1;
                this.bHs = (short)-1;
            }
            return;
        }
        if (this.bHr[s2] == aqs_02) {
            this.bHt = (short)(this.bHt - 1);
            if (this.bHt < this.bHs) {
                this.bHt = (short)-1;
                this.bHs = (short)-1;
            }
            return;
        }
        while (s < s2) {
            short s3 = (short)((s + s2) / 2);
            if (this.bHr[s3] == aqs_02) {
                System.arraycopy(this.bHr, s3 + 1, this.bHr, s3, this.bHt - s3);
                this.bHt = (short)(this.bHt - 1);
                return;
            }
            if (aqs_02.cOS >= this.bHr[s3].cOS) {
                s = (short)(s3 + 1);
                if (this.bHr[s3] == aqs_02) {
                    System.arraycopy(this.bHr, s + 1, this.bHr, s, this.bHt - s);
                    this.bHt = (short)(this.bHt - 1);
                    return;
                }
            }
            if (!(aqs_02.cOS <= this.bHr[s3].cOS)) continue;
            s2 = (short)(s3 - 1);
            if (this.bHr[s3] != aqs_02) continue;
            System.arraycopy(this.bHr, s2 + 1, this.bHr, s2, this.bHt - s2);
            this.bHt = (short)(this.bHt - 1);
            return;
        }
    }

    private void b(aqs_0 aqs_02) {
        assert (aqs_02 != null) : "'can't insert a null PathFinderNode";
        if (this.bHs == -1) {
            this.bHr[0] = aqs_02;
            this.bHt = 0;
            this.bHs = 0;
            return;
        }
        for (int j = this.bHs; j <= this.bHt; ++j) {
            if (!(aqs_02.cOS < this.bHr[j].cOS)) continue;
            if (this.bHs > 0) {
                if (j == this.bHs) {
                    this.bHs = (short)(this.bHs - 1);
                    this.bHr[this.bHs] = aqs_02;
                    return;
                }
                System.arraycopy(this.bHr, this.bHs, this.bHr, this.bHs - 1, j - this.bHs);
                this.bHs = (short)(this.bHs - 1);
                this.bHr[j - 1] = aqs_02;
                return;
            }
            System.arraycopy(this.bHr, j, this.bHr, j + 1, this.bHt - j + 1);
            this.bHr[j] = aqs_02;
            this.bHt = (short)(this.bHt + 1);
            return;
        }
        this.bHt = (short)(this.bHt + 1);
        this.bHr[this.bHt] = aqs_02;
    }

    private aqs_0 cB(long l2) {
        return this.s(qe_0.cy(l2), qe_0.cz(l2), qe_0.cA(l2));
    }

    private aqs_0 s(int n2, int n3, short s) {
        long l2 = qe_0.r(n2, n3, s);
        short s2 = this.bHw.bU(l2);
        if (s2 != 0) {
            return this.bHo[s2];
        }
        if (this.bHp >= bGW - 1) {
            a.error((Object)"No more free nodes. Ceel can't be added to open nodes. Think about increasing PathFinder.MAX_NODES");
            return null;
        }
        int n4 = this.aN(n2, n3);
        if (n4 == 0) {
            return null;
        }
        int n5 = qe_0.hp(n4);
        int n6 = qe_0.hq(n4);
        for (int j = n5; j < n5 + n6; ++j) {
            if (this.aNf[j].wp != s) continue;
            aqs_0 aqs_02 = this.bHo[this.bHp + 1];
            aqs_02.cOP = (short)n5;
            aqs_02.cOQ = (byte)(j - n5);
            aqs_02.cOO = (byte)n6;
            aqs_02.cOT = null;
            aqs_02.cOV = 0;
            aqs_02.cmD = false;
            aqs_02.cOS = 0.0f;
            aqs_02.bIK = Float.MAX_VALUE;
            aqs_02.cOR = 0.0f;
            this.bHp = (short)(this.bHp + 1);
            this.bHw.h(l2, this.bHp);
            return aqs_02;
        }
        return null;
    }

    private int aN(int n2, int n3) {
        long l2 = qe_0.aM(n2, n3);
        int n4 = this.bHu.eL(l2);
        if (n4 != 0) {
            return n4;
        }
        acm_1 acm_12 = this.bHj.ch(n2, n3);
        if (acm_12 == null) {
            return 0;
        }
        if (!acm_12.F(n2, n3)) {
            a.error((Object)"Map pas pr\u00e9sente pour ces coordonn\u00e9es... Probl\u00e8me de topologyMapInstanceSet mal charg\u00e9/initialis\u00e9 ?");
            return 0;
        }
        int n5 = acm_12.a(n2, n3, this.aNf, (int)this.bHq);
        assert (n5 != 0) : "no data for a specific cell";
        n4 = qe_0.aL(this.bHq, n5);
        this.bHu.m(l2, n4);
        this.bHq = (short)(this.bHq + n5);
        return n4;
    }

    private int c(aqs_0 aqs_02) {
        assert (aqs_02 != null) : "can't compute a path with a null end node";
        this.bHm = 0;
        aqs_0 aqs_03 = aqs_02;
        if (!this.bHk.cpL && aqs_03.cOT != null) {
            aqs_03 = aqs_03.cOT;
        }
        if (!this.bHk.cpK) {
            while (aqs_03.cOT != null && this.bHm < 128) {
                akd_0 akd_02 = this.aNf[aqs_03.cOP + aqs_03.cOQ];
                short s = this.bHm;
                this.bHm = (short)(s + 1);
                this.bHn[s] = qe_0.r(akd_02.aG, akd_02.aH, akd_02.wp);
                aqs_03 = aqs_03.cOT;
            }
            if (aqs_03.cOT == null) {
                return this.bHm;
            }
        } else {
            while (aqs_03 != null && this.bHm < 128) {
                akd_0 akd_03 = this.aNf[aqs_03.cOP + aqs_03.cOQ];
                short s = this.bHm;
                this.bHm = (short)(s + 1);
                this.bHn[s] = qe_0.r(akd_03.aG, akd_03.aH, akd_03.wp);
                aqs_03 = aqs_03.cOT;
            }
            if (aqs_03 == null) {
                return this.bHm;
            }
        }
        return -1;
    }

    public boolean c(ry ry2, ry ry3) {
        int n2;
        int n3;
        int n4;
        int n5;
        if (ry2 == null || ry3 == null) {
            a.error((Object)("IMpossible de savoir si un chemin en ligne droite existe: " + ry2 + "/" + ry3));
            return false;
        }
        if (ry3.equals(ry2)) {
            return true;
        }
        if (ry2.getX() != ry3.getX() && ry2.getY() != ry3.getY()) {
            a.info((Object)"Cellules non align\u00e9es : impossible d'avoir un chemin en ligne droite");
            return false;
        }
        int n6 = ry2.getX();
        int n7 = ry3.getX();
        int n8 = ry2.getY();
        int n9 = ry3.getY();
        if (n6 == n7) {
            n5 = 0;
            n4 = n9 > n8 ? 1 : -1;
            n3 = Math.abs(n9 - n8);
        } else {
            n5 = n7 > n6 ? 1 : -1;
            n4 = 0;
            n3 = Math.abs(n7 - n6);
        }
        int n10 = n6;
        int n11 = n8;
        int n12 = this.aN(n10, n11);
        if (n12 == 0) {
            a.info((Object)("IMpossible de r\u00e9cup\u00e9rer les informations de la cellule " + n10 + ", " + n11));
            return false;
        }
        int n13 = qe_0.hp(n12);
        int n14 = mo_1.a(n13, n2 = qe_0.hq(n12), this.aNf, ry2.wk());
        if (n14 == Short.MIN_VALUE) {
            a.error((Object)("Position en z non valide pour cette cellule : " + ry2));
            return false;
        }
        try {
            for (int j = 0; j < n3; ++j) {
                int n15;
                int n16 = n10 + n5;
                int n17 = n11 + n4;
                int n18 = this.aN(n16, n17);
                if (n18 == 0) {
                    a.debug((Object)("Une cellule du mouvement en ligne droite n'existe pas : " + n16 + ", " + n17));
                    return false;
                }
                int n19 = qe_0.hp(n18);
                int n20 = this.bHl.a(n14 + n13, n13, n2, this.aNf, n19, n15 = qe_0.hq(n18), this.aNf);
                if (n20 == 0) {
                    a.debug((Object)("Cellule " + n16 + ", " + n17 + " emp\u00eache le mouvement"));
                    return false;
                }
                n10 = n16;
                n11 = n17;
                n13 = n19;
                n2 = n15;
                n14 = this.bHl.bDi[0] - n13;
            }
        }
        catch (Throwable throwable) {
            a.error((Object)"Exception pendant le check pour savoir s'il y a un chemin en ligne droite : ", throwable);
            return false;
        }
        return true;
    }
}

