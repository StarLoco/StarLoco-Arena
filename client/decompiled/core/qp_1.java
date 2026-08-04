/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Qp
 */
public class qp_1
extends so_0 {
    private long aj;
    private short bGe;
    private short bGf;
    private long sB;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putLong(this.aj);
        byteBuffer.putShort(this.bGe);
        byteBuffer.putShort(this.bGf);
        byteBuffer.putLong(this.sB);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 6013;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public void aS(short s) {
        this.bGe = s;
    }

    public void aT(short s) {
        this.bGf = s;
    }

    public void am(long l2) {
        this.sB = l2;
    }
}

