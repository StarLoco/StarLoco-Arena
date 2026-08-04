/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from rY
 */
public class ry_1
extends ael_2 {
    private String aiK;
    private boolean aiL;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.aiK = aey_0.V(byArray2);
        this.aiL = byteBuffer.get() == 1;
        return true;
    }

    public int getId() {
        return 560;
    }

    public String xW() {
        return this.aiK;
    }

    public boolean xX() {
        return this.aiL;
    }
}

