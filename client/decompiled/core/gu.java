/*
 * Decompiled with CFR 0.152.
 */
public class gu
extends akw_0
implements aho_0 {
    public static final String NAME = "name";
    public static final String sU = "description";
    public static final String sV = "value";
    public static final String sW = "iconUrl";
    public static final String TYPE = "type";
    private static final String[] ce = new String[]{"name", "description", "value", "iconUrl", "type"};
    private int rv = 0;

    public gu(akw_0 akw_02) {
        super((int[])akw_02.rg().clone(), akw_02.aAl(), akw_02.aAm());
        this.JI = new int[akw_02.rg().length];
        System.arraycopy(akw_02.rg(), 0, this.JI, 0, this.JI.length);
        this.rv = akw_02.getType();
    }

    protected int aa() {
        return this.JI.length;
    }

    protected int a(et_2 et_22) {
        return 0;
    }

    public int getType() {
        return this.rv;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return aon_0.aYc().a(51, this.getType(), this.aAk());
        }
        if (string.equals(sU)) {
            return asf_0.b(new akw_0[]{this});
        }
        if (string.equals(sV)) {
            return this.JI[0];
        }
        if (string.equals(sW)) {
            // empty if block
        }
        if (string.equals(TYPE)) {
            return this.rv;
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

