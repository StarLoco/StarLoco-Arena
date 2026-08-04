/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from lR
 */
public class lr_2
extends so_0 {
    private long aj;
    private qc_0 Iq;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(9);
        byteBuffer.putLong(this.aj);
        byteBuffer.put((byte)this.Iq.getIndex());
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 4521;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void a(qc_0 qc_02) {
        this.Iq = qc_02;
    }
}

