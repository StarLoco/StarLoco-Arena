/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.List;

public final class aqt
extends akE {
    public final TK Pj;
    public final List cOc;
    public final lo_2 cOd;
    va_2 cOe = null;

    public aqt(lc_0 lc_02, TK tK, List list, lo_2 lo_22) {
        super(lc_02);
        this.Pj = tK;
        this.Pj.a(this);
        this.cOc = list;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            ((xp_1)iterator.next()).a(this);
        }
        this.cOd = lo_22;
        if (lo_22 != null) {
            lo_22.a(this);
        }
    }

    public String toString() {
        return "try ... " + this.cOc.size() + (this.cOd == null ? " catches" : " catches ... finally");
    }

    public void a(awv_0 awv_02) {
        awv_02.d(this);
    }
}

