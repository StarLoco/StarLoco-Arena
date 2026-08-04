/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aOS
 */
class aos_2
implements ja_1 {
    final /* synthetic */ sw_1 emS;
    final /* synthetic */ hu_2 cDw;

    aos_2(hu_2 hu_22, sw_1 sw_12) {
        this.cDw = hu_22;
        this.emS = sw_12;
    }

    public void b(int n2) {
        if (n2 == 8) {
            axp_0 axp_02 = apN.aDK().vJ();
            aba_0 aba_02 = this.emS.afE();
            for (long l2 : aba_02.eJ()) {
                adY.atu().Y(aba_02.du(l2));
            }
            Object object = new aad_1();
            ((aad_1)object).aj(this.emS.afG());
            ((aad_1)object).C(this.emS.tI());
            ((aad_1)object).M(this.emS.cB());
            axp_02.b((pr_0)object);
        }
    }
}

