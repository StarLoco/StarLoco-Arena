/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Kz
 */
public class kz_1
extends ael_2 {
    private String abZ;
    private String aca;
    private long bnX;
    private short bnY;
    private byte bnZ;
    private short boa;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.abZ = aey_0.V(byArray2);
        byte[] byArray3 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray3);
        this.aca = aey_0.V(byArray3);
        this.bnX = byteBuffer.getLong();
        this.bnY = byteBuffer.getShort();
        this.bnZ = byteBuffer.get();
        this.boa = byteBuffer.getShort();
        return true;
    }

    public int getId() {
        return 3156;
    }

    public String ui() {
        return this.abZ;
    }

    public long WK() {
        return this.bnX;
    }

    public String uj() {
        return this.aca;
    }

    public short WL() {
        return this.bnY;
    }

    public byte WM() {
        return this.bnZ;
    }

    public short WN() {
        return this.boa;
    }
}

