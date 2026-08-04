/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from of
 */
public class of_1
implements atG {
    protected static final Logger a = Logger.getLogger(of_1.class);
    private static of_1 QL = new of_1();

    public static of_1 th() {
        return QL;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 8100: {
                jg_1 jg_12 = (jg_1)pr_02;
                byte by = jg_12.N().lV();
                sY sY2 = new sY(jg_12.Ao(), by, jg_12.M());
                sY2.b(jg_12.oQ());
                sY2.aa(jg_12.Vw());
                vr_0.aiM().b(sY2);
                vr_0.aiM().aiQ();
                if (jg_12.oQ() != null) {
                    ny_2.sR().println("401|" + ny_2.cu(ny_2.Qk) + "|" + jg_12.Ao() + "|" + by + "|" + jg_12.M() + "|" + jg_12.oQ().getId() + "|");
                }
                return false;
            }
            case 8104: {
                kw_2 kw_22 = (kw_2)pr_02;
                byte by = kw_22.N().lV();
                azz_0 azz_02 = new azz_0(kw_22.Ao(), by, kw_22.M());
                azz_02.bB(kw_22.K());
                vr_0.aiM().b(azz_02);
                vr_0.aiM().aiQ();
                ny_2.sR().println("402|" + ny_2.cu(ny_2.Qj) + "|" + kw_22.Ao() + "|" + by + "|" + kw_22.M() + "|" + kw_22.K() + "|");
                return false;
            }
            case 8106: {
                TJ tJ = (TJ)pr_02;
                byte by = tJ.N().lV();
                pd_0 pd_02 = new pd_0(tJ.Ao(), by, tJ.M());
                pd_02.bB(tJ.K());
                vr_0.aiM().b(pd_02);
                vr_0.aiM().aiQ();
                ny_2.sR().println("407|" + ny_2.cu(ny_2.Qj) + "|" + tJ.Ao() + "|" + by + "|" + tJ.M() + "|" + tJ.K() + "|");
                return false;
            }
            case 4522: {
                u_0 u_02 = (u_0)pr_02;
                byte by = u_02.N().lV();
                wl_2 wl_22 = new wl_2(u_02.Ao(), by, u_02.M(), u_02.L());
                wl_22.bB(u_02.K());
                vr_0.aiM().b(wl_22);
                vr_0.aiM().aiQ();
                ny_2.sR().println("412|" + ny_2.cu(ny_2.Qi) + "|" + u_02.Ao() + "|" + by + "|" + u_02.M() + "|" + u_02.L() + "|" + u_02.K() + "|");
                return false;
            }
            case 6200: {
                jD jD2 = (jD)pr_02;
                byte by = jD2.N().lV();
                aor aor2 = new aor(jD2.Ao(), by, jD2.M(), jD2.mQ(), jD2.mT());
                aor2.bB(jD2.mR());
                aor2.bC(jD2.mS());
                akb_2 akb_22 = vr_0.aiM().b(aor2);
                aor2.a(new xj_2(akb_22));
                return false;
            }
            case 4506: {
                acg acg2 = (acg)pr_02;
                byte by = acg2.N().lV();
                xo_1 xo_12 = new xo_1(acg2.Ao(), by, acg2.M());
                xo_12.bB(acg2.aqG());
                xo_12.bC(acg2.aqF());
                vr_0.aiM().b(xo_12);
                ny_2.sR().println("411|" + ny_2.cu(ny_2.Qm) + "|" + acg2.Ao() + "|" + by + "|" + acg2.M() + "|" + acg2.aqG() + "|" + acg2.aqF() + "|");
                return false;
            }
            case 4524: {
                if (abt_1.aNp().aNr() || alx_2.aWN().aNr()) {
                    apN.aDK().b(alx_2.aWN());
                    apN.aDK().b(abt_1.aNp());
                }
                yr_1 yr_12 = (yr_1)pr_02;
                HB hB = new HB(yr_12.Ao(), yr_12.N().lV(), yr_12.M(), yr_12.K(), yr_12.FJ());
                hB.bC(yr_12.K());
                vr_0.aiM().b(hB);
                S.as().c(true);
                ny_2.sR().print("400|" + ny_2.cu(ny_2.Qh * (1 + (yr_12.FJ().aEF() > 3 ? 1 : 0))) + "|" + yr_12.Ao() + "|" + yr_12.N().lV() + "|" + yr_12.M() + "|" + yr_12.K() + "|" + yr_12.FJ().aEF() + "|");
                for (int j = 0; j < yr_12.FJ().aEF(); ++j) {
                    ny_2.sR().print(yr_12.FJ().lU(j)[0] + "," + yr_12.FJ().lU(j)[1] + "," + yr_12.FJ().lU(j)[2] + "|");
                }
                ny_2.sR().println("");
                return false;
            }
            case 8108: {
                arn_0 arn_02 = (arn_0)pr_02;
                byte by = arn_02.N().lV();
                tz_1 tz_12 = new tz_1(arn_02.Ao(), by, arn_02.M(), arn_02.aEq(), arn_02.wi(), arn_02.wj(), arn_02.no(), arn_02.aEr(), arn_02.aEs(), arn_02.aEt());
                akb_2 akb_23 = vr_0.aiM().b(tz_12);
                tz_12.a(new xj_2(akb_23));
                ny_2.sR().println("409|" + ny_2.cu(ny_2.Qf) + "|" + arn_02.Ao() + "|" + by + "|" + arn_02.M() + "|" + arn_02.aEq().getId() + "|" + arn_02.wi() + "|" + arn_02.wj() + "|" + arn_02.no() + "|" + arn_02.aEr() + "|" + arn_02.aEs() + "|" + arn_02.aEt() + "|");
                return false;
            }
            case 8112: {
                aAD aAD2 = (aAD)pr_02;
                byte by = aAD2.N().lV();
                akg_2 akg_22 = new akg_2(aAD2.Ao(), by, aAD2.M(), aAD2.wi(), aAD2.wj(), aAD2.no(), aAD2.aEr(), aAD2.aEs(), aAD2.aEt());
                akb_2 akb_24 = vr_0.aiM().b(akg_22);
                akg_22.a(new xj_2(akb_24));
                ny_2.sR().println("405|" + ny_2.cu(ny_2.Qf) + "|" + aAD2.Ao() + "|" + by + "|" + aAD2.M() + "|" + aAD2.wi() + "|" + aAD2.wj() + "|" + aAD2.no() + "|" + aAD2.aEr() + "|" + aAD2.aEs() + "|" + aAD2.aEt() + "|");
                return false;
            }
            case 8110: {
                axn_0 axn_02 = (axn_0)pr_02;
                byte by = axn_02.N().lV();
                yp_2 yp_22 = (yp_2)je_1.Wa().el(axn_02.el());
                aly_0 aly_02 = new aly_0(axn_02.Ao(), by, axn_02.M(), yp_22, axn_02.wi(), axn_02.wj(), axn_02.aJX(), axn_02.aJY(), axn_02.aJZ(), axn_02.aKa(), true);
                akb_2 akb_25 = vr_0.aiM().b(aly_02);
                aly_02.a(new xj_2(akb_25));
                ny_2.sR().println("406|" + ny_2.cu(ny_2.Qf) + "|" + axn_02.Ao() + "|" + by + "|" + axn_02.M() + "|" + axn_02.el() + "|" + axn_02.wi() + "|" + axn_02.wj() + "|" + axn_02.aJX() + "|" + axn_02.aJY() + "|" + axn_02.aJZ() + "|" + axn_02.aKa() + "|" + true + "|");
                return false;
            }
            case 8120: {
                amb_0 amb_02 = (amb_0)pr_02;
                byte by = amb_02.N().lV();
                el_2 el_22 = (el_2)mh_2.YJ().cr(amb_02.aey());
                if (el_22 == null) {
                    a.error((Object)("Impossible d'instancier un runningEffect :" + amb_02.aey() + " inconnu"));
                    return false;
                }
                mv_0 mv_02 = new mv_0(amb_02.Ao(), by, amb_02.M(), el_22, amb_02.aew(), amb_02.aBN(), amb_02.aBO());
                mv_02.fs(amb_02.Ap());
                ny_2.sR().print("408|" + ny_2.cu(ny_2.Qg) + "|" + amb_02.Ao() + "|" + by + "|" + amb_02.M() + "|" + amb_02.aey() + "|");
                byte[] byArray = amb_02.aew();
                for (int j = 0; j < byArray.length; ++j) {
                    ny_2.sR().print(byArray[j] + "/");
                }
                ny_2.sR().println("|" + amb_02.aBN() + "|" + amb_02.Ap() + "|" + mv_02.rS() + "|");
                if (amb_02.gI()) {
                    mv_02.run();
                } else {
                    vr_0.aiM().b(mv_02);
                }
                return false;
            }
            case 8121: {
                rq_2 rq_22 = (rq_2)pr_02;
                el_2 el_23 = (el_2)mh_2.YJ().cr(rq_22.aey());
                if (el_23 == null) {
                    a.error((Object)("Impossible d'instancier un runningEffect :" + rq_22.aey() + " inconnu"));
                    return false;
                }
                ZT zT = (ZT)el_23.a(apN.aDK().aDL().Np(), WF.ajj());
                zT.ad(rq_22.aew());
                zT.b(rq_22.aex());
                if (zT.ajR() != null) {
                    zT.ajR().PJ().o(zT);
                    azs_0.aLV().a((aho_0)((ee_2)zT.ajR()), "hasBuff");
                }
                return false;
            }
            case 8122: {
                zq_1 zq_12 = (zq_1)pr_02;
                ee_2 ee_22 = (ee_2)apN.aDK().aDL().eg(zq_12.K());
                ee_22.PJ().dL(zq_12.aow());
                azs_0.aLV().a((aho_0)ee_22, "hasBuff");
                return false;
            }
            case 4900: {
                xk_0 xk_02 = (xk_0)pr_02;
                avJ avJ2 = new avJ(xk_02.Ao(), xk_02.N().lV(), xk_02.M(), xk_02.Es(), xk_02.Et(), xk_02.Eu());
                ny_2.sR().println("418|" + ny_2.cu(ny_2.Ql) + "|" + xk_02.Ao() + "|" + xk_02.N().lV() + "|" + xk_02.M() + "|" + xk_02.Es() + "|" + xk_02.Et().getX() + "," + xk_02.Et().getY() + "," + xk_02.Et().wk() + "|" + xk_02.Eu() + "|");
                vr_0.aiM().b(avJ2);
                vr_0.aiM().aiQ();
                return false;
            }
            case 4902: {
                aiz_0 aiz_02 = (aiz_0)pr_02;
                ee_2 ee_23 = (ee_2)apN.aDK().aDL().eg(aiz_02.Es());
                long l2 = apN.aDK().Ln().getId();
                if (ee_23 != null && (ee_23.c(avx_0.deu) != 1 || ee_23.LQ().Lb() == l2)) {
                    aod_2 aod_22;
                    if (add_1.aOG().kR("interactiveBubbleDialog")) {
                        add_1.aOG().kO("interactiveBubbleDialog");
                    }
                    if ((aod_22 = (aod_2)add_1.aOG().a("ouch", oh_2.bq("interactiveBubbleDialog"), 1000, 64L, (short)30001)) != null) {
                        ago_2.getInstance().getLayeredContainer().a(aod_22, 25000);
                        aod_22.setTarget(ee_23.NW(), 100, 0);
                        aod_22.setForcedDisplaySpark(true);
                        aod_22.setUseTargetPositionning(true);
                        aod_22.setText("ouch !");
                        aod_22.setActAsButton(true);
                        aod_22.setVisible(true);
                        aod_22.setCloseOnClick(true);
                    }
                }
                return false;
            }
            case 4901: {
                xy_2 xy_22 = (xy_2)pr_02;
                ol_2 ol_22 = new ol_2(xy_22.Ao(), xy_22.N().lV(), xy_22.M(), xy_22.EF());
                vr_0.aiM().b(ol_22);
                vr_0.aiM().aiQ();
                return false;
            }
            case 8250: {
                wc_2 wc_22 = (wc_2)pr_02;
                aez_0 aez_02 = (aez_0)apN.aDK().aDL().ef(wc_22.mb());
                if (wc_22.ajh()) {
                    aez_02.nw(1);
                } else {
                    aez_02.nw(2);
                    if (wc_22.mb() == apN.aDK().Ln().getId()) {
                        add_1.aOG().a(aon_0.aYc().getString("cheat.turnDuration.decreased"), 1090L, 102, 1);
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
}

