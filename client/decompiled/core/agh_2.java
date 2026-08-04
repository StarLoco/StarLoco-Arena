/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aGh
 */
public class agh_2
extends so_0 {
    private long rc;
    private iz_0 dzD;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8 + this.dzD.nj());
        byteBuffer.putLong(this.rc);
        byteBuffer.put(this.dzD.cd());
        return this.a((byte)3, byteBuffer.array());
    }

    public void M(long l2) {
        this.rc = l2;
    }

    public void f(iz_0 iz_02) {
        this.dzD = iz_02;
    }

    public int getId() {
        return 17006;
    }
}

