/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;

public final class vP {
    public static final vP atH = new vP(0.0f, 0.0f, 0.0f, 0.0f);
    public static final vP atI = new vP(1.0f, 1.0f, 1.0f, 0.0f);
    public static final vP atJ = new vP(1.0f, 1.0f, 1.0f, 0.5f);
    public static final vP atK = new vP(1.0f, 1.0f, 1.0f, 0.25f);
    public static final vP atL = new vP(1.0f, 1.0f, 1.0f, 1.0f);
    public static final vP atM = new vP(0.0f, 0.0f, 0.0f, 1.0f);
    public static final vP atN = new vP(1.0f, 0.0f, 0.0f, 1.0f);
    public static final vP atO = new vP(0.0f, 1.0f, 0.0f, 1.0f);
    public static final vP atP = new vP(0.0f, 0.0f, 1.0f, 1.0f);
    public static final vP atQ = new vP(0.0f, 1.0f, 1.0f, 1.0f);
    public static final vP atR = new vP(128, 128, 128, 255);
    public static final vP atS = new vP(64, 64, 64, 255);
    public static final vP atT = new vP(192, 192, 192, 255);
    public static final vP atU = new vP(224, 224, 224, 255);
    public static final vP atV = new vP(0.57f, 0.2f, 0.75f, 0.66f);
    public static final vP atW = new vP(0.95f, 0.64f, 0.25f, 1.0f);
    private int atX;

    public vP() {
        this.atX = 0;
    }

    public vP(vP vP2) {
        this.atX = vP2.atX;
    }

    public vP(float f, float f2, float f3, float f4) {
        this.h(f, f2, f3, f4);
    }

    public vP(int n2) {
        this.set(n2);
    }

    public vP(byte by, byte by2, byte by3, byte by4) {
        this.c(by, by2, by3, by4);
    }

    public vP(int n2, int n3, int n4, int n5) {
        this.p(n2, n3, n4, n5);
    }

    public final int Cf() {
        return this.atX;
    }

    public final int Cg() {
        return this.atX >> 24 | this.atX << 8;
    }

    public final byte Ch() {
        return (byte)(this.atX >> 24 & 0xFF);
    }

    public final byte Ci() {
        return (byte)(this.atX & 0xFF);
    }

    public final byte Cj() {
        return (byte)(this.atX >> 8 & 0xFF);
    }

    public final byte Ck() {
        return (byte)(this.atX >> 16 & 0xFF);
    }

    public final int Cl() {
        return this.atX >> 24 & 0xFF;
    }

    public final int Cm() {
        return this.atX & 0xFF;
    }

    public final int Cn() {
        return this.atX >> 8 & 0xFF;
    }

    public final int Co() {
        return this.atX >> 16 & 0xFF;
    }

    public final float getAlpha() {
        int n2 = this.Cl();
        if (n2 < 0) {
            n2 = 256 + n2;
        }
        return (float)n2 / 255.0f;
    }

    public final float Cp() {
        return (float)this.Cm() / 255.0f;
    }

    public final float Cq() {
        return (float)this.Cn() / 255.0f;
    }

    public final float Cr() {
        return (float)this.Co() / 255.0f;
    }

    public final float Cs() {
        return Math.max(this.Cp(), Math.max(this.Cr(), this.Cq()));
    }

    public final float Ct() {
        return (this.Cp() + this.Cq() + this.Cr()) / 3.0f;
    }

    public final void V(float f) {
        assert (f >= 0.0f && f <= 1.0f) : "Invalid intensity value " + f;
        float f2 = Math.max(this.Cp(), Math.max(this.Cr(), this.Cq()));
        if (f2 == 0.0f) {
            this.h(f, f, f, this.getAlpha());
            return;
        }
        float f3 = f / f2;
        float f4 = Math.min(1.0f, this.Cp() * f3);
        float f5 = Math.min(1.0f, this.Cr() * f3);
        float f6 = Math.min(1.0f, this.Cq() * f3);
        this.h(f4, f6, f5, this.getAlpha());
    }

    public final void h(float f, float f2, float f3, float f4) {
        this.atX = vP.i(f, f2, f3, f4);
    }

    public final void W(float f) {
        f = ej_0.b(f, 0.0f, 1.0f);
        this.atX = this.atX & 0xFFFFFF | (int)(f * 255.0f) << 24;
    }

    public final void set(int n2) {
        this.atX = n2;
    }

