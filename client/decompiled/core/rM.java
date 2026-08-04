/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Font;
import javax.media.opengl.GL;

public class rM
extends afy_1 {
    public static pw_2 ahS = pw_2.uL();
    public static anw_0 ahT = anw_0.aCC();
    public static aea_2 ahU = aea_2.dzs;
    public static float ahV = 0.0f;
    public static float ahW = 0.0f;
    private up_1 ahX = null;
    private aea_2 ahY = ahU;
    protected float ahZ;
    protected float aia;
    protected float aib;
    protected float aic;
    private float aid = 0.0f;
    private float aie = 0.0f;
    private float aif = 0.0f;
    private float aig = 0.0f;
    private boolean aih = false;
    protected float aii;
    protected float aij;

    public rM(Font font) {
        this(font, false, null, null);
    }

    public rM(Font font, boolean bl2) {
        this(font, bl2, null, null);
    }

    public rM(Font font, boolean bl2, String string) {
        this(font, bl2, string, null);
    }

    public rM(Font font, String string) {
        this(font, false, string, null);
    }

    public rM(Font font, boolean bl2, String string, up_1 up_12) {
        super(font, bl2);
        this.setText(string);
        if (up_12 != null) {
            this.ahX = up_12;
        }
        this.init();
    }

    protected void init() {
        this.eP(true);
        this.a(ahS);
        this.b(ahT);
    }

    protected void a(up_1 up_12) {
        this.ahX = up_12;
    }

    public up_1 xK() {
        return this.ahX;
    }

    public aea_2 getHotPointPosition() {
        return this.ahY;
    }

    public void setHotPointPosition(aea_2 aea_22) {
        this.ahY = aea_22;
    }

    protected float xL() {
        return this.ahZ;
    }

    protected float xM() {
        return this.aia;
    }

    public void g(float f, float f2, float f3, float f4) {
        this.aid = f;
        this.aie = f2;
        this.aif = f + f3;
        this.aig = f2 + f4;
        this.aih = true;
    }

    public void b(GL gL) {
        if (this.isVisible()) {
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 0.0f;
            float f5 = this.ahX != null ? this.ahX.wW() : 0;
            float f6 = this.ahX != null ? this.ahX.wX() : 0;
            float f7 = this.ahX != null ? this.ahX.wY() : 0;
            float f8 = this.ahX != null ? this.ahX.wV() : 0;
            float f9 = f5;
            float f10 = f8;
            f = this.xL();
            float f11 = 0.0f;
            if (this.aSc() != 0.0f) {
                f3 = (this.aSf() + f5 + f6) / this.aSc();
                f11 = (this.aSg() + f5 + f6) / this.aSc();
                f9 = f5 / this.aSc();
            } else {
                f3 = f5 + f6;
            }
            f2 = this.xM();
            if (this.aSd() != 0.0f) {
                f4 = (this.aSe() + f8 + f7) / this.aSd();
                f10 = f8 / this.aSd();
            } else {
                f4 = f8 + f7;
            }
            if (f3 < this.aSb()) {
                f3 = this.aSb();
            }
            switch (this.ahY) {
                case dzt: {
                    f -= (float)((int)(f3 / 2.0f));
                    break;
                }
                case dzu: {
                    f -= f3;
                    break;
                }
                case dzv: {
                    f2 -= f4;
                    break;
                }
                case dzw: {
                    f -= (float)((int)(f3 / 2.0f));
                    f2 -= f4;
                    break;
                }
                case dzx: {
                    f -= f3;
                    f2 -= f4;
                    break;
                }
                case dzy: {
                    f2 -= (float)((int)(f4 / 2.0f));
                    break;
                }
                case dzz: {
                    f -= f3;
                    f2 -= (float)((int)(f4 / 2.0f));
                }
            }
            if (this.aih) {
                float f12 = this.aif - f3;
                float f13 = f < f12 ? f : f12;
                f = (int)(this.aid > f13 ? this.aid : f13);
                float f14 = this.aig - f4;
                float f15 = f2 < f14 ? f2 : f14;
                f2 = (int)(this.aie > f15 ? this.aie : f15);
            }
            this.x(f + (float)((int)f9) + this.aib);
            this.y(f2 + (float)((int)f10) + this.aic);
            if (f11 < f3) {
                this.x(this.xL() + (f3 - f11) / 2.0f + (float)((int)f9) + this.aib);
            }
            this.x(this.getX() + this.aii);
            this.y(this.getY() + this.aij);
            super.b(gL);
        }
    }

    public void r(float f, float f2) {
        this.c(f, f2, ahV, ahW);
    }

    public void c(float f, float f2, float f3, float f4) {
        super.r(f + f3, f2 + f4);
        this.ahZ = f;
        this.aia = f2;
        this.aib = f3;
        this.aic = f4;
    }

    public float bw() {
        return 1.0f;
    }

    public void s(float f, float f2) {
        this.aii = f;
        this.aij = f2;
    }
}

