/*
 * Decompiled with CFR 0.152.
 */
import java.util.AbstractCollection;
import java.util.Iterator;

/*
 * Renamed from nl
 */
class nl_2
extends AbstractCollection {
    private int size;
    private final afx_1 Oi;

    nl_2(afx_1 afx_12) {
        this.Oi = afx_12;
        this.size = 0;
        Iterator iterator = afx_1.a(afx_12).iterator();
        while (iterator.hasNext()) {
            this.size += ((mx_2)iterator.next()).size();
        }
    }

    public int size() {
        return this.size;
    }

    public Iterator iterator() {
        return new Wb(this, null);
    }

    static afx_1 a(nl_2 nl_22) {
        return nl_22.Oi;
    }
}

