/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from oB
 */
class ob_2
implements apx {
    final /* synthetic */ Lz aax;

    ob_2(Lz lz) {
        this.aax = lz;
    }

    public boolean a(ee_2 ee_22) {
        if (ee_22.getId() > 1000L && ee_22.Nx() > 0) {
            short[] sArray;
            Lz.Dm().info((Object)(ee_22.getName() + " : "));
            Lz.dT().info((Object)("xp : " + ee_22.Nx()));
            Lz.kF().info((Object)("moral : " + ee_22.NA()));
            Lz.sP().info((Object)("\u00e9tat : " + ee_22.NB() + " (0 en forme, 2 mort)"));
            Lz.XE().info((Object)("fatigue : " + ee_22.Nz()));
            Lz.XF().info((Object)("blessures : " + ee_22.kh().size()));
            for (short s : sArray = ee_22.kh().Gj()) {
                aiz_2 aiz_22 = bf_1.df().g(s);
                Lz.XG().info((Object)asf_0.b(aiz_22.ayX()));
                Lz.XH().info((Object)aon_0.aYc().a(40, aiz_22.tI(), new Object[0]));
                Lz.XI().info((Object)("dur\u00e9e : " + ee_22.kh().bp(s)));
            }
        }
        return true;
    }
}

