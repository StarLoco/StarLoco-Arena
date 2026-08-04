/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Ko
extends so_0 {
    private long bZ;
    private short bny;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.putLong(this.bZ);
        byteBuffer.putShort(this.bny);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 557;
    }

    public void g(long l2) {
        this.bZ = l2;
    }

    public void aH(short s) {
        this.bny = s;
    }
}

