/*
 * Decompiled with CFR 0.152.
 */
public class QB
implements QG {
    private final int abA;
    private final int abB;
    private final int bGL;

    public QB(int n2, int n3, int n4) {
        this.abA = n2;
        this.abB = n3;
        this.bGL = n4;
    }

    public void a(bd_0 bd_02, int n2) {
        float f;
        int n3 = bd_0.f(bd_02);
        int n4 = bd_02.getDuration();
        vP vP2 = bd_02.ap().getColor();
        if (n3 > 50) {
            vP2.W(bd_0.m(n3, 1.5f, -1.6f, n4));
        } else {
            vP2.W(0.0f);
        }
        bd_02.ap().setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        float f2 = 100.0f;
        if ((float)n3 < 100.0f) {
            f = (float)(this.bGL * n3) / 100.0f;
        } else {
            float f3 = ej_0.b(((float)n3 - 100.0f) / ((float)n4 - 100.0f) * 4.0f, 0.0f, 1.0f);
            f = Math.max(0.0f, (float)this.bGL * (1.0f - f3));
        }
        int n5 = bd_02.ap().KV().g(bd_02.getText());
        float f4 = Math.min(1.0f, (float)n3 / (float)n4 * 4.0f);
        double d = Math.PI * (double)f4;
        bd_02.setXOffset((int)((float)this.abA * f4) + 10 - n5 / 2);
        bd_02.setYOffset((int)((double)this.abB * Math.abs(Math.sin(d))) + 60);
        bd_02.ap().setZoom(f);
    }
}

