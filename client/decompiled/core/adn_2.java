/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from adn
 */
class adn_2
implements adx_1 {
    final /* synthetic */ long sk;
    final /* synthetic */ JX iS;
    final /* synthetic */ String iT;
    final /* synthetic */ jJ[] iU;
    final /* synthetic */ ld_1 cms;

    adn_2(ld_1 ld_12, long l2, JX jX, String string, jJ[] jJArray) {
        this.cms = ld_12;
        this.sk = l2;
        this.iS = jX;
        this.iT = string;
        this.iU = jJArray;
    }

    public void n(mT mT2) {
        if (mT2.getId() == this.sk) {
            this.iS.a(this.iT, this.iU, new amd_0[0]);
        }
    }
}

