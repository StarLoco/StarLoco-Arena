/*
 * Decompiled with CFR 0.152.
 */
public class ij
implements SQ,
aho_0 {
    public static final int xR = 200;
    public static final int xS = 5000;
    public static final int xT = 30000;
    public static final int xU = 40000;
    public static final String xV = "minimum";
    public static final String xW = "maximum";
    public static final String xX = "name";
    public static final short xY = 4;
    public static final String[] ce = new String[]{"minimum", "maximum", "name"};
    private final int xZ;
    private final int ya;

    public ij(int n2, int n3) {
        this.xZ = n3;
        this.ya = n2;
    }

    public boolean c(wy_2 wy_22) {
        int n2 = ((xj)wy_22.NR()).getValue();
        return n2 >= this.ya && n2 <= this.xZ;
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(xV)) {
            return this.ya;
        }
        if (string.equals(xW)) {
            return this.xZ;
        }
        if (string.equals(xX)) {
            return this.toString();
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

    public String toString() {
        String string;
        if (this.ya == 0 && this.xZ == Integer.MAX_VALUE) {
            return aon_0.aYc().getString("coachCardCost.all");
        }
        if (this.ya == 0) {
            string = aon_0.aYc().getString("coachCardCost.lessThan") + " " + this.xZ;
        } else if (this.xZ == Integer.MAX_VALUE) {
            string = aon_0.aYc().getString("coachCardCost.moreThan") + " " + this.ya;
        } else {
            string = aon_0.aYc().getString("coachCardCost.from") + " " + this.ya + " ";
            string = string + aon_0.aYc().getString("coachCardCost.to") + " " + this.xZ;
        }
        return string + " " + aon_0.aYc().getString("coachCardCost.unit");
    }
}

