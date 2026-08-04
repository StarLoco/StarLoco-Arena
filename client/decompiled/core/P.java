/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;

public class P
extends vz_1 {
    private EntitySprite aY;
    private vP aZ;
    private boolean ba = true;

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
        this.aY.setColor(f, f2, f3, f4);
    }

    public final vP getColor() {
        return this.aZ;
    }

    public void setFullCirclePercentage(float f) {
    }

    public float getFullCirclePercentage() {
        return 0.0f;
    }

    public boolean isHorizontal() {
        return this.ba;
    }

    public void setHorizontal(boolean bl2) {
        this.ba = bl2;
    }

    public void a(int n2, int n3, int n4, int n5, float f) {
        n4 = (int)((float)n4 * (this.ba ? f : 1.0f));
        n5 = (int)((float)n5 * (this.ba ? 1.0f : f));
        int n6 = n2;
        int n7 = n3 + n5;
        this.aY.x(n7, n6);
        this.aY.setSize(n4, n5);
    }

    public final Entity getEntity() {
        return this.aY;
    }

    public final void b() {
        assert (this.aY == null);
        this.aY = (EntitySprite)yW.FL().a(EntitySprite.it(), EntitySprite.class);
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        this.aY.a(gLGeometrySprite);
        gLGeometrySprite.HF();
    }

    public final void j() {
        super.j();
        this.aZ = null;
        this.aY.HF();
        this.aY = null;
    }
}

