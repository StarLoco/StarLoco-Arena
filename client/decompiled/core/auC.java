/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

public class auC
implements Cloneable {
    protected static final Logger a = Logger.getLogger(auC.class);
    private double cWz;

    public auC(int n2) {
        this.cWz = n2;
    }

    public auC(double d) {
        this.cWz = d;
    }

    public static auC jC(String string) {
        if (string.charAt(string.length() - 1) != '%') {
            return null;
        }
        double d = Double.valueOf(string.substring(0, string.length() - 1));
        return new auC(d);
    }

    public double getValue() {
        return this.cWz;
    }

    public void y(double d) {
        this.cWz = d;
    }

    public boolean a(auC auC2) {
        return auC2 != null && auC2.cWz == this.cWz;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            a.error((Object)"Exception", (Throwable)cloneNotSupportedException);
            return null;
        }
    }
}

