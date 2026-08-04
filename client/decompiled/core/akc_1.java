/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from aKc
 */
class akc_1
implements Comparator {
    final /* synthetic */ aij_0 dTk;

    akc_1(aij_0 aij_02) {
        this.dTk = aij_02;
    }

    public int a(fe_1 fe_12, fe_1 fe_13) {
        return Float.compare(1.0f * (float)fe_12.getValue() / (float)fe_12.aEb().length, 1.0f * (float)fe_13.getValue() / (float)fe_13.aEb().length);
    }
}

