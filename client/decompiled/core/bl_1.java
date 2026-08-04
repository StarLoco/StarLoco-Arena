/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Bl
 */
public class bl_1
extends so_0 {
    private long ap;
    private short Gm;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.putLong(this.ap);
        byteBuffer.putShort(this.Gm);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 26303;
    }

    public void d(long l2) {
        this.ap = l2;
    }

    public void C(short s) {
        this.Gm = s;
    }
}

