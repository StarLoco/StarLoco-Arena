/*
 * Decompiled with CFR 0.152.
 */
class aqQ
implements ja_1 {
    final /* synthetic */ bx_1 cOM;
    final /* synthetic */ ft_1 cOK;

    aqQ(ft_1 ft_12, bx_1 bx_12) {
        this.cOK = ft_12;
        this.cOM = bx_12;
    }

    public void b(int n2) {
        if (n2 != 8 && n2 != 16) {
            ft_1.a.error((Object)("Cas impossible \u00e0 traiter : type \u00e9gal \u00e0 " + n2 + "."));
        } else {
            Pg pg = new Pg();
            pg.cl(this.cOM.zI());
            pg.aQ(this.cOM.IQ());
            pg.a(this.cOM.zK());
            pg.cb(n2 == 8);
            apN.aDK().vJ().b(pg);
            if (n2 == 8) {
                po_0.abV().abW();
                lg_0.pU().C(this.cOM.IQ());
                apN.aDK().a(lg_0.pU());
            }
        }
    }
}

