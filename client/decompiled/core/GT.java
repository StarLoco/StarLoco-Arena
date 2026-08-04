/*
 * Decompiled with CFR 0.152.
 */
public final class GT
extends anw {
    public final atu_0 bcv;
    public final String fieldName;
    jy_2 aGv = null;

    public GT(lc_0 lc_02, atu_0 atu_02, String string) {
        super(lc_02);
        this.bcv = atu_02;
        this.fieldName = string;
    }

    public String toString() {
        return (this.bcv == null ? "super." : this.bcv.toString() + ".super.") + this.fieldName;
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

