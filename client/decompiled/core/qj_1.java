/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from QJ
 */
public class qj_1
implements QG {
    private cA bHz;

    public qj_1(qs_2 qs_22, cA cA2) {
        this.bHz = cA2;
    }

    public void a(bd_0 bd_02, int n2) {
        int n3;
        int n4;
        float f;
        int n5 = bd_0.e(bd_02);
        int n6 = bd_02.getDuration();
        vP vP2 = bd_02.ap().getColor();
        vP2.W(bd_0.m(n5, 1.5f, -1.6f, n6));
        float f2 = 450.0f;
        int n7 = bd_02.ap().KV().g(bd_02.getText());
        if ((float)n5 < 450.0f) {
            float f3 = Math.min(1.0f, (float)n5 / 450.0f);
            f = Math.max(0.1f, f3);
            n4 = -n7 / 4;
            n3 = (int)(60.0f * f3);
        } else {
            float f4 = Math.min(1.0f, ((float)n5 - 450.0f) / (float)n6 * 4.0f);
            float f5 = ej_0.b(((float)n5 - 450.0f) / ((float)n6 - 450.0f) * 4.0f, 0.0f, 1.0f);
            f = Math.max(0.0f, 1.0f - f5);
            n4 = (int)((float)(this.bHz.getScreenX() + this.bHz.getWidth() / 2) * f4) - n7 / 4;
            n3 = (int)((float)this.bHz.getScreenY() * f4) + 60;
        }
        bd_02.setXOffset(n4);
        bd_02.setYOffset(n3);
        bd_02.ap().setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        bd_02.ap().setZoom(f);
    }
}

