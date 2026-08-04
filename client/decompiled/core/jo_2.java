/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from jO
 */
public class jo_2
extends ha_2
implements aho_0 {
    private boolean CO;
    protected static final String CP = "isHeavy";
    public static final String[] ce = new String[]{"isHeavy"};
    public static final String[] oT = new String[ce.length + ha_2.ce.length];

    public jo_2(short s, byte by, short s2, akw_0[] akw_0Array, ArrayList arrayList, boolean bl2) {
        super(s, by, s2, akw_0Array, arrayList);
        this.CO = bl2;
    }

    public String[] getFields() {
        return oT;
    }

    public Object getFieldValue(String string) {
        if (string.equals("name")) {
            return aon_0.aYc().a(40, this.fL, new Object[0]);
        }
        if (string.equals("type")) {
            // empty if block
        }
        if (string.equals("description")) {
            return asf_0.b(this.czZ);
        }
        if (string.equals("duration")) {
            return this.czY;
        }
        if (string.equals(CP)) {
            return this.CO;
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

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(ha_2.ce, 0, oT, ce.length, ha_2.ce.length);
    }
}

