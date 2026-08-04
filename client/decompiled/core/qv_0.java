/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from qV
 */
public class qv_0
extends ael_2 {
    private String jg;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.jg = aey_0.V(byArray2);
        return true;
    }

    public int getId() {
        return 3202;
    }

    public String eQ() {
        return this.jg;
    }
}

