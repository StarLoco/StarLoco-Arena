/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aEC
 */
public class aec_2
extends ael_2 {
    private float dBv;
    private float dBw;
    private short nG;
    private short dBx;
    private boolean dBy;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.dBv = byteBuffer.getFloat();
        this.dBw = byteBuffer.getFloat();
        this.nG = byteBuffer.getShort();
        this.dBx = byteBuffer.getShort();
        this.dBy = byteBuffer.get() == 1;
        return true;
    }

    public int getId() {
        return 4600;
    }

    public short gQ() {
        return this.nG;
    }

    public short aQC() {
        return this.dBx;
    }

    public float aQD() {
        return this.dBv;
    }

    public float aQE() {
        return this.dBw;
    }

    public boolean isDynamic() {
        return this.dBy;
    }
}

