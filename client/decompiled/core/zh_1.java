/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.framework.graphics.engine.Anm2.Anm;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/*
 * Renamed from ZH
 */
public abstract class zh_1
extends mk_1 {
    private static final int MAP_SIZE = 18;
    private static final oj_2 cdL = new za_0(18, 18);
    private static Logger a = Logger.getLogger(DofusArenaClientInstance.class);
    private final hs_1 cdM = new aDU();
    private static final String[] cdN = new String[]{"-110", "-111", "-120", "-121", "-130", "-131", "-140", "-141", "-150", "-151", "-160", "-161", "-170", "-171", "-180", "-181", "-190", "-191", "-1100", "-1101", "-1110", "-1111", "-1120", "-1121", "7000", "7001"};

    public zh_1() {
        super(mu_1.rM().rN());
    }

    public static Logger getLogger() {
        return a;
    }

    public apN aoc() {
        return apN.aDK();
    }

    public fg_0 aod() {
        return (fg_0)super.YT();
    }

    protected void YU() {
        this.a(new fg_0());
    }

    public void YV() {
        super.YV();
        sr_0.zg().cS(true);
    }

    protected String Zc() {
        try {
            return mu_1.rM().getString("gfxPath");
        }
        catch (aih_2 aih_22) {
            a.error((Object)"", (Throwable)aih_22);
            return null;
        }
    }

    protected boolean c(String string, int n2, int n3) {
        if (nk_0.aaq().initialize()) {
            nk_0.aaq().start();
            return true;
        }
        a.error((Object)"Erreur durant l'initialisation du soundManager");
        return false;
    }

    public void initialize() {
        String string;
        super.initialize();
        String string2 = mu_1.rM().getString("gfxConfigFile");
        yW.FL().dc(string2);
        mu_1 mu_12 = mu_1.rM();
        add_1.aOG().dwz = mu_12.getString("ANMGUIPath");
        add_1.aOG().cFE = mu_12.getString("particlePath");
        fg_0 fg_02 = this.aod();
        asn_0 asn_02 = asn_0.jy(fg_02.f(akz_1.cEv));
        boolean bl2 = fg_02.a(adc_0.clT);
        String string3 = mu_12.getString("soundDevice");
        boolean bl3 = mu_12.getBoolean("soundEnable");
        if (bl3 && this.c(string3, 32, 1000)) {
            try {
                string = mu_12.getString("musicPath");
                nk_0.aaq().fp(string);
                nk_0.aaq().ah(fg_02.c(akz_1.cEw));
                nk_0.aaq().bU(fg_02.a(akz_1.cEz));
            }
            catch (Exception exception) {
                a.error((Object)"impossible d'initialiser musiques DofusArenaSoundManager ", (Throwable)exception);
            }
            try {
                string = mu_12.getString("soundPath");
                nk_0.aaq().fq(string);
                nk_0.aaq().ai(fg_02.c(akz_1.cEx));
                nk_0.aaq().bV(fg_02.a(akz_1.cEA));
            }
            catch (Exception exception) {
                a.error((Object)"impossible d'initialiser sounds DofusArenaSoundManager ", (Throwable)exception);
            }
        }
        this.a(asn_02, bl2);
        abz_0.cjd.a(tj_1.amE);
        aoz_1.aYF().lR(string2);
        string = mu_12.getString("shadersPath");
        ahA.axi().a(new azI(this));
        aoz_1.aYF().lS(string);
        uQ.AV().setPath(mu_12.getString("mapsLightPath"));
        ahn_0.dNL.initialize();
        cx_0 cx_02 = cx_0.JY();
        String string4 = string + "textures/firework01.tga";
        ef_1 ef_12 = cx_02.a(arX.cQT.iE(), -1296775008915292157L, string4, false);
        ado_0.aPH().setTexture(ef_12);
        ahA.axi().dx(fg_02.a(adc_0.clQ));
        this.b(this.kW());
        nk_0.aaq().a(this.bxL.vn());
        mu_1 mu_13 = mu_1.rM();
        auU.setPath(mu_13.getString("mapsTopologyPath"));
        auU.aHJ();
        aCH.aOu().load(mu_13.getString("worldInfoFile"));
        xx_1.q(mu_13.getString("mapsTplgCoord"), mu_13.getString("mapsGfxCoord"));
        xx_1.aS(false);
        aku_0.lC(mu_13.getString("mapsFightPath"));
        gC gC2 = gC.kg();
        gC2.setPath(mu_13.getString("mapsEnvironmentPath"));
        String string5 = mu_13.getString("mapsGfxPath");
        aga_0.aSG().setPath(string5);
        aga_0.aSG().a((byte)-1, true);
        for (int j = 0; j < this.bxK.length; ++j) {
            this.bxK[j].vB().setPath(string5);
        }
        ahn_0.dNL.a(aga_0.aSG());
        ahn_0.dNL.a(bd_1.Is());
        ahn_0.dNL.a(GY.Ss());
        gC2.a(new azh_0(this));
        qd_1.uW().ak(this.aod().Ph());
        gC2.a(qd_1.uW());
        gC2.b(nk_0.aaq());
        gC2.a(OH.abC());
        ke_0.pk().setFile(mu_13.getString("ambienceBankFile"));
        hc_2 hc_22 = hc_2.kI();
        String string6 = mu_12.getString("shortcutsFile");
        try {
            hc_22.l(string6, false);
            hc_22.k("debug", true);
            this.kW().a(hc_22, false);
        }
        catch (Exception exception) {
            a.error((Object)"Exception : ", (Throwable)exception);
            throw new Exception("Impossible de charger les raccourcis clavier depuis le fichier " + string6 + " !");
        }
        Ky.WG().setPath(mu_12.getString("scriptPath"));
        Ky.WG().c(new mp_0[0]);
        Ky.WG().e(gp_2.Sb(), uc_2.AH(), asO.aFM(), Wz.ajg(), aau_0.apB(), apM.aDH(), rt_0.aeA(), vp_2.BG(), akn.azL(), adg_1.asg());
        a.info((Object)"LUAManager initialis\u00e9.");
        asO.aFM().e(this.bxL);
        arq_0.aEv().jd(mu_12.getString("statisticsReportsModelsFile"));
        xw_1 xw_12 = xw_1.EB();
        xw_12.cU(mu_12.getString("ANMIndexFile"));
        String string7 = mu_12.getString("playerGfxPath");
        for (int j = 0; j < cdN.length; ++j) {
            try {
                Anm anm = xw_12.f(String.format(string7, cdN[j]), false);
                anm.HE();
                continue;
            }
            catch (Exception exception) {
                a.error((Object)"Unable to load fighter", (Throwable)exception);
            }
        }
        fl_2.rO = 4;
        fl_2.rP = 2;
        fl_2.rQ = 250;
        fl_2.rR = 400;
        this.YN().kX();
    }

    protected void XZ() {
        amf_1.dXt.a(new zO());
        amf_1.dXt.a(new aqc_0());
        amf_1.dXt.a(new arY());
        amf_1.dXt.a(new ps_2());
        amf_1.dXt.a(new aad_2());
        amf_1.dXt.a(new ami_2());
        amf_1.dXt.a(new zp_2());
        amf_1.dXt.a(new xh_0());
        amf_1.dXt.initialize();
        amf_1.dXt.GO();
        amf_1.dXt.cleanUp();
    }

    protected void YZ() {
    }

    public void ym() {
        this.a(du_1.MC());
        this.a(new zv_1());
        this.a(apS.aDW());
        this.a(aGl.aSl());
        this.a(aeb_0.aQB());
        this.a(adp_1.aPI());
        this.a(ux_1.AG());
        this.a(eh_2.he());
        this.a(q_0.ar());
        this.a(nh_2.sa());
        this.a(zw_1.aoB());
        this.a(MY.ZZ());
        this.a(lc_2.Xs());
        this.a(aoY.aDk());
        this.a(mu_2.Zx());
        this.a(dq_1.fO());
        this.a(vj_2.aii());
        this.a(atp_0.aGx());
        this.a(ack_2.aOv());
        this.a(WO.ajs());
        this.a(ne_0.rZ());
        this.a(ank_1.aXL());
        this.a(xs_0.Ei());
        this.a(fs_2.OU());
        this.a(aIp.aUT());
        this.a(gc_1.Qr());
    }

    public void start() {
    }

    public void cleanUp() {
        super.cleanUp();
        hc_2.kI().k("common", false);
        hc_2.kI().k("world", false);
        hc_2.kI().k("fight", false);
        apN.aDK().cleanUp();
        add_1.aOG().aPb();
        add_1.aOG().aOR();
        mb_0.Yl().hide();
        add_1.aOG().aOU();
        add_1.aOG().aPa();
        this.yo();
        this.YP().ao(false);
        this.start();
    }

    public hs_1 YN() {
        return this.cdM;
    }

    protected void YR() {
        aor_1.aYh().a(azs_0.aLV());
    }

    public void yn() {
        this.a(add_1.aOG());
        aMi.aWT().a(add_1.aOG());
        super.yn();
        add_1.aOG().a(hc_2.kI());
        add_1.aOG().a(g_0.l());
        aek.atD().a(new azf_0(this));
        add_1.aOG().a(aon_0.aYc());
        this.yo();
        add_1.aOG().ap(mu_1.rM().getString("themeFile"), mu_1.rM().getString("themeDirectory"));
        add_1.aOG().kI(oh_2.bq("messageBoxDialog"));
        add_1.aOG().kK(oh_2.bq("popupDialog"));
        azs_0.aLV().g("buildVersion", kS.FL);
        add_1.aOG().a(new lh_2());
    }

    public void YW() {
        super.YW();
        ye_2.amJ().f("interactiveBubble", aod_2.class);
        ye_2.amJ().f("InteractiveBubbleAppearance", aj_0.class);
        ye_2.amJ().f("sphereBoard", ahr_2.class);
    }

    protected agV aoe() {
        return new agV();
    }

    protected void a(aiQ aiQ2) {
    }

    protected ayb YY() {
        return new xu_2(this);
    }

    protected void b(qs_2 qs_22) {
        super.b(qs_22);
        mu_1 mu_12 = mu_1.rM();
        try {
            UF.load(mu_12.getString("elementsFile"));
            acg_1.arw().setPath(mu_12.getString("groupsFile"));
            qs_22.bD(mu_12.getString("gfxPath"));
            qs_22.bE(mu_12.getString("soundBank"));
            aiJ.ayv().setPath(mu_12.getString("particlePath"));
        }
        catch (aih_2 aih_22) {
            a.error((Object)"Erreur \u00e0 l'initialisation de la worldScene", (Throwable)aih_22);
        }
    }

    public void Zi() {
        try {
            String string = mu_1.rM().getString("highLightGfxDefaultFile");
            wn_2.Dj().cG(string);
        }
        catch (Exception exception) {
            a.error((Object)"Erreur \u00e0 l'initialisation du HighLightManager", (Throwable)exception);
        }
    }

    public void yo() {
    }

    protected void gJ(int n2) {
        pm_0.ur().bD(true).m(aon_0.aYc().getString("loading"), n2);
    }

    protected void gK(int n2) {
        pm_0.ur().done();
        aly_1.aAQ().shutdown();
    }

    protected void c(hR hR2) {
        pm_0.ur().bD(true).cX(hR2.getName());
    }

    protected void a(hR hR2, Exception exception) {
        add_1.aOG().a(aon_0.aYc().getString("error.loading") + hR2.getName(), 1090L, 4, 1);
    }

    protected void a(hR hR2, int n2) {
        pm_0.ur().bD(true).es(n2);
        pm_0.ur().bD(true).cX(" ");
    }

    public void b(Object object, String string) {
        String string2 = add_1.aOG().kE("error.unsupportedMaterial");
        JOptionPane.showMessageDialog(this.cdM.kV(), string2, "Error", 0);
    }

    public void aof() {
        apN.aDK().aDP();
    }

    protected void Zd() {
        a.info((Object)"Demarage du Binary Storage");
        aly_1.aAQ().iv(mu_1.rM().getString("contentStaticDataStorageDirectory"));
        aly_1.aAQ().art();
        super.Zd();
    }
}

