/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from nn
 */
public class nn_0
extends wy_2 {
    public static final String Oj = "value";
    public static final String Ok = "tokenValue";
    public static final String[] ce = new String[]{"value", "tokenValue"};
    public static final String[] oT = new String[ce.length + wy_2.ce.length];

    public String[] getFields() {
        return oT;
    }

    public Object getFieldValue(String string) {
        if (string.equals(Oj)) {
            return vt_2.it(((xj)this.NR()).getValue());
        }
        if (string.equals(Ok)) {
            return apN.aDK().Ln().yJ().toArray();
        }
        return super.getFieldValue(string);
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(wy_2.ce, 0, oT, ce.length, wy_2.ce.length);
    }
}

