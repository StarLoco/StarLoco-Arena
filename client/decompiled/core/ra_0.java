/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Ra
 */
public final class ra_0
extends aOE
implements TK {
    public final lo_2 bIo;

    public ra_0(lc_0 lc_02, boolean bl2, lo_2 lo_22) {
        super(lc_02, bl2);
        this.bIo = lo_22;
        this.bIo.a(this);
    }

    public String toString() {
        return this.emC ? "static " + this.bIo : this.bIo.toString();
    }

    public void a(ea_2 ea_22) {
        ea_22.a(this);
    }

    public void a(awv_0 awv_02) {
        awv_02.a(this);
    }

    public fb_2 cL(String string) {
        return this.bIo.cL(string);
    }
}

