/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

public class apN
extends aix_2 {
    private static apN cNu = new apN();
    private sj_1 aMT = null;
    private adu_0 bv = null;
    private boolean cNv = false;
    private boolean cNw = false;

    public static apN aDK() {
        return cNu;
    }

    public apN() {
        try {
            int n2 = mu_1.rM().W("connectionRetryCount");
            int n3 = mu_1.rM().W("connectionRetryDelay");
            this.Q(n2, n3);
        }
        catch (aih_2 aih_22) {
            a.error((Object)"Impossible de d\u00e9finit les param\u00e8tres du syst\u00e8me de d\u00e9co/reco");
        }
    }

    public sj_1 Ln() {
        return this.aMT;
    }

    public void b(sj_1 sj_12) {
        this.aMT = sj_12;
        azs_0.aLV().g("localCoach", this.aMT);
    }

    public adu_0 aDL() {
        return this.bv;
    }

    public boolean aDM() {
        return this.cNw;
    }

    public void dQ(boolean bl2) {
        this.cNw = bl2;
    }

    public void a(adu_0 adu_02) {
        this.bv = adu_02;
        if (this.bv != null) {
            azh.aLL().mU(adu_02.YF());
        }
        if (this.aMT != null) {
            if (this.bv != null) {
                cl_1 cl_12 = this.bv.ef(this.aMT.getId());
                if (cl_12 != null) {
                    this.aMT.a((aez_0)cl_12);
                }
            } else {
                this.aMT.a((aez_0)null);
            }
        }
    }

    public boolean aDN() {
        return this.cNv;
    }

    public void dR(boolean bl2) {
        this.cNv = bl2;
    }

    public void a(NM nM) {
        super.a(nM);
        mu_1.rM().g("lastProxyGroupIndex", nM.getIndex());
    }

    public void cleanUp() {
        super.cleanUp();
        this.a(md_0.Yp());
        adY.atu().clear();
        bs_0.IF().clear();
        xz_0.amc().clear();
        je_1.Wa().Wb();
        aca_0.aOq().Wb();
    }

    public boolean aDO() {
        return super.a(DofusArenaClientInstance.yl().Pl());
    }

    public void a(int n2, int n3, long l2) {
        amq amq2 = (amq)pm_0.ur().bD(false);
        amq2.lt(n3);
        amq2.es(n3 - n2);
        xv_2 xv_22 = new xv_2((long)n2 * l2);
        amq2.cX(aon_0.aYc().getString("logon.progressDescription", n2, n3, (int)xv_22.Ex(), xv_22.Ey(), xv_22.Ez()));
        pm_0.ur().bD(true).es(amq2.aBI() - n2);
    }

    public void vP() {
        pm_0.ur().done();
    }

    public void cV(int n2) {
        a.info((Object)("queryResultCode : " + n2));
    }

    public void r(byte[] byArray) {
        add_1.aOG().a(aon_0.aYc().getString("logon.invalidClientVersion", kS.j(kS.FO), kS.j(byArray)), 1091L, 1, 2);
        pm_0.ur().done();
        this.aDP();
    }

    protected void ayL() {
        pm_0.ur().done();
        add_1.aOG().a(aon_0.aYc().getString("logon.notConnectedToServer"), 1091L, 1, 1);
    }

    protected void ayM() {
        pm_0.ur().bD(false).m(aon_0.aYc().getString("logon.progress"), 0);
        na na2 = new na();
        this.vJ().b(na2);
        bu_1 bu_12 = new bu_1();
        bu_12.aQ(this.qc());
        bu_12.setPassword(this.getPassword());
        this.vJ().b(bu_12);
    }

    protected void ayN() {
        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("question.disconnect"), 1177L, 102, 1);
        r_02.a(new apj_1(this));
    }

    protected void ayO() {
    }

    protected void ayP() {
        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("question.quit"), 1177L, 102, 1);
        r_02.a(new api_1(this));
    }

    public void aDP() {
        if (this.vJ() != null) {
            a.info((Object)"Demande de d\u00e9connexion.");
            aqb aqb2 = new aqb();
            this.vJ().b(aqb2);
            apN.aDK().w((byte)16);
            this.vJ().closeConnection();
            nW.stop();
            ahv_0.aUv().aUz();
            ajX.azB().azC();
            mc_1.qM().qQ();
            iz_1.Vg().Vi();
            de_2.Mc().clear();
            vk_1.BZ().Cc();
        }
    }

    public boolean a(pr_0 pr_02) {
        return super.a(pr_02);
    }
}

