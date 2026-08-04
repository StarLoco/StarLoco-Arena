/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class ahJ
extends so_0 {
    private long mw;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.mw);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5109;
    }

    public void cm(long l2) {
        this.mw = l2;
    }
}

