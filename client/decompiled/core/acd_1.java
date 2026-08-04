/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCD
 */
public class acd_1 {
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;
    private boolean duA = true;
    private boolean duB = true;

    public acd_1() {
    }

    public acd_1(int n2, int n3) {
        this.min = n2;
        this.max = n3;
    }

    public acd_1(int n2, int n3, boolean bl2, boolean bl3) {
        this.min = n2;
        this.max = n3;
        this.duA = bl2;
        this.duB = bl3;
    }

    public static acd_1 kx(String string) {
        int n2;
        if (string == null) {
            new NullPointerException("Argument cannot be null");
        }
        acd_1 acd_12 = new acd_1();
        int n3 = string.indexOf(46);
        String string2 = null;
        String string3 = null;
        if (n3 != -1) {
            string2 = string.substring(0, n3);
            if (n3 + 1 == string.length()) {
                throw new IllegalArgumentException("Formatting string [" + string + "] should not end with '.'");
            }
            string3 = string.substring(n3 + 1);
        } else {
            string2 = string;
        }
        if (string2 != null && string2.length() > 0) {
            n2 = Integer.parseInt(string2);
            if (n2 >= 0) {
                acd_12.min = n2;
            } else {
                acd_12.min = -n2;
                acd_12.duA = false;
            }
        }
        if (string3 != null && string3.length() > 0) {
            n2 = Integer.parseInt(string3);
            if (n2 >= 0) {
                acd_12.max = n2;
            } else {
                acd_12.max = -n2;
                acd_12.duB = false;
            }
        }
        return acd_12;
    }

    public boolean aOs() {
        return this.duA;
    }

    public void eI(boolean bl2) {
        this.duA = bl2;
    }

    public int getMax() {
        return this.max;
    }

    public void at(int n2) {
        this.max = n2;
    }

    public int getMin() {
        return this.min;
    }

    public void as(int n2) {
        this.min = n2;
    }

    public boolean aOt() {
        return this.duB;
    }

    public void eJ(boolean bl2) {
        this.duB = bl2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof acd_1)) {
            return false;
        }
        acd_1 acd_12 = (acd_1)object;
        return this.min == acd_12.min && this.max == acd_12.max && this.duA == acd_12.duA && this.duB == acd_12.duB;
    }

    public String toString() {
        return "FormatInfo(" + this.min + ", " + this.max + ", " + this.duA + ", " + this.duB + ")";
    }
}

