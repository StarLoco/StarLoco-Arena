/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.dofusarena.common.game.statistics.PlayerStatisticsReport;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from aEz
 */
public class aez_0
extends amg_1
implements cl_1,
hy_0,
adq_2,
afJ,
aho_0,
aox_2 {
    protected static Logger a = Logger.getLogger(aez_0.class);
    public static final String dzV = "coach";
    public static final String xX = "name";
    public static final String aRT = "sex";
    public static final String dzW = "hairIndex";
    public static final String aRV = "hairColor";
    public static final String aRW = "skinColor";
    public static final String aRY = "actorDescriptorLibrary";
    public static final String aRZ = "actorLinkage";
    public static final String dzX = "actorAnimation";
    public static final String dzY = "actorDirection";
    public static final String dzZ = "strenght";
    public static final String dAa = "standing";
    public static final String dAb = "previousStrenght";
    public static final String dAc = "strenghtDescription";
    public static final String dAd = "level";
    public static final String dAe = "teamLevel";
    public static final String dAf = "previousLevel";
    public static final String dAg = "levelEvolution";
    public static final String dAh = "rank";
    public static final String dAi = "rankEvolution";
    public static final String dAj = "previousRank";
    public static final String dAk = "rankIconUrl";
    public static final String dAl = "teamRankIconUrl";
    public static final String dAm = "tournamentToken";
    public static final String aly = "guildInfos";
    public static final String dAn = "guildRankIconUrl";
    public static final String dAo = "betCards";
    public static final String aSp = "coachSpells";
    public static final String dAp = "statisticsTotalFights";
    public static final String dAq = "statisticsTotalFightsWon";
    public static final String dAr = "statisticsTotalFightsLost";
    public static final String dAs = "statisticsTotalFightsEvolution";
    public static final String dAt = "statisticsTotalFightsEvolutionWon";
    public static final String dAu = "statisticsTotalFightsEvolutionLost";
    public static final String dAv = "statisticsConsecutiveWins";
    public static final String dAw = "statisticsConsecutiveEvolutionWins";
    public static final String dAx = "statisticsConsecutiveLosses";
    public static final String dAy = "statisticsTotalFightsTime";
    public static final String dAz = "statisticsTotalPlayTime";
    public static final String dAA = "statisticsTeamConsecutiveVictories";
    public static final String dAB = "statisticsTournamentPoints";
    public static final String[] ce = new String[]{"name", "sex", "hairIndex", "hairColor", "skinColor", "actorDescriptorLibrary", "actorLinkage", "strenght", "standing", "previousStrenght", "strenghtDescription", "level", "teamLevel", "previousLevel", "levelEvolution", "rank", "previousRank", "rankEvolution", "rankIconUrl", "teamRankIconUrl", "guildInfos", "betCards", "coachSpells", "statisticsTotalFights", "statisticsTotalFightsWon", "statisticsTotalFightsLost", "statisticsConsecutiveWins", "statisticsConsecutiveLosses", "statisticsTotalFightsTime", "statisticsTotalPlayTime", "statisticsTeamConsecutiveVictories", "statisticsTournamentPoints"};
    public static final int dAC = 0;
    public static final int dAD = 1;
    public static final int dAE = 2;
    public static final int dAF = 4;
    public static final int dAG = 8;
    public static final int dAH = 32;
    public static final int dAI = 64;
    public static final int dAJ = 128;
    public static final int dAK = 256;
    public static final int dAL = 512;
    public static final int dAM = 1024;
    public static final int dAN = 2048;
    private String m_name = null;
    private byte zv = 0;
    private byte dAO;
    private byte dAP;
    private short UF;
    private qc_0 dAQ = qc_0.bEK;
    private String dAR = mT.a(qc_0.bEK.getIndex(), "AnimStatique", null);
    private final ky_2 dAS;
    private ajO bMQ;
    private nk dAT;
    private final HashMap dAU;
    private byte dAV;
    private axw dAW;
    private final axu dAX;
    private final axu dAY;
    private PlayerStatisticsReport dAZ = null;
    private int dBa = 0;
    private ca_0 dBb = null;
    private byte dBc = 0;
    private aGz Ir = new aGz();
    private asc dBd = new asc();
    private int dBe = 0;
    private int dBf = 0;
    private boolean dBg = false;
    private int dBh = 1;
    private int anV = 0;
    protected aim_1 Lp = new aim_1();
    private int bMU;
    private int dBi = 0;
    private static final int dBj = 1;
    private static final int dBk = 1;
    private static final int dBl = 2;
    private static final int dBm = 3;
    private ArrayList dBn = new ArrayList();

    public aez_0() {
        super(-1L);
        this.dAS = new ky_2(aoi_0.aXY());
        this.dAS.a(this);
        this.dAU = new HashMap();
        this.dAX = new axu();
        this.dAY = new axu();
        this.b(qc_0.bEK);
    }

    public long Lc() {
        return 0L;
    }

    public String getName() {
        return this.m_name;
    }

    public void setName(String string) {
        try {
            this.m_name = new String(string.getBytes("UTF-8"));
        }
        catch (Exception exception) {
            a.error((Object)("Format UTF-8 inapplicable sur le nom \"" + string + "\"."));
            this.m_name = string;
        }
    }

    public ca_0 aPY() {
        return this.dBb;
    }

    public void a(ca_0 ca_02) {
        this.dBb = ca_02;
        azs_0.aLV().a((aho_0)this, aly);
    }

    public byte aPZ() {
        return this.dBc;
    }

    public void bf(byte by) {
        this.dBc = by;
    }

    public aGz qI() {
        return this.Ir;
    }

    public void b(aGz aGz2) {
        this.Ir = aGz2;
    }

    public boolean c(aau_1 aau_12) {
        return aau_12.a(this.Ir, this.dBd);
    }

    public int aQa() {
        return this.dBe;
    }

    public void ns(int n2) {
        this.dBe = n2;
    }

    public int aQb() {
        return this.dBf;
    }

    public void nt(int n2) {
        this.dBf = n2;
    }

    public void nu(int n2) {
        this.anV = n2;
    }

    public aim_1 rs() {
        return this.Lp;
    }

    public int afQ() {
        return this.bMU;
    }

    public int aQc() {
        return this.dBi;
    }

    public void hR(int n2) {
        this.bMU = n2;
    }

    public byte lZ() {
        return this.zv;
    }

    public void S(byte by) {
        this.zv = by;
        this.iG(String.format("700%d", by));
    }

    public void bg(byte by) {
        this.dAO = by;
        agl_0 agl_02 = agl_0.oA(this.dAO);
        float[] fArray = agl_02 != null ? agl_02.Aa() : null;
        this.b(2, fArray);
    }

    public byte aQd() {
        return this.dAO;
    }

    public void bh(byte by) {
        this.dAP = by;
        apH apH2 = apH.lL(this.dAP);
        float[] fArray = apH2 != null ? apH2.Aa() : null;
        this.b(1, fArray);
    }

    public byte aQe() {
        return this.dAP;
    }

    public short tw() {
        return this.UF;
    }

    public void cm(short s) {
        this.UF = s;
    }

    public void kX(String string) {
        this.dAR = string;
    }

    public String aQf() {
        return this.dAR;
    }

    public qc_0 aQg() {
        return this.dAQ;
    }

    public int getLevel() {
        if (DofusArenaClientInstance.yl().aod().d(adc_0.clY) == 0) {
            return this.aQh();
        }
        return this.aQi();
    }

    public int aQh() {
        return aet_0.nH(this.bi((byte)1));
    }

    public int aQi() {
        return aet_0.nJ(this.afQ());
    }

    public int aQj() {
        return aet_0.nH(this.bj((byte)1));
    }

    public short aQk() {
        return aet_0.nN(this.getLevel());
    }

    public short aQl() {
        return aet_0.nN(this.aQj());
    }

    public asc aQm() {
        return this.dBd;
    }

    public void a(arh_0 arh_02, boolean bl2, boolean bl3) {
        super.a(arh_02, bl2, bl3);
        int n2 = this.dBn.size();
        for (int j = 0; j < n2; ++j) {
            ((adw_2)this.dBn.get(j)).aPQ().aVG().a(this, arh_02);
        }
    }

    public ky_2 aQn() {
        return this.dAS;
    }

    public ajO afO() {
        return this.bMQ;
    }

    public void a(ajO ajO2) {
        this.bMQ = ajO2;
    }

    public void L(byte[] byArray) {
        this.bMQ = new ajO(je_1.Wa(), 8);
        this.bMQ.b(byArray);
    }

    public void aQo() {
        this.S((byte)(Math.random() * 2.0));
        this.bh((byte)(Math.random() * (double)agl_0.values().length));
        this.bg((byte)(Math.random() * (double)apH.values().length));
    }

    public void c(wy_2 wy_22, short s) {
        try {
            afs_0 afs_02 = afs_0.bE(s);
            if (afs_02 == null) {
                return;
            }
            if (((xj)wy_22.NR()).tj() == aMK.dYn) {
                this.aQv();
            } else {
                int n2 = Math.abs(wy_22.jf());
                String[] stringArray = afs_02.ES();
                String string = mu_1.rM().getString("coachANMEquipmentPath");
                string = String.format(string, n2);
                this.a(s, string, stringArray);
            }
        }
        catch (Exception exception) {
            a.error((Object)("Erreur au chargement de l'\u00e9quipment : " + wy_22.jf()), (Throwable)exception);
        }
    }

    public void d(wy_2 wy_22, short s) {
        afs_0 afs_02 = this.bE(s);
        if (afs_02 == null) {
            return;
        }
        if (((xj)wy_22.NR()).tj() == aMK.dYn) {
            this.aQw();
        } else {
            this.bN(s);
        }
    }

    protected afs_0 bE(short s) {
        return afs_0.bE(s);
    }

    public void D(byte[] byArray) {
    }

    public byte[] Lh() {
        return ug_2.EMPTY_BYTE_ARRAY;
    }

    public int aQp() {
        return 0;
    }

    public boolean b(ByteBuffer byteBuffer, int n2) {
        try {
            this.V(byteBuffer);
            if ((n2 & 1) == 1) {
                this.U(byteBuffer);
            }
            this.T(byteBuffer);
            if ((n2 & 0x100) == 256) {
                this.anV = byteBuffer.getInt();
                this.Q(byteBuffer);
            }
            if ((n2 & 0x400) == 1024) {
                this.bMU = byteBuffer.getInt();
            }
            if ((n2 & 0x40) == 64) {
                boolean bl2 = this.dBg = byteBuffer.get() == 1;
            }
            if ((n2 & 0x20) == 32) {
                this.W(byteBuffer);
            }
            if ((n2 & 0x80) == 128) {
                this.P(byteBuffer);
            }
            if ((n2 & 2) == 2) {
                this.S(byteBuffer);
            }
            if ((n2 & 4) == 4) {
                this.R(byteBuffer);
            }
            if ((n2 & 0x200) == 512) {
                this.O(byteBuffer);
            }
            if ((n2 & 8) == 8) {
                this.X(byteBuffer);
            }
            if ((n2 & 0x800) == 2048) {
                this.Y(byteBuffer);
            }
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            a.error((Object)"pas assez de donn\u00e9es pour completer la cr\u00e9ation d'un Coach");
            return false;
        }
        return true;
    }

    protected void O(ByteBuffer byteBuffer) {
        short s = byteBuffer.getShort();
        int n2 = 0;
        while (n2 * 4 < s) {
            this.Ir.A(byteBuffer.getShort(), byteBuffer.getShort());
            ++n2;
        }
    }

    protected void P(ByteBuffer byteBuffer) {
        short s = byteBuffer.getShort();
        byte[] byArray = new byte[s];
        byteBuffer.get(byArray);
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(byArray);
        while (byteBuffer2.hasRemaining()) {
            this.dBd.d(byteBuffer2.getInt(), (byte)1);
        }
    }

    protected void Q(ByteBuffer byteBuffer) {
        short s = byteBuffer.getShort();
        byte[] byArray = new byte[s];
        byteBuffer.get(byArray);
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(byArray);
        while (byteBuffer2.hasRemaining()) {
            this.Lp.c(byteBuffer2.get(), byteBuffer2.getInt());
        }
    }

    protected void R(ByteBuffer byteBuffer) {
        short s = byteBuffer.getShort();
        byte[] byArray = new byte[s];
        byteBuffer.get(byArray);
        this.dAS.l(byArray);
    }

    protected void S(ByteBuffer byteBuffer) {
        short s = byteBuffer.getShort();
        byte[] byArray = new byte[s];
        byteBuffer.get(byArray);
        this.dAS.k(byArray);
    }

    protected void T(ByteBuffer byteBuffer) {
        byte by = byteBuffer.get();
        byte by2 = byteBuffer.get();
        byte by3 = byteBuffer.get();
        short s = byteBuffer.getShort();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        if (gregorianCalendar.get(2) == 3 && gregorianCalendar.get(5) == 1) {
            by3 = (byte)((by3 + 1) % 2);
        }
        this.S(by3);
        this.bh(by);
        this.bg(by2);
        this.cm(s);
    }

    protected void U(ByteBuffer byteBuffer) {
        int n2 = byteBuffer.getInt();
        int n3 = byteBuffer.getInt();
        short s = byteBuffer.getShort();
        this.a(n2, (double)n3, (double)s);
        this.b(qc_0.hf(byteBuffer.get()));
    }

    protected void V(ByteBuffer byteBuffer) {
        this.c(byteBuffer.getLong());
        byte[] byArray = new byte[byteBuffer.get()];
        byteBuffer.get(byArray);
        this.setName(new String(byArray));
    }

    protected void W(ByteBuffer byteBuffer) {
        byte[] byArray = new byte[byteBuffer.getShort()];
        if (byArray.length > 0) {
            byteBuffer.get(byArray);
            if (this.dBb == null) {
                this.dBb = new ca_0();
            }
            this.dBb.ad(byArray);
        }
    }

    protected void X(ByteBuffer byteBuffer) {
        int n2 = byteBuffer.get();
        for (int j = 0; j < n2; ++j) {
            byte by = byteBuffer.get();
            short s = byteBuffer.getShort();
            this.dAY.b(by, s);
            this.dAX.b(by, s);
        }
    }

    protected void Y(ByteBuffer byteBuffer) {
        this.dBi = byteBuffer.getInt();
    }

    public amg_1 Ov() {
        return this;
    }

    public axw Ou() {
        return this.dAW;
    }

    public boolean r(ee_2 ee_22) {
        if (!this.dAU.containsKey(ee_22.getId())) {
            this.dAU.put(ee_22.getId(), ee_22);
            if (ee_22.LQ() != null && ee_22.LQ() != this) {
                ee_22.LQ().b(ee_22);
            }
            ee_22.a(this);
            return true;
        }
        return false;
    }

    public boolean s(ee_2 ee_22) {
        if (this.dAU.containsKey(ee_22.getId())) {
            this.dAU.remove(ee_22.getId());
            ee_22.a((cl_1)null);
            return true;
        }
        return false;
    }

    public Collection Lg() {
        return this.dAU.values();
    }

    public ee_2 er(long l2) {
        return (ee_2)this.dAU.get(l2);
    }

    public void d(byte by, short s) {
        short s2 = this.bi(by);
        this.dAY.b(by, s);
        if (s2 > 0) {
            this.dAX.b(by, s2);
            this.dBa = aet_0.nH(s) - aet_0.nH(s2);
        }
    }

    public short bi(byte by) {
        if (this.dAY.K(by)) {
            return this.dAY.ba(by);
        }
        return 0;
    }

    public int aQq() {
        return this.dBa;
    }

    public void nv(int n2) {
        this.dBa = n2;
    }

    public boolean aQr() {
        return this.dBg;
    }

    public void eM(boolean bl2) {
        this.dBg = bl2;
    }

    public short bj(byte by) {
        if (this.dAX.K(by)) {
            return this.dAX.ba(by);
        }
        return 0;
    }

    public PlayerStatisticsReport aQs() {
        return this.dAZ;
    }

    public void a(PlayerStatisticsReport playerStatisticsReport) {
        if (playerStatisticsReport != null) {
            this.dAZ = playerStatisticsReport;
            this.d((byte)1, (short)playerStatisticsReport.dN());
        } else {
            a.error((Object)("statisticsReport null pour le client id : " + this.getId()));
        }
    }

    public int Lf() {
        return this.dAU.size();
    }

    public long Lb() {
        return this.getId();
    }

    public String Ld() {
        return this.getName();
    }

    public byte Le() {
        return this.dAV;
    }

    public void O(byte by) {
        this.dAV = by;
    }

    public void c(nk nk2) {
        this.dAT = nk2;
    }

    public nk aQt() {
        return this.dAT;
    }

    private int aQu() {
        int n2 = this.aQc();
        int n3 = !aet_0.nC(n2) ? 1 : (aet_0.nD(n2) ? 3 : (aet_0.nE(n2) ? 2 : (aet_0.nF(n2) ? 1 : 1)));
        return n3;
    }

    public void aQv() {
        int n2 = this.dBn.size();
        for (int j = 0; j < n2; ++j) {
            ((adw_2)this.dBn.get(j)).reset();
        }
        this.aQw();
        wy_2 wy_22 = (wy_2)this.dAS.z(aMK.dYn.aXg()[0]);
        if (wy_22 != null) {
            this.dBn.clear();
            int n3 = this.aQu();
            for (n2 = 0; n2 < n3; ++n2) {
                this.dBn.add(new adw_2());
                ((adw_2)this.dBn.get(n2)).a(this, Integer.toString(((xj)wy_22.NR()).tB()));
            }
        }
    }

    public void aQw() {
        int n2 = this.dBn.size();
        for (int j = 0; j < n2; ++j) {
            ((adw_2)this.dBn.get(j)).cleanUp();
        }
        this.dBn.clear();
    }

    public Object fb(int n2) {
        return 0;
    }

    public boolean ats() {
        return this.dAT == null;
    }

    public void b(String string, Object object) {
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        Object object;
        if (string.equals(dzV)) {
            return this;
        }
        if (string.equals(xX)) {
            return this.Ld();
        }
        if (string.equals(aRT)) {
            return this.lZ();
        }
        if (string.equals(aRY)) {
            return this;
        }
        if (string.equals(dzX)) {
            return this.AU();
        }
        if (string.equals(dzY)) {
            return this.L().getIndex();
        }
        if (string.equals(aRV)) {
            return this.aQd();
        }
        if (string.equals(aRW)) {
            return this.aQe();
        }
        if (string.equals(aRZ)) {
            return this.aQf();
        }
        if (string.equals(dzZ)) {
            return this.bi((byte)1);
        }
        if (string.equals(dAa)) {
            return this.afQ();
        }
        if (string.equals(dAb)) {
            return this.bj((byte)1);
        }
        if (string.equals(dAc)) {
            short s = (short)Math.max(1000, this.bi((byte)1));
            short s2 = (short)Math.max(1000, this.bj((byte)1));
            int n2 = s - s2;
            String string2 = aon_0.aYc().getString("strength") + " : " + s + " (" + (n2 >= 0 ? "+" : "") + n2 + ")";
            return string2;
        }
        if (string.equals(dAd)) {
            return Math.max(1, this.getLevel());
        }
        if (string.equals(dAe)) {
            return Math.max(1, aet_0.nH(this.aQb()));
        }
        if (string.equals(dAf)) {
            return Math.max(1, this.getLevel());
        }
        if (string.equals(dAg)) {
            return this.dBa;
        }
        if (string.equals(dAh)) {
            return this.aQk();
        }
        if (string.equals(dAj)) {
            return this.aQl();
        }
        if (string.equals(dAi)) {
            return this.aQk() - this.aQl();
        }
        if (string.equals(dAk)) {
            try {
                return String.format(mu_1.rM().getString("coachRankIconsPath"), this.aQk());
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        if (string.equals(dAl)) {
            try {
                int n3 = aet_0.nH(this.aQb());
                return String.format(mu_1.rM().getString("coachRankIconsPath"), aet_0.nN(n3));
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        if (string.equals(aly)) {
            return this.aPY();
        }
        if (string.equals(dAn)) {
            try {
                object = this.aPY();
                if (object != null) {
                    return String.format(mu_1.rM().getString("guildRankIconsPath"), ((ca_0)object).Kg().aRe());
                }
                if (this.dBc != 0) {
                    return String.format(mu_1.rM().getString("guildRankIconsPath"), this.dBc);
                }
                return null;
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        if (string.equals(aSp) && this.afO() != null && (object = this.afO().Oh()) != null) {
            ArrayList<yp_2> arrayList = new ArrayList<yp_2>();
            Iterator iterator = ((ajv_2)object).iterator();
            while (iterator.hasNext()) {
                yp_2 yp_22 = (yp_2)iterator.next();
                arrayList.add(yp_22);
            }
            return arrayList.toArray();
        }
        if (string.equals(dAm)) {
            return null;
        }
        if (this.aQs() == null) {
            this.dAZ = new PlayerStatisticsReport();
        }
        if (string.equals(dAp)) {
            return this.aQs().dI();
        }
        if (string.equals(dAq)) {
            return this.aQs().dJ();
        }
        if (string.equals(dAr)) {
            return this.aQs().dK();
        }
        if (string.equals(dAs)) {
            return this.qI().cp(or_0.YP.tI());
        }
        if (string.equals(dAt)) {
            return this.qI().cp(or_0.Yk.tI());
        }
        if (string.equals(dAu)) {
            return this.qI().cp(or_0.Yl.tI());
        }
        if (string.equals(dAv)) {
            return this.aQs().dP();
        }
        if (string.equals(dAw)) {
            return this.qI().cp(or_0.YM.tI());
        }
        if (string.equals(dAx)) {
            return this.aQs().dO();
        }
        if (string.equals(dAy)) {
            return aon_0.aYc().eS(this.aQs().dL() * 1000L);
        }
        if (string.equals(dAz)) {
            return aon_0.aYc().eS(this.aQs().dM() * 1000L);
        }
        if (string.equals(dAA)) {
            return this.dBe;
        }
        if (string.equals(dAB)) {
            return this.anV;
        }
        return null;
    }

    public boolean l(String string) {
        return false;
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
    }

    public void be(boolean bl2) {
    }

    public void a(wl_1 wl_12) {
        switch (wl_12.Dh()) {
            case cqa: 
            case cqb: {
                auA auA2 = (auA)wl_12;
                this.c((wy_2)auA2.aHD(), auA2.ha());
                break;
            }
            case cqc: 
            case cqd: {
                auA auA3 = (auA)wl_12;
                this.d((wy_2)auA3.aHD(), auA3.ha());
                break;
            }
            case cqh: {
                this.aBx();
            }
        }
    }

    public void b(ag_2 ag_22) {
    }

    public void f(int n2, Object object) {
        switch (n2) {
            case 300: {
                this.dAW = (axw)object;
                break;
            }
            case 301: {
                this.dAW = null;
            }
        }
    }

    public int eN(boolean bl2) {
        if (this.dBh == 2) {
            if (!bl2) {
                this.dBh = 6;
            }
            return 2;
        }
        return this.dBh;
    }

    public void nw(int n2) {
        this.dBh = n2;
    }

    public void l(short s, short s2) {
    }

    public void m(short s, short s2) {
    }

    public void n(short s, short s2) {
    }

    public void j() {
    }

    public void b() {
    }

    public void release() {
    }

    public String toString() {
        return this.Ld();
    }
}

