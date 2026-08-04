/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afZ
 */
public class afz_0
implements aho_0 {
    private static final String Gs = "isSelected";
    private static final String NAME = "name";
    private static final String aSY = "xp";
    private static final String ctj = "xpLimit";
    private static final String sU = "description";
    private static final String ctk = "helpDescription";
    private static final String ctl = "illustrationUrl";
    private static final String ctm = "achievementLinkedCompleted";
    public static final String[] ce = new String[]{"isSelected", "name", "xp", "xpLimit", "description", "illustrationUrl", "achievementLinkedCompleted"};
    private int aW;
    private String m_name;
    private String fM;
    private int bby;
    private boolean ctn;
    private short bbD;
    private int bbG;
    private int bbH;
    private int bbI;

    public afz_0(int n2, String string, String string2, int n3, boolean bl2, short s, int n4, int n5, int n6) {
        this.aW = n2;
        this.m_name = string;
        this.fM = string2;
        this.bby = n3;
        this.ctn = bl2;
        this.bbD = s;
        this.bbG = n4;
        this.bbH = n5;
        this.bbI = n6;
    }

    public int getId() {
        return this.aW;
    }

    public String getName() {
        return this.m_name;
    }

    public String getDescription() {
        return this.fM;
    }

    public int Qu() {
        return this.bby;
    }

    public boolean QA() {
        return this.ctn;
    }

    public short QB() {
        return this.bbD;
    }

    public int QC() {
        return this.bbG;
    }

    public int QD() {
        return this.bbH;
    }

    public String[] getFields() {
        return ce;
    }

    public int QE() {
        return this.bbI;
    }

    public Object getFieldValue(String string) {
        if (string.equals(Gs)) {
            return this == azs_0.aLV().getProperty("selectedChallenge").getValue();
        }
        if (string.equals(NAME)) {
            return this.m_name;
        }
        if (string.equals(sU)) {
            return this.fM;
        }
        if (string.equals(ctk)) {
            return aon_0.aYc().getString("challengeHelpDescription" + this.getId());
        }
        if (string.equals(ctl)) {
            try {
                return String.format(mu_1.rM().getString("challengeIllustrationsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(ctm)) {
            if (this.bbD != 0) {
                sj_1 sj_12 = apN.aDK().Ln();
                return sj_12.c(avq_0.ce(this.bbD));
            }
            return true;
        }
        if (string.equals(aSY)) {
            return this.QC();
        }
        if (string.equals(ctj)) {
            return this.QD();
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
}

