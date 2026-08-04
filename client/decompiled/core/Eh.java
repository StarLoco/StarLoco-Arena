/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Eh
extends ael_2 {
    private ho_0 aQa;
    private long aQb;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aQb = byteBuffer.getLong();
        this.aQa = new ho_0();
        this.aQa.f(byteBuffer);
        return true;
    }

    public int getId() {
        return 15003;
    }

    public ho_0 MO() {
        return this.aQa;
    }

    public long MP() {
        return this.aQb;
    }
}

