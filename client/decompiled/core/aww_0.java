/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from awW
 */
public class aww_0
extends ZT {
    private static final acl_0 aU = new ym_0(new app_1());
    private long nv;
    public aea_0 nw = new aps_1(this, 8);

    public aww_0 aJJ() {
        aww_0 aww_02;
        try {
            aww_02 = (aww_0)aU.adr();
            aww_02.uG = aU;
        }
        catch (Exception exception) {
            aww_02 = new aww_0();
            aww_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un SummonDouble : " + exception.getMessage()));
        }
        return aww_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl instanceof gn_0 && !this.bWl.PR() && !this.bWl.PT()) {
            this.b(xb_22, bl2);
            this.bWm = ((gn_0)this.bWl).c(this.nv, this.bWn, this.r);
            ((gn_0)this.bWm).a(((gn_0)this.bWl).LQ());
            this.bWm.a(Lr.brb).jZ(this.bWl.d(Lr.brg));
            this.bWm.a(Lr.bra).jZ(this.bWl.d(Lr.brh));
            this.bWm.a(Lr.bqU).jZ(this.bWl.d(Lr.bri));
            this.bWm.a(Lr.brd).jZ(this.bWl.d(Lr.brj));
            int n2 = (100 + this.bWl.d(Lr.brk)) * this.bWm.d(Lr.bqx) / 100;
            this.bWm.a(Lr.bqx).set(n2);
            this.bWm.a(Lr.bqx).at(n2);
            a.info((Object)("Dans le match " + ((gn_0)this.bWl).Oc() + ", nouvelle invocation " + this.bWm + " de parent " + this.bWl + "."));
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.nv = this.bdv.gW().al((byte)2);
        switch (((xj_0)this.bWj).Tb().length) {
            case 1: {
                this.r = (int)((xj_0)this.bWj).Tb()[0];
                break;
            }
            default: {
                a.error((Object)("Nombre de param\u00e8tres incorrect dans un Summon : " + ((xj_0)this.bWj).Tb().length));
                this.r = 0;
            }
        }
    }

    public boolean aH() {
        return true;
    }

    public boolean aI() {
        return false;
    }

    public boolean aJ() {
        return true;
    }

    public aea_0 gH() {
        return this.nw;
    }

    static /* synthetic */ long a(aww_0 aww_02) {
        return aww_02.nv;
    }

    static /* synthetic */ long a(aww_0 aww_02, long l2) {
        aww_02.nv = l2;
        return aww_02.nv;
    }

    static /* synthetic */ kc_2 a(aww_0 aww_02, kc_2 kc_22) {
        aww_02.bWm = kc_22;
        return aww_02.bWm;
    }
}

