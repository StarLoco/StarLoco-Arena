/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Ru
 */
public final class ru_1
extends lJ {
    private static final short fn = 1;
    private short Oo;
    private short bIY;
    private short bIZ;
    private short bJa;
    private short bJb;
    private short bJc;
    private int it;
    private aGz bJd = new aGz();
    private jg_0 bJe = new jg_0();
    private boolean bJf;
    private int bJg;
    private boolean bJh;

    public ru_1() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUX.getId();
    }

    public byte[] cr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(17 + 4 * this.bJd.size() + 1 + this.bJe.size() * 4 + 1 + 4 + 1);
        byteBuffer.putShort(this.Oo);
        byteBuffer.putShort(this.bIY);
        byteBuffer.putShort(this.bIZ);
        byteBuffer.putShort(this.bJa);
        byteBuffer.putShort(this.bJb);
        byteBuffer.putShort(this.bJc);
        byteBuffer.putInt(this.it);
        byteBuffer.put((byte)this.bJd.size());
        for (short s : this.bJd.Gj()) {
            byteBuffer.putShort(s);
            byteBuffer.putShort(this.bJd.cp(s));
        }
        byteBuffer.put((byte)this.bJe.size());
        for (int j = 0; j < this.bJe.size(); ++j) {
            byteBuffer.putInt(this.bJe.bu(j));
        }
        byteBuffer.put((byte)(this.bJf ? 1 : 0));
        byteBuffer.putInt(this.bJg);
        byteBuffer.put((byte)(this.bJh ? 1 : 0));
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            this.Oo = byteBuffer.getShort();
            this.bIY = byteBuffer.getShort();
            this.bIZ = byteBuffer.getShort();
            this.bJa = byteBuffer.getShort();
            this.bJb = byteBuffer.getShort();
            this.bJc = byteBuffer.getShort();
            this.it = byteBuffer.getInt();
            int n4 = byteBuffer.get();
            for (n3 = 0; n3 < n4; ++n3) {
                this.bJd.A(byteBuffer.getShort(), byteBuffer.getShort());
            }
            n4 = byteBuffer.get();
            for (n3 = 0; n3 < n4; ++n3) {
                this.bJe.add(byteBuffer.getInt());
            }
            this.bJf = byteBuffer.get() == 1;
            this.bJg = byteBuffer.getInt();
            this.bJh = byteBuffer.get() == 1;
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new ru_1();
    }

    public short adP() {
        return this.Oo;
    }

    public void K(short s) {
        this.Oo = s;
    }

    public short adQ() {
        return this.bIY;
    }

    public void aY(short s) {
        this.bIY = s;
    }

    public short adR() {
        return this.bJa;
    }

    public void aZ(short s) {
        this.bJa = s;
    }

    public aGz adS() {
        return this.bJd;
    }

    public void r(short s, short s2) {
        this.bJd.A(s, s2);
    }

    public short adT() {
        return this.bJb;
    }

    public void ba(short s) {
        this.bJb = s;
    }

    public short adU() {
        return this.bIZ;
    }

    public void bb(short s) {
        this.bIZ = s;
    }

    public short adV() {
        return this.bJc;
    }

    public void bc(short s) {
        this.bJc = s;
    }

    public boolean adW() {
        return this.bJf;
    }

    public void cl(boolean bl2) {
        this.bJf = bl2;
    }

    public jg_0 adX() {
        return this.bJe;
    }

    public void hv(int n2) {
        this.bJe.add(n2);
    }

    public int adY() {
        return this.bJg;
    }

    public void hw(int n2) {
        this.bJg = n2;
    }

    public int eA() {
        return this.it;
    }

    public void L(int n2) {
        this.it = n2;
    }

    public boolean isHidden() {
        return this.bJh;
    }

    public void setHidden(boolean bl2) {
        this.bJh = bl2;
    }
}

