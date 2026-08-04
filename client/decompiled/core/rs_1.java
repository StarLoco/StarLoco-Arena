/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from RS
 */
public class rs_1
extends tn_1 {
    private static rs_1 bKM = new rs_1();

    public static rs_1 aez() {
        return bKM;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16401: {
                byte by;
                sb_0 sb_02 = (sb_0)pr_02;
                sj_1 sj_12 = apN.aDK().Ln();
                if (sj_12 != null && (by = sb_02.aj()) != sj_12.lZ()) {
                    sj_12.S(by);
                    sj_12.bg(sj_12.aQd());
                    sj_12.bh(sj_12.aQe());
                    sj_12.yK();
                }
                return false;
            }
            case 16402: {
                byte by;
                sb_0 sb_03 = (sb_0)pr_02;
                sj_1 sj_13 = apN.aDK().Ln();
                if (sj_13 != null && (by = sb_03.aj()) != sj_13.aQd()) {
                    sj_13.bg(by);
                    azs_0.aLV().a((aho_0)sj_13, "actorDescriptorLibrary");
                }
                return false;
            }
            case 16403: {
                byte by;
                sb_0 sb_04 = (sb_0)pr_02;
                sj_1 sj_14 = apN.aDK().Ln();
                if (sj_14 != null && (by = sb_04.aj()) != sj_14.aQe()) {
                    sj_14.bh(by);
                    azs_0.aLV().a((aho_0)sj_14, "actorDescriptorLibrary");
                }
                return false;
            }
            case 16404: {
                sj_1 sj_15 = apN.aDK().Ln();
                if (sj_15 != null) {
                    sj_15.aQo();
                    azs_0.aLV().a((aho_0)sj_15, "skinColor");
                    azs_0.aLV().a((aho_0)sj_15, "hairColor");
                    azs_0.aLV().a((aho_0)sj_15, "sex");
                    azs_0.aLV().a((aho_0)sj_15, "actorDescriptorLibrary");
                    azs_0.aLV().a((aho_0)sj_15, "actorLinkage");
                }
                return false;
            }
            case 16400: {
                apm_1 apm_12 = (apm_1)pr_02;
                alq_0 alq_02 = new alq_0();
                alq_02.b(apm_12.Ln());
                apN.aDK().vJ().b(alq_02);
                return false;
            }
        }
        return super.a(pr_02);
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    protected void W() {
        add_1.aOG().kO("coachCreationDialog");
    }

    protected void X() {
        add_1.aOG().a("coachCreationDialog", oh_2.bq("coachCreationDialog"), 0L, (short)10000);
    }
}

