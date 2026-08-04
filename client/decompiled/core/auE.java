/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class auE
extends ael_2 {
    private long lc;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.lc = byteBuffer.getLong();
        return true;
    }

    public long fx() {
        return this.lc;
    }

    public int getId() {
        return 28604;
    }
}

