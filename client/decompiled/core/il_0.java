/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Il
 */
public class il_0
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
            long l3 = byteBuffer.getLong();
            short s = byteBuffer.getShort();
            byte[] byArray2 = new byte[s];
            byteBuffer.get(byArray2);
            long l4 = byteBuffer.getLong();
            this.aiM.a(l2, new ahk_0(l3, byArray2, l4));
        }
        return true;
    }

    public int getId() {
        return 204;
    }
}

