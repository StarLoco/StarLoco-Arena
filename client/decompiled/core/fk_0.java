/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from fK
 */
public class fk_0
implements atG {
    private static fk_0 rN = new fk_0();

    public static fk_0 jo() {
        return rN;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 18011: {
                nS nS2 = new nS();
                apN.aDK().vJ().b(nS2);
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
        if (!bl2) {
            add_1.aOG().a("fightObservationDialog", oh_2.bq("fightObservationDialog"), 1L, (short)10000);
            azs_0.aLV().g("fight.status", "observation");
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().a("fightEventCardsDialog", oh_2.bq("fightEventCardsDialog"), 1L, (short)10000);
            add_1.aOG().kO("fightObservationDialog");
            azs_0.aLV().g("fight.status", "action");
        }
    }
}

