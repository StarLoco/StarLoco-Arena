/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from agw
 */
public class agw_2
implements Iterator {
    private final akz_0 aoc;

    public agw_2(cp_2 cp_22) {
        this.aoc = cp_22.eI();
    }

    public boolean hasNext() {
        return this.aoc.hasNext();
    }

    public Object next() {
        this.aoc.fK();
        return this.aoc.value();
    }

    public void remove() {
        this.aoc.remove();
    }
}

