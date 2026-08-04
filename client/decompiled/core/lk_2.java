/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from lK
 */
public class lk_2
extends ael_2 {
    private long aj;
    private int nE;
    private int nF;
    private short nG;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aj = byteBuffer.getLong();
        this.nE = byteBuffer.getInt();
        this.nF = byteBuffer.getInt();
        this.nG = byteBuffer.getShort();
        return true;
    }

    public int getId() {
        return 8022;
    }

    public short gQ() {
        return this.nG;
    }

    public long K() {
        return this.aj;
    }

    public int gO() {
        return this.nE;
    }

    public int gP() {
        return this.nF;
    }
}

