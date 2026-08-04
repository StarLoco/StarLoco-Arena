/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from VX
 */
public abstract class vx_1
extends aap_0 {
    private final zm_1 bTJ = new zm_1();
    private final lb_0 bTK = new lb_0();

    public final void a(aeb_1[] aeb_1Array) {
        for (aeb_1 aeb_12 : aeb_1Array) {
            this.bTJ.b(aeb_12.auq(), aeb_12.aur());
        }
    }

    public final void a(int n2, short s, int n3, byte by, int n4) {
        this.bTK.c(n2, new pa_1(s, n3, by, n4, null));
    }

    public final Cs iv(int n2) {
        pa_1 pa_12 = (pa_1)this.bTK.get(n2);
        if (pa_12 == null) {
            a.error((Object)("Aucune d\u00e9finition pour la vue de viewModelId=" + n2));
            return null;
        }
        short s = pa_1.a(pa_12);
        Cs cs = (Cs)this.bTJ.an(s);
        if (cs == null) {
            a.error((Object)("Aucune factory d'enregistr\u00e9e pour le viewTypeId=" + s));
        }
        return cs;
    }

    public final axu_0 iw(int n2) {
        Cs cs = this.iv(n2);
        if (cs == null) {
            return null;
        }
        axu_0 axu_02 = (axu_0)cs.h();
        pa_1 pa_12 = (pa_1)this.bTK.get(n2);
        if (pa_12 == null) {
            return null;
        }
        axu_02.dE(n2);
        axu_02.aL(pa_12.aaZ);
        axu_02.B(pa_12.aba);
        axu_02.ay(pa_12.aba == 0);
        axu_02.setColor(pa_12.abb);
        return axu_02;
    }
}

