/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from AO
 */
public class ao_2
implements atG {
    private static ao_2 aIh = new ao_2();

    public static ao_2 HG() {
        return aIh;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        add_1.aOG().a("npcTalkDialog", oh_2.bq("npcTalkDialog"), (short)10000);
        add_1.aOG().l("dofusarena.npcTalk", TN.class);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        add_1.aOG().kO("npcTalkDialog");
        add_1.aOG().kG("dofusarena.npcTalk");
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 17001: {
                wm_0 wm_02 = (wm_0)pr_02;
                ana_2 ana_22 = wm_02.CE();
                if (ana_22.aCc() != null) {
                    ana_22.aCc().ut();
                }
                if (ana_22.aCa() != 0) {
                    azs_0.aLV().g("selectedTalkOption", Rq.aX(ana_22.aCa()));
                } else {
                    apN.aDK().b(this);
                }
                return false;
            }
            case 17002: {
                sb_0 sb_02 = (sb_0)pr_02;
                apN.aDK().b(this);
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
}

