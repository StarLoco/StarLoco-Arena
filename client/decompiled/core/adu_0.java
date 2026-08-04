/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.client.console.command.fight.ShowGridCommand;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/*
 * Renamed from adu
 */
public class adu_0
extends mv_1 {
    private final wh_2 cmE;
    private long PZ;
    private short bud;
    private long cmF;
    private static boolean cmG = false;
    private boolean cmH = false;

    public adu_0(byte by, int n2) {
        super(by, n2);
        this.b();
        this.cmE = new wh_2();
        this.PZ = 0L;
        this.djJ = new asF(this);
        this.djI = new aoq_0();
    }

    public long al(byte by) {
        throw new UnsupportedOperationException("Le client ne doit pas d\u00e9finir l'id des effectUsers");
    }

    public wh_2 asq() {
        return this.cmE;
    }

    public int getDuration() {
        if (this.PZ != 0L) {
            return (int)(System.currentTimeMillis() - this.PZ) / 60000;
        }
        return 0;
    }

    public int asr() {
        if (this.PZ != 0L) {
            return (int)(System.currentTimeMillis() - this.PZ) / 1000;
        }
        return 0;
    }

    public azg_0 ass() {
        return (azg_0)this.djH;
    }

    public Te ast() {
        return new Te();
    }

    public boolean a(yg_0 yg_02, byte by) {
        afJ afJ2;
        Iterator iterator = yg_02.amp();
        HashSet<cl_1> hashSet = new HashSet<cl_1>();
        while (iterator.hasNext()) {
            afJ2 = (ee_2)iterator.next();
            if (((gn_0)afJ2).LQ() != null) {
                hashSet.add(((gn_0)afJ2).LQ());
                continue;
            }
            a.error((Object)("Impossible d'ajouter le controller du fighter " + afJ2 + " dans le match d'id " + this.aW + " : Controller \u00e9gal \u00e0 null."));
        }
        if (by == 0) {
            azs_0.aLV().g("fight.team0", yg_02);
            azs_0.aLV().g("fight.controller0", hashSet.toArray());
            afJ2 = apN.aDK().Ln();
            if (afJ2 != null) {
                String string = ((aez_0)afJ2).getName();
                boolean bl2 = false;
                for (cl_1 cl_12 : hashSet) {
                    if (!cl_12.Ld().equalsIgnoreCase(string)) continue;
                    bl2 = true;
                }
                azs_0.aLV().g("fight.team0isLocal", bl2);
            }
        } else if (by == 1) {
            azs_0.aLV().g("fight.team1", yg_02);
            azs_0.aLV().g("fight.controller1", hashSet.toArray());
        }
        return super.a(yg_02, by);
    }

    public void a(wq_2 wq_22) {
        if (this.Zz() != null) {
            this.Zz().add(wq_22);
        }
        this.asw();
    }

    public tO asu() {
        tO tO2 = null;
        if (this.Zz() != null == !this.Zz().isEmpty()) {
            tO2 = (tO)this.Zz().get(0);
        }
        return tO2;
    }

    public ArrayList asv() {
        ArrayList arrayList = new ArrayList();
        int n2 = Math.min(4, this.Zz().size()) - 1;
        for (int j = 0; j < n2; ++j) {
            arrayList.add(this.Zz().get(j));
        }
        return arrayList;
    }

    public void asw() {
        azs_0.aLV().g("fight.eventCards", this.asv().toArray());
    }

    public boolean a(ee_2 ee_22, fv fv2, ry ry2) {
        return false;
    }

    public boolean a(ee_2 ee_22, jb_2 jb_22, ry ry2) {
        return false;
    }

    public alt_1 a(gn_0 gn_02, ry ry2) {
        if (gn_02.Qa()) {
            return alt_1.cFR;
        }
        return super.a(gn_02, ry2);
    }

    public boolean a(ee_2 ee_22, ry ry2) {
        return false;
    }

    public void a(ee_2 ee_22, ack_1 ack_12, int n2) {
    }

    public void ZO() {
        super.ZO();
        apN.aDK().a(jR.oc());
        apN.aDK().a(bo_1.Ik());
        this.asq().ef(20);
    }

    public void ZQ() {
        super.ZQ();
        apN.aDK().b(jR.oc());
        apN.aDK().b(bo_1.Ik());
        this.asq().stop();
    }

    public void ZP() {
        super.ZP();
        ShowGridCommand.aI(DofusArenaClientInstance.yl().aod().a(adc_0.clU));
        apN.aDK().a(qn_0.adc());
        apN.aDK().a(azL.aMm());
        this.asq().ef(Math.max(30, (int)(this.byv / 1000L)));
    }

    public void ZR() {
        super.ZR();
        apN.aDK().b(qn_0.adc());
        apN.aDK().b(azL.aMm());
        this.asq().stop();
    }

    public void ZS() {
        super.ZS();
        vt_0.aiU().activate();
        azh.aLL().aLM();
        apN.aDK().a(vv_1.Cx());
        apN.aDK().a(fk_0.jo());
        this.djE.a(new bq_1(this));
        this.asq().ef(10);
    }

    public void ZT() {
        super.ZT();
        apN.aDK().b(vv_1.Cx());
        apN.aDK().b(fk_0.jo());
        this.asq().stop();
    }

    public void ZU() {
        super.ZU();
        pm_0.ur().done();
        this.PZ = System.currentTimeMillis();
        apN.aDK().a(of_1.th());
    }

    public void sD() {
        ((jm_0)this.djH).nR();
        if (this.amr() > 1 && !add_1.aOG().kR("fighterInformationsDialog")) {
            azs_0.aLV().g("singleCardData", this.asu());
            add_1.aOG().a("singleCardDialog", oh_2.bq("singleCardDialog"), 2000, (short)10100);
        }
        super.sD();
    }

    public void sE() {
        super.sE();
        this.asw();
    }

    public void f(ee_2 ee_22) {
        super.l(ee_22);
        if (!cmG && this.i(ee_22) && !ee_22.Dk()) {
            apN.aDK().a(anx_1.aXx());
        }
        this.asq().ef((int)this.byv / ((aez_0)ee_22.LQ()).eN(ee_22.Dk()) / 1000 - 1);
    }

    public void g(ee_2 ee_22) {
        super.m(ee_22);
        if (apN.aDK().c(anx_1.aXx())) {
            apN.aDK().b(anx_1.aXx());
        }
        this.asq().stop();
        ee_22.a(Lr.bqz).ka(ee_22.d(Lr.brn));
        ee_22.a(Lr.bqy).ka(ee_22.d(Lr.brm));
    }

    public boolean h(ee_2 ee_22) {
        boolean bl2 = super.j(ee_22);
        bd_1.Is().j(ee_22.NW());
        return bl2;
    }

    public void l(xb_2 xb_22) {
    }

    public void m(xb_2 xb_22) {
    }

    public void ZN() {
        super.ZN();
        apN.aDK().a(avu_0.aIB());
        apN.aDK().a(qg_2.acV());
        apN.aDK().a(WE.aji());
    }

    public void asx() {
        super.asx();
        this.asq().stop();
        for (yg_0 yg_02 : this.aKn()) {
            Iterator iterator = yg_02.amp();
            while (iterator.hasNext()) {
                ee_2 ee_22 = (ee_2)iterator.next();
                ee_22.j();
            }
        }
        apN.aDK().b(do_2.Mm());
        apN.aDK().b(WE.aji());
        vt_0.aiU().deactivate();
        azs_0.aLV().kb("fight.team0");
        azs_0.aLV().kb("fight.team1");
    }

    public void b(yg_0 yg_02) {
        super.b(yg_02);
    }

    public void c(yg_0 yg_02) {
        super.c(yg_02);
    }

    public void a(ack_1 ack_12) {
        super.a(ack_12);
        vt_0.aiU().k(ack_12);
        ny_2.sR().println("416|" + ny_2.cu(ny_2.Qq) + "|" + ack_12.aqM() + "|" + ack_12.gn() + "|" + ack_12.go() + "|" + ack_12.gp() + "|");
    }

    public void b(ack_1 ack_12) {
        super.b(ack_12);
        vt_0.aiU().l(ack_12);
        ny_2.sR().println("417|" + ny_2.cu(ny_2.Qq) + "|" + ack_12.aqM() + "|" + ack_12.gn() + "|" + ack_12.go() + "|" + ack_12.gp() + "|");
    }

    public void b(aE aE2) {
    }

    public void a(ack_1 ack_12, aOf aOf2) {
    }

    public void c(ack_1 ack_12) {
        super.c(ack_12);
    }

    public void b(ack_1 ack_12, aOf aOf2) {
    }

    public boolean i(ee_2 ee_22) {
        cl_1 cl_12;
        sj_1 sj_12;
        return ee_22 != null && (sj_12 = apN.aDK().Ln()) != null && (cl_12 = ee_22.LQ()) != null && sj_12.yC() != null && sj_12.yC().equals(cl_12);
    }

    public boolean p(gn_0 gn_02) {
        sj_1 sj_12 = apN.aDK().Ln();
        return gn_02 != null && sj_12 != null && gn_02.PH() != null && sj_12.yC() != null && sj_12.yC().Le() == gn_02.PH().lV();
    }

    public short YF() {
        return this.bud;
    }

    public void aI(short s) {
        this.bud = s;
    }

    public long asy() {
        return this.cmF;
    }

    public void dy(long l2) {
        this.cmF = l2;
    }

    public static boolean asz() {
        return cmG;
    }

    public static void dc(boolean bl2) {
        cmG = bl2;
    }

    public void asA() {
    }

    public void asB() {
    }

    public boolean asC() {
        return this.cmH;
    }

    public void dd(boolean bl2) {
        this.cmH = bl2;
    }
}

