/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public final class GE
extends lJ {
    private static final short fn = 1;
    private int bbw;
    private int bbx;
    private int bby;
    private int bbz;
    private int bbA;
    private int bbB;
    private int bbC;
    private short bbD;
    private int[] bbE = ug_2.bQd;
    private boolean bbF;
    private int bbG;
    private int bbH;
    private int bbI;
    private int bbJ;
    private np_1[] UE = jn_1.bkb;
    private int bbK;
    private int bbL;

    public GE() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUT.getId();
    }

    public byte[] cr() {
        int n2 = 1;
        for (np_1 np_12 : this.UE) {
            n2 += np_12.nj();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2 + 4 + 4 + 4 + 4 + 4 + 4 + 4 + 1 + 4 * this.bbE.length + 1 + 2 + 4 + 4 + 4 + 4 + 4 + 4);
        byteBuffer.putInt(this.bbw);
        byteBuffer.putInt(this.bbx);
        byteBuffer.putInt(this.bby);
        byteBuffer.putInt(this.bbz);
        byteBuffer.putInt(this.bbA);
        byteBuffer.putInt(this.bbB);
        byteBuffer.putInt(this.bbC);
        byteBuffer.put((byte)this.bbE.length);
        for (int n4 : this.bbE) {
            byteBuffer.putInt(n4);
        }
        byteBuffer.put(this.bbF ? (byte)1 : 0);
        byteBuffer.putShort(this.bbD);
        byteBuffer.putInt(this.bbG);
        byteBuffer.putInt(this.bbH);
        byteBuffer.putInt(this.bbI);
        byteBuffer.putInt(this.bbJ);
        byteBuffer.put((byte)this.UE.length);
        for (np_1 np_13 : this.UE) {
            byteBuffer.put(np_13.cd());
        }
        byteBuffer.putInt(this.bbK);
        byteBuffer.putInt(this.bbL);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            this.bbw = byteBuffer.getInt();
            this.bbx = byteBuffer.getInt();
            this.bby = byteBuffer.getInt();
            this.bbz = byteBuffer.getInt();
            this.bbA = byteBuffer.getInt();
            this.bbB = byteBuffer.getInt();
            this.bbC = byteBuffer.getInt();
            this.bbE = new int[byteBuffer.get()];
            for (n3 = 0; n3 < this.bbE.length; ++n3) {
                this.bbE[n3] = byteBuffer.getInt();
            }
            this.bbF = byteBuffer.get() == 1;
            this.bbD = byteBuffer.getShort();
            this.bbG = byteBuffer.getInt();
            this.bbH = byteBuffer.getInt();
            this.bbI = byteBuffer.getInt();
            this.bbJ = byteBuffer.getInt();
            n3 = byteBuffer.get();
            if (n3 == 0) {
                this.UE = jn_1.bkb;
            } else {
                this.UE = new np_1[n3];
                for (int j = 0; j < this.UE.length; ++j) {
                    this.UE[j] = np_1.j(byteBuffer);
                }
            }
            this.bbK = byteBuffer.getInt();
            this.bbL = byteBuffer.getInt();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new GE();
    }

    public int Qs() {
        return this.bbw;
    }

    public void fH(int n2) {
        this.bbw = n2;
    }

    public int Qt() {
        return this.bbx;
    }

    public void fI(int n2) {
        this.bbx = n2;
    }

    public int Qu() {
        return this.bby;
    }

    public void fJ(int n2) {
        this.bby = n2;
    }

    public int Qv() {
        return this.bbz;
    }

    public void fK(int n2) {
        this.bbz = n2;
    }

    public int Qw() {
        return this.bbA;
    }

    public void fL(int n2) {
        this.bbA = n2;
    }

    public int[] Qx() {
        return this.bbE;
    }

    public void fM(int n2) {
        int[] nArray = new int[this.bbE.length + 1];
        System.arraycopy(this.bbE, 0, nArray, 0, this.bbE.length);
        nArray[this.bbE.length] = n2;
        this.bbE = nArray;
    }

    public int Qy() {
        return this.bbB;
    }

    public void fN(int n2) {
        this.bbB = n2;
    }

    public int Qz() {
        return this.bbC;
    }

    public void fO(int n2) {
        this.bbC = n2;
    }

    public boolean QA() {
        return this.bbF;
    }

    public void bu(boolean bl2) {
        this.bbF = bl2;
    }

    public short QB() {
        return this.bbD;
    }

    public void az(short s) {
        this.bbD = s;
    }

    public int QC() {
        return this.bbG;
    }

    public void fP(int n2) {
        this.bbG = n2;
    }

    public int QD() {
        return this.bbH;
    }

    public void fQ(int n2) {
        this.bbH = n2;
    }

    public void a(np_1 np_12) {
        np_1[] np_1Array = this.UE;
        this.UE = new np_1[this.UE.length + 1];
        System.arraycopy(np_1Array, 0, this.UE, 0, np_1Array.length);
        this.UE[this.UE.length - 1] = np_12;
    }

    public boolean fR(int n2) {
        for (np_1 np_12 : this.UE) {
            if (np_12.getId() != n2) continue;
            return true;
        }
        return false;
    }

    public np_1[] tv() {
        return this.UE;
    }

    public int QE() {
        return this.bbI;
    }

    public void fS(int n2) {
        this.bbI = n2;
    }

    public int QF() {
        return this.bbJ;
    }

    public void fT(int n2) {
        this.bbJ = n2;
    }

    public int QG() {
        return this.bbK;
    }

    public void fU(int n2) {
        this.bbK = n2;
    }

    public int QH() {
        return this.bbL;
    }

    public void fV(int n2) {
        this.bbL = n2;
    }
}

