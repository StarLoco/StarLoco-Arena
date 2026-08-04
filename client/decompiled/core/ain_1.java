/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aiN
 */
public class ain_1 {
    public static final ain_1 cyW = new ain_1(1.0f, 10.0f, 18.0f);
    private float bVZ;
    private float cyX;
    private float bCE;
    public static final String cyY = "rollOffFactor";
    public static final String cyZ = "refDistance";
    public static final String cza = "maxDistance";

    public ain_1() {
    }

    public ain_1(float f, float f2, float f3) {
        this.bVZ = f;
        this.cyX = f2;
        this.bCE = f3;
    }

    public float ajH() {
        return this.bVZ;
    }

    public float ajI() {
        return this.cyX;
    }

    public float getMaxDistance() {
        return this.bCE;
    }

    public static ain_1 q(k_0 k_02) {
        ain_1 ain_12 = new ain_1();
        for (k_0 k_03 : k_02.getChildren()) {
            if (k_03.getName().equals(cyY)) {
                ain_12.bVZ = k_03.f("value").getFloatValue();
                continue;
            }
            if (k_03.getName().equals(cyZ)) {
                ain_12.cyX = k_03.f("value").getFloatValue();
                continue;
            }
            if (!k_03.getName().equals(cza)) continue;
            ain_12.bCE = k_03.f("value").getFloatValue();
        }
        return ain_12;
    }

    public static asz r(k_0 k_02) {
        asz asz2 = new asz();
        for (k_0 k_03 : k_02.getChildren()) {
            if (!k_03.getName().equals("rollOff")) continue;
            asz2.put(k_03.f("id").getIntValue(), ain_1.q(k_03));
        }
        return asz2;
    }
}

