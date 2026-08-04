/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ao
 */
public class ao_0
implements aho_0,
nz_0 {
    public static final String ca = "debug.console";
    public static final String cb = "prompt";
    public static final String cc = "input";
    public static final String cd = "logs";
    public static final String[] ce = new String[]{"prompt", "input", "logs"};
    private static ao_0 cf = new ao_0();
    private String cg = "";
    private String ch = "";
    private String ci = "";

    public ao_0() {
        azs_0 azs_02 = azs_0.aLV();
        azs_02.g(ca, this);
    }

    public static ao_0 aU() {
        return cf;
    }

    public void setPrompt(String string) {
        this.cg = string;
        azs_0.aLV().a(ca, cb, (Object)string);
    }

    public void err(String string) {
        if (string != null && string.length() == 0) {
            string = "Commande invalide";
        }
        String string2 = new rw_2().bM("FF0000").bJ(string).bJ("\n").wR();
        azs_0.aLV().c(ca, cd, (Object)string2);
    }

    public void log(String string) {
        String string2 = new rw_2().bM("00FF00").bJ(string).bJ("\n").wR();
        azs_0.aLV().c(ca, cd, (Object)string2);
    }

    public void trace(String string) {
        azs_0.aLV().c(ca, cd, (Object)(string + "\n"));
    }

    public Object getFieldValue(String string) {
        if (string.equals(cb)) {
            return this.cg;
        }
        if (string.equals(cc)) {
            return this.ch;
        }
        if (string.equals(cd)) {
            return this.ci;
        }
        return null;
    }

    public String[] getFields() {
        return ce;
    }

    public void a(String string, Object object) {
        if (string.equals(cb)) {
            this.cg = (String)object;
        } else if (string.equals(cc)) {
            this.ch = (String)object;
        } else if (string.equals(cd)) {
            this.ci = (String)object;
        }
    }

    public void b(String string, Object object) {
        if (string.equals(cd)) {
            this.ci = this.ci + (String)object;
        }
    }

    public void c(String string, Object object) {
    }

    public boolean l(String string) {
        return !string.equals(cd);
    }

    public void b(String string, int n2) {
    }
}

