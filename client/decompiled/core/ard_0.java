/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from arD
 */
public class ard_0
extends tj_0 {
    private int yV;
    private int yW;
    private int yX;
    private int yY;

    public ard_0(int n2, int n3, int n4, int n5, adg_2 adg_22, int n6, int n7, ys ys2) {
        super(null, null, adg_22, n6, n7, ys2);
        this.yV = n2;
        this.yW = n3;
        this.yX = n4;
        this.yY = n5;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA != null) {
            int n3 = (int)this.amA.b(this.yV, this.yX, this.IP, this.wg);
            int n4 = (int)this.amA.b(this.yW, this.yY, this.IP, this.wg);
            this.getWidget().setPosition(n3, n4, true);
        }
        return true;
    }

    public void ly() {
        this.getWidget().setPosition(this.yX, this.yY, true);
        super.ly();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[PositionTween] (").append(this.yV).append(", ").append(this.yW).append(") -> (").append(this.yX).append(", ").append(this.yY).append(")");
        return stringBuilder.toString();
    }
}

