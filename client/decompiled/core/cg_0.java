/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from cG
 */
public class cg_0
extends so_0 {
    public boolean jr = true;
    private byte js;
    private boolean jt;
    private String ju;
    private String jv;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.ju);
        byte[] byArray2 = aey_0.hH(this.jv);
        int n2 = 3 + byArray.length + 1 + byArray2.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.put(this.js);
        byteBuffer.put((byte)(this.jt ? 1 : 0));
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.put((byte)byArray2.length);
        byteBuffer.put(byArray2);
        if (this.jr) {
            return this.a((byte)8, byteBuffer.array());
        }
        return this.a((byte)2, byteBuffer.array());
    }

    public final int getId() {
        return 503;
    }

    public void l(byte by) {
        this.js = by;
    }

    public void t(boolean bl2) {
        this.jt = bl2;
    }

    public void B(String string) {
        this.ju = string;
    }

    public void C(String string) {
        this.jv = string;
    }

    public void u(boolean bl2) {
        this.jr = bl2;
    }
}

