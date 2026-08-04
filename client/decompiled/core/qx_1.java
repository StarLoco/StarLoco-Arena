/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

/*
 * Renamed from Qx
 */
class qx_1
implements ov_1 {
    final /* synthetic */ ahr_2 za;

    qx_1(ahr_2 ahr_22) {
        this.za = ahr_22;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        int n2 = abd_12.p(this.za) - ahr_2.f((ahr_2)this.za).x;
        int n3 = abd_12.q(this.za) - ahr_2.f((ahr_2)this.za).y;
        if (ahr_2.g(this.za) || ej_0.o(n2) + ej_0.o(n3) > 500.0f) {
            ahr_2.a(this.za, true);
            int n4 = this.za.cwV + n2;
            int n5 = this.za.cwW + n3;
            ahr_2.a(this.za, n4, n5);
            ahr_2.a(this.za, new Point(abd_12.p(this.za), abd_12.q(this.za)));
        }
        return false;
    }
}

