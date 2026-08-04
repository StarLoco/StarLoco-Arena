/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from bD
 */
public class bd_2
extends so_0 {
    private long gY;
    private short gZ;

    public void k(long l2) {
        this.gY = l2;
    }

    public void f(short s) {
        this.gZ = s;
    }

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.putLong(this.gY);
        byteBuffer.putShort(this.gZ);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 201;
    }
}

