/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from atV
 */
public abstract class atv_0
extends xs_1 {
    private xs_1 cVr;
    private xs_1 cVs;

    public atv_0(xs_1 xs_12, xs_1 xs_13) {
        if (xs_12 == null || xs_13 == null) {
            xs_1.Dm().error((Object)("Probl\u00e8me dans un " + this.getClass().getName() + " : un des Spring est null"));
        }
        this.cVr = xs_12 != null ? xs_12 : atv_0.iU(0);
        this.cVs = xs_13 != null ? xs_13 : atv_0.iU(0);
    }

    public int getValue() {
        return this.op(this.cVr.getValue(), this.cVs.getValue());
    }

    public void setValue(int n2) {
        super.setValue(n2);
        this.cVr.setValue(n2);
        this.cVs.setValue(n2);
    }

    public abstract int op(int var1, int var2);

    public void j() {
        super.j();
        this.cVr = null;
        this.cVs = null;
    }
}

