/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from Bq
 */
class bq_0
implements Comparator {
    bq_0() {
    }

    public int a(ana_2 ana_22, ana_2 ana_23) {
        return ana_22.aCb() == ana_23.aCb() ? 0 : (ana_22.aCb() < ana_23.aCb() ? -1 : 1);
    }
}

