/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from kw
 */
public class kw_1
extends ael_2 {
    private byte aV;
    private byte Ew;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        this.Ew = byteBuffer.get();
        return true;
    }

    public byte an() {
        return this.aV;
    }

    public byte pa() {
        return this.Ew;
    }

    public int getId() {
        return 28616;
    }
}

