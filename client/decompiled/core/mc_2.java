/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from mC
 */
public class mc_2
extends so_0 {
    private long aj;
    private int ir;
    private int Lj;
    private int Lk;
    private short Ll;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(22);
        byteBuffer.putLong(this.aj);
        byteBuffer.putInt(this.ir);
        byteBuffer.putInt(this.Lj);
        byteBuffer.putInt(this.Lk);
        byteBuffer.putShort(this.Ll);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 8109;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void I(int n2) {
        this.ir = n2;
    }

    public void h(int n2, int n3, short s) {
        this.Lj = n2;
        this.Lk = n3;
        this.Ll = s;
    }
}

