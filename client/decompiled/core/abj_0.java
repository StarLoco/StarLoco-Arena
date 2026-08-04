/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aBJ
 */
public class abj_0
extends ael_2 {
    protected byte bLb;
    protected int cSq;
    protected long dsu;
    protected long dsv;
    protected long dsw;
    protected long dsx;

    public byte aFH() {
        return this.bLb;
    }

    public int getKey() {
        return this.cSq;
    }

    public long aNE() {
        return this.dsu;
    }

    public long aNF() {
        return this.dsv;
    }

    public long aNG() {
        return this.dsw;
    }

    public long aNH() {
        return this.dsx;
    }

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 29, true)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bLb = byteBuffer.get();
        this.cSq = byteBuffer.getInt();
        this.dsu = byteBuffer.getLong();
        this.dsv = byteBuffer.getLong();
        this.dsw = byteBuffer.getLong();
        this.dsx = System.nanoTime();
        return true;
    }

    public int getId() {
        return 108;
    }
}

