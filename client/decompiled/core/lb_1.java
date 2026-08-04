/*
 * Decompiled with CFR 0.152.
 */
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Renamed from lB
 */
public class lb_1
implements Iterator {
    private final Iterator[] Hu;
    private static final Iterator hn = new akx_0();

    public lb_1(Iterator iterator, int n2) {
        this.Hu = new Iterator[n2];
        this.Hu[0] = iterator;
        for (int j = 1; j < n2; ++j) {
            this.Hu[j] = hn;
        }
    }

    public boolean hasNext() {
        if (this.Hu[this.Hu.length - 1].hasNext()) {
            return true;
        }
        int n2 = this.Hu.length - 2;
        if (n2 < 0) {
            return false;
        }
        while (true) {
            if (!this.Hu[n2].hasNext()) {
                if (n2 == 0) {
                    return false;
                }
                --n2;
                continue;
            }
            if (n2 == this.Hu.length - 1) {
                return true;
            }
            Object e = this.Hu[n2].next();
            if (e instanceof Iterator) {
                this.Hu[++n2] = (Iterator)e;
                continue;
            }
            if (e instanceof Object[]) {
                this.Hu[++n2] = Arrays.asList((Object[])e).iterator();
                continue;
            }
            if (e instanceof Collection) {
                this.Hu[++n2] = ((Collection)e).iterator();
                continue;
            }
            if (!(e instanceof Enumeration)) break;
            this.Hu[++n2] = new KK((Enumeration)e);
        }
        throw new aat_1();
    }

    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return this.Hu[this.Hu.length - 1].next();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}

