/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from cz
 */
public class cz_0
extends ael_2 {
    private String jg;
    private String jh;
    private byte ji;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.jg = aey_0.V(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray3);
        this.jh = aey_0.V(byArray3);
        this.ji = byteBuffer.get();
        return true;
    }

    public int getId() {
        return 3130;
    }

    public String eQ() {
        return this.jg;
    }

    public byte eR() {
        return this.ji;
    }

    public String eS() {
        return this.jh;
    }
}

