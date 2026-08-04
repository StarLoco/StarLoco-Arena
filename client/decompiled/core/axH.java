/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class axH
extends so_0 {
    private long sB;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.sB);
        return this.a((byte)2, byteBuffer.array());
    }

    public void am(long l2) {
        this.sB = l2;
    }

    public int getId() {
        return 22004;
    }
}

