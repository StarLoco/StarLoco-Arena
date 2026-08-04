/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

public class auV
implements Comparator {
    public int compare(Object object, Object object2) {
        if (object == null) {
            if (object2 == null) {
                return 1;
            }
            return 0;
        }
        return object.equals(object2) ? 0 : 1;
    }

    public String toString() {
        return "EqualComparator";
    }
}

