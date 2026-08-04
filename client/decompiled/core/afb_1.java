/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from afb
 */
public class afb_1
implements atG {
    private static afb_1 cqj = new afb_1();
    private Ei coi = null;
    private long aj;

    public static afb_1 auN() {
        return cqj;
    }

    public Ei auO() {
        return this.coi;
    }

    public void setSphereBoard(Ei ei) {
        this.coi = ei;
        if (this.coi != null) {
            this.coi.MQ();
        }
    }

    public long K() {
        return this.aj;
    }

    public void j(long l2) {
        this.aj = l2;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16921: {
                ac_2 ac_22 = (ac_2)pr_02;
                ayr_0 ayr_02 = ac_22.bd();
                if (ayr_02.azm()) {
                    azs_0.aLV().g("sphereboard.selectedSphere", ayr_02);
                    if (ayr_02.el() != 0) {
                        azs_0.aLV().g("sphereboard.sphereSpell", je_1.Wa().el(ayr_02.el()));
                    }
                }
                return false;
            }
            case 16923: {
                ac_2 ac_23 = (ac_2)pr_02;
                ahr_2 ahr_22 = ac_23.be();
                ayr_0 ayr_03 = this.coi.MR();
                ee_2 ee_22 = adY.atu().dz(this.aj);
                short s = ayr_03.aux();
                short s2 = ayr_03.auy();
                ayr_0 ayr_04 = (ayr_0)this.coi.X(s, s2);
                ahr_22.setTokenPixelPosition((s - 1) * this.coi.getCellWidth(), (s2 - 1) * this.coi.getCellHeight());
                this.coi.fi(s - 1);
                this.coi.fj(s2 - 1);
                azs_0.aLV().g("sphereboard.selectedSphere", ayr_04);
                if (ayr_04.el() != 0) {
                    azs_0.aLV().g("sphereboard.sphereSpell", je_1.Wa().el(ayr_04.el()));
                }
                aow_2 aow_22 = new aow_2();
                aow_22.lE(ayr_04.getId());
                aow_22.j(this.aj);
                aow_22.i(ac_23.bf());
                apN.aDK().vJ().b(aow_22);
                ee_22.a(ayr_04, ahr_22, true);
                ahr_22.setZoomToOne(s - 1, s2 - 1);
                return false;
            }
            case 16924: {
                ac_2 ac_24 = (ac_2)pr_02;
                ahr_2 ahr_23 = ac_24.be();
                ayr_0 ayr_05 = (ayr_0)azs_0.aLV().getProperty("sphereboard.selectedSphere").getValue();
                ahr_23.setZoomToOne(ayr_05.aux() - 1, ayr_05.auy() - 1);
                return false;
            }
            case 16925: {
                ac_2 ac_25 = (ac_2)pr_02;
                ahr_2 ahr_24 = ac_25.be();
                ahr_24.setZoomToOne(this.coi.MU() - 1, this.coi.MV() - 1);
                return false;
            }
            case 16927: {
                ac_2 ac_26 = (ac_2)pr_02;
                ahr_2 ahr_25 = ac_26.be();
                ahr_25.setZoomToFullView();
                return false;
            }
            case 16928: {
                ac_2 ac_27 = (ac_2)pr_02;
                ahr_2 ahr_26 = ac_27.be();
                if (ahr_26.getZoom() != 1.0f) {
                    ahr_26.setZoomBack();
                }
                return false;
            }
            case 16926: {
                int n2;
                ac_2 ac_28 = (ac_2)pr_02;
                ahr_2 ahr_27 = ac_28.be();
                ayr_0 ayr_06 = ac_28.bd();
                ee_2 ee_23 = adY.atu().dz(this.aj);
                wy_2 wy_22 = (wy_2)azs_0.aLV().getProperty("sphereboard.selectedCard").getValue();
                ArrayList arrayList = this.coi.b(ayr_06);
                if (arrayList == null) {
                    return false;
                }
                if (!ayr_06.azm()) {
                    return false;
                }
                int n3 = n2 = ee_23.NE().contains(ayr_06.getId()) ? ayr_06.aus() / 10 : ayr_06.aus();
                if (!ee_23.c(ayr_06)) {
                    int n4 = n2 - ee_23.Nx();
                    String string = aon_0.aYc().getString("notEnoughXpToBuySphere", n4);
                    add_1.aOG().a(string, 2L, 102, 0);
                    return false;
                }
                if (!(!ayr_06.aKZ().equals("Barrier") || ayr_06.aKY() || wy_22 != null && ayr_06.auv().contains(wy_22.jf()))) {
                    String string = aon_0.aYc().getString("useCardToUnlockSphere", n2);
                    add_1.aOG().a(string, 2L, 102, 0);
                    return false;
                }
                String string = aon_0.aYc().getString("buySphereQuestion", n2);
                r_0 r_02 = add_1.aOG().a(string, 24L, 102, 0);
                r_02.a(new awu_0(this, ayr_06, wy_22, ee_23, ahr_27, arrayList, ac_28));
                return false;
            }
            case 16929: 
            case 16930: {
                int n5;
                int n6;
                int n7 = -1;
                if (pr_02.getId() == 16930) {
                    n7 = 1;
                }
                long[] lArray = xz_0.amc().afE().eJ();
                for (n6 = 0; n6 < lArray.length && lArray[n6] != this.aj; ++n6) {
                }
                ee_2 ee_24 = null;
                byte by = adY.atu().dz(lArray[n6]).NB();
                for (n5 = 1; n5 < lArray.length; ++n5) {
                    if (by != adY.atu().dz(lArray[(lArray.length + n6 + n5 * n7) % lArray.length]).NB()) continue;
                    ee_24 = adY.atu().dz(lArray[(lArray.length + n6 + n5 * n7) % lArray.length]);
                    break;
                }
                if (ee_24 != null) {
                    this.coi.MX();
                    n5 = ee_24.NH();
                    Ei ei = (Ei)akp_1.aVO().aW(n5);
                    ei.fi(ee_24.NC() - 1);
                    ei.fj(ee_24.ND() - 1);
                    azs_0.aLV().g("sphereboard.fighter", ee_24);
                    afb_1.auN().j(ee_24.getId());
                    afb_1.auN().setSphereBoard(ei);
                    aji_1 aji_12 = add_1.aOG().azj().lh("sphereBoardDialog");
                    ahr_2 ahr_28 = (ahr_2)aji_12.R("sphereBoard");
                    ahr_28.setSphereBoard(this.coi);
                    if (ahr_28.getZoom() != 1.0f) {
                        ahr_28.setZoomToOne(this.coi.MU() - 1, this.coi.MV() - 1);
                    }
                    azs_0.aLV().g("sphereboard.selectedSphere", this.coi.X(this.coi.MU(), this.coi.MV()));
                }
                return false;
            }
        }
        return true;
    }

    public void l(ee_2 ee_22) {
        this.coi.MX();
        int n2 = ee_22.NH();
        Ei ei = (Ei)akp_1.aVO().aW(n2);
        ei.fi(ee_22.NC() - 1);
        ei.fj(ee_22.ND() - 1);
        azs_0.aLV().g("sphereboard.fighter", ee_22);
        afb_1.auN().j(ee_22.getId());
        afb_1.auN().setSphereBoard(ei);
        aji_1 aji_12 = add_1.aOG().azj().lh("sphereBoardDialog");
        ahr_2 ahr_22 = (ahr_2)aji_12.R("sphereBoard");
        ahr_22.setSphereBoard(this.coi);
        if (ahr_22.getZoom() != 1.0f) {
            ahr_22.setZoomToOne(this.coi.MU() - 1, this.coi.MV() - 1);
        }
        azs_0.aLV().g("sphereboard.selectedSphere", this.coi.X(this.coi.MU(), this.coi.MV()));
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().a("sphereBoardDialog", oh_2.bq("sphereBoardDialog"), 1L, (short)10000);
            aji_1 aji_12 = add_1.aOG().azj().lh("sphereBoardDialog");
            ahr_2 ahr_22 = (ahr_2)aji_12.R("sphereBoard");
            ahr_22.setSphereBoard(this.coi);
            azs_0.aLV().g("sphereboard.selectedSphere", this.coi.X(this.coi.MU(), this.coi.MV()));
            if (apN.aDK().c(hu_2.li())) {
                azs_0.aLV().g("teamManagementOpen", false);
                if (add_1.aOG().kR("fighterEvolutionEquipmentDialog")) {
                    add_1.aOG().kO("fighterEvolutionEquipmentDialog");
                }
            } else if (apN.aDK().c(aks_2.aAh())) {
                add_1.aOG().kO("graveyardDialog");
            }
            add_1.aOG().l("dofusarena.sphereBoard", og_2.class);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().kO("sphereBoardDialog");
            add_1.aOG().kO("sphereDetailsDialog");
            if (apN.aDK().c(hu_2.li())) {
                azs_0.aLV().g("teamManagementOpen", true);
            } else if (apN.aDK().c(aks_2.aAh())) {
                add_1.aOG().a("graveyardDialog", oh_2.bq("graveyardDialog"), (short)10000);
            }
            azs_0.aLV().kb("sphereboard.selectedSphere");
            azs_0.aLV().kb("sphereboard.sphereSpell");
            add_1.aOG().kG("dofusarena.sphereBoard");
            this.coi.MX();
            this.coi = null;
        }
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    static /* synthetic */ long a(afb_1 afb_12) {
        return afb_12.aj;
    }

    static /* synthetic */ Ei b(afb_1 afb_12) {
        return afb_12.coi;
    }
}

