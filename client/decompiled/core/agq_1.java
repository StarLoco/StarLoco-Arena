/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from agq
 */
public class agq_1
implements atG {
    private static agq_1 cuf = new agq_1();

    public static agq_1 awr() {
        return cuf;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 19501: {
                sb_0 sb_02 = (sb_0)pr_02;
                agp_1 agp_12 = new agp_1();
                agp_12.M((short)1);
                agp_12.e(bs_0.IF().II().tI());
                apN.aDK().vJ().b(agp_12);
                apN.aDK().a(lg_0.pU());
                return false;
            }
            case 19502: {
                apN.aDK().b(this);
                return false;
            }
            case 19503: {
                adj_0 adj_02 = new adj_0();
                apN.aDK().vJ().b(adj_02);
                apN.aDK().b(lg_0.pU());
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
            add_1.aOG().l("dofusarena.randomFightSearch", o.class);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            apN.aDK().b(lg_0.pU());
            add_1.aOG().kO("randomFightCreationDialog");
            add_1.aOG().kO("randomFightSearchStatusDialog");
            add_1.aOG().kG("dofusarena.randomFightSearch");
        }
    }
}

