/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from RR
 */
public class rr_1
extends so_0 {
    private String m_name;

    public byte[] encode() {
        byte[] byArray = aey_0.hH(this.m_name);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length);
        byteBuffer.put((byte)byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)2, byteBuffer.array());
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public int getId() {
        return 15506;
    }
}

