/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aDB
 */
public class adb_0 {
    private static final float dxx = 2.0f;
    private static final int dxy = vP.atR.Cf();
    private final float dxz;
    private final float dxA;
    private final float dxB;
    private final float dxC;
    private final float dxD;
    private final float dxE;
    private final float dxF;
    private final float dxG;
    private final float dxH;
    public final boolean dxI;
    public final boolean dxJ;
    private final float[] dxK = new float[]{0.0f, 0.0f, 0.0f};
    private final float[] dxL;

    public adb_0(int n2, int n3, int n4, boolean bl2) {
        float[] fArray;
        this.dxz = vP.dY(n2) * 2.0f;
        this.dxA = vP.dX(n2) * 2.0f;
        this.dxB = vP.dW(n2) * 2.0f;
        this.dxJ = n3 != dxy;
        this.dxC = vP.dY(n3);
        this.dxD = vP.dX(n3);
        this.dxE = vP.dW(n3);
        if (n4 != dxy) {
            float[] fArray2 = new float[3];
            fArray2[0] = 0.0f;
            fArray2[1] = 0.0f;
            fArray = fArray2;
            fArray2[2] = 0.0f;
        } else {
            fArray = null;
        }
        this.dxL = fArray;
        this.dxF = vP.dY(n4) - 0.5f;
        this.dxG = vP.dX(n4) - 0.5f;
        this.dxH = vP.dW(n4) - 0.5f;
        this.dxI = bl2;
    }

    final void a(float f) {
        if (Math.abs(f) < 0.001f) {
            this.dxK[0] = this.dxz;
            this.dxK[1] = this.dxA;
            this.dxK[2] = this.dxB;
            return;
        }
        float f2 = this.dxz;
        float f3 = this.dxA;
        float f4 = this.dxB;
        if (f > 0.0f) {
            if (this.dxJ) {
                float f5 = f * 2.0f;
                float f6 = -f + 1.0f;
                this.dxK[0] = ej_0.b(f2 *= this.dxC * f5 + f6, 0.0f, 2.0f);
                this.dxK[1] = ej_0.b(f3 *= this.dxD * f5 + f6, 0.0f, 2.0f);
                this.dxK[2] = ej_0.b(f4 *= this.dxE * f5 + f6, 0.0f, 2.0f);
                return;
            }
        } else if (this.dxL != null) {
            float f7 = -f * 2.0f;
            this.dxL[0] = this.dxF * f7;
            this.dxL[1] = this.dxG * f7;
            this.dxL[2] = this.dxH * f7;
        }
        this.dxK[0] = f2;
        this.dxK[1] = f3;
        this.dxK[2] = f4;
    }

    public final float[] Aa() {
        return this.dxK;
    }

    public final float[] aPu() {
        return this.dxL;
    }

    public final boolean aPv() {
        return this.dxL != null;
    }
}

