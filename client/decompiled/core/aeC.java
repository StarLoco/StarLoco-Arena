/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aeC
extends so_0 {
    private long lc;
    private byte ata;
    private byte coY;
    private long coZ;

    public byte[] encode() {
        int n2 = 18;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putLong(this.lc);
        byteBuffer.put(this.ata);
        byteBuffer.put(this.coY);
        byteBuffer.putLong(this.coZ);
        return this.a((byte)2, byteBuffer.array());
    }

    public void ad(long l2) {
        this.lc = l2;
    }

    public void ax(byte by) {
        this.ata = by;
    }

    public void ay(byte by) {
        this.coY = by;
    }

    public void dB(long l2) {
        this.coZ = l2;
    }

    public int getId() {
        return 28635;
    }
}

