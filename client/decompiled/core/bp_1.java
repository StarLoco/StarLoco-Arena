/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from bp
 */
public class bp_1
extends so_0 {
    private ee_2 bN;
    private short fO;

    public byte[] encode() {
        byte[] byArray = ug_2.EMPTY_BYTE_ARRAY;
        byte[] byArray2 = ug_2.EMPTY_BYTE_ARRAY;
        if (this.bN != null) {
            byArray = this.bN.Oh().cd();
            byArray2 = this.bN.Oi().cd();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(12 + byArray.length + 2 + byArray2.length);
        byteBuffer.putLong(this.bN.getId());
        byteBuffer.putShort(this.fO);
        byteBuffer.putShort((short)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putShort((short)byArray2.length);
        byteBuffer.put(byArray2);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 6011;
    }

    public void b(ee_2 ee_22) {
        this.bN = ee_22;
    }

    public void e(short s) {
        this.fO = s;
    }
}

