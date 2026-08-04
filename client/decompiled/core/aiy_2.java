/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from aiY
 */
public class aiy_2
extends ael_2 {
    private byte aV;
    private long sB;
    private ee_2 bN;
    private boolean czC;
    private short fO;

    public boolean a(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.aV = byteBuffer.get();
        if (this.aV == 0) {
            this.sB = byteBuffer.getLong();
            long l2 = byteBuffer.getLong();
            byte[] byArray2 = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray2);
            et_2 et_22 = new et_2();
            et_22.b(byArray2, false);
            this.bN = new ee_2();
            this.bN.c(l2);
            this.bN.f(et_22);
            this.czC = byteBuffer.get() == 1;
            this.fO = byteBuffer.getShort();
        }
        return true;
    }

    public int getId() {
        return 6000;
    }

    public byte an() {
        return this.aV;
    }

    public ee_2 tG() {
        return this.bN;
    }

    public long mb() {
        return this.sB;
    }

    public boolean ayR() {
        return this.czC;
    }

    public short ayS() {
        return this.fO;
    }
}

