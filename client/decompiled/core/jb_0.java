/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;

/*
 * Renamed from JB
 */
class jb_0
implements ov_1 {
    final /* synthetic */ ana_0 yj;

    jb_0(ana_0 ana_02) {
        this.yj = ana_02;
    }

    public boolean a(ke ke2) {
        abd_1 abd_12 = (abd_1)ke2;
        if (!ana_0.f(this.yj)) {
            return false;
        }
        ana_0.a(this.yj, true);
        int n2 = abd_12.p(this.yj) - ana_0.g((ana_0)this.yj).x;
        int n3 = abd_12.q(this.yj) - ana_0.g((ana_0)this.yj).y;
        int n4 = this.yj.cwV + n2;
        int n5 = this.yj.cwW + n3;
        ana_0.a(this.yj, n2);
        ana_0.b(this.yj, n3);
        this.yj.setDeltaX(n4);
        this.yj.setDeltaY(n5);
        ana_0.a(this.yj, new Point(abd_12.p(this.yj), abd_12.q(this.yj)));
        return false;
    }
}

