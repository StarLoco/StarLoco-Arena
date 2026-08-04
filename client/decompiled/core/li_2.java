/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from li
 */
public class li_2
implements aho_0 {
    private final short Gp;
    private final short Gq;
    private final ArrayList Gr = new ArrayList();
    private static final String Gs = "isSelected";
    private static final String NAME = "name";
    private static final String sU = "description";
    private static final String Gt = "achievements";
    public static final String[] ce = new String[]{"isSelected", "name", "description", "achievements"};

    public li_2(short s, short s2) {
        this.Gp = s;
        this.Gq = s2;
    }

    public short getType() {
        return this.Gp;
    }

    public short pV() {
        return this.Gq;
    }

    public void a(aau_1 aau_12) {
        aea_1 aea_12 = new aea_1(aau_12);
        this.Gr.add(aea_12);
    }

    public ArrayList pW() {
        return this.Gr;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(Gs)) {
            if (azs_0.aLV().getProperty("selectedAchievementSubtype") == null) {
                return false;
            }
            return this == azs_0.aLV().getProperty("selectedAchievementSubtype").getValue();
        }
        if (string.equals(NAME)) {
            return aon_0.aYc().a(45, this.Gq, new Object[0]);
        }
        if (string.equals(sU)) {
            return aon_0.aYc().a(46, this.Gq, new Object[0]);
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

