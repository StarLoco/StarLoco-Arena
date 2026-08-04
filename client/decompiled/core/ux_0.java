/*
 * Decompiled with CFR 0.152.
 */
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/*
 * Renamed from Ux
 */
public class ux_0 {
    public static boolean a(Vector vector, Vector vector2) {
        if (vector == vector2) {
            return true;
        }
        if (vector == null || vector2 == null) {
            return false;
        }
        return vector.equals(vector2);
    }

    public static boolean a(Dictionary dictionary, Dictionary dictionary2) {
        if (dictionary == dictionary2) {
            return true;
        }
        if (dictionary == null || dictionary2 == null) {
            return false;
        }
        if (dictionary.size() != dictionary2.size()) {
            return false;
        }
        Enumeration enumeration = dictionary.keys();
        while (enumeration.hasMoreElements()) {
            Object k2 = enumeration.nextElement();
            Object v = dictionary.get(k2);
            Object v2 = dictionary2.get(k2);
            if (v2 != null && v.equals(v2)) continue;
            return false;
        }
        return true;
    }

    public static void b(Dictionary dictionary, Dictionary dictionary2) {
        Enumeration enumeration = dictionary2.keys();
        while (enumeration.hasMoreElements()) {
            Object k2 = enumeration.nextElement();
            dictionary.put(k2, dictionary2.get(k2));
        }
    }

    public static Enumeration a(Enumeration enumeration, Enumeration enumeration2) {
        return new aip_0(enumeration, enumeration2);
    }

    public static Enumeration d(Iterator iterator) {
        return new aGU(iterator);
    }

    public static Iterator a(Enumeration enumeration) {
        return new agr_1(enumeration);
    }
}

