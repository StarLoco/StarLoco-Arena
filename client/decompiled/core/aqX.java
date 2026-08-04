/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aqX
extends ael_2 {
    private byte cPb;
    private long mw;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.cPb = byteBuffer.get();
        this.mw = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 5114;
    }

    public long fX() {
        return this.mw;
    }

    public byte aEh() {
        return this.cPb;
    }
}

