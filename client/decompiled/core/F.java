/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class F
extends so_0 {
    private ug ay;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.ay.nj());
        byteBuffer.put(this.ay.cd());
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 539;
    }

    public void a(ug ug2) {
        this.ay = ug2;
    }
}

