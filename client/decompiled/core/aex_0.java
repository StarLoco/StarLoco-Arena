/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aeX
 */
public class aex_0
extends so_0 {
    private qa_2 ann;

    public void a(qa_2 qa_22) {
        this.ann = qa_22;
    }

    public byte[] encode() {
        int n2 = this.ann == null ? 0 : this.ann.size();
        int n3 = 4 + 8 * n2;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putInt(n2);
        for (int j = n2 - 1; 0 <= j; --j) {
            byteBuffer.putLong(this.ann.get(j));
        }
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 23116;
    }
}

