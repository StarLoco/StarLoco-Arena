/*
 * Decompiled with CFR 0.152.
 */
import java.util.Map;

/*
 * Renamed from afu
 */
public class afu_2 {
    static final String crc = "http://www.slf4j.org/codes.html#null_MDCA";
    static final String crd = "http://www.slf4j.org/codes.html#no_static_mdc_binder";
    static ahy_2 cre;

    private afu_2() {
    }

    public static void put(String string, String string2) {
        if (string == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (cre == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        cre.put(string, string2);
    }

    public static String get(String string) {
        if (string == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (cre == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        return cre.get(string);
    }

    public static void remove(String string) {
        if (string == null) {
            throw new IllegalArgumentException("key parameter cannot be null");
        }
        if (cre == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        cre.remove(string);
    }

    public static void clear() {
        if (cre == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        cre.clear();
    }

    public static Map Uv() {
        if (cre == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        return cre.Uv();
    }

    public static void e(Map map) {
        if (cre == null) {
            throw new IllegalStateException("MDCAdapter cannot be null. See also http://www.slf4j.org/codes.html#null_MDCA");
        }
        cre.e(map);
    }

    public static ahy_2 auZ() {
        return cre;
    }

    static {
        try {
            cre = arv_0.cPZ.aEA();
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            String string = noClassDefFoundError.getMessage();
            if (string != null && string.indexOf("org/slf4j/impl/StaticMDCBinder") != -1) {
                ql.bC("Failed to load class \"org.slf4j.impl.StaticMDCBinder\".");
                ql.bC("See http://www.slf4j.org/codes.html#no_static_mdc_binder for further details.");
            }
            throw noClassDefFoundError;
        }
        catch (Exception exception) {
            ql.a("Could not bind with an instance of class [" + arv_0.cPZ.aEB() + "]", exception);
        }
    }
}

