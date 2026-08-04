/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aAp
 */
public class aap_2
implements atG {
    private static aap_2 dpk = new aap_2();

    public static aap_2 aMJ() {
        return dpk;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 19000: {
                abl_2 abl_22;
                cx_1 cx_12 = (cx_1)pr_02;
                if (!azs_0.aLV().getBooleanProperty("chat.isMaximize")) {
                    azs_0.aLV().g("chat.isMaximize", true);
                }
                if ((abl_22 = ahv_0.aUv().aUx()) != null) {
                    abl_22.acY().iS(cx_12.getMessage());
                }
                return false;
            }
            case 19001: {
                asg asg2 = (asg)pr_02;
                abl_2 abl_23 = (abl_2)azs_0.aLV().getProperty("chat.selectedView").getValue();
                asg2.aFc().setOpen(asg2.aFd());
                if (asg2.aFd()) {
                    asg2.aFc().akQ().a(abl_23);
                } else {
                    asg2.aFc().akQ().b(abl_23);
                }
                abl_23.Zk();
                return false;
            }
            case 19007: {
                boolean bl2 = azs_0.aLV().getBooleanProperty("chat.isMaximize");
                azs_0.aLV().g("chat.isMaximize", !bl2);
                return false;
            }
            case 19005: {
                um_2 um_22 = (um_2)pr_02;
                axa_0 axa_02 = um_22.agJ();
                if (axa_02 != null) {
                    axa_02.setNotify(!axa_02.aJM());
                }
                return false;
            }
            case 19006: {
                nk_1 nk_12 = (nk_1)pr_02;
                rV rV2 = nk_12.aaP();
                if (rV2 != null) {
                    this.d(rV2);
                }
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

    private void d(pL pL2) {
        afl_0 afl_02 = azs_0.aLV().getProperty("chat.dialogView");
        afl_02.a("input", (Object)("/w \"" + pL2.getName() + "\" "));
    }
}

