/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;

/*
 * Renamed from wP
 */
public class wp_2
implements atG {
    private static final Logger a = Logger.getLogger(wp_2.class);
    private static wp_2 avz = new wp_2();
    private static float avA = 0.05f;
    private long avB;
    private boolean avC;

    public static wp_2 Dl() {
        return avz;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 30002: {
                afl_0 afl_02 = azs_0.aLV().getProperty("coachCanMove");
                if (afl_02 == null || ((Boolean)afl_02.getValue()).booleanValue()) {
                    this.avC = true;
                    acl_1 acl_12 = (acl_1)pr_02;
                    this.ad(acl_12.au(), acl_12.av());
                }
                return false;
            }
            case 30000: {
                ado ado2 = (ado)pr_02;
                int n2 = -1;
                int n3 = -1;
                if (DofusArenaClientInstance.yl().aod().a(adc_0.clW)) {
                    n2 = 1;
                    n3 = 3;
                } else {
                    n2 = 3;
                    n3 = 1;
                }
                if (ado2.aqY() == n2) {
                    afl_0 afl_03 = azs_0.aLV().getProperty("coachCanMove");
                    if ((afl_03 == null || ((Boolean)afl_03.getValue()).booleanValue()) && !auv_0.aHC()) {
                        this.ad(ado2.au(), ado2.av());
                    }
                } else if (ado2.aqY() == n3) {
                    qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
                    sj_1 sj_12 = apN.aDK().Ln();
                    ArrayList arrayList = ((xu_2)qs_22).bd(ado2.au(), ado2.av());
                    aiu_0 aiu_02 = null;
                    if (arrayList != null && arrayList.size() != 0) {
                        Object object;
                        boolean bl2 = false;
                        for (aiu_0 aiu_03 : arrayList) {
                            if (!(aiu_03 instanceof tp_1)) continue;
                            bl2 = true;
                            aiu_02 = aiu_03;
                            break;
                        }
                        if (bl2) {
                            object = ((tp_1)aiu_02).zp();
                            if (object.dR() != null) {
                                switch (object.dR()) {
                                    case dgg: {
                                        ((apn_0)object).b(object.dR(), sj_12);
                                    }
                                }
                            }
                        } else {
                            object = (mT)arrayList.get(0);
                            if (((ahh_1)object).getId() != apN.aDK().Ln().getId() && object instanceof aez_0) {
                                HashMap hashMap;
                                aiu_0 aiu_03;
                                aiu_03 = (aez_0)object;
                                awC awC2 = add_1.aOG().aOT();
                                awC2.a(((aez_0)aiu_03).Ld(), (akq_1)null);
                                HashMap hashMap2 = mc_1.qM().qN();
                                if (hashMap2 != null) {
                                    if (!hashMap2.containsKey(((aez_0)object).Ld().toLowerCase())) {
                                        awC2.a(aon_0.aYc().getString("chat.addToFriendList"), null, new wj(this, (aez_0)aiu_03), true);
                                    } else {
                                        awC2.a(aon_0.aYc().getString("chat.removeFromFriendList"), null, new vj_1(this, (aez_0)aiu_03), true);
                                    }
                                }
                                if ((hashMap = mc_1.qM().qO()) != null) {
                                    if (!hashMap.containsKey(((aez_0)object).Ld().toLowerCase())) {
                                        awC2.a(aon_0.aYc().getString("chat.addToIgnoreList"), null, new vh_0(this, (aez_0)aiu_03), true);
                                        awC2.a(aon_0.aYc().getString("chat.reportIncorrectBehaviour"), null, new vO(this, (aez_0)aiu_03), true);
                                    } else {
                                        awC2.a(aon_0.aYc().getString("chat.removeFromIgnoreList"), null, new vL(this, (aez_0)aiu_03), true);
                                    }
                                }
                                py_0 py_02 = new py_0();
                                py_02.am(((ahh_1)object).getId());
                                apN.aDK().vJ().b(py_02);
                                yq_2 yq_22 = yq_2.Fa();
                                yq_22.b((aez_0)aiu_03);
                                yq_22.a(awC2);
                            }
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    private boolean ad(int n2, int n3) {
        sj_1 sj_12 = apN.aDK().Ln();
        qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
        if (sj_12 != null) {
            int n4 = (int)Math.min(500.0f, (float)(System.currentTimeMillis() - this.avB) * avA);
            if (n4 == 0) {
                return false;
            }
            aen_0 aen_02 = new aen_0();
            aen_02.cpI = n4;
            aen_02.cpK = true;
            aen_02.cpH = false;
            aen_02.cpQ = true;
            arh_0 arh_02 = MJ.a(qs_22, sj_12, n2, n3, aen_02, null);
            if (arh_02 != null && arh_02.aEF() > 0) {
                sj_12.a(arh_02, false, true);
                aLY aLY2 = new aLY();
                aLY2.a(sj_12.aNR());
                ans_0.a(aLY2);
                sj_12.a(arh_02, false, true);
                this.avB = System.currentTimeMillis();
            }
            return true;
        }
        return false;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        xu_2 xu_22 = (xu_2)DofusArenaClientInstance.yl().YP();
        xu_22.cE(true);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        xu_2 xu_22 = (xu_2)DofusArenaClientInstance.yl().YP();
        xu_22.cE(false);
    }

    static /* synthetic */ Logger Dm() {
        return a;
    }
}

