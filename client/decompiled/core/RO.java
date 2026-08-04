/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.client.DofusArenaReplayPlayerInstance;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class RO
implements acg_2,
alx_0 {
    protected static final Logger a = Logger.getLogger(RO.class);
    public static final int bKw = 750;
    public static final int bKx = 8;
    private int bKy = 750;
    private int bKz = 0;
    private int bKA = -1;
    private int bKB = 0;
    private int bKC = 0;
    private boolean bKD = false;
    private boolean bKE = false;
    private boolean bKF = false;
    private atn_0[] bKG;
    private static final RO bKH = new RO();
    private lb_0 bKI = new lb_0();

    public static RO aer() {
        return bKH;
    }

    public void a(Eq eq, int n2) {
        akb_2 akb_22 = (akb_2)this.bKI.get(n2);
        if (akb_22 != null) {
            akb_22.c(eq);
        } else {
            akb_22 = new akb_2();
            akb_22.c(eq);
            this.bKI.c(n2, akb_22);
        }
        if (n2 > this.bKz) {
            this.bKz = n2;
        }
    }

    public void a(kr_0 kr_02, int n2) {
        akb_2 akb_22 = (akb_2)this.bKI.get(n2);
        if (akb_22 != null) {
            akb_22.c(kr_02);
        } else {
            akb_22 = new akb_2();
            akb_22.c(kr_02);
            this.bKI.c(n2, akb_22);
        }
        kr_02.a(new xj_2(akb_22));
        if (n2 > this.bKz) {
            this.bKz = n2;
        }
    }

    public void aes() {
        this.bKD = true;
        this.ar(1.3f);
        this.hz(0);
    }

    public void hz(int n2) {
        if (!this.bKE) {
            apN.aDK().aDL().asq().ef(4 * this.bKy / 1000);
        }
        for (int j = n2; j <= this.bKz; ++j) {
            akb_2 akb_22 = (akb_2)this.bKI.get(j);
            if (akb_22 == null || j == this.bKA) continue;
            aam_1.aMF().a(this, (j - n2) * this.bKy, j, 1);
            break;
        }
    }

    public void resume() {
        apN.aDK().aDL().asq().ef(this.bKC);
        this.hz(this.bKA);
        this.bKE = false;
    }

    public void pause() {
        this.bKC = azs_0.aLV().jf("countdown");
        apN.aDK().aDL().asq().stop();
        azs_0.aLV().g("countdown", this.bKC);
        this.bKE = true;
    }

    public void stop() {
        if (DofusArenaReplayPlayerInstance.XY().YP() == null) {
            this.clear();
            this.bKD = false;
            qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
            qs_22.ao(false);
            nk_0.aaq().ce(1L);
            xx_1.ai((short)0);
            sj_1 sj_12 = apN.aDK().Ln();
            sj_12.aTt();
            no_2.g(sj_12);
            hc_2.kI().k("world", true);
            hc_2.kI().k("common", true);
            hc_2.kI().k("tutorial", true);
            hc_2.kI().k("fight", false);
            qs_22.d(sj_12);
            qs_22.vs();
            apN.aDK().a(po_0.abV());
            apN.aDK().a(wp_2.Dl());
            apN.aDK().a(no_2.aaY());
            apN.aDK().a(ft_1.jr());
            apN.aDK().a(aap_2.aMJ());
            apN.aDK().a(ug_1.AL());
            apN.aDK().b(pq_1.acy());
            apN.aDK().b(hg_1.Tr());
            apN.aDK().b(bq_2.cF());
            qs_22.k(1.0);
            qs_22.an(true);
            qs_22.bk(true);
        } else {
            System.exit(0);
        }
    }

    public void a(String[] stringArray, long[] lArray) {
        this.bKF = true;
        this.bKG = new atn_0[stringArray.length];
        for (int j = 0; j < stringArray.length; ++j) {
            this.bKG[j] = new atn_0(stringArray[j]);
            this.bKG[j].setDuration(-1);
            mT mT2 = bd_1.Is().bb(lArray[j]);
            if (mT2 == null) continue;
            this.bKG[j].c(mT2);
            wj_2.Df().a(this.bKG[j]);
        }
        this.pause();
    }

    public void aet() {
        this.bKF = false;
        for (int j = 0; j < this.bKG.length; ++j) {
            wj_2.Df().b(this.bKG[j]);
        }
        this.resume();
    }

    public void hA(int n2) {
        this.bKA = n2;
    }

    private void ar(float f) {
        for (yg_0 yg_02 : apN.aDK().aDL().aKn()) {
            Iterator iterator = yg_02.amp();
            while (iterator.hasNext()) {
                ee_2 ee_22 = (ee_2)iterator.next();
                ee_22.NW().ar(f);
            }
        }
    }

    private void clear() {
        this.bKI.clear();
    }

    public boolean isRunning() {
        return this.bKD;
    }

    public boolean aeu() {
        return this.bKF;
    }

    public int aev() {
        return this.bKz;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case -2147483648: {
                int n2 = ((axe_0)pr_02).aKE();
                if (this.bKE || n2 == this.bKA) {
                    return false;
                }
                akb_2 akb_22 = (akb_2)this.bKI.get(n2);
                this.bKA = n2;
                this.bKB = 0;
                Eq eq = (Eq)akb_22.aVH().get(this.bKB);
                eq.bl(true);
                eq.a(this);
                eq.run();
                for (int j = n2 + 1; j <= this.bKz; ++j) {
                    akb_22 = (akb_2)this.bKI.get(j);
                    if (akb_22 == null || j == this.bKA) continue;
                    aam_1.aMF().a(this, (j - n2) * this.bKy, j, 1);
                    break;
                }
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }

    public void a(Eq eq) {
        ++this.bKB;
        akb_2 akb_22 = (akb_2)this.bKI.get(this.bKA);
        if (akb_22.aVH().size() > this.bKB) {
            Eq eq2 = (Eq)akb_22.aVH().get(this.bKB);
            eq2.bl(true);
            eq2.a(this);
            eq2.run();
        }
    }
}

