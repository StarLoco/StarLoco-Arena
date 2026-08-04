/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.awt.Rectangle;

/*
 * Renamed from Ho
 */
public class ho_2
extends tj_0 {
    private final boolean beg;

    public ho_2(Rectangle rectangle, Rectangle rectangle2, adg_2 adg_22, boolean bl2, int n2, int n3, ys ys2) {
        super(rectangle, rectangle2, adg_22, n2, n3, ys2);
        if (rectangle == null) {
            this.aJ(new Rectangle(new Point(0, 0), adg_22.getSize()));
        }
        this.beg = bl2;
    }

    public boolean aS(int n2) {
        if (!super.aS(n2)) {
            return false;
        }
        if (this.amA == null) {
            return true;
        }
        Rectangle rectangle = (Rectangle)this.eoN;
        Rectangle rectangle2 = (Rectangle)this.eoO;
        int n3 = (int)this.amA.b(rectangle.x, rectangle2.x, this.IP, this.wg);
        int n4 = (int)this.amA.b(rectangle.y, rectangle2.y, this.IP, this.wg);
        int n5 = (int)this.amA.b(rectangle.width, rectangle2.width, this.IP, this.wg);
        int n6 = (int)this.amA.b(rectangle.height, rectangle2.height, this.IP, this.wg);
        this.getWidget().setScissor(new Rectangle(n3, n4, n5, n6));
        return true;
    }

    public void ly() {
        if (this.beg) {
            this.getWidget().setScissor(null);
        } else {
            this.getWidget().setScissor((Rectangle)this.eoO);
        }
        super.ly();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[").append(this.getClass().getSimpleName()).append("]").append(this.eoN).append(" -> ").append(this.eoO);
        return stringBuilder.toString();
    }
}

