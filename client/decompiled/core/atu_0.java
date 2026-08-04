/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atU
 */
public abstract class atu_0
extends alb_0 {
    private aim_2 avV = null;

    protected atu_0(lc_0 lc_02) {
        super(lc_02);
    }

    public void a(aim_2 aim_22) {
        if (this.avV != null && aim_22 != this.avV) {
            throw new aHY("Enclosing scope already set for type \"" + this.toString() + "\" at " + this.aP());
        }
        this.avV = aim_22;
    }

    public aim_2 Dw() {
        return this.avV;
    }

    public atu_0 aAo() {
        return this;
    }

    public abstract void a(vb_0 var1);

    static aim_2 b(atu_0 atu_02) {
        return atu_02.avV;
    }

    static aim_2 a(atu_0 atu_02, aim_2 aim_22) {
        atu_02.avV = aim_22;
        return atu_02.avV;
    }
}

