/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import org.apache.log4j.Logger;

/*
 * Renamed from by
 */
public class by_2
implements atG {
    protected static final Logger a = Logger.getLogger(by_2.class);
    private static by_2 gN = new by_2();

    public static by_2 db() {
        return gN;
    }

    public boolean a(pr_0 pr_02) {
        apN.aDK().dQ(true);
        switch (pr_02.getId()) {
            case 1024: {
                Uk uk = (Uk)pr_02;
                if (uk.agF()) {
                    apN.aDK().dB(true);
                    apN.aDK().b(this);
                    apN.aDK().b(yy_0.amI());
                    apN.aDK().a(aog_1.aCQ());
                    apN.aDK().a(pq_1.acy());
                    apN.aDK().a(add_0.atb());
                    apN.aDK().a(om_0.tQ());
                    apN.aDK().a(yq_2.Fa());
                    apN.aDK().a(md_0.Yp());
                    apN.aDK().a(lh_1.XX());
                    apN.aDK().a(mq_0.Ys());
                    apN.aDK().a(asA.aFA());
                    apN.aDK().a(zN.GN());
                    apN.aDK().a(pe_2.ack());
                    apN.aDK().a(ajp_0.azc());
                    add_1.aOG().kO("logonDialog");
                    azs_0 azs_02 = azs_0.aLV();
                    boolean bl2 = (Boolean)azs_02.getProperty("account.remember").getValue();
                    String string = (String)azs_02.getProperty("account.name").getValue();
                    DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.clL, bl2 ? string : "");
                    azs_02.kb("account.name");
                    azs_02.kb("account.password");
                    azs_02.kb("account.remember");
                    azs_02.kb("proxy.list");
                    azs_02.kb("proxy.selected");
                    azs_0.aLV().g("onlyTabEnabledId", (byte)-1);
                    pm_0.ur().done();
                    nW.start();
                } else {
                    String string;
                    int n2;
                    String string2;
                    apN.aDK().aQ(null);
                    apN.aDK().setPassword(null);
                    apN.aDK().dB(false);
                    if (uk.agG() < 60) {
                        string2 = aon_0.aYc().getString("minutes");
                        n2 = uk.agG();
                    } else if (uk.agG() < 1440) {
                        string2 = aon_0.aYc().getString("hours");
                        n2 = uk.agG() / 60;
                    } else {
                        string2 = aon_0.aYc().getString("days");
                        n2 = uk.agG() / 1440;
                    }
                    switch (uk.an() & 0xFF) {
                        case 2: {
                            String string3 = DofusArenaClientInstance.yl().aod().f(adc_0.clL);
                            azs_0.aLV().g("account.name", string3);
                            string = aon_0.aYc().getString("error.connection.invalidLogin");
                            break;
                        }
                        case 3: {
                            string = aon_0.aYc().getString("error.connection.alreadyConnected");
                            break;
                        }
                        case 15: {
                            string = aon_0.aYc().getString("error.connection.saveInProgress");
                            break;
                        }
                        case 127: {
                            string = aon_0.aYc().getString("error.connection.closedBeta");
                            break;
                        }
                        case 73: {
                            string = aon_0.aYc().getString("error.connection.limitedAccessServer");
                            break;
                        }
                        case 8: {
                            string = aon_0.aYc().getString("error.connection.serverIsFull");
                            break;
                        }
                        case 5: {
                            string = aon_0.aYc().getString("error.connection.banned") + " " + n2 + " " + string2 + ".";
                            break;
                        }
                        case 6: {
                            string = aon_0.aYc().getString("error.connection.banned") + " " + n2 + " " + string2 + ".";
                            break;
                        }
                        default: {
                            string = aon_0.aYc().getString("error.connection.invalidLogin");
                        }
                    }
                    add_1.aOG().a(string, 1091L, 5, 2);
                    apN.aDK().vJ().closeConnection();
                }
                return false;
            }
            case 1026: {
                apN.aDK().aQ(null);
                apN.aDK().setPassword(null);
                apN.aDK().dB(false);
                add_1.aOG().a(aon_0.aYc().getString("logon.notConnectedToServer"), 1091L, 5, 2);
                apN.aDK().vJ().closeConnection();
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
}

