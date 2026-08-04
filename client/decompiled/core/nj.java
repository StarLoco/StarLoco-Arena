/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class nj
extends ael_2 {
    private String sI;

    public String getMessage() {
        return this.sI;
    }

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.getInt()];
        byteBuffer.get(byArray2);
        this.sI = new String(byArray2);
        return true;
    }

    public int getId() {
        return 2070;
    }
}

