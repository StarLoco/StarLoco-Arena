/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from abd
 */
class abd_2
implements ja_1 {
    final /* synthetic */ KI sX;
    final /* synthetic */ String chn;
    final /* synthetic */ vd_2 cho;
    final /* synthetic */ vd_2 chp;

    abd_2(KI kI, String string, vd_2 vd_22, vd_2 vd_23) {
        this.sX = kI;
        this.chn = string;
        this.cho = vd_22;
        this.chp = vd_23;
    }

    public void b(int n2) {
        if (n2 == 8) {
            Nr nr = new Nr();
            nr.g(this.sX.getId());
            nr.setName(this.chn);
            nr.gV(this.cho.aRd());
            nr.aH(this.cho.aRe());
            nr.aM(this.cho.aRe());
            apN.aDK().vJ().b(nr);
            this.cho.lb(this.chn);
            this.chp.gV(this.cho.aRd());
            this.chp.aH(this.cho.aRe());
            this.chp.lb(this.cho.aRf());
        } else if (n2 == 16) {
            this.cho.gV(this.chp.aRd());
            this.cho.aH(this.chp.aRe());
            this.cho.lb(this.chp.aRf());
        }
    }
}

