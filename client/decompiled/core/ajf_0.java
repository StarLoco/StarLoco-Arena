/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ajF
 */
public final class ajf_0
extends cg_2
implements rp_1 {
    public ajf_0(lc_0 lc_02, String string, short s, String string2, atu_0[] atu_0Array) {
        super(lc_02, string, s, string2, atu_0Array);
    }

    public String getClassName() {
        aao_0 aao_02 = (aao_0)this.Dw();
        return aao_02.getClassName() + '$' + this.getName();
    }

    public void a(el_1 el_12) {
        this.a((aim_2)el_12);
    }

    public el_1 bV() {
        return (el_1)this.Dw();
    }

    public boolean isStatic() {
        return (this.hQ() & 8) != 0;
    }

    public void a(qo_1 qo_12) {
        qo_12.a(this);
    }

    public void a(ea_2 ea_22) {
        ea_22.a(this);
    }
}

