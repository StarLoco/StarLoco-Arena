/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ud
 */
public class ud_2
extends ZT {
    private static final acl_0 aU = new ym_0(new avU());
    private long nv;
    public aea_0 nw = new avs_0(this, 8);

    public ud_2 agD() {
        ud_2 ud_22;
        try {
            ud_22 = (ud_2)aU.adr();
            ud_22.uG = aU;
        }
        catch (Exception exception) {
            ud_22 = new ud_2();
            ud_22.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un SummonDouble : " + exception.getMessage()));
        }
        return ud_22;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl instanceof gn_0 && !this.bWl.PR() && !this.bWl.PT()) {
            this.b(xb_22, bl2);
            this.bWm = ((gn_0)this.bWl).b(this.nv, this.bWn);
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

    static /* synthetic */ long a(ud_2 ud_22) {
        return ud_22.nv;
    }

    static /* synthetic */ long a(ud_2 ud_22, long l2) {
        ud_22.nv = l2;
        return ud_22.nv;
    }

    static /* synthetic */ kc_2 a(ud_2 ud_22, kc_2 kc_22) {
        ud_22.bWm = kc_22;
        return ud_22.bWm;
    }
}

