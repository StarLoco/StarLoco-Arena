/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.BitSet;
import org.apache.log4j.Logger;

/*
 * Renamed from aHv
 */
public abstract class ahv_2
implements XV {
    protected static final Logger a = Logger.getLogger(XV.class);
    private final int beu;
    private final int aQV;
    private final agf_2 cjy;
    private final ahl_2 dMn;
    private final long dMo;
    private short dMp;
    private boolean dMq;
    protected boolean cFY = true;
    private final BitSet dMr = new BitSet();
    private final BitSet dMs = new BitSet();
    private final BitSet dMt = new BitSet();
    private final BitSet dMu = new BitSet();
    private final BitSet dMv = new BitSet();
    private final BitSet dMw = new BitSet();
    private final BitSet dMx = new BitSet();
    private boolean dMy = false;
    private boolean dMz = false;
    private boolean dMA = false;
    private boolean dMB = false;
    private boolean beL = false;
    private boolean beM = false;
    private final boolean bey;
    private boolean dMC = false;

    private void a(int[] nArray, BitSet bitSet) {
        if (nArray != null) {
            for (int n2 : nArray) {
                if (n2 <= 0) continue;
                bitSet.set(n2);
            }
        }
    }

    public ahv_2(int n2, int n3, agf_2 agf_22, int[] nArray, int[] nArray2, int[] nArray3, int[] nArray4, int[] nArray5, int[] nArray6, int[] nArray7, long l2, ahl_2 ahl_22, boolean bl2, boolean bl3, short s, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, boolean bl9) {
        this.beu = n2;
        this.aQV = n3;
        this.cjy = agf_22;
        this.dMo = l2;
        this.dMn = ahl_22;
        this.a(nArray, this.dMr);
        this.a(nArray2, this.dMs);
        this.a(nArray3, this.dMw);
        this.a(nArray4, this.dMt);
        this.a(nArray5, this.dMu);
        this.a(nArray6, this.dMv);
        this.a(nArray7, this.dMx);
        this.bey = bl2;
        this.dMy = bl3;
        this.dMA = bl4;
        this.dMB = bl6;
        this.dMz = bl5;
        this.dMp = s;
        this.dMq = bl7;
        this.beL = bl8;
        this.beM = bl9;
    }

    public boolean ako() {
        return this.dMy;
    }

    public boolean akp() {
        return this.dMz;
    }

    public boolean alH() {
        return this.dMA;
    }

    public boolean akE() {
        return this.dMB;
    }

    public boolean akr() {
        return this.dMq;
    }

    protected ahv_2(int n2, int n3, agf_2 agf_22, BitSet bitSet, BitSet bitSet2, BitSet bitSet3, BitSet bitSet4, BitSet bitSet5, BitSet bitSet6, BitSet bitSet7, long l2, ahl_2 ahl_22, boolean bl2, boolean bl3, short s, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, boolean bl9) {
        int n4;
        this.beu = n2;
        this.aQV = n3;
        this.cjy = agf_22;
        this.dMo = l2;
        this.dMn = ahl_22;
        if (bitSet != null) {
            for (n4 = 0; n4 < bitSet.size(); ++n4) {
                if (n4 <= 0 || !bitSet.get(n4)) continue;
                this.dMr.set(n4);
            }
        }
        if (bitSet2 != null) {
            for (n4 = 0; n4 < bitSet2.size(); ++n4) {
                if (n4 <= 0 || !bitSet2.get(n4)) continue;
                this.dMs.set(n4);
            }
        }
        if (bitSet3 != null) {
            for (n4 = 0; n4 < bitSet3.size(); ++n4) {
                if (n4 <= 0 || !bitSet3.get(n4)) continue;
                this.dMw.set(n4);
            }
        }
        if (bitSet4 != null) {
            for (n4 = 0; n4 < bitSet4.size(); ++n4) {
                if (n4 <= 0 || !bitSet4.get(n4)) continue;
                this.dMt.set(n4);
            }
        }
        if (bitSet5 != null) {
            for (n4 = 0; n4 < bitSet5.size(); ++n4) {
                if (n4 <= 0 || !bitSet5.get(n4)) continue;
                this.dMu.set(n4);
            }
        }
        if (bitSet6 != null) {
            for (n4 = 0; n4 < bitSet6.size(); ++n4) {
                if (n4 <= 0 || !bitSet6.get(n4)) continue;
                this.dMv.set(n4);
            }
        }
        if (bitSet7 != null) {
            for (n4 = 0; n4 < bitSet7.size(); ++n4) {
                if (n4 <= 0 || !bitSet7.get(n4)) continue;
                this.dMx.set(n4);
            }
        }
        this.bey = bl2;
        this.dMy = bl3;
        this.dMA = bl4;
        this.dMz = bl5;
        this.dMB = bl6;
        this.dMp = s;
        this.dMq = bl7;
        this.beL = bl8;
        this.beM = bl9;
    }

    public int ST() {
        return this.beu;
    }

    public int M() {
        return this.aQV;
    }

    public boolean di(long l2) {
        return (this.dMo & l2) == l2;
    }

    public ahl_2 alI() {
        return this.dMn;
    }

    public BitSet akh() {
        return this.dMr;
    }

    public BitSet aki() {
        return this.dMs;
    }

    public BitSet akm() {
        return this.dMw;
    }

    public BitSet akj() {
        return this.dMt;
    }

    public BitSet alJ() {
        return this.dMu;
    }

    public BitSet alK() {
        return this.dMv;
    }

    public boolean akn() {
        if (this.aki() != null && this.aki().length() > 0) {
            return true;
        }
        if (this.akh() != null && this.akh().length() > 0) {
            return true;
        }
        if (this.akj() != null && this.akj().length() > 0) {
            return true;
        }
        if (this.alJ() != null && this.alJ().length() > 0) {
            return true;
        }
        return this.alK() != null && this.alK().length() > 0;
    }

    public BitSet alL() {
        return this.dMx;
    }

    public afr_2 a(Pi pi, kc_2 kc_22, ea_0 ea_02, nv nv2, int n2, int n3, short s, kc_2 kc_23, avz_0 avz_02) {
        el_2 el_22 = (el_2)nv2.cr(this.M());
        return el_22.a(this, pi, ea_02, kc_22, n2, n3, s, kc_23, avz_02);
    }

    public agf_2 alM() {
        return this.cjy;
    }

    public boolean SX() {
        return this.bey;
    }

    public long getFlags() {
        return this.dMo;
    }

    public short alN() {
        return this.dMp;
    }

    public boolean aTZ() {
        return this.cFY;
    }

    public void bt(short s) {
        this.dMp = s;
    }

    protected void fa(boolean bl2) {
        this.dMC = bl2;
    }

    public boolean alO() {
        return this.dMC;
    }

    public boolean alP() {
        return !this.dMC;
    }

    public boolean Tj() {
        return this.beL;
    }

    public boolean Tk() {
        return this.beM;
    }
}

