/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Al
 */
public class al_1
implements aho_0 {
    private String ahG;
    private int aGT;
    public static final String TYPE = "type";
    public static final String NAME = "name";
    public static final String sU = "description";
    public static final String aGU = "count";
    public static final String nN = "style";
    public static final String[] ce = new String[]{"type", "name", "description", "count", "style"};
    public static final String aGV = "Death";
    public static final String aGW = "Injury";

    public al_1(String string, int n2) {
        this.ahG = string;
        this.aGT = n2;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(TYPE)) {
            return this.ahG;
        }
        if (string.equals(NAME)) {
            return aon_0.aYc().getString("fightEndAchievement" + this.ahG + "Name");
        }
        if (string.equals(sU)) {
            if (this.aGT > 0) {
                return aon_0.aYc().getString("fightEndAchievement" + this.ahG + "Description");
            }
            return aon_0.aYc().getString("fightEndAchievement" + this.ahG + "DescriptionFailed");
        }
        if (string.equals(aGU)) {
            if (this.ahG.equals(aGV)) {
                return aon_0.aYc().getString("fightEndDead", this.aGT);
            }
            if (this.ahG.equals(aGW)) {
                return aon_0.aYc().getString("fightEndInjured", this.aGT);
            }
        }
        if (string.equals(nN)) {
            return this.ahG + "Achievement";
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

