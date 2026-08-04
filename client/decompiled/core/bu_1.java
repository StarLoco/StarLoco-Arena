/*
 * Decompiled with CFR 0.152.
 */
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/*
 * Renamed from BU
 */
public class bu_1
extends so_0 {
    private String GS = "";
    private String GT = "";

    public byte[] encode() {
        byte[] byArray = new byte[]{};
        try {
            byArray = this.GS.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            a.error((Object)"Encodage du login impossible :", (Throwable)unsupportedEncodingException);
        }
        byte by = (byte)byArray.length;
        byte[] byArray2 = this.GT.getBytes();
        byte by2 = (byte)byArray2.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(by + by2 + 2);
        byteBuffer.put(by);
        byteBuffer.put(byArray);
        byteBuffer.put(by2);
        byteBuffer.put(byArray2);
        return this.a((byte)1, byteBuffer.array());
    }

    public int getId() {
        return 1025;
    }

    public void aQ(String string) {
        this.GS = string;
    }

    public void setPassword(String string) {
        this.GT = string;
    }
}

