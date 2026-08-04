/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from HY
 */
public class hy_1
extends ZT {
    private static final acl_0 aU = new ym_0(new apj_0());
    private long nv;
    public aea_0 nw = new api_0(this, 8);

    public hy_1 TF() {
        hy_1 hy_12;
        try {
            hy_12 = (hy_1)aU.adr();
            hy_12.uG = aU;
        }
        catch (Exception exception) {
            hy_12 = new hy_1();
            hy_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return hy_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        if (this.bWl instanceof gn_0 && !this.bWl.PR() && !this.bWl.PT()) {
            this.b(xb_22, bl2);
            gn_0 gn_02 = (gn_0)this.bWl;
            gn_0 gn_03 = gn_02.d(this.nv, this.bWn, this.r);
            gn_03.a(gn_02.LQ());
            gn_03.a(Lr.brb).jZ(gn_02.d(Lr.brg));
            gn_03.a(Lr.bra).jZ(gn_02.d(Lr.brh));
            gn_03.a(Lr.bqU).jZ(gn_02.d(Lr.bri));
            gn_03.a(Lr.brd).jZ(gn_02.d(Lr.brj));
            int n2 = (100 + gn_02.d(Lr.brk)) * gn_03.d(Lr.bqx) / 100;
            gn_03.a(Lr.bqx).set(n2);
            gn_03.a(Lr.bqx).at(n2);
            a.info((Object)("Dans le match " + gn_03.Oc() + ", nouvelle invocation " + gn_03 + " de parent " + this.bWl + "."));
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

    static /* synthetic */ long a(hy_1 hy_12) {
        return hy_12.nv;
    }

    static /* synthetic */ long a(hy_1 hy_12, long l2) {
        hy_12.nv = l2;
        return hy_12.nv;
    }

    static /* synthetic */ kc_2 a(hy_1 hy_12, kc_2 kc_22) {
        hy_12.bWm = kc_22;
        return hy_12.bWm;
    }
}

