/*
 * Decompiled with CFR 0.152.
 */
public class vn {
    public static int asy = 18;
    public static int asz = 18;

    public static int dR(int n2) {
        return (int)Math.floor((double)n2 / (double)asy);
    }

    public static int dS(int n2) {
        return (int)Math.floor((double)n2 / (double)asz);
    }

    public static int dT(int n2) {
        return (int)(((double)n2 + 0.5) * (double)asy);
    }

    public static int dU(int n2) {
        return (int)(((double)n2 + 0.5) * (double)asz);
    }
}

