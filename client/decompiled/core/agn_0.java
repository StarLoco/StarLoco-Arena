/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from agn
 */
public class agn_0
extends avp_0 {
    private static Logger a = Logger.getLogger(agn_0.class);
    private static agn_0 cud = new agn_0();

    public static agn_0 awo() {
        return cud;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16702: {
                ia_2 ia_22 = (ia_2)pr_02;
                sj_1 sj_12 = ia_22.Ln();
                wy_2 wy_22 = ia_22.lm();
                wy_2 wy_23 = (wy_2)sj_12.yD().bW(-wy_22.jf());
                if (wy_23 != null) {
                    wy_22 = wy_23;
                }
                if (wy_22 != null) {
                    if (((xj)wy_22.NR()).tr() > apN.aDK().Ln().aQi()) {
                        add_1.aOG().a(aon_0.aYc().getString("error.equipment.levelToLow"), 66L, 102, 1);
                    }
                    short s = -1;
                    if (ia_22.ha() != -1) {
                        s = ia_22.ha();
                    } else {
                        short[] sArray = wy_22.tj().aXg();
                        if (sArray != null) {
                            if (sArray.length > 1) {
                                for (short s2 : sArray) {
                                    if (sj_12.yD().z(s2) != null) continue;
                                    s = s2;
                                    break;
                                }
                                if (s == -1) {
                                    for (short s2 : sArray) {
                                        wy_2 wy_24 = (wy_2)sj_12.yD().z(s2);
                                        if (wy_24 == null || wy_24.jf() == wy_22.jf()) continue;
                                        s = s2;
                                        break;
                                    }
                                }
                                if (s == -1) {
                                    s = sArray[0];
                                }
                            } else if (sArray.length != 0) {
                                s = sArray[0];
                            }
                        }
                    }
                    if (s != -1) {
                        sj_12.a(wy_22, s);
                    } else {
                        a.error((Object)("Position d'\u00e9quipement invalide : " + s));
                    }
                }
                return false;
            }
            case 16703: {
                ia_2 ia_23 = (ia_2)pr_02;
                sj_1 sj_13 = ia_23.Ln();
                wy_2 wy_25 = ia_23.lm();
                if (sj_13 != null && wy_25 != null) {
                    sj_13.d(wy_25);
                }
                return false;
            }
            case 16442: {
                sb_0 sb_02 = (sb_0)pr_02;
                OB oB = new OB();
                oB.i(sb_02.getIntValue());
                apN.aDK().vJ().b(oB);
                add_1.aOG().a(aon_0.aYc().getString("coachInventory.otherGameCardSent"), 1058L, 102, 1);
                return false;
            }
            case 16443: {
                sb_0 sb_03 = (sb_0)pr_02;
                Tx tx = new Tx();
                tx.i(sb_03.getIntValue());
                apN.aDK().vJ().b(tx);
                return false;
            }
            case 22002: {
                ls_0 ls_02 = (ls_0)pr_02;
                sj_1 sj_14 = apN.aDK().Ln();
                sj_14.b(ls_02.qI());
                if (!sj_14.c(qy_2.ce((short)275))) {
                    aea_1 aea_12 = new aea_1(qy_2.ce((short)275));
                    azs_0.aLV().g("tooltip.content", aea_12);
                } else if (!sj_14.c(qy_2.ce((short)276))) {
                    aea_1 aea_13 = new aea_1(qy_2.ce((short)276));
                    azs_0.aLV().g("tooltip.content", aea_13);
                } else if (!sj_14.c(qy_2.ce((short)284))) {
                    aea_1 aea_14 = new aea_1(qy_2.ce((short)284));
                    azs_0.aLV().g("tooltip.content", aea_14);
                } else if (!sj_14.c(qy_2.ce((short)278))) {
                    aea_1 aea_15 = new aea_1(qy_2.ce((short)278));
                    azs_0.aLV().g("tooltip.content", aea_15);
                } else if (!sj_14.c(qy_2.ce((short)277))) {
                    aea_1 aea_16 = new aea_1(qy_2.ce((short)277));
                    azs_0.aLV().g("tooltip.content", aea_16);
                } else if (!sj_14.qI().contains(or_0.YV.tI())) {
                    aea_1 aea_17 = new aea_1(qy_2.ce((short)279));
                    azs_0.aLV().g("tooltip.content", aea_17);
                }
                return false;
            }
            case 16721: {
                sb_0 sb_04 = (sb_0)pr_02;
                aJt aJt2 = (aJt)ER.OC().dZ(sb_04.aj());
                adT adT2 = new adT(null, aJt2);
                azs_0.aLV().g("tome.currentBreed", aon_0.aYc().a(10, sb_04.aj(), new Object[0]));
                azs_0.aLV().g("tome.currentBreedDescription", aon_0.aYc().a(11, sb_04.aj(), new Object[0]));
                azs_0.aLV().g("tome.currentBreedHelpDescription", aon_0.aYc().getString("summonDescription" + sb_04.aj()));
                azs_0.aLV().g("tome.currentFighter", adT2);
                return false;
            }
            case 16719: {
                sb_0 sb_05 = (sb_0)pr_02;
                abv_1 abv_12 = adY.atu().atv();
                abv_12.b((byte)-1, sb_05.aj(), (byte)0);
                abv_12.PK();
                azs_0.aLV().g("tome.currentBreed", aon_0.aYc().a(5, sb_05.aj(), new Object[0]));
                azs_0.aLV().g("tome.currentBreedDescription", aon_0.aYc().a(6, sb_05.aj(), new Object[0]));
                azs_0.aLV().g("tome.currentFighter", abv_12);
                return false;
            }
            case 16720: {
                da_1 da_12 = (da_1)pr_02;
                azs_0.aLV().g("coachManagement.selectedCard", da_12.fw());
                return false;
            }
            case 16610: {
                abb_2 abb_22 = (abb_2)pr_02;
                abv_1 abv_13 = (abv_1)abb_22.tG();
                if (abv_13 != null && abv_13.lZ() != abb_22.lZ()) {
                    abv_13.S(abb_22.lZ());
                    abv_13.NZ();
                }
                return false;
            }
        }
        return super.a(pr_02);
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            ArrayList arrayList = je_1.Wa().gu(xq.axU.lV());
            azs_0.aLV().g("tome.actionCards", arrayList.toArray());
        }
        super.a(fh_22, bl2);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            apN.aDK().Ln().yG();
            azs_0.aLV().kb("tome.currentBreed");
            azs_0.aLV().kb("tome.currentFighter");
            azs_0.aLV().kb("tome.currentBreedDescription");
            azs_0.aLV().kb("tome.actionCards");
        }
        super.b(fh_22, bl2);
    }

    protected void X() {
        add_1.aOG().a("cardBookDialog", oh_2.bq("cardBookDialog"), 1L, (short)10001);
    }

    protected void W() {
        add_1.aOG().kO("cardBookDialog");
    }
}

