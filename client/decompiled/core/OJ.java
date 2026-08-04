/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class OJ
extends ael_2 {
    private byte aV;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        return true;
    }

    public int getId() {
        return 6029;
    }

    public byte an() {
        return this.aV;
    }
}

