/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Y
 */
public class y_0
extends Eq {
    private final adu_0 bv;
    private final ArrayList bw;
    private final ArrayList bx;
    private final ArrayList by;
    private final ArrayList bz;
    private aLO bA;
    private aLO bB;
    private cp_2 bC;

    public y_0(int n2, int n3, int n4, adu_0 adu_02, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, aLO aLO2, aLO aLO3, cp_2 cp_22) {
        super(n2, n3, n4);
        this.bv = adu_02;
        this.bw = arrayList;
        this.bx = arrayList2;
        this.by = arrayList3;
        this.bz = arrayList4;
        this.bA = aLO2;
        this.bB = aLO3;
        this.bC = cp_22;
    }

    public void run() {
        if (this.bv != null) {
            sj_1 sj_12 = apN.aDK().Ln();
            afz_0 afz_02 = ahy_1.axg().dC(this.bv.asy());
            if (this.bv.aKl() != 5 || afz_02 != null && afz_02.QA() && (this.bw.size() > 0 && ((anp_2)this.bw.get(0)).getId() == sj_12.getId() || afz_02.QE() > 0 || afz_02.QC() > 0)) {
                cl_1 cl_12;
                Object object;
                Object object22;
                int n2 = -1;
                Te[] teArray = new Te[]{(Te)azs_0.aLV().getProperty("fight.team0").getValue(), (Te)azs_0.aLV().getProperty("fight.team1").getValue()};
                for (Object object22 : this.bx) {
                    object = this.bv.ef(((anp_2)object22).getId());
                    if (object == null) continue;
                    cl_12 = (aez_0)object;
                    ((aez_0)cl_12).a(((anp_2)object22).aQs());
                    ((aez_0)cl_12).ns(this.bA.eL(((ahh_1)((Object)cl_12)).getId()));
                    ((aez_0)cl_12).nt(this.bB.eL(((ahh_1)((Object)cl_12)).getId()));
                    if (((ahh_1)((Object)cl_12)).getId() == sj_12.getId()) {
                        sj_12.a(((anp_2)object22).aQs());
                        if (this.bv.aKl() == 3) {
                            iz_1 iz_12 = iz_1.Vg();
                            vk_1.BZ();
                            iz_12.bR(vk_1.fx());
                            add_1.aOG().f(aon_0.aYc().getString("tournamentDefeat"), 102, 1);
                        }
                    }
                    ajo_1.azb().f((aez_0)cl_12);
                    n2 = ((aez_0)cl_12).Le();
                    teArray[n2].hQ(this.bA.eL(((ahh_1)((Object)cl_12)).getId()));
                    teArray[n2].setStrength(this.bB.eL(((ahh_1)((Object)cl_12)).getId()));
                }
                ArrayList arrayList = this.bw;
                object22 = arrayList.iterator();
                while (object22.hasNext()) {
                    object = (anp_2)object22.next();
                    cl_12 = this.bv.ef(((anp_2)object).getId());
                    if (cl_12 == null) continue;
                    cl_1 cl_13 = cl_12;
                    ((aez_0)cl_13).a(((anp_2)object).aQs());
                    ((aez_0)cl_13).ns(this.bA.eL(((ahh_1)((Object)cl_13)).getId()));
                    ((aez_0)cl_13).nt(this.bB.eL(((ahh_1)((Object)cl_13)).getId()));
                    if (((ahh_1)((Object)cl_13)).getId() == sj_12.getId()) {
                        sj_12.a(((anp_2)object).aQs());
                        if (this.bv.aKl() == 3) {
                            vk_1 vk_12 = vk_1.BZ();
                            vk_1.BZ();
                            vg vg2 = vk_12.aQ(vk_1.fx());
                            if (vg2 != null) {
                                byte by = vg2.Bv();
                                vg2.C((byte)(by - 1));
                                if (by - 1 < 0) {
                                    iz_1 iz_13 = iz_1.Vg();
                                    vk_1.BZ();
                                    iz_13.bR(vk_1.fx());
                                    add_1.aOG().f(aon_0.aYc().getString("tournamentVictory"), 102, 1);
                                }
                            }
                        }
                    }
                    ajo_1.azb().g((aez_0)cl_13);
                    byte by = ((aez_0)cl_13).Le();
                    teArray[by].hQ(this.bA.eL(((ahh_1)((Object)cl_13)).getId()));
                    teArray[by].setStrength(this.bB.eL(((ahh_1)((Object)cl_13)).getId()));
                }
                ajo_1.azb().z(this.by);
                ajo_1.azb().A(this.bz);
                if (n2 == 0) {
                    teArray[1].hM(teArray[1].afB() + 1);
                    teArray[0].hP(teArray[0].afP() + 1);
                    azs_0.aLV().g("fight.winningTeam", teArray[1]);
                    azs_0.aLV().g("fight.losingTeam", teArray[0]);
                } else {
                    teArray[0].hM(teArray[0].afB() + 1);
                    teArray[1].hP(teArray[1].afP() + 1);
                    azs_0.aLV().g("fight.winningTeam", teArray[0]);
                    azs_0.aLV().g("fight.losingTeam", teArray[1]);
                }
                object22 = this.bC.eJ();
                for (int j = 0; j < ((Object)object22).length; ++j) {
                    adY.atu().dz((long)object22[j]).a((OW)this.bC.t((long)object22[j]));
                }
                ajo_1.azb().la(this.bv.getDuration());
                ajo_1.azb().lb(this.bv.aKl());
                ajo_1.azb().dH(this.bv.asy());
                apN.aDK().a(ajo_1.azb());
            }
            this.bv.aKt();
        } else {
            a.error((Object)"Erreur dans FIghtEndAction");
        }
        this.Nn();
    }

    protected void ax() {
        nv_0 nv_02 = new nv_0();
        apN.aDK().vJ().b(nv_02);
    }
}

