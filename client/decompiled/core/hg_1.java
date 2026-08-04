/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from HG
 */
public class hg_1
implements atG {
    private static hg_1 bfo = new hg_1();

    public static hg_1 Tr() {
        return bfo;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            apN.aDK().a(avu_0.aIB());
            apN.aDK().a(of_1.th());
            apN.aDK().a(qg_2.acV());
            apN.aDK().a(do_2.Mm());
            apN.aDK().b(po_0.abV());
            add_1.aOG().kO("fightMenuBarDialog");
            add_1.aOG().l("dofusarena.replay", wr_2.class);
            azs_0.aLV().g("replayMode", true);
            if (!azs_0.aLV().getBooleanProperty("tutorialMode")) {
                apN.aDK().a(bo_1.Ik());
            } else {
                sb_0 sb_02 = new sb_0();
                sb_02.f(31000);
                acu_1.ara().c(sb_02);
            }
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            azs_0.aLV().kb("replayPaused");
            azs_0.aLV().g("replayMode", false);
            add_1.aOG().kO("replayDialog");
            add_1.aOG().kO("replayIdentificationCertificate");
            add_1.aOG().kO("fightEventCardsDialog");
            add_1.aOG().kO("timelineDialog");
            add_1.aOG().kO("menuDialog");
            add_1.aOG().kG("dofusarena.replay");
        }
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 31000: {
                apN.aDK().b(bo_1.Ik());
                azs_0.aLV().g("fight.timeline", apN.aDK().aDL().ass());
                azs_0.aLV().g("fight.eventCards", apN.aDK().aDL().asv().toArray());
                azs_0.aLV().g("replayPaused", true);
                azs_0.aLV().g("replayFastForward", false);
                add_1.aOG().a("replayDialog", oh_2.bq("replayDialog"), 1L, (short)9000);
                add_1.aOG().a("replayIdentificationCertificate", oh_2.bq("replayIdentificationCertificate"), 1L, (short)9000);
                add_1.aOG().a("fightEventCardsDialog", oh_2.bq("fightEventCardsDialog"), 1L, (short)10000);
                add_1.aOG().a("timelineDialog", oh_2.bq("timelineDialog"), 1L, (short)10000);
                RO.aer().aes();
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

