/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collections;
import java.util.Comparator;

/*
 * Renamed from aON
 */
final class aon_1
extends avA {
    final /* synthetic */ sv_1 deQ;

    private aon_1(sv_1 sv_12, Comparator comparator) {
        this.deQ = sv_12;
        super(sv_12, comparator, null);
    }

    public final int a(sv_1 sv_12, Object object) {
        return Collections.binarySearch(sv_12, object, avA.a(this));
    }

    /* synthetic */ aon_1(sv_1 sv_12, Comparator comparator, qz_2 qz_22) {
        this(sv_12, comparator);
    }
}

