/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.AbstractCollection;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/*
 * Renamed from aoG
 */
public class aog_1
implements atG {
    protected static final Logger a = Logger.getLogger(aog_1.class);
    private static aog_1 cLf = new aog_1();

    public static aog_1 aCQ() {
        return cLf;
    }

    public boolean a(pr_0 pr_02) {
        DofusArenaClientInstance dofusArenaClientInstance = DofusArenaClientInstance.yl();
        switch (pr_02.getId()) {
            case 4600: {
                Object object;
                aec_2 aec_22 = (aec_2)pr_02;
                short s = xx_1.Em();
                sj_1 sj_12 = apN.aDK().Ln();
                if (aec_22.aQC() == s && (float)sj_12.gn() == aec_22.aQD() && (float)sj_12.go() == aec_22.aQE()) {
                    return false;
                }
                azs_0.aLV().g("currentInstanceId", "map" + aec_22.aQC());
                qs_2 qs_22 = dofusArenaClientInstance.YP();
                if (sj_12 != null) {
                    bd_1.Is().k(sj_12);
                }
                qs_22.ao(false);
                ajX.azB().azC();
                xx_1.ai(aec_22.aQC());
                xx_1.a(qs_22, (int)aec_22.aQD(), (int)aec_22.aQE(), aec_22.gQ());
                hc_2.kI().k("common", true);
                bg_2 bg_22 = aCH.aOu().cl(aec_22.aQC());
                int n2 = s != Short.MIN_VALUE ? 1000 : 0;
                String string = aon_0.aYc().getString("loading");
                ia_1.TJ().a(n2, 1000, string);
                if (aec_22.isDynamic()) {
                    nk_0.aaq().ce(2L);
                    object = new et_0(aec_22.aQD(), aec_22.aQE(), aec_22.gQ());
                    qs_22.d((Du)object);
                    qs_22.vs();
                    apN.aDK().b(pq_1.acy());
                    apN.aDK().b(po_0.abV());
                    apN.aDK().b(wp_2.Dl());
                    apN.aDK().b(no_2.aaY());
                    apN.aDK().b(ft_1.jr());
                    apN.aDK().b(hu_2.li());
                    apN.aDK().b(B.V());
                    apN.aDK().b(ug_1.AL());
                    apN.aDK().b(agn_0.awo());
                    apN.aDK().b(ajp_0.azc());
                    apN.aDK().b(afl_1.aRK());
                    apN.aDK().b(ce_1.IU());
                    apN.aDK().b(ahg_1.aTk());
                    apN.aDK().b(ju_1.mp());
                    apN.aDK().b(agz_1.awu());
                    azs_0.aLV().g("tools", aol_1.aCS().aJ((byte)1).toArray());
                    hc_2.kI().k("world", false);
                    hc_2.kI().k("fight", true);
                    qs_22.k(1.0);
                    ny_2.sR().println("200|" + aec_22.aQC() + "|" + aec_22.aQD() + "|" + aec_22.aQE() + "|" + aec_22.gQ() + "|");
                } else {
                    Object object2;
                    Object object3;
                    nk_0.aaq().ce(1L);
                    ahA.axi().a(ado_0.aPH());
                    object = vr_0.aiM().aiP();
                    while (object != null && !((AbstractCollection)object).isEmpty()) {
                        object3 = (akb_2)((LinkedList)object).getFirst();
                        object2 = ((akb_2)object3).pe(jl_0.bjI.lV());
                        ((akb_2)object3).kill();
                        if (object2 == null) continue;
                        ((Eq)object2).run();
                    }
                    if (sj_12 == null) {
                        a.error((Object)"Impossible d'entrer dans le monde si aucun localCoach n'est d\u00e9fini !");
                        return false;
                    }
                    sj_12.a(aec_22.aQD(), aec_22.aQE(), (double)aec_22.gQ());
                    sj_12.aTt();
                    no_2.g(sj_12);
                    xg_2.l(sj_12);
                    hc_2.kI().k("world", true);
                    hc_2.kI().k("fight", false);
                    qs_22.d(sj_12);
                    qs_22.vs();
                    apN.aDK().a(pq_1.acy());
                    apN.aDK().a(po_0.abV());
                    apN.aDK().a(wp_2.Dl());
                    apN.aDK().a(no_2.aaY());
                    apN.aDK().a(ft_1.jr());
                    apN.aDK().a(aap_2.aMJ());
                    apN.aDK().a(ug_1.AL());
                    apN.aDK().a(ajp_0.azc());
                    apN.aDK().a(afl_1.aRK());
                    apN.aDK().a(aef_0.atz());
                    apN.aDK().a(agz_1.awu());
                    azs_0.aLV().g("tools", aol_1.aCS().aJ((byte)0).toArray());
                    qs_22.k(1.0);
                    if (adY.atu().amq().isEmpty()) {
                        azs_0.aLV().g("teamManagementOpen", false);
                        add_1.aOG().a("teamManagementDialog", oh_2.bq("teamManagementDialog"), 1L, (short)19000);
                    }
                    if (!sj_12.c(avq_0.ce((short)456))) {
                        object3 = new aae_2();
                        apN.aDK().vJ().b((pr_0)object3);
                        object2 = new nq();
                        ((nq)object2).K(or_0.Zg.tI());
                        ((nq)object2).ab(true);
                        ((nq)object2).L((short)1);
                        apN.aDK().vJ().b((pr_0)object2);
                    }
                }
                object = new fq_1(this, dofusArenaClientInstance, qs_22, bg_22);
                if (yb_2.amk().aml()) {
                    yb_2.amk().a((NE)object);
                } else {
                    object.iG();
                }
                qs_22.an(true);
                qs_22.bk(true);
                return false;
            }
            case 4516: {
                auv_0.ek(false);
                Object[] objectArray = ajX.azB().xY().getValues();
                for (int j = 0; j < objectArray.length; ++j) {
                    do_1 do_12 = (do_1)objectArray[j];
                    if (!do_12.y(apN.aDK().Ln().aTI())) continue;
                    do_12.b(avr_0.dgp, apN.aDK().Ln());
                }
                return false;
            }
            case 4800: {
                yd yd2 = (yd)pr_02;
                a.info((Object)"ex\u00e9cution du script");
                if (anr_0.aXN().j(yd2.eA(), true) != null) {
                    anr_0.aXN().a(yd2.eA(), 0, yd2.EG(), false);
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
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }
}

