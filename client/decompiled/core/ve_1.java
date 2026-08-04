/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from vE
 */
public class ve_1
extends ael_2 {
    private String IC;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.IC = aey_0.V(byArray2);
        return true;
    }

    public int getId() {
        return 3204;
    }

    public String getUserName() {
        return this.IC;
    }
}

