/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/*
 * Renamed from MK
 */
public abstract class mk_1
extends gd_2
implements arQ {
    private static final Logger a = Logger.getLogger(mk_1.class);
    protected static final String bxE = "userPreferences.properties";
    public static final String bxF = "gamePreferences";
    public static final String bxG = "hardwareManager";
    protected aiQ bxH;
    protected ayy_0 bxI;
    private static final byte bxJ = 3;
    protected final ry_2[] bxK = new ry_2[3];
    protected qs_2 bxL;
    private final List bxM = new ArrayList();
    private int bxN = 0;
    protected sr_0 bxO;
    private avV bxP;

    public mk_1(boolean bl2) {
        this.YR();
        this.YU();
        this.YV();
        if (bl2) {
            acu_1.ara().arb();
        } else {
            acu_1.ara().start();
        }
        aam_1.aMF().start();
    }

    public abstract hs_1 YN();

    protected void a(ayy_0 ayy_02) {
        this.bxI = ayy_02;
    }

    public aiQ YO() {
        return this.bxH;
    }

    public qs_2 YP() {
        return this.bxL;
    }

    public ry_2[] YQ() {
        return this.bxK;
    }

    public final bx_2 kW() {
        return this.YN().kW();
    }

    protected abstract void YR();

    protected void YS() {
        this.YT().a(new abk_0(bxE));
    }

    public sr_0 YT() {
        if (this.bxO == null) {
            this.YU();
        }
        return this.bxO;
    }

    protected void a(sr_0 sr_02) {
        this.bxO = sr_02;
    }

    protected void YU() {
        this.a(new sr_0());
    }

    public void YV() {
        this.YS();
        try {
            sr_0.zg().load();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        sr_0 sr_02 = this.YT();
        if (sr_02 != null) {
            aor_1.aYh().g(bxF, sr_02);
            aor_1.aYh().g(bxG, Mf.btd);
        }
    }

    public void initialize() {
        akk_0.aVL().f(new mp_0[]{aau_0.apB()});
        this.YN().a(this);
    }

    protected void yn() {
        this.bxI.b(this.YN());
        this.YW();
    }

    public void YW() {
    }

    protected abstract aiQ YX();

    protected abstract void a(aiQ var1);

    protected abstract ayb YY();

    protected void b(qs_2 qs_22) {
        mk_1.c(qs_22);
    }

    protected static void c(qs_2 qs_22) {
        if (qs_22 != null) {
            qs_22.ci(true);
            qs_22.cj(true);
            qs_22.a(bu_0.cO());
            qs_22.b(aux__0.aHL());
        } else {
            a.error((Object)"Impossible d'initialiser la WorldScene car elle n'a pas \u00e9t\u00e9 cr\u00e9\u00e9e !");
        }
    }

    protected abstract boolean c(String var1, int var2, int var3);

    protected boolean a(asn_0 asn_02, boolean bl2) {
        this.YN().initialize();
        this.kW().k(bl2);
        this.YN().a(asn_02);
        return true;
    }

    protected abstract void XZ();

    protected abstract void YZ();

    public void Za() {
        try {
            this.ym();
            this.Zd();
        }
        catch (Exception exception) {
            a.error((Object)"Erreur lors du resomePostDiagnosticLoading : ", (Throwable)exception);
            JOptionPane.showMessageDialog(this.YN().kV(), exception.getMessage() + " (" + exception.getClass().getName() + ")");
        }
    }

    public void aJ(short s) {
        this.bxK[0].a(s, this.kW(), 0.0f);
    }

    protected final void fl(String string) {
        for (int j = 0; j < this.bxK.length; ++j) {
            this.bxK[j] = new ry_2();
            mk_1.c(this.bxK[j]);
            this.bxK[j].bD(string);
        }
    }

    protected void Zb() {
        this.bxH.a(new vy_2(this));
    }

    protected void b(bx_2 bx_22) {
        bx_22.a(yb_2.amk(), true);
        this.bxL = this.YY();
        this.fl(this.Zc());
        this.yn();
        this.bxH = this.YX();
        this.Zb();
        this.a(this.bxH);
        if (this.bxH != null) {
            bx_22.a(this.bxH, true);
            bx_22.a(this.bxH, true);
            bx_22.a(this.bxH, false);
            bx_22.a(this.bxH, false);
        }
        this.b(this.bxL);
        if (this.bxL != null) {
            bx_22.a(this.bxL, false);
            bx_22.a(this.bxL, false);
            bx_22.a(this.bxL, false);
            bx_22.a(this.bxL, false);
            for (int j = 0; j < this.bxK.length; ++j) {
                this.bxK[j].d(this.bxL);
            }
        }
    }

    protected abstract String Zc();

    protected void a(hR hR2) {
        this.bxM.add(hR2);
    }

    protected abstract void ym();

    protected void Zd() {
        this.bxN = -1;
        this.gJ(this.bxM.size() - 1);
        this.Ze();
    }

    private void Ze() {
        if (++this.bxN < this.bxM.size()) {
            hR hR2 = (hR)this.bxM.get(this.bxN);
            if (hR2 != null) {
                try {
                    hR2.a(this);
                }
                catch (Exception exception) {
                    this.a(hR2, exception);
                }
            }
        } else {
            this.gK(this.bxN);
            this.start();
        }
    }

    public void b(hR hR2) {
        this.a(hR2, this.bxN);
        try {
            hR hR3 = (hR)this.bxM.get(this.bxN);
            this.c(hR3);
        }
        catch (Exception exception) {
            a.error((Object)"exception sur onContentInitializerStart", (Throwable)exception);
        }
        this.bxH.a(new vx_2(this));
    }

    protected abstract void gJ(int var1);

    protected abstract void gK(int var1);

    protected abstract void c(hR var1);

    protected abstract void a(hR var1, Exception var2);

    protected abstract void a(hR var1, int var2);

    protected abstract void start();

    public void vM() {
        this.Zf();
    }

    public void cleanUp() {
        this.Zf();
    }

    private void Zf() {
        try {
            bd_1.Is().Iu();
        }
        catch (Exception exception) {
            a.error((Object)"Exception lors du nettoyage des mobiles", (Throwable)exception);
        }
        try {
            cW.fd().ajt();
            this.bxI.aLI();
        }
        catch (Exception exception) {
            a.error((Object)"Exception lors du nettoyage du TextureManager", (Throwable)exception);
        }
    }

    public void Zg() {
    }

    public void Zh() {
        asn_0 asn_02 = this.YN().lb();
        if (asn_02 == null) {
            return;
        }
        this.YT().a((ro_2)akz_1.cEv, asn_02.Fk());
    }

    public void b(asn_0 asn_02) {
        this.YT().a((ro_2)akz_1.cEv, asn_02.Fk());
    }

    public void bS(boolean bl2) {
        bx_2 bx_22 = this.kW();
        if (bx_22 == null) {
            return;
        }
        bx_22.h(bl2);
        pg_2 pg_22 = this.YN().kV();
        if (pg_22 != null) {
            pg_22.setSize(pg_22.getSize());
        }
    }

    public abstract void Zi();

    public void bT(boolean bl2) {
        if (this.Zj() == bl2) {
            return;
        }
        if (this.YN() == null) {
            return;
        }
        if (bl2) {
            if (this.bxP == null) {
                this.bxP = new avV(this);
                this.YN().a(this.bxP);
            }
            this.bxP.reset();
            this.b(this.bxP);
        } else if (this.bxP != null) {
            this.YN().kU();
            this.bxP.reset();
            this.bxP = null;
        }
    }

    protected void b(avV avV2) {
        avV2.F(zb_0.class);
        avV2.F(cf_0.class);
        avV2.F(aph_1.class);
    }

    public boolean Zj() {
        return this.bxP != null && this.bxP.isVisible();
    }

    static /* synthetic */ void d(mk_1 mk_12) {
        mk_12.Ze();
    }
}

