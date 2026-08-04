/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atk
 */
public class atk_0
implements aho_0 {
    public static final String NAME = "name";
    public static final String Gs = "isSelected";
    public static final String[] ce = new String[]{"name", "isSelected"};
    private int aW;
    private boolean cTK;

    public atk_0(int n2) {
        this.aW = n2;
    }

    public int getId() {
        return this.aW;
    }

    public boolean isSelected() {
        return this.cTK;
    }

    public void setSelected(boolean bl2) {
        this.cTK = bl2;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return aon_0.aYc().a(58, this.aW, new Object[0]);
        }
        if (string.equals(Gs)) {
            return this.cTK;
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

