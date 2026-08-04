/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from ayr
 */
public class ayr_0
extends ajM
implements aho_0 {
    private static final Logger a = Logger.getLogger(ayr_0.class);
    private boolean vd = false;
    private long dkO = 0L;
    private long dkP = 0L;
    private short dkQ = 0;
    private akq_1 dkR;
    private akq_1 dkS;
    private akq_1 dkT;
    private int dkU;
    private int dkV;
    private boolean dkW;
    private ayr_0[] dkX = new ayr_0[2];
    private boolean dkY = false;
    private int dkZ;
    private boolean dla;
    public static final String dlb = "Spell";
    public static final String dlc = "Bonus";
    public static final String dld = "Malus";
    public static final String dle = "Summon";
    public static final String dlf = "Barrier";
    public static final String dlg = "Teleport";
    public static final String dlh = "Item";
    public static final String dli = "DeadEndType";
    public static final String dlj = "EmptyType";
    public static final String ID = "id";
    public static final String aSY = "xp";
    public static final String dlk = "spellDescription";
    public static final String dll = "effects";
    public static final String dlm = "helpEffects";
    public static final String dln = "barrier";
    public static final String dlo = "cardList";
    public static final String dlp = "sphereType";
    public static final String dlq = "canBuy";
    public static final String dlr = "canTeleport";
    public static final String dls = "sphereIcon";
    public static final String ctk = "helpDescription";
    public static final String[] ce = new String[]{"id", "xp", "effects", "spellDescription", "barrier", "cardList", "sphereType", "canBuy", "canTeleport", "sphereIcon", "helpDescription"};
    public static final long dlt = 0L;
    public static final long dlu = 1L;
    public static final long dlv = 2L;
    public static final long dlw = 4L;
    public static final long dlx = 8L;
    public static final long dly = 16L;
    public static final long dlz = 32L;
    public static final long dlA = 64L;
    public static final long dlB = 128L;
    public static final long dlC = 256L;
    public static final long dlD = 512L;
    public static final long dlE = 1024L;
    public static final long dlF = 2048L;
    public static final long dlG = 4096L;
    public static final long dlH = 8192L;
    private static final long dlI = 15L;
    private static final long dlJ = 12288L;
    public static final long dlK = 0L;
    public static final long dlL = 1L;
    public static final long dlM = 2L;
    public static final long dlN = 3L;
    public static final long dlO = 4L;
    public static final long dlP = 5L;
    public static final long dlQ = 6L;
    public static final long dlR = 7L;
    public static final long dlS = 8L;
    public static final long dlT = 9L;
    public static final long dlU = 10L;
    public static final long dlV = 11L;
    public static final long dlW = 12L;
    public static final long dlX = 13L;
    public static final long dlY = 14L;
    public static final long dlZ = 15L;
    public static final long dma = 16L;
    public static final long dmb = 17L;
    public static final long dmc = 18L;
    public static final long dmd = 19L;
    public static final long dme = 20L;
    public static final long dmf = 21L;
    public static final long dmg = 22L;
    public static final long dmh = 23L;
    public static final long dmi = 24L;
    public static final long dmj = 25L;
    public static final long dmk = 26L;
    public static final long dml = 30L;
    public static final long dmm = 31L;
    public static final long dmn = 32L;
    public static final long dmo = 33L;
    public static final long dmp = 34L;
    public static final long dmq = 50L;
    public static final long dmr = 64L;

    public ayr_0(int n2, int n3, short s, short s2, int n4, ArrayList arrayList, jg_0 jg_02, int n5, boolean bl2, short s3, short s4) {
        super(n2, n3, s, s2, n4, arrayList, jg_02, n5, bl2, s3, s4);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        Object object;
        if (string.equals(ID)) {
            return this.aW;
        }
        if (string.equals(aSY)) {
            if (this.dkW) {
                return this.cpn / 10;
            }
            return this.cpn;
        }
        if (string.equals(dlk) && this.ir != 0) {
            return je_1.Wa().el(this.ir);
        }
        if (string.equals(dll)) {
            return asf_0.a(-1, true, null, this.iM, -1);
        }
        if (string.equals(dlm) && this.iM.size() > 0) {
            return asf_0.d((xj_0)this.iM.get(0));
        }
        if (string.equals(dln) && !this.dkW) {
            wy_2[] wy_2Array = new wy_2[this.cpq.size()];
            for (int j = 0; j < this.cpq.size(); ++j) {
                wy_2Array[j] = new wy_2(this.cpq.bu(j));
            }
            return wy_2Array;
        }
        if (string.equals(dlo) && (object = aca_0.aOq().F(this.cAT)) != null) {
            return ((lb_0)object).getValues();
        }
        if (string.equals(dlp)) {
            return this.aKZ();
        }
        if (string.equals(dlq)) {
            object = ((Ei)this.cAS).b(this);
            if (object == null) {
                return false;
            }
            ee_2 ee_22 = (ee_2)azs_0.aLV().getProperty("sphereboard.fighter").getValue();
            if (!ee_22.c(this)) {
                return false;
            }
            ayr_0 ayr_02 = ((Ei)this.cAS).MR();
            if (ayr_02.aux() == this.aut() && ayr_02.auy() == this.auu()) {
                return false;
            }
            return true;
        }
        if (string.equals(dlr)) {
            object = ((Ei)this.cAS).MR();
            return object == this && this.cps != 0 || ((ajM)object).aux() == this.aut() && ((ajM)object).auy() == this.auu();
        }
        if (string.equals(dls)) {
            try {
                if (this.dkW) {
                    return String.format(mu_1.rM().getString("sphereBoardSpherePath"), -this.aLl());
                }
                return String.format(mu_1.rM().getString("sphereBoardSpherePath"), this.aLl());
            }
            catch (aih_2 aih_22) {
                aih_22.printStackTrace();
            }
        }
        if (string.equals(ctk)) {
            return aon_0.aYc().getString("sphereHelpDescription" + this.aKZ());
        }
        return null;
    }

    public long aKX() {
        return this.dkO;
    }

    public boolean aKY() {
        return this.dkW;
    }

    public String aKZ() {
        if (this.ir != 0) {
            return dlb;
        }
        if (this.iM.size() > 0) {
            if (((xj_0)this.iM.get(0)).M() == mh_2.bvl.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvp.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvj.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvn.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvR.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buS.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buW.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buQ.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buU.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvP.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bwr.getId()) {
                return dld;
            }
            if (((xj_0)this.iM.get(0)).M() == mh_2.bwN.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bwM.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bwK.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bwO.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bwL.getId()) {
                return dle;
            }
            return dlc;
        }
        if (this.cpq.size() > 0) {
            return dlf;
        }
        if (this.cps != 0) {
            return dlg;
        }
        if (this.cAT != 0) {
            return dlh;
        }
        if (this.cpr) {
            return dli;
        }
        return dlj;
    }

    public void aLa() {
        this.a(this, new ArrayList());
    }

    public void a(ayr_0 ayr_02, ArrayList arrayList) {
        this.eA(false);
        if (this.azm()) {
            ayr_02 = this;
            for (int j = 0; j < arrayList.size(); ++j) {
                ((ayr_0)arrayList.get((int)j)).aLt()[1] = this;
            }
            arrayList.clear();
        } else {
            this.dkX[0] = ayr_02;
            arrayList.add(this);
        }
        if (this.cAX != null && this.aLr().aKX() == 0L) {
            this.aLr().a(ayr_02, arrayList);
        }
        if (this.cAU != null && this.aLs().aKX() == 0L) {
            this.aLs().a(ayr_02, arrayList);
        }
        if (this.cAW != null && this.aLq().aKX() == 0L) {
            this.aLq().a(ayr_02, arrayList);
        }
        if (this.cAV != null && this.aLp().aKX() == 0L) {
            this.aLp().a(ayr_02, arrayList);
        }
        if (this.dkX[1] == null) {
            ajM ajM2;
            ajM ajM3 = this.cAX != null ? this.cAX : (this.cAU != null ? this.cAU : (ajM2 = this.cAW != null ? this.cAW : this.cAV));
            if (ajM2 != null && ajM2.azm()) {
                for (int j = 0; j < arrayList.size(); ++j) {
                    ((ayr_0)arrayList.get((int)j)).aLt()[1] = (ayr_0)ajM2;
                }
            }
            arrayList.clear();
        }
    }

    private void eA(boolean bl2) {
        if (bl2) {
            this.dkO = 0L;
        }
        if (this.dkO != 0L) {
            return;
        }
        if (this.cAU != null) {
            this.dkO |= 1L;
            if (this.aLs().aKX() != 0L && this.aLs().aLb()) {
                this.dkO = this.cAU.azr() != null ? (this.dkO |= 0x10L) : (this.dkO |= 0x20L);
            }
        }
        if (this.cAW != null) {
            this.dkO |= 2L;
            if (this.aLq().aKX() != 0L && this.aLq().aLb()) {
                this.dkO = this.cAW.azs() != null ? (this.dkO |= 0x80L) : (this.dkO |= 0x40L);
            }
        }
        if (this.cAX != null) {
            this.dkO |= 4L;
            if (this.aLr().aKX() != 0L && this.aLr().aLb()) {
                this.dkO = this.cAX.azq() != null ? (this.dkO |= 0x200L) : (this.dkO |= 0x100L);
            }
        }
        if (this.cAV != null) {
            this.dkO |= 8L;
            if (this.aLp().aKX() != 0L && this.aLp().aLb()) {
                this.dkO = this.cAV.azp() != null ? (this.dkO |= 0x400L) : (this.dkO |= 0x800L);
            }
        }
        if (this.aLc()) {
            if (this.aLl() != 0L) {
                this.dkO |= 0x2000L;
            }
            if (this.cAX != null && this.aLr().aKX() != 0L) {
                if (this.aLr().aLc()) {
                    this.aLr().aLj();
                    this.aLj();
                    if (this.aLr().aLq() != null && !this.aLr().aLq().aLd()) {
                        this.aLr().aLq().eA(true);
                    }
                    if (this.aLr().aLp() != null && !this.aLr().aLp().aLd()) {
                        this.aLr().aLp().eA(true);
                    }
                } else {
                    this.aLr().eA(true);
                }
            }
            if (this.cAU != null && this.aLs().aKX() != 0L) {
                if (this.aLs().aLc()) {
                    this.aLs().aLj();
                    this.aLj();
                    if (this.aLs().aLq() != null && !this.aLs().aLq().aLd()) {
                        this.aLs().aLq().eA(true);
                    }
                    if (this.aLs().aLp() != null && !this.aLs().aLp().aLd()) {
                        this.aLs().aLp().eA(true);
                    }
                } else {
                    this.aLs().eA(true);
                }
            }
            if (this.cAW != null && this.aLq().aKX() != 0L) {
                if (this.aLq().aLc()) {
                    this.aLq().aLj();
                    this.aLj();
                    if (this.aLq().aLs() != null && !this.aLq().aLs().aLd()) {
                        this.aLq().aLs().eA(true);
                    }
                    if (this.aLq().aLr() != null && !this.aLq().aLr().aLd()) {
                        this.aLq().aLr().eA(true);
                    }
                } else {
                    this.aLq().eA(true);
                }
            }
            if (this.cAV != null && this.aLp().aKX() != 0L) {
                if (this.aLp().aLc()) {
                    this.aLp().aLj();
                    this.aLj();
                    if (this.aLp().aLs() != null && !this.aLp().aLs().aLd()) {
                        this.aLp().aLs().eA(true);
                    }
                    if (this.aLp().aLr() != null && !this.aLp().aLr().aLd()) {
                        this.aLp().aLr().eA(true);
                    }
                } else {
                    this.aLp().eA(true);
                }
            }
        }
    }

    public boolean aLb() {
        if (this.aLl() != 0L || (this.dkO & 0x1000L) != 0L) {
            return false;
        }
        return this.aLc();
    }

    public boolean aLc() {
        if ((this.dkO & 1L) != 0L) {
            if ((this.dkO & 2L) != 0L) {
                return (this.dkO & 4L) == 0L && (this.dkO & 8L) == 0L;
            }
            return (this.dkO & 8L) != 0L && (this.dkO & 4L) == 0L;
        }
        if ((this.dkO & 2L) != 0L) {
            return (this.dkO & 4L) != 0L && (this.dkO & 8L) == 0L;
        }
        return (this.dkO & 4L) != 0L && (this.dkO & 8L) != 0L;
    }

    public boolean aLd() {
        return (this.dkO & 0x1000L) != 0L;
    }

    public int aLe() {
        return this.aut() - 1;
    }

    public int aLf() {
        return this.auu() - 1;
    }

    public void eB(boolean bl2) {
        this.dkW = bl2;
    }

    public void aLg() {
        this.dkP = this.dkO;
        this.dkQ = 0;
        if (this.dkO != 0L && (this.dkO & 0xFL) != 15L) {
            while (this.dkO != 0L && ((this.dkP & 1L) == 0L || (this.dkP & 8L) != 0L)) {
                this.aLh();
                this.dkQ = (short)(this.dkQ + 1);
            }
        }
        this.dkP |= 0x3000L & this.dkO;
    }

    private void aLh() {
        long l2 = this.dkP;
        this.dkP = 0L;
        if ((l2 & 1L) != 0L) {
            this.dkP |= 2L;
            if ((l2 & 0x10L) != 0L) {
                this.dkP |= 0x80L;
            }
            if ((l2 & 0x20L) != 0L) {
                this.dkP |= 0x40L;
            }
        }
        if ((l2 & 8L) != 0L) {
            this.dkP |= 1L;
            if ((l2 & 0x400L) != 0L) {
                this.dkP |= 0x10L;
            }
            if ((l2 & 0x800L) != 0L) {
                this.dkP |= 0x20L;
            }
        }
        if ((l2 & 4L) != 0L) {
            this.dkP |= 8L;
            if ((l2 & 0x200L) != 0L) {
                this.dkP |= 0x400L;
            }
            if ((l2 & 0x100L) != 0L) {
                this.dkP |= 0x800L;
            }
        }
        if ((l2 & 2L) != 0L) {
            this.dkP |= 4L;
            if ((l2 & 0x80L) != 0L) {
                this.dkP |= 0x200L;
            }
            if ((l2 & 0x40L) != 0L) {
                this.dkP |= 0x100L;
            }
        }
    }

    private xd_1 aLi() {
        switch (this.dkQ) {
            case 1: {
                return xd_1.azm;
            }
            case 2: {
                return xd_1.azl;
            }
            case 3: {
                return xd_1.azk;
            }
        }
        return xd_1.azj;
    }

    public void aLj() {
        this.dkO |= 0x1000L;
        this.dkO &= 0xFFFFFFFFFFFFFFEFL;
        this.dkO &= 0xFFFFFFFFFFFFFFDFL;
        this.dkO &= 0xFFFFFFFFFFFFFFBFL;
        this.dkO &= 0xFFFFFFFFFFFFFF7FL;
        this.dkO &= 0xFFFFFFFFFFFFFEFFL;
        this.dkO &= 0xFFFFFFFFFFFFFDFFL;
        this.dkO &= 0xFFFFFFFFFFFFFBFFL;
        this.dkO &= 0xFFFFFFFFFFFFF7FFL;
    }

    public void a(int n2, ayr_0 ayr_02, ul_0 ul_02) {
        ayr_0 ayr_03;
        if (ayr_02 == this || this.cpr) {
            return;
        }
        int n3 = this.cpn;
        if (this.dkW) {
            n3 = this.cpn / 10;
        }
        this.dkZ = n2 + n3 + 1;
        if (this.cAX != null && (this.aLr().aLu() == -1 || this.aLr().aLu() > this.dkZ + this.aLr().aus())) {
            this.aLr().a(this.dkZ, ayr_02, ul_02);
        }
        if (this.cAU != null && (this.aLs().aLu() == -1 || this.aLs().aLu() > this.dkZ + this.aLs().aus())) {
            this.aLs().a(this.dkZ, ayr_02, ul_02);
        }
        if (this.cAW != null && (this.aLq().aLu() == -1 || this.aLq().aLu() > this.dkZ + this.aLq().aus())) {
            this.aLq().a(this.dkZ, ayr_02, ul_02);
        }
        if (this.cAV != null && (this.aLp().aLu() == -1 || this.aLp().aLu() > this.dkZ + this.aLp().aus())) {
            this.aLp().a(this.dkZ, ayr_02, ul_02);
        }
        if (this.cps != 0 && this.cpt != 0 && ((ayr_03 = (ayr_0)ul_02.X(this.cps, this.cpt)).aLu() == -1 || ayr_03.aLu() > this.dkZ + ayr_03.aus())) {
            ayr_03.a(this.dkZ, ayr_02, ul_02);
        }
    }

    public void a(ayr_0 ayr_02, ayr_0 ayr_03, ul_0 ul_02) {
        ayr_0 ayr_04;
        this.dla = true;
        if (ayr_03 == this) {
            return;
        }
        int n2 = Integer.MAX_VALUE;
        ayr_0 ayr_05 = null;
        if (this.cAX != null && this.aLr() != ayr_02 && this.aLr().aLu() != -1 && this.aLr().aLu() < n2) {
            ayr_05 = this.aLr();
            n2 = ayr_05.aLu();
        }
        if (this.cAU != null && this.aLs() != ayr_02 && this.aLs().aLu() != -1 && this.aLs().aLu() < n2) {
            ayr_05 = this.aLs();
            n2 = ayr_05.aLu();
        }
        if (this.cAW != null && this.aLq() != ayr_02 && this.aLq().aLu() != -1 && this.aLq().aLu() < n2) {
            ayr_05 = this.aLq();
            n2 = ayr_05.aLu();
        }
        if (this.cAV != null && this.aLp() != ayr_02 && this.aLp().aLu() != -1 && this.aLp().aLu() < n2) {
            ayr_05 = this.aLp();
            n2 = ayr_05.aLu();
        }
        if (this.cps != 0 && this.cpt != 0 && (ayr_04 = (ayr_0)ul_02.X(this.cps, this.cpt)) != ayr_02 && ayr_04.aLu() != -1 && ayr_04.aLu() < n2) {
            ayr_05 = ayr_04;
        }
        if (ayr_05 != null) {
            ayr_05.a(this, ayr_03, ul_02);
        }
    }

    protected boolean isSelected() {
        return this.vd;
    }

    public boolean aLk() {
        String string = this.aKZ();
        int n2 = 0;
        if (this.iM.size() > 0) {
            n2 = ((xj_0)this.iM.get(0)).M();
        }
        return string == dlb || string == dlh || n2 == mh_2.buB.getId() || n2 == mh_2.buF.getId() || n2 == mh_2.bvG.getId();
    }

    public long aLl() {
        long l2 = 0L;
        if (this.iM.size() != 0) {
            if (((xj_0)this.iM.get(0)).M() == mh_2.bwo.getId()) {
                l2 |= 1L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bwq.getId()) {
                l2 |= 2L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvE.getId()) {
                l2 |= 3L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvo.getId()) {
                l2 |= 4L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvi.getId()) {
                l2 |= 5L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvk.getId()) {
                l2 |= 6L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvm.getId()) {
                l2 |= 7L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvQ.getId()) {
                l2 |= 8L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.buV.getId()) {
                l2 |= 9L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.buP.getId()) {
                l2 |= 0xAL;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.buR.getId()) {
                l2 |= 0xBL;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.buT.getId()) {
                l2 |= 0xCL;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvO.getId()) {
                l2 |= 0xDL;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvI.getId()) {
                l2 |= 0xEL;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.buz.getId()) {
                l2 |= 0xFL;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.buD.getId()) {
                l2 |= 0x10L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvK.getId()) {
                l2 |= 0x11L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bux.getId()) {
                l2 |= 0x12L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvG.getId()) {
                l2 |= 0x13L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvX.getId()) {
                l2 |= 0x14L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvM.getId()) {
                l2 |= 0x15L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bwK.getId()) {
                l2 |= 0x18L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bwN.getId()) {
                l2 |= 0x16L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bwM.getId()) {
                l2 |= 0x17L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bwO.getId()) {
                l2 |= 0x19L;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bwL.getId()) {
                l2 |= 0x1AL;
            } else if (((xj_0)this.iM.get(0)).M() == mh_2.bvl.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvp.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvj.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvn.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvR.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buS.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buW.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buQ.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buU.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bvP.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.buy.getId() || ((xj_0)this.iM.get(0)).M() == mh_2.bwr.getId()) {
                l2 |= 0x32L;
            }
        }
        if (this.ir != 0) {
            l2 |= 0x1EL;
        }
        if (this.cps != 0) {
            l2 |= 0x20L;
        }
        if (this.cpq.size() > 0) {
            l2 |= 0x21L;
        }
        if (this.cAT != 0) {
            l2 |= 0x1FL;
        }
        if (this.cpr) {
            l2 |= 0x22L;
        }
        return l2;
    }

    public long aLm() {
        if (!this.dkW) {
            return this.aLl() | 0x40L;
        }
        return this.aLl();
    }

    public long aLn() {
        return this.dkP;
    }

    public short aLo() {
        return this.dkQ;
    }

    public ayr_0 aLp() {
        return (ayr_0)super.azq();
    }

    public ayr_0 aLq() {
        return (ayr_0)super.azr();
    }

    public ayr_0 aLr() {
        return (ayr_0)super.azs();
    }

    public ayr_0 aLs() {
        return (ayr_0)super.azp();
    }

    public ayr_0[] aLt() {
        return this.dkX;
    }

    public int aLu() {
        return this.dkZ;
    }

    public void mO(int n2) {
        this.dkZ = n2;
    }

    public void aLv() {
        this.dkZ = -1;
        this.dla = false;
    }

    public akq_1 getPixmap() {
        if (this.vd) {
            return this.aLx();
        }
        return this.aLw();
    }

    public akq_1 aLw() {
        long l2 = this.aLl();
        if (l2 == 0L) {
            return null;
        }
        if (this.dkR == null || this.dkY) {
            this.dkR = this.dkW ? this.a("sphereBoardSpherePath", -l2, xd_1.azj) : this.a("sphereBoardSpherePath", l2, xd_1.azj);
            this.dkY = false;
        }
        return this.dkR;
    }

    public akq_1 aLx() {
        long l2 = this.aLl();
        if (l2 == 0L) {
            return null;
        }
        if (this.dkS == null) {
            this.dkS = this.dkW ? this.a("sphereBoardSpherePath", -this.aLm(), xd_1.azj) : this.a("sphereBoardSpherePath", this.aLm(), xd_1.azj);
        }
        return this.dkS;
    }

    public akq_1 aLy() {
        if (this.dkT == null || this.dkY) {
            this.dkT = this.dkW ? this.a("sphereBoardPathPath", -this.aLn(), this.aLi()) : this.a("sphereBoardPathPath", this.aLn(), this.aLi());
        }
        return this.dkT;
    }

    public void a(agg agg2) {
        this.dkY = true;
        agg2.a(this.dkV, this.aLy());
        agg2.a(this.dkU, this.aLw());
    }

    public void MX() {
        if (this.dkR != null) {
            this.dkR.setTexture(null);
            this.dkR = null;
        }
        if (this.dkS != null) {
            this.dkS.setTexture(null);
            this.dkS = null;
        }
        if (this.dkT != null) {
            this.dkT.setTexture(null);
            this.dkT = null;
        }
    }

    public int aLz() {
        return this.dkU;
    }

    public void mP(int n2) {
        this.dkU = n2;
    }

    public void mQ(int n2) {
        this.dkV = n2;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }

    private akq_1 a(String string, long l2, xd_1 xd_12) {
        akq_1 akq_12 = null;
        try {
            String string2 = String.format(mu_1.rM().getString(string), l2);
            ef_1 ef_12 = cx_0.JY().a(arX.cQT.iE(), ej_0.aa(string2), string2, new adz_1(), false);
            akq_12 = new akq_1(ef_12);
            akq_12.setRotation(xd_12);
            akq_12.azR();
        }
        catch (aih_2 aih_22) {
            a.warn((Object)aih_22.getMessage());
        }
        return akq_12;
    }
}

