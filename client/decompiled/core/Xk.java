/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Xk
extends so_0 {
    private String IC;
    private String bY;

    public byte[] encode() {
        byte[] byArray;
        byte[] byArray2 = aey_0.hH(this.IC);
        try {
            byArray = aey_0.hH(this.bY);
        }
        catch (Exception exception) {
            byArray = this.bY.getBytes();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray2.length + 1 + byArray.length);
        byteBuffer.put((byte)byArray2.length);
        byteBuffer.put(byArray2);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)4, byteBuffer.array());
    }

    public int getId() {
        return 3155;
    }

    public void setUserName(String string) {
        this.IC = string;
    }

    public void k(String string) {
        this.bY = string;
    }
}

