/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from alV
 */
public class alv_1
extends so_0 {
    private int bbw;
    private short cFU;
    public static short cFV = (short)-1;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);
        byteBuffer.putInt(this.bbw);
        byteBuffer.putShort(this.cFU);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 26330;
    }

    public void fH(int n2) {
        this.bbw = n2;
    }

    public void bM(short s) {
        this.cFU = s;
    }
}

