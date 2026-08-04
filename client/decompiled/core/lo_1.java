/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from lO
 */
public class lo_1
implements QG {
    private int HX = 0;

    public lo_1(int n2) {
        this.HX = n2;
    }

    public void a(bd_0 bd_02, int n2) {
        int n3 = bd_0.c(bd_02);
        int n4 = bd_02.getDuration();
        int n5 = bd_02.ap().KV().g(bd_02.getText());
        int n6 = 10 - n5 / 2;
        int n7 = (int)bd_0.m(Math.max(0, n3 - this.HX), 80.0f, 100.0f, n4);
        if (n3 < this.HX) {
            float f = 1.0f - (float)n3 / (float)this.HX;
            n6 += (int)ej_0.e(-50.0f * f, 50.0f * f);
        }
        bd_02.setYOffset(n7);
        bd_02.setXOffset(n6);
        vP vP2 = bd_02.ap().getColor();
        vP2.W(bd_0.m(n3, 1.5f, -1.6f, n4));
        bd_02.ap().setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
    }
}

