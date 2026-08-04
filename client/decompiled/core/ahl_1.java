/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from ahL
 */
public abstract class ahl_1 {
    protected ahl_1() {
    }

    protected abstract void b(we_2 var1);

    protected abstract void c(we_2 var1);

    protected abstract void d(we_2 var1);

    protected abstract void a(we_2 var1, long var2);

    protected abstract void b(we_2 var1, long var2);

    protected void a(qa_2 qa_22, Comparator comparator) {
        long[] lArray = qa_22.adg();
        qa_22.clear();
        for (long l2 : lArray) {
            this.a(qa_22, l2, comparator);
        }
    }

    protected void a(qa_2 qa_22, long l2, Comparator comparator) {
        int n2;
        for (n2 = 0; n2 < qa_22.size() && comparator.compare(l2, qa_22.get(n2)) >= 0; ++n2) {
        }
        qa_22.d(n2, l2);
    }
}

