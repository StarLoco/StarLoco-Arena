/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from TW
 */
public class tw_0
extends so_0 {
    private long mw;
    private byte bOW;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9);
        byteBuffer.putLong(this.mw);
        byteBuffer.put(this.bOW);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5103;
    }

    public void am(byte by) {
        this.bOW = by;
    }

    public void cm(long l2) {
        this.mw = l2;
    }
}

