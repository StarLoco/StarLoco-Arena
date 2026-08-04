/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from df
 */
public class df_1
extends ael_2 {
    private long lc;
    private boolean ld;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.lc = byteBuffer.getLong();
        this.ld = byteBuffer.get() != 0;
        return true;
    }

    public long fx() {
        return this.lc;
    }

    public boolean fy() {
        return this.ld;
    }

    public int getId() {
        return 28648;
    }
}

