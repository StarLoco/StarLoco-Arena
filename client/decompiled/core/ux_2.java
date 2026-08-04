/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

/*
 * Renamed from uX
 */
public final class ux_2 {
    private static final short arB = 21845;
    private EntityGroup arC;
    private Entity3D arD;
    private Entity3D arE;
    private Entity3D arF;
    protected vP AC = null;
    private int arG = 0;
    private int fb;
    private int fc;
    private abs_1 arH = null;

    public void clear() {
        this.arH = null;
    }

    public void a(abs_1 abs_12) {
        this.arH = abs_12;
    }

    public int getHeight() {
        return this.fc;
    }

    public void setHeight(int n2) {
        this.fc = n2;
    }

    public int getWidth() {
        return this.fb;
    }

    public void setWidth(int n2) {
        this.fb = n2;
    }

    public void setCellWidth(int n2) {
        this.arG = n2;
    }

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
    }

    public vP getModulationColor() {
        return this.AC;
    }

    private static int Z(int n2, int n3) {
        return n2 / 2 + ux_2.aa(n2, n3);
    }

    private static int aa(int n2, int n3) {
        return n2 * n3;
    }

    private float Ba() {
        return this.arH != null ? -this.arH.apZ() : 0.0f;
    }

    private float Bb() {
        if (this.arH == null) {
            return 1.0f;
        }
        return (float)this.fc / (this.arH.aqa() - this.arH.apZ());
    }

    public float[] Bc() {
        int n2;
        int n3 = this.arH != null ? (int)this.arH.apZ() : 0;
        int n4 = this.arH != null ? (int)this.arH.aqa() : 0;
        int n5 = n3 / 5 * 5;
        int n6 = n4 / 5 * 5;
        int n7 = (n6 - n5) / 5 + 1;
        ps_0 ps_02 = new ps_0();
        for (n2 = 0; n2 < n7; ++n2) {
            float f = n5 + 5 * n2;
            ps_02.add(0.0f);
            ps_02.add(f);
            ps_02.add(this.fb);
            ps_02.add(f);
        }
        if (this.arH != null) {
            int n8 = this.arH.aqb().size();
            for (n2 = 0; n2 < n8; ++n2) {
                float f = this.arG * n2;
                ps_02.add(f);
                ps_02.add(n3);
                ps_02.add(f);
                ps_02.add(n4);
            }
        }
        return ps_02.uD();
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        Object object;
        Object object2;
        vP vP2;
        int n2 = insets.left + insets2.left + insets3.left;
        int n3 = insets.bottom + insets2.bottom + insets3.bottom;
        this.fb = dimension.width - n2;
        this.fc = dimension.height - n3;
        this.arD.clear();
        this.arF.clear();
        this.arE.clear();
        avz avz2 = (avz)this.arF.aUM().aI(0);
        avz2.m(1.0f, this.Bb(), 1.0f);
        avz2.e(n2, this.Ba() * this.Bb() + (float)n3, 0.0f);
        this.arF.aUM().b(0, avz2);
        avz2 = (avz)this.arE.aUM().aI(0);
        avz2.m(1.0f, this.Bb(), 1.0f);
        avz2.e(n2, this.Ba() * this.Bb() + (float)n3, 0.0f);
        this.arE.aUM().b(0, avz2);
        vP vP3 = vP2 = this.AC == null ? vP.atL : this.AC;
        if (this.arH != null) {
            object2 = this.arH.aqb();
            int n4 = ((ArrayList)object2).size();
            for (int j = 0; j < n4; ++j) {
                avL avL2 = (avL)((ArrayList)object2).get(j);
                akq_1 akq_12 = avL2.aJe();
                Object object3 = object = avL2.getModulationColor() != null ? vP.b(avL2.getModulationColor(), vP2) : vP2;
                if (akq_12 != null) {
                    this.a(n2 + ux_2.aa(this.arG, j), n3 + this.fc, this.arG, this.fc, akq_12, (vP)object);
                    continue;
                }
                this.a(n2 + ux_2.aa(this.arG, j), n3 + this.fc, this.arG, this.fc, (vP)object);
            }
        }
        object2 = this.Bc();
        int[] nArray = new int[((float[])object2).length / 2];
        this.a((float[])object2, new vP[]{new vP(0.0f, 0.0f, 0.0f, 0.5f)}, nArray, 1.0f, jB.AV, this.arE);
        if (this.arH != null) {
            ArrayList arrayList = this.arH.aqd();
            int n5 = arrayList.size();
            for (int j = 0; j < n5; ++j) {
                object = (c_0)arrayList.get(j);
                this.a(((c_0)object).a(this.arG), ((c_0)object).e(), ((c_0)object).f(), 1.0f, jB.AY, this.arF);
            }
            ArrayList arrayList2 = this.arH.aqc();
            int n6 = arrayList2.size();
            for (n5 = 0; n5 < n6; ++n5) {
                akj_2 akj_22 = (akj_2)arrayList2.get(n5);
                this.a(akj_22.a(this.arG), akj_22.e(), akj_22.f(), 2.0f, jB.AW, this.arF);
                this.a(akj_22.a(this.arG), akj_22.e(), akj_22.f(), 2.0f, jB.AU, this.arF);
            }
        }
    }

    public void j() {
        this.arC.HF();
        this.arC = null;
        this.AC = null;
        this.arH = null;
    }

    public void b() {
        assert (this.arC == null);
        this.arC = (EntityGroup)yW.FL().a(EntityGroup.it(), EntityGroup.class);
        this.arD = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        this.arE = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        this.arF = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        this.arC.i(this.arD);
        this.arC.i(this.arE);
        this.arC.i(this.arF);
        this.arE.a(new kn_2(this));
        this.arE.b(new kl_2(this));
        this.arF.a(new kr_1(this));
        this.arF.b(new kq_1(this));
        this.arF.aUM().a(new avz());
        this.arE.aUM().a(new avz());
    }

    public final Entity getEntity() {
        return this.arC;
    }

    private int a(float[] fArray, vP[] vPArray, int[] nArray, float f, jB jB2, Entity3D entity3D) {
        int n2;
        GeometryMesh geometryMesh = (GeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
        int n3 = fArray.length / 2;
        VertexBufferPCT vertexBufferPCT = new VertexBufferPCT(n3);
        ams_1 ams_12 = new ams_1();
        ams_12.setSize(n3);
        short[] sArray = new short[n3];
        for (n2 = 0; n2 < sArray.length; n2 = (int)((short)(n2 + 1))) {
            sArray[n2] = n2;
        }
        ams_12.c(sArray, 0, n3);
        vertexBufferPCT.dz(n3);
        vertexBufferPCT.f(fArray);
        int n4 = vertexBufferPCT.fq();
        for (n2 = 0; n2 < n4; ++n2) {
            vP vP2 = vPArray[nArray[n2]];
            vertexBufferPCT.a(n2, vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        }
        geometryMesh.a(jB2, vertexBufferPCT, ams_12, false);
        geometryMesh.b(f);
        return entity3D.b(geometryMesh);
    }

    private void a(int n2, int n3, int n4, int n5, akq_1 akq_12, vP vP2) {
        if (n4 == 0 || n5 == 0) {
            return;
        }
        float f = 0.0f;
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        gLGeometrySprite.x((float)n3 - (float)n5 / 2.0f, n2 - n4 / 2);
        gLGeometrySprite.setSize(n4, n5);
        gLGeometrySprite.setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        ef_1 ef_12 = null;
        if (akq_12 != null) {
            gLGeometrySprite.a(akq_12.Hy(), akq_12.Hw(), akq_12.Hz(), akq_12.Hx(), akq_12.getRotation());
            ef_12 = akq_12.jI();
        }
        this.arD.a(gLGeometrySprite, ef_12, null);
    }

    private void a(int n2, int n3, int n4, int n5, vP vP2) {
        int n6;
        if (n4 == 0 || n5 == 0) {
            return;
        }
        GeometryMesh geometryMesh = (GeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
        float[] fArray = new float[]{n2, n3 - n5, n2 + n4, n3 - n5, n2 + n4, n3, n2, n3};
        VertexBufferPCT vertexBufferPCT = new VertexBufferPCT(4);
        ams_1 ams_12 = new ams_1();
        ams_12.setSize(4);
        short[] sArray = new short[4];
        for (n6 = 0; n6 < sArray.length; n6 = (int)((short)(n6 + 1))) {
            sArray[n6] = n6;
        }
        ams_12.c(sArray, 0, 4);
        vertexBufferPCT.dz(4);
        vertexBufferPCT.f(fArray);
        int n7 = vertexBufferPCT.fq();
        for (n6 = 0; n6 < n7; ++n6) {
            vertexBufferPCT.a(n6, vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        }
        geometryMesh.a(jB.Ba, vertexBufferPCT, ams_12, false);
        this.arD.b(geometryMesh);
    }

    static /* synthetic */ int ab(int n2, int n3) {
        return ux_2.Z(n2, n3);
    }
}

