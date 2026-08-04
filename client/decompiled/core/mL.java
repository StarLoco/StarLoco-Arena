/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class mL
extends so_0 {
    long sB;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.sB);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 2600;
    }

    public void am(long l2) {
        this.sB = l2;
    }
}

