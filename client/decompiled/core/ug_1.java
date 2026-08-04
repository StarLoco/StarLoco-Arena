/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import org.apache.log4j.Logger;

/*
 * Renamed from uG
 */
public class ug_1
implements atG {
    protected static final Logger a = Logger.getLogger(ug_1.class);
    private static ug_1 aqK = new ug_1();
    private boolean aqL = false;

    public static ug_1 AL() {
        return aqK;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 5104: {
                Ul ul = (Ul)pr_02;
                switch (ul.agI()) {
                    case 0: {
                        aez_0 aez_02 = (aez_0)bd_1.Is().bb(ul.agH());
                        CG cG = new CG(apN.aDK().Ln(), aez_02, true);
                        cG.start();
                        break;
                    }
                    case 3: {
                        nk nk2 = apN.aDK().Ln().aQt();
                        aez_0 aez_03 = (aez_0)bd_1.Is().bb(ul.agH());
                        nk2.c(aez_03);
                        break;
                    }
                    case 1: 
                    case 2: {
                        nk nk3 = apN.aDK().Ln().aQt();
                        if (nk3 != null) {
                            aez_0 aez_04 = (aez_0)bd_1.Is().bb(ul.agH());
                            nk3.a((adq_2)aez_04, (byte)0);
                        }
                        if (!this.aqL) {
                            add_1.aOG().a(aon_0.aYc().getString("exchange.invitation.canceled"), 1090L, 102, 1);
                        }
                        this.aqL = false;
                        break;
                    }
                }
                return false;
            }
            case 5102: {
                uo_1 uo_12 = (uo_1)pr_02;
                aez_0 aez_05 = (aez_0)bd_1.Is().bb(uo_12.ahO());
                CG cG = new CG(aez_05, apN.aDK().Ln(), false);
                cG.start();
                apN.aDK().Ln().c(cG);
                return false;
            }
            case 6025: {
                dy_2 dy_22 = (dy_2)pr_02;
                if (!apN.aDK().Ln().yP() && !mc_1.qM().qO().containsKey(dy_22.MG().toLowerCase())) {
                    r_0 r_02 = add_1.aOG().a(dy_22.MG() + aon_0.aYc().getString("teamManagement.createXvsXInvitation"), 1176L, 102, 1);
                    r_02.a(new aiu_1(this, dy_22));
                } else {
                    abB abB2 = new abB();
                    abB2.U(dy_22.MF());
                    abB2.T(dy_22.ME());
                    abB2.setName(dy_22.getName());
                    abB2.M((short)2);
                    abB2.cV(false);
                    apN.aDK().vJ().b(abB2);
                }
                return false;
            }
            case 6027: {
                add_1.aOG().a(aon_0.aYc().getString("teamManagement.cancelXvsXTeamCreation"), 1090L, 102, 1);
                return false;
            }
            case 6028: {
                DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.cmb, 2);
                apN.aDK().a(hu_2.li());
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
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public void aJ(boolean bl2) {
        this.aqL = bl2;
    }
}

