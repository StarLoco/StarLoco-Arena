/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import java.awt.Dimension;
import java.awt.Insets;

public class aaH
extends aqn_0 {
    protected EntitySprite aY;
    protected vP aZ;
    protected vP AC;

    public void q(float[] fArray) {
        assert (fArray != null) : "Color components array can't be null";
        assert (fArray.length == 4) : "Color components size must be equal to 4";
        this.aZ = new vP(fArray[0], fArray[1], fArray[2], fArray[3]);
        this.aY.setColor(this.aZ);
    }

    public void setColor(vP vP2) {
        assert (vP2 != null) : "Color can't be null";
        if (this.aZ == vP2) {
            return;
        }
        this.aZ = vP2;
        this.aq();
    }

    private void aq() {
        float f = this.aZ != null ? this.aZ.Cp() : 1.0f;
        float f2 = this.aZ != null ? this.aZ.Cq() : 1.0f;
        float f3 = this.aZ != null ? this.aZ.Cr() : 1.0f;
        float f4 = this.aZ != null ? this.aZ.getAlpha() : 1.0f;
        this.aY.setColor(f *= this.AC != null ? this.AC.Cp() : 1.0f, f2 *= this.AC != null ? this.AC.Cq() : 1.0f, f3 *= this.AC != null ? this.AC.Cr() : 1.0f, f4 *= this.AC != null ? this.AC.getAlpha() : 1.0f);
    }

    public final vP getColor() {
        if (this.aZ == null) {
            return null;
        }
        return this.aZ;
    }

    public vP getModulationColor() {
        return this.AC;
    }

    public void setModulationColor(vP vP2) {
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
        this.aq();
    }

    public final EntitySprite apq() {
        return this.aY;
    }

    public void j() {
        this.AC = null;
        this.aZ = null;
        this.aY.HF();
        this.aY = null;
    }

    public void b() {
        assert (this.aY == null) : "Object is already checked out";
        this.aY = (EntitySprite)yW.FL().a(EntitySprite.it(), EntitySprite.class);
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        this.aY.a(gLGeometrySprite);
        gLGeometrySprite.HF();
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        int n2 = dimension.width - insets.right - insets2.right - insets.left - insets2.left;
        int n3 = dimension.height - insets.top - insets2.top - insets.bottom - insets2.bottom;
        int n4 = insets.left + insets2.left;
        int n5 = insets.bottom + insets2.bottom + n3;
        this.aY.x(n5, n4);
        this.aY.setSize(n2, n3);
    }
}

