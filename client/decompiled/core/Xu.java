/*
 * Decompiled with CFR 0.152.
 */
public class Xu
implements aho_0 {
    public static final String xX = "name";
    public static final String bXU = "selected";
    public static final String bXV = "pipeInternalName";
    public static final String bXW = "descSentence";
    public static final String bXX = "displayable";
    public static final String bXY = "colorString";
    public static final String bXZ = "command";
    public static final String[] ce = new String[]{"name", "selected", "pipeInternalName", "descSentence", "displayable", "colorString", "command"};
    private final ua bYa;
    private final String bYb;
    private boolean bYc;
    private aee_1 bYd;
    private boolean bYe = true;
    private String arg;

    public Xu(ua ua2, String string, aee_1 aee_12, String string2) {
        this.bYa = ua2;
        this.bYb = string;
        this.bYc = true;
        this.bYd = aee_12;
        this.arg = string2;
    }

    public ua akQ() {
        return this.bYa;
    }

    public String akR() {
        return this.bYb;
    }

    public boolean isOpen() {
        return !this.bYe || this.bYc;
    }

    public void setOpen(boolean bl2) {
        this.bYc = bl2;
    }

    public aee_1 akS() {
        return this.bYd;
    }

    public void a(aee_1 aee_12) {
        this.bYd = aee_12;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(xX)) {
            return this.bYb;
        }
        if (string.equals(bXU)) {
            return this.bYc;
        }
        if (string.equals(bXV)) {
            return this.akQ().zV();
        }
        if (string.equals(bXW)) {
            return this.bYb;
        }
        if (string.equals(bXX)) {
            return true;
        }
        if (string.equals(bXY)) {
            float[] fArray = this.bYa.Aa();
            return fArray[0] + "," + fArray[1] + "," + fArray[2];
        }
        if (string.equals(bXZ)) {
            return this.arg;
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

    public boolean Ab() {
        return this.bYe;
    }

    public Xu cC(boolean bl2) {
        this.bYe = bl2;
        aor_1.aYh().a(this, ce);
        return this;
    }

    public void setCommand(String string) {
        this.arg = string;
    }
}

