/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

/*
 * Renamed from jw
 */
class jw_2
implements ov_1 {
    final /* synthetic */ qs_1 AB;

    jw_2(qs_1 qs_12) {
        this.AB = qs_12;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        if (!qs_1.b(this.AB).isMovable() || qs_1.c(this.AB)) {
            return false;
        }
        if (!qs_1.a(this.AB)) {
            qs_1.a(this.AB, abd_12.p(qs_1.b(this.AB)));
            qs_1.b(this.AB, abd_12.q(qs_1.b(this.AB)));
            qs_1.a(this.AB, true);
        }
        int n2 = qs_1.b(this.AB).getX();
        int n3 = qs_1.b(this.AB).getY();
        if (qs_1.d(this.AB)) {
            n2 = abd_12.p(qs_1.b(this.AB).getContainer()) - qs_1.e(this.AB);
        }
        if (qs_1.f(this.AB)) {
            n3 = abd_12.q(qs_1.b(this.AB).getContainer()) - qs_1.g(this.AB);
        }
        if (qs_1.b(this.AB).isStickWithinRootContainer()) {
            int n4 = n2 - qs_1.b(this.AB).getX();
            int n5 = n3 - qs_1.b(this.AB).getY();
            int n6 = this.AB.getX(qs_1.h(this.AB));
            int n7 = this.AB.getY(qs_1.h(this.AB));
            int n8 = this.AB.getX(qs_1.b(this.AB));
            int n9 = this.AB.getY(qs_1.b(this.AB));
            int n10 = Math.min(50, this.AB.aLd.width);
            int n11 = Math.min(50, this.AB.aLd.height);
            if (qs_1.d(this.AB)) {
                if (n6 + n4 + this.AB.aLd.width - n10 < 0) {
                    n2 = -n8 - this.AB.aLd.width + n10;
                } else if (n6 + n4 + n10 > qs_1.h(this.AB).getWidth()) {
                    n2 = qs_1.h(this.AB).getWidth() - (n8 + n10);
                }
            }
            if (qs_1.f(this.AB)) {
                if (n7 + n5 + this.AB.aLd.height - n11 < 0) {
                    n3 = -n9 - this.AB.aLd.height + n11;
                } else if (n7 + n5 + n11 > qs_1.h(this.AB).getHeight()) {
                    n3 = qs_1.h(this.AB).getHeight() - (n9 + n11);
                }
            }
        }
        if (qs_1.b(this.AB).getStickData() != null) {
            Point point = new Point(n2, n3);
            acv_0.arH().a(qs_1.b(this.AB), qs_1.b(this.AB).getX(), qs_1.b(this.AB).getY(), n2, n3, point, ago_2.getInstance().isShiftPressed());
            n2 = point.x;
            n3 = point.y;
            if (abd_12.p(qs_1.b(this.AB).getContainer()) <= 0) {
                qs_1.b(this.AB).f(new ub_2(qs_1.b(this.AB), BT.aJW));
            } else if (abd_12.p(qs_1.b(this.AB).getContainer()) >= qs_1.b(this.AB).getContainer().getWidth()) {
                qs_1.b(this.AB).f(new ub_2(qs_1.b(this.AB), BT.aJY));
            } else if (abd_12.q(qs_1.b(this.AB).getContainer()) <= 0) {
                qs_1.b(this.AB).f(new ub_2(qs_1.b(this.AB), BT.aKa));
            } else if (abd_12.q(qs_1.b(this.AB).getContainer()) >= qs_1.b(this.AB).getContainer().getHeight()) {
                qs_1.b(this.AB).f(new ub_2(qs_1.b(this.AB), BT.aJU));
            }
        }
        qs_1.b(this.AB).setPosition(n2, n3);
        if (qs_1.b(this.AB).getContainer() != null) {
            qs_1.b(this.AB).getContainer().Am();
        }
        return false;
    }
}

