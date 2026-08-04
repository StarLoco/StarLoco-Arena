/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.chat.console.command.RaybanCommand;
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
import java.util.ArrayList;

/*
 * Renamed from ajo
 */
public class ajo_1
implements atG {
    private static ajo_1 cAl = new ajo_1();
    private final ArrayList cAm = new ArrayList();
    private final ArrayList cAn = new ArrayList();
    private ArrayList by;
    private ArrayList bz;
    private boolean cAo = false;
    private int ceE = 0;
    private boolean cAp = false;
    private int cAq;
    private long cAr;

    public static ajo_1 azb() {
        return cAl;
    }

    public void f(aez_0 aez_02) {
        this.cAn.add(aez_02);
    }

    public void g(aez_0 aez_02) {
        this.cAm.add(aez_02);
        if (!azs_0.aLV().getBooleanProperty("replayMode")) {
            this.cAo |= aez_02 == apN.aDK().Ln().yC();
        }
    }

    public void h(aez_0 aez_02) {
        PlayerStatisticsReport playerStatisticsReport = aez_02.aQs();
        aez_02.a(playerStatisticsReport);
        aez_02.nv(aez_02.aQq());
        aez_02.setName(aez_02.getName());
        aez_02.ns(aez_02.aQa());
        this.cAn.add(aez_02);
    }

    public void i(aez_0 aez_02) {
        PlayerStatisticsReport playerStatisticsReport = aez_02.aQs();
        aez_02.a(playerStatisticsReport);
        aez_02.setName(aez_02.getName());
        aez_02.nv(aez_02.aQq());
        aez_02.ns(aez_02.aQa());
        this.cAm.add(aez_02);
        if (!azs_0.aLV().getBooleanProperty("replayMode")) {
            this.cAo = aez_02 == apN.aDK().Ln().yC();
        }
    }

    public void z(ArrayList arrayList) {
        this.by = arrayList;
    }

    public void A(ArrayList arrayList) {
        this.bz = arrayList;
    }

    public void la(int n2) {
        this.ceE = n2;
    }

    public void dC(boolean bl2) {
        this.cAp = bl2;
    }

    public void lb(int n2) {
        this.cAq = n2;
    }

    public void dH(long l2) {
        this.cAr = l2;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 20005: {
                apN.aDK().b(this);
                return false;
            }
            case 16700: {
                ia_2 ia_22 = (ia_2)pr_02;
                wy_2 wy_22 = ia_22.lm();
                if (wy_22 != null) {
                    azs_0.aLV().g("singleCardData", wy_22);
                }
                return false;
            }
            case 16701: {
                azs_0.aLV().g("singleCardData", (Object)null);
                return false;
            }
            case 23064: {
                if (!add_1.aOG().kR("fightResultSubstitutesDetailsDialog")) {
                    add_1.aOG().a("fightResultSubstitutesDetailsDialog", oh_2.bq("fightResultSubstitutesDetailsDialog"), 257L, (short)10010);
                } else {
                    add_1.aOG().kO("fightResultSubstitutesDetailsDialog");
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
        if (!bl2) {
            if (!(this.cAm != null && this.cAm.size() != 0 || this.cAn != null && this.cAn.size() != 0)) {
                if (this.cAp) {
                    add_1.aOG().a(aon_0.aYc().getString("replay.replayNotComplete"), 1058L, 102, 1);
                } else {
                    add_1.aOG().a(aon_0.aYc().getString("fight.leavingAStillRunningFight"), 1058L, 102, 1);
                }
                azs_0.aLV().kb("fight.controller0");
                azs_0.aLV().kb("fight.controller1");
                apN.aDK().b(ajo_1.azb());
            } else {
                Object[] objectArray = null;
                Object[] objectArray2 = null;
                if (this.cAm.size() > 0 && ((aez_0)this.cAm.get(0)).Le() == 0) {
                    objectArray = (Object[])azs_0.aLV().getProperty("fight.controller0").getValue();
                    objectArray2 = (Object[])azs_0.aLV().getProperty("fight.controller1").getValue();
                } else {
                    objectArray = (Object[])azs_0.aLV().getProperty("fight.controller1").getValue();
                    objectArray2 = (Object[])azs_0.aLV().getProperty("fight.controller0").getValue();
                }
                aez_0[] aez_0Array = new aez_0[2];
                for (int j = 0; j < objectArray.length; ++j) {
                    aez_0Array[j] = new aez_0();
                    aez_0Array[j].b((gq_2)((aez_0)objectArray[j]));
                    aez_0Array[j].setName(((aez_0)objectArray[j]).getName());
                }
                aez_0[] aez_0Array2 = new aez_0[2];
                for (int j = 0; j < objectArray2.length; ++j) {
                    aez_0Array2[j] = new aez_0();
                    aez_0Array2[j].b((gq_2)((aez_0)objectArray2[j]));
                    aez_0Array2[j].setName(((aez_0)objectArray2[j]).getName());
                }
                azs_0.aLV().g("fight.winnerCoachs", aez_0Array);
                azs_0.aLV().g("fight.loserCoachs", aez_0Array2);
                azs_0.aLV().kb("fight.controller0");
                azs_0.aLV().kb("fight.controller1");
                if (this.by != null) {
                    azs_0.aLV().g("fight.lostCards", this.by.toArray());
                }
                if (this.bz != null) {
                    azs_0.aLV().g("fight.wonCards", this.bz.toArray());
                }
                azs_0.aLV().g("fight.duration", aon_0.aYc().getString("fight.durationValue", this.ceE));
                azs_0.aLV().g("fight.localWinner", this.cAo);
                if (this.cAq == 6) {
                    add_1.aOG().a("fightResultEvolutionDialog", oh_2.bq("fightResultEvolutionDialog"), 1L, (short)10001);
                } else {
                    afz_0 afz_02 = ahy_1.axg().dC(this.cAr);
                    if (afz_02 != null && afz_02.QC() > 0) {
                        if (!apN.aDK().Ln().c(avq_0.ce((short)284)) || this.cAr == 31L) {
                            add_1.aOG().a("fightResultEvolutionTutoDialog", oh_2.bq("fightResultEvolutionTutoDialog"), 1L, (short)10001);
                        } else {
                            add_1.aOG().a("fightResultEvolutionChallengeDialog", oh_2.bq("fightResultEvolutionChallengeDialog"), 1L, (short)10001);
                        }
                    } else if (afz_02 != null && afz_02.QE() > 0) {
                        add_1.aOG().a("fightResultEvolutionTimeChallengeDialog", oh_2.bq("fightResultEvolutionTimeChallengeDialog"), 1L, (short)10001);
                    } else {
                        add_1.aOG().a("fightResultDialog", oh_2.bq("fightResultDialog"), 1L, (short)10001);
                    }
                }
            }
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            RaybanCommand.uninitialize();
            add_1.aOG().kG("dofusarena.fight");
            add_1.aOG().kO("fightResultEvolutionDialog");
            add_1.aOG().kO("fightResultDialog");
            add_1.aOG().kO("fightResultEvolutionTutoDialog");
            add_1.aOG().kO("fightResultEvolutionTimeChallengeDialog");
            add_1.aOG().kO("fightResultEvolutionChallengeDialog");
            azs_0.aLV().kb("fight.winnerCoachs");
            azs_0.aLV().kb("fight.loserCoachs");
            azs_0.aLV().kb("fight.lostCards");
            azs_0.aLV().kb("fight.wonCards");
            azs_0.aLV().kb("fight.duration");
            azs_0.aLV().kb("fight.localWinner");
            azs_0.aLV().kb("fight.winningTeam");
            azs_0.aLV().kb("fight.losingTeam");
            this.cAn.clear();
            this.cAm.clear();
            if (this.by != null) {
                this.by.clear();
            }
            if (this.bz != null) {
                this.bz.clear();
            }
            this.ceE = 0;
            this.cAo = false;
        }
    }
}

