/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from qK
 */
public class qk_2
extends so_0 {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] encode() {
        short s;
        byte by;
        byte by2;
        int n2;
        int n3;
        int n4;
        afl_1 afl_12;
        afl_1 afl_13 = afl_12 = afl_1.aRK();
        synchronized (afl_13) {
            n4 = afl_12.ahz();
            n3 = afl_12.ahE();
            n2 = afl_12.ahJ();
            by2 = afl_12.ahs();
            by = afl_12.aht();
            s = afl_12.ahu();
        }
        int n5 = 12;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n5 += 4);
        byteBuffer.putInt(n4);
        byteBuffer.putInt(n3);
        byteBuffer.putInt(n2);
        byteBuffer.put(by2);
        byteBuffer.put(by);
        byteBuffer.putShort(s);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 27506;
    }
}

