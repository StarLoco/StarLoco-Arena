/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aqH
extends so_0 {
    private sw_1 cOw;

    public byte[] encode() {
        byte[] byArray = ug_2.EMPTY_BYTE_ARRAY;
        if (this.cOw != null) {
            byArray = this.cOw.cd();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(byArray.length + 1);
        byteBuffer.put(byArray);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 6021;
    }

    public void c(sw_1 sw_12) {
        this.cOw = sw_12;
    }
}

