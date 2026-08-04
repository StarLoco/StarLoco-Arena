/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aNq
extends ael_2 {
    private long lc;
    private long coZ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.lc = byteBuffer.getLong();
        this.coZ = byteBuffer.getLong();
        return true;
    }

    public long fx() {
        return this.lc;
    }

    public long aXu() {
        return this.coZ;
    }

    public int getId() {
        return 28646;
    }
}

