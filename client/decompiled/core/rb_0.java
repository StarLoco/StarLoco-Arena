/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

/*
 * Renamed from Rb
 */
public class rb_0
extends lJ {
    public static final short fn = 1;
    private int aW;
    private short Gp;
    private int amO;
    private int abb;
    private byte aba;
    private int it;

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int eA() {
        return this.it;
    }

    public void L(int n2) {
        this.it = n2;
    }

    public short getType() {
        return this.Gp;
    }

    public void setType(short s) {
        this.Gp = s;
    }

    public int adz() {
        return this.amO;
    }

    public void ht(int n2) {
        this.amO = n2;
    }

    public int adA() {
        return this.abb;
    }

    public void setColor(int n2) {
        this.abb = n2;
    }

    public byte PD() {
        return this.aba;
    }

    public void ag(byte by) {
        this.aba = by;
    }

    public rb_0() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUS.getId();
    }

    public byte[] cr() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(19);
        byteBuffer.putInt(this.aW);
        byteBuffer.putShort(this.Gp);
        byteBuffer.putInt(this.amO);
        byteBuffer.putInt(this.abb);
        byteBuffer.put(this.aba);
        byteBuffer.putInt(this.it);
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.aW = byteBuffer.getInt();
            this.Gp = byteBuffer.getShort();
            this.amO = byteBuffer.getInt();
            this.abb = byteBuffer.getInt();
            this.aba = byteBuffer.get();
            this.it = byteBuffer.getInt();
        } else {
            a.error((Object)"Tentative de deserialisation d'un objet binaire avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new rb_0();
    }
}

