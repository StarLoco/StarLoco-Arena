/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from rZ
 */
public class rz_2
extends ael_2 {
    private final cp_2 aiM = new cp_2();

    public cp_2 xY() {
        return this.aiM;
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        int n2 = byteBuffer.getShort();
        for (int j = 0; j < n2; ++j) {
            long l2 = byteBuffer.getLong();
            short s = byteBuffer.getShort();
            byte[] byArray2 = new byte[s];
            byteBuffer.get(byArray2);
            this.aiM.a(l2, byArray2);
        }
        return true;
    }

    public int getId() {
        return 200;
    }
}

