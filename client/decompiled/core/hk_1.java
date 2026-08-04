/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Hk
 */
public class hk_1
extends so_0 {
    private long bdw;
    private boolean acR;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9);
        byteBuffer.putLong(this.bdw);
        byteBuffer.put(this.acR ? (byte)1 : 0);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 26301;
    }

    public void bK(long l2) {
        this.bdw = l2;
    }

    public void aP(boolean bl2) {
        this.acR = bl2;
    }
}

