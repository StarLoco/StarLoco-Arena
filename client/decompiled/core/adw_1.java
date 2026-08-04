/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aDw
 */
public class adw_1
extends ael_2 {
    private String abZ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.abZ = aey_0.V(byArray2);
        return true;
    }

    public String ui() {
        return this.abZ;
    }

    public int getId() {
        return 3160;
    }
}

