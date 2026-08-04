/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
 * Renamed from SN
 */
class sn_1
extends sk_1 {
    private final Ue bLT;

    sn_1(Ue ue) {
        this.bLT = ue;
    }

    protected Collection getCollection() {
        ArrayList<iv_1> arrayList = new ArrayList<iv_1>();
        Iterator iterator = Ue.a(this.bLT).aff().iterator();
        block0: while (iterator.hasNext()) {
            iv_1 iv_12 = (iv_1)iterator.next();
            Iterator iterator2 = this.bLT.aXv();
            while (iterator2.hasNext()) {
                if (((wb_2)iterator2.next()).a(iv_12)) continue;
                continue block0;
            }
            arrayList.add(iv_12);
        }
        return arrayList;
    }
}

