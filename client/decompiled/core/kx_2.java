/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from kx
 */
public class kx_2
extends so_0 {
    private long lc;
    private byte Ex;

    public byte[] encode() {
        int n2 = 9;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putLong(this.lc);
        byteBuffer.put(this.Ex);
        return this.a((byte)2, byteBuffer.array());
    }

    public void ad(long l2) {
        this.lc = l2;
    }

    public void p(byte by) {
        this.Ex = by;
    }

    public int getId() {
        return 28633;
    }
}

