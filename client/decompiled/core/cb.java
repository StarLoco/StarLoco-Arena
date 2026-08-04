/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class cb
extends ael_2 {
    private long ia;
    private long ib;
    private wy_2[] ic;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.ia = byteBuffer.getLong();
        this.ib = byteBuffer.getLong();
        this.ic = new wy_2[byteBuffer.get()];
        for (int j = 0; j < this.ic.length; ++j) {
            this.ic[j] = new wy_2();
            this.ic[j].b(byteBuffer);
            this.ic[j].q((short)1);
        }
        return true;
    }

    public int getId() {
        return 15007;
    }

    public long ec() {
        return this.ia;
    }

    public wy_2[] ed() {
        return this.ic;
    }

    public long ee() {
        return this.ib;
    }
}

