/*
 * Decompiled with CFR 0.152.
 */
public class ako
implements aho_0 {
    private short fL;
    private int bnc;
    private short baM;
    private static final String NAME = "name";
    private static final String sV = "value";
    private static final String aVf = "completion";
    public static final String[] ce = new String[]{"value", "name", "completion"};

    public ako(short s, short s2, int n2) {
        this.fL = s;
        this.baM = s2;
        this.bnc = n2;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(sV)) {
            return this.baM;
        }
        if (string.equals(NAME)) {
            if (this.fL != 0) {
                return aon_0.aYc().a(48, this.fL, new Object[0]);
            }
            return aon_0.aYc().a(23, this.bnc, new Object[0]);
        }
        if (string.equals(aVf)) {
            if (this.fL != 0) {
                return apN.aDK().Ln().qI().cp(this.fL) / this.baM;
            }
            return apN.aDK().Ln().aQm().bY(this.bnc) ? 1 : 0;
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

    public short tI() {
        return this.fL;
    }
}

