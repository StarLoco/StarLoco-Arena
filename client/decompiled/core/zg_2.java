/*
 * Decompiled with CFR 0.152.
 */
import java.util.Comparator;

/*
 * Renamed from zg
 */
public class zg_2
extends ahl_1 {
    protected Comparator aEv;

    public zg_2(Comparator comparator) {
        this.aEv = comparator;
    }

    protected void b(we_2 we_22) {
        we_22.CA().clear();
        we_22.CA().i(we_22.CB().adg());
        this.a(we_22.CA(), this.aEv);
    }

    protected void c(we_2 we_22) {
    }

    protected void d(we_2 we_22) {
    }

    protected void a(we_2 we_22, long l2) {
        we_22.CB().ct(l2);
    }

    protected void b(we_2 we_22, long l2) {
        int n2 = we_22.CB().cw(l2);
        if (n2 != -1) {
            we_22.CB().remove(n2);
        }
    }
}

