/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from yP
 */
public final class yp_0
extends lJ {
    private static final short fn = 1;
    private int Ut;
    private akw_0[] UD = new akw_0[0];

    public yp_0() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUH.getId();
    }

    public byte[] cr() {
        int n2 = 0;
        for (int j = 0; j < this.UD.length; ++j) {
            n2 += this.UD[j].nj();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + n2 + 1);
        byteBuffer.putInt(this.Ut);
        byteBuffer.put((byte)this.UD.length);
        for (int j = 0; j < this.UD.length; ++j) {
            this.UD[j].c(byteBuffer);
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.Ut = byteBuffer.getInt();
            this.UD = new akw_0[byteBuffer.get()];
            for (int j = 0; j < this.UD.length; ++j) {
                this.UD[j] = akw_0.J(byteBuffer);
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new yp_0();
    }

    public int tm() {
        return this.Ut;
    }

    public void eA(int n2) {
        this.Ut = n2;
    }

    public void a(akw_0 akw_02) {
        akw_0[] akw_0Array = this.UD;
        this.UD = new akw_0[this.UD.length + 1];
        System.arraycopy(akw_0Array, 0, this.UD, 1, akw_0Array.length);
        this.UD[0] = akw_02;
    }

    public akw_0[] tu() {
        return this.UD;
    }
}

