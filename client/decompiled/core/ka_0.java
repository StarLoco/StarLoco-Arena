/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;
import org.xml.sax.Locator;

/*
 * Renamed from Ka
 */
public abstract class ka_0
extends ii_2 {
    public static final String aUC = "name";
    public static final String bnd = "value";
    public static final String bne = "file";
    public static final String aUG = "class";
    public static final String bnf = "pattern";
    public static final String bng = "actionClass";

    public abstract void a(qq_0 var1, String var2, Attributes var3);

    public void b(qq_0 qq_02, String string) {
    }

    public abstract void a(qq_0 var1, String var2);

    public String toString() {
        return this.getClass().getName();
    }

    protected int b(qq_0 qq_02) {
        jh_1 jh_12 = qq_02.vY();
        Locator locator = jh_12.getLocator();
        if (locator != null) {
            return locator.getColumnNumber();
        }
        return -1;
    }

    protected int c(qq_0 qq_02) {
        jh_1 jh_12 = qq_02.vY();
        Locator locator = jh_12.getLocator();
        if (locator != null) {
            return locator.getLineNumber();
        }
        return -1;
    }

    protected String d(qq_0 qq_02) {
        String string = "line: " + this.c(qq_02) + ", column: " + this.b(qq_02);
        return string;
    }
}

