/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from JT
 */
public class jt_2
extends ael_2 {
    private final cp_2 bmM = new cp_2();
    private long bmN;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bmM.clear();
        this.bmN = byteBuffer.getLong();
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            long l2 = byteBuffer.getLong();
            byte[] byArray2 = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray2);
            this.bmM.a(l2, byArray2);
        }
        return true;
    }

    public int getId() {
        return 6006;
    }

    public cp_2 Wl() {
        return this.bmM;
    }

    public long Wm() {
        return this.bmN;
    }
}

