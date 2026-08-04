/*
 * Decompiled with CFR 0.152.
 */
public class atu
implements aho_0 {
    public static final String nJ = "position";
    public static final String cTS = "rating";
    public static final String bTF = "coachName";
    public static final String nM = "guildName";
    public static final String nN = "style";
    public static final String[] ce = new String[]{"position", "rating", "coachName", "guildName", "style"};
    private short nO;
    private short cTT;
    private String aiK;
    private String nR;
    private int bdE;

    public short ha() {
        return this.nO;
    }

    public short aGy() {
        return this.cTT;
    }

    public String xW() {
        return this.aiK;
    }

    public String hd() {
        return this.nR;
    }

    public void k(short s) {
        this.nO = s;
    }

    public void bU(short s) {
        this.cTT = s;
    }

    public void iE(String string) {
        this.aiK = string;
    }

    public void T(String string) {
        this.nR = string;
    }

    public void gd(int n2) {
        this.bdE = n2;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(nJ)) {
            return this.nO;
        }
        if (string.equals(cTS)) {
            return this.cTT;
        }
        if (string.equals(bTF)) {
            return this.aiK;
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
            if (this.nO - 1 == this.bdE) {
                return "LadderLocalCoach";
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
        this.cTT = 0;
        this.aiK = "";
        this.nR = "";
        this.bdE = 0;
    }
}

