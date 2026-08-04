/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Gc
 */
public class gc_0
extends so_0 {
    private byte[] zs;
    private long sB;
    private boolean aTR;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + this.zs.length + 8 + 1);
        byteBuffer.put((byte)this.zs.length);
        byteBuffer.put(this.zs);
        byteBuffer.putLong(this.sB);
        byteBuffer.put(this.aTR ? (byte)1 : 0);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 27527;
    }

    public void G(byte[] byArray) {
        this.zs = byArray;
    }

    public void am(long l2) {
        this.sB = l2;
    }

    public void bq(boolean bl2) {
        this.aTR = bl2;
    }
}

