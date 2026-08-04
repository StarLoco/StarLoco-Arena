/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class aLi
extends ael_2 {
    private short Gm;
    private boolean jy;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.Gm = byteBuffer.getShort();
        this.jy = byteBuffer.get() != 0;
        return true;
    }

    public short qY() {
        return this.Gm;
    }

    public boolean eY() {
        return this.jy;
    }

    public int getId() {
        return 23104;
    }
}

