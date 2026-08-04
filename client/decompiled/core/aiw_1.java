/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aIw
 */
public class aiw_1
extends so_0 {
    private boolean jy;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.put((byte)(this.jy ? 1 : 0));
        return this.a((byte)2, byteBuffer.array());
    }

    public void cb(boolean bl2) {
        this.jy = bl2;
    }

    public int getId() {
        return 26334;
    }
}

