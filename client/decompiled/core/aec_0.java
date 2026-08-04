/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.text.GeometryBackground;

/*
 * Renamed from aEc
 */
public class aec_0
extends amz_2 {
    public static final int bci = -1;
    private int bcj = 0;
    private int wg = -1;
    private int dzB;

    public aec_0(ma_1 ma_12, String string) {
        super(ma_12, string);
        this.setDuration(-1);
    }

    public aec_0(ma_1 ma_12, String string, int n2) {
        super(ma_12, string);
        this.setDuration(n2);
    }

    public int getDuration() {
        return this.wg;
    }

    public void setDuration(int n2) {
        assert (n2 >= -1);
        this.wg = n2;
    }

    public boolean isAlive() {
        if (this.wg == -1) {
            return true;
        }
        return this.bcj <= this.wg + this.dzB;
    }

    public void EL() {
        this.bcj = 0;
    }

    public void bI(int n2) {
        if (this.dzB > 0) {
            this.dzB -= n2;
            if (this.dzB <= 0) {
                this.ap().g(true, true);
            }
        } else {
            this.bcj += n2;
        }
    }

    public void nr(int n2) {
        assert (n2 >= 0);
        this.dzB = n2;
        if (this.dzB > 0) {
            this.ap().g(false, true);
        }
    }

    protected int QI() {
        return this.bcj;
    }

    public final String getText() {
        return this.ap().getText();
    }

    public final void setColor(float f, float f2, float f3, float f4) {
        this.ap().setColor(f, f2, f3, f4);
    }

    public final void a(ma_1 ma_12) {
        this.ap().a(ma_12);
    }

    public final void r(float f, float f2) {
        this.ap().a(new agu_0(f, f2, 0.0f));
    }

    public final void setBorderWidth(float f) {
        this.ap().KW().setBorderWidth(f);
    }

    public final void setMaxWidth(int n2) {
        this.ap().setMaxWidth(n2);
    }

    public final int getMaxWidth() {
        return this.ap().getMaxWidth();
    }

    public final int getMinWidth() {
        return this.ap().getMinWidth();
    }

    public final void a(up_1 up_12) {
        GeometryBackground geometryBackground = this.ap().KW();
        geometryBackground.a(up_12.wZ(), up_12.xa());
        geometryBackground.f(up_12.xb());
        geometryBackground.e(up_12.xc());
        geometryBackground.e(up_12.wW(), up_12.wX(), up_12.wY(), up_12.wV());
    }

    public final void setMinWidth(int n2) {
        this.ap().setMinWidth(n2);
    }

    public final void setVisible(boolean bl2) {
        this.ap().setVisible(bl2);
    }

    public final void fa(int n2) {
        this.ap().fa(n2);
    }

    public final int aPR() {
        return this.ap().KX();
    }

    public final int aPS() {
        return this.ap().KY();
    }
}

