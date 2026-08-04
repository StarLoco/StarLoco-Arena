/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class DQ
extends ael_2 {
    private byte aV;
    private long aOp;
    private long aOq;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        this.aOp = byteBuffer.getLong();
        this.aOq = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 6002;
    }

    public byte an() {
        return this.aV;
    }

    public long Mn() {
        return this.aOp;
    }

    public long Mo() {
        return this.aOq;
    }

    public void bz(long l2) {
        this.aOq = l2;
    }
}

