/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class rC
extends so_0 {
    private long aj;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.aj);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 8105;
    }

    public void j(long l2) {
        this.aj = l2;
    }
}

