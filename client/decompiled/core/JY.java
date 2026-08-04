/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class JY
extends so_0 {
    String bnb;
    int bnc;
    long axC;

    public byte[] encode() {
        byte[] byArray = ug_2.EMPTY_BYTE_ARRAY;
        try {
            byArray = this.bnb.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length + 4);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putInt(this.bnc);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 4701;
    }

    public void eW(String string) {
        this.bnb = string;
    }

    public void gy(int n2) {
        this.bnc = n2;
    }
}

