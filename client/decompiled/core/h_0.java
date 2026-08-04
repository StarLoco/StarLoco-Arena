/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from H
 */
public class h_0
extends ael_2 {
    private long aL;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aL = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 556;
    }

    public long ai() {
        return this.aL;
    }
}

