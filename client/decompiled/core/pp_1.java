/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/*
 * Renamed from Pp
 */
public class pp_1
implements apG {
    protected static final Logger a = Logger.getLogger(pp_1.class);
    public static final Class ach = String.class;
    private static final Pattern bDK = Pattern.compile("(%([^%]*)%)");

    public String convert(String string) {
        return this.e(ach, string);
    }

    public String e(Class clazz, String string) {
        if (string == null) {
            return null;
        }
        Matcher matcher = bDK.matcher(string);
        String string2 = string.toString();
        while (matcher.find()) {
            try {
                string2 = string2.replace(matcher.group(1), add_1.aOG().kE(matcher.group(2)));
            }
            catch (Exception exception) {
                a.error((Object)"Exception", (Throwable)exception);
            }
        }
        return string2;
    }

    public Class uk() {
        return ach;
    }

    public boolean ul() {
        return true;
    }

    public boolean um() {
        return false;
    }

    public String a(zp_1 zp_12, DS dS, Class clazz, String string, afq_1 afq_12) {
        zp_12.j(ach);
        Matcher matcher = bDK.matcher(string);
        String string2 = string.toString();
        if (matcher.find()) {
            matcher.reset();
            String string3 = zp_12.GQ();
            zp_12.a(new aKI(clazz, string3, "\"" + string2 + "\""));
            while (matcher.find()) {
                zp_12.a(new aKI(clazz, string3, string3 + ".replace(\"" + matcher.group(1) + "\", Xulor.getInstance().getTranslatedString(\"" + matcher.group(2) + "\"))"));
            }
            return string3;
        }
        string2 = string2.replace("\\", "\\\\");
        return "\"" + string2 + "\"";
    }
}

