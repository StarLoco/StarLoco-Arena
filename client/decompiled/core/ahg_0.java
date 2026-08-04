/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aHG
 */
public class ahg_0
extends so_0 {
    private int[] GY;

    public byte[] encode() {
        int n2 = this.GY.length;
        int n3 = 4 + n2 * 32 / 8;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putInt(n2);
        for (int j = n2 - 1; 0 <= j; --j) {
            byteBuffer.putInt(this.GY[j]);
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5490;
    }

    public void e(int[] nArray) {
        this.GY = nArray;
    }
}

