/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public final class nP
extends so_0 {
    public boolean jr = true;
    private long Pk;
    private long Pl;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(this.Pk);
        byteBuffer.putLong(this.Pl);
        if (this.jr) {
            return this.a((byte)8, byteBuffer.array());
        }
        return this.a((byte)2, byteBuffer.array());
    }

    public final int getId() {
        return 505;
    }

    public void as(long l2) {
        this.Pk = l2;
    }

    public void at(long l2) {
        this.Pl = l2;
    }

    public void u(boolean bl2) {
        this.jr = bl2;
    }
}

