/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from alb
 */
public abstract class alb_0
extends aj_1 {
    public alb_0(lc_0 lc_02) {
        super(lc_02);
    }

    public atu_0 aAo() {
        return null;
    }

    public jy_2 oj() {
        return null;
    }

    public anw aAp() {
        return null;
    }

    public abstract String toString();

    public final atu_0 aAq() {
        atu_0 atu_02 = this.aAo();
        if (atu_02 == null) {
            this.j("Expression \"" + this.toString() + "\" is not a type");
        }
        return atu_02;
    }

    public final jy_2 aAr() {
        jy_2 jy_22 = this.oj();
        if (jy_22 == null) {
            this.j("Expression \"" + this.toString() + "\" is not an rvalue");
        }
        return jy_22;
    }

    public final anw aAs() {
        anw anw2 = this.aAp();
        if (anw2 == null) {
            this.j("Expression \"" + this.toString() + "\" is not an lvalue");
        }
        return anw2;
    }

    public abstract void a(Ax var1);
}

