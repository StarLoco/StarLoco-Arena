/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public final class mD
extends ael_2 {
    private byte js;
    private int Lm;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.js = byteBuffer.get();
        this.Lm = byteBuffer.getInt();
        return true;
    }

    public byte rp() {
        return this.js;
    }

    public int getResult() {
        return this.Lm;
    }

    public int getId() {
        return 504;
    }
}

