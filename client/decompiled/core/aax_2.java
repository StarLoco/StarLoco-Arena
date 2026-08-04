/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from aAx
 */
public class aax_2 {
    final jh_1 fm;
    List Pa;
    int CU;

    public aax_2(jh_1 jh_12) {
        this.fm = jh_12;
    }

    public void g(List list) {
        this.Pa = list;
        this.CU = 0;
        while (this.CU < this.Pa.size()) {
            xg_0 xg_02 = (xg_0)this.Pa.get(this.CU);
            if (xg_02 instanceof auk_0) {
                this.fm.a((auk_0)xg_02);
                this.fm.Vy().a(xg_02);
            }
            if (xg_02 instanceof AJ) {
                this.fm.Vy().a(xg_02);
                this.fm.a((AJ)xg_02);
            }
            if (xg_02 instanceof bi_0) {
                this.fm.Vy().a(xg_02);
                this.fm.a((bi_0)xg_02);
            }
            ++this.CU;
        }
    }

    public void h(List list) {
        this.Pa.addAll(this.CU + 2, list);
    }
}

