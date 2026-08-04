/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from jg
 */
public class jg_2
extends ael_2 {
    private byte[] zs;
    private byte zt;
    private byte zu;
    private byte zv;
    private byte[] zw;
    private long sB;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.zs = new byte[byteBuffer.get()];
        byteBuffer.get(this.zs);
        this.zt = byteBuffer.get();
        this.zu = byteBuffer.get();
        this.zv = byteBuffer.get();
        this.zw = new byte[byteBuffer.getShort()];
        byteBuffer.get(this.zw);
        this.sB = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 27526;
    }

    public byte[] lW() {
        return this.zs;
    }

    public byte lX() {
        return this.zt;
    }

    public byte lY() {
        return this.zu;
    }

    public byte lZ() {
        return this.zv;
    }

    public byte[] ma() {
        return this.zw;
    }

    public long mb() {
        return this.sB;
    }
}

