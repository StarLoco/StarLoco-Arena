/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aiO
 */
public class aio_0
extends ael_2 {
    private long sB;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.sB = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 8012;
    }

    public long mb() {
        return this.sB;
    }
}

