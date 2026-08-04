/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from sJ
 */
public class sj_1
extends aez_0 {
    public static final String akW = "cardInventory";
    public static final String akX = "cardSets";
    public static final String akY = "cardPets";
    public static final String akZ = "specialCardInventory";
    public static final String ala = "equipedEmotes";
    public static final String alb = "inventorySynchronized";
    public static final String alc = "filtredEquipmentList";
    public static final String ald = "unlockedColors";
    public static final String ale = "pantEquipment";
    public static final String alf = "hairsEquipment";
    public static final String alg = "tatooEquipment";
    public static final String alh = "armbandLeftEquipment";
    public static final String ali = "armbandRightEquipment";
    public static final String alj = "shoesEquipment";
    public static final String alk = "shoulderpadLeftEquipment";
    public static final String all = "shoulderpadRightEquipment";
    public static final String alm = "cloakEquipment";
    public static final String aln = "trousersEquipment";
    public static final String alo = "shirEquipment";
    public static final String alp = "hatEquipment";
    public static final String alq = "staffEquipment";
    public static final String alr = "petEquipment";
    public static final String als = "cureEquipment";
    public static final String alt = "coachCardEffects";
    public static final String alu = "standingForProgressBar";
    public static final String alv = "standingNeededForNextLevel";
    public static final String alw = "strengthForProgressBar";
    public static final String alx = "strengthNeededForNextLevel";
    public static final String aly = "guildInfos";
    public static final String[] ce = new String[]{"cardInventory", "filtredEquipmentList", "cardSets", "cardPets", "specialCardInventory", "equipedEmotes", "inventorySynchronized", "pantEquipment", "hairsEquipment", "tatooEquipment", "armbandLeftEquipment", "armbandRightEquipment", "shoesEquipment", "shoulderpadLeftEquipment", "shoulderpadRightEquipment", "cloakEquipment", "trousersEquipment", "shirEquipment", "hatEquipment", "staffEquipment", "petEquipment", "cureEquipment", "coachCardEffects"};
    public static final String[] oT = new String[ce.length + aez_0.ce.length];
    private static final String[] alz;
    private static final String[] alA;
    private final ky_2 alB = new ky_2(aoi_0.aXY(), akv_2.aVW());
    private boolean alC = false;
    private boolean alD = false;
    private aez_0 alE = null;
    private boolean alF = true;
    private boolean alG = false;
    private boolean alH = false;
    private aMK alI;
    private byte alJ;
    private vy_1 uk;
    private boolean alK = false;

    public sj_1() {
        this.alB.b(new amv_0(this));
        this.Lp = LS.Yf().Yh();
    }

    public aez_0 yC() {
        return this.alE;
    }

    public void a(aez_0 aez_02) {
        this.alE = aez_02;
    }

    public void a(PlayerStatisticsReport playerStatisticsReport) {
        super.a(playerStatisticsReport);
        azs_0.aLV().a((aho_0)this, alA);
    }

    public ky_2 yD() {
        return this.alB;
    }

    public boolean yE() {
        return this.alC;
    }

    public boolean yF() {
        return this.alD;
    }

    public void a(wy_2 wy_22, short s) {
        try {
            wy_2 wy_23;
            if (!this.yD().B(s)) {
                wy_23 = (wy_2)this.yD().z(s);
                if (wy_23 != null && wy_23.jf() != wy_22.jf()) {
                    this.d(wy_23);
                } else {
                    return;
                }
            }
            wy_23 = akv_2.aVW().ac(ByteBuffer.wrap(wy_22.cd()));
            wy_23.q((short)1);
            if (this.yD().a(wy_23, s)) {
                wy_22.w((short)-1);
                azs_0.aLV().a((aho_0)this, alz);
                azs_0.aLV().a((aho_0)this, akX);
                afl_0 afl_02 = azs_0.aLV().getProperty("coachManagement.currentSet");
                azs_0.aLV().a((aho_0)((fe_1)afl_02.getValue()), fe_1.aVa);
                this.alC = true;
            }
            for (short s2 = 0; s2 < 14; s2 = (short)(s2 + 1)) {
                wy_2 wy_24 = (wy_2)apN.aDK().Ln().yD().z(s2);
                if (wy_24 == null) continue;
                azs_0.aLV().a((aho_0)wy_24, wy_2.ce);
            }
        }
        catch (Exception exception) {
            a.warn((Object)"", (Throwable)exception);
        }
    }

    public void d(wy_2 wy_22) {
        wy_2 wy_23 = aoi_0.aXY().ac(ByteBuffer.wrap(wy_22.cd()));
        wy_23.q((short)1);
        this.yD().b(wy_22);
        try {
            this.yD().pI().a(wy_23);
        }
        catch (Exception exception) {
            a.warn((Object)"", (Throwable)exception);
        }
        this.alC = true;
        afl_0 afl_02 = azs_0.aLV().getProperty("coachManagement.currentSet");
        azs_0.aLV().a((aho_0)((fe_1)afl_02.getValue()), fe_1.aVa);
        for (short s = 0; s < 14; s = (short)(s + 1)) {
            wy_2 wy_24 = (wy_2)apN.aDK().Ln().yD().z(s);
            if (wy_24 == null) continue;
            azs_0.aLV().a((aho_0)wy_24, wy_2.ce);
        }
    }

    public void e(wy_2 wy_22) {
        this.yD().d(wy_22);
        this.yL();
        this.alD = true;
    }

    public void f(wy_2 wy_22) {
        Object object;
        Object object2;
        this.e(wy_22);
        sj_1 sj_12 = apN.aDK().Ln();
        sj_12.av(false);
        if (sj_12.yE()) {
            object2 = new int[14];
            for (short s = 0; s < 14; s = (short)(s + 1)) {
                wy_2 wy_23 = (wy_2)sj_12.yD().z(s);
                if (wy_23 == null) continue;
                object2[s] = wy_23.jf();
            }
            object = new aEl();
            ((aEl)object).I((int[])object2);
            apN.aDK().vJ().b((pr_0)object);
        }
        if (sj_12.yF()) {
            object2 = new ArrayList();
            for (wy_2 wy_23 : sj_12.aQn().pI()) {
                wy_2 wy_24 = (wy_2)sj_12.yD().ai(wy_23.je());
                if (wy_24 != null) continue;
                ((ArrayList)object2).add(wy_23);
            }
            object = new fh_0();
            ((fh_0)object).l((ArrayList)object2);
            apN.aDK().vJ().b((pr_0)object);
        }
        sj_12.av(true);
    }

    public void yG() {
        Object object;
        Object object2;
        this.av(false);
        if (this.yE()) {
            object2 = new int[14];
            for (short s = 0; s < 14; s = (short)(s + 1)) {
                wy_2 wy_22 = (wy_2)this.yD().z(s);
                if (wy_22 == null) continue;
                object2[s] = wy_22.jf();
            }
            object = new aEl();
            ((aEl)object).I((int[])object2);
            apN.aDK().vJ().b((pr_0)object);
        }
        if (this.yF()) {
            object2 = new ArrayList();
            for (wy_2 wy_22 : this.aQn().pI()) {
                wy_2 wy_23 = (wy_2)this.yD().ai(wy_22.je());
                if (wy_23 != null) continue;
                ((ArrayList)object2).add(wy_22);
            }
            object = new fh_0();
            ((fh_0)object).l((ArrayList)object2);
            apN.aDK().vJ().b((pr_0)object);
        }
        this.av(true);
    }

    public void yH() {
        this.yI();
        this.alB.a(this);
        this.alB.k(this.aQn().pE());
        this.alB.l(this.aQn().pF());
        this.yR();
    }

    public void yI() {
        this.alB.c(this);
        this.alB.removeAll();
        this.alC = false;
    }

    public ArrayList yJ() {
        ArrayList arrayList = new ArrayList();
        this.Lp.a(new amR(this, arrayList));
        return arrayList;
    }

    public String[] getFields() {
        return oT;
    }

    public Object getFieldValue(String string) {
        if (string.equals(akW)) {
            ArrayList<wy_2> arrayList = new ArrayList<wy_2>();
            for (wy_2 wy_22 : this.yD().pI()) {
                if (wy_22.hG() <= 0 || wy_22.tj() == aMK.dYu) continue;
                arrayList.add(wy_22);
            }
            Collections.sort(arrayList);
            return arrayList.toArray();
        }
        if (string.equals(akX)) {
            int n2;
            lb_0 lb_02 = new lb_0();
            ArrayList arrayList = new ArrayList();
            for (wy_2 wy_23 : this.aQn().pI()) {
                n2 = ((xj)wy_23.NR()).tm();
                if (n2 == 0) continue;
                lb_02.c(n2, (fe_1)ayc_0.aLE().mS(n2));
            }
            for (wy_2 wy_23 : this.aQn().pH()) {
                n2 = ((xj)wy_23.NR()).tm();
                if (n2 == 0) continue;
                lb_02.c(n2, (fe_1)ayc_0.aLE().mS(n2));
            }
            lb_02.a(new amp_0(this, arrayList));
            Collections.sort(arrayList);
            fe_1[] fe_1Array = new fe_1[lb_02.size()];
            arrayList.toArray(fe_1Array);
            return fe_1Array;
        }
        if (string.equals(akZ)) {
            ArrayList<wy_2> arrayList = new ArrayList<wy_2>();
            for (wy_2 wy_24 : this.yD().pI()) {
                if (wy_24.tj() != aMK.dYv && wy_24.tj() != aMK.dYb || wy_24.hG() <= 0) continue;
                arrayList.add(wy_24);
            }
            return arrayList.toArray();
        }
        if (string.equals(ala)) {
            ano_0 ano_02 = new ano_0();
            for (wy_2 wy_25 : this.aQn().pI()) {
                if (wy_25.hG() <= 0 || wy_25.tj() != aMK.dYb) continue;
                ano_02.put((Object)Math.abs(wy_25.jf()), wy_25);
            }
            return ano_02.values().toArray();
        }
        if (string.equals(alc)) {
            ano_0 ano_03 = new ano_0();
            for (wy_2 wy_26 : this.yD().pI()) {
                if (wy_26.hG() <= 0 || wy_26.tj() != this.alI) continue;
                ano_03.put(wy_26.getName(), wy_26);
            }
            return ano_03.values().toArray();
        }
        if (string.equals(ald)) {
            ArrayList<xj> arrayList = new ArrayList<xj>();
            for (wy_2 wy_27 : this.yD().pI()) {
                xj xj2;
                if (wy_27.hG() <= 0 || wy_27.tj() != aMK.dYD || (xj2 = (xj)la_0.XJ().pj(wy_27.jf())).tD() != this.alJ) continue;
                arrayList.add(xj2);
            }
            return arrayList.toArray();
        }
        if (string.equals(ale)) {
            return this.yD().z(aMK.dYc.aXg()[0]);
        }
        if (string.equals(alf)) {
            return this.yD().z(aMK.dYd.aXg()[0]);
        }
        if (string.equals(alg)) {
            return this.yD().z(aMK.dYe.aXg()[0]);
        }
        if (string.equals(alh)) {
            return this.yD().z(aMK.dYf.aXg()[0]);
        }
        if (string.equals(ali)) {
            return this.yD().z(aMK.dYf.aXg()[0]);
        }
        if (string.equals(alj)) {
            return this.yD().z(aMK.dYg.aXg()[0]);
        }
        if (string.equals(alk)) {
            return this.yD().z(aMK.dYh.aXg()[0]);
        }
        if (string.equals(all)) {
            return this.yD().z(aMK.dYh.aXg()[0]);
        }
        if (string.equals(alm)) {
            return this.yD().z(aMK.dYi.aXg()[0]);
        }
        if (string.equals(aln)) {
            return this.yD().z(aMK.dYj.aXg()[0]);
        }
        if (string.equals(alo)) {
            return this.yD().z(aMK.dYk.aXg()[0]);
        }
        if (string.equals(alp)) {
            return this.yD().z(aMK.dYl.aXg()[0]);
        }
        if (string.equals(alq)) {
            return this.yD().z(aMK.dYm.aXg()[0]);
        }
        if (string.equals(alr)) {
            return this.yD().z(aMK.dYn.aXg()[0]);
        }
        if (string.equals(als)) {
            return this.yD().z(aMK.dYo.aXg()[0]);
        }
        if (string.equals(alt)) {
            gu gu2;
            akw_0 akw_02;
            akw_0[] akw_0Array;
            asc asc2 = new asc();
            lb_0 lb_03 = new lb_0();
            for (wy_2 wy_28 : this.aQn().pH()) {
                if (((xj)wy_28.NR()).tr() > aet_0.nJ(this.afQ())) continue;
                akw_0Array = ((xj)wy_28.NR()).tu();
                for (int j = 0; j < akw_0Array.length; ++j) {
                    akw_02 = akw_0Array[j];
                    gu2 = (gu)lb_03.get(akw_02.getType());
                    if (gu2 == null) {
                        gu2 = new gu(akw_02);
                        lb_03.c(gu2.getType(), gu2);
                        continue;
                    }
                    gu2.rg()[0] = gu2.rg()[0] + akw_02.rg()[0];
                }
                aqy_0 aqy_02 = ((xj)wy_28.NR()).tn();
                if (aqy_02 == null) continue;
                if (!asc2.contains(aqy_02.getId())) {
                    asc2.d(aqy_02.getId(), (byte)1);
                    continue;
                }
                asc2.d(aqy_02.getId(), (byte)(asc2.get(aqy_02.getId()) + 1));
            }
            Object object = asc2.pL();
            for (int j = 0; j < ((Object)object).length; ++j) {
                akw_0Array = ayc_0.aLE().mS((int)object[j]).tu();
                for (int i2 = 0; i2 < akw_0Array.length; ++i2) {
                    akw_02 = akw_0Array[i2];
                    if (akw_02.aAm() > asc2.get((int)object[j])) continue;
                    gu2 = (gu)lb_03.get(akw_02.getType());
                    if (gu2 == null) {
                        gu2 = new gu(akw_02);
                        lb_03.c(gu2.getType(), gu2);
                        continue;
                    }
                    gu2.rg()[0] = gu2.rg()[0] + akw_02.rg()[0];
                }
            }
            return lb_03.getValues();
        }
        if (string.equals(alu)) {
            int n3 = aet_0.nJ(this.afQ());
            int n4 = nr_0.ct(n3);
            int n5 = nr_0.ct(n3 + 1);
            return 100 - Math.round((float)(this.afQ() - n4) / (float)(n5 - n4) * 100.0f);
        }
        if (string.equals(alv)) {
            return nr_0.ct(aet_0.nJ(this.afQ()) + 1);
        }
        if (string.equals(alw)) {
            short s = this.bi((byte)1);
            int n6 = aet_0.nH(s);
            int n7 = aet_0.nI(n6);
            int n8 = aet_0.nI(n6 + 1);
            return 100 - Math.round((float)(s - n7) / (float)(n8 - n7) * 100.0f);
        }
        if (string.equals(alx)) {
            return aet_0.nI(aet_0.nH(this.bi((byte)1)) + 1);
        }
        if (string.equals("tournamentToken")) {
            return this.yJ();
        }
        return super.getFieldValue(string);
    }

    public boolean l(String string) {
        return string.equals("name");
    }

    public void a(String string, Object object) {
        if (string.equals("name")) {
            this.setName((String)object);
        }
    }

    public void yK() {
        azs_0.aLV().a((aho_0)this, "actorDescriptorLibrary");
    }

    public void yL() {
        fe_1[] fe_1Array;
        azs_0.aLV().a((aho_0)this, akZ);
        this.yM();
        if (!this.alK && (fe_1Array = (fe_1[])this.getFieldValue(akX)) != null && fe_1Array.length > 0) {
            this.alK = true;
            azs_0.aLV().g("coachManagement.currentSet", fe_1Array[0]);
        }
    }

    public void yM() {
        azs_0.aLV().a((aho_0)this, akX);
        afl_0 afl_02 = azs_0.aLV().getProperty("coachManagement.currentSet");
        if (afl_02 != null) {
            azs_0.aLV().a((aho_0)((fe_1)afl_02.getValue()), fe_1.ce);
        }
    }

    public void yN() {
        azs_0.aLV().a((aho_0)this, alz);
    }

    public void a(wl_1 wl_12) {
        super.a(wl_12);
        this.yN();
    }

    public void b(ag_2 ag_22) {
        switch (ag_22.Ho()) {
            case aR: {
                lq lq2 = (lq)ag_22;
                switch (lq2.qj()) {
                    case aaE: 
                    case aaF: {
                        CG cG = (CG)ag_22.Hp();
                        r_0 r_02 = cG.Kw();
                        if (r_02 == null) break;
                        r_02.D();
                        break;
                    }
                    case aaH: 
                    case aaG: {
                        apN.aDK().b(amq_1.aXh());
                        break;
                    }
                    case aaI: {
                        apN.aDK().b(amq_1.aXh());
                    }
                }
                break;
            }
            case aQ: {
                CG cG = (CG)ag_22.Hp();
                byte by = cG.h(this);
                if (ag_22.fY() != by) {
                    cG.KB();
                    break;
                }
                cG.KA();
                break;
            }
            case aO: {
                CG cG = (CG)ag_22.Hp();
                cG.Kw().D();
                po_0.abV().abW();
                apN.aDK().b(abv_2.aNt());
                azs_0.aLV().g("exchange.cardTrade", cG);
                int n2 = cG.Kx() ? 1 : 0;
                azs_0.aLV().g("exchange.remoteCoach", cG.ck(n2));
                apN.aDK().a(amq_1.aXh());
                break;
            }
            case aN: {
                apN.aDK().a(abv_2.aNt());
                CG cG = (CG)ag_22.Hp();
                String string = aon_0.aYc().getString("exchangeInvitation.messageOut", cG.ck(1).getName());
                r_0 r_03 = add_1.aOG().a(string, 1156L, 102, 1);
                cG.a(r_03);
                r_03.a(new amo_1(this, cG));
                break;
            }
            case aM: {
                apN.aDK().a(abv_2.aNt());
                CG cG = (CG)ag_22.Hp();
                String string = aon_0.aYc().getString("exchangeInvitation.messageIn", cG.ck(0).getName());
                if (!this.alG && !mc_1.qM().qO().containsKey(cG.ck(0).getName().toLowerCase())) {
                    r_0 r_04 = add_1.aOG().a(string, 1176L, 102, 1);
                    cG.a(r_04);
                    r_04.a(new amn_0(this, cG));
                    break;
                }
                azS azS2 = azS.aMv();
                azS2.d(cG.getId());
                acu_1.ara().c(azS2);
                break;
            }
            case aP: {
                aak aak2 = (aak)ag_22;
                CG cG = (CG)aak2.Hp();
                byte by = cG.h(this);
                if (aak2.fY() == by) {
                    short s = 0;
                    switch (aak2.aoS()) {
                        case ckq: {
                            s = -aak2.aoU();
                            break;
                        }
                        case ckr: {
                            s = aak2.aoU();
                        }
                    }
                    ky_2 ky_22 = this.yD();
                    uh_1 uh_12 = aak2.aoT();
                    if (ky_22.c((wy_2)uh_12)) {
                        ky_22.f(uh_12.je(), s);
                    } else {
                        try {
                            wy_2 wy_22 = ((wy_2)uh_12).CQ();
                            wy_22.q(s);
                            ky_22.f(wy_22);
                        }
                        catch (gg gg2) {
                            a.error((Object)"Maximum de capacit\u00e9 pour l'inventaire des cartes de panoplie du coach.");
                        }
                        catch (xR xR2) {
                            a.error((Object)"Cas \"Ajout impossible\" avec l'inventaire des cartes de panoplie du coach.");
                        }
                    }
                    this.yL();
                    cG.Ky();
                } else {
                    cG.Kz();
                }
                cG.KB();
                cG.KA();
                break;
            }
        }
        super.b(ag_22);
    }

    protected void g(int[] nArray) {
        Object[] objectArray = ajX.azB().xY().getValues();
        for (int j = 0; j < objectArray.length; ++j) {
            do_1 do_12 = (do_1)objectArray[j];
            if (!do_12.y(new ry(nArray[0], nArray[1], (short)nArray[2]))) continue;
            do_12.b(avr_0.dgp, this);
        }
        nk_0.aaq().at(nArray[0], nArray[1]);
        super.g(nArray);
    }

    public void a(arh_0 arh_02, boolean bl2, boolean bl3) {
        for (int j = 1; j < arh_02.aEF(); ++j) {
            int[] nArray = arh_02.lU(j);
            Object[] objectArray = ajX.azB().xY().getValues();
            for (int i2 = 0; i2 < objectArray.length; ++i2) {
                do_1 do_12 = (do_1)objectArray[i2];
                if (!do_12.gC() || !do_12.y(new ry(nArray[0], nArray[1], (short)nArray[2])) || j == arh_02.aEF() - 1) continue;
                arh_02 = arh_02.bS(0, j + 1);
            }
        }
        super.a(arh_02, bl2, bl3);
    }

    public boolean yO() {
        return this.alF;
    }

    public void av(boolean bl2) {
        this.alF = bl2;
        azs_0.aLV().g("menuBar.coachInventoryButton", bl2);
    }

    public boolean yP() {
        return this.alG;
    }

    public void aw(boolean bl2) {
        this.alG = bl2;
    }

    public boolean yQ() {
        return this.alH;
    }

    public void ax(boolean bl2) {
        this.alH = bl2;
    }

    public void yR() {
        int[] nArray = DofusArenaClientInstance.yl().aod().Pi();
        if (nArray != null) {
            for (int j = 0; j < nArray.length; ++j) {
                wy_2 wy_22 = (wy_2)this.yD().bW(nArray[j]);
                if (wy_22 == null) continue;
                wy_22.w((short)-1);
            }
        }
    }

    public void a(aMK aMK2) {
        this.alI = aMK2;
    }

    public void A(byte by) {
        this.alJ = by;
    }

    public vy_1 kh() {
        return this.uk;
    }

    public void a(vy_1 vy_12) {
        this.uk = vy_12;
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(aez_0.ce, 0, oT, ce.length, aez_0.ce.length);
        alz = new String[]{ale, alf, alg, alh, ali, alj, alk, all, alm, aln, alo, alp, alq, alr, als, "actorDescriptorLibrary", alt};
        alA = new String[]{"statisticsTotalFights", "statisticsTotalFightsWon", "statisticsTotalFightsLost", "statisticsConsecutiveWins", "statisticsConsecutiveLosses", "statisticsTotalFightsTime", "statisticsTotalPlayTime"};
    }
}

