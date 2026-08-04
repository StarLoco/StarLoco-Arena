/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;

public class Te
extends axD
implements aho_0 {
    public static final String NAME = "name";
    public static final String aFW = "level";
    public static final String aFP = "fighters";
    public static final String aFT = "totalVictories";
    public static final String aFU = "totalDefeats";
    public static final String aFV = "consecutiveVictories";
    public static final String arM = "rankIconUrl";
    public static final String bMP = "coachSpellInventory";
    public static final String[] ce = new String[]{"name", "fighters", "totalVictories", "totalDefeats", "consecutiveVictories", "rankIconUrl", "coachSpellInventory"};
    protected ajO bMQ;
    private int bMG;
    private int bMR;
    private int bMS;
    private int bMT;
    private int bMU;

    public void L(byte[] byArray) {
        this.bMQ = new ajO(je_1.Wa(), 8);
        this.bMQ.b(byArray);
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        ajv_2 ajv_22;
        if (string.equals(NAME)) {
            return this.m_name;
        }
        if (string.equals(aFW)) {
            if (this.bMU != 0) {
                return aet_0.nJ(this.bMU) + " (" + this.bMU + ")";
            }
            return aet_0.nH(this.bMT) + " (" + this.bMT + ")";
        }
        if (string.equals(aFP)) {
            Iterator iterator = this.amp();
            ArrayList arrayList = new ArrayList();
            while (iterator.hasNext()) {
                arrayList.add(iterator.next());
            }
            return arrayList.toArray();
        }
        if (string.equals(aFT)) {
            return this.bMG;
        }
        if (string.equals(aFU)) {
            return this.bMR;
        }
        if (string.equals(aFV)) {
            return this.bMS;
        }
        if (string.equals(arM)) {
            try {
                if (this.bMU != 0) {
                    return String.format(mu_1.rM().getString("coachRankIconsPath"), aet_0.nM(this.bMU));
                }
                return String.format(mu_1.rM().getString("coachRankIconsPath"), aet_0.nL(this.bMT));
            }
            catch (Exception exception) {
                a.warn((Object)"", (Throwable)exception);
            }
        }
        if (string.equals(bMP) && this.afO() != null && (ajv_22 = this.afO().Oh()) != null) {
            ArrayList<yp_2> arrayList = new ArrayList<yp_2>();
            for (yp_2 yp_22 : ajv_22) {
                arrayList.add(yp_22);
            }
            return arrayList.toArray();
        }
        return null;
    }

    public String[] getFields() {
        return ce;
    }

    public boolean l(String string) {
        return false;
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
    }

    public ajO afO() {
        return this.bMQ;
    }

    public void hM(int n2) {
        this.bMG = n2;
    }

    public void hP(int n2) {
        this.bMR = n2;
    }

    public void hQ(int n2) {
        this.bMS = n2;
    }

    public void setStrength(int n2) {
        this.bMT = n2;
    }

    public void hR(int n2) {
        this.bMU = n2;
    }

    public int afP() {
        return this.bMR;
    }

    public int afB() {
        return this.bMG;
    }

    public int afQ() {
        return this.bMU;
    }
}

