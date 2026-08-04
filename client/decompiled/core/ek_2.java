/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from EK
 */
public final class ek_2
extends lJ {
    private static final short fn = 1;
    private byte aTP;

    public ek_2() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cVe.getId();
    }

    public byte[] cr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.put(this.aTP);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.aTP = byteBuffer.get();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new ek_2();
    }

    public byte Oy() {
        return this.aTP;
    }

    public void X(byte by) {
        this.aTP = by;
    }
}

