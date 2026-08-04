/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from rR
 */
public final class rr_2
extends xh_1 {
    public final jy_2 B;
    public final String aik;
    public final jy_2 ail;

    public rr_2(lc_0 lc_02, jy_2 jy_22, String string, jy_2 jy_23) {
        super(lc_02);
        this.B = jy_22;
        this.aik = string;
        this.ail = jy_23;
    }

    public String toString() {
        return this.B.toString() + ' ' + this.aik + ' ' + this.ail.toString();
    }

    public Iterator xO() {
        jy_2 jy_22;
        ArrayList<jy_2> arrayList = new ArrayList<jy_2>();
        rr_2 rr_22 = this;
        while (true) {
            arrayList.add(rr_22.ail);
            jy_22 = rr_22.B;
            if (!(jy_22 instanceof rr_2) || ((rr_2)jy_22).aik != this.aik) break;
            rr_22 = (rr_2)jy_22;
        }
        arrayList.add(jy_22);
        return new aov_1(arrayList.listIterator(arrayList.size()));
    }

    public void a(Ax ax) {
        ax.d(this);
    }

    public void a(EO eO) {
        eO.d(this);
    }
}

