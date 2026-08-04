/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class atM
extends so_0 {
    private byte js;
    private String jv;

    public atM(byte by, String string) {
        this.js = by;
        this.jv = string;
    }

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.jv);
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 + byArray.length);
        byteBuffer.put(this.js);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)3, byteBuffer.array());
    }

    public byte rp() {
        return this.js;
    }

    public String CL() {
        return this.jv;
    }

    public int getId() {
        return 509;
    }
}

