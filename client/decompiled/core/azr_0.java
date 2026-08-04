/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from azr
 */
class azr_0
implements ov_1 {
    final /* synthetic */ ajc cfS;

    azr_0(ajc ajc2) {
        this.cfS = ajc2;
    }

    public boolean a(ke ke2) {
        aqq_0 aqq_02 = (aqq_0)ke2.oF();
        int n2 = ajc.d(this.cfS).indexOf(aqq_02);
        if (n2 >= 0 && n2 < this.cfS.Hy.size()) {
            vP vP2 = (vP)this.cfS.Hy.get(n2);
            aGJ aGJ2 = aGJ.a((abd_1)ke2, this.cfS, qe_1.bFi, vP2);
            this.cfS.f(aGJ2);
        }
        return false;
    }
}

