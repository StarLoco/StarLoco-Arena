/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

/*
 * Renamed from aLX
 */
public class alx_2
extends jl_2 {
    private static alx_2 dXj = new alx_2();
    private yp_2 aKF = null;

    private alx_2() {
        this.Au = new Cc();
    }

    public static alx_2 aWN() {
        return dXj;
    }

    public void e(yp_2 yp_22) {
        this.aKF = yp_22;
    }

    public yp_2 aWO() {
        return this.aKF;
    }

    protected Pi mi() {
        return this.aKF;
    }

    protected void f(int n2, int n3, short s) {
        mc_2 mc_22 = new mc_2();
        mc_22.j(this.bN.getId());
        mc_22.I(this.aKF.getId());
        mc_22.h(n2, n3, s);
        apN.aDK().vJ().b(mc_22);
    }

    protected String mk() {
        if (this.aKF != null) {
            return (String)this.aKF.getFieldValue("iconUrl");
        }
        return null;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void mj() {
        xu_2 xu_22;
        super.mj();
        if (this.aKF != null && this.bN != null && (xu_22 = (xu_2)DofusArenaClientInstance.yl().YP()) != null) {
            ((Cc)this.Au).a(this.aKF, this.bN);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        this.aKF = null;
        super.b(fh_22, bl2);
    }

    public boolean aNr() {
        return this.aKF != null;
    }
}

