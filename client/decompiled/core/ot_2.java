/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from OT
 */
public class ot_2
extends so_0 {
    private long aj;
    private short fO;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.putLong(this.aj);
        byteBuffer.putShort(this.fO);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 6003;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void e(short s) {
        this.fO = s;
    }
}

