/*
 * Decompiled with CFR 0.152.
 */
import java.util.Calendar;
import java.util.List;

/*
 * Renamed from aFl
 */
public class afl_1
implements atG {
    private static afl_1 dGl = new afl_1();
    private static final int dGm = Integer.MIN_VALUE;
    private static final int dGn = Integer.MIN_VALUE;
    private static final int dGo = Integer.MIN_VALUE;
    private static final int dGp = Integer.MIN_VALUE;
    private static final int dGq = 0;
    private static final int dGr = 0;
    private static final int dDC = 0;
    private static final int dGs = 0;
    private static final int dGt = 0;
    private static final int dGu = 0;
    private static final int dGv = 0;
    private static final short dGw = 1;
    private static final long dGx = 10000L;
    private static final int dGy = 0;
    private static final byte dGz = 12;
    private static final byte dGA = 3;
    private static final byte dGB = 4;
    private static final byte dGC = 1;
    private static int dnY = 0;
    private static int dnZ = 0;
    private static int doa = 0;
    private static int dGD = Integer.MIN_VALUE;
    private static int bhS = 0;
    private static int blF = 0;
    private static int blG = 0;
    private static int blH = 0;
    private static jg_0 dGE;
    private static byte bQW;
    private static byte bQX;
    private static short bQY;
    private static int bQZ;
    private static int bRa;
    private static int bRb;
    private static int bRc;
    private static int bRd;
    private static int bRe;
    private static int bRf;
    private static int bRj;
    private static int bRk;
    private static int bRl;
    private static int bRm;
    private static int bRq;
    private static int bRr;
    private static int bRs;
    private static int bRt;
    private static int dGF;
    private static int dGG;
    private static int dGH;
    private static int dGI;
    private static int cIs;
    private static int bXy;
    private static int dGJ;
    private static int dGK;
    private static int dGL;
    private static int dGM;
    private static int dGN;
    private static int dGO;
    private static int dGP;
    private static String dGQ;
    private static String dGR;

    public static afl_1 aRK() {
        return dGl;
    }

    public void a(fh_2 fh_22, boolean bl2) {
        azs_0.aLV().g("ladderManager", adn_1.aPi());
    }

    public void b(fh_2 fh_22, boolean bl2) {
        azs_0.aLV().kb("ladderManager");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean a(pr_0 pr_02) {
        boolean bl2 = true;
        switch (pr_02.getId()) {
            case 27501: {
                int n2;
                azd_0 azd_02 = (azd_0)pr_02;
                azs_0 azs_02 = azs_0.aLV();
                Object object = this;
                synchronized (object) {
                    dnY = azd_02.aMb();
                    dnZ = azd_02.aMc();
                    doa = azd_02.aMd();
                    dGD = azd_02.aMe();
                }
                object = adn_1.aPi().aPj();
                for (n2 = 0; n2 < doa - dnZ; ++n2) {
                    vv_2 vv_22 = (vv_2)object.get(n2);
                    vv_22.k((short)(n2 + 1 + dnZ));
                    vv_22.q(azd_02.getNames(n2));
                    vv_22.T(azd_02.gs(n2).length() == 0 ? dGR : azd_02.gs(n2));
                    vv_22.bj(azd_02.mW(n2));
                    vv_22.bo(azd_02.mZ(n2));
                    vv_22.aA(azd_02.gq(n2));
                    vv_22.aB(azd_02.gr(n2));
                    vv_22.z(dGD);
                }
                for (n2 = doa - dnZ; n2 < dnY; ++n2) {
                    ((vv_2)object.get(n2)).clear();
                }
                azs_02.g("ladderPlayerSearchButtonVisible", azd_02.VY());
                azs_02.a((aho_0)adn_1.aPi(), "list1vs1");
                if (!add_1.aOG().kR("ladderInformationDialog")) {
                    apN.aDK().a(ahg_1.aTk());
                }
                bl2 = false;
                break;
            }
            case 27503: {
                ij_1 ij_12 = (ij_1)pr_02;
                if (ij_12.cB() == 1) {
                    int n3;
                    azs_0 azs_03 = azs_0.aLV();
                    int n4 = Math.min(ij_12.getSize(), 10);
                    int n5 = ij_12.UI();
                    afl_1 afl_12 = afl_1.aRK();
                    Object object = afl_12;
                    synchronized (object) {
                        afl_1.nR(n5);
                    }
                    object = adn_1.aPi().aPl();
                    for (n3 = 0; n3 < n4; ++n3) {
                        axb_0 axb_02 = (axb_0)object.get(n3);
                        axb_02.k((short)(n5 + n3 + 1));
                        axb_02.setName(ij_12.gj(n3));
                        axb_02.jX(ij_12.gk(n3));
                        axb_02.bj(ij_12.gl(n3));
                    }
                    for (n3 = n4; n3 < 10; ++n3) {
                        ((axb_0)object.get(n3)).clear();
                    }
                }
                azs_0.aLV().a((aho_0)adn_1.aPi(), "listGuild");
                if (!add_1.aOG().kR("ladderInformationDialog")) {
                    apN.aDK().a(ahg_1.aTk());
                }
                bl2 = false;
                break;
            }
            case 27505: {
                int n6;
                aka_0 aka_02 = (aka_0)pr_02;
                azs_0 azs_04 = azs_0.aLV();
                Object object = this;
                synchronized (object) {
                    blF = aka_02.VU();
                    blG = aka_02.VV();
                    blH = aka_02.VW();
                    dGE = aka_02.aVx();
                }
                object = adn_1.aPi().aPk();
                for (n6 = 0; n6 < blH - blG; ++n6) {
                    vv_2 vv_23 = (vv_2)object.get(n6);
                    vv_23.k((short)(n6 + 1 + blG));
                    vv_23.q(aka_02.oR(n6), aka_02.oS(n6));
                    vv_23.ae(aka_02.oT(n6).length() == 0 ? dGR : aka_02.oT(n6));
                    vv_23.bj(aka_02.oU(n6));
                    vv_23.bo(aka_02.oX(n6));
                    vv_23.aA(aka_02.oZ(n6));
                    vv_23.aB(aka_02.pa(n6));
                    vv_23.z(dGE.nm());
                }
                for (n6 = blH - blG; n6 < blF; ++n6) {
                    ((vv_2)object.get(n6)).clear();
                }
                azs_04.g("ladder2vs2BestTeamSearchButtonVisible", aka_02.VY());
                azs_04.a((aho_0)adn_1.aPi(), "list2vs2");
                if (!add_1.aOG().kR("ladderInformationDialog")) {
                    apN.aDK().a(ahg_1.aTk());
                }
                bl2 = false;
                break;
            }
            case 27507: {
                ayb_0 ayb_02;
                int n7;
                uj_0 uj_02 = (uj_0)pr_02;
                azs_0 azs_05 = azs_0.aLV();
                afl_1 afl_13 = this;
                synchronized (afl_13) {
                    bQW = uj_02.ahs();
                    bQX = uj_02.aht();
                    bQY = uj_02.ahu();
                    bQZ = uj_02.ahv();
                    bRa = uj_02.ahw();
                    bRb = uj_02.ahx();
                    bRc = uj_02.ahy();
                    bRd = uj_02.ahz();
                    bRe = uj_02.ahA();
                    bRf = uj_02.ahB();
                    bRj = uj_02.ahD();
                    bRk = uj_02.ahE();
                    bRl = uj_02.ahF();
                    bRm = uj_02.ahG();
                    bRq = uj_02.ahI();
                    bRr = uj_02.ahJ();
                    bRs = uj_02.ahK();
                    bRt = uj_02.ahL();
                }
                short s = (short)Calendar.getInstance().get(1);
                boolean bl3 = bQY == s - 1;
                List list = adn_1.aPi().aPm();
                for (n7 = 0; n7 < bRe - bRd; ++n7) {
                    ayb_02 = (ayb_0)list.get(n7);
                    ayb_02.k((short)(n7 + 1 + bRd));
                    ayb_02.setName(uj_02.ij(n7));
                    ayb_02.mR(uj_02.ik(n7));
                    ayb_02.gd(bRf);
                }
                List list2 = adn_1.aPi().aPn();
                for (n7 = 0; n7 < bRl - bRk; ++n7) {
                    ayb_02 = (ayb_0)list2.get(n7);
                    ayb_02.k((short)(n7 + 1 + bRk));
                    ayb_02.setName(uj_02.il(n7));
                    ayb_02.mR(uj_02.im(n7));
                    ayb_02.gd(bRm);
                }
                List list3 = adn_1.aPi().aPo();
                for (n7 = 0; n7 < bRs - bRr; ++n7) {
                    ayb_02 = (ayb_0)list3.get(n7);
                    ayb_02.k((short)(n7 + 1 + bRr));
                    ayb_02.setName(uj_02.in(n7));
                    ayb_02.mR(uj_02.io(n7));
                    ayb_02.gd(bRt);
                }
                for (n7 = bRe - bRd; n7 < bRc; ++n7) {
                    ((ayb_0)list.get(n7)).clear();
                }
                for (n7 = bRl - bRk; n7 < bRj; ++n7) {
                    ((ayb_0)list2.get(n7)).clear();
                }
                for (n7 = bRs - bRr; n7 < bRq; ++n7) {
                    ((ayb_0)list3.get(n7)).clear();
                }
                azs_05.g("ladderTournamentInformationsMonth", aon_0.aYc().getString("month" + bQW) + " " + s);
                azs_05.g("ladderTournamentInformationsTrimester", aon_0.aYc().getString("ladderInformation.trimester") + " " + (bQX + 1) + ", " + s);
                azs_05.g("ladderTournamentInformationsYear", aon_0.aYc().getString("ladderInformation.year") + " " + bQY);
                azs_05.g("ladderTournamentInformationsCoachPointsInTheMonth", bQZ);
                azs_05.g("ladderTournamentInformationsCoachPointsInTheTrimester", bRa);
                azs_05.g("ladderTournamentInformationsCoachPointsInTheYear", bRb);
                azs_05.g("ladderTournamentBackwardOneInTheYearButtonVisible", !bl3);
                azs_05.g("ladderTournamentForwardOneInTheYearButtonVisible", bl3);
                azs_05.g("ladderTournamentBackwardTenInTheYearButtonVisible", !bl3);
                azs_05.g("ladderTournamentForwardTenInTheYearButtonVisible", !bl3);
                azs_05.g("ladderCoachSearchInTheMonthButtonVisible", uj_02.ahC());
                azs_05.g("ladderCoachSearchInTheTrimesterButtonVisible", uj_02.ahH());
                azs_05.g("ladderCoachSearchInTheYearButtonVisible", !bl3 && uj_02.ahM());
                azs_05.a((aho_0)adn_1.aPi(), "listTournamentInTheMonth");
                azs_05.a((aho_0)adn_1.aPi(), "listTournamentInTheTrimester");
                azs_05.a((aho_0)adn_1.aPi(), "listTournamentInTheYear");
                if (!add_1.aOG().kR("ladderInformationDialog")) {
                    apN.aDK().a(ahg_1.aTk());
                }
                bl2 = false;
                break;
            }
            case 27509: {
                int n8;
                jw_0 jw_02 = (jw_0)pr_02;
                azs_0 azs_06 = azs_0.aLV();
                afl_1 afl_14 = this;
                synchronized (afl_14) {
                    dGF = jw_02.VU();
                    dGG = jw_02.VV();
                    dGH = jw_02.VW();
                    dGI = jw_02.VX();
                }
                List list = adn_1.aPi().aPp();
                for (n8 = 0; n8 < dGH - dGG; ++n8) {
                    String string;
                    try {
                        string = afg_1.kn(jw_02.gt(n8));
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        string = dGQ;
                    }
                    hl_2 hl_22 = (hl_2)list.get(n8);
                    hl_22.k((short)(n8 + 1 + dGG));
                    hl_22.gc(jw_02.gn(n8));
                    hl_22.ep(jw_02.go(n8));
                    hl_22.ae(jw_02.gp(n8));
                    hl_22.aA(jw_02.gq(n8));
                    hl_22.aB(jw_02.gr(n8));
                    hl_22.T(jw_02.gs(n8).length() == 0 ? dGR : jw_02.gs(n8));
                    hl_22.S(string);
                    hl_22.gd(dGI);
                }
                for (n8 = dGH - dGG; n8 < dGF; ++n8) {
                    ((hl_2)list.get(n8)).clear();
                }
                azs_06.g("ladderReputationSearchButtonVisible", jw_02.VY());
                azs_06.a((aho_0)adn_1.aPi(), "listReputation");
                if (!add_1.aOG().kR("ladderInformationDialog")) {
                    apN.aDK().a(ahg_1.aTk());
                }
                bl2 = false;
                break;
            }
            case 27511: {
                String string;
                anc_0 anc_02 = (anc_0)pr_02;
                adn_1 adn_12 = adn_1.aPi();
                if (anc_02.cB() == 1) {
                    int n9;
                    afl_1 afl_15;
                    int n10 = Math.min(anc_02.getSize(), 10);
                    int n11 = anc_02.aCe();
                    afl_1 afl_16 = afl_15 = afl_1.aRK();
                    synchronized (afl_16) {
                        afl_1.nY(anc_02.aCd());
                        afl_1.nX(n11);
                    }
                    List list = adn_12.aPq();
                    for (n9 = 0; n9 < n10; ++n9) {
                        awj awj2 = (awj)list.get(n9);
                        awj2.k((short)(n11 + n9 + 1));
                        awj2.T(anc_02.gj(n9));
                        awj2.y(anc_02.iT(n9));
                        awj2.dZ(anc_02.lw(n9));
                        awj2.ea(anc_02.lx(n9));
                    }
                    for (n9 = n10; n9 < 10; ++n9) {
                        ((awj)list.get(n9)).clear();
                    }
                }
                azs_0 azs_07 = azs_0.aLV();
                try {
                    string = afg_1.kn(anc_02.aCd());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    string = "" + anc_02.aCd();
                }
                azs_07.g("ladderGuildDemonInformationsDemonName", aon_0.aYc().getString("ladderInformation.demon") + " " + string);
                azs_0.aLV().g("demonAffiliationTrade", new ps_1(anc_02.aCf()));
                azs_07.a((aho_0)adn_12, "listGuildDemon");
                if (!add_1.aOG().kR("demonLadderInformationDialog")) {
                    apN.aDK().a(aak_0.aME());
                }
                bl2 = false;
                break;
            }
            case 27513: {
                xn_2 xn_22 = (xn_2)pr_02;
                adn_1 adn_13 = adn_1.aPi();
                if (xn_22.cB() == 1) {
                    int n12;
                    afl_1 afl_17;
                    int n13 = Math.min(xn_22.getSize(), 12);
                    int n14 = xn_22.akP();
                    afl_1 afl_18 = afl_17 = afl_1.aRK();
                    synchronized (afl_18) {
                        afl_1.nZ(n14);
                    }
                    List list = adn_13.aPr();
                    for (n12 = 0; n12 < n13; ++n12) {
                        String string;
                        try {
                            string = afg_1.kn(xn_22.iS(n12));
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            string = "" + xn_22.iS(n12);
                        }
                        ec ec2 = (ec)list.get(n12);
                        ec2.k((short)(n14 + n12 + 1));
                        ec2.S(string);
                        ec2.y(xn_22.iT(n12));
                        ec2.T(xn_22.gj(n12));
                    }
                    for (n12 = n13; n12 < 12; ++n12) {
                        ((ec)list.get(n12)).clear();
                    }
                }
                azs_0.aLV().a((aho_0)adn_13, "listDemon");
                if (!add_1.aOG().kR("ladderInformationDialog")) {
                    apN.aDK().a(ahg_1.aTk());
                }
                bl2 = false;
                break;
            }
            case 27515: {
                int n15;
                amu_0 amu_02 = (amu_0)pr_02;
                azs_0 azs_08 = azs_0.aLV();
                afl_1 afl_19 = this;
                synchronized (afl_19) {
                    dGO = amu_02.aRO();
                    dGK = amu_02.aMb();
                    dGL = amu_02.aRN();
                    dGM = amu_02.aXm();
                    dGN = amu_02.aXn();
                }
                azs_08.g("proLeagueDefinitionName", aon_0.aYc().a(58, dGO, new Object[0]));
                List list = adn_1.aPi().aPs();
                for (n15 = 0; n15 < dGM - dGL; ++n15) {
                    atu atu2 = (atu)list.get(n15);
                    atu2.k((short)(n15 + 1 + dGL));
                    atu2.bU(amu_02.pt(n15));
                    atu2.iE(amu_02.ps(n15));
                    atu2.T(amu_02.gs(n15).length() == 0 ? dGR : amu_02.gs(n15));
                    atu2.gd(dGN);
                }
                for (n15 = dGM - dGL; n15 < dGK; ++n15) {
                    ((atu)list.get(n15)).clear();
                }
                azs_08.g("ladderGlickoRatingSearchButtonVisible", amu_02.VY());
                azs_08.a((aho_0)adn_1.aPi(), "listGlickoRating");
                if (!add_1.aOG().kR("ladderInformationDialog")) {
                    apN.aDK().a(ahg_1.aTk());
                }
                bl2 = false;
                break;
            }
        }
        return bl2;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public static int aMc() {
        return dnZ;
    }

    public static int UI() {
        return bhS;
    }

    public static int VV() {
        return blG;
    }

    public static int aRL() {
        return dGG;
    }

    public static int ahz() {
        return bRd;
    }

    public static int ahE() {
        return bRk;
    }

    public static int ahJ() {
        return bRr;
    }

    public static byte ahs() {
        return bQW;
    }

    public static byte aht() {
        return bQX;
    }

    public static short ahu() {
        return bQY;
    }

    public static int aCe() {
        return cIs;
    }

    public static int akP() {
        return bXy;
    }

    public static int aRM() {
        return dGJ;
    }

    public static int aRN() {
        return dGL;
    }

    public static int aRO() {
        return dGO;
    }

    public static int aRP() {
        return dGP;
    }

    public static void nQ(int n2) {
        dnZ = n2;
    }

    public static void nR(int n2) {
        bhS = n2;
    }

    public static void nS(int n2) {
        blG = n2;
    }

    public static void nT(int n2) {
        dGG = n2;
    }

    public static void nU(int n2) {
        bRd = n2;
    }

    public static void nV(int n2) {
        bRk = n2;
    }

    public static void nW(int n2) {
        bRr = n2;
    }

    public static void nX(int n2) {
        cIs = n2;
    }

    public static void nY(int n2) {
        dGJ = n2;
    }

    public static void nZ(int n2) {
        bXy = n2;
    }

    public static void oa(int n2) {
        dGL = n2;
    }

    public static void ob(int n2) {
        dGP = n2;
    }

    public static void oc(int n2) {
        dnZ += n2;
    }

    public static void od(int n2) {
        bhS += n2;
    }

    public static void oe(int n2) {
        blG += n2;
    }

    public static void of(int n2) {
        dGG += n2;
    }

    public static void og(int n2) {
        bRd += n2;
    }

    public static void oh(int n2) {
        bRk += n2;
    }

    public static void oi(int n2) {
        bRr += n2;
    }

    public static void oj(int n2) {
        if ((bQW = (byte)((bQW + n2) % 12)) < 0) {
            bQW = (byte)(bQW + 12);
        }
    }

    public static void ok(int n2) {
        if ((bQX = (byte)((bQX + n2) % 4)) < 0) {
            bQX = (byte)(bQX + 4);
        }
    }

    public static void ol(int n2) {
        int n3 = Calendar.getInstance().get(1);
        bQY = (short)Math.max(n3 - 1, Math.min(n3, bQY + n2));
    }

    public static void om(int n2) {
        cIs += n2;
    }

    public static void on(int n2) {
        bXy += n2;
    }

    public static void oo(int n2) {
        dGL += n2;
    }

    static {
        bQZ = 0;
        bRa = 0;
        bRb = 0;
        bRc = 0;
        bRd = 0;
        bRe = 0;
        bRf = Integer.MIN_VALUE;
        bRj = 0;
        bRk = 0;
        bRl = 0;
        bRm = Integer.MIN_VALUE;
        bRq = 0;
        bRr = 0;
        bRs = 0;
        bRt = Integer.MIN_VALUE;
        dGF = 0;
        dGG = 0;
        dGH = 0;
        dGI = Integer.MIN_VALUE;
        cIs = 0;
        bXy = 0;
        dGJ = 0;
        dGK = 0;
        dGL = 0;
        dGM = 0;
        dGN = Integer.MIN_VALUE;
        dGO = 0;
        dGP = 0;
        dGQ = "-";
        dGR = "-";
        Calendar calendar = Calendar.getInstance();
        bQW = (byte)calendar.get(2);
        bQX = (byte)(bQW / 3);
        bQY = (short)calendar.get(1);
    }
}

