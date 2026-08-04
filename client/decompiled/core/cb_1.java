/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Cb
 */
public final class cb_1
extends anw {
    public final alb_0 aKA;
    public final jz_0 aKB;

    public cb_1(lc_0 lc_02, alb_0 alb_02, jz_0 jz_02) {
        super(lc_02);
        this.aKA = alb_02;
        this.aKB = jz_02;
    }

    public String toString() {
        return this.aKA.toString() + '.' + this.aKB.getName();
    }

    public void a(Ax ax) {
        ax.g(this);
    }

    public void a(EO eO) {
        eO.g(this);
    }

    public void a(ale_0 ale_02) {
        ale_02.g(this);
    }
}

