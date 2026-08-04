/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aIf
 */
public class aif_2
implements aho_0 {
    private String nN = "style";
    private String NAME = "name";
    private String dOH = "enabled";
    private String[] ce = new String[]{this.NAME, this.nN, this.dOH};
    private String dOI;
    private byte aIm;
    private String arg;

    public String[] getFields() {
        return this.ce;
    }

    public aif_2(String string, byte by, String string2) {
        this.dOI = string;
        this.aIm = by;
        this.arg = string2;
    }

    public byte getType() {
        return this.aIm;
    }

    public void execute() {
        apk_0.aDz().iS(this.arg);
    }

    public Object getFieldValue(String string) {
        if (string.equals(this.nN)) {
            return this.dOI != null ? this.dOI : "";
        }
        if (string.equals(this.NAME)) {
            return aon_0.aYc().getString(this.dOI);
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

