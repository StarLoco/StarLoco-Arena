/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

public abstract class fv
implements Pi,
akU {
    protected acy rd = new acy();
    private final int aW;
    private final byte re;
    private final int rf;
    private final byte rg;
    private final byte rh;
    private final byte ri;
    private final byte rj;
    private final byte iA;
    private final boolean rk;
    private final boolean rl;
    private final boolean rm;
    private final byte rn;
    private final byte ro;
    private final int r;
    private final int rp;
    private final List rq;
    private boolean rr = false;
    private boolean iI;
    private boolean iK;
    protected long[] rs;
    protected fv[] rt;
    protected fv ru;

    public fv(int n2, int n3, byte by, byte by2, byte by3, byte by4, byte by5, byte by6, boolean bl2, boolean bl3, byte by7, byte by8, int n4, int n5, boolean bl4, List list, boolean bl5, long[] lArray, boolean bl6, fv fv2) {
        this.aW = n2;
        this.rf = n3;
        this.re = by;
        this.rg = by2;
        this.rh = by3;
        this.ri = by4;
        this.rj = by5;
        this.rk = bl2;
        this.rl = bl3;
        this.rn = (byte)Math.max(0, Math.min(by7, by8));
        this.ro = (byte)Math.max(0, Math.max(by7, by8));
        this.r = n4;
        this.rp = n5;
        this.rm = bl4;
        this.rq = list;
        this.iA = by6;
        this.iI = bl5;
        this.rs = lArray;
        this.iK = bl6;
        this.rt = null;
        this.ru = fv2;
    }

    public String toString() {
        return "(" + this.aW + ", " + xq.ej(this.rf) + ")";
    }

    public int getId() {
        return this.aW;
    }

    public void a(xj_0 xj_02) {
        this.rd.add(xj_02);
        if (xj_02.di(1L)) {
            this.rr = true;
        }
    }

    public void a(xj_0[] xj_0Array) {
        for (xj_0 xj_02 : xj_0Array) {
            this.a(xj_02);
        }
    }

    public xj_0 ax(int n2) {
        for (xj_0 xj_02 : this.rd) {
            if (xj_02.ST() != n2) continue;
            return xj_02;
        }
        return null;
    }

    public xj_0 ay(int n2) {
        for (xj_0 xj_02 : this.rd) {
            if (xj_02.M() != n2) continue;
            return xj_02;
        }
        return null;
    }

    public acy iK() {
        return this.rd;
    }

    public List iL() {
        return this.rq;
    }

    public boolean iM() {
        return this.rr;
    }

    public boolean iN() {
        return this.rl;
    }

    public long iO() {
        return this.getId();
    }

    public int iP() {
        return 13;
    }

    public Iterator iterator() {
        return this.rd.iterator();
    }

    public int iQ() {
        return this.rf;
    }

    public byte iR() {
        return this.re;
    }

    public byte iS() {
        return this.rg;
    }

    public byte iT() {
        return this.rh;
    }

    public byte iU() {
        return this.ri;
    }

    public byte iV() {
        return this.rj;
    }

    public boolean iW() {
        return this.rk;
    }

    public boolean iX() {
        return this.rm;
    }

    public byte iY() {
        return this.rn;
    }

    public byte iZ() {
        return this.ro;
    }

    public int getValue() {
        return this.r;
    }

    public byte ja() {
        return this.rj;
    }

    public byte et() {
        return this.iA;
    }

    public boolean eD() {
        return this.iI;
    }

    public long[] jb() {
        return this.rs;
    }

    public void b(long[] lArray) {
        this.rs = lArray;
    }

    public fv[] jc() {
        return this.rt;
    }

    public void a(fv fv2) {
        if (this.rt != null) {
            fv[] fvArray = new fv[this.rt.length + 1];
            System.arraycopy(this.rt, 0, fvArray, 0, this.rt.length);
            fvArray[fvArray.length - 1] = fv2;
            this.rt = fvArray;
        } else {
            this.rt = new fv[]{fv2};
        }
    }

    public fv jd() {
        return this.ru;
    }

    public void b(fv fv2) {
        this.ru = fv2;
    }

    public int getTarget() {
        return this.rp;
    }

    public void release() {
    }

    public long je() {
        return this.getId();
    }

    public int jf() {
        return this.getId();
    }

    public boolean eF() {
        return this.iK;
    }

    public void s(boolean bl2) {
        this.iK = bl2;
    }

    public byte[] cd() {
        byte[] byArray = new byte[4];
        ByteBuffer.wrap(byArray).putInt(this.getId());
        return byArray;
    }

    public boolean b(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("AbstractSpell can't be unserialized. Need to be get from AbstractReferenceCoachCardManager");
    }

    public void b(byte[] byArray) {
        throw new UnsupportedOperationException("AbstractSpell can't be unserialized. Need to be get from AbstractReferenceCoachCardManager");
    }

    public short hG() {
        return 1;
    }

    public void q(short s) {
        throw new UnsupportedOperationException("Spell can't be stacked");
    }

    public void w(short s) {
        throw new UnsupportedOperationException("Spell can't be stacked");
    }

    public short jg() {
        return 1;
    }

    public boolean e(uh_1 uh_12) {
        return false;
    }

    public uh_1 G(boolean bl2) {
        try {
            return (fv)this.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException("Unable to copy AbstractSpell", cloneNotSupportedException);
        }
    }

    public uh_1 jh() {
        try {
            return (fv)this.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException("Unable to clone AbstractSpell", cloneNotSupportedException);
        }
    }

    public boolean ji() {
        return true;
    }
}

