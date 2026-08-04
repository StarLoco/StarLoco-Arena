/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from auM
 */
public class aum_0
extends avc {
    static Class cWR;
    static Class cWS;

    public axk aHE() {
        return this.K(null);
    }

    public axk K(UI uI) {
        String string = null;
        string = uI == null ? System.getProperty("ant.regexp.regexpimpl") : uI.getProperty("ant.regexp.regexpimpl");
        if (string != null) {
            return this.jD(string);
        }
        Throwable throwable = null;
        try {
            this.jP("java.util.regex.Matcher");
            return this.jD("org.apache.tools.ant.util.regexp.Jdk14RegexpRegexp");
        }
        catch (eq_2 eq_22) {
            throwable = aum_0.a(throwable, eq_22, ako_1.azZ() < 14);
            try {
                this.jP("org.apache.oro.text.regex.Pattern");
                return this.jD("org.apache.tools.ant.util.regexp.JakartaOroRegexp");
            }
            catch (eq_2 eq_23) {
                throwable = aum_0.a(throwable, eq_23, true);
                try {
                    this.jP("org.apache.regexp.RE");
                    return this.jD("org.apache.tools.ant.util.regexp.JakartaRegexpRegexp");
                }
                catch (eq_2 eq_24) {
                    throwable = aum_0.a(throwable, eq_24, true);
                    throw new eq_2("No supported regular expression matcher found" + (throwable != null ? ": " + throwable : ""), throwable);
                }
            }
        }
    }

    protected axk jD(String string) {
        return (axk)awK.a(string, (cWR == null ? (cWR = aum_0.a("auM")) : cWR).getClassLoader(), cWS == null ? (cWS = aum_0.a("axk")) : cWS);
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

