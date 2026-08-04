/*
 * Decompiled with CFR 0.152.
 */
public class B
extends hu_2 {
    private static B ao = new B();
    private long ap;

    public static B V() {
        return ao;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16601: 
            case 16602: 
            case 16604: 
            case 16605: 
            case 16606: 
            case 16609: 
            case 16610: 
            case 16611: 
            case 16612: 
            case 16613: 
            case 16614: 
            case 16615: 
            case 16616: 
            case 16618: 
            case 16619: 
            case 16620: 
            case 16621: 
            case 16622: 
            case 16623: 
            case 16624: 
            case 16625: 
            case 16626: 
            case 16627: 
            case 16629: 
            case 16630: 
            case 16631: 
            case 16632: 
            case 16633: 
            case 16634: 
            case 16635: 
            case 16636: 
            case 24000: {
                add_1.aOG().a(aon_0.aYc().getString("error.fight.creation.badClick"), 1090L, 102, 1);
                return false;
            }
            case 16617: {
                kd_0 kd_02 = (kd_0)pr_02;
                zK zK2 = bs_0.IF().at(kd_02.qY());
                zK zK3 = bs_0.IF().II();
                if (zK2 != null && (zK3 == null || kd_02.qY() != zK3.tI())) {
                    bs_0.IF().d(zK.a(zK2));
                    azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset1vs1List");
                    azs_0.aLV().a((aho_0)bs_0.IF(), "teamManagement.teamPreset2vs2List");
                }
                return false;
            }
            case 20004: {
                mz_0 mz_02 = new mz_0();
                mz_02.d(this.ap);
                apN.aDK().vJ().b(mz_02);
                apN.aDK().a(ft_1.jr());
                apN.aDK().b(do_2.Mm());
                apN.aDK().b(B.V());
                apN.aDK().b(this);
                return false;
            }
            case 16600: {
                PV pV = (PV)pr_02;
                if (pV.GJ() == null) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                if (pV.GJ().isEmpty()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                if (!pV.GJ().afH()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmptyForACoach"), 1090L, 102, 1);
                    return false;
                }
                int n2 = pV.GJ().getValue();
                if (n2 > 6000) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.budgetExploded", n2, 6000), 1090L, 102, 1);
                    return false;
                }
                apN.aDK().a(do_2.Mm());
                bl_1 bl_12 = new bl_1();
                bl_12.d(pV.Y());
                bl_12.C(pV.GJ().tI());
                apN.aDK().vJ().b(bl_12);
                apN.aDK().b(this);
                r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("fight.creation.waitingForOpponentCoach"), 1156L, 102, 1);
                r_02.a(new atc_0(this));
                return false;
            }
            case 23051: {
                ahn ahn2 = (ahn)pr_02;
                if (ahn2.GJ() == null) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                if (ahn2.GJ().isEmpty()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                if (!ahn2.GJ().afH()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmptyForACoach"), 1090L, 102, 1);
                    return false;
                }
                int n3 = ahn2.GJ().getValue();
                if (n3 > 6000) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.budgetExploded", n3, 6000), 1090L, 102, 1);
                    return false;
                }
                apN.aDK().a(do_2.Mm());
                bl_1 bl_13 = new bl_1();
                bl_13.d(ahn2.Y());
                bl_13.C(ahn2.GJ().tI());
                apN.aDK().vJ().b(bl_13);
                apN.aDK().b(this);
                r_0 r_03 = add_1.aOG().a(aon_0.aYc().getString("fight.creation.waitingForOpponentCoach"), 1156L, 102, 1);
                r_03.a(new atb(this));
                return false;
            }
            case 23111: {
                zi_0 zi_02 = (zi_0)pr_02;
                if (zi_02.GJ() == null) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                if (zi_02.GJ().isEmpty()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmpty"), 1090L, 102, 1);
                    return false;
                }
                if (!zi_02.GJ().afH()) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.teamEmptyForACoach"), 1090L, 102, 1);
                    return false;
                }
                int n4 = zi_02.GJ().getValue();
                if (n4 > 6000) {
                    add_1.aOG().a(aon_0.aYc().getString("error.teamManagement.budgetExploded", n4, 6000), 1090L, 102, 1);
                    return false;
                }
                apN.aDK().a(do_2.Mm());
                bl_1 bl_14 = new bl_1();
                bl_14.d(zi_02.Y());
                bl_14.C(zi_02.GJ().tI());
                apN.aDK().vJ().b(bl_14);
                apN.aDK().b(this);
                r_0 r_04 = add_1.aOG().a(aon_0.aYc().getString("fight.creation.waitingForOpponentCoach"), 1156L, 102, 1);
                r_04.a(new ath(this));
                return false;
            }
        }
        return super.a(pr_02);
    }

    protected void W() {
        azs_0.aLV().g("teamManagementOpen", false);
    }

    protected void X() {
        azs_0.aLV().g("teamManagementOpen", true);
    }

    public void a(fh_2 fh_22, boolean bl2) {
        super.a(fh_22, bl2);
        acx_2.deactivateInterface();
    }

    public void b(fh_2 fh_22, boolean bl2) {
        super.b(fh_22, bl2);
        acx_2.activateInterface();
    }

    public void d(long l2) {
        this.ap = l2;
    }

    public long Y() {
        return this.ap;
    }

    static /* synthetic */ long a(B b) {
        return b.ap;
    }
}

