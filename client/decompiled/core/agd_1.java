/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;

/*
 * Renamed from agD
 */
public class agd_1
extends jl_2 {
    private static agd_1 cuC = new agd_1();

    private agd_1() {
        this.Au = new ayj();
    }

    public static agd_1 awz() {
        return cuC;
    }

    protected Pi mi() {
        return null;
    }

    protected void f(int n2, int n3, short s) {
        aso_0 aso_02 = new aso_0();
        aso_02.j(this.bN.getId());
        aso_02.t(n2, n3, s);
        apN.aDK().vJ().b(aso_02);
    }

    protected String mk() {
        return null;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            // empty if block
        }
    }

    public void mj() {
        xu_2 xu_22;
        super.mj();
        if (this.bN != null && (xu_22 = (xu_2)DofusArenaClientInstance.yl().YP()) != null) {
            ((ayj)this.Au).m(this.bN);
        }
    }
}

