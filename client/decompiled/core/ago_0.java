/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aGO
 */
public class ago_0
extends so_0 {
    private long lc;
    private long Ho;
    private short Gm;
    private int Hp;

    public byte[] encode() {
        int n2 = 22;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putLong(this.lc);
        byteBuffer.putLong(this.Ho);
        byteBuffer.putShort(this.Gm);
        byteBuffer.putInt(this.Hp);
        return this.a((byte)2, byteBuffer.array());
    }

    public void ad(long l2) {
        this.lc = l2;
    }

    public void aj(long l2) {
        this.Ho = l2;
    }

    public void C(short s) {
        this.Gm = s;
    }

    public void kV(int n2) {
        this.Hp = n2;
    }

    public int getId() {
        return 28607;
    }
}

