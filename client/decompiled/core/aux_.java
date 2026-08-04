/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aux
 */
public class aux_
extends so_0 {
    private long BK;
    private String bY;

    public byte[] encode() {
        byte[] byArray = new byte[]{};
        try {
            byArray = aey_0.hH(this.bY);
        }
        catch (Exception exception) {
            byArray = this.bY.getBytes();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(10 + byArray.length);
        byteBuffer.putLong(this.BK);
        byteBuffer.putShort((short)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 3161;
    }

    public void dS(long l2) {
        this.BK = l2;
    }

    public void k(String string) {
        this.bY = string;
    }
}

