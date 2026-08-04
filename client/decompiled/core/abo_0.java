/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aBO
 */
public class abo_0
extends so_0 {
    private long bZ;
    private int bzu;
    private String m_name;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.m_name);
        ByteBuffer byteBuffer = ByteBuffer.allocate(13 + byArray.length);
        byteBuffer.putLong(this.bZ);
        byteBuffer.putInt(this.bzu);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 553;
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
}

