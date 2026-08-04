/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aNb
extends so_0 {
    private et_2 dYV;
    private boolean czC;
    private short fO;

    public byte[] encode() {
        byte[] byArray = ug_2.EMPTY_BYTE_ARRAY;
        if (this.dYV != null) {
            byArray = this.dYV.cd();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(3 + byArray.length + 2);
        byteBuffer.put((byte)(this.czC ? 1 : 0));
        byteBuffer.putShort(this.fO);
        byteBuffer.putShort((short)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 6001;
    }

    public void h(et_2 et_22) {
        this.dYV = et_22;
    }

    public void fn(boolean bl2) {
        this.czC = bl2;
    }

    public void e(short s) {
        this.fO = s;
    }
}

