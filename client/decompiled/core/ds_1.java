/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from dS
 */
public class ds_1
extends ZT {
    private static final acl_0 aU = new ym_0(new atK());
    private ack_1 nu;
    private long nv;
    public aea_0 nw = new atC(this, 8);

    public ds_1 gG() {
        ds_1 ds_12;
        try {
            ds_12 = (ds_1)aU.adr();
            ds_12.uG = aU;
        }
        catch (Exception exception) {
            ds_12 = new ds_1();
            ds_12.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        ds_12.nv = this.nv;
        ds_12.nu = this.nu;
        return ds_12;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        yl_1 yl_12 = ame_1.aWP().eN(this.r);
        if ((this.bWl == null || !this.bWl.PR() && !this.bWl.PT()) && yl_12 != null) {
            this.nu = yl_12.a(new akh_0(this.nv, this.bWn.getX(), this.bWn.getY(), this.bWn.wk(), this.bdv, this.bWl));
            this.b(xb_22, bl2);
            this.bdv.gX().f(this.nu);
            this.bWl.PJ().o(this);
        } else {
            this.aoy();
        }
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
        this.r = (int)((xj_0)this.bWj).iY(0);
        if (this.bdv == null) {
            a.error((Object)"Pas de contexte associ\u00e9 \u00e0 un SetEffectArea.");
        } else if (this.bdv.gW() == null) {
            a.error((Object)"Pas d'effectUserInformationProvider associ\u00e9 \u00e0 un SetEffectArea.");
        } else {
            this.nv = this.bdv.gW().al((byte)1);
        }
    }

    public void aK() {
        if (this.nu != null) {
            this.bdv.gX().g(this.nu);
        }
        super.aK();
    }

    public boolean aH() {
        return false;
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

    public boolean gI() {
        return true;
    }

    static /* synthetic */ long a(ds_1 ds_12) {
        return ds_12.nv;
    }

    static /* synthetic */ long a(ds_1 ds_12, long l2) {
        ds_12.nv = l2;
        return ds_12.nv;
    }

    static /* synthetic */ kc_2 a(ds_1 ds_12, kc_2 kc_22) {
        ds_12.bWm = kc_22;
        return ds_12.bWm;
    }
}

