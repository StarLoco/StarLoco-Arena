/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from EQ
 */
public class eq_1
extends ael_2 {
    private byte[] zs;
    private boolean aTR;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte by = byteBuffer.get();
        if (by > 0) {
            this.zs = new byte[by];
            byteBuffer.get(this.zs);
            this.aTR = true;
        } else {
            this.aTR = false;
        }
        return true;
    }

    public int getId() {
        return 27528;
    }

    public byte[] lW() {
        return this.zs;
    }

    public boolean OB() {
        return this.aTR;
    }
}

