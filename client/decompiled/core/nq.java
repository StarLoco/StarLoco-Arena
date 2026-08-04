/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class nq
extends so_0 {
    private short Oo;
    private boolean Op;
    private short Oq;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.putShort(this.Oo);
        byteBuffer.put((byte)(this.Op ? 1 : 0));
        byteBuffer.putShort(this.Oq);
        return this.a((byte)2, byteBuffer.array());
    }

    public void K(short s) {
        this.Oo = s;
    }

    public void ab(boolean bl2) {
        this.Op = bl2;
    }

    public void L(short s) {
        this.Oq = s;
    }

    public int getId() {
        return 22003;
    }
}

