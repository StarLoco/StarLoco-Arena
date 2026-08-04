/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from AR
 */
class ar_1
implements Iterator {
    final akz_0 aIl;

    ar_1(aaR aaR2) {
        this.aIl = aaR.c(aaR2).eI();
    }

    public boolean hasNext() {
        return this.aIl.hasNext();
    }

    public ry HH() {
        this.aIl.fK();
        return wn_2.aT(this.aIl.TO());
    }

    public void remove() {
        throw new UnsupportedOperationException("interdit de modifier le layer. Utiliser HighLightManager.remove()");
    }
}

