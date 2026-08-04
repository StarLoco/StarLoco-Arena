/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.text.EntityText;
import com.ankamagames.framework.graphics.engine.text.GeometryBackground;

/*
 * Renamed from Bd
 */
public class bd_0
extends aec_0
implements xu_1 {
    private QG aIG = null;
    private int aW;
    private int aX = 3;

    private static float l(float f, float f2, float f3, float f4) {
        f = f / f4 - 1.0f;
        return -f3 * (f * f * f * f - 1.0f) + f2;
    }

    public bd_0(ma_1 ma_12, String string) {
        this(ma_12, string, -1);
    }

    public bd_0(ma_1 ma_12, String string, int n2) {
        this(ma_12, string, new ew_1(), n2);
    }

    public bd_0(ma_1 ma_12, String string, QG qG, int n2) {
        super(ma_12, string, n2);
        this.ap().a((GeometryBackground)null);
        this.ap().a(ma_12);
        this.aIG = qG;
    }

    public QG Ig() {
        return this.aIG;
    }

    public void a(QG qG) {
        this.aIG = qG;
    }

    public float bw() {
        return 2.0f;
    }

    public void c(float f, float f2, float f3, float f4) {
        this.ap().a(new agu_0(f, f2, -1.0f));
        this.ap().aj((int)f3, (int)f4);
    }

    public void a(qs_2 qs_22, int n2) {
        this.bI(n2);
    }

    public void bI(int n2) {
        super.bI(n2);
        this.Ig().a(this, n2);
    }

    public int getId() {
        return this.aW;
    }

    public void f(int n2) {
        this.aW = n2;
    }

    public int ao() {
        return this.aX;
    }

    public void h(int n2) {
        this.aX = n2;
    }

    public EntityText ap() {
        return super.ap();
    }

    public void cleanUp() {
    }

    static /* synthetic */ int a(bd_0 bd_02) {
        return bd_02.QI();
    }

    static /* synthetic */ float m(float f, float f2, float f3, float f4) {
        return bd_0.l(f, f2, f3, f4);
    }

    static /* synthetic */ int b(bd_0 bd_02) {
        return bd_02.QI();
    }

    static /* synthetic */ int c(bd_0 bd_02) {
        return bd_02.QI();
    }

    static /* synthetic */ int d(bd_0 bd_02) {
        return bd_02.QI();
    }

    static /* synthetic */ int e(bd_0 bd_02) {
        return bd_02.QI();
    }

    static /* synthetic */ int f(bd_0 bd_02) {
        return bd_02.QI();
    }
}

