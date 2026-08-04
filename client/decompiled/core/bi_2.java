/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from BI
 */
public class bi_2
extends so_0 {
    private long lc;

    public byte[] encode() {
        int n2 = 8;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putLong(this.lc);
        return this.a((byte)2, byteBuffer.array());
    }

    public void ad(long l2) {
        this.lc = l2;
    }

    public int getId() {
        return 28605;
    }
}

