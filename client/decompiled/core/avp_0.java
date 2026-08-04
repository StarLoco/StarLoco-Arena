/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from avp
 */
public abstract class avp_0
extends tn_1 {
    private r_0 dek = null;

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16700: {
                ia_2 ia_22 = (ia_2)pr_02;
                wy_2 wy_22 = ia_22.lm();
                if (wy_22 != null) {
                    azs_0.aLV().g("coachManagement.selectedCard", wy_22);
                }
                return false;
            }
            case 16701: {
                azs_0.aLV().g("coachManagement.selectedCard", (Object)null);
                return false;
            }
            case 16717: {
                ia_2 ia_23 = (ia_2)pr_02;
                sj_1 sj_12 = ia_23.Ln();
                wy_2 wy_23 = ia_23.lm();
                if (sj_12 != null && wy_23 != null) {
                    if (((xj)wy_23.NR()).tq()) {
                        add_1.aOG().a(aon_0.aYc().getString("coachInventory.undestructibleCard"), 1058L, 102, 1);
                    } else {
                        String string = aon_0.aYc().getString("question.deleteCoachEquipment", wy_23.hG(), wy_23.getName());
                        this.dek = add_1.aOG().a(string, 1176L, 102, 1);
                        this.dek.a(new aJh(this, sj_12, wy_23));
                    }
                }
                return false;
            }
            case 16718: {
                ia_2 ia_24 = (ia_2)pr_02;
                sj_1 sj_13 = ia_24.Ln();
                wy_2 wy_24 = ia_24.lm();
                if (sj_13 != null && wy_24 != null) {
                    sj_13.f(wy_24);
                }
                return false;
            }
        }
        return super.a(pr_02);
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            po_0.abV().abW();
            azs_0.aLV().g("coachManagement.errors", "");
            apN.aDK().Ln().yH();
            hc_2.kI().k("world", false);
        }
        super.a(fh_22, bl2);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            azs_0.aLV().g("coachManagement.errors", "");
            if (this.dek != null) {
                this.dek.D();
                this.dek = null;
            }
            int n2 = 0;
            for (atG atG2 : apN.aDK().ii()) {
                if (!(atG2 instanceof avp_0)) continue;
                ++n2;
            }
            if (n2 == 0) {
                apN.aDK().Ln().yI();
            }
            hc_2.kI().k("world", apN.aDK().aDL() == null);
        }
        super.b(fh_22, bl2);
    }

    static /* synthetic */ r_0 a(avp_0 avp_02, r_0 r_02) {
        avp_02.dek = r_02;
        return avp_02.dek;
    }
}

