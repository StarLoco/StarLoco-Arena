/*
 * Decompiled with CFR 0.152.
 */
public class awj
implements aho_0 {
    public static final String nJ = "position";
    public static final String nM = "guildName";
    public static final String nL = "quarterlyReputationPoints";
    public static final String dhz = "differentialQuarterlyReputationPoints";
    public static final String dhA = "monthlyReputationPoints";
    public static final String nN = "style";
    public static final String[] ce = new String[]{"position", "guildName", "quarterlyReputationPoints", "differentialQuarterlyReputationPoints", "monthlyReputationPoints", "style"};
    private short nO;
    private String nR;
    private long nQ;
    private long dhB;
    private long dhC;

    public short ha() {
        return this.nO;
    }

    public String hd() {
        return this.nR;
    }

    public long hc() {
        return this.nQ;
    }

    public String aJv() {
        long l2 = this.dhB - this.nQ;
        return l2 < 0L ? "" + l2 : "+" + l2;
    }

    public long aJw() {
        return this.dhC;
    }

    public void k(short s) {
        this.nO = s;
    }

    public void T(String string) {
        this.nR = string;
    }

    public void y(long l2) {
        this.nQ = l2;
    }

    public void dZ(long l2) {
        this.dhB = l2;
    }

    public void ea(long l2) {
        this.dhC = l2;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(nJ)) {
            return this.nO;
        }
        if (string.equals(nM)) {
            return this.nR;
        }
        if (string.equals(nL)) {
            return this.nQ;
        }
        if (string.equals(dhz)) {
            return this.aJv();
        }
        if (string.equals(dhA)) {
            return this.dhC;
        }
        if (string.equals(nN)) {
            if (this.nO == 1) {
                return "LadderFirst";
            }
            if (this.nO == 2) {
                return "LadderSecond";
            }
            if (this.nO == 3) {
                return "LadderThird";
            }
            if (this.nO % 2 == 1) {
                return "LadderOdd";
            }
            return "";
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

    public void clear() {
        this.nO = 0;
        this.nR = "";
        this.nQ = 0L;
        this.dhB = 0L;
        this.dhC = 0L;
    }
}

