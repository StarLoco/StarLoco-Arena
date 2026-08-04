/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from xp
 */
public class xp_0
extends ael_2 {
    private long axC;
    private int nE;
    private int nF;
    private short nG;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 18, true)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.axC = byteBuffer.getLong();
        this.nE = byteBuffer.getInt();
        this.nF = byteBuffer.getInt();
        this.nG = byteBuffer.getShort();
        return true;
    }

    public int getId() {
        return 4510;
    }

    public long DJ() {
        return this.axC;
    }

    public short gQ() {
        return this.nG;
    }

    public int gO() {
        return this.nE;
    }

    public int gP() {
        return this.nF;
    }
}

