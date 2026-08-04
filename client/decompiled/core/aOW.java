/*
 * Decompiled with CFR 0.152.
 */
class aOW
implements ja_1 {
    final /* synthetic */ zK emR;
    final /* synthetic */ mk_2 emU;
    final /* synthetic */ hu_2 cDw;

    aOW(hu_2 hu_22, zK zK2, mk_2 mk_22) {
        this.cDw = hu_22;
        this.emR = zK2;
        this.emU = mk_22;
    }

    public void b(int n2) {
        if (n2 == 8) {
            so_0 so_02;
            int n3;
            long[] lArray = this.emR.afE().eJ();
            for (n3 = 0; n3 < lArray.length; ++n3) {
                so_02 = new ot_2();
                ((ot_2)so_02).j(lArray[n3]);
                ((ot_2)so_02).e(bs_0.IF().bd(lArray[n3]));
                apN.aDK().vJ().b(so_02);
            }
            for (n3 = 0; n3 < this.emU.rt().length; ++n3) {
                so_02 = new aNb();
                ((aNb)so_02).fn(true);
                ((aNb)so_02).e(this.emR.tI());
                ((aNb)so_02).h(this.emU.rt()[n3]);
                apN.aDK().vJ().b(so_02);
            }
            add_1.aOG().kO("teamLoadDialog");
            add_1.aOG().a(aon_0.aYc().getString("fileLoaded"), 1091L, 102, 1);
        }
    }
}

