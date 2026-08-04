/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from pU
 */
public class pu_1
extends ael_2 {
    private long ap;
    private boolean acR;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.ap = byteBuffer.getLong();
        this.acR = byteBuffer.get() == 1;
        return true;
    }

    public int getId() {
        return 26302;
    }

    public long Y() {
        return this.ap;
    }

    public boolean uK() {
        return this.acR;
    }
}

