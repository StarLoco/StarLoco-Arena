/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aIK
 */
public class aik_1
extends ael_2 {
    private String jz;
    private long jA;
    private String bY;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.jz = aey_0.V(byArray2);
        this.jA = byteBuffer.getLong();
        byte[] byArray3 = new byte[byteBuffer.getShort()];
        byteBuffer.get(byArray3);
        this.bY = aey_0.V(byArray3);
        return true;
    }

    public int getId() {
        return 3170;
    }

    public long eZ() {
        return this.jA;
    }

    public String fa() {
        return this.jz;
    }

    public String fb() {
        return this.bY;
    }
}

