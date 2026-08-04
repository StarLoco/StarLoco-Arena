/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class QZ
extends so_0 {
    private String abZ;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.abZ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)4, byteBuffer.array());
    }

    public int getId() {
        return 3129;
    }

    public void fK(String string) {
        this.abZ = string;
    }
}

