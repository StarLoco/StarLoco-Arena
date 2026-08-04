/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class ahU
extends ael_2 {
    private String aiK;
    private String nR;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.aiK = aey_0.V(byArray2);
        byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.nR = aey_0.V(byArray2);
        return true;
    }

    public int getId() {
        return 558;
    }

    public String xW() {
        return this.aiK;
    }

    public String hd() {
        return this.nR;
    }
}

