/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aFJ
 */
public class afj_2
extends ael_2 {
    private long sB;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.sB = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 15507;
    }

    public long mb() {
        return this.sB;
    }
}

