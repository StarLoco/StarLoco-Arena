/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from ir
 */
public class ir_0
extends so_0 {
    private String m_name;
    private long yk;
    private long yl;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.m_name);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length + 8 + 8);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        byteBuffer.putLong(this.yk);
        byteBuffer.putLong(this.yl);
        return this.a((byte)2, byteBuffer.array());
    }

    public int getId() {
        return 6024;
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
}

