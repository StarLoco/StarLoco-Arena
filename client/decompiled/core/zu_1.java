/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from zU
 */
public class zu_1 {
    public static final String aGw = "wetdry";
    public static final String aGx = "decaytime";
    public static final String aGy = "decayLPF";
    public static final String aGz = "earlygain";
    public static final String aGA = "predelay";
    private float aGB = 0.32f;
    private float aGC = 1.49f;
    private float aGD = 0.83f;
    private float aGE = 1.26f;
    private float aGF = 0.011f;

    private zu_1() {
    }

    public zu_1(float f, float f2, float f3, float f4, float f5) {
        this.aGB = f;
        this.aGC = f2;
        this.aGD = f3;
        this.aGE = f4;
        this.aGF = f5;
    }

    public static zu_1 e(k_0 k_02) {
        zu_1 zu_12 = new zu_1();
        for (k_0 k_03 : k_02.getChildren()) {
            if (k_03.getName().equals(aGw)) {
                zu_12.aGB = k_03.f("value").getFloatValue();
                continue;
            }
            if (k_03.getName().equals(aGx)) {
                zu_12.aGC = k_03.f("value").getFloatValue();
                continue;
            }
            if (k_03.getName().equals(aGy)) {
                zu_12.aGD = k_03.f("value").getFloatValue();
                continue;
            }
            if (k_03.getName().equals(aGz)) {
                zu_12.aGE = k_03.f("value").getFloatValue();
                continue;
            }
            if (!k_03.getName().equals(aGA)) continue;
            zu_12.aGF = k_03.f("value").getFloatValue();
        }
        return zu_12;
    }

    public static lb_0 f(k_0 k_02) {
        lb_0 lb_02 = new lb_0();
        for (k_0 k_03 : k_02.getChildren()) {
            if (!k_03.getName().equals("reverb")) continue;
            lb_02.c(k_03.f("id").getIntValue(), zu_1.e(k_03));
        }
        return lb_02;
    }

    public float GV() {
        return this.aGB;
    }

    public float GW() {
        return this.aGC;
    }

    public float GX() {
        return this.aGD;
    }

    public float GY() {
        return this.aGE;
    }

    public float GZ() {
        return this.aGF;
    }
}

