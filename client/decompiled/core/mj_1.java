/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from mJ
 */
public class mj_1
extends ael_2 {
    private byte Lo;
    private aim_1 Lp = new aim_1();

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.Lo = byteBuffer.get();
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            this.Lp.c(byteBuffer.get(), byteBuffer.getInt());
        }
        return true;
    }

    public int getId() {
        return 5403;
    }

    public byte rr() {
        return this.Lo;
    }

    public aim_1 rs() {
        return this.Lp;
    }
}

