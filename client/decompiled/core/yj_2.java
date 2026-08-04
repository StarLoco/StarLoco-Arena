/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from yj
 */
public class yj_2
extends or_1 {
    public yj_2(avE avE2, qq_1 qq_12, float f, boolean bl2, float f2) {
        super(avE2, qq_12, f, bl2, f2);
    }

    public void e(agv_0 agv_02) {
        float f = agv_02.getX();
        float f2 = f > 2.0f ? f - 2.0f : (f < -2.0f ? f + 2.0f : 0.0f);
        float f3 = agv_02.aSy();
        f3 = Math.max(1.0f, f3);
        this.bCB.setPosition(f2, f3, 0.0f);
    }
}

