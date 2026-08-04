/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from LO
 */
public final class lo_2
extends akE {
    public final List bsj = new ArrayList();

    public lo_2(lc_0 lc_02) {
        super(lc_02);
    }

    public void e(TK tK) {
        this.bsj.add(tK);
        tK.a(this);
    }

    public void i(List list) {
        this.bsj.addAll(list);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            ((TK)iterator.next()).a(this);
        }
    }

    public TK[] Ya() {
        return this.bsj.toArray(new TK[this.bsj.size()]);
    }

    public void a(awv_0 awv_02) {
        awv_02.d(this);
    }

    public String toString() {
        return "{ ... }";
    }
}