    public final void c(byte by, byte by2, byte by3, byte by4) {
        this.atX = vP.d(by, by2, by3, by4);
    }

    public final void p(int n2, int n3, int n4, int n5) {
        this.atX = vP.q(n2, n3, n4, n5);
    }

    public final void c(vP vP2) {
        this.h(this.Cp() * vP2.Cp(), this.Cq() * vP2.Cq(), this.Cr() * vP2.Cr(), this.getAlpha() * vP2.getAlpha());
    }

    public void Cu() {
        this.p(ej_0.n(0, 255), ej_0.n(0, 255), ej_0.n(0, 255), ej_0.n(0, 255));
    }

    public static vP b(vP vP2, vP vP3) {
        vP vP4 = new vP(vP2);
        vP4.c(vP3);
        return vP4;
    }

    public static float dV(int n2) {
        return (float)(n2 >> 24 & 0xFF) / 255.0f;
    }

    public static float dW(int n2) {
        return (float)(n2 >> 16 & 0xFF) / 255.0f;
    }

    public static float dX(int n2) {
        return (float)(n2 >> 8 & 0xFF) / 255.0f;
    }

    public static float dY(int n2) {
        return (float)(n2 & 0xFF) / 255.0f;
    }

    public static int i(float f, float f2, float f3, float f4) {
        return (int)(ej_0.b(f4, 0.0f, 1.0f) * 255.0f) << 24 | (int)(ej_0.b(f, 0.0f, 1.0f) * 255.0f) | (int)(ej_0.b(f2, 0.0f, 1.0f) * 255.0f) << 8 | (int)(ej_0.b(f3, 0.0f, 1.0f) * 255.0f) << 16;
    }

    public static int d(byte by, byte by2, byte by3, byte by4) {
        return afy_0.aA(by4) << 24 | afy_0.aA(by) | afy_0.aA(by2) << 8 | afy_0.aA(by3) << 16;
    }

    public static int q(int n2, int n3, int n4, int n5) {
        return ej_0.e(n5, 0, 255) << 24 | ej_0.e(n2, 0, 255) | ej_0.e(n3, 0, 255) << 8 | ej_0.e(n4, 0, 255) << 16;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(vP.dY(this.atX)).append(", ").append(vP.dX(this.atX)).append(", ").append(vP.dW(this.atX)).append(", ").append(vP.dV(this.atX));
        return stringBuilder.toString();
    }

    public String Cv() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = (this.Cm() < 16 ? "0" : "") + Integer.toHexString(this.Cm());
        String string2 = (this.Cn() < 16 ? "0" : "") + Integer.toHexString(this.Cn());
        String string3 = (this.Co() < 16 ? "0" : "") + Integer.toHexString(this.Co());
        stringBuilder.append(string).append(string2).append(string3);
        return stringBuilder.toString();
    }

    public String Cw() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = (this.Cm() < 16 ? "0" : "") + Integer.toHexString(this.Cm());
        String string2 = (this.Cn() < 16 ? "0" : "") + Integer.toHexString(this.Cn());
        String string3 = (this.Co() < 16 ? "0" : "") + Integer.toHexString(this.Co());
        String string4 = (this.Cl() < 16 ? "0" : "") + Integer.toHexString(this.Cl());
        stringBuilder.append(string).append(string2).append(string3).append(string4);
        return stringBuilder.toString();
    }

    public static vP cB(String string) {
        int n2 = Integer.parseInt(string.substring(0, 2), 16);
        int n3 = Integer.parseInt(string.substring(2, 4), 16);
        int n4 = Integer.parseInt(string.substring(4, 6), 16);
        int n5 = 1;
        if (string.length() == 8) {
            n5 = Integer.parseInt(string.substring(6, 8), 16);
        }
        return new vP(n2, n3, n4, n5);
    }

    public static void main(String[] stringArray) {
        int n2 = 200000;
        vP[] vPArray = new vP[200000];
        int[][] nArray = new int[200000][1];
        try {
            while (System.in.read() != 97) {
                Thread.sleep(40L);
            }
            System.out.println("run");
            for (int j = 0; j < vPArray.length; ++j) {
                vPArray[j] = new vP(j);
                nArray[j][0] = vPArray[j].Cl();
            }
            System.out.println("done");
            while (System.in.read() != 122) {
                Thread.sleep(40L);
            }
        }
        catch (IOException iOException) {
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }
}

