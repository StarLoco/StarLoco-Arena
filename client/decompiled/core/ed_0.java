/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ED
 */
public class ed_0
implements aho_0 {
    public static final String aRN = "replies";
    public static final String vq = "message";
    public static final String[] ce = new String[]{"replies", "message"};
    private short fL;
    private sv_1 aRO = new sv_1(ana_2.OU);

    public ed_0(short s) {
        this.fL = s;
    }

    public void b(ana_2 ana_22) {
        this.aRO.add(ana_22);
    }

    public short tI() {
        return this.fL;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(vq)) {
            return aon_0.aYc().a(59, this.fL, new Object[0]);
        }
        if (string.equals(aRN)) {
            return this.aRO;
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

