/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from YD
 */
public class yd_0
extends tj_0 {
    private float cbj;
    private float cbk;

    public yd_0(float f, float f2, rf_0 rf_02, int n2, int n3, ys ys2) {
        super(null, null, rf_02, n2, n3, ys2);
        this.cbj = f;
        this.cbk = f2;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            float f = this.amA.b(this.cbj, this.cbk, this.IP, this.wg);
            ((rf_0)this.getWidget()).setOffset(f);
        }
        return true;
    }

    public void ly() {
        ((rf_0)this.getWidget()).setOffset(this.cbk);
        super.ly();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ListOffsetTween] (").append(this.cbj).append(") -> (").append(this.cbk).append(")");
        return stringBuilder.toString();
    }
}

