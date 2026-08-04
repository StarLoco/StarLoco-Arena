/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

class aJn
implements ov_1 {
    final /* synthetic */ YN cHP;

    aJn(YN yN) {
        this.cHP = yN;
    }

    public boolean a(ke ke2) {
        if (ke2.oF() != this.cHP) {
            return false;
        }
        Point point = YN.a(this.cHP, (abd_1)ke2);
        pf_0 pf_02 = this.cHP.getTextBuilder().ai(point.x, -point.y);
        aFH aFH2 = (aFH)pf_02.getFirst();
        sy_0 sy_02 = (sy_0)((Object)pf_02.acl());
        if (aFH2 != null) {
            int n2 = 0;
            switch (sy_02) {
                case akc: 
                case akd: {
                    n2 = 0;
                    break;
                }
                case ake: {
                    n2 = aFH2.a(this.cHP.getTextBuilder().mH(), point.x - aFH2.getX() - aFH2.aSV().getX());
                    break;
                }
                case akf: 
                case akg: {
                    n2 = aFH2.CX();
                }
            }
            this.cHP.c(aFH2, n2);
        }
        return true;
    }
}

