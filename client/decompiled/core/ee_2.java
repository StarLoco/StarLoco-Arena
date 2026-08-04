/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 * Renamed from EE
 */
public class ee_2
extends gn_0
implements hy_0,
ph_2,
aho_0,
oh_0 {
    public static final byte aRP = 0;
    public static final byte aRQ = 1;
    public static final byte aRR = 2;
    public static final String xX = "name";
    public static final String aRS = "breedId";
    public static final String aRT = "sex";
    public static final String aRU = "version";
    public static final String aRV = "hairColorIndex";
    public static final String aRW = "skinColorIndex";
    public static final String aRX = "eyeColorIndex";
    public static final String aRY = "actorDescriptorLibrary";
    public static final String aRZ = "actorLinkage";
    public static final String aSa = "actorAnimation";
    public static final String aSb = "actorDirection";
    public static final String aSc = "actorMaterial";
    public static final String aSd = "actorEquipment";
    public static final String aSe = "isSummoned";
    public static final String aSf = "timelineIconUrl";
    public static final String aSg = "timelineButtonStyle";
    public static final String aSh = "backgroundUrl";
    public static final String aSi = "teamMember";
    public static final String aSj = "teamId";
    public static final String aSk = "isLocalCoachFighter";
    public static final String aSl = "closeCombatSmallDescription";
    public static final String aSm = "closeCombatUsable";
    public static final String aSn = "spells";
    public static final String aSo = "usableFighterCards";
    public static final String aSp = "coachSpells";
    public static final String Oj = "value";
    public static final String aSq = "healthPoints";
    public static final String aSr = "maxHealthPoints";
    public static final String arV = "actionPoints";
    public static final String aSs = "maxActionPoints";
    public static final String aSt = "movePoints";
    public static final String aSu = "maxMovePoints";
    public static final String aSv = "initiativePoints";
    public static final String aSw = "resFirePercent";
    public static final String aSx = "resWaterPercent";
    public static final String aSy = "resEarthPercent";
    public static final String aSz = "resWindPercent";
    public static final String aSA = "dmgFirePercent";
    public static final String aSB = "dmgWaterPercent";
    public static final String aSC = "dmgEarthPercent";
    public static final String aSD = "dmgWindPercent";
    public static final String aSE = "rangeBonus";
    public static final String aSF = "healBonus";
    public static final String aSG = "summonsCount";
    public static final String aSH = "tacklePercent";
    public static final String aSI = "dodgePercent";
    public static final String aSJ = "criticalHitBonus";
    public static final String aSK = "criticalMissMalus";
    public static final String aSL = "damagesRebound";
    public static final String aSM = "damagesBonus";
    public static final String aSN = "weaponEquipment";
    public static final String alr = "petEquipment";
    public static final String alm = "cloakEquipment";
    public static final String alp = "hatEquipment";
    public static final String aSO = "dofusEquipment";
    public static final String aSP = "sphereBoardBonus";
    public static final String aSQ = "runningEffects";
    public static final String aSR = "hasBuff";
    public static final String aSS = "hideInTimeline";
    public static final String Gs = "isSelected";
    public static final String aST = "moraleForProgressBar";
    public static final String aSU = "tirednessForProgressBar";
    public static final String aSV = "moraleForTooltip";
    public static final String aSW = "tirednessForTooltip";
    public static final String aSX = "tirednessIsDangerous";
    public static final String aSY = "xp";
    public static final String aSZ = "totalXp";
    public static final String aTa = "level";
    public static final String aTb = "state";
    public static final String aTc = "conditions";
    public static final String aTd = "legInjury";
    public static final String aTe = "torsoInjury";
    public static final String aTf = "armInjury";
    public static final String aTg = "headInjury";
    public static final String aTh = "otherInjury";
    public static final String aTi = "sphereBoard";
    public static final String aTj = "availableSpells";
    public static final String aTk = "availableFighterCards";
    public static final String aTl = "endFightModifications";
    public static final String aTm = "hasBeenKilled";
    public static final String[] ce = new String[]{"name", "breedId", "sex", "version", "actorDescriptorLibrary", "actorLinkage", "actorAnimation", "actorDirection", "actorMaterial", "isSummoned", "timelineIconUrl", "timelineButtonStyle", "backgroundUrl", "teamMember", "teamId", "isLocalCoachFighter", "closeCombatSmallDescription", "closeCombatUsable", "spells", "usableFighterCards", "coachSpells", "value", "healthPoints", "maxHealthPoints", "actionPoints", "maxActionPoints", "movePoints", "maxMovePoints", "initiativePoints", "resFirePercent", "resWaterPercent", "resEarthPercent", "resWindPercent", "dmgFirePercent", "dmgWaterPercent", "dmgEarthPercent", "dmgWindPercent", "rangeBonus", "healBonus", "tacklePercent", "dodgePercent", "criticalHitBonus", "criticalMissMalus", "summonsCount", "damagesRebound", "damagesBonus", "weaponEquipment", "petEquipment", "cloakEquipment", "hatEquipment", "dofusEquipment", "sphereBoardBonus", "runningEffects", "hasBuff", "hideInTimeline", "isSelected", "moraleForTooltip", "tirednessForTooltip", "tirednessIsDangerous", "xp", "totalXp", "level", "state", "conditions", "sphereBoard", "availableSpells", "availableFighterCards", "endFightModifications"};
    private static final HashMap aTn = new HashMap();
    private static final String[] aTo;
    private mv_1 aTp;
    private final vD aTq;
    protected en_1 aTr;
    protected ajv_2 aTs;
    protected final List aTt = new ArrayList(5);
    protected final Set aTu = new HashSet(5);
    protected ee_2 aTv = null;
    private boolean aTw = false;
    private String aTx = null;
    protected int aRv;
    protected byte aRx;
    protected byte aRy;
    protected byte aRz;
    protected short aRA;
    protected short aRB;
    protected int aRC;
    protected jg_0 aRE = new jg_0();
    protected jg_0 aRF = new jg_0();
    protected OW aTy;

    public ee_2() {
        ll_0 ll_02 = this.baQ.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            aeq_0 aeq_02 = (aeq_0)ll_02.value();
            aeq_02.a(this);
        }
        this.baO.aAt();
        this.aTs = new ajv_2(6, je_1.Wa(), new aau_2(this), true, false, false);
        this.aTs.e(this);
        this.aTr = new en_1(aca_0.aOq(), new abc_1(this), 5, false, false);
        this.aTr.e(this);
        this.baR.a(this);
        this.aTq = new vD(this);
        this.b();
    }

    public void j() {
        super.j();
        this.aTv = null;
        if (this.aTq != null) {
            this.aTq.dispose();
        }
    }

    public vD NW() {
        return this.aTq;
    }

    public void c(long l2) {
        super.c(l2);
        this.aTq.c(l2);
    }

    public boolean Dk() {
        return this.aTv != null;
    }

    public ee_2 NX() {
        return this.aTv;
    }

    public void c(ee_2 ee_22) {
        this.aTv = ee_22;
    }

    public xq NY() {
        return this.baJ;
    }

    public void W(byte by) {
        super.W(by);
        this.Of();
    }

    public void S(byte by) {
        super.S(by);
        this.Of();
    }

    public void b(byte by) {
        super.b(by);
        this.Of();
    }

    public void P(byte by) {
        super.P(by);
        tn_0 tn_02 = tn_0.hT(this.zu);
        float[] fArray = tn_02 != null ? tn_02.Aa() : null;
        this.NW().b(2, fArray);
    }

    public void Q(byte by) {
        super.Q(by);
        tn_0 tn_02 = tn_0.hT(this.zt);
        float[] fArray = tn_02 != null ? tn_02.Aa() : null;
        this.NW().b(1, fArray);
    }

    public void R(byte by) {
        super.R(by);
        tn_0 tn_02 = tn_0.hT(this.aRl);
        float[] fArray = tn_02 != null ? tn_02.Aa() : null;
        this.NW().b(8, fArray);
    }

    public void NZ() {
        this.P(this.zu);
        this.Q(this.zt);
        this.R(this.aRl);
    }

    public void fy(int n2) {
        this.aTx = String.valueOf(n2);
        this.Of();
        if (this.NW() != null) {
            this.NW().BQ();
        }
        this.aTq.aY("AnimStatique02");
        this.Ob();
    }

    public void fz(int n2) {
        this.aTx = String.valueOf(Math.abs(Integer.parseInt(this.Oe())) + 50000);
        this.Of();
        this.aTq.aY("AnimStatique02");
        this.Ob();
    }

    public void fA(int n2) {
        xb_2 xb_22 = null;
        boolean bl2 = false;
        for (xb_2 xb_23 : this.PJ()) {
            ZT zT = (ZT)xb_23;
            if (zT.getId() == mh_2.bvu.getId()) {
                xb_22 = zT;
                continue;
            }
            if (zT.getId() != mh_2.bwg.getId()) continue;
            bl2 = true;
        }
        if (xb_22 != null) {
            xb_22.akv();
        } else if (!bl2) {
            this.aTx = null;
            this.Of();
            this.NZ();
            if (this.NW() != null) {
                this.NW().BQ();
            }
        }
    }

    public void b(byte by, byte by2, byte by3) {
        super.b(by, by2, by3);
        this.Of();
    }

    public void m(int n2, int n3, short s) {
        if (!this.b(avx_0.deu) || apN.aDK().aDL().p(this)) {
            super.m(n2, n3, s);
            this.NW().a(n2, (double)n3, (double)s);
            this.NW().aY(this.NW().Po());
        } else if (this.baV != null) {
            this.baV.m(n2, n3, (short)(s + this.PE()));
        }
    }

    public void n(int n2, int n3, short s) {
        super.m(n2, n3, s);
    }

    public void m(ry ry2) {
        if (!this.b(avx_0.deu) || apN.aDK().aDL().p(this)) {
            super.m(ry2);
            this.NW().a(ry2.getX(), (double)ry2.getY(), (double)ry2.wk());
            this.NW().aY(this.NW().Po());
        } else if (this.baV != null) {
            this.baV.m(ry2.getX(), ry2.getY(), ry2.wk());
        }
    }

    public void a(aak_2 aak_22, byte by) {
    }

    public void b(ye_0 ye_02) {
        this.NW().b((qc_0)ye_02);
        this.d(ye_02);
    }

    public ye_0 Oa() {
        return null;
    }

    public void c(ye_0 ye_02) {
    }

    public void d(ye_0 ye_02) {
        boolean bl2;
        boolean bl3 = bl2 = !this.L().equals(ye_02);
        if (bl2) {
            super.b(ye_02);
            this.Ob();
        }
    }

    public void Ob() {
        azs_0.aLV().a((aho_0)this, aSa);
        azs_0.aLV().a((aho_0)this, aSb);
        azs_0.aLV().a((aho_0)this, aSc);
        azs_0.aLV().a((aho_0)this, aSd);
        azs_0.aLV().a((aho_0)this, aRY);
    }

    public mv_1 Oc() {
        return this.aTp;
    }

    public boolean Od() {
        return this.aTp != null;
    }

    public void g(gn_0 gn_02) {
        super.g(gn_02);
        azs_0.aLV().a((aho_0)this, aTo);
    }

    public void h(gn_0 gn_02) {
        super.h(gn_02);
        azs_0.aLV().a((aho_0)this, aTo);
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        Object object;
        Object object2;
        Object object3;
        if (string.equals(xX)) {
            return this.getName();
        }
        if (string.equals(aRS)) {
            return this.baJ.lV();
        }
        if (string.equals(aRT)) {
            return this.lZ();
        }
        if (string.equals(aRU)) {
            return this.cc();
        }
        if (string.equals(aRV)) {
            return this.lY();
        }
        if (string.equals(aRW)) {
            return this.lX();
        }
        if (string.equals(aRX)) {
            return this.Ns();
        }
        if (string.equals(aRY)) {
            return this.aTq;
        }
        if (string.equals(aRZ)) {
            return this.aTq.Sg();
        }
        if (string.equals(aSa)) {
            return this.aTq.AU();
        }
        if (string.equals(aSb)) {
            return this.aTq.L().getIndex();
        }
        if (string.equals(aSc)) {
            return this.aTq.getMaterial();
        }
        if (string.equals(aSd)) {
            return this.aTq.aTF();
        }
        if (string.equals(aSf)) {
            return this.Oe();
        }
        if (string.equals(aSg)) {
            if (this.On() == 0) {
                if (this.NX() == null) {
                    return "timelineTeam0";
                }
                return "timelineTeamSummon0";
            }
            if (this.NX() == null) {
                return "timelineTeam1";
            }
            return "timelineTeamSummon1";
        }
        if (string.equals(aSe)) {
            return this.Dk();
        }
        if (string.equals(aSh)) {
            try {
                return String.format(mu_1.rM().getString("breedsBackgroundPath"), this.NY().lV());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (string.equals(aSi)) {
            zK zK2 = bs_0.IF().II();
            if (zK2 != null) {
                return zK2.cI(this.getId());
            }
            return false;
        }
        if (string.equals(aSj) && this.PH() != null) {
            return (int)this.PH().lV();
        }
        if (string.equals(aSk) && (object3 = bs_0.IF().II()) != null) {
            boolean bl2 = ((sw_1)object3).afE().du(this.getId()) == apN.aDK().Ln().getId();
            return bl2;
        }
        if (string.equals(aSl)) {
            object3 = new StringBuilder();
            adu_0 adu_02 = apN.aDK().aDL();
            if (adu_02 != null) {
                ((StringBuilder)object3).append(aon_0.aYc().getString("closeCombat")).append(" (").append(this.NY().DO()).append(' ').append(aon_0.aYc().getString("AP")).append(")");
                alt_1 alt_12 = adu_02.a((gn_0)this, null);
                if (alt_12 != alt_1.cFN) {
                    ((StringBuilder)object3).append('\n').append(aon_0.aYc().getString(alt_12.toString()));
                }
            }
            return ((StringBuilder)object3).toString();
        }
        if (string.equals(aSm)) {
            object3 = apN.aDK().aDL();
            if (object3 == null) {
                return "";
            }
            return ((adu_0)object3).a((gn_0)this, null) == alt_1.cFN;
        }
        if (string.equals(aSn)) {
            object3 = new ArrayList();
            for (yp_2 yp_22 : this.aTs) {
                ((ArrayList)object3).add((yp_2)yp_22);
            }
            return ((ArrayList)object3).toArray();
        }
        if (string.equals(aSo)) {
            object3 = new ArrayList();
            for (ve_0 ve_02 : this.aTr) {
                if (!ve_02.isUsable()) continue;
                ((ArrayList)object3).add((ve_0)ve_02);
            }
            return ((ArrayList)object3).toArray();
        }
        if (string.equals(aSp) && (object3 = this.Oj()) != null) {
            ArrayList<yp_2> arrayList = new ArrayList<yp_2>();
            Iterator iterator = ((ajv_2)object3).iterator();
            while (iterator.hasNext()) {
                yp_2 yp_23 = (yp_2)iterator.next();
                arrayList.add(yp_23);
            }
            return arrayList.toArray();
        }
        if (string.equals(Oj)) {
            return this.Oo();
        }
        if (string.equals(aSq)) {
            return this.d(Lr.bqx);
        }
        if (string.equals(aSr)) {
            return this.a(Lr.bqx).max();
        }
        if (string.equals(arV)) {
            return this.d(Lr.bqy);
        }
        if (string.equals(aSs)) {
            return this.a(Lr.bqy).max();
        }
        if (string.equals(aSt)) {
            return this.d(Lr.bqz);
        }
        if (string.equals(aSu)) {
            return this.a(Lr.bqz).max();
        }
        if (string.equals(aSv)) {
            return this.d(Lr.bqA);
        }
        if (string.equals(aSw)) {
            return ee_2.a(this.c(Lr.bqB) + this.c(Lr.bra), Lr.bqB);
        }
        if (string.equals(aSx)) {
            return ee_2.a(this.c(Lr.bqC) + this.c(Lr.bra), Lr.bqC);
        }
        if (string.equals(aSy)) {
            return ee_2.a(this.c(Lr.bqD) + this.c(Lr.bra), Lr.bqD);
        }
        if (string.equals(aSz)) {
            return ee_2.a(this.c(Lr.bqE) + this.c(Lr.bra), Lr.bqE);
        }
        if (string.equals(aSA)) {
            return ee_2.a(this.c(Lr.bqF) + this.c(Lr.brb), Lr.bqF);
        }
        if (string.equals(aSB)) {
            return ee_2.a(this.c(Lr.bqG) + this.c(Lr.brb), Lr.bqG);
        }
        if (string.equals(aSC)) {
            return ee_2.a(this.c(Lr.bqH) + this.c(Lr.brb), Lr.bqH);
        }
        if (string.equals(aSD)) {
            return ee_2.a(this.c(Lr.bqI) + this.c(Lr.brb), Lr.bqI);
        }
        if (string.equals(aSE)) {
            return this.d(Lr.bqT);
        }
        if (string.equals(aSF)) {
            return this.d(Lr.bqX);
        }
        if (string.equals(aSH)) {
            return Math.max(-100, this.d(Lr.brd));
        }
        if (string.equals(aSI)) {
            return Math.min(this.d(Lr.bre), 300);
        }
        if (string.equals(aSJ)) {
            return this.d(Lr.bqU);
        }
        if (string.equals(aSK)) {
            return this.d(Lr.bqV);
        }
        if (string.equals(aSL)) {
            return this.d(Lr.brc);
        }
        if (string.equals(aSG)) {
            return this.d(Lr.bqW);
        }
        if (string.equals(aSN)) {
            return this.Oi().p(vi_1.bSW.aiJ());
        }
        if (string.equals(alr)) {
            return this.Oi().p(vi_1.bSX.aiJ());
        }
        if (string.equals(alm)) {
            return this.Oi().p(vi_1.bSY.aiJ());
        }
        if (string.equals(alp)) {
            return this.Oi().p(vi_1.bSZ.aiJ());
        }
        if (string.equals(aSO)) {
            return this.Oi().p(vi_1.bTa.aiJ());
        }
        if (string.equals(aSQ)) {
            object3 = this.PJ().l(this);
            object2 = new ArrayList();
            while (((age)object3).hasNext()) {
                object = (ZT)((age)object3).zP();
                if (((xb_2)object).mi() == null || ((xb_2)object).mi().iP() != 13 || !((ZT)object).gM()) continue;
                ((ArrayList)object2).add(object);
            }
            Collections.sort(object2, gq.sR);
            if (!((ArrayList)object2).isEmpty()) {
                object = new lb_0();
                Iterator iterator = ((ArrayList)object2).iterator();
                while (iterator.hasNext()) {
                    xb_2 xb_22 = (xb_2)iterator.next();
                    ex_1 ex_12 = new ex_1((yp_2)xb_22.mi(), xb_22.ajQ().getId());
                    int n2 = ex_12.getId();
                    if (!((lb_0)object).bY(n2)) {
                        ((lb_0)object).c(n2, ex_12);
                    }
                    ((ex_1)((lb_0)object).get(n2)).b(xb_22);
                }
                Object[] objectArray = ((lb_0)object).getValues();
                ex_1[] ex_1Array = new ex_1[objectArray.length];
                int n3 = ex_1Array.length;
                for (int j = 0; j < n3; ++j) {
                    ex_1Array[j] = (ex_1)objectArray[j];
                }
                return ex_1Array;
            }
        }
        if (string.equals(aSR)) {
            if (apN.aDK().Ln() != null && this.LQ() != null && this.LQ().Lb() != apN.aDK().Ln().getId() && this.c(avx_0.deu) != 0) {
                return false;
            }
            object3 = this.PJ().l(this);
            while (((age)object3).hasNext()) {
                object2 = ((age)object3).zP();
                if (((xb_2)object2).mi() == null || ((xb_2)object2).mi().iP() != 13 || !((ZT)object2).gM()) continue;
                return true;
            }
            return false;
        }
        if (string.equals(aSS)) {
            boolean bl3 = false;
            object2 = apN.aDK().aDL();
            if (object2 != null && this.b(avx_0.deu) && !((adu_0)object2).p(this)) {
                bl3 = true;
            }
            return bl3;
        }
        if (string.equals(Gs)) {
            object3 = adY.atu().Ol();
            return object3 != null && ((gn_0)object3).getId() == this.getId();
        }
        if (string.equals(aST)) {
            return 100 - this.aRy;
        }
        if (string.equals(aSU)) {
            return 100 - this.aRx;
        }
        if (string.equals(aSV)) {
            return aon_0.aYc().getString("morale") + " : " + this.aRy;
        }
        if (string.equals(aSW)) {
            return aon_0.aYc().getString("tiredness") + " : " + this.aRx;
        }
        if (string.equals(aSX)) {
            return this.aRx > nr_0.Pq - 24 - 12;
        }
        if (string.equals(aSY)) {
            return this.aRv;
        }
        if (string.equals(aSZ)) {
            return this.aRw;
        }
        if (string.equals(aTa)) {
            return this.Op();
        }
        if (string.equals(aTb)) {
            return this.aRz;
        }
        if (string.equals(aTd)) {
            object3 = this.uk.Gj();
            for (int j = 0; j < ((Object)object3).length; ++j) {
                object = bf_1.df().g((short)object3[j]);
                if (((aiz_2)object).getType() == 1) {
                    return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), false);
                }
                if (((aiz_2)object).getType() != 11) continue;
                return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), true);
            }
        }
        if (string.equals(aTf)) {
            object3 = this.uk.Gj();
            for (int j = 0; j < ((Object)object3).length; ++j) {
                object = bf_1.df().g((short)object3[j]);
                if (((aiz_2)object).getType() == 2) {
                    return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), false);
                }
                if (((aiz_2)object).getType() != 12) continue;
                return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), true);
            }
        }
        if (string.equals(aTe)) {
            object3 = this.uk.Gj();
            for (int j = 0; j < ((Object)object3).length; ++j) {
                object = bf_1.df().g((short)object3[j]);
                if (((aiz_2)object).getType() == 4) {
                    return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), false);
                }
                if (((aiz_2)object).getType() != 14) continue;
                return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), true);
            }
        }
        if (string.equals(aTg)) {
            object3 = this.uk.Gj();
            for (int j = 0; j < ((Object)object3).length; ++j) {
                object = bf_1.df().g((short)object3[j]);
                if (((aiz_2)object).getType() == 3) {
                    return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), false);
                }
                if (((aiz_2)object).getType() != 13) continue;
                return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), true);
            }
        }
        if (string.equals(aTh)) {
            object3 = this.uk.Gj();
            for (int j = 0; j < ((ArrayList<E>)object3).length; ++j) {
                object = bf_1.df().g((short)object3[j]);
                if (((aiz_2)object).getType() == 5) {
                    return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), false);
                }
                if (((aiz_2)object).getType() != 15) continue;
                return new jo_2((short)object3[j], this.uk.bp((short)object3[j]), ((aiz_2)object).getType(), ((aiz_2)object).ayX(), ((aiz_2)object).eC(), true);
            }
        }
        if (string.equals(aTc)) {
            object3 = new ArrayList();
            short[] sArray = this.uk.Gj();
            for (int j = 0; j < sArray.length; ++j) {
                aiz_2 aiz_22 = bf_1.df().g(sArray[j]);
                ((ArrayList)object3).add(new ha_2(sArray[j], this.uk.bp(sArray[j]), aiz_22.getType(), aiz_22.ayX(), aiz_22.eC()));
            }
            return ((ArrayList)object3).toArray();
        }
        if (string.equals(aTi)) {
            return akp_1.aVO().aW(this.aRC);
        }
        if (string.equals(aTj)) {
            object3 = new yp_2[this.aRE.size()];
            for (int j = 0; j < this.aRE.size(); ++j) {
                object3[j] = (yp_2)je_1.Wa().el(this.aRE.bu(j));
            }
            return object3;
        }
        if (string.equals(aTk)) {
            object3 = new lb_0();
            if (azs_0.aLV().getProperty("teamManagement.selectedItemCardListType") != null) {
                byte by = (Byte)azs_0.aLV().getProperty("teamManagement.selectedItemCardListType").getValue();
                for (int j = 0; j < this.aRF.size(); ++j) {
                    lb_0 lb_02 = aca_0.aOq().F(this.aRF.bu(j));
                    int[] nArray = lb_02.pL();
                    for (int i2 = 0; i2 < nArray.length; ++i2) {
                        ve_0 ve_03 = (ve_0)lb_02.get(nArray[i2]);
                        if (ve_03.Vk().aiK() != by) continue;
                        ((lb_0)object3).c(nArray[i2], ve_03);
                    }
                }
            }
            return ((lb_0)object3).a(new ve_0[((kB)object3).size()]);
        }
        if (string.equals(aTl)) {
            return this.aTy;
        }
        if (string.equals(aTm)) {
            return this.aTy.isDead();
        }
        return null;
    }

    public String[] getFields() {
        return ce;
    }

    public boolean l(String string) {
        return false;
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
    }

    public boolean i(gn_0 gn_02) {
        boolean bl2 = super.i(gn_02);
        if (bl2) {
            vD vD2 = this.NW();
            vD2.a(false, awm_0.dhH);
            vD2.ls("Porte");
            vD2.aY("Anim01");
            vD2.d(((ee_2)gn_02).NW());
            vD2.dW("AnimStatique02");
        }
        return bl2;
    }

    public boolean a(ry ry2, boolean bl2) {
        return this.a(ry2, bl2, true);
    }

    protected boolean b(ry ry2, boolean bl2) {
        return this.a(ry2, bl2, true);
    }

    public boolean a(ry ry2, boolean bl2, boolean bl3) {
        vD vD2 = this.NW();
        vD2.ls(null);
        vD2.dW("AnimStatique02");
        vD2.Pv();
        if (this.Qa()) {
            mT mT2;
            int n2 = 0;
            int n3 = 0;
            int n4 = 0;
            if (ry2 != null) {
                n2 = Math.abs(ry2.getX() - this.gg().getX());
                n3 = Math.abs(ry2.getY() - this.gg().getY());
                n4 = Math.abs(ry2.wk() - this.gg().wk());
                if (bl3) {
                    if (n4 == 0 && n2 + n3 > 1) {
                        vD2.aY("Anim03Porte");
                    } else {
                        vD2.aY("Anim02Porte");
                    }
                }
            }
            if (n2 == 0 && n3 == 0 && this.gg().wk() < this.baV.gg().wk()) {
                this.baV.m(ry2);
            }
            if ((mT2 = vD2.rF()) != null && !this.baV.b(avx_0.deu) || apN.aDK().aDL().p(this.baV)) {
                vD vD3 = (vD)mT2;
                if (vD3 != null) {
                    vD3.a(true, awm_0.dhI);
                    aEH aEH2 = (aEH)vD3.Pr();
                    if (ry2 != null) {
                        aEH2.nA(n2 + n3);
                        mT2.a(ry2.getX(), (double)ry2.getY(), (double)ry2.wk());
                    }
                }
            } else {
                a.trace((Object)"Lancer: n'avait pas de mobile port\u00e9");
            }
            return super.a(ry2, bl2);
        }
        a.trace((Object)"Essaye de d\u00e9poser alors qu'il ne porte personne");
        return false;
    }

    public void bm(boolean bl2) {
        super.bm(bl2);
        vD vD2 = this.NW();
        vD2.Pv();
        vD2.rF();
    }

    protected String Oe() {
        if (this.baJ != null) {
            return String.valueOf(this.ey) + String.valueOf(this.baJ.lV()) + String.valueOf(this.zv);
        }
        return "";
    }

    protected void Of() {
        if (this.aTq != null) {
            String string = this.aTx == null ? this.Oe() : this.aTx;
            this.aTq.iG(string);
        }
    }

    public boolean Og() {
        return this.aTw;
    }

    public ee_2 a(long l2, ry ry2, int n2) {
        aJt aJt2 = (aJt)ER.OC().dZ(n2);
        if (aJt2 != null) {
            return this.a(l2, new adT(this, aJt2), ry2);
        }
        a.error((Object)("SummoningDefinition id=" + n2 + " est inconnue !"));
        return null;
    }

    public ee_2 a(long l2, ry ry2) {
        return this.a(l2, new wo_1(this), ry2);
    }

    public ee_2 b(long l2, ry ry2, int n2) {
        aJt aJt2 = (aJt)ER.OC().dZ(n2);
        if (aJt2 != null) {
            return this.a(l2, new aad_0(this, aJt2), ry2);
        }
        a.error((Object)("SummoningDefinition id=" + n2 + " est inconnue !"));
        return null;
    }

    private ee_2 a(long l2, ee_2 ee_22, ry ry2) {
        ee_22.c(l2);
        ee_22.m(ry2);
        this.PH().j(ee_22);
        ee_22.aTv = this;
        this.LQ().a(ee_22);
        if (this.Oc() != null) {
            this.Oc().b(ee_22);
        }
        qg_2.g(ee_22.NW());
        ee_22.NW().BR();
        azs_0.aLV().a((aho_0)this, aSn);
        return ee_22;
    }

    public ajv_2 Oh() {
        return this.aTs;
    }

    public en_1 Oi() {
        return this.aTr;
    }

    public ajv_2 Oj() {
        ajO ajO2;
        if (this.LQ() != null && (ajO2 = ((aez_0)this.LQ()).afO()) != null) {
            return ajO2.Oh();
        }
        return null;
    }

    public void b(yp_2 yp_22) {
        if (this.LQ() != null) {
            ((aez_0)this.LQ()).afO().dI(yp_22.getId());
            azs_0.aLV().a((aho_0)this, aSp);
        }
    }

    public void a(wl_1 wl_12) {
        if (wl_12.Di().equals(this.Oi())) {
            switch (wl_12.Dh()) {
                case cqa: 
                case cqb: {
                    auA auA2 = (auA)wl_12;
                    this.NW().b((ve_0)auA2.aHD());
                    this.b((ve_0)auA2.aHD());
                    if (!(auA2.aHD() instanceof on_2)) break;
                    ((on_2)auA2.aHD()).b(this);
                    break;
                }
                case cqc: 
                case cqd: {
                    auA auA3 = (auA)wl_12;
                    this.NW().c((ve_0)auA3.aHD());
                    this.c((ve_0)auA3.aHD());
                    break;
                }
                case cqh: {
                    this.NW().aBx();
                    this.PK();
                }
            }
        } else if (wl_12.Di().equals(this.Oh())) {
            switch (wl_12.Dh()) {
                case cqa: 
                case cqb: 
                case cqc: 
                case cqd: {
                    auA auA4 = (auA)wl_12;
                    if (!(auA4.aHD() instanceof zd_2)) break;
                    ((zd_2)auA4.aHD()).b(this);
                    break;
                }
            }
        }
    }

    public void a(alm_0 alm_02) {
        String[] stringArray = (String[])aTn.get(alm_02.atT());
        if (stringArray != null) {
            for (String string : stringArray) {
                azs_0.aLV().a((aho_0)this, string);
            }
        }
    }

    public void a(aak_2 aak_22) {
        if (aak_22 == null) {
            return;
        }
        if (aak_22 instanceof avx_0) {
            switch ((avx_0)aak_22) {
                case dez: {
                    azs_0.aLV().a((aho_0)this, aSn);
                    azs_0.aLV().a((aho_0)this, aSp);
                    azs_0.aLV().a((aho_0)this, aSm);
                    azs_0.aLV().a((aho_0)this, aSo);
                    break;
                }
                case deG: {
                    if (this.b(aak_22)) {
                        this.aTq.aO(true);
                    } else {
                        this.aTq.aO(false);
                    }
                    ((Entity3D)this.aTq.aTm()).c(this.aTq.getMaterial());
                    break;
                }
                case dew: {
                    azs_0.aLV().a((aho_0)this, aSn);
                    azs_0.aLV().a((aho_0)this, aSp);
                    azs_0.aLV().a((aho_0)this, aSm);
                    azs_0.aLV().a((aho_0)this, aSo);
                    break;
                }
                case deu: {
                    adu_0 adu_02 = (adu_0)this.Oc();
                    if (adu_02 == null || this.aTq == null) {
                        return;
                    }
                    azs_0.aLV().a((aho_0)this, aSS);
                    if (adu_02.p(this)) {
                        if (this.b(aak_22)) {
                            if (avu_0.aIB().aIC()) {
                                this.aTq.W(0.2f);
                            } else {
                                this.aTq.W(0.4f);
                            }
                        } else if (avu_0.aIB().aIC()) {
                            this.aTq.W(0.3f);
                        } else {
                            this.aTq.W(1.0f);
                        }
                        ((Entity3D)this.aTq.aTm()).c(this.aTq.getMaterial());
                        break;
                    }
                    if (avu_0.aIB().aIC()) {
                        this.aTq.W(0.3f);
                    } else {
                        this.aTq.W(1.0f);
                    }
                    ((Entity3D)this.aTq.aTm()).c(this.aTq.getMaterial());
                    aoq_0 aoq_02 = this.Oc().gV();
                    gn_0 gn_02 = this.rD() ? this.baV : (this.Qa() ? this.baW : null);
                    if (this.b(aak_22)) {
                        this.NW().eT(false);
                        if (gn_02 == null || gn_02.PL().c((aak_2)avx_0.deu) == 0) {
                            aoq_02.a(this, false);
                            if (gn_02 != null) {
                                aoq_02.a(gn_02, false);
                            }
                        }
                        if (add_1.aOG().kR("fighterInformationsDialog") && ((ee_2)azs_0.aLV().getProperty("fight.timeline.selectedFighter").getValue()).getId() == this.nD) {
                            add_1.aOG().kO("fighterInformationsDialog");
                        }
                    } else if (gn_02 == null || gn_02.PL().c((aak_2)avx_0.deu) == 0) {
                        aoq_02.a(this, true);
                        if (gn_02 != null) {
                            aoq_02.a(gn_02, true);
                        }
                    }
                    this.aTq.setVisible(!this.b(aak_22));
                }
            }
        }
    }

    public void a(axw axw2) {
        this.aTp = (mv_1)axw2;
        super.a(this.aTp);
    }

    public void Ok() {
        this.aTp = null;
    }

    public void a(kb_1 kb_12) {
        switch (kb_12.getId()) {
            case 101: {
                this.baS.yB();
                if (apN.aDK().aDL().p(this) || !this.b(avx_0.deu)) {
                    this.NW().BT();
                }
                this.aTw = true;
                break;
            }
            case 102: {
                this.NW().BU();
                this.aTw = false;
            }
        }
    }

    public abv_1 Ol() {
        abv_1 abv_12 = new abv_1();
        abv_12.b(ByteBuffer.wrap(this.cd()));
        abv_12.d(this.aRF);
        abv_12.c(this.aRE);
        return abv_12;
    }

    public et_2 Om() {
        et_2 et_22 = new et_2();
        et_22.setName(this.getName());
        et_22.c(this.baJ.lV());
        et_22.S(this.lZ());
        et_22.b(this.cc());
        et_22.P(this.lY());
        et_22.Q(this.lX());
        et_22.R(this.Ns());
        et_22.E(this.Oh().cd());
        et_22.F(this.Oi().cd());
        et_22.av(this.Oo());
        return et_22;
    }

    public String toString() {
        return this.getName();
    }

    public byte On() {
        if (this.Aw != null) {
            return this.Aw.lV();
        }
        return -1;
    }

    public short Oo() {
        je_2 je_22 = jk_1.mf().mg();
        int n2 = je_22.ng()[this.baJ.lV()];
        return (short)(super.Oo() + n2);
    }

    public boolean isEditable() {
        Object[] objectArray = bs_0.IF().IH().getValues();
        int n2 = objectArray.length;
        for (int j = 0; j < n2; ++j) {
            zK zK2 = (zK)objectArray[j];
            if (!zK2.afE().m(this.getId())) continue;
            return zK2.tI() < 100;
        }
        return true;
    }

    public void a(fv fv2, int n2, short s) {
        super.a(fv2, n2, s);
        azs_0.aLV().a((aho_0)this, aSn);
    }

    public void f(et_2 et_22) {
        super.f(et_22);
        if (et_22.NK()) {
            this.aRv = et_22.Nx();
            this.aRw = et_22.Ny();
            this.aRx = et_22.Nz();
            this.aRy = et_22.NA();
            this.aRz = et_22.NB();
            this.aRC = et_22.NH();
            this.aRA = et_22.NC();
            this.aRB = et_22.ND();
            this.uk = et_22.kh();
            this.aRF = et_22.NJ();
            this.aRE = et_22.NI();
            this.aRD = et_22.NE();
        }
    }

    public boolean a(ayr_0 ayr_02, ahr_2 ahr_22, boolean bl2) {
        int n2;
        ArrayList arrayList;
        Ei ei = (Ei)akp_1.aVO().aW(this.aRC);
        ayr_0 ayr_03 = (ayr_0)ei.X(this.aRA, this.aRB);
        this.aRA = ayr_02.aut();
        this.aRB = ayr_02.auu();
        if (this.aRD.contains(ayr_02.getId())) {
            this.aRv -= ayr_02.aus() / 10;
        } else {
            this.aRv -= ayr_02.aus();
            this.aRD.add(ayr_02.getId());
            if (ayr_02.el() > 0) {
                this.aRE.add(ayr_02.el());
            }
            if (ayr_02.azt() > 0) {
                this.aRF.add(ayr_02.azt());
            }
        }
        if (bl2) {
            ayr_02.a(ahr_22.getMesh());
        } else {
            arrayList = ei.a((ajM)ayr_03, (ajM)ayr_02);
            for (n2 = 0; n2 < arrayList.size(); ++n2) {
                ayr_0 ayr_04 = (ayr_0)arrayList.get(n2);
                ayr_04.eB(true);
                ayr_04.a(ahr_22.getMesh());
            }
        }
        arrayList = ayr_02.eC();
        int n3 = arrayList.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ((xj_0)arrayList.get(n2)).a(ayr_02, this, this.baZ, mh_2.YJ(), this.gn(), this.go(), this.gp(), this, null);
        }
        azs_0.aLV().a((aho_0)this, ce);
        azs_0.aLV().a((aho_0)ayr_02, ayr_0.ce);
        return true;
    }

    public boolean c(ayr_0 ayr_02) {
        return this.NB() != 2 && this.NB() != 3 && (ayr_02.aus() <= this.aRv || this.aRD.contains(ayr_02.getId()) && ayr_02.aus() / 10 <= this.aRv);
    }

    public int Nx() {
        return this.aRv;
    }

    public int Ny() {
        return this.aRw;
    }

    public void ft(int n2) {
        this.aRv += n2;
        this.aRw += n2;
    }

    public short Op() {
        return nr_0.cs(this.aRw);
    }

    public byte Nz() {
        return this.aRx;
    }

    public byte NA() {
        return this.aRy;
    }

    public byte NB() {
        return this.aRz;
    }

    public void V(byte by) {
        this.aRz = by;
    }

    public short NC() {
        return this.aRA;
    }

    public short ND() {
        return this.aRB;
    }

    public void aw(short s) {
        this.aRA = s;
    }

    public void ax(short s) {
        this.aRB = s;
    }

    public int NH() {
        return this.aRC;
    }

    public OW Oq() {
        return this.aTy;
    }

    public void a(OW oW) {
        this.aTy = oW;
        this.ft(this.aTy.atk());
    }

    public void c(jg_0 jg_02) {
        this.aRE = jg_02;
    }

    public void d(jg_0 jg_02) {
        this.aRF = jg_02;
    }

    protected void a(jg_0 jg_02, vy_1 vy_12) {
        this.a(jg_02, vy_12, akp_1.aVO());
    }

    public void a(bz_0 bz_02) {
        this.aTt.add(bz_02);
    }

    public List Or() {
        return this.aTt;
    }

    public final void b(bz_0 bz_02) {
        if (bz_02 != null) {
            this.aTu.add(bz_02);
        }
    }

    public final void Os() {
        this.aTt.removeAll(this.aTu);
        this.aTu.clear();
    }

    public int oz() {
        return 0;
    }

    static {
        aTn.put(Lr.bqx, new String[]{aSq, aSr, aSn});
        aTn.put(Lr.bqy, new String[]{arV, aSs, aSp, aSn, aSo, aSl, aSm});
        aTn.put(Lr.bqz, new String[]{aSt, aSu, aSn, aSo, aSl, aSm});
        aTn.put(Lr.bqA, new String[]{aSv});
        aTn.put(Lr.bra, new String[]{aSw, aSx, aSy, aSz});
        aTn.put(Lr.bqB, new String[]{aSw});
        aTn.put(Lr.bqC, new String[]{aSx});
        aTn.put(Lr.bqD, new String[]{aSy});
        aTn.put(Lr.bqE, new String[]{aSz});
        aTn.put(Lr.brb, new String[]{aSA, aSB, aSC, aSD});
        aTn.put(Lr.bqF, new String[]{aSA});
        aTn.put(Lr.bqG, new String[]{aSB});
        aTn.put(Lr.bqH, new String[]{aSC});
        aTn.put(Lr.bqI, new String[]{aSD});
        aTn.put(Lr.bqT, new String[]{aSE});
        aTn.put(Lr.bqX, new String[]{aSF});
        aTn.put(Lr.brd, new String[]{aSH});
        aTn.put(Lr.bre, new String[]{aSI});
        aTn.put(Lr.bqU, new String[]{aSJ});
        aTn.put(Lr.bqV, new String[]{aSK});
        aTn.put(Lr.bqW, new String[]{aSG, aSn});
        aTn.put(Lr.brc, new String[]{aSL});
        aTo = new String[]{aSn, aSo, aSp, aSl, aSm};
    }
}

