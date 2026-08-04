/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from mG
 */
public class mg_0
implements atG {
    private static final Logger a = Logger.getLogger(mg_0.class);
    private static final mg_0 Ln = new mg_0();

    public static mg_0 rq() {
        return Ln;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        apN.aDK().Ln().yH();
        iu_0.Ut().clean();
        aij_0.aUF().aUG();
        add_1.aOG().a("fireworkDialog", oh_2.bq("fireworkDialog"), (short)10000);
        add_1.aOG().l("dofusarena.firework", aeA.class);
        azs_0.aLV().g("fireworkLauncher", iu_0.Ut());
    }

    public void b(fh_2 fh_22, boolean bl2) {
        apN.aDK().Ln().yI();
        add_1.aOG().kO("fireworkDialog");
        add_1.aOG().kG("dofusarena.firework");
        azs_0.aLV().kb("fireworkLauncher");
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 21020: {
                apN.aDK().b(Ln);
                return false;
            }
            case 21021: {
                ia_2 ia_22 = (ia_2)pr_02;
                if (ia_22.lm().tj() == aMK.dYz) {
                    akl_2 akl_22 = (akl_2)iu_0.Ut().Uu().an(ia_22.ak());
                    ky_2 ky_22 = apN.aDK().Ln().yD();
                    wy_2 wy_22 = ia_22.lm();
                    if (wy_22.hG() == 0 || ky_22.bW(wy_22.jf()) == null) {
                        wy_22 = (wy_2)ky_22.bW(-Math.abs(wy_22.jf()));
                    }
                    wy_22.w((short)-1);
                    akl_22.i(aoi_0.aXY().ac(ByteBuffer.wrap(wy_22.cd())));
                    azs_0.aLV().a((aho_0)iu_0.Ut(), iu_0.ce);
                    afl_0 afl_02 = azs_0.aLV().getProperty("coachManagement.currentSet");
                    azs_0.aLV().a((aho_0)((fe_1)afl_02.getValue()), fe_1.aVa);
                } else {
                    add_1.aOG().f(aon_0.aYc().getString("error.cardIsNotFirework"), 102, 1);
                }
                return false;
            }
            case 21023: {
                ia_2 ia_23 = (ia_2)pr_02;
                akl_2 akl_23 = (akl_2)iu_0.Ut().Uu().an(ia_23.ak());
                akl_23.i(null);
                azs_0.aLV().a((aho_0)iu_0.Ut(), iu_0.ce);
                return false;
            }
            case 21022: {
                iu_0.Ut().start();
                apN.aDK().b(Ln);
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
}

