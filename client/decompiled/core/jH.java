/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class jH
extends ael_2 {
    private String BJ;
    private long BK;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.BJ = aey_0.V(byArray2);
        this.BK = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 3164;
    }

    public String nn() {
        return this.BJ;
    }

    public long no() {
        return this.BK;
    }
}

