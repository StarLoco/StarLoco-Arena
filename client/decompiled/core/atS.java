/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class atS
implements Iterable {
    protected final List cVp = new ArrayList();

    public void a(hs_0 hs_02) {
        this.cVp.add(hs_02);
    }

    public Iterator iterator() {
        return this.cVp.iterator();
    }

    public hc_1 eD(String string) {
        for (hs_0 hs_02 : this.cVp) {
            if (!hs_02.match(string)) continue;
            return hs_02.eD(string);
        }
        return null;
    }

    public abstract String aGW();
}

