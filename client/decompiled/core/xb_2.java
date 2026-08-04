/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/*
 * Renamed from Xb
 */
public abstract class xb_2
extends aJj
implements el_2,
cn_1,
eU {
    private static final byte bWh = 3;
    protected static final Logger a = Logger.getLogger(xb_2.class);
    protected acl_0 uG;
    private byte bWi;
    protected XV bWj;
    protected Pi bWk;
    protected kc_2 bWl;
    protected kc_2 bWm;
    protected final ry bWn = new ry();
    private avz_0 bWo;
    private alf_1 bWp = null;
    protected ea_0 bdv;
    protected int aW;
    protected long aFL;
    protected long bWq;
    protected am_2 bWr;
    protected int r;
    protected EnumSet bWs;
    protected final BitSet bWt = new BitSet();
    protected int ahI;
    private xb_2 bWu;
    protected boolean bWv = true;
    private boolean bWw;
    private boolean bWx;
    private static byte bWy;
    private static final byte bWz = 25;
    protected nc_2 bWA;
    private static final boolean cR = false;
    private boolean bWB = false;
    private static long bWC;
    private static aes_2 bWD;
    protected boolean bWE = false;
    protected afr_2 bWF;
    private boolean bWG = false;
    protected boolean bWH = false;
    private static boolean bWI;
    private boolean bWJ = false;
    public aea_0 bWK = new yi_1(this, 34);
    public aea_0 bWL = new Yk(this, 8);
    public aea_0 nw = new yl_2(this, 8);

    public static void ajM() {
        bWy = 0;
    }

    public static void a(aes_2 aes_22) {
        bWD = aes_22;
    }

    private static long ahT() {
        if (bWC < Long.MAX_VALUE) {
            return bWC++;
        }
        bWC = 0L;
        return bWC;
    }

    protected xb_2() {
        this.akb();
        this.bWB = false;
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public long je() {
        return this.aFL;
    }

    public long ajN() {
        return this.bWq;
    }

    public void dh(long l2) {
        this.aFL = l2;
    }

    public am_2 Oz() {
        return this.bWr;
    }

    public void a(am_2 am_22) {
        this.bWr = am_22;
    }

    public XV ajO() {
        return this.bWj;
    }

    public void a(XV xV) {
        this.bWj = xV;
    }

    public void ajP() {
        if (bWI) {
            this.bWF = new afr_2();
        }
        this.bWH = true;
    }

    public kc_2 ajQ() {
        return this.bWl;
    }

    public void g(kc_2 kc_22) {
        this.bWl = kc_22;
    }

    public kc_2 ajR() {
        return this.bWm;
    }

    public ry ajS() {
        return this.bWn;
    }

    public Pi mi() {
        return this.bWk;
    }

    public void h(kc_2 kc_22) {
        this.bWm = kc_22;
    }

    public void w(int n2, int n3, short s) {
        this.bWn.l(n2, n3, s);
    }

    public avz_0 ajT() {
        return this.bWo;
    }

    public void a(Pi pi) {
        this.bWk = pi;
    }

    public void b(ea_0 ea_02) {
        this.bdv = ea_02;
    }

    public ea_0 Np() {
        return this.bdv;
    }

    public int getValue() {
        return this.r;
    }

    public void cz(boolean bl2) {
        this.bWx = bl2;
    }

    public void iQ(int n2) {
        this.r = n2;
    }

    public boolean gI() {
        return false;
    }

    public void release() {
        if (this.bWi > 0) {
            a.error((Object)("On essaye de release un RunningEffect encore r\u00e9f\u00e9renc\u00e9 refCount=" + this.bWi + " id=" + this.aW));
            return;
        }
        if (this.bWB && this.ajU()) {
            a.error((Object)("Double release sur un " + this.getClass().getSimpleName() + " hashCode : " + this.hashCode() + " : " + bl_0.dH()));
            this.j();
            return;
        }
        if (!this.bWB) {
            this.j();
            return;
        }
        this.ajV();
    }

    public boolean ajU() {
        return this.uG == null;
    }

    private void ajV() {
        try {
            this.uG.af(this);
        }
        catch (Exception exception) {
            a.error((Object)("Exception dans le release de " + this.getClass().toString() + " normalement impossible"));
        }
        this.uG = null;
    }

    public List ajW() {
        return this.a(this.bWj, this.bWl, this.bdv, this.bWn.getX(), this.bWn.getY(), this.bWn.wk());
    }

    public static void cA(boolean bl2) {
        bWI = bl2;
    }

    public void ajX() {
        if (this.bWF != null) {
            this.bWF.clear();
        }
    }

    public afr_2 a(XV xV, Pi pi, ea_0 ea_02, kc_2 kc_22, int n2, int n3, short s, kc_2 kc_23, avz_0 avz_02) {
        Object object;
        if (bWI) {
            if (this.bWF != null) {
                this.bWF.clear();
            } else {
                this.bWF = afr_2.avH();
                this.bWG = true;
            }
        }
        bWy = 0;
        this.a(xV, pi, ea_02, kc_22, null, n2, n3, s, avz_02);
        if (this.aJ() && !this.aI() && (object = this.aka()) != null) {
            if (((xb_2)object).akF() && !((xb_2)object).isInfinite()) {
                ((xb_2)object).akB();
            }
            if (((xb_2)object).akf()) {
                if (((xb_2)object).aI() && this.bWm == null) {
                    a.warn((Object)(" on veut calculer un effet qui a besoin d'une cible, sans cible : " + this.akx()));
                }
                if (((xb_2)object).aH() && this.bWl == null) {
                    a.warn((Object)("on veut calculer un effet qui a besoin d'un caster, sans caster : " + this.akx()));
                }
                if (((xb_2)object).aJ() && this.bWn == null) {
                    a.warn((Object)("on veut calculer un effet qui a besoin d'une cellule cible, sans cellule cible : " + this.akx()));
                }
                ((xb_2)object).a((xb_2)null);
            }
            if (this.bWl != null && this.bWl.PJ() != null) {
                if (((xb_2)object).isInfinite() || ((xb_2)object).akF()) {
                    this.bWl.PJ().o((xb_2)object);
                }
            } else {
                ((xb_2)object).akw();
            }
            ((xb_2)object).akv();
        }
        if (this.aI()) {
            if (kc_23 == null) {
                object = this.a(xV, kc_22, ea_02, n2, n3, s);
                Iterator iterator = object.iterator();
                while (iterator.hasNext()) {
                    List list = (List)iterator.next();
                    this.l(list);
                    if (!bWI || this.bWF == null) continue;
                    this.bWF.f(list);
                }
            } else {
                this.i(kc_23);
                if (bWI && this.bWF != null) {
                    this.bWF.j(kc_23);
                }
            }
        }
        if (this.bWH) {
            this.clearParameters();
        }
        if (bWI) {
            return this.bWF;
        }
        return null;
    }

    public void a(XV xV, Pi pi, ea_0 ea_02, kc_2 kc_22, kc_2 kc_23, int n2, int n3, short s, avz_0 avz_02) {
        this.bWj = xV;
        this.bWk = pi;
        this.bWl = kc_22;
        this.bdv = ea_02;
        this.bWm = kc_23;
        this.w(n2, n3, s);
        if (this.bWo != null) {
            this.bWo.release();
        }
        this.bWo = avz_02 != null ? avz_02.aJm() : null;
        this.ajY();
    }

    public void a(avz_0 avz_02) {
        if (this.bWo != null) {
            this.bWo.release();
        }
        this.bWo = avz_02;
    }

    protected void ajY() {
        if (this.bWj != null) {
            this.ahI = this.bWj.alN();
        }
    }

    protected void clearParameters() {
        this.bWj = null;
        this.bWk = null;
        this.bWl = null;
        this.bdv = null;
        this.bWm = null;
        if (this.bWo != null) {
            this.bWo.release();
        }
        this.bWo = null;
    }

    public xb_2 ajZ() {
        return this.bWu;
    }

    public void f(xb_2 xb_22) {
        this.bWu = xb_22;
    }

    public xb_2 a(ea_0 ea_02, aav_2 aav_22) {
        xb_2 xb_22 = this.aka();
        xb_22.f(this.getId());
        xb_22.b(ea_02);
        xb_22.a(this.Oz());
        return xb_22;
    }

    public xb_2 aka() {
        xb_2 xb_22 = this.aN();
        xb_22.aFL = bWD != null ? bWD.n(xb_22) : xb_2.ahT();
        xb_22.g(this);
        xb_22.bWq = xb_22.aFL;
        return xb_22;
    }

    public abstract xb_2 aN();

    public void b() {
        this.akb();
        this.bWB = true;
    }

    private void akb() {
        this.bWw = false;
        this.bWx = true;
        this.bWp = null;
        this.bWi = 0;
        this.ahI = -1;
        this.bWl = null;
        this.bWm = null;
        this.bdv = null;
        this.bWj = null;
        this.bWk = null;
        this.bWv = true;
        this.bWt.clear();
        this.bWH = false;
        this.bWF = null;
        this.aFL = -1L;
        this.bWq = -1L;
        this.bWG = false;
        this.bWA = null;
    }

    public void j() {
        this.bWJ = false;
        this.akb();
        this.bWn.l(Integer.MIN_VALUE, Integer.MIN_VALUE, (short)Short.MIN_VALUE);
        if (this.bWo != null) {
            this.bWo.release();
        }
        this.bWo = null;
        this.r = 0;
        this.bWu = null;
        this.bWE = false;
        if (bWI && this.bWF != null && this.bWG) {
            this.bWF.release();
        }
    }

    protected void g(xb_2 xb_22) {
        this.bWJ = xb_22.bWJ;
        this.bWj = xb_22.bWj;
        this.bWk = xb_22.bWk;
        this.bWl = xb_22.bWl;
        this.bWm = xb_22.bWm;
        this.bWn.g(xb_22.bWn);
        if (this.bWo != null) {
            this.bWo.release();
        }
        this.bWo = xb_22.bWo != null ? xb_22.bWo.aJm() : null;
        this.bdv = xb_22.bdv;
        this.aW = xb_22.aW;
        this.bWr = xb_22.bWr;
        this.r = xb_22.r;
        this.bWt.clear();
        this.bWt.or(xb_22.bWt);
        this.ahI = xb_22.ahI;
        this.bWu = xb_22.bWu;
        this.bWE = false;
        this.bWF = xb_22.bWF;
        this.bWv = xb_22.bWv;
    }

    public alf_1 akc() {
        return this.bWp;
    }

    public void a(alf_1 alf_12) {
        this.bWp = alf_12;
    }

    public void akd() {
        this.bWv = false;
    }

    public void ake() {
        this.bWv = true;
    }

    public boolean akf() {
        return this.bWv;
    }

    public afr_2 akg() {
        if (!bWI) {
            return null;
        }
        return this.bWF;
    }

    public BitSet akh() {
        if (this.bWj == null) {
            return null;
        }
        return this.bWj.akh();
    }

    public BitSet aki() {
        if (this.bWj == null) {
            return null;
        }
        return this.bWj.aki();
    }

    public BitSet akj() {
        if (this.bWj == null) {
            return null;
        }
        return this.bWj.akj();
    }

    public BitSet akk() {
        if (this.bWj == null) {
            return null;
        }
        return this.bWj.alJ();
    }

    public BitSet akl() {
        if (this.bWj == null) {
            return null;
        }
        return this.bWj.alK();
    }

    public void HE() {
        this.bWi = (byte)(this.bWi + 1);
    }

    public void HF() {
        if (this.bWi > 0) {
            this.bWi = (byte)(this.bWi - 1);
        } else {
            a.error((Object)"ON TENTE DE RETIRER PLUS DE REFERENCE QU'IL N'Y EN A SUR UN RUNNINGEFFECT");
        }
    }

    public BitSet akm() {
        if (this.bWj == null) {
            return null;
        }
        return this.bWj.akm();
    }

    public BitSet eL() {
        if (this.bWj != null) {
            this.bWt.or(this.bWj.alL());
        }
        return this.bWt;
    }

    public void aG() {
        this.bWt.clear();
        this.bWt.set(0);
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
        if (this.akk() != null && this.akk().length() > 0) {
            return true;
        }
        return this.akl() != null && this.akl().length() > 0;
    }

    public abstract boolean aH();

    public abstract boolean aI();

    public abstract boolean aJ();

    public void a(int n2, float f, boolean bl2) {
    }

    public boolean ako() {
        if (this.bWj == null) {
            return false;
        }
        return this.bWj.ako();
    }

    public boolean akp() {
        if (this.bWj == null) {
            return false;
        }
        return this.bWj.akp();
    }

    public void Q(byte ... byArray) {
        if (this.eL() == null || this.akr()) {
            return;
        }
        for (byte by : byArray) {
            this.ar(by);
        }
    }

    public void akq() {
        this.bWJ = true;
    }

    public boolean akr() {
        return this.bWJ || this.bWj != null && this.bWj.akr();
    }

    public void ar(byte by) {
        if (this.eL() == null || this.akr()) {
            return;
        }
        switch (by) {
            case 1: {
                ArrayList<kc_2> arrayList = new ArrayList<kc_2>();
                while (this.ajQ() != null && !arrayList.contains(this.ajQ())) {
                    arrayList.add(this.ajQ());
                    this.ajQ().a(this.eL(), this, (byte)10);
                }
                arrayList.clear();
                while (this.ajR() != null && !arrayList.contains(this.ajR())) {
                    arrayList.add(this.ajR());
                    this.ajR().a(this.eL(), this, (byte)1);
                }
                break;
            }
            case 2: {
                if (this.ajQ() != null) {
                    this.ajQ().a(this.eL(), this, (byte)20);
                }
                if (this.ajR() == null) break;
                this.ajR().a(this.eL(), this, (byte)2);
                break;
            }
            case 3: {
                if (this.ajQ() != null) {
                    this.ajQ().a(this.eL(), this, (byte)30);
                }
                if (this.ajR() == null) break;
                this.ajR().a(this.eL(), this, (byte)3);
                break;
            }
            case 4: {
                if (this.ajQ() != null) {
                    this.ajQ().a(this.eL(), this, (byte)40);
                }
                if (this.ajR() == null) break;
                this.ajR().a(this.eL(), this, (byte)4);
                break;
            }
        }
    }

    public void aks() {
        if (this.bdv != null && this.bdv.gS() != null) {
            this.bdv.gS().d(this);
        }
    }

    public void akt() {
        if (this.bdv != null && this.bdv.gS() != null) {
            this.bdv.gS().e(this);
        }
    }

    public void i(kc_2 kc_22) {
        if (kc_22 == null) {
            return;
        }
        this.l(Collections.singletonList(kc_22));
    }

    public boolean l(List list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        boolean bl2 = false;
        if ((bWy = (byte)(bWy + 1)) > 25) {
            a.error((Object)"boucle infinie pour un runningEffect ?", (Throwable)new NullPointerException("erreur g\u00e9n\u00e9r\u00e9e pour \u00e9tude de stack"));
            return false;
        }
        ArrayList<xb_2> arrayList = new ArrayList<xb_2>();
        ArrayList<xb_2> arrayList2 = new ArrayList<xb_2>();
        for (kc_2 object : list) {
            xb_2 xb_22 = this.aka();
            xb_22.h(object);
            if (this.bdv != null && this.bdv.gU() != null) {
                xb_22.bWA = this.bdv.gU().JM();
            }
            if (!xb_22.akn()) {
                if (object != null) {
                    xb_22.ar((byte)1);
                }
                if (this.akf() && xb_22.akf()) {
                    if (this.aH() && xb_22.ajQ() == null) {
                        a.warn((Object)("on veut calculer un effet qui a besoin d'un caster, sans caster " + this.getId() + (this.bWj != null ? " generic effect " + this.bWj.ST() + "action " + this.bWj.M() : "")));
                    }
                    if (this.aJ() && xb_22.ajS() == null) {
                        a.warn((Object)("on veut calculer un effet qui a besoin d'une cellule cible, sans cellule cible" + this.getId() + (this.bWj != null ? " generic effect" + this.bWj.ST() + " action " + this.bWj.M() : "")));
                    }
                    xb_22.a((xb_2)null);
                }
                bl2 = true;
                arrayList.add(xb_22);
            } else if (xb_22.akF() && !xb_22.isInfinite()) {
                bl2 = true;
                xb_22.akB();
            }
            if (!xb_22.akF() && !xb_22.akD()) continue;
            if (this.akE()) {
                if (xb_22.ajQ() != null && xb_22.ajQ().PJ() != null) {
                    xb_22.ajQ().PJ().o(xb_22);
                    xb_22.aks();
                    continue;
                }
                arrayList2.add(xb_22);
                continue;
            }
            if (xb_22.ajR() != null && xb_22.ajR().PJ() != null) {
                xb_22.ajR().PJ().o(xb_22);
                xb_22.aks();
                continue;
            }
            arrayList2.add(xb_22);
        }
        for (xb_2 xb_23 : arrayList) {
            if (xb_23.akF() && !xb_23.isInfinite()) {
                xb_23.akB();
            }
            if (!xb_23.akD()) {
                xb_23.akv();
                continue;
            }
            xb_23.akA();
        }
        for (xb_2 xb_24 : arrayList2) {
            xb_24.release();
        }
        return bl2;
    }

    public abstract boolean aku();

    public boolean h(xb_2 xb_22) {
        return this.bWx;
    }

    public void akv() {
        if (this.aku()) {
            if (this.ajR() != null && this.ajR().PT()) {
                this.cB(false);
                return;
            }
            this.ar((byte)2);
            if (this.ajR() != null && this.ajR().PT()) {
                this.cB(false);
                return;
            }
            this.a(this.ajZ(), false);
            return;
        }
        this.cB(false);
    }

    public void akw() {
        this.bWE = true;
    }

    public final void i(xb_2 xb_22) {
        if (this.h(xb_22)) {
            xb_2 xb_23 = this.aka();
            xb_23.f(this);
            xb_23.bWq = this.je();
            if (xb_23.ajO() != null && xb_23.ajO().Tj() && xb_23.akF()) {
                xb_23.akB();
                if (xb_23.ajR() != null && xb_23.ajR().PJ() != null) {
                    xb_23.ajR().PJ().o(xb_23);
                }
                xb_23.cz(false);
            } else {
                xb_23.akw();
            }
            if (xb_22 != null) {
                Object object;
                xb_23.h(this.j(xb_22));
                if (xb_23.ajR() != null) {
                    object = xb_23.ajR();
                    xb_23.w(object.gn(), object.go(), object.gp());
                } else {
                    object = xb_22.ajS();
                    xb_23.w(((ry)object).getX(), ((ry)object).getY(), ((ry)object).wk());
                }
            }
            if (xb_23.aI() && xb_23.ajR() == null || xb_23.aH() && xb_23.ajQ() == null || xb_23.ajR() != null && xb_23.ajR().PT() || xb_23.aJ() && xb_23.ajS() == null) {
                if (xb_23.aI() && xb_23.ajR() == null) {
                    a.error((Object)("on veut executer un effet qui a besoin d'une cible, sans cible (action=" + xb_23.akx() + ")"));
                }
                if (xb_23.aH() && xb_23.ajQ() == null) {
                    a.error((Object)("on veut executer un effet qui a besoin d'un caster, sans caster\t(action = " + xb_23.akx() + ")"));
                }
                if (xb_23.aJ() && xb_23.ajS() == null) {
                    a.error((Object)("on veut executer un effet qui a besoin d'une cellule cible, sans cellule cible (action=" + xb_23.akx() + ")"));
                }
                xb_23.release();
                return;
            }
            if (xb_23.akf()) {
                xb_23.a(xb_22);
            }
            xb_23.Q(1, 2);
            long l2 = this.je();
            xb_23.a(xb_22, true);
            if (this.je() == l2) {
                this.cB(true);
            }
        }
    }

    protected String akx() {
        return this.bWj == null ? "null" : "Act#" + this.bWj.M() + "@Eff#" + this.bWj.ST();
    }

    protected kc_2 j(xb_2 xb_22) {
        if (xb_22 == null || this.bWj == null || this.akp()) {
            return this.bWm;
        }
        if (this.ako()) {
            if (this.bWj.alH()) {
                return xb_22.ajQ();
            }
            return xb_22.ajR();
        }
        if (this.bWj.alH()) {
            return xb_22.ajR();
        }
        return xb_22.ajQ();
    }

    public void aky() {
        if (this.bWp != null) {
            this.bWp.p(this);
        } else {
            this.aK();
        }
    }

    public boolean akz() {
        return this.bWw;
    }

    public void aK() {
        this.akH();
        this.akt();
        this.bWw = true;
        if (!this.bWH) {
            this.release();
        }
    }

    public abstract void akA();

    public abstract void akB();

    public nc_2 akC() {
        return this.bWA;
    }

    public abstract akv_0 aex();

    public abstract boolean akD();

    public boolean akE() {
        return this.bWj != null && this.bWj.akE();
    }

    public boolean akF() {
        if (this.bWE) {
            return false;
        }
        return this.akn();
    }

    public abstract boolean isInfinite();

    public boolean akG() {
        return this.bWB;
    }

    protected void a(xb_2 xb_22, boolean bl2) {
        this.akH();
        if (bWI && this.bWF != null) {
            this.bWF.a(this.eL());
            this.bWF.avI();
        }
        this.Q(3, 4);
        this.cB(true);
    }

    public void cB(boolean bl2) {
        boolean bl3 = false;
        if (bl2 && this.ahI >= 0) {
            if (this.ahI > 0) {
                --this.ahI;
            }
            if (this.ahI == 0) {
                bl3 = true;
            }
        }
        if (!this.akF() || bl3) {
            this.aky();
        }
    }

    protected void akH() {
        for (int n2 = 0; this.akI() && n2 < 3; n2 = (int)((byte)(n2 + 1))) {
        }
    }

    protected boolean akI() {
        boolean bl2 = false;
        if (this.bWm != null && this.bWm.PP() && !this.bWm.PR() && this.bWm.Qg()) {
            this.bWm.bt(true);
            bl2 = true;
            this.bWm.b(this.bWl);
            if (this.bWm != null) {
                this.bWm.bt(false);
            }
        }
        if (this.bWl != null && this.bWl.PP() && !this.bWl.PR() && this.bWl.Qg()) {
            this.bWl.bt(true);
            bl2 = true;
            this.bWl.b(this.bWl);
            if (this.bWl != null) {
                this.bWl.bt(false);
            }
        }
        return bl2;
    }

    public abstract void a(xb_2 var1);

    public boolean k(xb_2 xb_22) {
        return false;
    }

    public byte[] akJ() {
        return this.a(this.bWK, this.akL(), this.gN(), this.akM(), this.akN());
    }

    public byte[] cd() {
        return this.a(this.bWK, this.akL(), this.gH(), this.gN(), this.akM(), this.akN());
    }

    public byte[] akK() {
        return this.a(this.bWK, this.gN(), this.akM(), this.akN());
    }

    public aea_0[] Kl() {
        return new aea_0[]{this.bWK, this.akL(), this.gH(), this.gN(), this.akM(), this.akN()};
    }

    public aea_0 akL() {
        return this.bWL;
    }

    public aea_0 gH() {
        return this.nw;
    }

    public aea_0 gN() {
        return aea_0.dBr;
    }

    public aea_0 akM() {
        return aea_0.dBr;
    }

    public aea_0 akN() {
        return aea_0.dBr;
    }

    public abstract XV akO();

    public boolean isPersistent() {
        return false;
    }

    static {
        bWC = 0L;
        bWD = null;
        bWI = false;
    }
}

