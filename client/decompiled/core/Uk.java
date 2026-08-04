/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Uk
extends ael_2 {
    private byte bPG;
    private int bPH;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.bPG = byteBuffer.get();
        if (this.bPG == 5 || this.bPG == 6) {
            this.bPH = byteBuffer.getInt();
        }
        return true;
    }

    public int getId() {
        return 1024;
    }

    public boolean agF() {
        return this.bPG == 0;
    }

    public byte an() {
        return this.bPG;
    }

    public int agG() {
        return this.bPH;
    }
}

