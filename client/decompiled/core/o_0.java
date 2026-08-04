/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.text.EntityText;
import com.ankamagames.framework.graphics.engine.text.GeometryText;

/*
 * Renamed from O
 */
public class o_0
extends aec_0
implements xu_1 {
    private int aW;
    protected int aX = 2;

    public o_0(ma_1 ma_12, String string) {
        super(ma_12, string);
        this.init();
    }

    public o_0(ma_1 ma_12, String string, int n2) {
        super(ma_12, string, n2);
        this.init();
    }

    public void a(float f, float f2, float f3, float f4) {
        this.ap().KW().setColor(f, f2, f3, f4);
    }

    public void b(float f, float f2, float f3, float f4) {
        this.ap().KW().b(f, f2, f3, f4);
    }

    protected void init() {
        this.a(new aas_0());
    }

    public void c(float f, float f2, float f3, float f4) {
        EntityText entityText = this.ap();
        GeometryText geometryText = entityText.KV();
        float f5 = (float)entityText.KX() / geometryText.qZ() / 2.0f;
        entityText.a(new agu_0(f - f5, f2, -1.0f));
        entityText.aj((int)f3, (int)f4);
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

    public EntityText ap() {
        return super.ap();
    }

    public void cleanUp() {
    }
}

