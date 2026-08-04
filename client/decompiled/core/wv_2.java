/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from WV
 */
public class wv_2
extends ael_2 {
    private boolean bWd;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bWd = byteBuffer.get() != 0;
        return true;
    }

    public int getId() {
        return 2261;
    }

    public boolean Od() {
        return this.bWd;
    }
}

