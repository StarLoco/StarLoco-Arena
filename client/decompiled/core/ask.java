/*
 * Decompiled with CFR 0.152.
 */
class ask
extends ub_0 {
    private boolean DE = true;
    final /* synthetic */ ud_1 Dg;

    private ask(ud_1 ud_12) {
        this.Dg = ud_12;
    }

    public void b(db_2 db_22) {
        if (this.DE) {
            ud_1.d(this.Dg).setScreenPosition(ud_1.d(this.Dg).getScreenX(), ud_1.d(this.Dg).getScreenY());
            nm_0 nm_02 = ud_1.d(this.Dg).getScissor(null);
            nm_02.setSize(ud_1.l(this.Dg).getSize());
            nm_02.setLocation(nm_02.getX(), (int)((double)nm_02.getY() + ud_1.s(this.Dg)));
            add_1.aOG().aON().f(nm_02);
            alj_0.aWw().h(nm_02);
            nm_0 nm_03 = alj_0.aWw().aWy();
            vo_1.aik().cv(true);
            vo_1.aik().w(nm_03.getX(), nm_03.getY(), nm_03.getWidth() + 1, nm_03.getHeight() + 1);
        } else {
            ud_1.d(this.Dg).setScreenPosition(-1, -1);
            alj_0.aWw().aWz();
            nm_0 nm_04 = alj_0.aWw().aWy();
            if (nm_04 != null) {
                vo_1.aik().cv(true);
                vo_1.aik().w(nm_04.getX(), nm_04.getY(), nm_04.getWidth() + 1, nm_04.getHeight() + 1);
            }
        }
        this.DE = !this.DE;
    }

    /* synthetic */ ask(ud_1 ud_12, dz_0 dz_02) {
        this(ud_12);
    }
}

