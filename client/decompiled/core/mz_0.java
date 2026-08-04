/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from mZ
 */
public class mz_0
extends so_0 {
    private long ap;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.ap);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 26307;
    }

    public void d(long l2) {
        this.ap = l2;
    }
}

