/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

abstract class avA {
    private final Comparator cIj;
    final /* synthetic */ sv_1 deQ;

    private avA(sv_1 sv_12, Comparator comparator) {
        this.deQ = sv_12;
        this.cIj = comparator;
    }

    public final int compare(Object object, Object object2) {
        return this.cIj.compare(object, object2);
    }

    public abstract int a(sv_1 var1, Object var2);

    /* synthetic */ avA(sv_1 sv_12, Comparator comparator, qz_2 qz_22) {
        this(sv_12, comparator);
    }

    static /* synthetic */ Comparator a(avA avA2) {
        return avA2.cIj;
    }
}

