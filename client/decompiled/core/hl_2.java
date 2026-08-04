/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Hl
 */
public class hl_2
implements aho_0 {
    private static final Logger a = Logger.getLogger(hl_2.class);
    public static final String nJ = "position";
    public static final String bdx = "reputation";
    public static final String bdy = "creatorCoachName";
    public static final String bdz = "teamName";
    public static final String aFT = "totalVictories";
    public static final String aFU = "totalDefeats";
    public static final String nM = "guildName";
    public static final String nK = "demonName";
    public static final String nN = "style";
    public static final String[] ce = new String[]{"position", "reputation", "creatorCoachName", "teamName", "totalVictories", "totalDefeats", "guildName", "demonName", "style"};
    private short nO;
    private int bdA;
    private String bdB;
    private String pX;
    private short bdC;
    private short bdD;
    private String nR;
    private String nP;
    private int bdE;

    public short ha() {
        return this.nO;
    }

    public int SE() {
        return this.bdA;
    }

    public String SF() {
        return this.bdB;
    }

    public String hX() {
        return this.pX;
    }

    public short SG() {
        return this.bdC;
    }

    public short SH() {
        return this.bdD;
    }

    public String hd() {
        return this.nR;
    }

    public String hb() {
        return this.nP;
    }

    public void k(short s) {
        this.nO = s;
    }

    public void gc(int n2) {
        this.bdA = n2;
    }

    public void ep(String string) {
        this.bdB = string;
    }

    public void ae(String string) {
        this.pX = string;
    }

    public void aA(short s) {
        this.bdC = s;
    }

    public void aB(short s) {
        this.bdD = s;
    }

    public void T(String string) {
        this.nR = string;
    }

    public void S(String string) {
        this.nP = string;
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
        if (string.equals(bdx)) {
            return this.bdA;
        }
        if (string.equals(bdy)) {
            return this.bdB;
        }
        if (string.equals(bdz)) {
            return this.pX;
        }
        if (string.equals(aFT)) {
            return this.bdC;
        }
        if (string.equals(aFU)) {
            return this.bdD;
        }
        if (string.equals(nM)) {
            return this.nR;
        }
        if (string.equals(nK)) {
            return this.nP;
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
        this.bdA = 0;
        this.bdB = "";
        this.pX = "";
        this.bdC = 0;
        this.bdD = 0;
        this.nR = "";
        this.nP = "";
        this.bdE = 0;
    }
}

