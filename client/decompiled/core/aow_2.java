/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aow
 */
public class aow_2
extends so_0 {
    private long aj;
    private int cKY;
    private int cD;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.putLong(this.aj);
        byteBuffer.putInt(this.cKY);
        byteBuffer.putInt(this.cD);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 23009;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void lE(int n2) {
        this.cKY = n2;
    }

    public void i(int n2) {
        this.cD = n2;
    }
}

