/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from aag
 */
class aag_0
implements Iterator {
    int i = 0;
    final /* synthetic */ byte ceF;
    final /* synthetic */ Om ceG;

    aag_0(Om om, byte by) {
        this.ceG = om;
        this.ceF = by;
    }

    public boolean hasNext() {
        return Om.a(this.ceG)[this.ceF].size() > this.i;
    }

    public ry HH() {
        int n2 = Om.a(this.ceG)[this.ceF].get(this.i);
        ++this.i;
        return Om.hb(n2);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

