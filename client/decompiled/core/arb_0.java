/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

/*
 * Renamed from arB
 */
public class arb_0
extends avp_0 {
    private static final Logger a = Logger.getLogger(ku_2.class);
    private static arb_0 cQb = new arb_0();

    public static arb_0 aED() {
        return cQb;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 20151: {
                ey_2 ey_22 = (ey_2)pr_02;
                ps_1 ps_12 = (ps_1)azs_0.aLV().getProperty("demonAffiliationTrade").getValue();
                if (ps_12.acB()) {
                    wy_2 wy_22 = ey_22.hF();
                    ky_2 ky_22 = apN.aDK().Ln().yD();
                    wy_2 wy_23 = (wy_2)ky_22.bW(-wy_22.jf());
                    if (((xj)wy_22.NR()).tq()) {
                        add_1.aOG().a(aon_0.aYc().getString("coachInventory.undestructibleCard"), 66L, 102, 1);
                    } else {
                        for (int j = 0; j < ey_22.hG(); ++j) {
                            if ((wy_22.hG() == 0 || ky_22.bW(wy_22.jf()) == null) && wy_23 != null && wy_23.hG() > 0) {
                                wy_22 = wy_23;
                            }
                            ps_12.b(wy_22, (short)1);
                            wy_22.w((short)-1);
                        }
                        azs_0.aLV().a((aho_0)ps_12, "localCardExchange");
                        azs_0.aLV().a((aho_0)ps_12, "localCardsPrice");
                        azs_0.aLV().a((aho_0)ps_12, "canBuyCards");
                        afl_0 afl_02 = azs_0.aLV().getProperty("coachManagement.currentSet");
                        azs_0.aLV().a((aho_0)((fe_1)afl_02.getValue()), fe_1.aVa);
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.exchange.tooMuchCard", (byte)10), 66L, 102, 1);
                }
                return false;
            }
            case 20152: {
                ey_2 ey_23 = (ey_2)pr_02;
                ps_1 ps_13 = (ps_1)azs_0.aLV().getProperty("demonAffiliationTrade").getValue();
                ps_13.cq(ey_23.hF().je());
                sj_1 sj_12 = apN.aDK().Ln();
                wy_2 wy_24 = aoi_0.aXY().ac(ByteBuffer.wrap(ey_23.hF().cd()));
                wy_24.q((short)1);
                try {
                    sj_12.yD().f(wy_24);
                }
                catch (xR xR2) {
                    xR2.printStackTrace();
                }
                catch (gg gg2) {
                    gg2.printStackTrace();
                }
                azs_0.aLV().a((aho_0)ps_13, "localCardExchange");
                azs_0.aLV().a((aho_0)ps_13, "localCardsPrice");
                azs_0.aLV().a((aho_0)ps_13, "canBuyCards");
                afl_0 afl_03 = azs_0.aLV().getProperty("coachManagement.currentSet");
                azs_0.aLV().a((aho_0)((fe_1)afl_03.getValue()), fe_1.aVa);
                return false;
            }
            case 16820: {
                ki_0 ki_02 = (ki_0)pr_02;
                add_1.aOG().a("splitDemonAffiliationTradeDialog", oh_2.bq("splitDemonAffiliationTradeDialog"), null, false, ki_02.pi(), (int)ki_02.pj(), 1L, (short)10001);
                azs_0.aLV().g("itemToSplit", (short)1);
                return false;
            }
            case 20153: {
                ps_1 ps_14 = (ps_1)azs_0.aLV().getProperty("demonAffiliationTrade").getValue();
                Zu zu = new Zu();
                zu.bu((short)afl_1.aRM());
                zu.f((Object[])ps_14.getFieldValue("localCardExchange"));
                apN.aDK().vJ().b(zu);
                return false;
            }
        }
        return super.a(pr_02);
    }

    protected void X() {
        add_1.aOG().a("demonAffiliationDialog", oh_2.bq("demonAffiliationDialog"), (short)10000);
    }

    protected void W() {
        add_1.aOG().kO("demonAffiliationDialog");
        add_1.aOG().kO("splitDemonAffiliationTradeDialog");
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().l("dofusarena.demonAffiliation", ke_1.class);
            apN.aDK().a(au_1.aX());
        }
        super.a(fh_22, bl2);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kG("dofusarena.demonAffiliation");
            ps_1 ps_12 = (ps_1)azs_0.aLV().getProperty("demonAffiliationTrade").getValue();
            ps_12.clean();
        }
        super.b(fh_22, bl2);
    }
}

