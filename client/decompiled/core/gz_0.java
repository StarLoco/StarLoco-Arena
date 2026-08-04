/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from gZ
 */
public class gz_0
extends ael_2 {
    private long ap;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.ap = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 26304;
    }

    public long Y() {
        return this.ap;
    }
}

