/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from aLg
 */
class alg_2
implements aLR {
    final /* synthetic */ no dVt;
    final /* synthetic */ List dVu;
    final /* synthetic */ ho_0 dVv;

    alg_2(ho_0 ho_02, no no2, List list) {
        this.dVv = ho_02;
        this.dVt = no2;
        this.dVu = list;
    }

    public boolean eG(int n2) {
        wy_2 wy_22 = new wy_2(n2);
        wy_22.q(this.dVt.get(n2));
        this.dVu.add(wy_22);
        return true;
    }
}

