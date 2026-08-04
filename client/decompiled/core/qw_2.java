/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Qw
 */
class qw_2
implements ov_1 {
    final /* synthetic */ ahr_2 za;

    qw_2(ahr_2 ahr_22) {
        this.za = ahr_22;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        ahr_2.a(this.za, (int)((float)(abd_12.p(this.za) - this.za.cwV) / ((float)this.za.coi.getCellWidth() * ahr_2.h(this.za))));
        ahr_2.b(this.za, (int)((float)(abd_12.q(this.za) - this.za.cwW) / ((float)this.za.coi.getCellHeight() * ahr_2.h(this.za))));
        if (ahr_2.i(this.za) == null || ahr_2.i(this.za).aLe() != ahr_2.j(this.za) || ahr_2.i(this.za).aLf() != ahr_2.k(this.za)) {
            aGJ aGJ2;
            if (ahr_2.i(this.za) != null) {
                ahr_2.e(this.za).a(ahr_2.i(this.za).aLz(), ahr_2.i(this.za).getPixmap());
                aGJ2 = aGJ.a((abd_1)ke2, this.za, qe_1.bFk, ahr_2.i(this.za));
                this.za.f(aGJ2);
            }
            ahr_2.a(this.za, ahr_2.b(this.za, ahr_2.j(this.za), ahr_2.k(this.za)));
            if (ahr_2.i(this.za) != null) {
                ahr_2.e(this.za).a(ahr_2.i(this.za).aLz(), ahr_2.i(this.za).aLx());
                aGJ2 = aGJ.a((abd_1)ke2, this.za, qe_1.bFl, ahr_2.i(this.za));
                this.za.f(aGJ2);
            }
        }
        return false;
    }
}

