/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Pg
extends so_0 {
    private long anl;
    private short aKn;
    private qa_2 ann;
    private boolean jy;

    public byte[] encode() {
        int n2 = this.ann == null ? 0 : this.ann.size();
        int n3 = 14 + 8 * n2 + 1;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putLong(this.anl);
        byteBuffer.putShort(this.aKn);
        byteBuffer.putInt(n2);
        for (int j = n2 - 1; 0 <= j; --j) {
            byteBuffer.putLong(this.ann.get(j));
        }
        byteBuffer.put((byte)(this.jy ? 1 : 0));
        return this.a((byte)2, byteBuffer.array());
    }

    public void cl(long l2) {
        this.anl = l2;
    }

    public void aQ(short s) {
        this.aKn = s;
    }

    public void a(qa_2 qa_22) {
        this.ann = qa_22;
    }

    public void cb(boolean bl2) {
        this.jy = bl2;
    }

    public int getId() {
        return 2308;
    }
}

