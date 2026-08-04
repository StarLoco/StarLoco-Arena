/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from nK
 */
public abstract class nk_2 {
    final List Pa;
    vU Pb;

    protected nk_2(vU vU2, List list) {
        this.Pb = vU2;
        this.Pa = new ArrayList(list);
        this.sH();
    }

    void sH() {
        this.Pa.remove(0);
        this.Pa.remove(this.Pa.size() - 1);
    }

    public abstract fz_1 bo(String var1);

    adr_0 a(vU vU2, String string) {
        fz_1 fz_12 = this.bo(string);
        fz_12.a(vU2);
        fz_12.a(this.Pa);
        return fz_12.Lo();
    }

    public List sI() {
        return this.Pa;
    }
}

