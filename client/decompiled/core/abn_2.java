/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from abn
 */
public class abn_2
extends sh_0 {
    private short chU;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(18);
        byteBuffer.putLong(this.Pk);
        byteBuffer.putLong(this.Pl);
        byteBuffer.putShort(this.chU);
        return this.a((byte)2, byteBuffer.array());
    }

    public void bx(short s) {
        this.chU = s;
    }
}

