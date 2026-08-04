/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aDs
 */
public class ads_0
extends so_0 {
    private long[] dxo;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + this.dxo.length * 8);
        byteBuffer.put((byte)this.dxo.length);
        for (long l2 : this.dxo) {
            byteBuffer.putLong(l2);
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 15004;
    }

    public void l(long[] lArray) {
        this.dxo = lArray;
    }
}

