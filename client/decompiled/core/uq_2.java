/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from uq
 */
public class uq_2
extends so_0 {
    public static final byte aqn = 0;
    public static final byte aqo = 1;
    public boolean jr = true;
    private byte js;
    private long Pk;
    private String aqp;
    private long aqq;

    public byte[] encode() {
        ByteBuffer byteBuffer;
        if (this.aqp != null) {
            byte[] byArray = aey_0.hH(this.aqp);
            byteBuffer = ByteBuffer.allocate(3 + byArray.length + 8);
            byteBuffer.put(this.js);
            byteBuffer.put((byte)0);
            byteBuffer.put((byte)byArray.length);
            byteBuffer.put(byArray);
            byteBuffer.putLong(this.Pk);
        } else {
            byteBuffer = ByteBuffer.allocate(18);
            byteBuffer.put(this.js);
            byteBuffer.put((byte)1);
            byteBuffer.putLong(this.aqq);
            byteBuffer.putLong(this.Pk);
        }
        if (this.jr) {
            return this.a((byte)8, byteBuffer.array());
        }
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 501;
    }

    public void l(byte by) {
        this.js = by;
    }

    public void cw(String string) {
        this.aqp = string;
        this.aqq = -1L;
    }

    public void aN(long l2) {
        this.aqq = l2;
        this.aqp = null;
    }

    public void u(boolean bl2) {
        this.jr = bl2;
    }

    public void as(long l2) {
        this.Pk = l2;
    }
}

