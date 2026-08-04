/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from dL
 */
public class dl_0
extends ael_2 {
    private long mw;
    private byte mx;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.mw = byteBuffer.getLong();
        this.mx = byteBuffer.get();
        return true;
    }

    public int getId() {
        return 5116;
    }

    public long fX() {
        return this.mw;
    }

    public byte fY() {
        return this.mx;
    }
}

