/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from WH
 */
public class wh_0
extends ael_2 {
    private String IC;
    private byte CZ;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.IC = aey_0.V(byArray2);
        this.CZ = byteBuffer.get();
        return true;
    }

    public int getId() {
        return 3142;
    }

    public byte Iq() {
        return this.CZ;
    }

    public String getUserName() {
        return this.IC;
    }
}

