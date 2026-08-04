/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from dy
 */
public class dy_0
extends ael_2 {
    private long lc;
    private byte aV;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.lc = byteBuffer.getLong();
        this.aV = byteBuffer.get();
        return true;
    }

    public long fx() {
        return this.lc;
    }

    public byte an() {
        return this.aV;
    }

    public int getId() {
        return 28608;
    }
}

