/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aLD
 */
public class ald_0 {
    public static final ald_0 dVQ = new ald_0(0.5f, 0.5f);
    public static final String aGw = "gain";
    public static final String dVR = "gainHF";
    private float Ov = 0.5f;
    private float dpW = 0.5f;

    private ald_0() {
    }

    public ald_0(float f, float f2) {
        this.Ov = f;
        this.dpW = f2;
    }

    public static ald_0 u(k_0 k_02) {
        ald_0 ald_02 = new ald_0();
        for (k_0 k_03 : k_02.getChildren()) {
            if (k_03.getName().equals(aGw)) {
                ald_02.Ov = k_03.f("value").getFloatValue();
                continue;
            }
            if (!k_03.getName().equals(dVR)) continue;
            ald_02.dpW = k_03.f("value").getFloatValue();
        }
        return ald_02;
    }

    public static lb_0 f(k_0 k_02) {
        lb_0 lb_02 = new lb_0();
        for (k_0 k_03 : k_02.getChildren()) {
            if (!k_03.getName().equals("lowpass")) continue;
            lb_02.c(k_03.f("id").getIntValue(), ald_0.u(k_03));
        }
        return lb_02;
    }

    public float getGain() {
        return this.Ov;
    }

    public float aWs() {
        return this.dpW;
    }
}

