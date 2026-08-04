/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class cJ
extends ael_2 {
    private boolean jy;

    public boolean eY() {
        return this.jy;
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.jy = byteBuffer.get() != 0;
        return true;
    }

    public int getId() {
        return 2309;
    }
}

