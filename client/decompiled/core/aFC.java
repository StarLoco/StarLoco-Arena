/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aFC
extends so_0 {
    private String arg = null;
    private byte bLb;

    public byte[] encode() {
        byte[] byArray = this.arg.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(byArray.length + 2);
        byteBuffer.putShort((short)byArray.length);
        byteBuffer.put(byArray);
        return this.a(this.bLb, byteBuffer.array());
    }

    public int getId() {
        return 101;
    }

    public void setCommand(String string) {
        this.arg = string;
    }

    public void bn(byte by) {
        this.bLb = by;
    }
}

