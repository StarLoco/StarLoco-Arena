/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class ahV
extends ael_2 {
    private String cxh;
    private short fO;
    private String pX;
    private long ap;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.cxh = aey_0.V(byArray2);
        this.fO = byteBuffer.getShort();
        byArray2 = new byte[byteBuffer.get()];
        byteBuffer.get(byArray2);
        this.pX = aey_0.V(byArray2);
        this.ap = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 26314;
    }

    public String axz() {
        return this.cxh;
    }

    public short axA() {
        return this.fO;
    }

    public String hX() {
        return this.pX;
    }

    public long Y() {
        return this.ap;
    }
}

