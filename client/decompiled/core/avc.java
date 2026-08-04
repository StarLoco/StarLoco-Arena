/*
 * Decompiled with CFR 0.152.
 */
public class avc {
    static Class cYx;
    static Class cYy;

    public aqm aIc() {
        return this.L(null);
    }

    public aqm L(UI uI) {
        String string = null;
        string = uI == null ? System.getProperty("ant.regexp.regexpimpl") : uI.getProperty("ant.regexp.regexpimpl");
        if (string != null) {
            return this.jO(string);
        }
        Throwable throwable = null;
        try {
            this.jP("java.util.regex.Matcher");
            return this.jO("org.apache.tools.ant.util.regexp.Jdk14RegexpMatcher");
        }
        catch (eq_2 eq_22) {
            throwable = avc.a(throwable, eq_22, ako_1.azZ() < 14);
            try {
                this.jP("org.apache.oro.text.regex.Pattern");
                return this.jO("org.apache.tools.ant.util.regexp.JakartaOroMatcher");
            }
            catch (eq_2 eq_23) {
                throwable = avc.a(throwable, eq_23, true);
                try {
                    this.jP("org.apache.regexp.RE");
                    return this.jO("org.apache.tools.ant.util.regexp.JakartaRegexpMatcher");
                }
                catch (eq_2 eq_24) {
                    throwable = avc.a(throwable, eq_24, true);
                    throw new eq_2("No supported regular expression matcher found" + (throwable != null ? ": " + throwable : ""), throwable);
                }
            }
        }
    }

    static Throwable a(Throwable throwable, eq_2 eq_22, boolean bl2) {
        if (throwable != null) {
            return throwable;
        }
        Throwable throwable2 = eq_22.getException();
        return bl2 && throwable2 instanceof ClassNotFoundException ? null : throwable2;
    }

    protected aqm jO(String string) {
        return (aqm)awK.a(string, (cYx == null ? (cYx = avc.a("avc")) : cYx).getClassLoader(), cYy == null ? (cYy = avc.a("aqm")) : cYy);
    }

    protected void jP(String string) {
        try {
            Class.forName(string);
        }
        catch (Throwable throwable) {
            throw new eq_2(throwable);
        }
    }

    public static boolean M(UI uI) {
        try {
            new avc().L(uI);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

