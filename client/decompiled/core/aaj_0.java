/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aaJ
 */
public class aaj_0
extends ael_2 {
    private long lc;
    private long cgB;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.lc = byteBuffer.getLong();
        this.cgB = byteBuffer.getLong();
        return true;
    }

    public long fx() {
        return this.lc;
    }

    public long apr() {
        return this.cgB;
    }

    public int getId() {
        return 28644;
    }
}

