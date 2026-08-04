/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aBN
 */
public class abn_0
extends so_0 {
    private long Ho;
    private short Gm;

    public byte[] encode() {
        int n2 = 10;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putLong(this.Ho);
        byteBuffer.putShort(this.Gm);
        return this.a((byte)2, byteBuffer.array());
    }

    public void aj(long l2) {
        this.Ho = l2;
    }

    public void C(short s) {
        this.Gm = s;
    }

    public int getId() {
        return 23001;
    }
}

