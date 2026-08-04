/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from NO
 */
public class no_2
implements atG {
    protected static final Logger a = Logger.getLogger(no_2.class);
    private static no_2 bAz = new no_2();
    private static final aja_1 bAA = new aja_1();

    public static no_2 aaY() {
        return bAz;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 4096: {
                xe_2 xe_22 = (xe_2)pr_02;
                Iterable iterable = xe_22.alm();
                for (hy_0 hy_02 : iterable) {
                    no_2.g(hy_02.Ov());
                    xg_2.l(hy_02.Ov());
                }
                return false;
            }
            case 4098: {
                th_1 th_12 = (th_1)pr_02;
                qa_2 qa_22 = th_12.afR();
                int n2 = qa_22.size();
                for (int j = 0; j < n2; ++j) {
                    aez_0 aez_02 = (aez_0)bd_1.Is().ba(qa_22.get(j));
                    aez_02.aQw();
                    xg_2.m(aez_02);
                }
                return false;
            }
            case 4500: {
                avf_0 avf_02 = (avf_0)pr_02;
                abm_2 abm_22 = (abm_2)bd_1.Is().bb(avf_02.DJ());
                if (abm_22 != null) {
                    arh_0 arh_02 = avf_02.FJ();
                    ry ry2 = new ry(abm_22.aNU(), abm_22.aNV(), (short)abm_22.getAltitude());
                    ry ry3 = new ry(arh_02.aEI()[0], arh_02.aEI()[1], (short)arh_02.aEI()[2]);
                    qe_0 qe_02 = qe_0.adj();
                    qe_02.adk();
                    qe_02.t(ry2);
                    qe_02.u(ry3);
                    qe_02.a((int)abm_22.ge(), abm_22.ox(), abm_22.BP());
                    aen_0 aen_02 = new aen_0();
                    aen_02.cpL = true;
                    aen_02.cpK = true;
                    aen_02.cpH = false;
                    aen_02.cpI = 100;
                    aen_02.cpQ = true;
                    qe_02.a(aen_02);
                    bAA.reset();
                    auU.a(ry2.getX(), ry2.getY(), ry3.getX(), ry3.getY(), 9, bAA);
                    qe_02.a(bAA);
                    qe_02.ado();
                    arh_0 arh_03 = qe_02.FJ();
                    qe_02.release();
                    if (arh_03 != null && arh_03.aEG()) {
                        abm_22.a(arh_03, true, true);
                    } else {
                        a.warn((Object)"Pas de chemin trouv\u00e9 pour le chemin de l'acteur distant : on le t\u00e9l\u00e9porte");
                        abm_22.a(ry3.getX(), (double)ry3.getY(), (double)ry3.wk());
                    }
                } else {
                    a.error((Object)("Impossible de d\u00e9placer le personnage " + avf_02.DJ() + " car il n'existe pas !"));
                }
                return false;
            }
            case 4510: {
                xp_0 xp_02 = (xp_0)pr_02;
                abm_2 abm_23 = (abm_2)bd_1.Is().bb(xp_02.DJ());
                if (abm_23 != null) {
                    abm_23.a(xp_02.gO(), (double)xp_02.gP(), (double)xp_02.gQ());
                    if (abm_23.getId() == apN.aDK().Ln().getId()) {
                        qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
                        xx_1.a(qs_22, xp_02.gO(), xp_02.gP(), xp_02.gQ());
                    }
                } else {
                    a.error((Object)("Impossible de t\u00e9l\u00e9porter le personnage " + xp_02.DJ() + " car il n'existe pas !"));
                }
                return false;
            }
            case 4700: {
                azt_0 azt_02 = (azt_0)pr_02;
                mT mT2 = bd_1.Is().bb(azt_02.DJ());
                if (!qc_0.d(mT2.L())) {
                    mT2.b(qc_0.hf((mT2.L().getIndex() + 1) % 8));
                }
                mT2.aY(azt_02.aMw());
                return false;
            }
            case 200: {
                rz_2 rz_22 = (rz_2)pr_02;
                akz_0 akz_02 = rz_22.xY().eI();
                while (akz_02.hasNext()) {
                    akz_02.fK();
                    long l2 = akz_02.TO();
                    byte[] byArray = (byte[])akz_02.value();
                    a.trace((Object)("[INTERACTIVE_ELEMENT_SPAWN_MESSAGE] Element id=" + l2 + ", data.length=" + byArray.length + " byte(s)"));
                    do_1 do_12 = do_1.a(l2, byArray);
                    if (do_12 != null) {
                        ajX.azB().e(do_12);
                        continue;
                    }
                    a.error((Object)("spawn d'un element interactif inconnu id=" + l2));
                }
                return false;
            }
            case 206: {
                acc_2 acc_22 = (acc_2)pr_02;
                Iterator iterator = acc_22.aOr().iterator();
                while (iterator.hasNext()) {
                    long l3 = (Long)iterator.next();
                    do_1 do_13 = ajX.azB().dJ(l3);
                    if (do_13 != null) {
                        if (do_13.gv()) {
                            do_13.gj();
                            for (axu_0 axu_02 : do_13.aYW()) {
                                if (!(axu_02 instanceof hh_2)) continue;
                                ((hh_2)axu_02).a(new bw_1(this));
                            }
                            continue;
                        }
                        ajX.azB().b(do_13);
                        continue;
                    }
                    a.warn((Object)("Impossible de retirer un \u00e9l\u00e9ment interactif ID=" + l3 + ", il n'est r\u00e9f\u00e9renc\u00e9 dans aucune partition."));
                }
                return false;
            }
            case 4601: {
                aez_0 aez_03;
                int n3;
                afV afV2 = (afV)pr_02;
                long[] lArray = afV2.avM();
                for (n3 = 0; n3 < lArray.length; ++n3) {
                    aez_03 = (aez_0)bd_1.Is().bb(lArray[n3]);
                    if (aez_03 == null) continue;
                    aez_03.eM(true);
                    if (!qc_0.d(aez_03.L())) {
                        aez_03.b(qc_0.hf((aez_03.L().getIndex() + 1) % 8));
                    }
                    aez_03.aY("AnimAssis-Debut");
                    if (yq_2.Fa().Fb() == null || yq_2.Fa().Fc() != aez_03) continue;
                    add_1.aOG().aOU();
                }
                lArray = afV2.avN();
                for (n3 = 0; n3 < lArray.length; ++n3) {
                    aez_03 = (aez_0)bd_1.Is().bb(lArray[n3]);
                    if (aez_03 == null) continue;
                    aez_03.eM(false);
                    if (!qc_0.d(aez_03.L())) {
                        aez_03.b(qc_0.hf((aez_03.L().getIndex() + 1) % 8));
                    }
                    aez_03.aY("AnimAssis-Fin");
                }
                return false;
            }
            case 22092: {
                axA axA2 = (axA)pr_02;
                aez_0 aez_04 = (aez_0)bd_1.Is().bb(axA2.DJ());
                if (aez_04 != null) {
                    aez_04.cm(axA2.tw());
                }
                return false;
            }
            case 22094: {
                float[] fArray;
                la_1 la_12 = (la_1)pr_02;
                do_1 do_14 = ajX.azB().dJ(la_12.qs());
                sj_1 sj_12 = apN.aDK().Ln();
                xj xj2 = (xj)la_0.XJ().pj(la_12.qo());
                if (xj2 != null && xj2.tj() == aMK.dYz && (fArray = xj2.tk()).length == 3) {
                    byte by = xj2.tl();
                    int n4 = (do_14 == null ? 0 : do_14.gn()) + la_12.qp();
                    int n5 = (do_14 == null ? 0 : do_14.go()) + la_12.qq();
                    int n6 = (do_14 == null ? (short)0 : do_14.gp()) + la_12.qr() + ej_0.am(10) + 15;
                    float f = fArray[0] / 255.0f;
                    float f2 = fArray[1] / 255.0f;
                    float f3 = fArray[2] / 255.0f;
                    ado_0.aPH().a(by, n4, n5, n6, f, f2, f3);
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
        if (mT2 instanceof aez_0) {
            if (((aez_0)mT2).aQr()) {
                if (!qc_0.d(mT2.L())) {
                    mT2.b(qc_0.hf((mT2.L().getIndex() + 1) % 8));
                }
                mT2.aY("AnimAssis-Debut");
            }
            ((aez_0)mT2).aQv();
        }
        mT2.ayl();
        mT2.a(new bu_2());
    }
}

