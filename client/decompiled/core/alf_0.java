/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from alF
 */
public class alf_0
extends so_0 {
    private long lc = 0L;
    private int bhZ = 0;
    private String aiK = "";

    public byte[] encode() {
        byte[] byArray;
        try {
            byArray = this.aiK.getBytes("UTF-8");
        }
        catch (Exception exception) {
            byArray = ug_2.EMPTY_BYTE_ARRAY;
        }
        int n2 = byArray.length;
        int n3 = 16 + n2 * 8 / 8;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        byteBuffer.putLong(this.lc);
        byteBuffer.putInt(this.bhZ);
        byteBuffer.putInt(n2);
        byteBuffer.put(byArray);
        return this.a((byte)2, byteBuffer.array());
    }

    public void ad(long l2) {
        this.lc = l2;
    }

    public void lr(int n2) {
        this.bhZ = n2;
    }

    public void iE(String string) {
        this.aiK = string;
    }

    public int getId() {
        return 28649;
    }
}

