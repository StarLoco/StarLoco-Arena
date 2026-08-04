/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aid
 */
public class aid_1
extends so_0 {
    private short fA;
    private short atZ;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] encode() {
        int n2;
        afl_1 afl_12;
        afl_1 afl_13 = afl_12 = afl_1.aRK();
        synchronized (afl_13) {
            n2 = afl_12.aCe();
        }
        int n3 = 8;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putShort(this.atZ);
        byteBuffer.putShort(this.fA);
        byteBuffer.putInt(n2);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 27510;
    }

    public void M(short s) {
        this.fA = s;
    }

    public void bu(short s) {
        this.atZ = s;
    }
}

