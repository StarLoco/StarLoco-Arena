/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Nr
extends so_0 {
    private long bZ;
    private short bny;
    private short bzt;
    private int bzu;
    private String m_name;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.m_name);
        ByteBuffer byteBuffer = ByteBuffer.allocate(17 + byArray.length);
        byteBuffer.putLong(this.bZ);
        byteBuffer.putInt(this.bzu);
        byteBuffer.putShort(this.bny);
        byteBuffer.putShort(this.bzt);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 555;
    }

    public void g(long l2) {
        this.bZ = l2;
    }

    public void gV(int n2) {
        this.bzu = n2;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public void aH(short s) {
        this.bny = s;
    }

    public void aM(short s) {
        this.bzt = s;
    }
}

