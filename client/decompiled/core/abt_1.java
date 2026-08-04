/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

/*
 * Renamed from aBt
 */
public class abt_1
extends jl_2 {
    private static abt_1 drr = new abt_1();
    private ve_0 drs = null;

    private abt_1() {
        this.Au = new vm_0();
    }

    public static abt_1 aNp() {
        return drr;
    }

    public void f(ve_0 ve_02) {
        this.drs = ve_02;
    }

    public ve_0 aNq() {
        return this.drs;
    }

    protected Pi mi() {
        return this.drs;
    }

    protected void f(int n2, int n3, short s) {
        sg_2 sg_22 = new sg_2();
        sg_22.j(this.bN.getId());
        sg_22.gy(this.drs.getId());
        sg_22.t(n2, n3, s);
        apN.aDK().vJ().b(sg_22);
    }

    protected String mk() {
        if (this.drs != null) {
            return (String)this.drs.getFieldValue("itemIconUrl");
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
        if (this.drs != null && this.bN != null && (xu_22 = (xu_2)DofusArenaClientInstance.yl().YP()) != null) {
            ((vm_0)this.Au).a(this.drs, this.bN);
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        this.drs = null;
        super.b(fh_22, bl2);
    }

    public boolean aNr() {
        return this.drs != null;
    }
}

