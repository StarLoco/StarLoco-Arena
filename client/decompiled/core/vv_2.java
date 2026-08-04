/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from VV
 */
public class vv_2
implements aho_0 {
    private static final Logger a = Logger.getLogger(vv_2.class);
    public static final String nJ = "position";
    public static final String aFW = "level";
    public static final String arM = "rankIconUrl";
    public static final String bTE = "rankName";
    public static final String bTF = "coachName";
    public static final String nM = "guildName";
    public static final String bdz = "teamName";
    public static final String aFV = "consecutiveVictories";
    public static final String aFT = "totalVictories";
    public static final String aFU = "totalDefeats";
    public static final String nN = "style";
    public static final String[] ce = new String[]{"position", "level", "rankIconUrl", "coachName", "guildName", "teamName", "consecutiveVictories", "totalVictories", "totalDefeats", "style"};
    private static final String[] bTG = new String[0];
    private short nO;
    private short bMF;
    private String[] bOq;
    private String nR;
    private String pX;
    private short bTH;
    private short bdC;
    private short bdD;
    private jg_0 bTI = new jg_0();

    public short ha() {
        return this.nO;
    }

    public short afA() {
        return this.bMF;
    }

    public String[] agb() {
        return this.bOq;
    }

    public String hd() {
        return this.nR;
    }

    public String hX() {
        return this.pX;
    }

    public short aiX() {
        return this.bTH;
    }

    public short SG() {
        return this.bdC;
    }

    public short SH() {
        return this.bdD;
    }

    public void k(short s) {
        this.nO = s;
    }

    public void bj(short s) {
        this.bMF = s;
    }

    public void q(String ... stringArray) {
        this.bOq = stringArray;
    }

    public void T(String string) {
        this.nR = string;
    }

    public void ae(String string) {
        this.pX = string;
    }

    public void bo(short s) {
        this.bTH = s;
    }

    public void aA(short s) {
        this.bdC = s;
    }

    public void aB(short s) {
        this.bdD = s;
    }

    public void z(int ... nArray) {
        this.bTI.clear();
        for (int j = nArray.length - 1; 0 <= j; --j) {
            this.bTI.add(nArray[j]);
        }
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(nJ)) {
            return this.nO;
        }
        if (string.equals(aFW)) {
            return aet_0.nH(this.bMF) + "(" + this.bMF + ")";
        }
        if (string.equals(arM)) {
            try {
                return String.format(mu_1.rM().getString("coachRankIconsPath"), aet_0.nL(this.bMF));
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        if (string.equals(bTE)) {
            return aon_0.aYc().getString("coach.rankName" + aet_0.nL(this.bMF));
        }
        if (string.equals(bTF)) {
            return this.bOq;
        }
        if (string.equals(nM)) {
            return this.nR;
        }
        if (string.equals(bdz)) {
            return this.pX;
        }
        if (string.equals(aFV)) {
            return this.bTH;
        }
        if (string.equals(aFT)) {
            return this.bdC;
        }
        if (string.equals(aFU)) {
            return this.bdD;
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
            if (this.bTI.contains(this.nO - 1)) {
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
        this.bMF = 0;
        this.bOq = bTG;
        this.nR = "";
        this.pX = "";
        this.bTH = 0;
        this.bdC = 0;
        this.bdD = 0;
        this.bTI.clear();
    }
}

