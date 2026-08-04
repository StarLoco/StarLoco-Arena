/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from qR
 */
public abstract class qr_2
extends ael_2 {
    private int afy;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.n(byteBuffer);
        return true;
    }

    public int getId() {
        return 103;
    }

    protected void n(ByteBuffer byteBuffer) {
        this.afy = byteBuffer.getInt();
    }

    public int wd() {
        return this.afy;
    }
}

