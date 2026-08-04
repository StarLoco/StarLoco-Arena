/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/*
 * Renamed from ack
 */
public abstract class ack_1
implements JG,
kc_2,
Pi,
tl_2,
ace_2 {
    protected static final Logger a = Logger.getLogger(ack_1.class);
    public static final int cju = 45;
    protected BitSet cjv;
    protected BitSet cjw;
    protected acy rd;
    protected long cjx;
    protected long nD;
    private byte baK;
    protected final ry baN = new ry();
    protected agf_2 cjy;
    protected ea_0 bdv;
    protected acl_0 uG;
    protected kc_2 cjz;
    protected int ahI;
    protected final ArrayList cjA = new ArrayList();
    protected float[] cjB;
    private rG bdu;
    protected int ahJ;
    protected float[] beD;
    private byte aIm;
    protected boolean cjC = false;
    protected boolean cjD = false;
    private boolean cjE = true;
    private boolean cjF = true;
    private Iterable cjG;
    private boolean cjH = false;
    private boolean cjI = false;

    protected ack_1() {
    }

    public int lF() {
        return 18;
    }

    public ByteBuffer aqL() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.lF());
        byteBuffer.putLong(this.nD);
        byteBuffer.putInt(this.baN.getX());
        byteBuffer.putInt(this.baN.getY());
        byteBuffer.putShort(this.baN.wk());
        return byteBuffer;
    }

    public void I(ByteBuffer byteBuffer) {
        this.nD = byteBuffer.getLong();
        this.m(byteBuffer.getInt(), byteBuffer.getInt(), byteBuffer.getShort());
    }

    public int PX() {
        return 8;
    }

    public void I(byte[] byArray) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        long l2 = byteBuffer.getLong();
        if (this.bdv != null && l2 != 0L) {
            this.cjz = this.bdv.gW().cL(l2);
        } else {
            a.error((Object)"contexte non initialis\u00e9");
        }
    }

    public byte[] PW() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.PX());
        byteBuffer.putLong(this.cjz != null ? this.cjz.getId() : 0L);
        return byteBuffer.array();
    }

    public ack_1(int n2, agf_2 agf_22, BitSet bitSet, BitSet bitSet2, int n3, int n4, float[] fArray, float[] fArray2, boolean bl2) {
        this.b();
        this.cjx = n2;
        this.cjy = agf_22;
        this.cjv = bitSet;
        this.cjw = bitSet2;
        this.ahI = n3;
        this.ahJ = n4;
        this.cjB = fArray;
        this.beD = fArray2;
        this.cjC = bl2;
    }

    public ack_1 a(Es es) {
        ack_1 ack_12 = this.hr();
        ack_12.a(this.cjy);
        ack_12.cjv = this.cjv;
        ack_12.cjw = this.cjw;
        ack_12.rd = this.rd;
        ack_12.cjx = this.cjx;
        ack_12.ahI = this.ahI;
        ack_12.cjB = this.cjB;
        ack_12.ahJ = this.ahJ;
        ack_12.beD = this.beD;
        ack_12.cjC = this.cjC;
        ack_12.cjE = this.cjE;
        if (es != null) {
            ack_12.nD = es.getId();
            ack_12.m(es.getX(), es.getY(), es.wk());
            ack_12.bdv = es.Np();
            ack_12.cjz = es.Nq();
        }
        ack_12.cjA.clear();
        return ack_12;
    }

    protected abstract ack_1 hr();

    public abstract int getType();

    public void b() {
        this.rd = new acy();
        this.nD = 0L;
        this.baN.setX(0);
        this.baN.setY(0);
        this.baN.T((short)0);
        this.cjy = null;
        this.bdv = null;
        this.cjz = null;
        this.ahI = 0;
        this.bdu = null;
        this.cjD = false;
        this.cjA.clear();
        this.cjH = false;
    }

    public void j() {
        this.rd = null;
        this.nD = 0L;
        this.baN.setX(0);
        this.baN.setY(0);
        this.baN.T((short)0);
        this.cjy = null;
        this.bdv = null;
        this.cjz = null;
        this.ahI = 0;
        this.bdu = null;
        this.cjD = false;
        this.cjA.clear();
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"impossible");
            }
            this.uG = null;
        } else {
            this.j();
        }
    }

    public long aqM() {
        return this.cjx;
    }

    public void a(rG rG2) {
        this.bdu = rG2;
    }

    public acy iK() {
        return this.rd;
    }

    public abstract boolean FF();

    public abstract boolean FH();

    public boolean a(BitSet bitSet, xb_2 xb_22, byte by) {
        if (this.PJ() != null) {
            this.PJ().a(bitSet, xb_22, by);
        }
        if (xb_22 == null) {
            return false;
        }
        switch (by) {
            case 1: {
                ry ry2;
                boolean bl2 = true;
                if (xb_22.ajO() == null && !this.baN.equals(ry2 = xb_22.ajS())) {
                    bl2 = false;
                }
                if (!bl2 || !this.a(bitSet, (aOf)xb_22.ajQ())) break;
                this.b(bitSet, (aOf)xb_22.ajQ());
                return true;
            }
        }
        return false;
    }

    public kc_2 Nq() {
        return this.cjz;
    }

    public alm_0 a(aiq_2 aiq_22) {
        return null;
    }

    public boolean b(aiq_2 aiq_22) {
        return false;
    }

    public int d(aiq_2 aiq_22) {
        alm_0 alm_02 = this.a(aiq_22);
        if (alm_02 != null) {
            return alm_02.value();
        }
        throw new UnsupportedOperationException("caract\u00e9ristique inexistante");
    }

    public qc_0 L() {
        return qc_0.bEQ;
    }

    public void b(ye_0 ye_02) {
    }

    public ye_0 Oa() {
        return null;
    }

    public void c(ye_0 ye_02) {
    }

    public abc_0 Qj() {
        return null;
    }

    public boolean PP() {
        return this.ahI == 0 && !this.cjH;
    }

    public boolean PQ() {
        return false;
    }

    public alf_1 PJ() {
        return null;
    }

    public boolean Px() {
        return true;
    }

    public long getId() {
        return this.nD;
    }

    public long iO() {
        return this.nD;
    }

    public int gn() {
        return this.baN.getX();
    }

    public int go() {
        return this.baN.getY();
    }

    public short gp() {
        return this.baN.wk();
    }

    public double getWorldX() {
        return this.baN.getX();
    }

    public double getWorldY() {
        return this.baN.getY();
    }

    public double getAltitude() {
        return this.baN.wk();
    }

    public void m(int n2, int n3, short s) {
        this.baN.setX(n2);
        this.baN.setY(n3);
        this.baN.T(s);
    }

    public final void m(ry ry2) {
        this.m(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public boolean aqN() {
        return true;
    }

    public void o(int n2, int n3, short s) {
        throw new UnsupportedOperationException("Teleport de BasicEffectArea non impl\u00e9ment\u00e9");
    }

    public void n(ry ry2) {
        throw new UnsupportedOperationException("Teleport de BasicEffectArea non impl\u00e9ment\u00e9");
    }

    public int iP() {
        return 3;
    }

    public void b(XV xV) {
        this.rd.add(xV);
    }

    public void a(XV[] xVArray) {
        this.rd.add(xVArray);
    }

    public Iterator iterator() {
        return this.rd.iterator();
    }

    public void a(agf_2 agf_22) {
        this.cjy = agf_22;
    }

    public agf_2 aqO() {
        return this.cjy;
    }

    public void a(acl_0 acl_02) {
        this.uG = acl_02;
    }

    public boolean i(ry ry2) {
        return this.x(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public boolean x(int n2, int n3, short s) {
        if (this.cjy != null) {
            if (this.cjG != null) {
                for (int[] nArray : this.cjG) {
                    if (nArray[0] != n2 || nArray[1] != n3) continue;
                    return true;
                }
                return false;
            }
            return this.cjy.a(this.gn(), this.go(), this.gp(), this.gn(), this.go(), this.gp(), n2, n3, s);
        }
        a.error((Object)"m_area est null");
        return false;
    }

    public void b(kc_2 kc_22) {
        this.j(null);
        this.Qc();
        this.cjD = true;
    }

    public void c(kc_2 kc_22) {
    }

    public boolean a(int n2, aOf aOf2) {
        BitSet bitSet = new BitSet();
        bitSet.set(n2);
        return this.a(bitSet, aOf2);
    }

    public boolean a(BitSet bitSet, aOf aOf2) {
        if (this.cjv.intersects(bitSet)) {
            return true;
        }
        return this.cjw.intersects(bitSet);
    }

    public void b(int n2, aOf aOf2) {
        BitSet bitSet = new BitSet();
        bitSet.set(n2);
        this.b(bitSet, aOf2);
    }

    public void b(BitSet bitSet, aOf aOf2) {
        if (this.cjv.intersects(bitSet)) {
            this.k(aOf2);
        }
        if (this.cjw.intersects(bitSet)) {
            this.l(aOf2);
        }
    }

    public boolean jF(int n2) {
        BitSet bitSet = new BitSet();
        bitSet.set(n2);
        return this.cjv.intersects(bitSet) || this.cjw.intersects(bitSet);
    }

    public void i(aOf aOf2) {
        if (aOf2 != null) {
            this.cjA.remove(aOf2);
        }
        this.b(aOf2);
    }

    public void j(aOf aOf2) {
        if (aOf2 != null && !this.cjA.contains(aOf2)) {
            this.cjA.add(aOf2);
        }
        this.a(aOf2);
    }

    public void cX(boolean bl2) {
        this.cjE = bl2;
    }

    protected boolean aqP() {
        return this.cjE;
    }

    public boolean k(aOf aOf2) {
        if (this.c(aOf2) && this.ahI != 0 && !this.cjH) {
            if (this.FH()) {
                this.j(aOf2);
                this.g(aOf2);
            }
            this.m(aOf2);
            if (this.aqP()) {
                if (!this.FF() && this.ahI > 0) {
                    --this.ahI;
                }
                long[] lArray = this.e(aOf2);
                this.cjH = true;
                if (lArray != null) {
                    for (int j = lArray.length - 1; j >= 0; --j) {
                        long l2 = lArray[j];
                        try {
                            this.d(wi_2.dd(l2), wi_2.de(l2), wi_2.df(l2));
                            continue;
                        }
                        catch (Exception exception) {
                            a.error((Object)"Exception levee lors de l'execution des effets d'une zone", (Throwable)exception);
                        }
                    }
                }
                this.cjH = false;
            }
            if (this.bdu != null) {
                this.bdu.c(this);
            }
            return true;
        }
        return false;
    }

    public abstract long[] e(aOf var1);

    public abstract List h(aOf var1);

    public abstract void d(int var1, int var2, short var3);

    public abstract boolean c(aOf var1);

    public abstract void g(aOf var1);

    public float jG(int n2) {
        if (this.beD == null || n2 >= this.beD.length) {
            a.error((Object)("appel d'un param\u00e8tre inexistant : " + n2));
            return -1.0f;
        }
        return this.beD[n2];
    }

    public void l(aOf aOf2) {
        for (aOf aOf3 : this.h(aOf2)) {
            if (aOf3 != null && aOf3 instanceof kc_2 && ((kc_2)aOf3).PJ() != null) {
                ((kc_2)aOf3).PJ().a(this, true);
            }
            this.n(aOf3);
        }
    }

    public boolean b(aak_2 aak_22) {
        return false;
    }

    public void d(aak_2 aak_22) {
    }

    public byte c(aak_2 aak_22) {
        return 0;
    }

    public void a(aak_2 aak_22, byte by) {
    }

    public void e(aak_2 aak_22) {
    }

    public void f(aak_2 aak_22) {
    }

    public boolean PR() {
        return this.cjD;
    }

    public void Qc() {
    }

    public boolean PS() {
        return this.PR();
    }

    public boolean PT() {
        return this.PR();
    }

    public void Qe() {
    }

    public void Qd() {
    }

    public void d(kc_2 kc_22) {
    }

    public void m(aOf aOf2) {
        this.bdu.a(this, aOf2);
    }

    public void n(aOf aOf2) {
        this.bdu.b(this, aOf2);
    }

    public void b(aOf aOf2) {
    }

    public void a(aOf aOf2) {
    }

    public void aqQ() {
    }

    public void aqR() {
    }

    public boolean aqS() {
        return false;
    }

    public byte PD() {
        return 0;
    }

    public byte ox() {
        return 0;
    }

    public boolean Qg() {
        return !this.cjI;
    }

    public void bt(boolean bl2) {
        this.cjI = bl2;
    }

    public byte On() {
        if (this.cjz != null) {
            return ((ace_2)((Object)this.cjz)).On();
        }
        return 0;
    }

    public byte Py() {
        return this.baK;
    }

    public void Y(byte by) {
        this.baK = by;
    }

    public boolean Pz() {
        return false;
    }

    public boolean PA() {
        return false;
    }

    public boolean PB() {
        return false;
    }

    public boolean aqT() {
        return this.cjC;
    }

    public void aqU() {
        int n2 = this.cjz == null ? this.baN.getX() : this.cjz.gn();
        int n3 = this.cjz == null ? this.baN.getY() : this.cjz.go();
        short s = this.cjz == null ? this.baN.wk() : this.cjz.gp();
        qc_0 qc_02 = this.cjz == null ? qc_0.bEQ : this.cjz.Qk();
        this.cjG = this.cjy.b(this.baN.getX(), this.baN.getY(), this.baN.wk(), n2, n3, s, qc_02);
    }

    public Iterable aqV() {
        return this.cjG;
    }

    public boolean aqW() {
        return this.cjF;
    }

    public void cY(boolean bl2) {
        this.cjF = bl2;
    }
}

