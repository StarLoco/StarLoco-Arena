/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class acS
extends so_0 {
    private String jg;
    private String bY;

    public byte[] encode() {
        byte[] byArray;
        byte[] byArray2 = aey_0.hH(this.jg);
        try {
            byArray = aey_0.hH(this.bY);
        }
        catch (Exception exception) {
            byArray = this.bY.getBytes();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length + 1 + byArray2.length);
        byteBuffer.put((byte)byArray2.length);
        byteBuffer.put(byArray2);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)4, byteBuffer.array());
    }

    public int getId() {
        return 3151;
    }

    public void hv(String string) {
        this.jg = string;
    }

    public void k(String string) {
        this.bY = string;
    }
}

