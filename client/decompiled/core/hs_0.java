/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/*
 * Renamed from HS
 */
public class hs_0 {
    protected static final Logger a = Logger.getLogger(hs_0.class);
    private final Class bfA;
    private final boolean bfB;
    private final String[] bfC;
    public static final String bfD = "(\"([^\"\\\\]|\\\\(.|\n))*\")";
    public static final String bfE = "([a-zA-Z]+)";
    public static final String bfF = "([0-9]+)";
    private String bfG = "";

    public hs_0(Class clazz, String ... stringArray) {
        this(clazz, true, stringArray);
    }

    public hs_0(Class clazz, boolean bl2, String ... stringArray) {
        this.bfA = clazz;
        this.bfC = this.l(stringArray);
        this.bfB = bl2;
        boolean bl3 = true;
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < stringArray.length; ++j) {
            if (Pattern.matches(bfE, stringArray[j]) || Pattern.matches(bfD, stringArray[j]) || Pattern.matches(bfF, stringArray[j])) continue;
            if (!bl3) {
                stringBuilder.append('|');
            }
            stringBuilder.append(stringArray[j].replaceAll("([^a-zA-Z0-9])", "\\\\$1"));
            bl3 = false;
        }
        this.bfG = stringBuilder.toString();
        if (!bl3) {
            this.bfG = '(' + this.bfG + ')';
        }
    }

    public hc_1 eD(String string) {
        hc_1 hc_12 = null;
        try {
            Constructor constructor = this.bfA.getConstructor(String.class);
            hc_12 = (hc_1)constructor.newInstance(string);
        }
        catch (InstantiationException instantiationException) {
            a.error((Object)"Exception", (Throwable)instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            a.error((Object)"Exception", (Throwable)illegalAccessException);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            a.error((Object)"Exception", (Throwable)noSuchMethodException);
        }
        catch (InvocationTargetException invocationTargetException) {
            a.error((Object)"Exception", (Throwable)invocationTargetException);
        }
        return hc_12;
    }

    public boolean match(String string) {
        for (String string2 : this.bfC) {
            if ((!this.bfB || !string2.equalsIgnoreCase(string)) && (this.bfB || !string2.equals(string))) continue;
            return true;
        }
        return false;
    }

    public String TA() {
        return this.bfG;
    }

    private String[] l(String[] stringArray) {
        return this.h(stringArray, stringArray.length - 1);
    }

    private String[] h(String[] stringArray, int n2) {
        if (n2 > 0) {
            this.h(stringArray, n2 - 1);
            for (int j = 0; j < n2; ++j) {
                if (stringArray[j].length() > stringArray[n2].length() || stringArray[j].length() >= stringArray[n2].length()) continue;
                String string = stringArray[n2];
                stringArray[n2] = stringArray[j];
                stringArray[j] = string;
            }
        }
        return stringArray;
    }
}

