/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from By
 */
public class by_1
extends ael_2 {
    private String jg;
    private byte CZ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.jg = aey_0.V(byArray2);
        this.CZ = byteBuffer.get();
        return true;
    }

    public int getId() {
        return 3128;
    }

    public String eQ() {
        return this.jg;
    }

    public byte Iq() {
        return this.CZ;
    }
}

