/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from bk
 */
public class bk_1
extends ael_2 {
    private long fz;
    private short fA;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.fz = byteBuffer.getLong();
        this.fA = byteBuffer.getShort();
        return true;
    }

    public int getId() {
        return 2300;
    }

    public long cA() {
        return this.fz;
    }

    public short cB() {
        return this.fA;
    }
}

