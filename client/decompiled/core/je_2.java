/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.Arrays;

/*
 * Renamed from jE
 */
public class je_2 {
    private int Bh;
    private int Bi;
    private int Bj;
    private int Bk;
    private int[] Bl = new int[15];
    private boolean[] Bm = new boolean[15];
    private boolean[] Bn = new boolean[15];
    private int[] Bo = new int[15];
    private ano_2 Bp = new ano_2();
    private ano_2 Bq = new ano_2();
    private int Br;
    private int Bs;
    private jg_0 Bt = new jg_0();
    private jg_0 Bu = new jg_0();
    private jg_0 Bv = new jg_0();
    private jg_0 Bw = new jg_0();
    private boolean Bx;
    private boolean By;
    private int Bz;
    public static je_2 BA = new je_2();
    public static final int BB = 1000;
    public static final int BC = -600;
    public static final int BD = -100;
    public static final int BE = -50;

    public static je_2 mU() {
        return BA;
    }

    public void clear() {
        this.Bh = 6000;
        this.Bi = 1;
        this.Bj = 6;
        this.Bk = 8;
        Arrays.fill(this.Bl, 2);
        Arrays.fill(this.Bm, false);
        Arrays.fill(this.Bn, false);
        Arrays.fill(this.Bo, 0);
        this.By = true;
        this.Bx = true;
        this.Bs = 0;
        this.Br = 0;
        this.Bu.clear();
        this.Bw.clear();
        this.Bt.clear();
        this.Bv.clear();
        this.Bp.clear();
        this.Bq.clear();
        this.Bz = 6;
    }

    private void normalize() {
        int n2;
        int n3;
        this.Bh = Math.max(1000, this.Bh);
        this.Bi = Math.min(Math.max(this.Bi, 1), 8);
        this.Bj = Math.max(Math.min(8, this.Bj), this.Bi);
        this.Br = Math.max(this.Br, -100);
        this.Bs = Math.max(this.Bs, -50);
        this.Bk = Math.max(this.Bk, 1);
        for (n3 = 0; n3 < this.Bl.length; ++n3) {
            if (this.Bl[n3] > 0) continue;
            this.Bl[n3] = 1;
        }
        for (n3 = 0; n3 < this.Bo.length; ++n3) {
            this.Bo[n3] = Math.max(this.Bo[n3], -600);
        }
        int[] nArray = this.Bp.pL();
        for (n2 = 0; n2 < nArray.length; ++n2) {
            if (this.Bp.get(nArray[n2]) + this.Br >= -100) continue;
            this.Bp.bz(nArray[n2], -100 - this.Br);
        }
        nArray = this.Bq.pL();
        for (n2 = 0; n2 < nArray.length; ++n2) {
            if (this.Bq.get(nArray[n2]) + this.Bs >= -50) continue;
            this.Bq.bz(nArray[n2], -50 - this.Bs);
        }
    }

    public void a(np_1[] np_1Array) {
        np_1 np_12 = null;
        for (int j = 0; j < np_1Array.length; ++j) {
            if ((np_12 = np_1.b(np_12, np_1Array[j])).rg().length < np_12.T()) continue;
            np_12.a(this);
            np_12 = null;
        }
    }

    public byte a(et_2[] et_2Array, np_1[] np_1Array, ib_2 ib_22) {
        this.clear();
        this.a(np_1Array);
        this.normalize();
        return this.a(et_2Array, ib_22);
    }

