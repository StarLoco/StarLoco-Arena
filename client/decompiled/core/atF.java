/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public final class atF
extends lJ {
    private static final short fn = 1;
    private short cIm;
    private short cUh;
    private short cIo;
    private short cIp;
    private short cUi;
    private int[] cUj;

    public atF() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cVl.getId();
    }

    public byte[] cr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(11 + 4 * this.cUj.length);
        byteBuffer.putShort(this.cIm);
        byteBuffer.putShort(this.cUh);
        byteBuffer.putShort(this.cIo);
        byteBuffer.putShort(this.cIp);
        byteBuffer.putShort(this.cUi);
        byteBuffer.put((byte)this.cUj.length);
        for (int n2 : this.cUj) {
            byteBuffer.putInt(n2);
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.cIm = byteBuffer.getShort();
            this.cUh = byteBuffer.getShort();
            this.cIo = byteBuffer.getShort();
            this.cIp = byteBuffer.getShort();
            this.cUi = byteBuffer.getShort();
            this.cUj = new int[byteBuffer.get()];
            for (int j = 0; j < this.cUj.length; ++j) {
                this.cUj[j] = byteBuffer.getInt();
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new atF();
    }

    public short aBY() {
        return this.cIm;
    }

    public void bV(short s) {
        this.cIm = s;
    }

    public short aGJ() {
        return this.cUh;
    }

    public void bW(short s) {
        this.cUh = s;
    }

    public short aCb() {
        return this.cIp;
    }

    public void bX(short s) {
        this.cIp = s;
    }

    public short aGK() {
        return this.cUi;
    }

    public void bY(short s) {
        this.cUi = s;
    }

    public short aCa() {
        return this.cIo;
    }

    public void bZ(short s) {
        this.cIo = s;
    }

    public int[] aGL() {
        return this.cUj;
    }

    public void D(float[] fArray) {
        this.cUj = new int[fArray.length];
        for (int j = 0; j < fArray.length; ++j) {
            this.cUj[j] = (int)fArray[j];
        }
    }
}

