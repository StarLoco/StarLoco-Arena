/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;

/*
 * Renamed from wz
 */
public abstract class wz_1
extends akC {
    public float auL;
    public float auM;
    protected Entity3D AH;
    protected ams_1 az;
    protected VertexBufferPCT aA;
    protected static final int auN = 4096;
    protected static final float[] auO = new float[32768];
    protected static final float[] auP = new float[65536];
    protected static final float[] auQ = new float[32768];
    protected final fb_0 auR;
    protected aua_0 auS;
    protected int auT;
    protected int auU;
    protected int auV;
    protected jg_0 auW;
    protected akk_2[] auX;
    protected azo_0 auY;
    protected float fG;
    protected float auZ;

    protected wz_1() {
        this.auR = null;
    }

    public wz_1(fb_0 fb_02) {
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        this.auR = fb_02;
    }

    public void ac(int n2, int n3) {
        int n4;
        n2 = Math.min(n2, 4096);
        this.auU = 0;
        this.auT = 0;
        this.auV = Math.min(n3, n2);
        this.auX = new akk_2[n2];
        for (n4 = 0; n4 < n2; ++n4) {
            this.auX[n4] = this.auR.OM();
        }
        this.auS = new aua_0(n2);
        this.auW = new jg_0(n2);
        n4 = n2 * 4;
        this.aA = new VertexBufferPCT(n4);
        this.az = new ams_1(n4);
        for (int j = 0; j < n4; ++j) {
            this.az.add(j);
        }
        GLGeometryMesh gLGeometryMesh = (GLGeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
        gLGeometryMesh.a(jB.Ba, this.aA, this.az, false);
        gLGeometryMesh.a(air.cyd, air.cya);
        this.AH.b(gLGeometryMesh);
    }

    public void initialize(int n2) {
        this.ac(n2, n2);
    }

    public void eb(int n2) {
        this.auV = Math.min(n2, this.auX.length);
    }

    public void clear() {
        this.AH.HF();
    }

    public void reset() {
        this.fG = 0.0f;
        this.auZ = 0.0f;
        if (this.auS != null) {
            for (int j = 0; j < this.auU; ++j) {
                this.auS.mm(this.auW.get(j) - 1);
            }
        }
        if (this.auW != null) {
            this.auW.clear();
        }
        this.auU = 0;
    }

    public void a(float f) {
        akk_2 akk_22;
        int n2;
        this.fG += f;
        float f2 = (float)this.cDB.getAltitude();
        int n3 = 0;
        while (n3 < this.auU) {
            n2 = this.auW.bu(n3) - 1;
            akk_22 = this.auX[n2];
            float f3 = akk_22.Hm - f2;
            if (f3 <= this.auY.aMt() && f3 >= this.auY.aMu() && akk_22.KQ <= akk_22.KR) {
                ++n3;
                continue;
            }
            --this.auU;
            this.auW.bv(n3);
            this.auS.mm(n2);
        }
        this.auZ += f;
        if (this.auZ > this.auL) {
            n3 = 0;
            while ((float)n3 < this.auM && this.auU < this.auV) {
                n2 = this.auS.aHc();
                this.auW.add(n2 + 1);
                ++this.auU;
                akk_22 = this.auX[n2];
                this.a(akk_22);
                ++n3;
            }
            this.auZ -= this.auL;
        }
        for (n3 = 0; n3 < this.auU; ++n3) {
            n2 = this.auW.bu(n3) - 1;
            akk_22 = this.auX[n2];
            this.a(akk_22, f);
        }
        n3 = 0;
        n2 = 0;
        int n4 = 0;
        int n5 = 0;
        this.aA.clear();
        for (int j = 0; j < this.auU; ++j) {
            float f4;
            int n6 = this.auW.bu(j) - 1;
            akk_2 akk_23 = this.auX[n6];
            float f5 = (akk_23.Hk - akk_23.Hl) * 43.0f;
            float f6 = -(akk_23.Hk + akk_23.Hl) * 21.5f + akk_23.Hm * 10.0f;
            float f7 = 1.0f;
            if (!this.CS()) {
                f7 = 1.5f / this.cDB.aEK();
            }
            float f8 = akk_23.bsE * f7;
            float f9 = akk_23.bsF * f7;
            if (this.CR()) {
                f4 = ej_0.l(akk_23.KS);
                float f10 = ej_0.k(akk_23.KS);
                float f11 = f4 * f9;
                float f12 = f10 * f9;
                float f13 = -f10 * f8;
                float f14 = f4 * f8;
                wz_1.auO[n3++] = f5 - f11 - f13;
                wz_1.auO[n3++] = f6 - f12 - f14;
                wz_1.auO[n3++] = f5 - f11 + f13;
                wz_1.auO[n3++] = f6 - f12 + f14;
                wz_1.auO[n3++] = f5 + f11 + f13;
                wz_1.auO[n3++] = f6 + f12 + f14;
                wz_1.auO[n3++] = f5 + f11 - f13;
                wz_1.auO[n3++] = f6 + f12 - f14;
            } else {
                wz_1.auO[n3++] = f5 - f8;
                wz_1.auO[n3++] = f6 - f9;
                wz_1.auO[n3++] = f5 - f8;
                wz_1.auO[n3++] = f6 + f9;
                wz_1.auO[n3++] = f5 + f8;
                wz_1.auO[n3++] = f6 + f9;
                wz_1.auO[n3++] = f5 + f8;
                wz_1.auO[n3++] = f6 - f9;
            }
            f4 = akk_23.IT;
            wz_1.auP[n2++] = akk_23.IQ;
            wz_1.auP[n2++] = akk_23.IR;
            wz_1.auP[n2++] = akk_23.IS;
            wz_1.auP[n2++] = f4;
            wz_1.auP[n2++] = akk_23.IQ;
            wz_1.auP[n2++] = akk_23.IR;
            wz_1.auP[n2++] = akk_23.IS;
            wz_1.auP[n2++] = f4;
            wz_1.auP[n2++] = akk_23.IQ;
            wz_1.auP[n2++] = akk_23.IR;
            wz_1.auP[n2++] = akk_23.IS;
            wz_1.auP[n2++] = f4;
            wz_1.auP[n2++] = akk_23.IQ;
            wz_1.auP[n2++] = akk_23.IR;
            wz_1.auP[n2++] = akk_23.IS;
            wz_1.auP[n2++] = f4;
            wz_1.auQ[n4++] = akk_23.bsB;
            wz_1.auQ[n4++] = akk_23.bsD;
            wz_1.auQ[n4++] = akk_23.bsB;
            wz_1.auQ[n4++] = akk_23.bsA;
            wz_1.auQ[n4++] = akk_23.bsC;
            wz_1.auQ[n4++] = akk_23.bsA;
            wz_1.auQ[n4++] = akk_23.bsC;
            wz_1.auQ[n4++] = akk_23.bsD;
            ++n5;
        }
        this.aA.b(auO, n3);
        this.aA.d(auP, n2);
        this.aA.f(auQ, n4);
        this.aA.dz(n5 * 4);
    }

    public void a(db_2 db_22) {
        this.AH.a(db_22);
    }

    public void a(azo_0 azo_02) {
        this.auY = azo_02;
    }

    protected abstract boolean CR();

    protected abstract boolean CS();

    protected abstract void a(akk_2 var1);

    protected abstract void a(akk_2 var1, float var2);
}

