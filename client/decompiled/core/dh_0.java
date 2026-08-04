/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Dh
 */
public class dh_0
extends ael_2 {
    private String abZ;
    private String aca;
    private long BK;
    private String aNr;
    private short aNs;
    private long aNt;
    private byte zv;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.abZ = aey_0.V(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray3);
        this.aca = aey_0.V(byArray3);
        byte[] byArray4 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray4);
        this.aNr = aey_0.V(byArray4);
        this.BK = byteBuffer.getLong();
        this.aNs = byteBuffer.getShort();
        this.zv = byteBuffer.get();
        this.aNt = byteBuffer.getLong();
        return true;
    }

    public int getId() {
        return 3148;
    }

    public String ui() {
        return this.abZ;
    }

    public long no() {
        return this.BK;
    }

    public String uj() {
        return this.aca;
    }

    public byte lZ() {
        return this.zv;
    }

    public short LA() {
        return this.aNs;
    }

    public String LB() {
        return this.aNr;
    }

    public long LC() {
        return this.aNt;
    }
}

