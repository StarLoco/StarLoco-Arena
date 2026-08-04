/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.text.EntityText;

/*
 * Renamed from LJ
 */
public class lj_0
extends aec_0
implements xu_1 {
    private int aW;
    private int aX = 1;

    public lj_0(ma_1 ma_12, String string) {
        super(ma_12, string);
    }

    public lj_0(ma_1 ma_12, String string, int n2) {
        super(ma_12, string, n2);
    }

    protected void init() {
        this.a(new atf_0());
    }

    public void a(qs_2 qs_22, int n2) {
        this.bI(n2);
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

    public void c(float f, float f2, float f3, float f4) {
        this.ap().a(new agu_0(f, f2, -1.0f));
        this.ap().aj((int)f3, (int)f4 + 3);
    }

    public final EntityText ap() {
        return super.ap();
    }

    public void cleanUp() {
    }
}

