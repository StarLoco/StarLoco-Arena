/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from QU
 */
public class qu_2
implements atG {
    private static qu_2 bHW = new qu_2();

    public static qu_2 adx() {
        return bHW;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 21050: {
                aym_0 aym_02 = new aym_0();
                aym_02.fK(((sb_0)pr_02).getStringValue());
                apN.aDK().vJ().b(aym_02);
                return false;
            }
            case 21051: {
                QZ qZ = new QZ();
                qZ.fK(((sb_0)pr_02).getStringValue());
                apN.aDK().vJ().b(qZ);
                return false;
            }
            case 21052: {
                aer_0 aer_02 = new aer_0();
                aer_02.fo(((sb_0)pr_02).getStringValue());
                apN.aDK().vJ().b(aer_02);
                return false;
            }
            case 21053: {
                MP mP = new MP();
                mP.fo(((sb_0)pr_02).getStringValue());
                apN.aDK().vJ().b(mP);
                return false;
            }
            case 21054: {
                sj_1 sj_12 = apN.aDK().Ln();
                ca_0 ca_02 = sj_12.aPY();
                if (ca_02 != null && ca_02.Kg().aQY()) {
                    uq_2 uq_22 = new uq_2();
                    uq_22.as(ca_02.Kd());
                    uq_22.l(kG.Fi.lV());
                    uq_22.cw(((sb_0)pr_02).getStringValue());
                    uq_22.u(false);
                    apN.aDK().vJ().b(uq_22);
                }
                return false;
            }
        }
        return true;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            so_0 so_02;
            po_0.abV().abW();
            ca_0 ca_02 = apN.aDK().Ln().aPY();
            if (ca_02 != null) {
                so_02 = new add_2(ca_02.Kd());
                apN.aDK().vJ().b(so_02);
            }
            add_1.aOG().a("socialDialog", oh_2.bq("socialDialog"), (short)10000);
            add_1.aOG().l("dofusarena.social", uk_1.class);
            so_02 = new auZ(apN.aDK().Ln().getId());
            apN.aDK().vJ().b(so_02);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        add_1.aOG().kO("socialDialog");
        add_1.aOG().kO("guildManagementDialog");
        add_1.aOG().kG("dofusarena.social");
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }
}

