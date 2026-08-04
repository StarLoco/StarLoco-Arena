/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from uh
 */
public final class uh_0
extends lJ {
    private static final short fn = 1;
    private int aW;
    private short Gp;
    private byte iv;
    private int r;
    private int apB;
    private int apC;
    private int it;
    private int apD;
    private boolean iE;
    private boolean iD;
    private boolean iF;
    private boolean apE;
    private boolean apF;
    private boolean apG;
    private final ArrayList iM = new ArrayList();

    public uh_0() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUP.getId();
    }

    public byte[] cr() {
        int n2 = 4;
        for (Object object : this.iM) {
            n2 += ((Ht)object).cr().length + 4 + 4 + 2;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2 + 4 + 2 + 1 + 4 + 4 + 4 + 4 + 4 + 1 + 1 + 1 + 1 + 1 + 1);
        byteBuffer.putInt(this.aW);
        byteBuffer.putShort(this.Gp);
        byteBuffer.put(this.iv);
        byteBuffer.putInt(this.r);
        byteBuffer.putInt(this.apB);
        byteBuffer.putInt(this.apC);
        byteBuffer.putInt(this.it);
        byteBuffer.putInt(this.apD);
        byteBuffer.put(this.iE ? (byte)1 : 0);
        byteBuffer.put(this.iD ? (byte)1 : 0);
        byteBuffer.put(this.iF ? (byte)1 : 0);
        byteBuffer.put(this.apE ? (byte)1 : 0);
        byteBuffer.put(this.apF ? (byte)1 : 0);
        byteBuffer.put((byte)(this.apG ? 1 : 0));
        byteBuffer.putInt(this.iM.size());
        for (Ht ht : this.iM) {
            byteBuffer.putInt(ht.qw());
            byteBuffer.putShort(ht.qx());
            byte[] byArray = ht.cr();
            byteBuffer.putInt(byArray.length);
            byteBuffer.put(byArray);
        }
        return byteBuffer.array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            this.aW = byteBuffer.getInt();
            this.Gp = byteBuffer.getShort();
            this.iv = byteBuffer.get();
            this.r = byteBuffer.getInt();
            this.apB = byteBuffer.getInt();
            this.apC = byteBuffer.getInt();
            this.it = byteBuffer.getInt();
            this.apD = byteBuffer.getInt();
            this.iE = byteBuffer.get() == 1;
            this.iD = byteBuffer.get() == 1;
            this.iF = byteBuffer.get() == 1;
            this.apE = byteBuffer.get() == 1;
            this.apF = byteBuffer.get() == 1;
            this.apG = byteBuffer.get() == 1;
            int n3 = byteBuffer.getInt();
            for (int j = 0; j < n3; ++j) {
                int n4 = byteBuffer.getInt();
                short s2 = byteBuffer.getShort();
                byte[] byArray = new byte[byteBuffer.getInt()];
                byteBuffer.get(byArray);
                Ht ht = new Ht();
                ht.a(ByteBuffer.wrap(byArray), n4, s2);
                this.a(ht);
            }
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new uh_0();
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public short getType() {
        return this.Gp;
    }

    public void setType(short s) {
        this.Gp = s;
    }

    public byte eo() {
        return this.iv;
    }

    public void d(byte by) {
        this.iv = by;
    }

    public int getValue() {
        return this.r;
    }

    public void setValue(int n2) {
        this.r = n2;
    }

    public int Az() {
        return this.apB;
    }

    public void dM(int n2) {
        this.apB = n2;
    }

    public int AA() {
        return this.apC;
    }

    public void dN(int n2) {
        this.apC = n2;
    }

    public boolean ex() {
        return this.iE;
    }

    public void o(boolean bl2) {
        this.iE = bl2;
    }

    public boolean ew() {
        return this.iD;
    }

    public void n(boolean bl2) {
        this.iD = bl2;
    }

    public boolean ey() {
        return this.iF;
    }

    public void p(boolean bl2) {
        this.iF = bl2;
    }

    public boolean AB() {
        return this.apE;
    }

    public void aF(boolean bl2) {
        this.apE = bl2;
    }

    public boolean AC() {
        return this.apF;
    }

    public void aG(boolean bl2) {
        this.apF = bl2;
    }

    public boolean AD() {
        return this.apG;
    }

    public void aH(boolean bl2) {
        this.apG = bl2;
    }

    public int eA() {
        return this.it;
    }

    public void L(int n2) {
        this.it = n2;
    }

    public int AE() {
        return this.apD;
    }

    public void dO(int n2) {
        this.apD = n2;
    }

    public ArrayList eC() {
        return this.iM;
    }

    public void a(Ht ht) {
        this.iM.add(ht);
    }
}

