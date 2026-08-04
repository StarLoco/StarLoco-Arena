/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Sd
 */
public class sd_1
implements aho_0 {
    public static final String NAME = "name";
    public static final String sV = "value";
    public static final String bLc = "fightRules";
    private final ArrayList bLd = new ArrayList();
    private String m_name;
    public static final String[] ce = new String[]{"name", "value", "fightRules"};

    public sd_1(String string, ArrayList arrayList) {
        this.m_name = string;
        this.bLd.addAll(arrayList);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return this.m_name;
        }
        if (!string.equals(sV) && string.equals(bLc)) {
            return this.bLd.toArray();
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

    public ArrayList aeI() {
        ArrayList arrayList = new ArrayList();
        for (int j = 0; j < this.bLd.size(); ++j) {
            if (!jk_1.mf().me().contains(this.bLd.get(j))) continue;
            arrayList.add(this.bLd.get(j));
        }
        return arrayList;
    }

    public String getName() {
        return this.m_name;
    }
}

