/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from DG
 */
public class dg_0
extends ael_2 {
    private long lc;
    private boolean aOd;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.lc = byteBuffer.getLong();
        this.aOd = byteBuffer.get() != 0;
        return true;
    }

    public long fx() {
        return this.lc;
    }

    public boolean Mh() {
        return this.aOd;
    }

    public int getId() {
        return 28630;
    }
}

