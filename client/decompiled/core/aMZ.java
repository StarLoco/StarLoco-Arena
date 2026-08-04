/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

class aMZ
implements ov_1 {
    final /* synthetic */ asq dYU;

    aMZ(asq asq2) {
        this.dYU = asq2;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        if (!asq.a(this.dYU)) {
            asq.a(this.dYU, new Point(abd_12.p(asq.b(this.dYU).getContainer()), abd_12.q(asq.b(this.dYU).getContainer())));
        }
        int n2 = abd_12.p(asq.b(this.dYU).getContainer()) - asq.c((asq)this.dYU).x;
        int n3 = abd_12.q(asq.b(this.dYU).getContainer()) - asq.c((asq)this.dYU).y;
        int n4 = asq.b(this.dYU).getX() + asq.b(this.dYU).getWidth();
        int n5 = asq.b(this.dYU).getY() + asq.b(this.dYU).getHeight();
        int n6 = asq.b(this.dYU).getWidth();
        int n7 = asq.b(this.dYU).getHeight();
        int n8 = asq.b(this.dYU).getX();
        int n9 = asq.b(this.dYU).getY();
        switch (asq.d(this.dYU)) {
            case aJY: {
                n6 = Math.min(this.dYU.setCheckedWidth(asq.b(this.dYU).getWidth() + n2), asq.e(this.dYU).getWidth() - asq.b(this.dYU).getX());
                break;
            }
            case aJU: {
                n7 = Math.min(this.dYU.setCheckedHeight(asq.b(this.dYU).getHeight() + n3), asq.e(this.dYU).getHeight() - asq.b(this.dYU).getY());
                break;
            }
            case aKa: {
                n7 = Math.min(this.dYU.setCheckedHeight(asq.b(this.dYU).getHeight() - n3), n5);
                n9 = n5 - n7;
                break;
            }
            case aJV: {
                n6 = Math.min(this.dYU.setCheckedWidth(asq.b(this.dYU).getWidth() + n2), asq.e(this.dYU).getWidth() - asq.b(this.dYU).getX());
                n7 = Math.min(this.dYU.setCheckedHeight(asq.b(this.dYU).getHeight() + n3), asq.e(this.dYU).getHeight() - asq.b(this.dYU).getY());
                break;
            }
            case aJT: {
                n7 = Math.min(this.dYU.setCheckedHeight(asq.b(this.dYU).getHeight() + n3), asq.e(this.dYU).getHeight() - asq.b(this.dYU).getY());
                n6 = Math.min(this.dYU.setCheckedWidth(asq.b(this.dYU).getWidth() - n2), n4);
                n8 = n4 - n6;
                break;
            }
            case aJZ: {
                n6 = Math.min(this.dYU.setCheckedWidth(asq.b(this.dYU).getWidth() - n2), n4);
                n8 = n4 - n6;
                n7 = Math.min(this.dYU.setCheckedHeight(asq.b(this.dYU).getHeight() - n3), n5);
                n9 = n5 - n7;
                break;
            }
            case aKb: {
                n7 = Math.min(this.dYU.setCheckedHeight(asq.b(this.dYU).getHeight() - n3), n5);
                n9 = n5 - n7;
                n6 = Math.min(this.dYU.setCheckedWidth(asq.b(this.dYU).getWidth() + n2), asq.e(this.dYU).getWidth() - asq.b(this.dYU).getX());
                break;
            }
            case aJW: {
                n6 = Math.min(this.dYU.setCheckedWidth(asq.b(this.dYU).getWidth() - n2), n4);
                n8 = n4 - n6;
                break;
            }
        }
        asq.b(this.dYU).setSize(n6, n7);
        asq.b(this.dYU).setPosition(n8, n9);
        asq.a(this.dYU, new Point(abd_12.p(asq.b(this.dYU).getContainer()), abd_12.q(asq.b(this.dYU).getContainer())));
        if (asq.b(this.dYU).getContainer() != null) {
            asq.b(this.dYU).getContainer().Am();
        }
        asq.a(this.dYU, true);
        return false;
    }
}

