/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from adL
 */
public class adl_0 {
    protected boolean cng;
    protected boolean cnh;
    protected int cni;
    protected boolean cnj;
    protected boolean cnk;
    protected int cnl;
    protected int cnm;
    protected boolean cnn;
    protected boolean baT;
    protected byte aRx;
    protected byte cno;
    protected byte aRy;
    protected byte cnp;
    protected int cnq;
    protected int cnr;
    protected byte cns;
    protected boolean cnt;
    protected int cnu;
    protected int cnv;
    protected boolean cnw;

    public void a(boolean bl2, int n2, byte by, byte by2, long l2, boolean bl3, int n3, int n4) {
        this.cnq = n3;
        this.a(n2, l2, by);
        this.cnw = n4 < axi.aJR();
        this.a(by2, l2, bl2);
        this.cni = 0;
        this.cnl = 0;
        this.cnm = 0;
        this.baT = false;
        this.cnj = false;
        this.cnn = false;
        this.cng = false;
        this.cnh = bl3;
        this.cnk = false;
        this.aRy = by;
        this.dg(bl2);
    }

    public void a(int n2, long l2, byte by) {
        this.cnr = n2;
        this.cns = by;
        int n3 = n2 * (100 + by) / 100;
        if (l2 > (long)nr_0.Pw) {
            n3 = n3 * (100 + nr_0.Px) / 100;
            this.cnt = true;
        } else {
            this.cnt = false;
        }
        this.cnu = n3;
        this.cnv = n3;
    }

    public void a(byte by, long l2, boolean bl2) {
        byte by2 = et_2.a(by, l2);
        this.cno = bl2 ? (byte)jr_0.VF().nextInt(25) : (byte)0;
        this.aRx = (byte)Math.min(by2 + this.cno, nr_0.Pq);
        this.ate();
    }

    public void dg(boolean bl2) {
        if (this.cnh) {
            this.cnp = (byte)(jr_0.VF().nextInt(5) + (bl2 ? jr_0.VF().nextInt(5) : 0));
            this.cnp = (byte)(this.cnp * (nr_0.Pt - this.aRy) / (nr_0.Pt / 2));
        } else {
            this.cnp = (byte)(-(jr_0.VF().nextInt(5) - (!bl2 ? jr_0.VF().nextInt(5) : 0)));
            this.cnp = (byte)(this.cnp * this.aRy / (nr_0.Pt / 2));
        }
        this.aRy = (byte)(this.aRy + this.cnp);
    }

    public void atd() {
        int n2;
        this.cng = true;
        this.cni = n2 = this.cnq * 100 / nr_0.Pv;
        if (this.cni == 0) {
            this.cnj = true;
        }
        this.cnm = n2 * n2 / 100;
        if (this.cnm == 0) {
            this.cnn = true;
        }
        int n3 = jr_0.VF().nextInt(13);
        this.cno = (byte)(this.cno + n3);
        this.aRx = (byte)Math.min(this.aRx + n3, nr_0.Pq);
        this.ate();
    }

    public void ate() {
        if (this.aRx >= nr_0.Pq) {
            this.cnk = true;
        } else if (this.cni != 0) {
            this.cni += this.aRx * this.cni / 100;
        }
    }

    public int nj() {
        return 40;
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.nj());
        byteBuffer.put(this.cng ? (byte)1 : 0);
        byteBuffer.put(this.cnh ? (byte)1 : 0);
        byteBuffer.putInt(this.cni);
        byteBuffer.put(this.cnj ? (byte)1 : 0);
        byteBuffer.put(this.cnk ? (byte)1 : 0);
        byteBuffer.putInt(this.cnl);
        byteBuffer.putInt(this.cnm);
        byteBuffer.put(this.cnn ? (byte)1 : 0);
        byteBuffer.put(this.baT ? (byte)1 : 0);
        byteBuffer.put(this.aRx);
        byteBuffer.put(this.cno);
        byteBuffer.put(this.aRy);
        byteBuffer.put(this.cnp);
        byteBuffer.putInt(this.cnq);
        byteBuffer.putInt(this.cnr);
        byteBuffer.put(this.cns);
        byteBuffer.put(this.cnt ? (byte)1 : 0);
        byteBuffer.putInt(this.cnu);
        byteBuffer.putInt(this.cnv);
        return byteBuffer.array();
    }

    public boolean atf() {
        return this.cng;
    }

    public boolean atg() {
        return this.cnh;
    }

    public int ath() {
        return this.cnu;
    }

    public int ati() {
        if (this.cnk) {
            return 100;
        }
        return this.cni;
    }

    public void jT(int n2) {
        if (this.cng) {
            this.cni += n2;
        }
    }

    public int atj() {
        return this.cnm;
    }

    public void jU(int n2) {
        if (this.cng && this.cnq * 10 < nr_0.Pv) {
            this.cnm += n2;
        }
    }

    public int atk() {
        if (this.cnw) {
            return 0;
        }
        return this.cnv;
    }

    public void ft(int n2) {
        if (this.cnv != 0) {
            this.cnv += n2;
        }
    }

    public boolean atl() {
        return this.cnj;
    }

    public void dh(boolean bl2) {
        this.cnj = bl2;
    }

    public boolean atm() {
        return this.cnn;
    }

    public void di(boolean bl2) {
        this.cnn = bl2;
    }

    public byte Nz() {
        return (byte)Math.max(Math.min(this.aRx, nr_0.Pq), 0);
    }

    public void au(byte by) {
        this.aRy = (byte)(this.aRy + by);
        this.cnp = (byte)(this.cnp + by);
    }

    public void av(byte by) {
        this.cno = (byte)(this.cno + by);
        this.aRx = (byte)(this.aRx + by);
    }

    public byte NA() {
        return (byte)Math.max(0, Math.min(this.aRy, nr_0.Pt));
    }

    public boolean atn() {
        return this.cnk;
    }

    public int ato() {
        return this.cnr;
    }

    public byte atp() {
        return this.cns;
    }

    public boolean atq() {
        return this.cnt;
    }

    public int atr() {
        return this.cnl;
    }

    public void jV(int n2) {
        this.cnl = n2;
    }

    public boolean isDead() {
        return this.baT;
    }

    public void dj(boolean bl2) {
        this.baT = bl2;
    }
}

