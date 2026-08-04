/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from es
 */
public class es_0
extends ael_2 {
    private byte oC = (byte)-1;
    private short nO = (short)-1;
    private short oD = (short)-1;
    private int oE = -1;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 9, true)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.oC = byteBuffer.get();
        this.nO = byteBuffer.getShort();
        this.oD = byteBuffer.getShort();
        this.oE = byteBuffer.getInt();
        return true;
    }

    public int getId() {
        return 100;
    }

    public short ha() {
        return this.nO;
    }

    public byte hx() {
        return this.oC;
    }

    public short hy() {
        return this.oD;
    }

    public int hz() {
        return this.oE;
    }
}

