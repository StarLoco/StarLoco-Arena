/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class auZ
extends so_0 {
    private long cYp;

    public auZ(long l2) {
        this.cYp = l2;
    }

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.cYp);
        return this.a((byte)2, byteBuffer.array());
    }

    public long aHZ() {
        return this.cYp;
    }

    public int getId() {
        return 517;
    }
}

