/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from CB
 */
public class cb_2
extends lJ {
    public static final cb_2 aLZ = null;
    private static final short fn = 1;
    private int aW;
    private int aMa;

    public cb_2() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cVk.getId();
    }

    public byte[] cr() {
        int n2 = 8;
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putInt(this.aW);
        byteBuffer.putInt(this.aMa);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.aW = byteBuffer.getInt();
            this.aMa = byteBuffer.getInt();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge.");
        }
    }

    public lJ cs() {
        return new cb_2();
    }

    public int getId() {
        return this.aW;
    }

    public int Km() {
        return this.aMa;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public void eQ(int n2) {
        this.aMa = n2;
    }
}

