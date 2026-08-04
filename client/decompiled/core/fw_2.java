/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from fw
 */
public final class fw_2
extends lJ {
    private static final short fn = 1;
    private int aW;
    private int rv;

    public fw_2() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUW.getId();
    }

    public byte[] cr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putInt(this.aW);
        byteBuffer.putInt(this.rv);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.aW = byteBuffer.getInt();
            this.rv = byteBuffer.getInt();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new fw_2();
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int getType() {
        return this.rv;
    }

    public void setType(int n2) {
        this.rv = n2;
    }
}

