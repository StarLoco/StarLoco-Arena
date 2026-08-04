/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class ayQ
extends so_0 {
    public static final String NO_NAME = "-";
    private short ash;
    private String m_name;

    public byte[] encode() {
        byte[] byArray;
        try {
            byArray = this.m_name.getBytes("UTF-8");
        }
        catch (Exception exception) {
            try {
                byArray = NO_NAME.getBytes("UTF-8");
            }
            catch (Exception exception2) {
                byArray = ug_2.EMPTY_BYTE_ARRAY;
            }
        }
        int n2 = 6 + 1 * byArray.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putShort(this.ash);
        byteBuffer.putInt(byArray.length);
        byteBuffer.put(byArray);
        return this.a((byte)2, byteBuffer.array());
    }

    public void ca(short s) {
        this.ash = s;
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public int getId() {
        return 28603;
    }
}

