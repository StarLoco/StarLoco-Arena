/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from aMG
 */
public final class amg_2 {
    private Entity3D AH;
    protected vP AC = null;
    protected akq_1[] AD = new akq_1[4];
    protected int[] AG = new int[32];
    protected int aG = 0;
    protected int aH = 0;
    protected int fc = 0;
    protected int fb = 0;
    protected kx_1 aFh = kx_1.FR;
    protected boolean AE = false;

    public int getX() {
        return this.aG;
    }

    public void setX(int n2) {
        this.aG = n2;
    }

    public int getY() {
        return this.aH;
    }

    public void setY(int n2) {
        this.aH = n2;
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

    public kx_1 getShape() {
        return this.aFh;
    }

    public void setShape(kx_1 kx_12) {
        this.aFh = kx_12;
    }

    public void a(akq_1 akq_12, akq_1 akq_13, akq_1 akq_14, akq_1 akq_15) {
        this.AD[0] = akq_12;
        this.AD[1] = akq_13;
        this.AD[2] = akq_14;
        this.AD[3] = akq_15;
        if (this.AD[0] != null && this.AD[1] != null && this.AD[2] != null && this.AD[3] != null) {
            this.AE = true;
        }
    }

    public boolean Gk() {
        return this.AE;
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

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        Object object;
        int n2 = insets.left + insets2.left + insets3.left + this.aG;
        int n3 = insets.bottom + insets2.bottom + insets3.bottom + this.aH;
        this.fb = dimension.width;
        this.fc = dimension.height;
        this.AH.clear();
        if (this.aFh == kx_1.FT) {
            object = new sj_0();
            ((sj_0)object).fb = this.fb;
            ((sj_0)object).fc = this.fc;
            this.AH.a((ub_0)object);
            this.AH.b(new na_0());
        }
        object = this.AC == null ? vP.atL : this.AC;
        this.b(n2, n3 + this.fc, this.fb, this.fc, this.AD[0], (vP)object);
        this.b(n2 + this.fb, n3 + this.fc, this.fb, this.fc, this.AD[1], (vP)object);
        this.b(n2, n3, this.fb, this.fc, this.AD[2], (vP)object);
        this.b(n2 + this.fb, n3, this.fb, this.fc, this.AD[3], (vP)object);
    }

    public void j() {
        this.AC = null;
        this.AD = null;
        this.AH.HF();
        this.AH = null;
    }

    public void b() {
        assert (this.AH == null);
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
    }

    public final Entity getEntity() {
        return this.AH;
    }

    private void b(int n2, int n3, int n4, int n5, akq_1 akq_12, vP vP2) {
        if (n4 == 0 || n5 == 0) {
            return;
        }
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        gLGeometrySprite.x(n3, n2);
        gLGeometrySprite.setSize(n4, n5);
        gLGeometrySprite.setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        ef_1 ef_12 = null;
        if (akq_12 != null) {
            gLGeometrySprite.a(akq_12.Hy(), akq_12.Hw(), akq_12.Hz(), akq_12.Hx(), akq_12.getRotation());
            ef_12 = akq_12.jI();
        }
        this.AH.a(gLGeometrySprite, ef_12, null);
    }
}

