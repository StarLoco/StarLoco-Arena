/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from WC
 */
public class wc_2
extends ael_2 {
    private long sB;
    private boolean bUG;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.sB = byteBuffer.getLong();
        this.bUG = byteBuffer.get() == 1;
        return true;
    }

    public int getId() {
        return 8250;
    }

    public long mb() {
        return this.sB;
    }

    public boolean ajh() {
        return this.bUG;
    }
}

