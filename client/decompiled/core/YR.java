/*
 * Decompiled with CFR 0.152.
 */
public class YR
extends yg_1 {
    private int cbJ = 0;
    private int aoq = 0;

    public YR(qs_2 qs_22) {
        super(qs_22);
    }

    public YR(qs_2 qs_22, float f, float f2) {
        super(qs_22, f, f2);
    }

    public final int Ge() {
        return this.cbJ;
    }

    public final int amZ() {
        return this.aoq;
    }

    public final void bf(int n2, int n3) {
        this.cbJ = n2;
        this.aoq = n3;
    }

    public final float[] a(xw_0 xw_02) {
        return qi_1.vV().a(xw_02);
    }

    public final int zU() {
        Du du = this.Fx();
        if (du instanceof xw_0) {
            xw_0 xw_02 = (xw_0)((Object)du);
            return xw_02.Ge();
        }
        return this.Ge();
    }
}

