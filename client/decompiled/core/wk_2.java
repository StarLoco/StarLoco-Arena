/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from Wk
 */
public class wk_2
extends aei_2 {
    private Entity3D AH;
    private vP aZ;
    private vP AC;
    private VertexBufferPCT aA;
    private static final short[] eX = new short[]{5, 4, 7, 6, 5, 7, 4, 11, 8, 7, 4, 8, 11, 10, 9, 8, 11, 9, 3, 2, 4, 5, 3, 4, 12, 13, 10, 11, 12, 10, 0, 1, 2, 3, 0, 2, 1, 15, 12, 2, 1, 12, 15, 14, 13, 12, 15, 13};

    public final void setColor(vP vP2) {
        assert (vP2 != null) : "Color can't be null";
        if (this.aZ == vP2) {
            return;
        }
        this.aZ = vP2;
        this.aq();
    }

    public final vP getColor() {
        return this.aZ;
    }

    public final void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
        this.aq();
    }

    public final vP getModulationColor() {
        return this.AC;
    }

    private void aq() {
        float f = this.aZ != null ? this.aZ.Cp() : 1.0f;
        float f2 = this.aZ != null ? this.aZ.Cq() : 1.0f;
        float f3 = this.aZ != null ? this.aZ.Cr() : 1.0f;
        float f4 = this.aZ != null ? this.aZ.getAlpha() : 1.0f;
        f *= this.AC != null ? this.AC.Cp() : 1.0f;
        f2 *= this.AC != null ? this.AC.Cq() : 1.0f;
        f3 *= this.AC != null ? this.AC.Cr() : 1.0f;
        f4 *= this.AC != null ? this.AC.getAlpha() : 1.0f;
        for (int j = 0; j < this.aA.fp(); ++j) {
            this.aA.a(j, f, f2, f3, f4);
        }
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        int n2 = insets.left + insets2.left;
        int n3 = insets.right + insets2.right;
        int n4 = insets.top + insets2.top;
        int n5 = insets.bottom + insets2.bottom;
        this.aA.b(0, insets.left, insets.bottom);
        this.aA.b(1, n2, insets.bottom);
        this.aA.b(2, n2, n5);
        this.aA.b(3, insets.left, n5);
        this.aA.b(4, n2, dimension.height - n4);
        this.aA.b(5, insets.left, dimension.height - n4);
        this.aA.b(6, insets.left, dimension.height - insets.top);
        this.aA.b(7, n2, dimension.height - insets.top);
        this.aA.b(8, dimension.width - n3, dimension.height - insets.top);
        this.aA.b(9, dimension.width - insets.right, dimension.height - insets.top);
        this.aA.b(10, dimension.width - insets.right, dimension.height - n4);
        this.aA.b(11, dimension.width - n3, dimension.height - n4);
        this.aA.b(12, dimension.width - n3, n5);
        this.aA.b(13, dimension.width - insets.right, n5);
        this.aA.b(14, dimension.width - insets.right, insets.bottom);
        this.aA.b(15, dimension.width - n3, insets.bottom);
    }

    public Entity getEntity() {
        return this.AH;
    }

    public void j() {
        this.aZ = null;
        this.AC = null;
        this.AH.HF();
        this.AH = null;
    }

    public void b() {
        assert (this.AH == null) : "Object is already checked out";
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
        GLGeometryMesh gLGeometryMesh = (GLGeometryMesh)yW.FL().a(GLGeometryMesh.it(), GLGeometryMesh.class);
        this.AH.b(gLGeometryMesh);
        ams_1 ams_12 = new ams_1(48);
        ams_12.g(eX);
        this.aA = new VertexBufferPCT(16);
        gLGeometryMesh.a(jB.AX, this.aA, ams_12, false);
    }
}

