/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from arR
 */
class arr_0
implements Comparator {
    arr_0() {
    }

    public int a(aAY aAY2, aAY aAY3) {
        return aAY2.aNa() == aAY3.aNa() ? (int)aAY2.wD() - (int)aAY3.wD() : (aAY2.aNa() == aAY3.aNa() ? 0 : (aAY2.aNa() > aAY3.aNa() ? 1 : -1));
    }
}

