/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from FW
 */
public final class fw_0
extends lJ {
    private static final short fn = 1;
    private short auD;

    public fw_0() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUY.getId();
    }

    public byte[] cr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.putShort(this.auD);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.auD = byteBuffer.getShort();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new fw_0();
    }

    public short CJ() {
        return this.auD;
    }

    public void ag(short s) {
        this.auD = s;
    }
}

