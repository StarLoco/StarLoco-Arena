/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class bw
extends so_0 {
    private long aj;
    private int cD;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.putLong(this.aj);
        byteBuffer.putInt(this.cD);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 22099;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void i(int n2) {
        this.cD = n2;
    }
}

