/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import org.apache.log4j.Logger;

public class S
implements atG {
    private static final Logger a = Logger.getLogger(S.class);
    private static S be = new S();
    private final NG bf = NG.aaO();
    private final aen_0 bg = new aen_0();
    private acv_2 bh = new acv_2("100%");
    private int bi = 0;
    private int bj = -1;
    private int bk;
    private int bl;
    private ry bm = null;
    private boolean bn = false;

    public S() {
        this.bg.cpI = 1000;
        this.bg.cpK = false;
        this.bg.cpH = true;
        this.bh.setDuration(-1);
    }

    public static S as() {
        return be;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 30001: {
                abu_1 abu_12 = (abu_1)pr_02;
                this.bk = abu_12.au();
                this.bl = abu_12.av();
                this.aw();
                adu_0 adu_02 = apN.aDK().aDL();
                ee_2 ee_22 = (ee_2)adu_02.ass().nP();
                ry ry2 = ee_22.gg();
                ry ry3 = this.c(this.bk, this.bl);
                if (ry3 != null && Math.abs(ry3.getX() - ry2.getX()) + Math.abs(ry3.getY() - ry2.getY()) == 1) {
                    if (this.bf.i(this.c(this.bk, this.bl))) {
                        this.a(ee_22, adu_02);
                    }
                } else {
                    wj_2.Df().b(this.bh);
                }
                return false;
            }
            case 30000: {
                ado ado2 = (ado)pr_02;
                this.bk = ado2.au();
                this.bl = ado2.av();
                int n2 = -1;
                n2 = DofusArenaClientInstance.yl().aod().a(adc_0.clW) ? 1 : 3;
                if (ado2.aqY() == n2) {
                    int n3;
                    vD vD2;
                    adu_0 adu_03;
                    ee_2 ee_23;
                    wj_2.Df().b(this.bh);
                    this.bi = 0;
                    this.bf.clear();
                    if (this.bm != null && this.bm.equals(this.c(this.bk, this.bl)) && (ee_23 = (ee_2)(adu_03 = apN.aDK().aDL()).ass().nP()) != null && (vD2 = ee_23.NW()) != null && (n3 = ee_23.d(Lr.bqz)) > 0) {
                        this.bg.cpJ = n3;
                        arh_0 arh_02 = MJ.a(DofusArenaClientInstance.yl().YP(), vD2, ado2.au(), ado2.av(), this.bg, adu_03.gV());
                        if (arh_02 != null && arh_02.aEF() > 0) {
                            this.bi = arh_02.aEF();
                            md_1 md_12 = new md_1();
                            md_12.a(arh_02);
                            md_12.j(ee_23.getId());
                            apN.aDK().vJ().b(md_12);
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        this.clear();
    }

    public void b(fh_2 fh_22, boolean bl2) {
        this.clear();
    }

    public void at() {
        this.bf.clear();
    }

    private void clear() {
        wj_2.Df().b(this.bh);
        this.bm = null;
        this.bi = 0;
        this.bj = -1;
        this.bn = false;
        this.at();
    }

    public void b(int n2, int n3) {
        this.bm = null;
        this.bk = n2;
        this.bl = n3;
    }

    public int au() {
        return this.bk;
    }

    public int av() {
        return this.bl;
    }

    public void aw() {
        adu_0 adu_02 = apN.aDK().aDL();
        ee_2 ee_22 = (ee_2)adu_02.ass().nP();
        if (ee_22 != null) {
            ry ry2 = this.c(this.bk, this.bl);
            if (ry2 == null) {
                this.bf.clear();
            } else if (this.bm == null || !this.bm.equals(ry2)) {
                int n2 = ee_22.d(Lr.bqz);
                if (this.bj != n2) {
                    this.bj = n2;
                    this.bi = 0;
                    this.bn = false;
                } else if (this.bn) {
                    n2 = 0;
                }
                if (n2 > 0 && !ee_22.PL().b((aak_2)avx_0.dew) && !ee_22.PL().b((aak_2)avx_0.dex)) {
                    vD vD2 = ee_22.NW();
                    if (vD2 != null) {
                        this.bg.cpJ = n2;
                        arh_0 arh_02 = MJ.a(DofusArenaClientInstance.yl().YP(), vD2, this.bk, this.bl, this.bg, adu_02.gV());
                        if (arh_02 != null && arh_02.aEF() > 0) {
                            if (!abt_1.aNp().aNr() && !alx_2.aWN().aNr()) {
                                this.bf.b(arh_02);
                            }
                        } else {
                            this.bf.clear();
                        }
                        this.bm = ry2;
                    }
                } else {
                    this.bf.clear();
                }
            }
        }
    }

    private void a(ee_2 ee_22, adu_0 adu_02) {
        Object object;
        float f = 100.0f;
        yg_0 yg_02 = ee_22.PH();
        if (!ee_22.rD()) {
            object = adu_02.aKo();
            while (object.hasNext()) {
                ry ry2;
                ee_2 ee_23 = (ee_2)object.next();
                if (ee_23.PH() == yg_02 || ee_23.PL().b((aak_2)avx_0.dew) || ee_23 instanceof aad_0 || ee_23.rD() || ee_23.PL().b((aak_2)avx_0.deu) || ee_23.d(Lr.brd) < 0 || !auU.a((ry2 = ee_23.gg()).getX(), ry2.getY(), ee_22.gg().getX(), ee_22.gg().getY(), false)) continue;
                float f2 = ee_22.d(Lr.bre) - ee_23.d(Lr.brd);
                f = 100.0f * (f / 100.0f) * (f2 / 100.0f);
            }
        }
        if ((object = bd_1.Is().bb(ee_22.getId())) != null) {
            if (f > 0.0f) {
                if (f > 100.0f) {
                    f = 100.0f;
                }
                this.bh.setText(Math.round(f) + "%");
            } else {
                this.bh.setText("0%");
            }
            this.bh.c((aln_1)object);
            wj_2.Df().a(this.bh);
        }
    }

    protected ry c(int n2, int n3) {
        boolean bl2 = true;
        if (apN.aDK().c(avu_0.aIB())) {
            bl2 = !avu_0.aIB().aIC();
        }
        return MJ.a(DofusArenaClientInstance.yl().YP(), n2, n3, bl2);
    }

    public void c(boolean bl2) {
        this.bn = bl2;
    }
}

