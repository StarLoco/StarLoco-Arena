/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ow
 */
public class ow_2
extends so_0 {
    private short fA;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] encode() {
        int n2;
        afl_1 afl_12;
        afl_1 afl_13 = afl_12 = afl_1.aRK();
        synchronized (afl_13) {
            n2 = afl_12.akP();
        }
        int n3 = 6;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putShort(this.fA);
        byteBuffer.putInt(n2);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 27512;
    }

    public void M(short s) {
        this.fA = s;
    }
}

