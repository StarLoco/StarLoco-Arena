/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from ajK
 */
public class ajk_1
implements aho_0 {
    private final short fL;
    private final ArrayList cAQ = new ArrayList();
    private static final String Gs = "isSelected";
    private static final String NAME = "name";
    private static final String sU = "description";
    private static final String cAR = "subtypes";
    public static final String[] ce = new String[]{"isSelected", "name", "description", "subtypes"};

    public ajk_1(short s) {
        this.fL = s;
    }

    public short tI() {
        return this.fL;
    }

    public void b(li_2 li_22) {
        this.cAQ.add(li_22);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(Gs)) {
            if (azs_0.aLV().getProperty("selectedAchievementType") == null) {
                return false;
            }
            return this == azs_0.aLV().getProperty("selectedAchievementType").getValue();
        }
        if (string.equals(NAME)) {
            return aon_0.aYc().a(43, this.fL, new Object[0]);
        }
        if (string.equals(sU)) {
            return aon_0.aYc().a(44, this.fL, new Object[0]);
        }
        if (string.equals(cAR)) {
            return this.cAQ.toArray();
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

    public ArrayList azl() {
        return this.cAQ;
    }
}

