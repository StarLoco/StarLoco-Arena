/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Zx
 */
public final class zx_2
implements ajf_1 {
    private static final int cdg = 7;
    public static final int cdh = 128;
    private static final int MASK = 127;
    private static final int cdi = 16383;
    private final float[][] cdj;
    private final agf_0 cdk = new agf_0(0, 0, 0, 0);

    public zx_2() {
        this.cdj = new float[16384][];
        for (int j = 0; j < 16384; ++j) {
            this.cdj[j] = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        }
    }

    public void a(int n2, int n3, short s, float[] fArray) {
        assert (fArray.length == 3);
        int n4 = zx_2.getOffset(n2, n3);
        float[] fArray2 = this.cdj[n4];
        fArray2[0] = fArray[0];
        fArray2[1] = fArray[1];
        fArray2[2] = fArray[2];
    }

    public void b(int n2, int n3, short s, float[] fArray) {
        assert (fArray.length == 3);
        int n4 = zx_2.getOffset(n2, n3);
        float[] fArray2 = this.cdj[n4];
        fArray2[3] = fArray[0];
        fArray2[4] = fArray[1];
        fArray2[5] = fArray[2];
    }

    public void bg(int n2, int n3) {
        int n4 = zx_2.getOffset(n2, n3);
        float[] fArray = this.cdj[n4];
        ko_1.c(fArray);
    }

    public final void d(int n2, int n3, float f) {
        int n4 = zx_2.getOffset(n2, n3);
        float[] fArray = this.cdj[n4];
        fArray[0] = fArray[0] * f;
        fArray[1] = fArray[1] * f;
        fArray[2] = fArray[2] * f;
    }

    public final void a(float[] fArray, int n2, int n3, int n4, int n5) {
        for (int j = 0; j < n3; ++j) {
            int n6 = j * n2;
            int n7 = zx_2.getOffset(n4, j + n5);
            for (int i2 = 0; i2 < n2; ++i2) {
                float f = fArray[i2 + n6];
                float[] fArray2 = this.cdj[i2 + n7 & 0x3FFF];
                fArray2[0] = fArray2[0] * f;
                fArray2[1] = fArray2[1] * f;
                fArray2[2] = fArray2[2] * f;
            }
        }
    }

    public final void a(float[] fArray, int n2, int n3, int n4, int n5, float f, float f2) {
        assert (this.cdj.length >= fArray.length);
        float f3 = (1.0f - f) * (1.0f - f2);
        float f4 = (1.0f - f) * f2;
        float f5 = f * (1.0f - f2);
        float f6 = f * f2;
        for (int j = 0; j < n3 - 1; ++j) {
            int n6 = j * n2;
            int n7 = (j + 1) * n2;
            int n8 = zx_2.getOffset(n4, j + n5);
            for (int i2 = 0; i2 < n2 - 1; ++i2) {
                float f7 = fArray[i2 + n6];
                float f8 = fArray[i2 + n7];
                float f9 = fArray[i2 + 1 + n6];
                float f10 = fArray[i2 + 1 + n7];
                float f11 = f3 * f7 + f4 * f8 + f5 * f9 + f6 * f10;
                float[] fArray2 = this.cdj[i2 + n8 & 0x3FFF];
                fArray2[0] = fArray2[0] * f11;
                fArray2[1] = fArray2[1] * f11;
                fArray2[2] = fArray2[2] * f11;
            }
        }
    }

    public final void a(float[] fArray, int n2, float f, float f2) {
        float f3 = f;
        float f4 = f2;
        int n3 = (int)Math.floor(f3);
        int n4 = (int)Math.floor(f4);
        float f5 = (float)n3 - f3;
        float f6 = (float)n4 - f4;
        int n5 = (int)Math.signum(f5);
        int n6 = (int)Math.signum(f6);
        int n7 = n5 + n6;
        float f7 = 1.0f;
        if (n7 != 0) {
            f7 = (f5 + f6) / (float)n7;
        }
        for (int j = 0; j < n2; ++j) {
            for (int i2 = 0; i2 < n2; ++i2) {
                float f8;
                int n8 = n3 + i2 - (n2 >> 1) - 1;
                int n9 = n4 + j - (n2 >> 1) - 1;
                float f9 = f8 = fArray[i2 + j * n2];
                if (n7 != 0) {
                    int n10 = i2 + n5;
                    int n11 = j + n6;
                    float f10 = 1.0f;
                    if (n10 >= 0 && n10 < n2 && n11 >= 0 && n11 < n2) {
                        f10 = fArray[n10 + n11 * n2];
                    }
                    f9 = f8 * (1.0f - f7) + f10 * f7;
                }
                this.d(n8, n9, f9);
            }
        }
    }

    public final void f(float f, float f2, float f3) {
        int n2 = this.cdk.aSQ();
        int n3 = this.cdk.aSS();
        int n4 = this.cdk.width() - 1;
        int n5 = this.cdk.height() - 1;
        for (int j = 0; j < n5; ++j) {
            int n6 = j + n3;
            for (int i2 = 0; i2 < n4; ++i2) {
                float[] fArray = this.cdj[zx_2.getOffset(i2 + n2, n6)];
                fArray[0] = fArray[0] * f;
                fArray[1] = fArray[1] * f2;
                fArray[2] = fArray[2] * f3;
            }
        }
    }

    public final void a(int n2, int n3, int n4, float f, float f2, float f3, float f4, float f5, float f6) {
        int n5 = zx_2.getOffset(n2, n3);
        float[] fArray = this.cdj[n5];
        fArray[0] = fArray[0] + f;
        fArray[1] = fArray[1] + f2;
        fArray[2] = fArray[2] + f3;
        fArray[3] = fArray[3] + f4;
        fArray[4] = fArray[4] + f5;
        fArray[5] = fArray[5] + f6;
    }

    public final void g(float f, float f2, float f3) {
        int n2 = this.cdk.aSQ();
        int n3 = this.cdk.aSS();
        int n4 = this.cdk.width() - 1;
        int n5 = this.cdk.height() - 1;
        for (int j = 0; j < n5; ++j) {
            int n6 = zx_2.getOffset(n2, j + n3);
            for (int i2 = 0; i2 < n4; ++i2) {
                float[] fArray = this.cdj[i2 + n6 & 0x3FFF];
                fArray[0] = fArray[0] + f;
                fArray[1] = fArray[1] + f2;
                fArray[2] = fArray[2] + f3;
            }
        }
    }

    public void a(mI mI2) {
        int n2 = this.cdk.aSQ();
        int n3 = this.cdk.aSS();
        int n4 = this.cdk.width() - 1;
        int n5 = this.cdk.height() - 1;
        for (int j = 0; j <= n5; ++j) {
            for (int i2 = 0; i2 <= n4; ++i2) {
                int n6 = n2 + i2;
                int n7 = n3 + j;
                int n8 = zx_2.getOffset(n6, n7);
                mI2.a(n6, n7, 0, this.cdj[n8]);
            }
        }
    }

    public float[] F(int n2, int n3, int n4) {
        int n5 = zx_2.getOffset(n2, n3);
        return this.cdj[n5];
    }

    public agf_0 anU() {
        return this.cdk;
    }

    public void setBounds(int n2, int n3, int n4, int n5) {
        this.cdk.set(n2, n2 + n4, n3, n3 + n5);
    }

    public void normalize() {
        int n2 = this.cdk.aSQ();
        int n3 = this.cdk.aSS();
        int n4 = this.cdk.width() - 1;
        int n5 = this.cdk.height() - 1;
        float f = 1.0f;
        for (int j = 0; j < n5; ++j) {
            int n6 = (j + n3 & 0x7F) << 7;
            for (int i2 = 0; i2 < n4; ++i2) {
                float[] fArray = this.cdj[(i2 + n2 & 0x7F) + n6];
                if (fArray[0] > 1.0f) {
                    fArray[0] = 1.0f;
                }
                if (fArray[1] > 1.0f) {
                    fArray[1] = 1.0f;
                }
                if (fArray[2] > 1.0f) {
                    fArray[2] = 1.0f;
                }
                if (fArray[3] > 1.0f) {
                    fArray[3] = 1.0f;
                }
                if (fArray[4] > 1.0f) {
                    fArray[4] = 1.0f;
                }
                if (!(fArray[5] > 1.0f)) continue;
                fArray[5] = 1.0f;
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("LightMap window={").append(this.cdk).append("}");
        return stringBuffer.toString();
    }

    private static int getOffset(int n2, int n3) {
        return (n2 & 0x7F) + ((n3 & 0x7F) << 7);
    }
}

