/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Or
extends ael_2 {
    public static final byte bBS = 0;
    public static final byte bBT = 1;
    public static final byte bBU = 2;
    private byte bBV;
    private long mw;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bBV = byteBuffer.get();
        this.mw = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 5113;
    }

    public long fX() {
        return this.mw;
    }

    public byte abt() {
        return this.bBV;
    }
}

