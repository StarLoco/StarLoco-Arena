/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from kY
 */
public class ky_2
implements afJ,
zc_2 {
    protected static final Logger a = Logger.getLogger(ky_2.class);
    public static final int OK = 0;
    public static final int FV = 1;
    public static final int FW = 2;
    protected ajv_2 FX;
    protected en_1 FY;
    private static final se_2 FZ = new se_2();
    private final lb_0 Ga = new lb_0();

    protected ky_2() {
    }

    public ky_2(ut_0 ut_02, ut_0 ut_03) {
        this.FX = new ajv_2(100, ut_02, this, false, true, true);
        this.FY = new en_1(ut_03, this, 14, false, false);
        this.FX.e(this);
    }

    public ky_2(ut_0 ut_02) {
        this(ut_02, ut_02);
    }

    public byte[] pE() {
        return this.FY.cd();
    }

    public byte[] pF() {
        return this.FX.cd();
    }

    public boolean k(byte[] byArray) {
        return this.FY.d(byArray);
    }

    public boolean l(byte[] byArray) {
        return this.FX.d(byArray);
    }

    public void a(afJ afJ2) {
        this.FY.e(afJ2);
    }

    public void b(afJ afJ2) {
        this.FX.e(afJ2);
    }

    public void c(afJ afJ2) {
        this.FY.f(afJ2);
    }

    public void d(afJ afJ2) {
        this.FX.f(afJ2);
    }

    public void removeAll() {
        this.Ga.clear();
        this.FX.hn();
        this.FY.hn();
    }

    public void pG() {
        this.Ga.clear();
        this.FX.ho();
        this.FY.ho();
    }

    public boolean a(eb_1 eb_12) {
        return this.FY.e(eb_12);
    }

    public boolean bS(int n2) {
        return this.FY.ab(n2);
    }

    public eb_1 z(short s) {
        return (eb_1)this.FY.p(s);
    }

    public boolean b(eb_1 eb_12) {
        return this.FY.b(eb_12);
    }

    public boolean A(short s) {
        return this.FY.m(s) != null;
    }

    public boolean a(eb_1 eb_12, short s) {
        return this.FY.a((akU)eb_12, s);
    }

    public int b(eb_1 eb_12, short s) {
        if (this.FY.hp() == null) {
            return 0;
        }
        return this.FY.hp().a((mi_2)this.FY, (uh_1)eb_12, s);
    }

    public en_1 pH() {
        return this.FY;
    }

    public eb_1 af(long l2) {
        return (eb_1)this.FY.F(l2);
    }

    public eb_1 bT(int n2) {
        return (eb_1)this.FY.ac(n2);
    }

    public boolean B(short s) {
        return this.FY.o(s);
    }

    public boolean c(eb_1 eb_12) {
        return this.FX.e(eb_12);
    }

    public boolean bU(int n2) {
        return this.FX.ab(n2);
    }

    public void bV(int n2) {
        for (eb_1 eb_12 : this.FX.ad(n2)) {
            this.FX.b(eb_12);
        }
    }

    public boolean d(eb_1 eb_12) {
        return this.FX.b(eb_12);
    }

    public boolean e(eb_1 eb_12) {
        return this.FX.c(eb_12);
    }

    public eb_1 ag(long l2) {
        return (eb_1)this.FX.C(l2);
    }

    public boolean ah(long l2) {
        return this.FX.D(l2);
    }

    public boolean f(eb_1 eb_12) {
        if (eb_12 == null) {
            a.error((Object)"Impossible d'ajouter une carte nulle \u00e0 un inventaire.");
            return false;
        }
        if (this.a((mi_2)this.FX, eb_12) == 0) {
            return this.FX.a(eb_12);
        }
        return false;
    }

    public ajv_2 pI() {
        return this.FX;
    }

    public eb_1 ai(long l2) {
        return (eb_1)this.FX.F(l2);
    }

    public eb_1 bW(int n2) {
        return (eb_1)this.FX.ac(n2);
    }

    public boolean f(long l2, short s) {
        return this.FX.d(l2, s);
    }

    public void a(wl_1 wl_12) {
        switch (wl_12.Dh()) {
            case cqh: {
                this.Ga.clear();
                break;
            }
            case cqa: 
            case cqb: {
                eb_1 eb_12 = (eb_1)((auA)wl_12).aHD();
                aqy_0 aqy_02 = eb_12.NR().tn();
                if (aqy_02 == null) break;
                se_2 se_22 = (se_2)this.Ga.get(aqy_02.getId());
                if (se_22 == null) {
                    se_22 = new se_2();
                    this.Ga.c(aqy_02.getId(), se_22);
                }
                se_22.add(eb_12.je());
                break;
            }
            case cqc: 
            case cqd: {
                se_2 se_23;
                eb_1 eb_13 = (eb_1)((auA)wl_12).aHD();
                aqy_0 aqy_03 = eb_13.NR().tn();
                if (aqy_03 == null || (se_23 = (se_2)this.Ga.get(aqy_03.getId())) == null) break;
                se_23.aI(eb_13.je());
                break;
            }
        }
    }

    public int a(mi_2 mi_22, eb_1 eb_12) {
        if (eb_12.NR().isUnique() && (this.FX.ab(eb_12.jf()) || this.FY.ab(eb_12.jf()))) {
            return 2;
        }
        return 0;
    }

    public int a(mi_2 mi_22, eb_1 eb_12, short s) {
        if (mi_22 == this.FY) {
            aMK aMK2 = eb_12.NR().tj();
            if (aMK2 == null) {
                a.error((Object)("Type de carte inconnu pour la carte " + eb_12.jf()));
                return 0;
            }
            short[] sArray = aMK2.aXg();
            boolean bl2 = false;
            if (sArray != null) {
                for (int j = sArray.length - 1; j >= 0; --j) {
                    if (sArray[j] != s) continue;
                    bl2 = true;
                    break;
                }
            }
            if (!bl2) {
                return 1;
            }
        }
        return 0;
    }

    public int a(mi_2 mi_22, eb_1 eb_12, int n2) {
        throw new UnsupportedOperationException("Pas de position en Int pour cet inventaire, Utiliser une position en short");
    }

    public int a(mi_2 mi_22, eb_1 eb_12, eb_1 eb_13) {
        return 0;
    }

    public int b(mi_2 mi_22, eb_1 eb_12) {
        return 0;
    }

    public se_2 bX(int n2) {
        se_2 se_22 = (se_2)this.Ga.get(n2);
        return se_22 != null ? se_22 : FZ;
    }

    public boolean a(eb_1 eb_12, kc_2 kc_22, ea_0 ea_02) {
        return true;
    }

    public boolean a(mi_2 mi_22, kc_2 kc_22, ea_0 ea_02) {
        return true;
    }
}

