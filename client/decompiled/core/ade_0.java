/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aDE
 */
public class ade_0
extends ael_2 {
    private short Oo;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.Oo = byteBuffer.getShort();
        return true;
    }

    public int getId() {
        return 22000;
    }

    public short adP() {
        return this.Oo;
    }
}

