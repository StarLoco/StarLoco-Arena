/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class agH
extends ael_2 {
    private byte aV;
    private short Gm;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        if (this.aV == 0) {
            this.Gm = byteBuffer.getShort();
        }
        return true;
    }

    public int getId() {
        return 6022;
    }

    public byte an() {
        return this.aV;
    }

    public short qY() {
        return this.Gm;
    }
}

