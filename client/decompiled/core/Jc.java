/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Jc
extends so_0 {
    private long aj;
    private boolean bjk = false;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9);
        byteBuffer.putLong(this.aj);
        byteBuffer.put((byte)(this.bjk ? 1 : 0));
        return this.a((byte)2, byteBuffer.array());
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void bI(boolean bl2) {
        this.bjk = bl2;
    }

    public int getId() {
        return 23000;
    }
}

