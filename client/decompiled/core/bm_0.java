/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * Renamed from bm
 */
public class bm_0
implements Iterator {
    private final List fB = new ArrayList(2);
    private Iterator fC;
    private int fD = -1;

    public bm_0(Collection collection) {
        this.fB.addAll(collection);
        if (!this.fB.isEmpty()) {
            this.fC = (Iterator)this.fB.get(0);
            this.fD = 0;
        }
    }

    public bm_0() {
        this(Collections.emptyList());
    }

    public bm_0(Iterator ... iteratorArray) {
        this(Arrays.asList(iteratorArray));
    }

    public bm_0(Iterator iterator) {
        this(Arrays.asList(iterator));
    }

    public bm_0(Iterator iterator, Iterator iterator2) {
        this(Arrays.asList(iterator, iterator2));
    }

    public void a(Iterator iterator) {
        if (this.fC == null) {
            this.fC = iterator;
            this.fD = this.fB.size();
        }
        this.fB.add(iterator);
    }

    public boolean hasNext() {
        if (this.fC == null) {
            return false;
        }
        if (this.fC.hasNext()) {
            return true;
        }
        int n2 = this.fB.size();
        for (int j = this.fD + 1; j < n2; ++j) {
            if (!((Iterator)this.fB.get(j)).hasNext()) continue;
            return true;
        }
        return false;
    }

    public Object next() {
        if (this.fC.hasNext()) {
            return this.fC.next();
        }
        int n2 = this.fB.size();
        for (int j = this.fD + 1; j < n2; ++j) {
            this.fD = j;
            this.fC = (Iterator)this.fB.get(j);
            if (!this.fC.hasNext()) continue;
            return this.fC.next();
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

