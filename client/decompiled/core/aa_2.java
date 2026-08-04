/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aA
 */
public class aa_2
extends so_0 {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] encode() {
        int n2;
        afl_1 afl_12;
        afl_1 afl_13 = afl_12 = afl_1.aRK();
        synchronized (afl_13) {
            n2 = afl_12.aRL();
        }
        int n3 = 4;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putInt(n2);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 27508;
    }
}

