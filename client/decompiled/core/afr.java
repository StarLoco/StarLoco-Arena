/*
 * Decompiled with CFR 0.152.
 */
class afr
implements aha_0 {
    afr() {
    }

    public double b(double d, double d2, float f) {
        double d3 = d2 - d;
        double d4 = Math.signum(d3);
        if (Math.abs(d3) < 100.0) {
            return d;
        }
        return ej_0.b(d, d2 - d4 * 100.0, (double)f);
    }
}

