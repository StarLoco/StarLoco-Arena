/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from aBQ
 */
public abstract class abq_2
extends ahj_0 {
    public abq_2(Iterator iterator) {
        super(iterator);
    }

    public final Object next() {
        return this.n(this.dLW.next());
    }

    protected abstract Object n(Object var1);
}

