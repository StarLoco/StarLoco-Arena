/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;

/*
 * Renamed from co
 */
public final class co_1
extends lJ {
    private static final short fn = 1;
    private int ir;
    private int is;
    private int r;
    private int it;
    private int iu;
    private byte iv;
    private byte iw;
    private byte ix;
    private byte iy;
    private byte iz;
    private byte iA;
    private byte iB;
    private byte iC;
    private boolean iD;
    private boolean iE;
    private boolean iF;
    private boolean iG;
    private String iH;
    private boolean iI;
    private long[] iJ;
    private boolean iK;
    private int iL;
    private final ArrayList iM = new ArrayList();

    public co_1() {
        super((short)1);
    }

    public int cq() {
        return atr_0.cUL.getId();
    }

    public byte[] cr() {
        Object object2;
        int n2 = 4;
        for (Object object2 : this.iM) {
            n2 += ((Ht)object2).cr().length + 4 + 4 + 2;
        }
        Object object3 = aey_0.hH(this.iH);
        object2 = ByteBuffer.allocate(n2 + 4 + 4 + 4 + 4 + 4 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 4 + this.iH.length() + 4 + (this.iJ == null ? 0 : this.iJ.length * 8) + 4);
        ((ByteBuffer)object2).putInt(this.ir);
        ((ByteBuffer)object2).putInt(this.is);
        ((ByteBuffer)object2).putInt(this.r);
        ((ByteBuffer)object2).putInt(this.iu);
        ((ByteBuffer)object2).putInt(this.it);
        ((ByteBuffer)object2).put(this.iv);
        ((ByteBuffer)object2).put(this.iw);
        ((ByteBuffer)object2).put(this.ix);
        ((ByteBuffer)object2).put(this.iy);
        ((ByteBuffer)object2).put(this.iz);
        ((ByteBuffer)object2).put(this.iA);
        ((ByteBuffer)object2).put(this.iB);
        ((ByteBuffer)object2).put(this.iC);
        ((ByteBuffer)object2).put(this.iD ? (byte)1 : 0);
        ((ByteBuffer)object2).put(this.iE ? (byte)1 : 0);
        ((ByteBuffer)object2).put(this.iF ? (byte)1 : 0);
        ((ByteBuffer)object2).put(this.iG ? (byte)1 : 0);
        ((ByteBuffer)object2).put(this.iI ? (byte)1 : 0);
        ((ByteBuffer)object2).put(this.iK ? (byte)1 : 0);
        ((ByteBuffer)object2).putInt(((Object)object3).length);
        ((ByteBuffer)object2).put((byte[])object3);
        ((ByteBuffer)object2).putInt(this.iM.size());
        for (Ht ht : this.iM) {
            ((ByteBuffer)object2).putInt(ht.qw());
            ((ByteBuffer)object2).putShort(ht.qx());
            byte[] byArray = ht.cr();
            ((ByteBuffer)object2).putInt(byArray.length);
            ((ByteBuffer)object2).put(byArray);
        }
        if (this.iJ != null) {
            ((ByteBuffer)object2).putInt(this.iJ.length);
            for (Object object4 : (Object)this.iJ) {
                ((ByteBuffer)object2).putLong((long)object4);
            }
        } else {
            ((ByteBuffer)object2).putInt(0);
        }
        ((ByteBuffer)object2).putInt(this.iL);
        return ((ByteBuffer)object2).array();
    }

    public void a(ByteBuffer byteBuffer, int n2, short s) {
        this.cd(n2);
        if (s == 1) {
            int n3;
            this.ir = byteBuffer.getInt();
            this.is = byteBuffer.getInt();
            this.r = byteBuffer.getInt();
            this.iu = byteBuffer.getInt();
            this.it = byteBuffer.getInt();
            this.iv = byteBuffer.get();
            this.iw = byteBuffer.get();
            this.ix = byteBuffer.get();
            this.iy = byteBuffer.get();
            this.iz = byteBuffer.get();
            this.iA = byteBuffer.get();
            this.iB = byteBuffer.get();
            this.iC = byteBuffer.get();
            this.iD = byteBuffer.get() == 1;
            this.iE = byteBuffer.get() == 1;
            this.iF = byteBuffer.get() == 1;
            this.iG = byteBuffer.get() == 1;
            this.iI = byteBuffer.get() == 1;
            this.iK = byteBuffer.get() == 1;
            byte[] byArray = new byte[byteBuffer.getInt()];
            byteBuffer.get(byArray);
            this.iH = aey_0.V(byArray);
            int n4 = byteBuffer.getInt();
            for (n3 = 0; n3 < n4; ++n3) {
                int n5 = byteBuffer.getInt();
                short s2 = byteBuffer.getShort();
                byte[] byArray2 = new byte[byteBuffer.getInt()];
                byteBuffer.get(byArray2);
                Ht ht = new Ht();
                ht.a(ByteBuffer.wrap(byArray2), n5, s2);
                this.a(ht);
            }
            n4 = byteBuffer.getInt();
            this.iJ = new long[n4];
            for (n3 = 0; n3 < n4; ++n3) {
                this.iJ[n3] = byteBuffer.getLong();
            }
            this.iL = byteBuffer.getInt();
        } else {
            a.error((Object)"Tentative de d\u00e9s\u00e9rialisation d'un objet avec une version non prise en charge");
        }
    }

    public lJ cs() {
        return new co_1();
    }

    public int el() {
        return this.ir;
    }

    public void I(int n2) {
        this.ir = n2;
    }

    public int em() {
        return this.is;
    }

    public void J(int n2) {
        this.is = n2;
    }

    public int getValue() {
        return this.r;
    }

    public void setValue(int n2) {
        this.r = n2;
    }

    public int en() {
        return this.iu;
    }

    public void K(int n2) {
        this.iu = n2;
    }

    public byte eo() {
        return this.iv;
    }

    public void d(byte by) {
        this.iv = by;
    }

    public byte ep() {
        return this.iw;
    }

    public void e(byte by) {
        this.iw = by;
    }

    public byte eq() {
        return this.ix;
    }

    public void f(byte by) {
        this.ix = by;
    }

    public byte er() {
        return this.iy;
    }

    public void g(byte by) {
        this.iy = by;
    }

    public byte es() {
        return this.iz;
    }

    public void h(byte by) {
        this.iz = by;
    }

    public byte et() {
        return this.iA;
    }

    public void i(byte by) {
        this.iA = by;
    }

    public byte eu() {
        return this.iB;
    }

    public void j(byte by) {
        this.iB = by;
    }

    public byte ev() {
        return this.iC;
    }

    public void k(byte by) {
        this.iC = by;
    }

    public boolean ew() {
        return this.iD;
    }

    public void n(boolean bl2) {
        this.iD = bl2;
    }

    public boolean ex() {
        return this.iE;
    }

    public void o(boolean bl2) {
        this.iE = bl2;
    }

    public boolean ey() {
        return this.iF;
    }

    public void p(boolean bl2) {
        this.iF = bl2;
    }

    public String ez() {
        return this.iH;
    }

    public void A(String string) {
        this.iH = string;
    }

    public int eA() {
        return this.it;
    }

    public void L(int n2) {
        this.it = n2;
    }

    public boolean eB() {
        return this.iG;
    }

    public void q(boolean bl2) {
        this.iG = bl2;
    }

    public ArrayList eC() {
        return this.iM;
    }

    public void a(Ht ht) {
        this.iM.add(ht);
    }

    public boolean eD() {
        return this.iI;
    }

    public void r(boolean bl2) {
        this.iI = bl2;
    }

    public long[] eE() {
        return this.iJ;
    }

    public void a(long[] lArray) {
        this.iJ = lArray;
    }

    public boolean eF() {
        return this.iK;
    }

    public void s(boolean bl2) {
        this.iK = bl2;
    }

    public int eG() {
        return this.iL;
    }

    public void M(int n2) {
        this.iL = n2;
    }
}

