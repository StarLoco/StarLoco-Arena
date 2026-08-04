/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from aEL
 */
class ael_0
implements Comparator {
    final /* synthetic */ ayg_0 dBM;

    ael_0(ayg_0 ayg_02) {
        this.dBM = ayg_02;
    }

    public int a(ho_0 ho_02, ho_0 ho_03) {
        return ho_03.getDate().compareTo(ho_02.getDate());
    }
}

