/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aaz
 */
public class aaz_1
extends ael_2 {
    private long mw;
    private byte mx;
    private wy_2 boK;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.mw = byteBuffer.getLong();
        this.mx = byteBuffer.get();
        this.boK = new wy_2();
        this.boK.b(byteBuffer);
        this.boK.q(byteBuffer.getShort());
        return true;
    }

    public int getId() {
        return 5112;
    }

    public long fX() {
        return this.mw;
    }

    public wy_2 apc() {
        return this.boK;
    }

    public byte fY() {
        return this.mx;
    }
}

