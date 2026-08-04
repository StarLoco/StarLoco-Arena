/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from pY
 */
public class py_0
extends so_0 {
    private long sB;

    public byte[] encode() {
        int n2 = 8;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putLong(this.sB);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 2260;
    }

    public void am(long l2) {
        this.sB = l2;
    }
}

