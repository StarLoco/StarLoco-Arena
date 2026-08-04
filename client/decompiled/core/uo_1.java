/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from UO
 */
public class uo_1
extends ael_2 {
    private long mw;
    private long bRF;
    private String bRG;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.mw = byteBuffer.getLong();
        this.bRF = byteBuffer.getLong();
        byte[] byArray2 = new byte[byteBuffer.get() & 0xFF];
        byteBuffer.get(byArray2);
        this.bRG = aey_0.V(byArray2);
        return true;
    }

    public int getId() {
        return 5102;
    }

    public long fX() {
        return this.mw;
    }

    public long ahO() {
        return this.bRF;
    }

    public String ahP() {
        return this.bRG;
    }
}

