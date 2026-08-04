/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from AX
 */
public class ax_2
extends ael_2 {
    private String jg;
    private byte[] aIt;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.jg = aey_0.V(byArray2);
        this.aIt = new byte[byteBuffer.remaining()];
        byteBuffer.get(this.aIt);
        return true;
    }

    public int getId() {
        return 3138;
    }

    public String eQ() {
        return this.jg;
    }

    public byte[] HW() {
        return this.aIt;
    }
}

