/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from afH
 */
public class afh_0
implements QG {
    public void a(bd_0 bd_02, int n2) {
        int n3 = bd_0.b(bd_02);
        int n4 = bd_02.getDuration();
        int n5 = bd_02.ap().KV().g(bd_02.getText());
        bd_02.setYOffset((int)bd_0.m(n3, 80.0f, 100.0f, n4));
        bd_02.setXOffset(10 - n5 / 2);
        vP vP2 = bd_02.ap().getColor();
        vP2.W(this.a(n3, n4, 4));
        bd_02.ap().setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
    }

    private float a(float f, float f2, int n2) {
        boolean bl2;
        float f3 = f * (float)n2 / f2;
        int n3 = (int)Math.floor(f3);
        float f4 = f3 - (float)n3;
        boolean bl3 = bl2 = n3 % 2 == 0;
        if (bl2) {
            return f4;
        }
        return 1.0f - f4;
    }
}

