/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Ck
 */
public class ck_2
extends so_0 {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] encode() {
        int n2;
        int n3;
        int n4;
        afl_1 afl_12;
        afl_1 afl_13 = afl_12 = afl_1.aRK();
        synchronized (afl_13) {
            n4 = afl_12.aRN();
            n3 = afl_12.aRO();
            n2 = afl_12.aRP();
        }
        int n5 = 12;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n5);
        byteBuffer.putInt(n4);
        byteBuffer.putInt(n3);
        byteBuffer.putInt(n2);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 27514;
    }
}

