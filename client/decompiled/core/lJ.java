/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import org.apache.log4j.Logger;

public abstract class lJ {
    protected static final Logger a = Logger.getLogger(lJ.class);
    private static final CRC32 HI = new CRC32();
    private int HJ;
    private short HK;
    private long HL = System.currentTimeMillis();
    private long HM = Long.MAX_VALUE;
    private boolean HN = false;
    private long HO;
    private boolean HP = false;

    private lJ() {
    }

    protected lJ(short s) {
        this();
        this.HK = s;
    }

    public int qw() {
        return this.HJ;
    }

    public void cd(int n2) {
        this.HJ = n2;
    }

    public short qx() {
        return this.HK;
    }

    public void D(short s) {
        this.HK = s;
    }

    public long m(byte[] byArray) {
        if (byArray != null && byArray.length > 0) {
            HI.reset();
            HI.update(byArray);
            this.HO = HI.getValue();
        } else {
            this.HO = 0L;
        }
        return this.HO;
    }

    public final void qy() {
        if (!this.HP) {
            if (this.HM < Long.MAX_VALUE) {
                this.HN = true;
            }
        } else {
            a.error((Object)("toggleModified sur un BinaryStorable flagg\u00e9 UNUSED " + this.qw()));
        }
    }

    public abstract int cq();

    public abstract byte[] cr();

    public abstract void a(ByteBuffer var1, int var2, short var3);

    public abstract lJ cs();

    public long qz() {
        return this.HM;
    }

    public void ak(long l2) {
        this.HM = l2;
    }

    public boolean qA() {
        return this.HN;
    }

    public long qB() {
        return this.HL;
    }

    public void a(ace_0 ace_02) {
        ace_02.a(W.br, (Object)this);
        this.HN = false;
        this.HL = System.currentTimeMillis();
    }

    public void b(ace_0 ace_02) {
        ace_02.a(W.bq, (Object)this);
        this.HN = false;
        this.HL = System.currentTimeMillis();
    }

    public boolean qC() {
        return this.HP;
    }

    public String toString() {
        return "Bstorable type:" + this.cq() + ", id:" + this.qw() + ", version:" + this.qx();
    }
}

