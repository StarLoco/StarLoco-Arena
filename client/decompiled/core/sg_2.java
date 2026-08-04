/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from SG
 */
public class sg_2
extends so_0 {
    private long aj;
    private int bnc;
    private int bLA;
    private int bLB;
    private short bLC;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(22);
        byteBuffer.putLong(this.aj);
        byteBuffer.putInt(this.bnc);
        byteBuffer.putInt(this.bLA);
        byteBuffer.putInt(this.bLB);
        byteBuffer.putShort(this.bLC);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 8107;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void gy(int n2) {
        this.bnc = n2;
    }

    public void t(int n2, int n3, short s) {
        this.bLA = n2;
        this.bLB = n3;
        this.bLC = s;
    }
}

