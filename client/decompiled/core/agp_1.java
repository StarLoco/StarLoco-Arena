/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aGp
 */
public class agp_1
extends so_0 {
    private short fA;
    private short fO;
    private qa_2 ann;

    public byte[] encode() {
        int n2 = this.ann == null ? 0 : this.ann.size();
        int n3 = 8 + 8 * n2;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putShort(this.fA);
        byteBuffer.putShort(this.fO);
        byteBuffer.putInt(n2);
        for (int j = n2 - 1; 0 <= j; --j) {
            byteBuffer.putLong(this.ann.get(j));
        }
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 2301;
    }

    public void e(short s) {
        this.fO = s;
    }

    public void a(qa_2 qa_22) {
        this.ann = qa_22;
    }

    public void M(short s) {
        this.fA = s;
    }
}

