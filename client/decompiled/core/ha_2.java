/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from ha
 */
public class ha_2
extends aiz_2
implements aho_0 {
    protected static final String NAME = "name";
    protected static final String sU = "description";
    protected static final String uW = "duration";
    protected static final String TYPE = "type";
    public static final String[] ce = new String[]{"name", "description", "duration", "type"};

    public ha_2(short s, byte by, short s2, akw_0[] akw_0Array, ArrayList arrayList) {
        super(s, by, s2, akw_0Array, arrayList);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(NAME)) {
            return aon_0.aYc().a(40, this.fL, new Object[0]);
        }
        if (string.equals(TYPE)) {
            return aon_0.aYc().a(-1, this.Gp, new Object[0]);
        }
        if (string.equals(sU)) {
            return asf_0.b(this);
        }
        if (string.equals(uW)) {
            return this.czY;
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

