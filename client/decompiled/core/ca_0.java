/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from CA
 */
public class ca_0
extends aJj
implements aho_0 {
    public static final String NAME = "name";
    public static final String aLL = "privileges";
    public static final String aLM = "canPromote";
    public static final String aLN = "canDepromote";
    public static final String nM = "guildName";
    public static final String aLO = "connected";
    public static final String arM = "rankIconUrl";
    public static final String[] ce = new String[]{"name", "privileges", "canPromote", "canDepromote", "guildName", "connected", "rankIconUrl"};
    private aen_1 aLP;
    private String m_name;
    private String nR;
    private long bZ;
    private long Pl;
    private boolean aLQ = false;
    private long aLR;
    private short aLS;
    private int aLT;
    private int aLU;
    private short aLV;
    public aea_0 aLW = new uy_2(this);
    public aea_0 aLX = new ut_2(this);
    public aea_0 aLY = new uu_2(this);

    public String[] getFields() {
        return ce;
    }

    public String getName() {
        return this.m_name + " (" + this.aLP.aRf() + ")";
    }

    public void setName(String string) {
        this.m_name = string;
    }

    public long Kd() {
        return this.bZ;
    }

    public String hd() {
        if (this.aLV != 0) {
            String string = "";
            try {
                string = afg_1.kn(this.aLV);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                string = "" + this.aLV;
            }
            return this.nR + " (" + string + ")";
        }
        return this.nR;
    }

    public void T(String string) {
        this.nR = string;
    }

    public long Ke() {
        return this.Pl;
    }

    public long Kf() {
        return this.aLR;
    }

    public void bv(long l2) {
        this.aLR = l2;
    }

    public boolean isConnected() {
        return this.aLQ;
    }

    public aen_1 Kg() {
        return this.aLP;
    }

    public void a(aen_1 aen_12) {
        this.aLP = aen_12;
    }

    public short Kh() {
        return this.aLS;
    }

    public int Ki() {
        return this.aLT;
    }

    public int Kj() {
        return this.aLU;
    }

    public short Kk() {
        return this.aLV;
    }

    public void au(short s) {
        this.aLV = s;
    }

    public aea_0[] Kl() {
        return new aea_0[]{this.aLW, this.aLX, this.aLY};
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return this.getName().toLowerCase();
        }
        if (string.equals(nM)) {
            return this.hd();
        }
        if (string.equals(aLL)) {
            return this.Kg().aRd();
        }
        if (string.equals(aLM)) {
            aez_0 aez_02 = (aez_0)azs_0.aLV().getProperty("guildCoachStats").getValue();
            return aez_02 != null && this.Kg().aRe() < aez_02.aPZ() && this.Kg().aRa();
        }
        if (string.equals(aLN)) {
            aez_0 aez_03 = (aez_0)azs_0.aLV().getProperty("guildCoachStats").getValue();
            return aez_03 != null && this.Kg().aRe() < aez_03.aPZ() && this.Kg().aRb();
        }
        if (string.equals(aLO)) {
            return this.isConnected();
        }
        if (string.equals(arM)) {
            try {
                return String.format(mu_1.rM().getString("guildRankIconsPath"), this.Kg().aRe());
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        return null;
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

    static /* synthetic */ long a(ca_0 ca_02, long l2) {
        ca_02.Pl = l2;
        return ca_02.Pl;
    }

    static /* synthetic */ aen_1 a(ca_0 ca_02, aen_1 aen_12) {
        ca_02.aLP = aen_12;
        return ca_02.aLP;
    }

    static /* synthetic */ String a(ca_0 ca_02, String string) {
        ca_02.m_name = string;
        return ca_02.m_name;
    }

    static /* synthetic */ boolean a(ca_0 ca_02, boolean bl2) {
        ca_02.aLQ = bl2;
        return ca_02.aLQ;
    }

    static /* synthetic */ String b(ca_0 ca_02, String string) {
        ca_02.nR = string;
        return ca_02.nR;
    }

    static /* synthetic */ long b(ca_0 ca_02, long l2) {
        ca_02.aLR = l2;
        return ca_02.aLR;
    }

    static /* synthetic */ short a(ca_0 ca_02, short s) {
        ca_02.aLS = s;
        return ca_02.aLS;
    }

    static /* synthetic */ int a(ca_0 ca_02, int n2) {
        ca_02.aLT = n2;
        return ca_02.aLT;
    }

    static /* synthetic */ int b(ca_0 ca_02, int n2) {
        ca_02.aLU = n2;
        return ca_02.aLU;
    }

    static /* synthetic */ short b(ca_0 ca_02, short s) {
        ca_02.aLV = s;
        return ca_02.aLV;
    }

    static /* synthetic */ long c(ca_0 ca_02, long l2) {
        ca_02.bZ = l2;
        return ca_02.bZ;
    }
}

