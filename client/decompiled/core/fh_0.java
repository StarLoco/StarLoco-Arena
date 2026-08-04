/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from FH
 */
public class fh_0
extends so_0 {
    private ArrayList aVl = null;

    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 + 8 * this.aVl.size());
        if (this.aVl != null) {
            byteBuffer.putShort((short)this.aVl.size());
            for (wy_2 wy_22 : this.aVl) {
                byteBuffer.putLong(wy_22.je());
            }
        } else {
            byteBuffer.putShort((short)0);
        }
        return this.a((byte)3, byteBuffer.array());
    }

    public int getId() {
        return 5203;
    }

    public void l(ArrayList arrayList) {
        this.aVl = arrayList;
    }
}

