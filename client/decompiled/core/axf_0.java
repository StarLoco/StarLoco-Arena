/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from axF
 */
public class axf_0
extends so_0 {
    private int cD;
    private int Hq;
    private int Hr;
    private long Ht;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.putInt(this.cD);
        byteBuffer.putInt(this.Hq);
        byteBuffer.putInt(this.Hr);
        byteBuffer.putLong(this.Ht);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 22095;
    }

    public void i(int n2) {
        this.cD = n2;
    }

    public void li(int n2) {
        this.Hq = n2;
    }

    public void lj(int n2) {
        this.Hr = n2;
    }

    public void bO(long l2) {
        this.Ht = l2;
    }
}

