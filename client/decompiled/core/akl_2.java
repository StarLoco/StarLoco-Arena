/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from akL
 */
public class akl_2
implements aho_0 {
    private wy_2 boK;
    private long cql;
    private int Hq;
    private int Hr;
    public static final String cDR = "card";
    public static final String cDS = "delay";
    public static final String cDT = "xDeviation";
    public static final String cDU = "yDeviation";
    public static final String[] ce = new String[]{"card", "delay", "xDeviation", "yDeviation"};

    public akl_2(wy_2 wy_22, long l2, int n2, int n3) {
        this.boK = wy_22;
        this.cql = l2;
        this.Hq = n2;
        this.Hr = n3;
    }

    public wy_2 apc() {
        return this.boK;
    }

    public int qp() {
        return this.Hq;
    }

    public int qq() {
        return this.Hr;
    }

    public long azW() {
        return this.cql;
    }

    public void i(wy_2 wy_22) {
        this.boK = wy_22;
    }

    public void setDelay(long l2) {
        this.cql = l2;
    }

    public void li(int n2) {
        this.Hq = n2;
    }

    public void lj(int n2) {
        this.Hr = n2;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(cDR)) {
            return this.boK;
        }
        if (string.equals(cDS)) {
            return this.cql;
        }
        if (string.equals(cDT)) {
            return this.Hq;
        }
        if (string.equals(cDU)) {
            return this.Hr;
        }
        return null;
    }

    public void a(String string, Object object) {
        if (string.equals(cDS)) {
            this.setDelay((Long)object);
        }
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return string.equals(cDS);
    }
}

