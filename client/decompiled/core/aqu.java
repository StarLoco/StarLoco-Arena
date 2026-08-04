/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;

public class aqu
extends vz_1 {
    private static byte cOf = (byte)32;
    private Entity3D AH;
    private VertexBufferPCT aA;
    private ams_1 az;
    private vP aZ;
    private float cOg = 0.5f;
    private float cOh = 1.0f;
    private byte cOi = cOf;
    private float cOj = 1.5707964f;
    private float cOk = 1.0f;
    private akq_1 arn = null;

    public float getDeltaAngle() {
        return this.cOj;
    }

    public void setDeltaAngle(float f) {
        this.cOj = f;
    }

    public final void setColor(vP vP2) {
        if (this.aZ == vP2) {
            return;
        }
        this.aZ = vP2;
        this.aq();
    }

    public void setModulationColor(vP vP2) {
        super.setModulationColor(vP2);
        this.aq();
    }

    private void aq() {
        float f;
        float f2;
        float f3;
        float f4;
        if (this.aZ == null) {
            f4 = 1.0f;
            f3 = 1.0f;
            f2 = 1.0f;
            f = 1.0f;
        } else {
            f = this.aZ.Cp();
            f2 = this.aZ.Cq();
            f3 = this.aZ.Cr();
            f4 = this.aZ.getAlpha();
        }
        if (this.AC != null) {
            f *= this.AC.Cp();
            f2 *= this.AC.Cq();
            f3 *= this.AC.Cr();
            f4 *= this.AC.getAlpha();
        }
        for (int j = 0; j < this.aA.fp(); ++j) {
            this.aA.a(j, f, f2, f3, f4);
        }
    }

    public float getFullCirclePercentage() {
        return this.cOk;
    }

    public void setFullCirclePercentage(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.cOk = f;
    }

    public final vP getColor() {
        return this.aZ;
    }

    public float aDZ() {
        return this.cOg;
    }

    public void ba(float f) {
        this.cOg = f;
    }

    public float aEa() {
        return this.cOh;
    }

    public void bb(float f) {
        this.cOh = f;
    }

    public boolean isHorizontal() {
        return false;
    }

    public void setHorizontal(boolean bl2) {
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19, akq_1 akq_110) {
        this.arn = akq_16;
        this.AH.a(0, this.arn.jI());
        this.a(this.aA, this.aA.fq());
    }

    public void a(int n2, int n3, int n4, int n5, float f) {
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        int n6;
        float f8 = -((float)(Math.PI * 2 * (double)this.cOk)) / (float)this.cOi;
        int n7 = (int)Math.floor((float)this.cOi * f) + 1;
        float f9 = -((float)(Math.PI * 2 * (double)this.cOk * (double)f));
        int n8 = (n7 + (n7 > this.cOi ? 0 : 1)) * 2;
        if (n8 < 4) {
            this.dC(0);
            return;
        }
        this.dC(n8);
        int n9 = n4 / 2 + n2;
        int n10 = n5 / 2 + n3;
        float f10 = (float)Math.min(n4, n5) / 2.0f;
        float f11 = f10 * this.cOg;
        float f12 = f10 * this.cOh;
        for (n6 = 0; n6 < n7; ++n6) {
            f7 = (float)Math.cos(f8 * (float)n6 + this.cOj);
            f6 = (float)Math.sin(f8 * (float)n6 + this.cOj);
            f5 = f7 * f11 + (float)n9;
            f4 = f6 * f11 + (float)n10;
            f3 = f7 * f12 + (float)n9;
            f2 = f6 * f12 + (float)n10;
            this.aA.b(n6 * 2, f5, f4);
            this.aA.b(n6 * 2 + 1, f3, f2);
        }
        if (n7 < this.cOi + 1) {
            n6 = n7;
            f7 = (float)Math.cos(f9 + this.cOj);
            f6 = (float)Math.sin(f9 + this.cOj);
            f5 = f7 * f11 + (float)n9;
            f4 = f6 * f11 + (float)n10;
            f3 = f7 * f12 + (float)n9;
            f2 = f6 * f12 + (float)n10;
            this.aA.b(n6 * 2, f5, f4);
            this.aA.b(n6 * 2 + 1, f3, f2);
        }
    }

    public final Entity getEntity() {
        return this.AH;
    }

    private void dC(int n2) {
        if (n2 != this.aA.fq()) {
            this.a(this.aA, n2);
            this.a(this.az, n2);
        }
    }

    private void a(VertexBufferPCT vertexBufferPCT, int n2) {
        vertexBufferPCT.dz(n2);
        if (this.arn != null) {
            boolean bl2 = false;
            for (int j = 0; j < n2; ++j) {
                boolean bl3 = j % 4 < 2;
                bl2 = !bl2;
                float f = bl3 ? this.arn.Hx() : this.arn.Hw();
                float f2 = bl2 ? this.arn.Hy() : this.arn.Hz();
                vertexBufferPCT.a(j, f, f2);
            }
        }
    }

    private void a(ams_1 ams_12, int n2) {
        ams_12.setSize(n2);
        short[] sArray = new short[n2];
        for (int n3 = 0; n3 < sArray.length; n3 = (int)((short)(n3 + 1))) {
            sArray[n3] = n3;
        }
        ams_12.c(sArray, 0, n2);
    }

    public final void b() {
        assert (this.AH == null);
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        GeometryMesh geometryMesh = (GeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
        int n2 = (this.cOi + 1) * 2;
        this.aA = new VertexBufferPCT(n2);
        this.az = new ams_1();
        this.a(this.az, n2);
        this.aA.dz(n2);
        this.setColor(new vP(vP.atL));
        geometryMesh.a(jB.AY, this.aA, this.az, false);
        this.AH.b(geometryMesh);
    }

    public final void j() {
        super.j();
        this.AH.HF();
        this.AH = null;
        this.aZ = null;
        this.arn = null;
    }
}

