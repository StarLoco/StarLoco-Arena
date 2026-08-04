/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aKk
 */
public class akk_1
extends so_0 {
    private long ia;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9);
        byteBuffer.putLong(this.ia);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 15006;
    }

    public void eE(long l2) {
        this.ia = l2;
    }
}

