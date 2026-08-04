/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class Uy
extends ael_2 {
    private String jg;
    private String aIQ;
    private byte CZ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.jg = aey_0.V(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray3);
        this.aIQ = aey_0.V(byArray3);
        this.CZ = byteBuffer.get();
        return true;
    }

    public int getId() {
        return 3134;
    }

    public String eQ() {
        return this.jg;
    }

    public byte Iq() {
        return this.CZ;
    }

    public String getMemberName() {
        return this.aIQ;
    }
}

