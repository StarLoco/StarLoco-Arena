/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aBB
 */
public abstract class abb_1
implements atG {
    protected boolean dsb = true;

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 20008: {
                if (add_1.aOG().kR("menuDialog")) {
                    apN.aDK().b(fc_2.ia());
                } else {
                    apN.aDK().a(fc_2.ia());
                }
                return false;
            }
            case 20010: {
                if (add_1.aOG().kR("fightMenuDialog")) {
                    apN.aDK().b(fc_2.ia());
                } else {
                    apN.aDK().a(fc_2.ia());
                }
                return false;
            }
            case 20009: {
                apN.aDK().b(fc_2.ia());
                return false;
            }
            case 16427: {
                if (add_1.aOG().kR("reportBugDialog") || !this.dsb) {
                    add_1.aOG().kO("reportBugDialog");
                    apN.aDK().b(aOG.aYD());
                } else {
                    add_1.aOG().a("reportBugDialog", oh_2.bq("reportBugDialog"), 1025L, (short)19501);
                    apN.aDK().a(aOG.aYD());
                }
                return false;
            }
            case 16436: {
                if (add_1.aOG().kR("mailboxDialog")) {
                    apN.aDK().b(ayf_0.aKO());
                } else {
                    ajs_0 ajs_02 = new ajs_0();
                    apN.aDK().vJ().b(ajs_02);
                }
                return false;
            }
            case 16428: {
                add_1.aOG().kO("reportBugDialog");
                apN.aDK().b(aOG.aYD());
                return false;
            }
            case 20014: {
                if (add_1.aOG().kR("optionsDialog")) {
                    add_1.aOG().kO("optionsDialog");
                } else {
                    add_1.aOG().a("optionsDialog", oh_2.bq("optionsDialog"), 256L, (short)19501);
                }
                return false;
            }
            case 20015: {
                add_1.aOG().kO("optionsDialog");
                return false;
            }
            case 21010: {
                aqe_0 aqe_02 = (aqe_0)pr_02;
                aif_2 aif_22 = aqe_02.aEe();
                aif_22.execute();
                return false;
            }
        }
        return true;
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().aPa();
        }
    }
}

