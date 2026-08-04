/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from wt
 */
public class wt_1
extends so_0 {
    private long Pk;
    private String jv;

    public wt_1(long l2, String string) {
        this.Pk = l2;
        this.jv = string;
    }

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.jv);
        ByteBuffer byteBuffer = ByteBuffer.allocate(9 + byArray.length);
        byteBuffer.putLong(this.Pk);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)2, byteBuffer.array());
    }

    public long CK() {
        return this.Pk;
    }

    public String CL() {
        return this.jv;
    }

    public int getId() {
        return 513;
    }
}

