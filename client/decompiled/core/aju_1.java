/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aJu
 */
public class aju_1
extends so_0 {
    private long yl;
    private qa_2 dRv = new qa_2();
    private byte dRw;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10 + 8 * this.dRv.size());
        byteBuffer.put(this.dRw);
        byteBuffer.putLong(this.yl);
        byteBuffer.put((byte)this.dRv.size());
        for (int j = 0; j < this.dRv.size(); ++j) {
            byteBuffer.putLong(this.dRv.get(j));
        }
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 26313;
    }

    public void b(qa_2 qa_22) {
        this.dRv = qa_22;
    }

    public void eD(long l2) {
        this.dRv.ct(l2);
    }

    public void U(long l2) {
        this.yl = l2;
    }

    public void bq(byte by) {
        this.dRw = by;
    }
}

