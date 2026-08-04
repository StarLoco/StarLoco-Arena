/*
 * Decompiled with CFR 0.152.
 */
public class rV
extends pL
implements aho_0 {
    public static final String xX = "name";
    public static final String aiD = "characterName";
    public static final String aiE = "displayedName";
    public static final String aiF = "online";
    public static final String[] ce = new String[]{"name", "characterName", "displayedName", "online"};

    public rV(String string, String string2, boolean bl2, long l2) {
        super(string, string2, bl2, l2);
    }

    public rV(String string) {
        super(string);
    }

    public void b(String string, Object object) {
    }

    public Object getFieldValue(String string) {
        if (string.equals(xX)) {
            return this.getName();
        }
        if (string.equals(aiD)) {
            return this.uj();
        }
        if (string.equals(aiE)) {
            String string2 = this.uj();
            if (string2 != null && !string2.equals("")) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2).append("\n(");
                stringBuilder.append(this.getName()).append(")");
                return stringBuilder.toString();
            }
            return this.getName();
        }
        if (string.equals(aiF)) {
            return this.uq();
        }
        return null;
    }

    public void ai(boolean bl2) {
        super.ai(bl2);
        aor_1.aYh().a(this, aiF);
        aor_1.aYh().a(this, aiE);
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
}

