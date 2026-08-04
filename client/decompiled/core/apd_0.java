/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Insets;
import java.awt.Point;

/*
 * Renamed from aPd
 */
public class apd_0
extends aaH {
    public void f(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        int n10 = n2 + n8;
        int n11 = n3 + n7 + n5;
        this.aY.x(n11, n10);
        this.aY.setSize(n4, n5);
    }

    public void a(Point point, agj_1 agj_12, Insets insets) {
        this.f(point.x, point.y, agj_12.width, agj_12.height, insets.top, insets.bottom, insets.left, insets.right);
    }
}

