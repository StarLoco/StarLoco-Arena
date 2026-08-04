/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;
import java.util.List;

public final class asD
extends so_1 {
    public final jy_2 bMM;
    public final List cSp;

    public asD(lc_0 lc_02, jy_2 jy_22, List list) {
        super(lc_02);
        this.bMM = jy_22;
        this.bMM.a(this);
        this.cSp = list;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            jt_1 jt_12 = (jt_1)iterator.next();
            Iterator iterator2 = jt_12.blm.iterator();
            while (iterator2.hasNext()) {
                ((jy_2)iterator2.next()).a(this);
            }
            iterator2 = jt_12.blo.iterator();
            while (iterator2.hasNext()) {
                ((TK)iterator2.next()).a(this);
            }
        }
    }

    public String toString() {
        return "switch (" + this.bMM + ") { (" + this.cSp.size() + " statement groups) }";
    }

    public void a(awv_0 awv_02) {
        awv_02.c(this);
    }
}

