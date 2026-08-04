/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Renamed from aBM
 */
public class abm_2
extends mT
implements gj_2 {
    private static final byte dsA = 0;
    private static final byte dsB = 1;
    private static final byte dsC = 2;
    protected final List dsD = new ArrayList(5);
    protected final Set dsE = new HashSet(5);
    public static final int[][] dsF = new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}, {1, 1}};
    public static final int[][] dsG = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    public static final short dsH = 4;
    public static final short dsI = 8;
    public int[][] dsJ = dsF;
    protected short dsK = (short)4;
    private zt_2 dsL = amh_2.aBP();
    private jp_1 dsM = ig_2.lE();
    private float dsN = 1.0f;
    protected arh_0 dsO;
    protected int dsP;
    protected int dsQ;
    protected static boolean dsR = false;
    public static boolean dsS = false;
    public static boolean dsT = false;
    protected aBp dsU = new aBp();
    protected static double dsV = 2.0;
    protected adh_1 dsW;
    protected agv_0 dsX;
    private int dsY;
    protected byte dsZ = (byte)8;
    protected boolean dta = true;
    private static final boolean cR = false;
    private static final akd_0[] aNf = new akd_0[32];
    private final oe_1 dtb = new oe_1(null);
    private final oe_1 dtc = new oe_1(null);

    public abm_2(long l2) {
        super(l2);
    }

    public abm_2(long l2, double d, double d2, double d3) {
        super(l2, d, d2, d3);
    }

    public abm_2(long l2, double d, double d2) {
        super(l2, d, d2);
    }

    public void aNJ() {
        this.dsO = null;
        this.dsP = -1;
        this.dsQ = -1;
        this.dsW = null;
        this.dsD.clear();
        this.dsL.d(this);
        if (this.dsM != null) {
            this.dsM.a((gj_2)this, 0);
            this.dsM.b(this);
        }
    }

    public final void a(aOV aOV2) {
        if (this.dsD.contains(aOV2)) {
            if (this.dsE.contains(aOV2)) {
                this.dsE.remove(aOV2);
            }
            return;
        }
        this.dsD.add(aOV2);
    }

    public final void b(aOV aOV2) {
        if (aOV2 != null) {
            this.dsE.add(aOV2);
        }
    }

    public final void aNK() {
        this.dsE.addAll(this.dsD);
    }

    public void if(int n2) {
        super.if(n2);
        mT mT2 = this.rB();
        if (mT2 != null) {
            mT2.if(n2);
        }
    }

    public void an(byte by) {
        super.an(by);
        mT mT2 = this.rB();
        if (mT2 != null) {
            mT2.an(by);
        }
    }

    private static void a(int n2, int n3, int n4, oe_1 oe_12) {
        if (oe_12.i(n2, n3, n4)) {
            return;
        }
        oe_12.reset();
        dc_0 dc_02 = auU.bW(n2, n3);
        if (dc_02 == null) {
            return;
        }
        int n5 = dc_02.Ls().a(n2, n3, aNf, 0);
        if (n5 == 0) {
            return;
        }
        for (int j = 0; j < n5; ++j) {
            DisplayedScreenElement displayedScreenElement;
            akd_0 akd_02 = aNf[j];
            if (akd_02.wp != n4 || (displayedScreenElement = aga_0.aSG().c(akd_02.aG, akd_02.aH, akd_02.wp, pq_2.abV)) == null) continue;
            zl_1 zl_12 = displayedScreenElement.atV().avY();
            oe_12.QG = zl_12.aol();
            oe_12.QH = oe_12.QG != 0 ? (byte)zl_12.getVisualHeight() : (byte)0;
            oe_12.setPosition(n2, n3, n4);
            break;
        }
        if (oe_12.QK == Integer.MAX_VALUE) {
            oe_12.QG = 0;
            oe_12.QH = 0;
            oe_12.setPosition(n2, n3, n4);
        }
    }

    private void aNL() {
        int n2;
        int n3;
        ArrayList arrayList;
        agv_0 agv_02 = this.dsW.v();
        super.a(agv_02.getX(), agv_02.getY(), agv_02.id());
        if (this.rB() != null) {
            this.rB().a(this.oF, this.oG, this.oH + (double)this.ge());
        }
        if ((arrayList = this.rH()) != null) {
            n3 = arrayList.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((mT)arrayList.get(n2)).a(this.oF, this.oG, this.oH);
            }
        }
        this.dsW = null;
        this.dsO = null;
        this.dsP = -1;
        this.dsQ = -1;
        this.dsM.a((gj_2)this, 0);
        this.dsL.d(this);
        this.dsM.b(this);
        this.dsD.removeAll(this.dsE);
        this.dsE.clear();
        n3 = this.dsD.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ((aOV)this.dsD.get(n2)).b(this, (int)this.oF, (int)this.oG, (short)this.oH);
        }
        this.dsD.removeAll(this.dsE);
        this.dsE.clear();
        ajh_2.b(this);
    }

    public void a(qs_2 qs_22, int n2) {
        double[] dArray;
        this.dsY += n2;
        if (this.dsW == null) {
            return;
        }
        if ((long)this.dsY >= this.dsW.s()) {
            this.aNL();
            return;
        }
        this.dsD.removeAll(this.dsE);
        this.dsE.clear();
        if (dsS) {
            this.aNO();
        }
        double d = abm_2.a(this.dsW, this.dsY);
        agv_0 agv_02 = this.dsW.a(this.dsY);
        agv_0 agv_03 = this.dsW.b(this.dsY);
        double d2 = agv_02.getX();
        double d3 = agv_02.getY();
        double d4 = agv_02.id();
        agv_0 agv_04 = agv_02.n(this.dsX);
        int n3 = (int)Math.round(d2);
        int n4 = Math.round(agv_03.getX());
        int n5 = (int)Math.round(d3);
        int n6 = Math.round(agv_03.getY());
        int n7 = (int)Math.round(d4);
        int n8 = Math.round(agv_03.id());
        n7 = this.N(n3, n5, n7);
        n8 = this.N(n4, n6, n8);
        int n9 = this.cd(n3, n5);
        this.M(n3, n5, n9);
        if (n4 != n3 || n6 != n5) {
            abm_2.a(n3, n5, n7, this.dtb);
            abm_2.a(n4, n6, n8, this.dtc);
        }
        this.dsX = new agv_0((float)this.oF, (float)this.oG, (float)this.oH);
        assert (!Double.isNaN(d2));
        assert (!Double.isNaN(d3));
        if (n3 != this.gn() || n5 != this.go() || n7 != this.gp()) {
            ajh_2.b(this);
        }
        if (!this.a(agv_02, n4, n6, dArray = new double[]{this.oH})) {
            if (this.dsO != null) {
                this.dsM.a((gj_2)this, (int)(d * (double)this.dsO.aEF()));
            }
        } else {
            this.dsQ = this.dsP;
        }
        if ((double)agv_04.aSz() != 0.0) {
            if (this.aNX() == 8) {
                this.dsM.a((gj_2)this, agv_04.aqA());
            } else {
                this.dsM.a((gj_2)this, agv_04.aqB());
            }
        }
        super.a(d2, d3, dArray[0]);
        this.aNM();
    }

    private void aNM() {
        ArrayList arrayList;
        if (this.rB() != null) {
            this.rB().a(this.oF, this.oG, this.oH + (double)this.ge());
        }
        if ((arrayList = this.rH()) != null) {
            int n2 = arrayList.size();
            for (int j = 0; j < n2; ++j) {
                ((mT)arrayList.get(j)).a(this.oF, this.oG, this.oH);
            }
        }
    }

    protected void M(int n2, int n3, int n4) {
        if (n4 > this.dsP) {
            int n5 = this.dsP;
            for (int j = this.dsP + 1; j < n4; ++j) {
                this.g(this.dsO.lU(j));
            }
            if (this.dsP != n5) {
                n4 = this.cd(n2, n3);
            }
            this.dsP = n4;
            if (dsT) {
                this.aNP();
            }
        }
    }

    private int cd(int n2, int n3) {
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        for (int j = this.dsP; j < this.dsO.aEF(); ++j) {
            int[] nArray = this.dsO.lU(j);
            int n6 = (nArray[0] - n2) * (nArray[0] - n2) + (nArray[1] - n3) * (nArray[1] - n3);
            if (n6 >= n4) continue;
            n4 = n6;
            n5 = j;
        }
        return n5;
    }

    private static double a(adh_1 adh_12, int n2) {
        return (double)(adh_12.s() - (long)n2) / (double)(adh_12.s() - adh_12.t());
    }

    private boolean a(agv_0 agv_02, int n2, int n3, double[] dArray) {
        float f;
        if (!this.dtb.initialized() || !this.dtc.initialized()) {
            return false;
        }
        float f2 = (float)n2 - agv_02.getX();
        float f3 = (float)n3 - agv_02.getY();
        float f4 = (float)(Math.sqrt(f2 * f2) + Math.sqrt(f3 * f3));
        float f5 = f = f4 > 1.0f ? 0.0f : 1.0f - f4;
        if ((double)f4 == 0.0) {
            dArray[0] = this.a(this.dtb, this.dtc, (double)f);
            return false;
        }
        boolean bl2 = this.dsM.b(this, this.dtc.QK - this.dtb.QK);
        byte by = abm_2.a(this.dtb, this.dtc, 1);
        if (bl2 && (by == 1 || by == 2)) {
            boolean bl3 = false;
            int n4 = this.dtc.QK - this.dtb.QK;
            aww aww2 = n4 >= 0 ? this.dsM.dz() : this.dsM.dy();
            db_0 db_02 = aww2.bo(f);
            switch (db_02) {
                case lL: {
                    break;
                }
                case lP: {
                    dArray[0] = this.dtc.QK;
                    break;
                }
                case lM: 
                case lN: 
                case lO: {
                    bl3 = true;
                    dArray[0] = aww2.a(this.dtb.QK, this.dtc.QK, f, db_02);
                }
            }
            if (this.dta && bl3 && by != 2) {
                this.dsM.a(this, n4, db_02);
                if ((double)f <= 0.5 && this.dsP == this.dsQ + 1) {
                    this.aTt();
                }
                return true;
            }
            return false;
        }
        if (by == 0) {
            dArray[0] = (float)this.dtb.QK + f * (float)(this.dtc.QK - this.dtb.QK);
            return false;
        }
        dArray[0] = this.a(this.dtb, this.dtc, (double)f);
        return false;
    }

    private double a(oe_1 oe_12, oe_1 oe_13, double d) {
        int n2 = oe_13.QK - oe_12.QK;
        if (n2 == 0) {
            return oe_12.QK;
        }
        return ej_0.b((double)oe_12.QK, (double)oe_13.QK, d);
    }

    private int N(int n2, int n3, int n4) {
        if (this.aNX() == 8) {
            int n5 = this.dsO.aEF();
            for (int j = this.dsP; j < n5; ++j) {
                int[] nArray = this.dsO.lU(j);
                if (nArray[0] != n2 || nArray[1] != n3) continue;
                return nArray[2];
            }
        }
        return n4;
    }

    public void aNN() {
        if (this.dsO == null) {
            return;
        }
        arh_0 arh_02 = this.dsO.bS(this.dsP, this.dsP + 2);
        this.a(arh_02, false, true);
    }

    protected void aNO() {
    }

    protected void aNP() {
    }

    private static byte a(oe_1 oe_12, oe_1 oe_13, int n2) {
        int n3 = oe_13.QI - oe_12.QI;
        int n4 = oe_13.QJ - oe_12.QJ;
        byte by = oe_12.QH;
        byte by2 = oe_13.QH;
        byte by3 = oe_12.QG;
        byte by4 = oe_13.QG;
        int n5 = oe_13.QK - oe_12.QK;
        if (n3 == 0 || n4 == 0) {
            if (n3 > 0) {
                if ((by3 & 0xC) == 12 && (by4 & 3) == 3) {
                    return 2;
                }
                double d = (by3 & 4) == 4 ? (double)by : 0.0;
                double d2 = (by3 & 8) == 8 ? (double)by : 0.0;
                double d3 = by4 == 0 || (by4 & 1) == 1 ? (double)by2 : 0.0;
                double d4 = by4 == 0 || (by4 & 2) == 2 ? (double)by2 : 0.0;
                return Math.abs(d - (d4 + (double)n5)) <= (double)Math.abs(n2) && Math.abs(d2 - (d3 + (double)n5)) <= (double)Math.abs(n2) ? (byte)0 : 1;
            }
            if (n3 < 0) {
                if ((by3 & 3) == 3 && (by4 & 0xC) == 12) {
                    return 2;
                }
                double d = (by3 & 1) == 1 ? (double)by : 0.0;
                double d5 = (by3 & 2) == 2 ? (double)by : 0.0;
                double d6 = by4 == 0 || (by4 & 8) == 8 ? (double)by2 : 0.0;
                double d7 = by4 == 0 || (by4 & 4) == 4 ? (double)by2 : 0.0;
                return Math.abs(d - (d6 + (double)n5)) <= (double)Math.abs(n2) && Math.abs(d5 - (d7 + (double)n5)) <= (double)Math.abs(n2) ? (byte)0 : 1;
            }
            if (n4 > 0) {
                if ((by3 & 9) == 9 && (by4 & 6) == 6) {
                    return 2;
                }
                double d = (by3 & 1) == 1 ? (double)by : 0.0;
                double d8 = (by3 & 8) == 8 ? (double)by : 0.0;
                double d9 = by4 == 0 || (by4 & 2) == 2 ? (double)by2 : 0.0;
                double d10 = by4 == 0 || (by4 & 4) == 4 ? (double)by2 : 0.0;
                return Math.abs(d - (d9 + (double)n5)) <= (double)Math.abs(n2) && Math.abs(d8 - (d10 + (double)n5)) <= (double)Math.abs(n2) ? (byte)0 : 1;
            }
            if ((by3 & 6) == 6 && (by4 & 9) == 9) {
                return 2;
            }
            double d = (by3 & 2) == 2 ? (double)by : 0.0;
            double d11 = (by3 & 4) == 4 ? (double)by : 0.0;
            double d12 = by4 == 0 || (by4 & 1) == 1 ? (double)by2 : 0.0;
            double d13 = by4 == 0 || (by4 & 8) == 8 ? (double)by2 : 0.0;
            return Math.abs(d11 - (d13 + (double)n5)) <= (double)Math.abs(n2) && Math.abs(d - (d12 + (double)n5)) <= (double)Math.abs(n2) ? (byte)0 : 1;
        }
        if (n3 > 0) {
            if (n4 > 0) {
                double d = (by3 & 8) == 8 ? (double)by : 0.0;
                double d14 = by4 == 0 || (by4 & 2) == 2 ? (double)by2 : 0.0;
                return Math.abs(d - (d14 + (double)n5)) <= (double)Math.abs(n2) ? (byte)0 : 1;
            }
            double d = (by3 & 4) == 4 ? (double)by : 0.0;
            double d15 = by4 == 0 || (by4 & 1) == 1 ? (double)by2 : 0.0;
            return Math.abs(d - (d15 + (double)n5)) <= (double)Math.abs(n2) ? (byte)0 : 1;
        }
        if (n4 > 0) {
            double d = (by3 & 1) == 1 ? (double)by : 0.0;
            double d16 = by4 == 0 || (by4 & 4) == 4 ? (double)by2 : 0.0;
            return Math.abs(d - (d16 + (double)n5)) <= (double)Math.abs(n2) ? (byte)0 : 1;
        }
        double d = (by3 & 2) == 2 ? (double)by : 0.0;
        double d17 = by4 == 0 || (by4 & 8) == 8 ? (double)by2 : 0.0;
        return Math.abs(d - (d17 + (double)n5)) <= (double)Math.abs(n2) ? (byte)0 : 1;
    }

    public short BP() {
        return this.dsK;
    }

    public int[][] aNQ() {
        return this.dsJ;
    }

    public void a(int[][] nArray) {
        this.dsJ = nArray;
    }

    public void ck(short s) {
        this.dsK = s;
    }

    public final arh_0 aNR() {
        return this.dsO;
    }

    private void nl(int n2) {
        jp_1 jp_12 = this.dsL.c(this, n2);
        assert (jp_12 != null);
        this.dsM = jp_12;
    }

    public final jp_1 Pr() {
        return this.dsM;
    }

    public zt_2 Pu() {
        return this.dsL;
    }

    public void Pv() {
        this.dsL.e(this);
    }

    public void a(boolean bl2, String string) {
        jp_1 jp_12 = awm_0.aJy().jU(string);
        this.a(Bb.a(bl2, this, jp_12, jp_12));
    }

    public void a(boolean bl2, String string, String string2) {
        jp_1 jp_12 = awm_0.aJy().jU(string);
        jp_1 jp_13 = awm_0.aJy().jU(string2);
        this.a(Bb.a(bl2, this, jp_12, jp_13));
    }

    public void a(zt_2 zt_22) {
        this.dsL = zt_22;
        this.nl(0);
    }

    public void bD(float f) {
        this.dsN = f;
    }

    public void a(arh_0 arh_02, boolean bl2, boolean bl3) {
        Object object;
        if (arh_02.aEF() < 2) {
            return;
        }
        if (arh_02.aEF() == 2 && arh_02.aEH()[arh_0.cQk] == arh_02.aEI()[arh_0.cQk] && arh_02.aEH()[arh_0.cQl] == arh_02.aEI()[arh_0.cQl]) {
            this.nl(0);
            return;
        }
        this.nl(arh_02.aEF());
        float f = (float)this.dsM.a(this) / this.dsN;
        Object object2 = arh_02.d((int)f, true);
        agv_0 agv_02 = new agv_0((float)this.oF, (float)this.oG, (float)this.oH);
        if (this.dsW != null) {
            object = (awf_0)((ArrayList)object2).get(0);
            ((awf_0)object).f(agv_02);
            ((awf_0)object).g(((awf_0)object).v().n(((awf_0)object).u()));
        }
        this.dsY = 0;
        if (bl3 && ((ArrayList)object2).size() > 1) {
            object = new ArrayList(((ArrayList)object2).size());
            Iterator iterator = ((ArrayList)object2).iterator();
            while (iterator.hasNext()) {
                awf_0 awf_02 = (awf_0)iterator.next();
                ((ArrayList)object).addAll(awf_02.H(dsV));
            }
            object2 = object;
            this.dsW = new l((List)object2, this.dsY);
        } else {
            this.dsW = new hl_1((List)object2, this.dsY);
        }
        this.dsX = new agv_0((float)this.oF, (float)this.oG, (float)this.oH);
        this.dsO = arh_02;
        this.dsP = 0;
        if (dsS) {
            this.dsU.a(new xq_0(this));
            this.dsU.clear();
        }
    }

    public int aNS() {
        return this.aNU();
    }

    public int aNT() {
        return this.aNV();
    }

    public int aNU() {
        if (this.dsO != null) {
            return this.dsO.lU(this.dsP)[0];
        }
        return (int)this.oF;
    }

    public int aNV() {
        if (this.dsO != null) {
            return this.dsO.lU(this.dsP)[1];
        }
        return (int)this.oG;
    }

    public short aNW() {
        if (this.rD()) {
            return (short)this.rC().getAltitude();
        }
        if (this.dsO != null) {
            return (short)this.dsO.lU(this.dsP)[2];
        }
        return (short)this.oH;
    }

    private void l(double d, double d2, double d3) {
        super.a(d, d2, d3);
    }

    public void a(double d, double d2, double d3) {
        if (this.Pr().c(this)) {
            arh_0 arh_02;
            if (this.dsO == null) {
                arh_02 = new arh_0(2);
                arh_02.b(0, (int)this.oF, (int)this.oG, (short)this.oH);
                arh_02.b(1, (int)d, (int)d2, (short)d3);
            } else {
                arh_02 = new arh_0(3);
                arh_02.b(0, (int)this.oF, (int)this.oG, (short)this.oH);
                arh_02.c(1, this.dsO.aEI());
                arh_02.b(2, (int)d, (int)d2, (short)d3);
            }
            this.a(arh_02, true, false);
            return;
        }
        super.a(d, d2, d3);
        this.aNJ();
    }

    public void be(byte by) {
        this.dsZ = by;
    }

    public byte aNX() {
        return this.dsZ;
    }

    public void eG(boolean bl2) {
        this.dta = bl2;
    }

    public boolean aNY() {
        return this.dta;
    }

    public int Pq() {
        return fl_2.rO;
    }

    public ei_0 Ps() {
        return ei_0.pe;
    }

    public ei_0 Pt() {
        return ei_0.pf;
    }

    public String Pp() {
        return this.Po();
    }

    public boolean isMoving() {
        return this.dsO != null;
    }

    protected void g(int[] nArray) {
        int[] nArray2 = new int[]{nArray[0], nArray[1], nArray[2]};
        if (this.dsO != null) {
            int n2 = this.dsO.aEF();
            boolean bl2 = false;
            for (int j = 0; j < n2; ++j) {
                int[] nArray3 = this.dsO.lU(j);
                if (nArray3[0] != nArray[0] || nArray3[1] != nArray[1]) continue;
                nArray2[2] = nArray3[2];
                bl2 = true;
                break;
            }
            if (!bl2) {
                return;
            }
        }
        if (this.Ls != null) {
            aje[] ajeArray;
            for (aje aje2 : ajeArray = this.Ls.toArray(new aje[this.Ls.size()])) {
                aje2.a(this, nArray2[arh_0.cQk], nArray2[arh_0.cQl], (short)nArray2[arh_0.cQm]);
            }
        }
        if (this.rB() != null) {
            this.rB().g(nArray2);
        }
    }

    static {
        for (int j = 0; j < aNf.length; ++j) {
            abm_2.aNf[j] = new akd_0();
        }
    }
}

