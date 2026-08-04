/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aCq
 */
public final class acq_0
extends jy_2 {
    public final jy_2 B;
    public final jy_2 dun;
    public final jy_2 ail;

    public acq_0(lc_0 lc_02, jy_2 jy_22, jy_2 jy_23, jy_2 jy_24) {
        super(lc_02);
        this.B = jy_22;
        this.dun = jy_23;
        this.ail = jy_24;
    }

    public String toString() {
        return this.B.toString() + " ? " + this.dun.toString() + " : " + this.ail.toString();
    }

    public void a(Ax ax) {
        ax.d(this);
    }

    public void a(EO eO) {
        eO.d(this);
    }
}

