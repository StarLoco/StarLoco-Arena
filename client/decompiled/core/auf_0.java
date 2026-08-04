/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from auF
 */
public class auf_0
extends ael_2 {
    private String ju;
    private String jv;
    private byte js;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.js = byteBuffer.get();
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.ju = aey_0.V(byArray2);
        byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.jv = aey_0.V(byArray2);
        return true;
    }

    public String MG() {
        return this.ju;
    }

    public byte rp() {
        return this.js;
    }

    public String CL() {
        return this.jv;
    }

    public int getId() {
        return 502;
    }
}

