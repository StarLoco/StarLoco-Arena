/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import com.ankamagames.framework.graphics.engine.geometry.GeometrySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometryMesh;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import java.awt.Insets;
import java.util.ArrayList;

/*
 * Renamed from VO
 */
public final class vo_2 {
    private vP AC = null;
    private ArrayList bTh = new ArrayList();
    private ArrayList bTi = new ArrayList();
    private int aG = 0;
    private int aH = 0;
    private int bTj = 0;
    private int bTk = 0;
    private boolean AE = false;
    private Entity3D AH;
    static int aFm = 0;

    public final int getX() {
        return this.aG;
    }

    public final void setX(int n2) {
        this.aG = n2;
    }

    public final int getY() {
        return this.aH;
    }

    public final void setY(int n2) {
        this.aH = n2;
    }

    public final int getHeight() {
        return this.bTk;
    }

    public final void setHeight(int n2) {
        this.bTk = n2;
    }

    public final int getWidth() {
        return this.bTj;
    }

    public final void setWidth(int n2) {
        this.bTj = n2;
    }

    public void a(xt_1 xt_12) {
        this.bTh.add(xt_12);
        this.c(xt_12);
    }

    public void a(bt_2 bt_22) {
        this.bTi.add(bt_22);
        this.b(bt_22);
    }

    public void b(xt_1 xt_12) {
        int n2 = this.bTh.indexOf(xt_12);
        this.bTh.remove(n2);
        this.AH.c(this.AH.ma(n2));
    }

    public final boolean Gk() {
        return this.AE;
    }

    public final void setModulationColor(vP vP2) {
        if (vP2 == this.AC) {
            return;
        }
        if (vP2 != null) {
            this.AH.setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        } else {
            this.AH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.AC = vP2;
    }

    public final vP getModulationColor() {
        return this.AC;
    }

    public void clear() {
        for (int j = this.AH.aFz() - 1; j >= 0; --j) {
            this.AH.c(this.AH.ma(0));
        }
        this.bTh.clear();
        this.bTi.clear();
    }

    public void a(agj_1 agj_12, Insets insets, Insets insets2, Insets insets3) {
        Object object;
        int n2;
        this.AH.setVisible(true);
        int n3 = insets.left + insets2.left + insets3.left;
        int n4 = insets.bottom + insets2.bottom + insets3.bottom;
        if (this.AC != null) {
            this.AH.setColor(this.AC.Cp(), this.AC.Cq(), this.AC.Cr(), this.AC.getAlpha());
        } else {
            this.AH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        int n5 = this.bTh.size();
        for (n2 = 0; n2 < n5; ++n2) {
            object = (xt_1)this.bTh.get(n2);
            int n6 = ((xt_1)object).getX() * this.bTj + this.aG + n3;
            int n7 = ((xt_1)object).getY() * this.bTk + this.aH + n4 + agj_12.height;
            GeometrySprite geometrySprite = (GeometrySprite)this.AH.ma(n2);
            geometrySprite.x(n7, n6);
        }
        n5 = this.bTi.size();
        for (n2 = 0; n2 < n5; ++n2) {
            object = (bt_2)this.bTi.get(n2);
            GeometryMesh geometryMesh = (GeometryMesh)this.AH.ma(n2 + this.bTh.size());
            this.a(geometryMesh.ab(), (bt_2)object);
        }
    }

    public final void j() {
        this.bTh.clear();
        this.bTi.clear();
        this.AC = null;
        this.AH.HF();
        this.AH = null;
    }

    private void c(xt_1 xt_12) {
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        akq_1 akq_12 = xt_12.getPixmap();
        gLGeometrySprite.k(akq_12.Hy(), akq_12.Hw(), akq_12.Hz(), akq_12.Hx());
        gLGeometrySprite.setSize(akq_12.getWidth(), akq_12.getHeight());
        this.AH.a(gLGeometrySprite, akq_12.jI(), null);
    }

    private void a(VertexBufferPCT vertexBufferPCT, bt_2 bt_22) {
        float[] fArray = new float[]{bt_22.getX() + this.aG, bt_22.getY() + this.aH, bt_22.getX() + bt_22.getWidth() + this.aG, bt_22.getY() + this.aH, bt_22.getX() + bt_22.getWidth() + this.aG, bt_22.getY() + this.aH, bt_22.getX() + bt_22.getWidth() + this.aG, bt_22.getY() + bt_22.getHeight() + this.aH, bt_22.getX() + bt_22.getWidth() + this.aG, bt_22.getY() + bt_22.getHeight() + this.aH, bt_22.getX() + this.aG, bt_22.getY() + bt_22.getHeight() + this.aH, bt_22.getX() + this.aG, bt_22.getY() + bt_22.getHeight() + this.aH, bt_22.getX() + this.aG, bt_22.getY() + this.aH};
        vertexBufferPCT.f(fArray);
    }

    private void b(bt_2 bt_22) {
        int n2;
        GLGeometryMesh gLGeometryMesh = new GLGeometryMesh();
        int n3 = 16;
        VertexBufferPCT vertexBufferPCT = new VertexBufferPCT(n3);
        ams_1 ams_12 = new ams_1();
        ams_12.setSize(n3);
        short[] sArray = new short[n3];
        for (n2 = 0; n2 < sArray.length; n2 = (int)((short)(n2 + 1))) {
            sArray[n2] = n2;
        }
        ams_12.c(sArray, 0, n3);
        vertexBufferPCT.dz(n3);
        int n4 = vertexBufferPCT.fq();
        for (n2 = 0; n2 < n4; ++n2) {
            vertexBufferPCT.a(n2, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        gLGeometryMesh.a(jB.AV, vertexBufferPCT, ams_12, false);
        this.AH.b(gLGeometryMesh);
    }

    public final void b() {
        assert (this.AH == null);
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
    }

    public final Entity getEntity() {
        return this.AH;
    }
}

