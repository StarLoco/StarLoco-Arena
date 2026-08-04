/*
 * Decompiled with CFR 0.152.
 */
public class ec
implements aho_0 {
    public static final String nJ = "position";
    public static final String nK = "demonName";
    public static final String nL = "quarterlyReputationPoints";
    public static final String nM = "guildName";
    public static final String nN = "style";
    public static final String[] ce = new String[]{"position", "demonName", "quarterlyReputationPoints", "guildName", "style"};
    private short nO;
    private String nP;
    private long nQ;
    private String nR;

    public short ha() {
        return this.nO;
    }

    public String hb() {
        return this.nP;
    }

    public long hc() {
        return this.nQ;
    }

    public String hd() {
        return this.nR;
    }

    public void k(short s) {
        this.nO = s;
    }

    public void S(String string) {
        this.nP = string;
    }

    public void y(long l2) {
        this.nQ = l2;
    }

    public void T(String string) {
        this.nR = string;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(nJ)) {
            return this.nO;
        }
        if (string.equals(nK)) {
            return this.nP;
        }
        if (string.equals(nL)) {
            return this.nQ;
        }
        if (string.equals(nM)) {
            return this.nR;
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
        this.nP = "";
        this.nQ = 0L;
        this.nR = "";
    }
}

