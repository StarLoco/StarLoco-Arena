/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from WT
 */
public class wt_2
extends ael_2 {
    private byte bWc;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bWc = byteBuffer.get();
        return true;
    }

    public int getId() {
        return 15005;
    }

    public byte ajK() {
        return this.bWc;
    }
}

