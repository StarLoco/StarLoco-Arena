/*
 * Decompiled with CFR 0.152.
 */
public class tO
extends wq_2
implements aho_0 {
    public static final String xX = "name";
    public static final String aog = "description";
    public static final String aoh = "iconUrl";
    public static final String aoi = "illustrationUrl";
    public static final String aoj = "cardType";
    public static final String[] ce = new String[]{"name", "description", "iconUrl", "illustrationUrl", "cardType"};
    private String fM = null;
    boolean aok;

    public tO(int n2, boolean bl2) {
        super(n2);
        this.aok = bl2;
    }

    public String getName() {
        return aon_0.aYc().a(8, this.getId(), new Object[0]);
    }

    public String getDescription() {
        if (this.fM == null) {
            this.fM = asf_0.a(this.getId(), this.aok, this.ajz(), null, 27);
        }
        return this.fM;
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        if (string.equals(xX)) {
            return this.getName();
        }
        if (string.equals(aog)) {
            return this.getDescription();
        }
        if (string.equals(aoh)) {
            try {
                return String.format(mu_1.rM().getString("eventsIconsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aoi)) {
            try {
                return String.format(mu_1.rM().getString("eventsIllustrationsPath"), this.getId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (string.equals(aoj)) {
            return "event";
        }
        return null;
    }

    public String[] getFields() {
        return ce;
    }

    public boolean l(String string) {
        return false;
    }

    public void c(String string, Object object) {
    }

    public void a(String string, Object object) {
    }

    public String toString() {
        return "ClientEvent : " + this.getName();
    }
}

