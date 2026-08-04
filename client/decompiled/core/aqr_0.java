/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aqR
 */
class aqr_0
implements ja_1 {
    final /* synthetic */ ahV cON;
    final /* synthetic */ ft_1 cOK;

    aqr_0(ft_1 ft_12, ahV ahV2) {
        this.cOK = ft_12;
        this.cON = ahV2;
    }

    public void b(int n2) {
        if (n2 == 8) {
            apN.aDK().a(do_2.Mm());
            po_0.abV().abW();
            bl_1 bl_12 = new bl_1();
            bl_12.d(this.cON.Y());
            bl_12.C(this.cON.axA());
            apN.aDK().vJ().b(bl_12);
            ft_1.a(this.cOK, add_1.aOG().a(aon_0.aYc().getString("fight.creation.waitingForOpponentCoach"), 1156L, 102, 1));
            ft_1.a(this.cOK).a(new ane_1(this));
        } else if (ft_1.a(this.cOK) != null) {
            ft_1.a(this.cOK, null);
            mz_0 mz_02 = new mz_0();
            mz_02.d(this.cON.Y());
            apN.aDK().vJ().b(mz_02);
            pw_1.acG().cr(this.cON.Y());
            apN.aDK().a(ft_1.jr());
        }
    }
}

