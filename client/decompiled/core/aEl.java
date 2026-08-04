/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aEl
extends so_0 {
    private int[] dzH;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(56);
        if (this.dzH != null) {
            for (int j = 0; j < this.dzH.length; ++j) {
                byteBuffer.putInt(this.dzH[j]);
            }
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5201;
    }

    public void I(int[] nArray) {
        this.dzH = nArray;
    }
}

