/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from jM
 */
public abstract class jm_0
extends cn_0
implements alx_0,
cn_1 {
    private aml_2 CC;
    protected aam_1 CD = aam_1.aMF();
    protected long CE = -1L;
    protected long CF = -1L;
    protected ym_0 rI;
    public lf CG;

    protected jm_0() {
        super(null);
    }

    public void b(OZ oZ) {
        this.aLp = oZ;
    }

    protected void a(aml_2 aml_22) {
        this.CC = aml_22;
        this.a(new zg_2(new act_0(aml_22)));
    }

    protected void b(aml_2 aml_22) {
        this.CC = aml_22;
        this.a(new zg_2(new aih()));
    }

    protected gn_0 V(long l2) {
        return (gn_0)this.CC.ej(l2);
    }

    public gn_0 nP() {
        if (!this.du()) {
            return null;
        }
        return this.V(this.JG());
    }

    public void b() {
        this.CG = new lf();
    }

    public void j() {
        this.aLo.clear();
        this.aLp = null;
        this.stop();
        this.NC = 0;
        this.aLr = 0;
        this.aLs = anz_2.dZu;
        this.aFf = 0;
        if (this.CF > 0L) {
            aam_1.aMF().en(this.CF);
            this.CF = 0L;
        }
        if (this.CE > 0L) {
            aam_1.aMF().en(this.CE);
            this.CE = 0L;
        }
        this.CG = null;
    }

    public void release() {
        if (this.rI != null) {
            try {
                this.rI.af(this);
            }
            catch (Exception exception) {
                a.warn((Object)("Erreur de release sur un " + this.getClass().getSimpleName()));
            }
            this.rI = null;
        } else {
            this.j();
        }
    }

    public ArrayList nQ() {
        ArrayList<gn_0> arrayList = new ArrayList<gn_0>();
        qa_2 qa_22 = this.CA();
        int n2 = qa_22.size();
        for (int j = 0; j < n2; ++j) {
            arrayList.add(this.V(qa_22.get(j)));
        }
        return arrayList;
    }

    public void a(atD atD2) {
        this.CG.a(atD2, true);
    }

    public void nR() {
        for (atD atD2 : this.CG.pS()) {
            atD2.a(this.JL());
        }
        this.CG.clear();
    }

    public void nS() {
        this.CE = this.c(21000L, 1);
    }

    public void W(long l2) {
        this.CE = this.c(l2, 2);
    }

    public void nT() {
        this.CE = this.c(11000L, 3);
    }

    public boolean a(gn_0 gn_02) {
        return super.bm(gn_02.getId());
    }

    public void nU() {
        aam_1.aMF().en(this.CE);
        this.CE = 0L;
        ((aej_0)this.JL()).a(awy_0.aJK());
    }

    public void nV() {
        aam_1.aMF().en(this.CE);
        this.CE = 0L;
        this.JL().a(ajy_1.azg());
    }

    public void nW() {
        aam_1.aMF().en(this.CE);
        this.CE = 0L;
        ((aej_0)this.JL()).a(abi_1.aqv());
    }

    public boolean X(long l2) {
        aam_1.aMF().en(this.CF);
        this.CF = 0L;
        return super.X(l2);
    }

    public void nX() {
        if (!this.aLo.di()) {
            this.X(this.JG());
            this.JJ();
            return;
        }
        this.bm(this.aLo.dk());
        this.X(this.JG());
    }

    public void b(gn_0 gn_02) {
        this.Y(gn_02.getId());
    }

    public void Y(long l2) {
        ra_2 ra_22 = this.aLo.p(l2);
        if (ra_22 != null) {
            while (ra_22.wf()) {
                yd_2 yd_22 = (yd_2)ra_22.wh();
                akv_0 akv_02 = ra_22.wg();
                int n2 = this.bj(l2);
                int n3 = this.aLo.do().size();
                if (n3 <= 1) continue;
                if (n2 == 0) {
                    yd_22.aW(((gn_0)this.nQ().get(1)).getId());
                    this.aLo.a(yd_22, ((gn_0)this.nQ().get(1)).getId(), akv_02.aVC(), false);
                    continue;
                }
                yd_22.aW(((gn_0)this.nQ().get(n2 - 1)).getId());
                this.aLo.a(yd_22, ((gn_0)this.nQ().get(n2 - 1)).getId(), akv_02.aVC(), true);
            }
        }
        super.Y(l2);
    }

    public aE D(int n2, int n3) {
        return null;
    }

    public void stop() {
        if (this.CF > 0L) {
            aam_1.aMF().en(this.CF);
        }
        this.CF = 0L;
        if (this.CE > 0L) {
            aam_1.aMF().en(this.CE);
        }
        this.CE = 0L;
        super.stop();
    }

    protected void Z(long l2) {
        this.c(this.V(l2));
    }

    protected abstract void c(gn_0 var1);

    protected void aa(long l2) {
        this.d(this.V(l2));
    }

    protected abstract void d(gn_0 var1);

    protected final void ab(long l2) {
        this.e(this.V(l2));
    }

    protected abstract void e(gn_0 var1);

    protected final void ac(long l2) {
        this.f(this.V(l2));
    }

    protected abstract void f(gn_0 var1);

    protected long c(long l2, int n2) {
        return this.CD.a(this, l2, n2, 1);
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }
}

