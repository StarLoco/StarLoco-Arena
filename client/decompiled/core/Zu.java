/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Zu
extends so_0 {
    Object[] cdf;
    short atZ;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(6 + this.cdf.length * 6);
        byteBuffer.putShort(this.atZ);
        byteBuffer.putShort((short)this.cdf.length);
        for (int j = 0; j < this.cdf.length; ++j) {
            byteBuffer.putInt(((wy_2)this.cdf[j]).jf());
            byteBuffer.putShort(((wy_2)this.cdf[j]).hG());
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5470;
    }

    public void f(Object[] objectArray) {
        this.cdf = objectArray;
    }

    public void bu(short s) {
        this.atZ = s;
    }
}

