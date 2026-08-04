/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from eZ
 */
public class ez_0
extends ZT {
    private static final acl_0 aU = new ym_0(new aka_1());

    public ez_0 hY() {
        ez_0 ez_02;
        try {
            ez_02 = (ez_0)aU.adr();
            ez_02.uG = aU;
        }
        catch (Exception exception) {
            ez_02 = new ez_0();
            ez_02.uG = null;
            a.error((Object)("Erreur lors d'un checkOut sur un ArenaRunningEffect : " + exception.getMessage()));
        }
        return ez_02;
    }

    public void a(xb_2 xb_22, boolean bl2) {
        aby_2 aby_22 = new aby_2(this.bWm.gn(), this.bWm.go(), this.bWm.gp(), this.bWn.getX(), this.bWn.getY(), this.bWn.wk());
        this.bWm.b(aby_22.aqB());
        super.a(xb_22, bl2);
    }

    public void a(xb_2 xb_22) {
    }

    public boolean aH() {
        return false;
    }

    public boolean aI() {
        return true;
    }

    public boolean aJ() {
        return true;
    }
}

