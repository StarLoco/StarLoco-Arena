/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Mo
 */
public class mo_2
extends so_0 {
    final jg_0 bts = new jg_0();
    int btt;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(6 + this.bts.size() * 4);
        byteBuffer.putInt(this.btt);
        byteBuffer.putShort((short)this.bts.size());
        int n2 = this.bts.size();
        for (int j = 0; j < n2; ++j) {
            byteBuffer.putInt(this.bts.get(j));
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5450;
    }

    public void gH(int n2) {
        this.bts.add(n2);
    }

    public void gI(int n2) {
        this.btt = n2;
    }
}

