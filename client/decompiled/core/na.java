/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class na
extends so_0 {
    public byte[] encode() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + kS.BUILD_VERSION.length());
        byteBuffer.put((byte)2);
        byteBuffer.putShort((short)70);
        byteBuffer.put((byte)kS.BUILD_VERSION.length());
        byteBuffer.put(kS.BUILD_VERSION.getBytes());
        return this.a((byte)0, byteBuffer.array());
    }

    public int getId() {
        return 7;
    }
}

