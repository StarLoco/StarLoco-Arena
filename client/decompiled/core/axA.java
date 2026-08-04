/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class axA
extends ael_2 {
    private long axC;
    private short UF;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.axC = byteBuffer.getLong();
        this.UF = byteBuffer.getShort();
        return true;
    }

    public int getId() {
        return 22092;
    }

    public long DJ() {
        return this.axC;
    }

    public short tw() {
        return this.UF;
    }
}

