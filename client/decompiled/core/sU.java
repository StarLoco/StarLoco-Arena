/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;

public class sU
extends vz_1 {
    private Entity3D AH;
    private VertexBufferPCT aA;
    private ams_1 az;
    private vP aZ;

    public float getDeltaAngle() {
        return 0.0f;
    }

    public void setDeltaAngle(float f) {
    }

    public void setPixmaps(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15, akq_1 akq_16, akq_1 akq_17, akq_1 akq_18, akq_1 akq_19, akq_1 akq_110) {
    }

    public final void setColor(vP vP2) {
        if (this.aZ == vP2) {
            return;
        }
        this.aZ = vP2;
        this.aq();
    }

    public final vP getColor() {
        return this.aZ;
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

    public boolean isHorizontal() {
        return false;
    }

    public void setHorizontal(boolean bl2) {
    }

    public void setFullCirclePercentage(float f) {
    }

    public float getFullCirclePercentage() {
        return 0.0f;
    }

    private float[] S(float f) {
        float f2;
        float[] fArray = null;
        fArray = f == 0.0f ? new float[]{} : (f <= 0.3f ? new float[6] : (f <= 0.6f ? new float[10] : (f <= 0.9f ? new float[14] : new float[18])));
        if (f > 0.0f) {
            f2 = Math.min(1.0f, f / 0.3f);
            fArray[0] = 0.5f;
            fArray[1] = 0.0f;
            fArray[2] = 0.5f - 0.3f * f2;
            fArray[3] = 0.3f * f2;
            fArray[4] = 1.0f - fArray[2];
            fArray[5] = fArray[3];
        }
        if (f > 0.3f) {
            f2 = Math.min(1.0f, (f - 0.3f) / 0.3f);
            fArray[6] = 0.2f * (1.0f - f2);
            fArray[7] = 0.3f + 0.3f * f2;
            fArray[8] = 1.0f - fArray[6];
            fArray[9] = fArray[7];
        }
        if (f > 0.6f) {
            f2 = Math.min(1.0f, (f - 0.6f) / 0.3f);
            fArray[10] = 0.0f;
            fArray[11] = 0.6f + 0.3f * f2;
            fArray[12] = 1.0f;
            fArray[13] = fArray[11];
        }
        if (f > 0.9f) {
            f2 = Math.min(1.0f, (f - 0.9f) / 0.1f);
            fArray[14] = 0.3f * f2;
            fArray[15] = 0.9f + 0.1f * f2;
            fArray[16] = 1.0f - fArray[14];
            fArray[17] = fArray[15];
        }
        return fArray;
    }

    public void a(int n2, int n3, int n4, int n5, float f) {
        float[] fArray = this.S(f);
        this.dC(fArray.length / 2);
        int n6 = fArray.length / 2;
        for (int j = 0; j < n6; ++j) {
            float f2 = (float)n2 + (float)n4 * fArray[j * 2];
            float f3 = (float)n3 + (float)n5 * fArray[j * 2 + 1];
            this.aA.b(j, f2, f3);
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
        this.aA = new VertexBufferPCT(9);
        this.az = new ams_1();
        this.a(this.az, 0);
        this.aA.dz(0);
        this.setColor(new vP(vP.atM));
        geometryMesh.a(jB.AY, this.aA, this.az, false);
        this.AH.b(geometryMesh);
    }

    public final void j() {
        super.j();
        this.aZ = null;
        this.AH.HF();
        this.AH = null;
    }
}

