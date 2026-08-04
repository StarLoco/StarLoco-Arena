/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from mV
 */
public class mv_0
extends kr_0 {
    private ZT Nu;
    private boolean Nv;
    private byte[] Nw;
    private int Nx;
    protected static aLN Hv = new aLN();

    public mv_0(int n2, int n3, int n4, el_2 el_22, byte[] byArray, boolean bl2, int n5) {
        super(n2, n3, n4);
        this.Nu = (ZT)el_22.a(apN.aDK().aDL().Np(), WF.ajj());
        this.Nw = byArray;
        this.Nv = bl2;
        this.Nx = n5;
        this.a(gp_2.Sb());
        this.a(Wz.ajg());
        this.a(aau_0.apB());
        this.a(new aql(this));
        this.bG(this.ci(n4));
    }

    public ZT rR() {
        return this.Nu;
    }

    public long oS() {
        if (this.Nu != null) {
            this.Nu.ad(this.Nw);
            if (this.Nu.ajR() != null) {
                this.bC(this.Nu.ajR().getId());
            }
            if (this.Nu.ajQ() != null) {
                this.bB(this.Nu.ajQ().getId());
            }
        }
        return super.oS();
    }

    protected void ax() {
        adu_0 adu_02 = apN.aDK().aDL();
        if (adu_02 != null && this.Nu != null) {
            if (adu_02.eg(this.mS()) != null && (!((ee_2)adu_02.eg(this.mS())).PL().b((aak_2)avx_0.deu) || adu_02.p((gn_0)adu_02.eg(this.mS())))) {
                switch (this.ci(this.Nu.getId())) {
                    case 1001: {
                        if (this.Nu.getValue() <= 0) break;
                        if (this.Nu.getId() == mh_2.bup.getId() || this.Nu.getId() == mh_2.bwA.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.HPEarthLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buo.getId() || this.Nu.getId() == mh_2.bwz.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.HPFireLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buq.getId() || this.Nu.getId() == mh_2.bwB.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.HPWaterLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bur.getId() || this.Nu.getId() == mh_2.bwC.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.HPWindLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        Hv.info(aon_0.aYc().getString("fight.HPLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                        break;
                    }
                    case 1002: {
                        Hv.info(aon_0.aYc().getString("fight.HPGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                        break;
                    }
                    case 1003: {
                        if (this.Nu.getValue() <= 0) break;
                        Hv.info(aon_0.aYc().getString("fight.HPLeech", ((ee_2)adu_02.eg(this.Nl())).getName(), this.Nu.getValue(), ((ee_2)adu_02.eg(this.mS())).getName()));
                        break;
                    }
                    case 1004: {
                        if (this.Nu.getId() == mh_2.bwa.getId()) break;
                        Hv.info(aon_0.aYc().getString("fight.MPLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                        break;
                    }
                    case 1005: {
                        Hv.info(aon_0.aYc().getString("fight.MPGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                        break;
                    }
                    case 1006: {
                        Hv.info(aon_0.aYc().getString("fight.MPLeech", ((ee_2)adu_02.eg(this.Nl())).getName(), this.Nu.getValue(), ((ee_2)adu_02.eg(this.mS())).getName()));
                        break;
                    }
                    case 1007: {
                        if (this.Nu.getId() == mh_2.bvZ.getId()) break;
                        Hv.info(aon_0.aYc().getString("fight.APLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                        break;
                    }
                    case 1008: {
                        Hv.info(aon_0.aYc().getString("fight.APGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                        break;
                    }
                    case 1009: {
                        Hv.info(aon_0.aYc().getString("fight.APLeech", ((ee_2)adu_02.eg(this.Nl())).getName(), this.Nu.getValue(), ((ee_2)adu_02.eg(this.mS())).getName()));
                        break;
                    }
                    case -1: {
                        if (this.Nu.getId() == mh_2.buR.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResEarthGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buS.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResEarthLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buP.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResFireGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buQ.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResFireLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buT.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResWaterGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buU.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResWaterLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buV.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResWindGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.buW.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResWindLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvO.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResAllGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvP.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.ResAllLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvl.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgEarthLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvk.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgEarthGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvj.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgFireLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvi.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgFireGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvm.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgWaterGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvn.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgWaterLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvo.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgWindGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvp.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgWindLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvQ.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgAllGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bvR.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DmgAllLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bwq.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DodgeGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bwr.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.DodgeLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() == mh_2.bwo.getId()) {
                            Hv.info(aon_0.aYc().getString("fight.TackleGain", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                            break;
                        }
                        if (this.Nu.getId() != mh_2.bwp.getId()) break;
                        Hv.info(aon_0.aYc().getString("fight.TackleLoss", ((ee_2)adu_02.eg(this.mS())).getName(), this.Nu.getValue()));
                        break;
                    }
                }
            }
            this.Nu.akd();
            if (this.Nv) {
                this.Nu.i((xb_2)null);
            } else {
                if (this.Nu.akF() && this.Nu.ajR() != null && this.Nu.ajR().PJ() != null) {
                    this.Nu.ajR().PJ().o(this.Nu);
                }
                if (this.Nu.akF() && !this.Nu.isInfinite()) {
                    this.Nu.jt(this.Nx);
                }
                if (!this.Nu.akn()) {
                    this.Nu.akv();
                }
            }
            azs_0.aLV().a((aho_0)((ee_2)this.Nu.ajR()), "hasBuff");
        }
    }

    private int ci(int n2) {
        aih_1 aih_12 = (aih_1)mh_2.YJ().cq(n2);
        if (aih_12 == null) {
            return -1;
        }
        return aih_12.eA();
    }

    public int rS() {
        return this.Nu.getValue();
    }

    public void cj(int n2) {
        this.Nu.iQ(n2);
    }
}

