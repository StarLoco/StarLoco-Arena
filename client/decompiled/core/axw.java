/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;

public abstract class axw
extends aJj
implements JG,
aml_2,
abr_1,
aii_0,
rG {
    private static final String djw = "mainLog.fightLog";
    protected static final Logger a = Logger.getLogger((String)"mainLog.fightLog");
    public static final int djx = 300;
    public static final int djy = 301;
    public static final int djz = 305;
    public static final int djA = 306;
    private boolean djB;
    protected List djC;
    protected final cp_2 djD = new cp_2();
    protected final cp_2 djE = new cp_2();
    protected final cp_2 djF = new cp_2();
    protected final ArrayList djG = new ArrayList();
    protected cn_0 djH;
    protected aoq_0 djI;
    protected adt_2 djJ;
    protected he_1 djK;
    protected acl_0 uG;
    protected int aW;
    protected int cAq;
    public aea_0 djL = new iz_2(this, 8);
    public aea_0 djM = new iy_2(this);
    public aea_0 djN = new ix_1(this);
    public aea_0 djO = new im_2(this);
    public aea_0 djP = new il_1(this);

    public abstract long al(byte var1);

    public void b() {
        this.aW = -1;
        this.djH = null;
        this.djJ = null;
        this.a((aoq_0)null);
        this.djC = new Vector();
        this.djB = false;
        this.djG.clear();
        this.djE.clear();
        this.djF.clear();
    }

    public void j() {
        this.aW = -1;
        this.cAq = 0;
        if (this.djK != null) {
            this.djK.release();
            this.djK = null;
        }
        if (this.djC != null && this.djC.size() > 0) {
            for (JG jG : this.djC) {
                ((yg_0)jG).release();
            }
            this.djC.clear();
            this.djC = null;
        }
        this.djE.clear();
        this.djF.clear();
        if (this.djH != null && this.djH instanceof cn_1) {
            ((cn_1)((Object)this.djH)).release();
        }
        this.djH = null;
        if (this.djJ != null) {
            this.djJ.release();
        }
        this.djJ = null;
        this.a((aoq_0)null);
        this.djB = false;
        for (JG jG : this.djG) {
            jG.release();
        }
        this.djG.clear();
        this.djD.clear();
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)("Exception dans le release de " + this.getClass().toString() + " normalement impossible."), (Throwable)exception);
            }
            this.uG = null;
        } else {
            this.j();
        }
    }

    public aea_0[] Kl() {
        return new aea_0[]{this.aKe(), this.aKf(), this.aKh(), this.aKi(), this.aKg()};
    }

    public aea_0 aKe() {
        return this.djL;
    }

    public aea_0 aKf() {
        return this.djP;
    }

    public aea_0 aKg() {
        return this.djN;
    }

    public aea_0 aKh() {
        return this.djO;
    }

    public aea_0 aKi() {
        return this.djM;
    }

    public String toString() {
        return "(" + this.aW + ", " + this.cAq + ")";
    }

    public final void ab(byte[] byArray) {
        this.sF().a(this.Np(), byArray);
    }

    public void f(int n2) {
        this.aW = n2;
        if (this.sF() != null) {
            this.sF().eC(n2);
        }
    }

    public int getId() {
        return this.aW;
    }

    public Iterator aKj() {
        return new agw_2(this.djD);
    }

    public int aKk() {
        return this.djD.size();
    }

    public aoq_0 gV() {
        return this.djI;
    }

    public int aKl() {
        return this.cAq;
    }

    public void lb(int n2) {
        this.cAq = n2;
    }

    public PA sF() {
        return this.djH;
    }

    public void a(cn_0 cn_02) {
        this.djH = cn_02;
    }

    public he_1 gX() {
        return this.djK;
    }

    public int aKm() {
        return this.djC.size();
    }

    public Iterable aKn() {
        return this.djC;
    }

    public cl_1 ef(long l2) {
        return (cl_1)this.djD.t(l2);
    }

    public abstract cl_1 cb(long var1);

    public yg_0 bc(byte by) {
        for (yg_0 yg_02 : this.djC) {
            if (yg_02.lV() != by) continue;
            return yg_02;
        }
        return null;
    }

    public int amr() {
        return this.djE.size();
    }

    public Iterator aKo() {
        return new agw_2(this.djE);
    }

    public Iterator aKp() {
        return new agw_2(this.djF);
    }

    public boolean d(apx apx2) {
        return this.djE.a(apx2);
    }

    public boolean e(apx apx2) {
        return this.djF.a(apx2);
    }

    public void a(aoq_0 aoq_02) {
        this.djI = aoq_02;
    }

    public alp_0 eg(long l2) {
        alp_0 alp_02 = null;
        alp_02 = (alp_0)this.djE.t(l2);
        if (alp_02 != null) {
            return alp_02;
        }
        alp_02 = (alp_0)this.djF.t(l2);
        if (alp_02 != null) {
            return alp_02;
        }
        if (this.djC != null) {
            for (JG jG : this.djC) {
                Iterator iterator = ((yg_0)jG).amp();
                while (iterator.hasNext()) {
                    alp_0 alp_03 = (alp_0)iterator.next();
                    if (alp_03.getId() != l2) continue;
                    return alp_03;
                }
            }
        }
        for (JG jG : this.djG) {
            if (jG == null || jG.getId() != l2) continue;
            return jG;
        }
        return null;
    }

    public alp_0 eh(long l2) {
        return this.cd(l2);
    }

    public Iterator aKq() {
        return new ci_0(this.djE.eI(), this.djF.eI());
    }

    public abstract alp_0 cd(long var1);

    public boolean m(alp_0 alp_02) {
        if (alp_02 == null) {
            return false;
        }
        return this.djE.t(alp_02.getId()) == alp_02 || this.djF.t(alp_02.getId()) == alp_02;
    }

    public ea_0 Np() {
        return this.djJ;
    }

    public abstract void i(Map var1);

    public boolean g(Collection collection) {
        if (collection == null) {
            a.error((Object)("Pas d'opposant dans le match d'id " + this.aW + " : Opponents \u00e9gal \u00e0 null."));
            return false;
        }
        if (collection.size() != 2) {
            a.error((Object)("Mauvais nombre d'opposants dans le match d'id " + this.aW + " : Opponents \u00e9gal \u00e0 " + collection.size() + "."));
            return false;
        }
        for (yg_0 yg_02 : collection) {
            int n2 = yg_02.amr();
            if (n2 >= 1 && n2 <= 8) continue;
            a.error((Object)("Mauvais nombre de combattants dans l'\u00e9quipe d'id " + yg_02.lV() + " du match d'id " + this.aW + " : Count \u00e9gal " + n2 + "."));
            return false;
        }
        return true;
    }

    public boolean d(yg_0 yg_02) {
        return this.djC.size() < 2;
    }

    public abstract yg_0 asD();

    public boolean a(yg_0 yg_02, byte by) {
        if (!this.d(yg_02)) {
            return false;
        }
        yg_02.as(by);
        if (this.djC.contains(yg_02)) {
            return false;
        }
        this.djC.add(yg_02);
        Iterator iterator = yg_02.amp();
        ahf_1 ahf_12 = new ahf_1();
        while (iterator.hasNext()) {
            alp_0 alp_02 = (alp_0)iterator.next();
            this.j(alp_02);
            if (alp_02.LQ() != null) {
                alp_02.LQ().O(by);
                ahf_12.add(alp_02.LQ());
                continue;
            }
            a.error((Object)("Impossible d'ajouter un controller dans le match d'id " + this.aW + " : Controller \u00e9gal \u00e0 null."));
        }
        for (cl_1 cl_12 : ahf_12) {
            this.djD.a(cl_12.Lb(), cl_12);
            this.d(cl_12);
        }
        this.e(yg_02);
        return true;
    }

    public void j(alp_0 alp_02) {
        if (alp_02.PH() == null || alp_02.PH().xg()) {
            throw new RuntimeException("Attention ! On ajoute un fighter a un combat via 'addFighter' alors qu'il n'a pas encore d'\u00e9quipe.");
        }
        this.djE.a(alp_02.getId(), alp_02);
        if (this.r(alp_02)) {
            a.error((Object)("Impossible d'ajouter le combattant d'id " + alp_02.getId() + " dans le match d'id " + this.aW + " : D\u00e9j\u00e0 pr\u00e9sent."), (Throwable)new Exception());
        }
        this.djG.add(alp_02);
        this.t(alp_02);
    }

    public boolean c(cl_1 cl_12) {
        if (cl_12 == null) {
            return false;
        }
        if (!this.djD.v(cl_12.Lb())) {
            return false;
        }
        for (alp_0 alp_02 : cl_12.Lg()) {
            alp_02.LS();
        }
        this.djE.a(new ij_2(this, cl_12));
        this.djF.a(new ih_1(this, cl_12));
        if (!this.aKr()) {
            this.f(cl_12);
            this.djD.u(cl_12.Lb());
            this.g(cl_12);
            return true;
        }
        return false;
    }

    public boolean i(alp_0 alp_02) {
        if (alp_02 == null) {
            throw new IllegalArgumentException("appel de putFighterOffPlay avec fighter = null");
        }
        if (alp_02.PR()) {
            return false;
        }
        boolean bl2 = true;
        if (this.q(alp_02)) {
            this.djF.a(alp_02.getId(), alp_02);
        } else {
            bl2 = false;
            if (this.djE.size() > 1) {
                a.error((Object)("Echec de transition du combattant d'id " + alp_02.getId() + " de inPlay \u00e0 offPlay dans le match d'id " + this.aW + "."));
            }
        }
        alp_02.Qc();
        yg_0 yg_02 = alp_02.PH();
        if (yg_02 != null && !yg_02.xg()) {
            jo_1 jo_12 = new jo_1(this, yg_02, null);
            this.djE.a(jo_12);
            if (jo_12.Av) {
                return bl2;
            }
            this.c(yg_02);
        }
        return bl2;
    }

    public void n(alp_0 alp_02) {
        if (alp_02 == null) {
            throw new IllegalArgumentException("appel de putFighterBackInPlay avec fighter = null");
        }
        if (this.p(alp_02)) {
            this.djE.a(alp_02.getId(), alp_02);
        } else {
            a.error((Object)("Echec de transition du combattant d'id " + alp_02.getId() + " de offPlay \u00e0 inPlay dans le match d'id " + this.aW + "."));
            this.djE.a(alp_02.getId(), alp_02);
        }
        alp_02.Qd();
    }

    public boolean o(alp_0 alp_02) {
        if (alp_02 == null) {
            throw new IllegalArgumentException("appel de putFighterOutOfPlay avec fighter = null");
        }
        boolean bl2 = true;
        if (!this.p(alp_02)) {
            a.info((Object)("Echec de transition du combattant d'id " + alp_02.getId() + " de offPlay \u00e0 outPlay dans le match d'id " + this.aW + "."));
            bl2 = false;
        }
        alp_02.Qe();
        this.s(alp_02);
        return bl2;
    }

    protected boolean p(alp_0 alp_02) {
        boolean bl2 = false;
        if (alp_02 != null) {
            if (this.djF.u(alp_02.getId()) != null) {
                bl2 = true;
                this.u(alp_02);
            }
        } else {
            a.error((Object)("Impossible de retirer un combattant du match d'id " + this.aW + " : Fighter \u00e9gal \u00e0 null."));
        }
        return bl2;
    }

    protected boolean q(alp_0 alp_02) {
        boolean bl2 = false;
        if (alp_02 != null) {
            if (this.djE.size() > 1 && this.djE.u(alp_02.getId()) != null) {
                bl2 = true;
                this.h(alp_02);
            }
        } else {
            a.error((Object)("Impossible de retirer un combattant du match d'id " + this.aW + " : Fighter \u00e9gal \u00e0 null."));
        }
        return bl2;
    }

    public boolean aKr() {
        try {
            if (!this.djB) {
                return false;
            }
            avX avX2 = new avX(this, null);
            if (!this.djE.a(avX2)) {
                return false;
            }
            for (yg_0 yg_02 : avX2.dhm) {
                this.b(yg_02);
            }
            this.aKt();
        }
        catch (Exception exception) {
            a.error((Object)("V\u00e9rification de fin du match d'id " + this.aW + " : On termine de force, on loggue, mais on ne fait rien, sinon on p\u00e8te un combat."), (Throwable)exception);
        }
        return true;
    }

    public boolean aKs() {
        return this.djB;
    }

    public boolean r(alp_0 alp_02) {
        for (alp_0 alp_03 : this.djG) {
            if (alp_03 != alp_02) continue;
            return true;
        }
        return false;
    }

    public abstract void b(alp_0 var1);

    public void aKt() {
        a.info((Object)("Fin du match d'id " + this.aW + "."));
        try {
            this.sF().stop();
        }
        catch (Exception exception) {
            a.error((Object)("Fin du match d'id " + this.aW + " : Arret de la timeline."), (Throwable)exception);
        }
        try {
            this.djE.a(new iq_2(this));
        }
        catch (Exception exception) {
            a.error((Object)("Fin du match d'id " + this.aW + " : Retrait des personnages inplay."), (Throwable)exception);
        }
        try {
            this.djF.a(new io_2(this));
        }
        catch (Exception exception) {
            a.error((Object)("Fin du match d'id " + this.aW + " : Retrait des personnages offplay."), (Throwable)exception);
        }
        try {
            for (ace_2 ace_22 : this.djK.Sy()) {
                this.b((ack_1)ace_22);
            }
        }
        catch (Exception exception) {
            a.error((Object)("Fin du match d'id " + this.aW + " : Retrait des d'effets."), (Throwable)exception);
        }
        try {
            for (ace_2 ace_22 : this.djG) {
                ace_22.Ok();
                if (ace_22.LQ() == null) continue;
                ace_22.LQ().b((dv_1)((Object)ace_22));
            }
        }
        catch (Exception exception) {
            a.error((Object)("Fin du match d'id " + this.aW + " : Retrait des fighters li\u00e9s aux combats de leur controlleur."), (Throwable)exception);
        }
        try {
            this.djD.a(new ym_1(this));
        }
        catch (Exception exception) {
            a.error((Object)("Fin du match d'id " + this.aW + " : Retrait des controlleurs."), (Throwable)exception);
        }
        try {
            this.asx();
        }
        catch (Exception exception) {
            a.error((Object)("Fin du match d'id " + this.aW + " : Dispatch de l'\u00e9v\u00e8nement de fin de combat."), (Throwable)exception);
        }
        this.djK.pG();
        this.release();
    }

    protected void s(alp_0 alp_02) {
        this.djI.d(alp_02);
    }

    public Iterator aKu() {
        agw_2 agw_22 = new agw_2(this.djE);
        if (this.djK != null) {
            return new bm_0((Iterator)agw_22, this.djK.SB().iterator());
        }
        return agw_22;
    }

    public int aKv() {
        return this.djE.size() + this.djK.SA();
    }

    public kc_2 cL(long l2) {
        kc_2 kc_22 = null;
        kc_22 = (kc_2)this.djE.t(l2);
        if (kc_22 != null) {
            return kc_22;
        }
        kc_22 = (kc_2)this.djF.t(l2);
        if (kc_22 != null) {
            return kc_22;
        }
        kc_22 = this.djK.bG(l2);
        if (kc_22 != null) {
            return kc_22;
        }
        for (yg_0 yg_02 : this.aKn()) {
            Iterator iterator = yg_02.amp();
            while (iterator.hasNext()) {
                kc_22 = (kc_2)iterator.next();
                if (kc_22.getId() != l2) continue;
                return kc_22;
            }
        }
        return null;
    }

    public int aKw() {
        int n2 = 0;
        for (yg_0 yg_02 : this.aKn()) {
            n2 += yg_02.amr();
        }
        return n2;
    }

    public Iterator aKx() {
        bm_0 bm_02 = new bm_0();
        for (yg_0 yg_02 : this.aKn()) {
            bm_02.a(yg_02.amp());
        }
        return bm_02;
    }

    public Iterator aKy() {
        return new aHr();
    }

    public boolean ei(long l2) {
        return false;
    }

    public Iterator agn() {
        return new av_0(this);
    }

    public List v(ry ry2) {
        return this.D(ry2.getX(), ry2.getY(), ry2.wk());
    }

    public List D(int n2, int n3, int n4) {
        Iterator iterator = this.agn();
        ArrayList<kc_2> arrayList = new ArrayList<kc_2>();
        while (iterator.hasNext()) {
            kc_2 kc_22 = (kc_2)iterator.next();
            if (amd.a(kc_22, n2, n3) != 0) continue;
            arrayList.add(kc_22);
        }
        return arrayList;
    }

    public void d(cl_1 cl_12) {
        cl_12.f(300, this);
    }

    public void e(cl_1 cl_12) {
        cl_12.f(305, this);
    }

    public void f(cl_1 cl_12) {
        cl_12.f(306, this);
    }

    public void g(cl_1 cl_12) {
        alp_0[] alp_0Array = new alp_0[cl_12.Lf()];
        alp_0Array = cl_12.Lg().toArray(alp_0Array);
        for (int j = 0; j < alp_0Array.length; ++j) {
            alp_0 alp_02 = alp_0Array[j];
            if (alp_02.PH() != null && !alp_02.PH().xg()) {
                alp_02.PH().k(alp_02);
            }
            alp_02.Ok();
        }
        cl_12.f(301, this);
    }

    public void e(yg_0 yg_02) {
    }

    public void t(alp_0 alp_02) {
        this.djI.c(alp_02);
        alp_02.a(this);
        ArrayList<ack_1> arrayList = new ArrayList<ack_1>();
        for (ack_1 ack_12 : this.djK.SB()) {
            if (!ack_12.x(alp_02.gn(), alp_02.go(), alp_02.gp())) continue;
            arrayList.add(ack_12);
        }
        if (!arrayList.isEmpty()) {
            for (ack_1 ack_12 : arrayList) {
                ack_12.b(10001, (aOf)alp_02);
                if (!this.aKr()) continue;
                a.error((Object)("Probl\u00e8me d\u00e9tect\u00e9 sur un effet dans le match d'id " + this.aW + " : Position de BasicEffectArea = (" + ack_12.gn() + ", " + ack_12.go() + ", " + ack_12.gp() + ")"));
                return;
            }
        }
    }

    public void h(alp_0 alp_02) {
        if (this.gX() != null) {
            this.gX().e(alp_02);
        }
    }

    public void u(alp_0 alp_02) {
    }

    public void a(alp_0 alp_02, List list) {
    }

    public void v(alp_0 alp_02) {
    }

    public void aKz() {
        this.djB = true;
    }

    public void asx() {
    }

    public void b(yg_0 yg_02) {
        byte by = yg_02.lV();
        this.djD.a(new yk_1(this, by));
    }

    public void c(yg_0 yg_02) {
        byte by = yg_02.lV();
        this.djD.a(new yj_1(this, by));
    }

    public void a(ack_1 ack_12) {
        this.djI.c(ack_12);
    }

    public void b(ack_1 ack_12) {
        this.djI.d(ack_12);
        ArrayList<kc_2> arrayList = new ArrayList<kc_2>();
        Iterator iterator = this.aKu();
        while (iterator.hasNext()) {
            kc_2 kc_22 = (kc_2)iterator.next();
            if (kc_22.PJ() == null || !kc_22.Qg()) continue;
            arrayList.add(kc_22);
        }
        for (kc_2 kc_22 : arrayList) {
            kc_22.PJ().m(ack_12);
        }
    }

    public void c(ack_1 ack_12) {
        if (ack_12.PP() && ack_12.Qg()) {
            ack_12.bt(true);
            ack_12.b((kc_2)null);
            ack_12.bt(false);
        }
    }
}

