/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aOo
extends so_0 {
    final jg_0 bts = new jg_0();
    Object[] cdf;
    int btt;

    public byte[] encode() {
        int n2;
        ByteBuffer byteBuffer = ByteBuffer.allocate(6 + this.bts.size() * 4 + 2 + this.cdf.length * 6);
        byteBuffer.putInt(this.btt);
        byteBuffer.putShort((short)this.bts.size());
        int n3 = this.bts.size();
        for (n2 = 0; n2 < n3; ++n2) {
            byteBuffer.putInt(this.bts.get(n2));
        }
        byteBuffer.putShort((short)this.cdf.length);
        for (n2 = 0; n2 < this.cdf.length; ++n2) {
            byteBuffer.putInt(((wy_2)this.cdf[n2]).jf());
            byteBuffer.putShort(((wy_2)this.cdf[n2]).hG());
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5400;
    }

    public void gH(int n2) {
        this.bts.add(n2);
    }

    public void f(Object[] objectArray) {
        this.cdf = objectArray;
    }

    public void gI(int n2) {
        this.btt = n2;
    }
}

