/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Pv
 */
public abstract class pv_2
extends so_0 {
    private long mw;
    private int cD;
    private short bDT;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(14);
        byteBuffer.putLong(this.mw);
        byteBuffer.putInt(this.cD);
        byteBuffer.putShort(this.bDT);
        return this.a((byte)3, byteBuffer.array());
    }

    public void cm(long l2) {
        this.mw = l2;
    }

    public void i(int n2) {
        this.cD = n2;
    }

    public void aR(short s) {
        this.bDT = s;
    }
}

