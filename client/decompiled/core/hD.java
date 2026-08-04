/*
 * Decompiled with CFR 0.152.
 */
public class hD {
    private long wb;
    private long wc;
    private byte wd;
    private short we;
    private byte wf;
    private int wg;

    public hD() {
    }

    public hD(long l2, byte by, short s, byte by2) {
        this(l2, by, s, by2, 120);
    }

    public hD(long l2, byte by, short s, byte by2, int n2) {
        this(l2, l2, by, s, by2, n2);
    }

    public hD(long l2, long l3, byte by, short s, byte by2, int n2) {
        this.wb = l2;
        this.wc = l3;
        this.wd = by;
        this.we = s;
        this.wf = by2;
        this.wg = n2;
    }

    public long kJ() {
        return this.wb;
    }

    public long kK() {
        return this.wc == -1L ? this.wb : this.wc;
    }

    public byte kL() {
        return this.wd;
    }

    public short kM() {
        return this.we;
    }

    public byte kN() {
        return this.wf;
    }

    public int getDuration() {
        return this.wg;
    }

    public void b(acf acf2) {
        this.wb = acf2.readLong();
        this.wc = acf2.readLong();
        this.wd = acf2.readByte();
        this.we = acf2.readShort();
        this.wf = acf2.readByte();
        this.wg = acf2.readInt();
    }

    public void a(aij_1 aij_12) {
        aij_12.writeLong(this.wb);
        aij_12.writeLong(this.wc);
        aij_12.writeByte(this.wd);
        aij_12.writeShort(this.we);
        aij_12.writeByte(this.wf);
        aij_12.writeInt(this.wg);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof hD)) {
            return false;
        }
        hD hD2 = (hD)object;
        return this.wb == hD2.wb && this.wc == hD2.wc && this.wd == hD2.wd && this.we == hD2.we && this.wf == hD2.wf && this.wg == hD2.wg;
    }
}

