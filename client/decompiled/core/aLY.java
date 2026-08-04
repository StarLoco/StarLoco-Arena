/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aLY
extends so_0 {
    private arh_0 IH;

    public byte[] encode() {
        int n2 = this.IH.aEF();
        int n3 = n2 * 10;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n3);
        for (int j = 0; j < n2; ++j) {
            int[] nArray = this.IH.lU(j);
            byteBuffer.putInt(nArray[0]);
            byteBuffer.putInt(nArray[1]);
            byteBuffer.putShort((short)nArray[2]);
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 4501;
    }

    public void a(arh_0 arh_02) {
        this.IH = arh_02;
    }
}

