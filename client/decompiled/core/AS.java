/*
 * Decompiled with CFR 0.152.
 */
public class AS
implements aho_0 {
    public static final String NAME = "name";
    public static final String aGa = "iconUrl";
    public static final String aGU = "count";
    public static final String[] ce = new String[]{"name", "iconUrl", "count"};
    private byte aIm;
    private int aGT;

    public AS(byte by, int n2) {
        this.aIm = by;
        this.aGT = n2;
    }

    public String[] getFields() {
        return ce;
    }

    public byte getType() {
        return this.aIm;
    }

    public int getCount() {
        return this.aGT;
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return aon_0.aYc().a(62, this.aIm, new Object[0]);
        }
        if (string.equals(aGa)) {
            try {
                return String.format(mu_1.rM().getString("tokensIconsPath"), this.aIm);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aGU)) {
            return this.aGT;
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

