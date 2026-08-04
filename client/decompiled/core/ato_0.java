/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from atO
 */
public class ato_0
extends ael_2 {
    private String jg;
    private String aIQ;
    private String cUw;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.jg = aey_0.V(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray3);
        this.aIQ = aey_0.V(byArray3);
        byte[] byArray4 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray4);
        this.cUw = aey_0.V(byArray4);
        return true;
    }

    public int getId() {
        return 3136;
    }

    public String eQ() {
        return this.jg;
    }

    public String aGS() {
        return this.cUw;
    }

    public String getMemberName() {
        return this.aIQ;
    }
}

