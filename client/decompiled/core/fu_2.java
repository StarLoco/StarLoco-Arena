/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from fu
 */
public class fu_2
extends so_0 {
    private long rc;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.rc);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 17004;
    }

    public void M(long l2) {
        this.rc = l2;
    }
}

