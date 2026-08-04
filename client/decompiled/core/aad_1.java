/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aAd
 */
public class aad_1
extends so_0 {
    private long Ho;
    private short Gm;
    private short fA;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(12);
        byteBuffer.putLong(this.Ho);
        byteBuffer.putShort(this.Gm);
        byteBuffer.putShort(this.fA);
        return this.a((byte)2, byteBuffer.array());
    }

    public void aj(long l2) {
        this.Ho = l2;
    }

    public void C(short s) {
        this.Gm = s;
    }

    public void M(short s) {
        this.fA = s;
    }

    public int getId() {
        return 6023;
    }
}

