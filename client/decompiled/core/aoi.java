/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aoi
extends ael_2 {
    private byte aV;
    private long aj;
    private short bGe;
    private short bGf;
    private long sB;
    private long cKH;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        if (this.aV == 0) {
            this.aj = byteBuffer.getLong();
            this.bGe = byteBuffer.getShort();
            this.bGf = byteBuffer.getShort();
            this.sB = byteBuffer.getLong();
            this.cKH = byteBuffer.getLong();
        }
        return true;
    }

    public int getId() {
        return 6014;
    }

    public byte an() {
        return this.aV;
    }

    public long K() {
        return this.aj;
    }

    public short aCE() {
        return this.bGe;
    }

    public short aCF() {
        return this.bGf;
    }

    public long mb() {
        return this.sB;
    }

    public long aCG() {
        return this.cKH;
    }
}

