/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ati
 */
public class ati_0
implements gi {
    public static final float[] cTs = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    public static final float[] cTt = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
    protected float[] cTu = new float[]{0.2f, 0.2f, 0.2f, 0.2f};
    protected float[] cTv = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
    protected float[] cTw = new float[]{0.0f, 0.0f, 0.0f, 0.0f};
    protected float[] cTx = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    protected float[] cTy = new float[]{0.5f, 0.5f, 0.5f, 1.0f};
    protected float[] cTz = new float[]{0.5f, 0.5f, 0.5f, 1.0f};
    protected float[] cTA = new float[]{0.5f, 0.5f, 0.5f, 1.0f};
    protected boolean cTB;
    protected boolean cTC;
    protected boolean cTD;
    protected boolean cTE;
    protected boolean cTF = true;
    protected boolean cTG = true;
    protected boolean cTH = true;
    protected boolean cTI = true;
    protected boolean cTJ = true;

    public void reset() {
        if (!this.cTF) {
            this.cTB = false;
            this.cTC = false;
            this.cTD = false;
            this.cTE = false;
            this.cTG = true;
            this.cTH = true;
            this.cTI = true;
            this.cTJ = true;
            this.cTu[3] = 0.2f;
            this.cTu[2] = 0.2f;
            this.cTu[1] = 0.2f;
            this.cTu[0] = 0.2f;
            this.cTv[3] = 0.0f;
            this.cTv[2] = 0.0f;
            this.cTv[1] = 0.0f;
            this.cTv[0] = 0.0f;
            this.cTw[3] = 0.0f;
            this.cTw[2] = 0.0f;
            this.cTw[1] = 0.0f;
            this.cTw[0] = 0.0f;
            this.cTx[2] = 1.0f;
            this.cTx[1] = 1.0f;
            this.cTx[0] = 1.0f;
            this.cTx[3] = 1.0f;
            this.cTy[2] = 1.0f;
            this.cTy[1] = 1.0f;
            this.cTy[0] = 1.0f;
            this.cTy[3] = 1.0f;
            this.cTA[2] = 1.0f;
            this.cTA[1] = 1.0f;
            this.cTA[0] = 1.0f;
            this.cTA[3] = 1.0f;
            this.cTz[2] = 1.0f;
            this.cTz[1] = 1.0f;
            this.cTz[0] = 1.0f;
            this.cTz[3] = 1.0f;
            this.cTF = true;
        }
    }

    public boolean hasDefaultValue() {
        return this.cTF;
    }

    public boolean aGg() {
        return this.cTB;
    }

    public boolean aGh() {
        return this.cTC;
    }

    public boolean aGi() {
        return this.cTD;
    }

    public boolean aGj() {
        return this.cTE;
    }

    public void dX(boolean bl2) {
        this.cTB = bl2;
        this.cTG = true;
        this.cTF = false;
    }

    public void dY(boolean bl2) {
        this.cTC = bl2;
        this.cTH = true;
        this.cTF = false;
    }

    public void dZ(boolean bl2) {
        this.cTD = bl2;
        this.cTI = true;
        this.cTF = false;
    }

    public void ea(boolean bl2) {
        this.cTE = bl2;
        this.cTJ = true;
        this.cTF = false;
    }

    public void eb(boolean bl2) {
        this.cTF = true;
    }

    public float[] aGk() {
        return this.cTu;
    }

    public float[] aGl() {
        return this.cTx;
    }

    public float[] aGm() {
        return this.cTx;
    }

    public float[] aGn() {
        return this.cTy;
    }

    public float[] aGo() {
        return this.cTz;
    }

    public float[] aGp() {
        return this.cTA;
    }

    public float[] aGq() {
        return this.cTw;
    }

    public float[] aGr() {
        return this.cTv;
    }

    public void t(float f, float f2, float f3, float f4) {
        this.cTu[0] = f;
        this.cTu[1] = f2;
        this.cTu[2] = f3;
        this.cTu[3] = f4;
        this.cTG = true;
        this.cTF = false;
    }

    public void u(float f, float f2, float f3, float f4) {
        this.cTx[0] = f;
        this.cTx[1] = f2;
        this.cTx[2] = f3;
        this.cTx[3] = f4;
        this.cTz[0] = f;
        this.cTz[1] = f2;
        this.cTz[2] = f3;
        this.cTz[3] = f4;
        this.cTy[0] = f;
        this.cTy[1] = f2;
        this.cTy[2] = f3;
        this.cTy[3] = f4;
        this.cTA[0] = f;
        this.cTA[1] = f2;
        this.cTA[2] = f3;
        this.cTA[3] = f4;
        this.cTH = true;
        this.cTF = false;
    }

    public void v(float f, float f2, float f3, float f4) {
        this.cTw[0] = f;
        this.cTw[1] = f2;
        this.cTw[2] = f3;
        this.cTw[3] = f4;
        this.cTJ = true;
        this.cTF = false;
    }

    public void w(float f, float f2, float f3, float f4) {
        this.cTv[0] = f;
        this.cTv[1] = f2;
        this.cTv[2] = f3;
        this.cTv[3] = f4;
        this.cTI = true;
        this.cTF = false;
    }

    public void z(float[] fArray) {
        this.cTu = fArray;
        this.cTG = true;
        this.cTF = false;
    }

    public void A(float[] fArray) {
        this.cTx = fArray;
        this.cTy = fArray;
        this.cTz = fArray;
        this.cTA = fArray;
        this.cTH = true;
        this.cTF = false;
    }

    public void B(float[] fArray) {
        this.cTw = fArray;
        this.cTJ = true;
        this.cTF = false;
    }

    public void C(float[] fArray) {
        this.cTv = fArray;
        this.cTI = true;
        this.cTF = false;
    }

    public void x(float f, float f2, float f3, float f4) {
        if (f != 0.0f || f2 != 0.0f || f3 != 0.0f || f4 != 0.0f) {
            this.cTG = true;
            this.cTu[0] = this.cTu[0] + f;
            this.cTu[1] = this.cTu[1] + f2;
            this.cTu[2] = this.cTu[2] + f3;
            this.cTu[3] = this.cTu[3] + f4;
            this.cTF = false;
        }
    }

    public void y(float f, float f2, float f3, float f4) {
        if (f != 0.0f || f2 != 0.0f || f3 != 0.0f || f4 != 0.0f) {
            this.cTH = true;
            this.cTx[0] = this.cTx[0] + f;
            this.cTx[1] = this.cTx[1] + f2;
            this.cTx[2] = this.cTx[2] + f3;
            this.cTx[3] = this.cTx[3] + f4;
            this.cTF = false;
        }
    }

    public void z(float f, float f2, float f3, float f4) {
        if (f != 0.0f || f2 != 0.0f || f3 != 0.0f || f4 != 0.0f) {
            this.cTH = true;
            this.cTz[0] = this.cTz[0] + f;
            this.cTz[1] = this.cTz[1] + f2;
            this.cTz[2] = this.cTz[2] + f3;
            this.cTz[3] = this.cTz[3] + f4;
            this.cTF = false;
        }
    }

    public void A(float f, float f2, float f3, float f4) {
        if (f != 0.0f || f2 != 0.0f || f3 != 0.0f || f4 != 0.0f) {
            this.cTH = true;
            this.cTy[0] = this.cTy[0] + f;
            this.cTy[1] = this.cTy[1] + f2;
            this.cTy[2] = this.cTy[2] + f3;
            this.cTy[3] = this.cTy[3] + f4;
            this.cTF = false;
        }
    }

    public void B(float f, float f2, float f3, float f4) {
        if (f != 0.0f || f2 != 0.0f || f3 != 0.0f || f4 != 0.0f) {
            this.cTH = true;
            this.cTA[0] = this.cTA[0] + f;
            this.cTA[1] = this.cTA[1] + f2;
            this.cTA[2] = this.cTA[2] + f3;
            this.cTA[3] = this.cTA[3] + f4;
            this.cTF = false;
        }
    }

    public void C(float f, float f2, float f3, float f4) {
        if (f != 0.0f || f2 != 0.0f || f3 != 0.0f || f4 != 0.0f) {
            this.cTI = true;
            this.cTv[0] = this.cTv[0] + f;
            this.cTv[1] = this.cTv[1] + f2;
            this.cTv[2] = this.cTv[2] + f3;
            this.cTv[3] = this.cTv[3] + f4;
            this.cTF = false;
        }
    }

    public void D(float f, float f2, float f3, float f4) {
        if (f != 0.0f || f2 != 0.0f || f3 != 0.0f || f4 != 0.0f) {
            this.cTJ = true;
            this.cTw[0] = this.cTw[0] + f;
            this.cTw[1] = this.cTw[1] + f2;
            this.cTw[2] = this.cTw[2] + f3;
            this.cTw[3] = this.cTw[3] + f4;
            this.cTF = false;
        }
    }

    public boolean aGs() {
        return this.cTG;
    }

    public void ec(boolean bl2) {
        this.cTG = bl2;
    }

    public boolean aGt() {
        return this.cTJ;
    }

    public void ed(boolean bl2) {
        this.cTJ = bl2;
    }

    public boolean aGu() {
        return this.cTI;
    }

    public void ee(boolean bl2) {
        this.cTI = bl2;
    }

    public boolean aGv() {
        return this.cTH;
    }

    public void ef(boolean bl2) {
        this.cTH = bl2;
    }

    public boolean hasChanged() {
        return this.aGv() || this.aGs() || this.aGt() || this.aGu();
    }

    public void b(ati_0 ati_02) {
        int n2;
        float[] fArray;
        this.dX(ati_02.aGg());
        this.dY(ati_02.aGh());
        this.dZ(ati_02.aGi());
        this.ea(ati_02.aGj());
        if (this.aGg()) {
            fArray = ati_02.aGk();
            for (n2 = 0; n2 < fArray.length; ++n2) {
                this.cTu[n2] = fArray[n2];
            }
            this.cTG = true;
        }
        if (this.aGh()) {
            fArray = ati_02.aGl();
            this.u(fArray[0], fArray[1], fArray[2], fArray[3]);
        }
        if (this.aGi()) {
            fArray = ati_02.aGr();
            for (n2 = 0; n2 < fArray.length; ++n2) {
                this.cTv[n2] = fArray[n2];
            }
            this.cTI = true;
        }
        if (this.aGj()) {
            fArray = ati_02.aGq();
            for (n2 = 0; n2 < fArray.length; ++n2) {
                this.cTw[n2] = fArray[n2];
            }
            this.cTJ = true;
        }
        this.cTF = false;
    }

    public ati_0 aGw() {
        ati_0 ati_02 = new ati_0();
        ati_02.b(this);
        return ati_02;
    }

    public String toString() {
        return String.format("ambient  = [%f, %f, %f, %f]  use = %b\ndiffuse  = [%f, %f, %f, %f]  use = %b\nspecular = [%f, %f, %f, %f]  use = %b\nemission = [%f, %f, %f, %f]  use = %b", Float.valueOf(this.cTu[0]), Float.valueOf(this.cTu[1]), Float.valueOf(this.cTu[2]), Float.valueOf(this.cTu[3]), this.cTB, Float.valueOf(this.cTx[0]), Float.valueOf(this.cTx[1]), Float.valueOf(this.cTx[2]), Float.valueOf(this.cTx[3]), this.cTC, Float.valueOf(this.cTv[0]), Float.valueOf(this.cTv[1]), Float.valueOf(this.cTv[2]), Float.valueOf(this.cTv[3]), this.cTD, Float.valueOf(this.cTw[0]), Float.valueOf(this.cTw[1]), Float.valueOf(this.cTw[2]), Float.valueOf(this.cTw[3]), this.cTE);
    }
}

