/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Mw
 */
public final class mw_0
extends lJ {
    private static final short fn = 1;
    private short bud;
    private byte bue;
    private akw_0[] UD = new akw_0[0];

    public mw_0() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cVm.getId();
    }

    public byte[] cr() {
        int n2 = 0;
        for (int j = 0; j < this.UD.length; ++j) {
            n2 += this.UD[j].nj();
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + n2);
        byteBuffer.putShort(this.bud);
        byteBuffer.put(this.bue);
        byteBuffer.put((byte)this.UD.length);
        for (int j = 0; j < this.UD.length; ++j) {
            this.UD[j].c(byteBuffer);
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.bud = byteBuffer.getShort();
            this.bue = byteBuffer.get();
            this.UD = new akw_0[byteBuffer.get()];
            for (int j = 0; j < this.UD.length; ++j) {
                this.UD[j] = akw_0.J(byteBuffer);
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new mw_0();
    }

    public short YF() {
        return this.bud;
    }

    public void aI(short s) {
        this.bud = s;
    }

    public byte YG() {
        return this.bue;
    }

    public void ab(byte by) {
        this.bue = by;
    }

    public akw_0[] tu() {
        return this.UD;
    }

    public void a(akw_0 akw_02) {
        if (this.UD == null) {
            this.UD = new akw_0[]{akw_02};
        } else {
            akw_0[] akw_0Array = this.UD;
            this.UD = new akw_0[this.UD.length + 1];
            System.arraycopy(akw_0Array, 0, this.UD, 1, akw_0Array.length);
            this.UD[0] = akw_02;
        }
    }
}

