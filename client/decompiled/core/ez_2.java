/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Ez
 */
public class ez_2
extends ael_2 {
    private String aIQ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.aIQ = aey_0.V(byArray2);
        return true;
    }

    public int getId() {
        return 3208;
    }

    public String getMemberName() {
        return this.aIQ;
    }
}

