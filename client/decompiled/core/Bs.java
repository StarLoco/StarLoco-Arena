/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Bs
extends ael_2 {
    private String jg;
    private String aIQ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.jg = aey_0.V(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray3);
        this.aIQ = aey_0.V(byArray3);
        return true;
    }

    public int getId() {
        return 3132;
    }

    public String eQ() {
        return this.jg;
    }

    public String getMemberName() {
        return this.aIQ;
    }
}

