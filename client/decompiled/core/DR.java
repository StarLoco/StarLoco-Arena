/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class DR
extends ael_2 {
    private long lc;
    private short Gm;
    private boolean jy;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.lc = byteBuffer.getLong();
        this.Gm = byteBuffer.getShort();
        this.jy = byteBuffer.get() != 0;
        return true;
    }

    public long fx() {
        return this.lc;
    }

    public short qY() {
        return this.Gm;
    }

    public boolean eY() {
        return this.jy;
    }

    public int getId() {
        return 28612;
    }
}

