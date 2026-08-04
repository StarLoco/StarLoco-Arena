/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from TT
 */
public class tt_2
extends ael_2 {
    private long gY;
    private byte[] bOS;

    public long ago() {
        return this.gY;
    }

    public byte[] agp() {
        return this.bOS;
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.G(byteBuffer);
        return true;
    }

    protected void G(ByteBuffer byteBuffer) {
        this.gY = byteBuffer.getLong();
        int n2 = byteBuffer.getShort() & 0xFFFF;
        if (n2 > 0) {
            this.bOS = new byte[n2];
            byteBuffer.get(this.bOS);
        } else {
            this.bOS = null;
        }
    }

    public int getId() {
        return 202;
    }
}

