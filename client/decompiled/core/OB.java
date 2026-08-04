/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class OB
extends so_0 {
    private int cD;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt(this.cD);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 22097;
    }

    public void i(int n2) {
        this.cD = n2;
    }
}

