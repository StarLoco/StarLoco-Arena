/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.Entity3D;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from jy
 */
public abstract class jy_1
extends aei_2 {
    protected vP AC = null;
    protected akq_1[] AD;
    protected boolean AE = false;
    protected short[] AF;
    protected int[] AG;
    protected Entity3D AH;

    public abstract void a(Dimension var1, Insets var2, Insets var3, Insets var4);

    public final Entity getEntity() {
        return this.AH;
    }

    public vP getModulationColor() {
        return this.AC;
    }

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
        if (this.AC != null) {
            this.AH.setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        } else {
            this.AH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public void j() {
        this.AH.HF();
        this.AH = null;
        this.AC = null;
    }

    public void b() {
        assert (this.AH == null);
        this.AH = (Entity3D)yW.FL().a(Entity3D.it(), Entity3D.class);
    }

    protected void a(int n2, int n3, int n4, int n5, akq_1 akq_12) {
        if (n4 == 0 || n5 == 0) {
            return;
        }
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        gLGeometrySprite.x(n3, n2);
        gLGeometrySprite.setSize(n4, n5);
        if (this.AC != null) {
            gLGeometrySprite.setColor(this.AC.Cp(), this.AC.Cq(), this.AC.Cr(), this.AC.getAlpha());
        } else {
            gLGeometrySprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        ef_1 ef_12 = null;
        if (akq_12 != null) {
            gLGeometrySprite.a(akq_12.Hy(), akq_12.Hw(), akq_12.Hz(), akq_12.Hx(), akq_12.getRotation());
            ef_12 = akq_12.jI();
        }
        this.AH.a(gLGeometrySprite, ef_12, null);
    }
}

