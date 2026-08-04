/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from fW
 */
public class fw_1
extends so_0 {
    private long sa;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.sa);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5101;
    }

    public void N(long l2) {
        this.sa = l2;
    }
}

