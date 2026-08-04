/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from aah
 */
class aah_1
implements Iterator {
    final hp_0 ceH;
    final pf_0 ceI;
    final /* synthetic */ Om ceG;

    aah_1(Om om) {
        this.ceG = om;
        this.ceH = Om.b(this.ceG).aCq();
        this.ceI = new pf_0();
    }

    public boolean hasNext() {
        return this.ceH.hasNext();
    }

    public pf_0 aoH() {
        this.ceH.fK();
        this.ceI.ac(Om.hb(this.ceH.kR()));
        this.ceI.ad(this.ceH.value());
        return this.ceI;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}

