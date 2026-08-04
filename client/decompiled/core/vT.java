/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class vT
extends so_0 {
    private long ap;
    private boolean acR;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9);
        byteBuffer.putLong(this.ap);
        byteBuffer.put(this.acR ? (byte)1 : 0);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 26305;
    }

    public void d(long l2) {
        this.ap = l2;
    }

    public void aP(boolean bl2) {
        this.acR = bl2;
    }
}

