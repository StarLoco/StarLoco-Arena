/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from Rf
 */
class rf_1
implements Comparator {
    private rf_1() {
    }

    public int a(zy_2 zy_22, zy_2 zy_23) {
        if (zy_22.anV() == -1) {
            return 1;
        }
        if (zy_23.anV() == -1 || zy_23.anV() > zy_22.anV()) {
            return -1;
        }
        return 1;
    }

    /* synthetic */ rf_1(aCI aCI2) {
        this();
    }
}

