/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class awR
extends so_0 {
    private long Pk;

    public awR(long l2) {
        this.Pk = l2;
    }

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.Pk);
        return this.a((byte)2, byteBuffer.array());
    }

    public long CK() {
        return this.Pk;
    }

    public int getId() {
        return 511;
    }
}

