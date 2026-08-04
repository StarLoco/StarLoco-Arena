/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

public class add
extends avp_0 {
    private static final Logger a = Logger.getLogger(add.class);
    private static add cme = new add();

    public static add ase() {
        return cme;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 20170: {
                sb_0 sb_02 = (sb_0)pr_02;
                if (((xj)la_0.XJ().pj(sb_02.getIntValue())).tq()) {
                    add_1.aOG().a(aon_0.aYc().getString("coachInventory.undestructibleCard"), 66L, 102, 1);
                } else {
                    ajt_1 ajt_12 = (ajt_1)azs_0.aLV().getProperty("fusionTrade").getValue();
                    ajt_12.ld(sb_02.getIntValue());
                    azs_0.aLV().a((aho_0)ajt_12, ajt_1.ce);
                    wy_2 wy_22 = (wy_2)apN.aDK().Ln().yD().bW(sb_02.getIntValue());
                    if (wy_22 == null) {
                        wy_22 = (wy_2)apN.aDK().Ln().yD().bW(-sb_02.getIntValue());
                    }
                    wy_22.w((short)-1);
                    azs_0.aLV().a((aho_0)wy_22, "quantity");
                    afl_0 afl_02 = azs_0.aLV().getProperty("coachManagement.currentSet");
                    azs_0.aLV().a((aho_0)((fe_1)afl_02.getValue()), fe_1.aVa);
                }
                return false;
            }
            case 20171: {
                sb_0 sb_03 = (sb_0)pr_02;
                ajt_1 ajt_13 = (ajt_1)azs_0.aLV().getProperty("fusionTrade").getValue();
                ajt_13.le(sb_03.getIntValue());
                azs_0.aLV().a((aho_0)ajt_13, ajt_1.ce);
                sj_1 sj_12 = apN.aDK().Ln();
                xj xj2 = (xj)la_0.XJ().pj(sb_03.getIntValue());
                wy_2 wy_23 = aoi_0.aXY().ac(ByteBuffer.wrap(xj2.cd()));
                if (wy_23 != null) {
                    wy_23.q((short)1);
                    try {
                        sj_12.yD().f(wy_23);
                    }
                    catch (xR xR2) {
                        xR2.printStackTrace();
                    }
                    catch (gg gg2) {
                        gg2.printStackTrace();
                    }
                }
                return false;
            }
            case 20172: {
                sb_0 sb_04 = (sb_0)pr_02;
                ajt_1 ajt_14 = (ajt_1)azs_0.aLV().getProperty("fusionTrade").getValue();
                xj xj3 = (xj)la_0.XJ().pj(sb_04.getIntValue());
                if (xj3 != null && (xj3.tz() != 0 || xj3.tA() != 0)) {
                    ajt_14.lf(sb_04.getIntValue());
                    azs_0.aLV().a((aho_0)ajt_14, ajt_1.ce);
                    wy_2 wy_24 = (wy_2)apN.aDK().Ln().yD().bW(sb_04.getIntValue());
                    wy_24.w((short)-1);
                    azs_0.aLV().a((aho_0)wy_24, "quantity");
                    afl_0 afl_03 = azs_0.aLV().getProperty("coachManagement.currentSet");
                    azs_0.aLV().a((aho_0)((fe_1)afl_03.getValue()), fe_1.aVa);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("mustBeFusionCard"), 1090L, 102, 1);
                }
                return false;
            }
            case 20173: {
                sb_0 sb_05 = (sb_0)pr_02;
                ajt_1 ajt_15 = (ajt_1)azs_0.aLV().getProperty("fusionTrade").getValue();
                ajt_15.lf(0);
                azs_0.aLV().a((aho_0)ajt_15, ajt_1.ce);
                sj_1 sj_13 = apN.aDK().Ln();
                xj xj4 = (xj)la_0.XJ().pj(sb_05.getIntValue());
                wy_2 wy_25 = aoi_0.aXY().ac(ByteBuffer.wrap(xj4.cd()));
                if (wy_25 != null) {
                    wy_25.q((short)1);
                    try {
                        sj_13.yD().f(wy_25);
                    }
                    catch (xR xR3) {
                        xR3.printStackTrace();
                    }
                    catch (gg gg3) {
                        gg3.printStackTrace();
                    }
                }
                return false;
            }
            case 20174: {
                ajt_1 ajt_16 = (ajt_1)azs_0.aLV().getProperty("fusionTrade").getValue();
                ajt_16.lc(0);
                ahg_0 ahg_02 = new ahg_0();
                jg_0 jg_02 = ajt_16.azw();
                jg_02.v(0, ajt_16.azv());
                ahg_02.e(jg_02.nm());
                apN.aDK().vJ().b(ahg_02);
                return false;
            }
        }
        return super.a(pr_02);
    }

    protected void X() {
        add_1.aOG().a("fusionLabDialog", oh_2.bq("fusionLabDialog"), (short)10000);
    }

    protected void W() {
        add_1.aOG().kO("fusionLabDialog");
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().l("dofusarena.fusionLaboratory", ahd_1.class);
            apN.aDK().a(cp_0.Lj());
        }
        super.a(fh_22, bl2);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kG("dofusarena.fusionLaboratory");
            apN.aDK().b(cp_0.Lj());
        }
        super.b(fh_22, bl2);
    }
}

