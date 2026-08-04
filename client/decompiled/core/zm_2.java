/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from Zm
 */
public class zm_2
implements Comparator {
    public int a(arN arN2, arN arN3) {
        if (arN2.getName().equals(arN3.getName())) {
            return 0;
        }
        if (arN2.getName().equals("root")) {
            return -1;
        }
        if (arN3.getName().equals("root")) {
            return 1;
        }
        return arN2.getName().compareTo(arN3.getName());
    }
}

