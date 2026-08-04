/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from SH
 */
public class sh_0
extends so_0 {
    protected long Pk;
    protected long Pl;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(this.Pk);
        byteBuffer.putLong(this.Pl);
        return this.a((byte)8, byteBuffer.array());
    }

    public final int getId() {
        return 515;
    }

    public void as(long l2) {
        this.Pk = l2;
    }

    public void at(long l2) {
        this.Pl = l2;
    }
}

