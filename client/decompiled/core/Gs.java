/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Gs
extends so_0 {
    private int bbc;

    public void fG(int n2) {
        this.bbc = n2;
    }

    public byte[] encode() {
        int n2 = 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putInt(this.bbc);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 4512;
    }
}

