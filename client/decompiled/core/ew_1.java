/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from EW
 */
public class ew_1
implements QG {
    public void a(bd_0 bd_02, int n2) {
        int n3 = bd_0.a(bd_02);
        int n4 = bd_02.getDuration();
        int n5 = bd_02.ap().KV().g(bd_02.getText());
        bd_02.setYOffset((int)bd_0.m(n3, 80.0f, 100.0f, n4));
        bd_02.setXOffset(15 - n5 / 2);
        vP vP2 = bd_02.ap().getColor();
        vP2.W(bd_0.m(n3, 1.5f, -1.6f, n4));
        bd_02.ap().setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
    }
}

