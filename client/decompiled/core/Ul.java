/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Ul
extends ael_2 {
    private byte bPI;
    private long mw;
    private long bPJ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bPI = byteBuffer.get();
        this.mw = byteBuffer.getLong();
        this.bPJ = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 5104;
    }

    public long fX() {
        return this.mw;
    }

    public long agH() {
        return this.bPJ;
    }

    public byte agI() {
        return this.bPI;
    }
}

