/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from azT
 */
public class azt_0
extends ael_2 {
    String bnb;
    long axC;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.axC = byteBuffer.getLong();
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.bnb = new String(byArray2);
        return true;
    }

    public int getId() {
        return 4700;
    }

    public String aMw() {
        return this.bnb;
    }

    public long DJ() {
        return this.axC;
    }
}

