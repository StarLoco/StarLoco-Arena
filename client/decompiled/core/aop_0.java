/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from aop
 */
public class aop_0
implements atG {
    private static final Logger a = Logger.getLogger(aop_0.class);

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            // empty if block
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        Im im = (Im)pr_02.uy();
        if (!im.isConnected() || im.Ku().ayh() || im.Ku().ayg()) {
            return false;
        }
        switch (pr_02.getId()) {
            case 20: {
                rx_0 rx_02 = (rx_0)pr_02;
                int n2 = rx_02.xV();
                ko_0 ko_02 = jt_0.mn().aZ(n2);
                if (ko_02 != null) {
                    try {
                        ko_02.c(rx_02.getParameters());
                    }
                    catch (Exception exception) {
                        a.error((Object)("Exception lev\u00e9e lors de l'appel \u00e0 la commande d'admin : " + ko_02.getName()), (Throwable)exception);
                    }
                }
                bl2 = false;
                break;
            }
            case 12: {
                try {
                    pn_2 pn_22 = (pn_2)pr_02;
                    String[] stringArray = pn_22.getPropertyName().split("/");
                    mu mu2 = jN.nY().aH(stringArray[0]);
                    if (mu2 != null) {
                        if (mu2.rn().length == 0 && mu2.rm().length == 0) {
                            this.a(mu2, im, null, -1);
                        }
                        for (String string : mu2.rm()) {
                            if (stringArray.length >= 2 && !stringArray[1].equals(string)) continue;
                            for (int n3 : mu2.rn()) {
                                if (stringArray.length >= 3 && Integer.parseInt(stringArray[2]) != n3) continue;
                                this.a(mu2, im, string, n3);
                            }
                        }
                    }
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
                bl2 = false;
                break;
            }
            case 10: {
                try {
                    for (mu mu3 : jN.nY().oa()) {
                        if (mu3.rn().length == 0 && mu3.rm().length == 0) {
                            this.a(mu3, im, null, -1);
                        }
                        try {
                            for (String string : mu3.rm()) {
                                for (int n4 : mu3.rn()) {
                                    this.a(mu3, im, string, n4);
                                }
                            }
                        }
                        catch (Exception exception) {
                            a.error((Object)"Exception", (Throwable)exception);
                        }
                    }
                }
                catch (Exception exception) {
                    a.error((Object)"Exception", (Throwable)exception);
                }
                bl2 = false;
            }
        }
        return bl2;
    }

    private void a(mu mu2, Im im, String string, int n2) {
        Object object = mu2.k(string, n2);
        if (object != null) {
            try {
                afy_2 afy_22 = afy_2.avQ();
                afy_22.fF(mu2.getPropertyName());
                afy_22.ks(mu2.rl());
                afy_22.ic(string);
                afy_22.kt(n2);
                switch (mu2.rl()) {
                    case 1: {
                        afy_22.a((Byte)object);
                        break;
                    }
                    case 2: {
                        afy_22.bF((Short)object);
                        break;
                    }
                    case 3: {
                        afy_22.g((Integer)object);
                        break;
                    }
                    case 4: {
                        afy_22.e((Long)object);
                        break;
                    }
                    case 5: {
                        afy_22.a((Double)object);
                        break;
                    }
                    case 6: {
                        afy_22.c(((Float)object).floatValue());
                        break;
                    }
                    case 7: {
                        afy_22.b((String)object);
                    }
                }
                im.b(afy_22);
            }
            catch (Throwable throwable) {
                a.error((Object)("AdminFrame.sendMonitoredProoperty(stringIndex=" + string + ", intIndex=" + n2 + ") exception raised : "), throwable);
            }
        }
    }

    public long getId() {
        return 1L;
    }

    public void c(long l2) {
    }
}

