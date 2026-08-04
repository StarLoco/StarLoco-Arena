/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.chat.console.command.RaybanCommand;
import org.apache.log4j.Logger;

/*
 * Renamed from akS
 */
public class aks_2
implements atG {
    private static final Logger a = Logger.getLogger(aks_2.class);
    private static final aks_2 cEj = new aks_2();

    public static aks_2 aAh() {
        return cEj;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            po_0.abV().abW();
            apN.aDK().a(adi_2.ash());
            add_1.aOG().l("dofusarena.graveyard", vc_0.class);
            aij_0.aUF().c(null);
            apN.aDK().Ln().yH();
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        add_1.aOG().kO("graveyardDialog");
        add_1.aOG().kG("dofusarena.graveyard");
        apN.aDK().Ln().yG();
        apN.aDK().b(adi_2.ash());
        apN.aDK().Ln().yI();
        RaybanCommand.uninitialize();
    }

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
            case 23060: {
                ayd_0 ayd_02 = (ayd_0)pr_02;
                if (apN.aDK().c(afb_1.auN())) {
                    apN.aDK().b(afb_1.auN());
                    return false;
                }
                ee_2 ee_22 = ayd_02.tG();
                int n2 = ee_22.NH();
                Ei ei = (Ei)akp_1.aVO().aW(n2);
                if (ei == null) {
                    a.error((Object)("Il n'existe pas de board d'id " + n2));
                    return false;
                }
                ei.fi(ee_22.NC() - 1);
                ei.fj(ee_22.ND() - 1);
                azs_0.aLV().g("sphereboard.fighter", ee_22);
                afb_1.auN().j(ee_22.getId());
                afb_1.auN().setSphereBoard(ei);
                apN.aDK().a(afb_1.auN());
                return false;
            }
            case 23056: {
                avP avP2 = (avP)pr_02;
                mb_0.Yl().hide();
                qa_2 qa_22 = xz_0.amc().cF(apN.aDK().Ln().getId());
                int n3 = 0;
                for (long l2 : qa_22.adg()) {
                    ee_2 ee_23 = adY.atu().dz(l2);
                    if (ee_23 == null || ee_23.NB() != 1) continue;
                    n3 = (short)(n3 + 1);
                }
                Object object = avP2.hF();
                if (((xj)((eb_1)object).NR()).DF()) {
                    if (n3 < 7) {
                        r_0 r_02 = add_1.aOG().a(aon_0.aYc().getString("questionUseItemOnFighter", ((xj)la_0.XJ().pj(avP2.getIntValue())).getName()), 24L, 102, 0);
                        r_02.a(new aoM(this, avP2, (wy_2)object));
                    } else {
                        add_1.aOG().a(aon_0.aYc().getString("error.evolution.tooManyFightersOnBench"), 1090L, 4, 1);
                    }
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("noConsumableEffectOnDeadFighter"), 1090L, 4, 1);
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

