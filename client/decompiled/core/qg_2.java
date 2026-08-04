/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from Qg
 */
public class qg_2
implements atG {
    protected static final Logger a = Logger.getLogger(qg_2.class);
    private static qg_2 bFT = new qg_2();

    public static qg_2 acV() {
        return bFT;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 4102: {
                aEV aEV2 = (aEV)pr_02;
                adu_0 adu_02 = apN.aDK().aDL();
                if (adu_02 != null) {
                    ArrayList<ee_2> arrayList = new ArrayList<ee_2>();
                    Iterable iterable = aEV2.aEc();
                    for (aam_0 aam_02 : iterable) {
                        Object object;
                        Object object2;
                        Object object3;
                        ee_2 ee_22 = (ee_2)adu_02.eg(aam_02.getId());
                        if (ee_22 != null) {
                            ee_22.m(aam_02.gO(), aam_02.gP(), aam_02.gQ());
                            ee_22.b(aam_02.L());
                            object3 = ee_22.NW();
                            ((mT)object3).aY("AnimStatique02");
                            ((ahh_1)object3).dW("AnimStatique02");
                            if (!((gq_2)object3).ap("AnimHit")) {
                                ((ahh_1)object3).lt("AnimHit02");
                            }
                            object2 = ((gq_2)object3).ap("AnimMarche02") ? ut_1.ahW() : ajy_0.aVw();
                            object = ((gq_2)object3).ap("AnimCourse02") ? awn_0.aJE() : bj_0.dx();
                            ((abm_2)object3).a(new Bb(amh_2.aBP(), (jp_1)object2, (jp_1)object));
                            ry ry2 = ee_22.gg();
                            for (int j = 0; j < arrayList.size(); ++j) {
                                ry ry3 = ((ee_2)arrayList.get(j)).gg();
                                if (ry3.getX() != ry2.getX() || ry3.getY() != ry2.getY()) continue;
                                if (ry3.wk() < ry2.wk()) {
                                    ((ee_2)arrayList.get(j)).i(ee_22);
                                    continue;
                                }
                                ee_22.i((gn_0)arrayList.get(j));
                            }
                            arrayList.add(ee_22);
                        } else {
                            object2 = adu_02.ef(aam_02.getId());
                            ny_2.sR().println("300|" + ny_2.cu(ny_2.Qn) + "|" + ny_2.au(aam_02.getId()) + "|" + aam_02.gO() + "|" + aam_02.gP() + "|" + aam_02.gQ() + "|" + aam_02.L() + "|");
                            object = (aez_0)object2;
                            ((abm_2)object).a(aam_02.gO(), (double)aam_02.gP(), (double)aam_02.gQ());
                            ((mT)object).b(aam_02.L());
                            object3 = object;
                        }
                        if (object3 != null) {
                            qg_2.g((mT)object3);
                            continue;
                        }
                        a.error((Object)("L'acteur " + aam_02.getId() + " est inconnu !"));
                    }
                } else {
                    a.error((Object)"Aucun fight !");
                }
                return false;
            }
            case 4104: {
                aya_0 aya_02 = (aya_0)pr_02;
                adu_0 adu_03 = apN.aDK().aDL();
                if (adu_03 != null) {
                    qa_2 qa_22 = aya_02.aKI();
                    int n2 = qa_22.size();
                    for (int j = 0; j < n2; ++j) {
                        long l2 = qa_22.get(j);
                        ee_2 ee_23 = (ee_2)adu_03.eg(l2);
                        if (ee_23 == null) continue;
                        vD vD2 = ee_23.NW();
                        if (vD2 != null) {
                            bd_1.Is().j(vD2);
                            continue;
                        }
                        a.error((Object)("L'acteur " + l2 + " est inconnu !"));
                    }
                } else {
                    a.error((Object)"Aucun fight !");
                }
                return false;
            }
            case 4106: {
                aqb_0 aqb_02 = (aqb_0)pr_02;
                adu_0 adu_04 = apN.aDK().aDL();
                if (adu_04 != null) {
                    Iterable iterable = aqb_02.aEc();
                    for (dx_1 dx_12 : iterable) {
                        ee_2 ee_24 = (ee_2)adu_04.eg(dx_12.getId());
                        if (ee_24 != null) {
                            ee_24.m(dx_12.gO(), dx_12.gP(), dx_12.gQ());
                            continue;
                        }
                        a.error((Object)("L'acteur " + dx_12.getId() + " est inconnu !"));
                    }
                } else {
                    a.error((Object)"Aucun fight !");
                }
                return false;
            }
            case 4500: {
                avf_0 avf_02 = (avf_0)pr_02;
                abm_2 abm_22 = (abm_2)bd_1.Is().bb(avf_02.DJ());
                if (abm_22 != null) {
                    arh_0 arh_02 = avf_02.FJ();
                    abm_22.a(arh_02, true, true);
                } else {
                    a.error((Object)("Impossible de d\u00e9placer le personnage " + avf_02.DJ() + " car il n'existe pas !"));
                }
                return false;
            }
            case 4510: {
                xp_0 xp_02 = (xp_0)pr_02;
                adu_0 adu_05 = apN.aDK().aDL();
                if (adu_05 != null) {
                    ee_2 ee_25 = (ee_2)adu_05.eg(xp_02.DJ());
                    if (ee_25 != null) {
                        qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
                        xx_1.a(qs_22, xp_02.gO(), xp_02.gP(), xp_02.gQ());
                        ee_25.m(xp_02.gO(), xp_02.gP(), xp_02.gQ());
                    } else {
                        a.error((Object)("Impossible de t\u00e9l\u00e9porter le personnage " + xp_02.DJ() + " car il n'existe pas !"));
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

    public void a(fh_2 fh_22, boolean bl2) {
    }

    public void b(fh_2 fh_22, boolean bl2) {
    }

    public static void g(mT mT2) {
        bd_1.Is().g(mT2);
        if (!mT2.isVisible()) {
            mT2.setVisible(true);
        }
        mT2.a(new pb_0());
    }
}

