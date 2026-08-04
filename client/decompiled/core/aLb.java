/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.Date;
import org.apache.log4j.Logger;

public abstract class aLb
implements cn_1 {
    public static int dUv = 0;
    public static int dUw = 0;
    public static int dUx = 1;
    public static int dUy = 2;
    public static int dUz = 3;
    protected long nD;
    protected long dUA = 0L;
    protected String dUB;
    protected int dUC;
    protected long dUD = 0L;
    protected String dUE;
    protected byte[] dUF;
    protected Date dUG;
    protected boolean dUH;
    protected boolean dUI;
    protected boolean dUJ;
    protected int dUK;
    protected static final Logger a = Logger.getLogger(aLb.class);

    public void b() {
        this.nD = 0L;
        this.dUA = -1L;
        this.dUB = null;
        this.dUC = dUv;
        this.dUD = -1L;
        this.dUE = null;
        this.dUF = null;
        this.dUK = dUw;
        this.dUG = null;
        this.dUH = false;
        this.dUJ = false;
        this.dUI = false;
    }

    public void j() {
    }

    public byte[] cd() {
        byte[] byArray = aey_0.hH(this.dUB);
        byte[] byArray2 = aey_0.hH(this.dUE);
        int n2 = this.dUF == null ? 0 : this.dUF.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(17 + byArray.length + 4 + 8 + 1 + byArray2.length + 4 + n2 + 8 + 1 + 1 + 1 + 4);
        byteBuffer.putLong(this.nD);
        byteBuffer.putLong(this.dUA);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putInt(this.dUC);
        byteBuffer.putLong(this.dUD);
        byteBuffer.put((byte)byArray2.length);
        byteBuffer.put(byArray2);
        byteBuffer.putInt(n2);
        if (this.dUF != null) {
            byteBuffer.put(this.dUF);
        }
        byteBuffer.putLong(this.dUG == null ? System.currentTimeMillis() : this.dUG.getTime());
        if (this.dUH) {
            byteBuffer.put((byte)1);
        } else {
            byteBuffer.put((byte)0);
        }
        if (this.dUI) {
            byteBuffer.put((byte)1);
        } else {
            byteBuffer.put((byte)0);
        }
        if (this.dUJ) {
            byteBuffer.put((byte)1);
        } else {
            byteBuffer.put((byte)0);
        }
        byteBuffer.putInt(this.dUK);
        return byteBuffer.array();
    }

    public void f(ByteBuffer byteBuffer) {
        this.nD = byteBuffer.getLong();
        this.dUA = byteBuffer.getLong();
        byte[] byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        this.dUB = aey_0.V(byArray);
        this.dUC = byteBuffer.getInt();
        this.dUD = byteBuffer.getLong();
        byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        this.dUE = aey_0.V(byArray);
        this.dUF = new byte[byteBuffer.getInt()];
        byteBuffer.get(this.dUF);
        this.dUG = new Date(byteBuffer.getLong());
        this.dUH = byteBuffer.get() == 1;
        this.dUI = byteBuffer.get() == 1;
        this.dUJ = byteBuffer.get() == 1;
        this.dUK = byteBuffer.getInt();
    }

    public int nj() {
        int n2 = this.dUF == null ? 0 : this.dUF.length;
        return this.aVZ() + n2;
    }

    public int aVZ() {
        byte[] byArray = aey_0.hH(this.dUB);
        byte[] byArray2 = aey_0.hH(this.dUE);
        return 17 + byArray.length + 4 + 8 + 1 + byArray2.length + 4 + 8 + 1 + 1 + 1 + 4;
    }

    public abstract void release();

    public long aWa() {
        return this.dUA;
    }

    public void eG(long l2) {
        this.dUA = l2;
    }

    public long aWb() {
        return this.dUD;
    }

    public void eH(long l2) {
        this.dUD = l2;
    }

    public void c(long l2) {
        this.nD = l2;
    }

    public long getId() {
        return this.nD;
    }

    public String aWc() {
        return this.dUB;
    }

    public void lD(String string) {
        this.dUB = string;
    }

    public int aWd() {
        return this.dUC;
    }

    public void pi(int n2) {
        this.dUC = n2;
    }

    public String aWe() {
        return this.dUE;
    }

    public void lE(String string) {
        this.dUE = string;
    }

    public byte[] aWf() {
        return this.dUF;
    }

    public void af(byte[] byArray) {
        this.dUF = byArray;
    }

    public int getState() {
        return this.dUK;
    }

    public boolean awR() {
        return this.dUK == dUx;
    }

    public boolean aWg() {
        return this.dUK == dUy;
    }

    public void setState(int n2) {
        this.dUK = n2;
    }

    public boolean aWh() {
        return this.dUH;
    }

    public void fk(boolean bl2) {
        this.dUH = bl2;
    }

    public boolean aWi() {
        return this.dUI;
    }

    public void fl(boolean bl2) {
        this.dUI = bl2;
    }

    public boolean aWj() {
        return this.dUJ;
    }

    public void fm(boolean bl2) {
        this.dUJ = bl2;
    }

    public Date getDate() {
        return this.dUG;
    }

    public void setDate(Date date) {
        this.dUG = date;
    }

    public String toString() {
        return "Id : " + this.nD + " SenderId : " + this.dUA + " SenderName : " + this.dUB + " SenderGame : " + this.dUC + " ReceiverId : " + this.dUD + " ReceiverName : " + this.dUE + " DeletedByReceiver : " + this.dUJ + " DeletedBySender : " + this.dUI + " Read : " + this.dUH + " Date : " + (this.dUG != null ? this.dUG : "null");
    }
}

