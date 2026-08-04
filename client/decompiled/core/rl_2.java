/*
 * Decompiled with CFR 0.152.
 */
import java.io.Serializable;

/*
 * Renamed from rl
 */
public final class rl_2
implements Serializable {
    private static final long serialVersionUID = -814092767334282137L;
    public static final int OFF_INT = Integer.MAX_VALUE;
    public static final int ERROR_INT = 40000;
    public static final int WARN_INT = 30000;
    public static final int INFO_INT = 20000;
    public static final int DEBUG_INT = 10000;
    public static final int TRACE_INT = 5000;
    public static final int ALL_INT = Integer.MIN_VALUE;
    public static final Integer afR = new Integer(Integer.MAX_VALUE);
    public static final Integer afS = new Integer(40000);
    public static final Integer afT = new Integer(30000);
    public static final Integer afU = new Integer(20000);
    public static final Integer afV = new Integer(10000);
    public static final Integer afW = new Integer(10000);
    public static final Integer afX = new Integer(Integer.MIN_VALUE);
    public static final rl_2 afY = new rl_2(Integer.MAX_VALUE, "OFF");
    public static final rl_2 afZ = new rl_2(40000, "ERROR");
    public static final rl_2 aga = new rl_2(30000, "WARN");
    public static final rl_2 agb = new rl_2(20000, "INFO");
    public static final rl_2 agc = new rl_2(10000, "DEBUG");
    public static final rl_2 agd = new rl_2(5000, "TRACE");
    public static final rl_2 age = new rl_2(Integer.MIN_VALUE, "ALL");
    public final int agf;
    public final String agg;

    private rl_2(int n2, String string) {
        this.agf = n2;
        this.agg = string;
    }

    public final String toString() {
        return this.agg;
    }

    public final int toInt() {
        return this.agf;
    }

    public final Integer toInteger() {
        switch (this.agf) {
            case -2147483648: {
                return afX;
            }
            case 5000: {
                return afW;
            }
            case 10000: {
                return afV;
            }
            case 20000: {
                return afU;
            }
            case 30000: {
                return afT;
            }
            case 40000: {
                return afS;
            }
            case 0x7FFFFFFF: {
                return afR;
            }
        }
        throw new IllegalStateException("Level " + this.agg + ", " + this.agf + " is unknown.");
    }

    public boolean a(rl_2 rl_22) {
        return this.agf >= rl_22.agf;
    }

    public static rl_2 bH(String string) {
        return rl_2.a(string, agc);
    }

    public static rl_2 bI(String string) {
        return rl_2.a(string, agc);
    }

    public static rl_2 cY(int n2) {
        return rl_2.a(n2, agc);
    }

    public static rl_2 a(int n2, rl_2 rl_22) {
        switch (n2) {
            case -2147483648: {
                return age;
            }
            case 5000: {
                return agd;
            }
            case 10000: {
                return agc;
            }
            case 20000: {
                return agb;
            }
            case 30000: {
                return aga;
            }
            case 40000: {
                return afZ;
            }
            case 0x7FFFFFFF: {
                return afY;
            }
        }
        return rl_22;
    }

    public static rl_2 a(String string, rl_2 rl_22) {
        if (string == null) {
            return rl_22;
        }
        if (string.equalsIgnoreCase("ALL")) {
            return age;
        }
        if (string.equalsIgnoreCase("TRACE")) {
            return agd;
        }
        if (string.equalsIgnoreCase("DEBUG")) {
            return agc;
        }
        if (string.equalsIgnoreCase("INFO")) {
            return agb;
        }
        if (string.equalsIgnoreCase("WARN")) {
            return aga;
        }
        if (string.equalsIgnoreCase("ERROR")) {
            return afZ;
        }
        if (string.equalsIgnoreCase("OFF")) {
            return afY;
        }
        return rl_22;
    }

    private Object readResolve() {
        return rl_2.cY(this.agf);
    }
}

