/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Qv
 */
class qv_1
implements ov_1 {
    final /* synthetic */ ahr_2 za;

    qv_1(ahr_2 ahr_22) {
        this.za = ahr_22;
    }

    public boolean a(ke ke2) {
        if (ahr_2.l(this.za)) {
            abd_1 abd_12 = (abd_1)ke2;
            if (!ahr_2.g(this.za)) {
                if (ahr_2.i(this.za) != null) {
                    if (abd_12.getButton() == 1) {
                        // empty if block
                    }
                    aGJ aGJ2 = aGJ.a(abd_12, this.za, qe_1.bFi, ahr_2.i(this.za));
                    this.za.f(aGJ2);
                }
                if (abd_12.getButton() == 3) {
                    if (ahr_2.h(this.za) == 1.0f) {
                        this.za.setZoomToFullView();
                    } else {
                        this.za.setZoomToOne(ahr_2.j(this.za), ahr_2.k(this.za));
                    }
                }
            }
        }
        return false;
    }
}

