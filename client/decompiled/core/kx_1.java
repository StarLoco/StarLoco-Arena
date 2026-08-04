/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from kX
 */
public enum kx_1 {
    FR,
    FS,
    FT;


    public boolean g(int n2, int n3, int n4, int n5) {
        switch (this) {
            case FR: {
                return n2 >= 0 && n3 >= 0 && n2 < n4 && n3 < n5;
            }
            case FS: {
                float f = (float)n5 / 2.0f;
                float f2 = f / ((float)n4 / 2.0f) * (float)n2;
                return (float)n3 >= -f2 + f && (float)n3 >= f2 - f && (float)n3 < f2 + f && (float)n3 < -f2 + 3.0f * f;
            }
            case FT: {
                int n6 = Math.min(n4, n5) / 2;
                return (n2 -= n4 / 2) * n2 + (n3 -= n5 / 2) * n3 <= n6 * n6;
            }
        }
        return true;
    }

    public int h(int n2, int n3, int n4, int n5) {
        switch (this) {
            case FR: {
                if (n2 < 0) {
                    return 0;
                }
                if (n2 > n4) {
                    return n4;
                }
                return n2;
            }
            case FT: {
                int n6;
                int n7 = (int)((float)Math.min(n4 / 2, n5 / 2) * 0.8f);
                int n8 = n6 = n2 < n4 / 2 ? -1 : 1;
                if (n2 * n2 + n3 * n3 < n7 * n7) {
                    return n2;
                }
                return n6 * (int)Math.sqrt(n7 * n7 * (1 / (n3 * n3 / (n2 * n2) + 1)));
            }
            case FS: {
                float f;
                float f2;
                float f3 = (float)n4 / 2.0f;
                float f4 = (float)n5 / 2.0f;
                float f5 = ((float)n3 - f4) / ((float)n2 - f3);
                float f6 = (float)n3 - f5 * (float)n2;
                if ((float)n2 < f3) {
                    if ((float)n3 < f4) {
                        f2 = -f4 / f3;
                        f = f4;
                    } else {
                        f2 = f4 / f3;
                        f = f4;
                    }
                } else if ((float)n3 < f4) {
                    f2 = f4 / f3;
                    f = -f4;
                } else {
                    f2 = -f4 / f3;
                    f = 3.0f * f4;
                }
                return (int)((f - f6) / (f5 - f2));
            }
        }
        return n2;
    }

    public int i(int n2, int n3, int n4, int n5) {
        switch (this) {
            case FR: {
                if (n3 < 0) {
                    return 0;
                }
                if (n3 > n5) {
                    return n5;
                }
                return n3;
            }
            case FT: {
                int n6;
                int n7 = (int)((float)Math.min(n4 / 2, n5 / 2) * 0.8f);
                int n8 = n6 = n3 < n5 / 2 ? -1 : 1;
                if (n2 * n2 + n3 * n3 < n7 * n7) {
                    return n3;
                }
                return n6 * (int)Math.sqrt(n7 * n7 * (1 / (n2 * n2 / (n3 * n3) + 1)));
            }
            case FS: {
                float f;
                float f2;
                float f3 = (float)n4 / 2.0f;
                float f4 = (float)n5 / 2.0f;
                float f5 = ((float)n3 - f4) / ((float)n2 - f3);
                float f6 = (float)n3 - f5 * (float)n2;
                if ((float)n2 < f3) {
                    if ((float)n3 < f4) {
                        f2 = -f4 / f3;
                        f = f4;
                    } else {
                        f2 = f4 / f3;
                        f = f4;
                    }
                } else if ((float)n3 < f4) {
                    f2 = f4 / f3;
                    f = -f4;
                } else {
                    f2 = -f4 / f3;
                    f = 3.0f * f4;
                }
                float f7 = (f - f6) / (f5 - f2);
                return (int)(f5 * f7 + f6);
            }
        }
        return n3;
    }

    public static kx_1 aP(String string) {
        kx_1[] kx_1Array;
        for (kx_1 kx_12 : kx_1Array = kx_1.values()) {
            if (!kx_12.name().equals(string.toUpperCase())) continue;
            return kx_12;
        }
        return kx_1Array[0];
    }
}

