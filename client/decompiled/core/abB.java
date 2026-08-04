/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class abB
extends so_0 {
    private boolean cit;
    private String m_name;
    private long yk;
    private long yl;
    private short fA = 0;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.m_name);
        ByteBuffer byteBuffer = ByteBuffer.allocate(2 + byArray.length + 8 + 8 + 2);
        byteBuffer.put(this.cit ? (byte)1 : 0);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putLong(this.yk);
        byteBuffer.putLong(this.yl);
        byteBuffer.putShort(this.fA);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 6026;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void T(long l2) {
        this.yk = l2;
    }

    public void U(long l2) {
        this.yl = l2;
    }

    public void M(short s) {
        this.fA = s;
    }

    public void cV(boolean bl2) {
        this.cit = bl2;
    }
}

