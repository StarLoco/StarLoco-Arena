/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Jf
 */
public class jf_0
extends ael_2 {
    private String bak;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.bak = aey_0.V(byArray2);
        return true;
    }

    public int getId() {
        return 3166;
    }

    public String nn() {
        return this.bak;
    }
}

