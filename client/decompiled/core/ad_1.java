/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from AD
 */
public class ad_1
implements aho_0 {
    public static final byte aHa = 1;
    public static final byte aHb = 0;
    public static final byte aHc = -1;
    public static final String NAME = "name";
    public static final String aHd = "fightResult";
    private String m_name;
    private byte aHe;
    public static final String[] ce = new String[]{"name", "fightResult"};

    public ad_1(String string, byte by) {
        this.m_name = string;
        this.aHe = by;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return this.m_name;
        }
        if (string.equals(aHd)) {
            return this.aHe;
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

