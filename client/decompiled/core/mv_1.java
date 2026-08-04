/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/*
 * Renamed from MV
 */
public abstract class mv_1
extends nI
implements anw_2,
aej_0 {
    protected ko_2 byk;
    protected byte byl;
    protected byte bym;
    protected afj_0 byn;
    protected afj_0 byo;
    protected byte byp;
    protected ArrayList byq;
    protected int byr;
    protected int bys;
    protected jg_0 byt = new jg_0();
    protected ArrayList byu = new ArrayList();
    protected long byv;

    public void b() {
        super.b();
        this.byn = null;
        this.byo = null;
        this.bym = 0;
        this.byl = 0;
        this.byk = ko_2.bpw;
        this.djK = tz_2.b(this);
        this.byv = 26000L;
        this.byu.clear();
        this.byt.clear();
    }

    public void j() {
        super.j();
        this.byn = null;
        this.byo = null;
        this.byp = 0;
        this.bym = 0;
        this.byl = 0;
        this.byk = ko_2.bpw;
        this.byv = 26000L;
        this.byu.clear();
        this.byt.clear();
    }

    protected mv_1() {
    }

    public mv_1(byte by, int n2) {
        this.cAq = n2;
        this.byp = by;
    }

    public ko_2 Zy() {
        return this.byk;
    }

    public void i(Map map) {
    }

    public ArrayList Zz() {
        return this.byq;
    }

    public void a(afj_0 afj_02) {
        this.byo = afj_02;
    }

    public afj_0 ZA() {
        return this.byo;
    }

    public void m(ArrayList arrayList) {
        this.byq = arrayList;
    }

    public void gN(int n2) {
        this.byr = n2;
    }

    public void gO(int n2) {
        this.bys = n2;
    }

    public void gP(int n2) {
        this.byt.add(n2);
    }

    public jm_0 ZB() {
        return (jm_0)this.djH;
    }

    public byte ZC() {
        return this.byp;
    }

    public long ZD() {
        return this.byv;
    }

    public void ca(long l2) {
        this.byv = l2;
    }

    public ArrayList ZE() {
        return this.byu;
    }

    public void b(xj_0 xj_02) {
        this.byu.add(xj_02);
    }

    public void gQ(int n2) {
    }

    public void gR(int n2) {
    }

    public void b(mp_2 mp_22) {
    }

    public void ZF() {
        this.ZB().start();
        this.ZN();
    }

    public boolean ZG() {
        this.ZB().nS();
        this.byk = ko_2.bps;
        this.ZO();
        return true;
    }

    public boolean ZH() {
        this.ZB().W(Math.max(31000L, this.byv));
        this.byk = ko_2.bpt;
        this.ZP();
        return true;
    }

    public void ZI() {
        this.ZQ();
    }

    public void ZJ() {
        this.ZR();
    }

    public boolean ZK() {
        if (this.byk == ko_2.bpt) {
            this.ZB().nT();
            this.byk = ko_2.bpu;
            this.ZS();
            return true;
        }
        a.error((Object)("Erreur d\u00e9tect\u00e9 dans le match d'id " + this.aW + " : On passe en mode observation sans \u00eatre en mode placement !"));
        return false;
    }

    public void ZL() {
        this.ZT();
    }

    public boolean ZM() {
        this.byk = ko_2.bpv;
        this.ZU();
        return true;
    }

    public boolean j(gn_0 gn_02) {
        a.info((Object)("Dans le match d'id " + this.aW + ", tour n\u00b0" + this.ZB().JI() + ", nombre de fighters \u00e9gal \u00e0 " + this.ZB().nQ().size() + ", putFighterOffPlay du fighter " + gn_02 + "."));
        boolean bl2 = gn_02.PR();
        boolean bl3 = super.i(gn_02);
        if (gn_02.Qa()) {
            gn_02.bm(false);
        }
        if (gn_02.rD()) {
            gn_02.PZ().bm(true);
        }
        ArrayList<kc_2> arrayList = new ArrayList<kc_2>();
        Iterator iterator = this.aKu();
        while (iterator.hasNext()) {
            kc_2 kc_22 = (kc_2)iterator.next();
            if (kc_22.PJ() == null) continue;
            arrayList.add(kc_22);
        }
        for (int j = arrayList.size() - 1; j >= 0; --j) {
            ((kc_2)arrayList.get(j)).PJ().m(gn_02);
        }
        gn_02.PJ().clear();
        if (bl2) {
            return false;
        }
        if (0 <= this.ZB().bj(gn_02.getId())) {
            this.ZB().b(gn_02);
        }
        this.o(gn_02);
        return bl3;
    }

    public jv_1 a(gn_0 gn_02, fv fv2, ry ry2) {
        if (fv2 == null) {
            a.error((Object)("Impossible de lancer un sort dans le match d'id " + this.aW + " : Spell \u00e9gal \u00e0 null."));
            return jv_1.blr;
        }
        if (gn_02.rD()) {
            return jv_1.blD;
        }
        if (!(gn_02.Dk() || gn_02.Oh().E(fv2.je()) || fv2.jd() != null && gn_02.Oh().E(fv2.jd().getId()) || gn_02.Oj() == null || gn_02.Oj().E(fv2.je()))) {
            return jv_1.blz;
        }
        if (fv2.iR() > gn_02.a(Lr.bqy).value()) {
            return jv_1.blv;
        }
        if (ry2 != null && this.djI != null) {
            Object object;
            if (!this.djI.bH(ry2.getX(), ry2.getY())) {
                return jv_1.blt;
            }
            int n2 = Math.abs(ry2.getX() - gn_02.gg().getX()) + Math.abs(ry2.getY() - gn_02.gg().getY());
            int n3 = gn_02.d(Lr.bqT);
            byte by = fv2.iY();
            int n4 = fv2.iZ();
            if (!(n4 <= 1 || n3 >= 0 && fv2.eD())) {
                n4 = Math.max(n4 + n3, by);
            }
            if (n2 < by || n2 > n4) {
                return jv_1.blu;
            }
            if (fv2.iN() && ry2.getX() != gn_02.gg().getX() && ry2.getY() != gn_02.gg().getY()) {
                return jv_1.blB;
            }
            kc_2 kc_22 = this.q(ry2);
            gn_0 gn_03 = null;
            if (kc_22 != null) {
                gn_03 = (gn_0)this.eg(this.q(ry2).getId());
            }
            if (gn_03 != null && fv2.iX()) {
                return jv_1.blA;
            }
            if (fv2.eF()) {
                ack_1 ack_12;
                object = new aLc(fv2.jb());
                ahf_2 ahf_22 = ((aLc)object).n(ack_12 = this.r(ry2));
                if (ahf_22 == ahf_2.dMP) {
                    return jv_1.blt;
                }
                if (ahf_22 == ahf_2.dMQ && kc_22 == null) {
                    return jv_1.blt;
                }
                if (((aLc)object).a((aOf)kc_22, (aOf)gn_02).getFirst() == ahf_2.dMP || ((aLc)object).a((gn_0)kc_22, gn_02) == ahf_2.dMP) {
                    return jv_1.blC;
                }
            }
            if (!((jv_1)((Object)(object = this.a(gn_02, fv2, gn_03)))).isValid()) {
                return object;
            }
            if (fv2.iW()) {
                if (!gn_02.gg().equals(ry2)) {
                    float f = 0.8f;
                    short s = (short)((float)gn_02.PE() * 0.8f);
                    if (s > 0) {
                        ry ry3 = new ry(gn_02.gg());
                        ry ry4 = new ry(ry2);
                        ry3.T((short)(ry3.wk() + s));
                        ry4.T((short)(ry4.wk() + s));
                        ahc_2 ahc_22 = ahc_2.axo();
                        ahc_22.z(ry3);
                        ahc_22.A(ry4);
                        ahc_22.a(this.djI);
                        if (!ahc_22.axq()) {
                            ahc_22.A(ry2);
                            if (!ahc_22.axq()) {
                                ahc_22.release();
                                return jv_1.bls;
                            }
                        }
                        ahc_22.release();
                    }
                }
            } else if (!this.djI.G(ry2.getX(), ry2.getY(), ry2.wk())) {
                return jv_1.bls;
            }
        } else {
            jv_1 jv_12 = this.a(gn_02, fv2, (gn_0)null);
            if (!jv_12.isValid()) {
                return jv_12;
            }
        }
        if (gn_02.b(avx_0.dew)) {
            return jv_1.blC;
        }
        if (fv2.iL() != null) {
            for (hx hx2 : fv2.iL()) {
                if (hx2.b(gn_02, null, fv2, this)) continue;
                return jv_1.blC;
            }
        }
        return jv_1.blp;
    }

    public static boolean a(gn_0 gn_02, XV xV, gn_0 gn_03) {
        boolean bl2 = false;
        if (xV.alI() == null) {
            bl2 = true;
            return true;
        }
        pf_0 pf_02 = xV.alI().a(gn_03, gn_02);
        switch ((ahf_2)((Object)pf_02.getFirst())) {
            case dMN: 
            case dMR: {
                bl2 = true;
            }
        }
        el_2 el_22 = (el_2)mh_2.YJ().cr(xV.M());
        if (gn_03.Qa() && el_22 != null && el_22.Oz() == am_2.dr) {
            pf_0 pf_03 = xV.alI().a(gn_03.PY(), gn_02);
            switch ((ahf_2)((Object)pf_03.getFirst())) {
                case dMN: 
                case dMR: {
                    bl2 = true;
                }
            }
        }
        if (el_22 != null) {
            if (gn_03.b(avx_0.dev) && (el_22 instanceof sa_2 || el_22 instanceof na_2)) {
                bl2 = false;
            }
            if (gn_03.b(avx_0.deA) && el_22 instanceof Jk) {
                bl2 = false;
            }
            if (gn_03.b(avx_0.deB) && el_22 instanceof aox_1) {
                bl2 = false;
            }
        }
        return bl2;
    }

    public ex_0 a(gn_0 gn_02, jb_2 jb_22, ry ry2) {
        if (jb_22 == null) {
            a.error((Object)("Impossible d'utiliser une carte dans le match d'id " + this.aW + " : Card \u00e9gal \u00e0 null."));
            return ex_0.aUe;
        }
        if (gn_02.rD()) {
            return ex_0.aUn;
        }
        if (!jb_22.isUsable()) {
            return ex_0.aUe;
        }
        if (jb_22.Vo() > gn_02.a(Lr.bqy).value()) {
            return ex_0.aUh;
        }
        if (!gn_02.Oi().E(jb_22.je())) {
            return ex_0.aUj;
        }
        if (!jb_22.Vs() && gn_02.Qa()) {
            return ex_0.aUm;
        }
        if (!jb_22.Vr() && gn_02.rD()) {
            return ex_0.aUm;
        }
        if (ry2 != null) {
            int n2;
            if (!this.djI.bH(ry2.getX(), ry2.getY())) {
                return ex_0.aUg;
            }
            int n3 = gn_02.d(Lr.bqT);
            int n4 = jb_22.Az();
            if (jb_22.Az() > 1) {
                n4 = Math.max(jb_22.AA(), n4 + n3);
            }
            if ((n2 = Math.abs(ry2.getX() - gn_02.gg().getX()) + Math.abs(ry2.getY() - gn_02.gg().getY())) < jb_22.AA() || n2 > n4) {
                return ex_0.aUi;
            }
            if (jb_22.Vp() && ry2.getX() != gn_02.gg().getX() && ry2.getY() != gn_02.gg().getY()) {
                return ex_0.aUl;
            }
            kc_2 kc_22 = this.q(ry2);
            if (kc_22 != null && jb_22.Vq()) {
                return ex_0.aUk;
            }
            if (jb_22.iW()) {
                float f = 0.8f;
                short s = (short)((float)gn_02.PE() * 0.8f);
                if (s > 0) {
                    ry ry3 = new ry(gn_02.gg());
                    ry ry4 = new ry(ry2);
                    ry3.T((short)(ry3.wk() + s));
                    ry4.T((short)(ry4.wk() + s));
                    ahc_2 ahc_22 = ahc_2.axo();
                    ahc_22.z(ry3);
                    ahc_22.A(ry4);
                    ahc_22.a(this.djI);
                    if (!ahc_22.axq()) {
                        ahc_22.A(ry2);
                        if (!ahc_22.axq()) {
                            ahc_22.release();
                            return ex_0.aUf;
                        }
                    }
                    ahc_22.release();
                }
            } else if (!this.djI.G(ry2.getX(), ry2.getY(), ry2.wk())) {
                return ex_0.aUf;
            }
        }
        if (gn_02.b(avx_0.dew)) {
            return ex_0.aUm;
        }
        return ex_0.aUd;
    }

    public alt_1 a(gn_0 gn_02, ry ry2) {
        xq xq2 = gn_02.NY();
        if (xq2 == null) {
            return null;
        }
        if (gn_02.rD()) {
            return alt_1.cFS;
        }
        if (xq2.DO() > gn_02.a(Lr.bqy).value()) {
            return alt_1.cFP;
        }
        if (ry2 != null) {
            if (!this.djI.G(ry2.getX(), ry2.getY(), ry2.wk())) {
                return alt_1.cFO;
            }
            int n2 = Math.abs(ry2.getX() - gn_02.gg().getX()) + Math.abs(ry2.getY() - gn_02.gg().getY());
            if (n2 != 1) {
                return alt_1.cFQ;
            }
        }
        return alt_1.cFN;
    }

    public void a(gn_0 gn_02, kc_2 kc_22, fv fv2) {
        akv_0 akv_02;
        yd_2 yd_22;
        gn_02.PN().a(fv2, this.ZB().JI(), (aOf)kc_22);
        if (fv2.iT() > 0 && kc_22 != null) {
            yd_22 = amt_2.a(gn_02, (gn_0)this.eg(kc_22.getId()), fv2);
            akv_02 = this.ZB().a(yd_22, arm_0.lQ(1).dS(false));
            akv_02.setPosition(this.ZB().bj(gn_02.getId()));
            ((gn_0)this.eg(kc_22.getId())).PN().d(fv2);
        }
        if (fv2.et() > 0) {
            yd_22 = aoo_0.a(gn_02.getId(), gn_02.On(), fv2.getId());
            akv_02 = this.ZB().a(yd_22, arm_0.lQ(fv2.et()).dS(false));
            akv_02.setPosition(this.ZB().bj(gn_02.getId()));
            ((axD)gn_02.PH()).aKB().a(fv2.getId(), akv_02);
        }
    }

    public jv_1 a(gn_0 gn_02, fv fv2, gn_0 gn_03) {
        sH sH2 = gn_02.PN();
        short s = this.ZB().JI();
        sH2.a(fv2, s);
        if (((axD)gn_02.PH()).aKB().t(fv2.getId()) != null) {
            return jv_1.bly;
        }
        if (gn_03 != null) {
            jv_1 jv_12 = gn_03.PN().c(fv2);
            if (jv_12 != jv_1.blp) {
                return jv_12;
            }
            return sH2.a(fv2, (int)s, (aOf)gn_03);
        }
        return sH2.b(fv2, s);
    }

    public abstract boolean b(gn_0 var1, fv var2, ry var3);

    public abstract boolean b(gn_0 var1, jb_2 var2, ry var3);

    public abstract boolean b(gn_0 var1, ry var2);

    public kc_2 q(ry ry2) {
        for (kc_2 kc_22 : this.v(ry2)) {
            if (!(kc_22 instanceof gn_0) || ((gn_0)kc_22).rD()) continue;
            return kc_22;
        }
        return null;
    }

    public ack_1 r(ry ry2) {
        return this.djK.o(ry2);
    }

    public cl_1 cb(long l2) {
        return null;
    }

    public gn_0 cc(long l2) {
        return null;
    }

    public void k(gn_0 gn_02) {
    }

    public void ZN() {
    }

    public void ZO() {
    }

    public void ZP() {
    }

    public void ZQ() {
    }

    public void ZR() {
    }

    public void ZS() {
    }

    public void ZT() {
    }

    public void ZU() {
    }

    public void sE() {
        if (this.byq != null && this.byq.size() > 0) {
            this.byq.remove(0);
        }
        super.sE();
    }

    public void l(gn_0 gn_02) {
        super.f(gn_02);
    }

    public void m(gn_0 gn_02) {
        gn_02.a(Lr.bqz).aAF();
        gn_02.a(Lr.bqy).aAF();
        super.g(gn_02);
    }

    public void d(xb_2 xb_22) {
    }

    public void e(xb_2 xb_22) {
    }

    public void a(awy_0 awy_02) {
        this.ZI();
    }

    public void a(ajy_1 ajy_12) {
        this.ZJ();
    }

    public void a(abi_1 abi_12) {
        this.ZL();
    }

    public void a(ayA ayA2) {
        this.sD();
    }

    public void a(wj_1 wj_12) {
        this.sE();
    }

    public void a(als_1 als_12) {
        this.l((gn_0)this.eg(als_12.K()));
    }

    public void a(aax_0 aax_02) {
        this.ar(aax_02.K());
    }

    public void a(afj_1 afj_12) {
        xb_2 xb_22 = afj_12.TG();
        if (xb_22 != null) {
            xb_22.akv();
        }
    }

    public void a(tU tU2) {
        xb_2 xb_22 = tU2.TG();
        if (xb_22 != null && (xb_22.ajR() == null || !xb_22.ajR().PR() && !xb_22.ajR().PT())) {
            xb_22.aky();
        }
    }

    public void a(aup_0 aup_02) {
        aup_02.aHr().i(this.eg(aup_02.aHs()));
    }

    public void a(aoo_0 aoo_02) {
        ((axD)this.bc(aoo_02.On())).aKB().u(aoo_02.aCL());
    }

    public void a(amt_2 amt_22) {
        if (this.eg(amt_22.mS()) != null) {
            ((gn_0)this.eg(amt_22.mS())).PN().e(amt_22.aBJ());
        }
    }

    public void n(gn_0 gn_02) {
        if (!gn_02.PR()) {
            this.djE.a(new yo_0(this, gn_02));
        }
        super.h(gn_02);
    }
}

