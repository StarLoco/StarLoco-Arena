/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from zW
 */
public class zw_0
implements aho_0 {
    public static final String aog = "description";
    public static final String Oj = "value";
    private int aGJ;

    public zw_0(int n2) {
        this.aGJ = n2;
    }

    public Object getFieldValue(String string) {
        if (string.equals(aog)) {
            return aon_0.aYc().getString("month" + this.aGJ);
        }
        if (string.equals(Oj)) {
            return this.aGJ;
        }
        return null;
    }

    public int Hb() {
        return this.aGJ;
    }

    public String[] getFields() {
        return new String[0];
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

