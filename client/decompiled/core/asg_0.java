/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from asG
 */
public class asg_0
extends so_0 {
    protected byte bLb;
    protected int cSq;

    public byte aFH() {
        return this.bLb;
    }

    public asg_0(byte by, int n2) {
        this.bLb = by;
        this.cSq = n2;
    }

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(13);
        byteBuffer.put(this.bLb);
        byteBuffer.putInt(this.cSq);
        byteBuffer.putLong(System.nanoTime());
        return this.a(this.bLb, byteBuffer.array());
    }

    public int getId() {
        return 107;
    }
}

