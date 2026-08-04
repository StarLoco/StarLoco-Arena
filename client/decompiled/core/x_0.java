/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from X
 */
public class x_0
extends so_0 {
    private long bu;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(this.bu);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 26331;
    }

    public void f(long l2) {
        this.bu = l2;
    }
}

