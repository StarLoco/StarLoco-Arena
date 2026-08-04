/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aFQ
 */
public class afq_0
extends so_0 {
    private String bY;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.bY);
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 + byArray.length);
        byteBuffer.putShort((short)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 3159;
    }

    public void k(String string) {
        this.bY = string;
    }
}

