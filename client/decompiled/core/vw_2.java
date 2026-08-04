/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from vw
 */
public class vw_2
implements aho_0 {
    public static final String aog = "description";
    public static final String asT = "quantity";
    public static final String asU = "timeRemaining";
    private short oX;
    private short asV;
    private String fM;
    public static final String[] ce = new String[]{"description", "quantity", "timeRemaining"};

    public String[] getFields() {
        return ce;
    }

    public vw_2(short s, short s2, String string) {
        this.oX = s;
        this.asV = s2;
        this.fM = string;
    }

    public Object getFieldValue(String string) {
        if (string.equals(aog)) {
            return this.fM;
        }
        if (string.equals(asT)) {
            return this.hG();
        }
        if (string.equals(asU)) {
            return this.BI() >= 0 ? aon_0.aYc().getString("cast.durationDescription", this.BI()) : aon_0.aYc().getString("cast.infiniteDuration");
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

    public short hG() {
        return this.oX;
    }

    public short BI() {
        return this.asV;
    }
}

