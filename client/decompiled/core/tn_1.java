/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from tn
 */
public abstract class tn_1
implements atG {
    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16405: {
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 != null) {
                    int n2 = (sj_12.L().getIndex() + 1) % 8;
                    sj_12.b(qc_0.hf(n2));
                    azs_0.aLV().a((aho_0)sj_12, "actorDirection");
                }
                return false;
            }
            case 16406: {
                sj_1 sj_13 = apN.aDK().Ln();
                if (sj_13 != null) {
                    int n3 = (sj_13.L().getIndex() + 7) % 8;
                    sj_13.b(qc_0.hf(n3));
                    azs_0.aLV().a((aho_0)sj_13, "actorDirection");
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
        if (!bl2) {
            add_1.aOG().l("dofusarena.coachManagement", aBC.class);
            this.X();
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kG("dofusarena.coachManagement");
            this.W();
        }
    }

    protected abstract void X();

    protected abstract void W();
}

