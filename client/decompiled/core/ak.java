/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class ak
extends so_0 {
    private String bY;
    private long bZ;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.bY);
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 + byArray.length + 8);
        byteBuffer.putShort((short)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putLong(this.bZ);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 3199;
    }

    public void k(String string) {
        this.bY = string;
    }

    public void g(long l2) {
        this.bZ = l2;
    }
}

