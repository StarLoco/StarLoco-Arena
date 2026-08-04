/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from YZ
 */
public class yz_2
extends ael_2 {
    private byte[] cbX;
    private long sB;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.sB = byteBuffer.getLong();
        this.cbX = new byte[byteBuffer.getShort()];
        byteBuffer.get(this.cbX);
        return true;
    }

    public int getId() {
        return 5202;
    }

    public byte[] ane() {
        return this.cbX;
    }

    public long mb() {
        return this.sB;
    }
}

