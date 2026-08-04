/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from mK
 */
public class mk_2
extends bb_2 {
    public static final String GX = "Teams";
    private et_2[] Lq;

    public mk_2(String string) {
        super((byte)0, GX, string);
    }

    public et_2[] rt() {
        return this.Lq;
    }

    public void a(et_2[] et_2Array) {
        this.Lq = et_2Array;
    }

    public byte[] cd() {
        int n2 = 0;
        for (int j = 0; j < this.Lq.length; ++j) {
            n2 += this.Lq[j].nj();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + 2 * this.Lq.length + n2);
        byteBuffer.put((byte)this.Lq.length);
        for (int j = 0; j < this.Lq.length; ++j) {
            byteBuffer.putShort((short)this.Lq[j].nj());
            byteBuffer.put(this.Lq[j].cd());
        }
        return byteBuffer.array();
    }

    public void b(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.Lq = new et_2[byteBuffer.get()];
        for (int j = 0; j < this.Lq.length; ++j) {
            byte[] byArray2 = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray2);
            this.Lq[j] = et_2.a(byArray2, false);
        }
    }
}

