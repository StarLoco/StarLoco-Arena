/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

/*
 * Renamed from agz
 */
public class agz_1
implements atG {
    private static final agz_1 cup = new agz_1();

    public static agz_1 awu() {
        return cup;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        add_1.aOG().a("infosDialog", oh_2.bq("infosDialog"), (short)10000);
        add_1.aOG().l("dofusarena.infos", dw_2.class);
    }

    public void b(fh_2 fh_22, boolean bl2) {
        add_1.aOG().kO("infosDialog");
        add_1.aOG().kG("dofusarena.infos");
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 21070: {
                fz_0 fz_02 = (fz_0)pr_02;
                aan_1 aan_12 = fz_02.jk();
                if (aan_12.jt()) {
                    iz_1.Vg().c(aan_12);
                }
                switch (aan_12.getType()) {
                    case 3: 
                    case 4: 
                    case 5: {
                        break;
                    }
                    case 2: {
                        add_1.aOG().a(aon_0.aYc().getString("goCheckYourMailbox"), 1058L, 102, 1);
                        break;
                    }
                    case 1: {
                        if (add_1.aOG().kR("tournamentsSearchStatusDialog")) break;
                        long l2 = ((td_0)aan_12).fx();
                        vg vg2 = vk_1.BZ().aQ(l2);
                        short s = vg2.Bw();
                        azs_0.aLV().g("selectedTournamentInformation", vg2);
                        azs_0.aLV().g("selectedTournamentClientInfos", aan_12);
                        aub aub2 = LS.Yf().gG(s);
                        if (aub2 != null && aub2.aHh() == aql_0.cOF) {
                            DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.cmb, 0);
                            apN.aDK().a(hu_2.li());
                            azs_0.aLV().g("onlyTabEnabledId", (byte)0);
                        } else if (aub2 != null && aub2.aHh() == aql_0.cOG) {
                            en_2.fk(aql_0.cOG);
                            apN.aDK().a(en_2.Na());
                        } else if (aub2 != null && aub2.aHh() == aql_0.cOH) {
                            DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.cmb, 4);
                            apN.aDK().a(hu_2.li());
                            azs_0.aLV().g("onlyTabEnabledId", (byte)4);
                        } else {
                            jk_1.mf().clear();
                            jk_1.mf().d(WN.A(vg2.By()));
                            jk_1.mf().md();
                            DofusArenaClientInstance.yl().aod().a((ro_2)adc_0.cmb, 3);
                            apN.aDK().a(hu_2.li());
                            azs_0.aLV().g("onlyTabEnabledId", (byte)3);
                        }
                        vk_1.ad(l2);
                        break;
                    }
                    case 6: {
                        add_1.aOG().a(((gc_2)aan_12).getDescription(), 1058L, 102, 1);
                        break;
                    }
                    case 7: {
                        tu_2 tu_22 = (tu_2)aan_12;
                        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("questionWatchFinale", tu_22.agb()), 1177L, 102, 1);
                        r_02.a(new op_2(this, tu_22));
                        break;
                    }
                    case 8: {
                        add_1.aOG().a("il reste : " + ((apd)aan_12).aDl() + " minutes", 1058L, 102, 1);
                    }
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
}

