/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from ku
 */
public class ku_2
extends avp_0 {
    private static final Logger a = Logger.getLogger(ku_2.class);
    private static ku_2 Ek = new ku_2();
    public static final int El = 0;
    public static final int Em = 1;
    private int En;

    public static ku_2 oU() {
        return Ek;
    }

    public void bH(int n2) {
        this.En = n2;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16435: {
                apN.aDK().b(this);
                return false;
            }
            case 16900: {
                ey_2 ey_22 = (ey_2)pr_02;
                aJd aJd2 = (aJd)azs_0.aLV().getProperty("cardMasterTrade").getValue();
                if (aJd2.acB()) {
                    wy_2 wy_22 = ey_22.hF();
                    ky_2 ky_22 = apN.aDK().Ln().yD();
                    int n2 = wy_22.jf();
                    wy_2 wy_23 = (wy_2)ky_22.bW(-n2);
                    short s = ey_22.hG();
                    if (((xj)wy_22.NR()).tq()) {
                        add_1.aOG().a(aon_0.aYc().getString("coachInventory.undestructibleCard"), 66L, 102, 1);
                    } else {
                        aJd2.k(n2, s);
                        short s2 = (short)Math.min(s, wy_22.hG());
                        if (wy_23 != null) {
                            short s3 = (short)(s - s2);
                            wy_23.w(-s3);
                        }
                        wy_22.w(-s2);
                        azs_0.aLV().a((aho_0)aJd2, "localCardExchange");
                        azs_0.aLV().a((aho_0)aJd2, "localCardsPrice");
                        azs_0.aLV().a((aho_0)aJd2, "canBuyCards");
                        afl_0 afl_02 = azs_0.aLV().getProperty("coachManagement.currentSet");
                        azs_0.aLV().a((aho_0)((fe_1)afl_02.getValue()), fe_1.aVa);
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("error.exchange.tooMuchCard", (byte)6), 66L, 102, 1);
                }
                return false;
            }
            case 16901: {
                ey_2 ey_23 = (ey_2)pr_02;
                aJd aJd3 = (aJd)azs_0.aLV().getProperty("cardMasterTrade").getValue();
                aJd3.oP(ey_23.hF().jf());
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
                azs_0.aLV().a((aho_0)aJd3, "localCardExchange");
                azs_0.aLV().a((aho_0)aJd3, "localCardsPrice");
                azs_0.aLV().a((aho_0)aJd3, "canBuyCards");
                afl_0 afl_03 = azs_0.aLV().getProperty("coachManagement.currentSet");
                azs_0.aLV().a((aho_0)((fe_1)afl_03.getValue()), fe_1.aVa);
                return false;
            }
            case 16820: {
                ki_0 ki_02 = (ki_0)pr_02;
                add_1.aOG().a("splitCardMasterTradeDialog", oh_2.bq("splitCardMasterTradeDialog"), null, false, ki_02.pi(), (int)ki_02.pj(), 1L, (short)10001);
                azs_0.aLV().g("itemToSplit", (short)1);
                return false;
            }
            case 16902: {
                aJd aJd4 = (aJd)azs_0.aLV().getProperty("cardMasterTrade").getValue();
                int n3 = aJd4.aVp().jf();
                if (((xj)la_0.XJ().pj(n3)).isUnique() && (apN.aDK().Ln().aQn().bT(n3) != null || apN.aDK().Ln().aQn().bW(n3) != null)) {
                    add_1.aOG().a(aon_0.aYc().getString("error.cardmaster.cantByUniqueCard"), 66L, 102, 1);
                    return false;
                }
                aOo aOo2 = new aOo();
                aOo2.f((Object[])aJd4.getFieldValue("localCardExchange"));
                aOo2.gH(n3);
                aOo2.gI(aJd4.aaW());
                apN.aDK().vJ().b(aOo2);
                return false;
            }
            case 16903: {
                add_1.aOG().a("cardPurchaseConfirmationDialog", oh_2.bq("cardPurchaseConfirmationDialog"), 256L, (short)10000);
                return false;
            }
            case 16905: {
                aJd aJd5 = (aJd)azs_0.aLV().getProperty("cardMasterTrade").getValue();
                aim_1 aim_12 = apN.aDK().Ln().rs();
                ArrayList arrayList = ((xj)aJd5.aVp().NR()).DG();
                boolean bl2 = true;
                for (int j = 0; j < arrayList.size(); ++j) {
                    AS aS = (AS)arrayList.get(j);
                    if (aim_12.aD(aS.getType()) >= aS.getCount()) continue;
                    bl2 = false;
                    break;
                }
                if (bl2) {
                    mo_2 mo_22 = new mo_2();
                    mo_22.gH(aJd5.aVp().jf());
                    mo_22.gI(aJd5.aaW());
                    apN.aDK().vJ().b(mo_22);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("notEnoughTokens"), 1091L, 102, 1);
                }
                return false;
            }
            case 16904: {
                if (add_1.aOG().kR("cardPurchaseConfirmationDialog")) {
                    add_1.aOG().kO("cardPurchaseConfirmationDialog");
                } else {
                    add_1.aOG().a("cardPurchaseConfirmationDialog", oh_2.bq("cardPurchaseConfirmationDialog"), 256L, (short)10000);
                }
                return false;
            }
        }
        return super.a(pr_02);
    }

    protected void X() {
        if (this.En == 0) {
            add_1.aOG().a("cardMasterDialog", oh_2.bq("cardMasterDialog"), (short)10000);
        } else {
            add_1.aOG().a("demonIIDialog", oh_2.bq("demonIIDialog"), (short)10000);
        }
    }

    protected void W() {
        add_1.aOG().kO("cardMasterDialog");
        add_1.aOG().kO("splitCardMasterTradeDialog");
        add_1.aOG().kO("demonIIDialog");
        add_1.aOG().kO("cardPurchaseConfirmationDialog");
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().l("dofusarena.cardMaster", tq_1.class);
        }
        super.a(fh_22, bl2);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            apN.aDK().b(kv_2.WF());
        }
        super.b(fh_22, bl2);
    }
}