    private byte a(et_2[] et_2Array, ib_2 ib_22) {
        int n2;
        int n3 = 0;
        if (et_2Array.length < this.Bi || et_2Array.length > this.Bj) {
            return 45;
        }
        int[] nArray = new int[15];
        Arrays.fill(nArray, 0);
        for (n2 = 0; n2 < et_2Array.length; ++n2) {
            et_2 et_22 = et_2Array[n2];
            if (this.Bm[et_22.cu()] && !this.Bn[et_22.cu()]) {
                return 45;
            }
            n3 += et_22.Nw() + this.Bo[et_22.cu()];
            byte by = et_22.cu();
            nArray[by] = nArray[by] + 1;
            ByteBuffer byteBuffer = ByteBuffer.wrap(et_22.Nt());
            while (byteBuffer.hasRemaining()) {
                int n4 = byteBuffer.getInt();
                if (this.Bt.contains(n4)) {
                    return 61;
                }
                if (!this.Bx && !this.Bv.contains(n4)) {
                    return 61;
                }
                if (this.Bp.contains(n4)) {
                    n3 += this.Bp.get(n4);
                }
                n3 += this.Br;
            }
            ByteBuffer byteBuffer2 = ByteBuffer.wrap(et_22.Nu());
            while (byteBuffer2.hasRemaining()) {
                byteBuffer2.getShort();
                int n5 = byteBuffer2.getInt();
                if (this.Bu.contains(n5)) {
                    return 62;
                }
                if (!this.By && !this.Bw.contains(n5)) {
                    return 62;
                }
                if (this.Bq.contains(n5)) {
                    n3 += this.Bq.get(n5);
                }
                n3 += this.Bs;
            }
            if (!et_22.NK()) continue;
            if (nr_0.cs(et_22.Ny()) > this.Bz) {
                return 71;
            }
            if (et_22.NB() != 4 && ib_22.aX(et_22.NH()) < axi.aJS()) {
                return 77;
            }
            if (et_22.NB() != 4 || ib_22.aX(et_22.NH()) >= axi.aJQ()) continue;
            return 77;
        }
        n2 = 0;
        for (int j = 0; j < 15; ++j) {
            if (nArray[j] <= 0) continue;
            ++n2;
            if (this.Bl[j] >= nArray[j]) continue;
            return 63;
        }
        if (n2 > this.Bk) {
            return 75;
        }
        if (n3 > this.Bh) {
            return 46;
        }
        return 0;
    }

    protected int mV() {
        return this.Bh;
    }

    protected void bf(int n2) {
        this.Bh = n2;
    }

    protected int mW() {
        return this.Bi;
    }

    protected void bg(int n2) {
        this.Bi = n2;
    }

    protected int mX() {
        return this.Bj;
    }

    protected void bh(int n2) {
        this.Bj = n2;
    }

    protected void r(int n2, int n3) {
        for (int j = 0; j < this.Bl.length; ++j) {
            if (j != n2 && n2 != 0) continue;
            int n4 = j;
            this.Bl[n4] = this.Bl[n4] + n3;
        }
    }

    protected void bi(int n2) {
        this.Bm[n2] = true;
    }

    protected void bj(int n2) {
        this.Bn[n2] = true;
    }

    protected void mY() {
        for (int j = 0; j < this.Bl.length; ++j) {
            this.Bm[j] = true;
        }
    }

    protected void s(int n2, int n3) {
        int n4 = n2;
        this.Bo[n4] = this.Bo[n4] + n3;
    }

    protected void bk(int n2) {
        int n3 = 0;
        while (n3 < this.Bo.length) {
            int n4 = n3++;
            this.Bo[n4] = this.Bo[n4] + n2;
        }
    }

    protected void t(int n2, int n3) {
        if (this.Bp.contains(n2)) {
            n3 += this.Bp.get(n2);
        }
        this.Bp.bz(n2, n3);
    }

    protected void u(int n2, int n3) {
        if (this.Bq.contains(n2)) {
            n3 += this.Bq.get(n2);
        }
        this.Bq.bz(n2, n3);
    }

    protected void bl(int n2) {
        this.Br += n2;
    }

    protected void bm(int n2) {
        this.Bs += n2;
    }

    protected void O(boolean bl2) {
        this.Bx = bl2;
    }

    protected void P(boolean bl2) {
        this.By = bl2;
    }

    protected void bn(int n2) {
        this.Bt.add(n2);
    }

    protected void bo(int n2) {
        this.Bu.add(n2);
    }

    protected void bp(int n2) {
        this.Bv.add(n2);
    }

    protected void bq(int n2) {
        this.Bw.add(n2);
    }

    public ano_2 mZ() {
        return this.Bp;
    }

    public ano_2 na() {
        return this.Bq;
    }

    public jg_0 nb() {
        return this.Bt;
    }

    public jg_0 nc() {
        return this.Bu;
    }

    public jg_0 nd() {
        return this.Bv;
    }

    public jg_0 ne() {
        return this.Bw;
    }

    public boolean[] nf() {
        return this.Bm;
    }

    public int[] ng() {
        return this.Bo;
    }

    public int nh() {
        return this.Bk;
    }

    public void br(int n2) {
        this.Bk = n2;
    }

    public int ni() {
        return this.Bz;
    }

    public void bs(int n2) {
        this.Bz = n2;
    }
}

