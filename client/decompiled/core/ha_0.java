/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from hA
 */
public final class ha_0 {
    public static int g(double d) {
        assert (!Double.isNaN(d)) : "Values of NaN are not supported.";
        long l2 = Double.doubleToLongBits(d);
        return (int)(l2 ^ l2 >>> 32);
    }

    public static int s(float f) {
        assert (!Float.isNaN(f)) : "Values of NaN are not supported.";
        return Float.floatToIntBits(f * 6.6360896E8f);
    }

    public static int aQ(int n2) {
        return n2 * 31;
    }

    public static int S(long l2) {
        return (int)(l2 ^ l2 >>> 32) * 31;
    }

    public static int q(Object object) {
        return object == null ? 0 : object.hashCode();
    }
}

