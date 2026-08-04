/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import com.ankamagames.framework.graphics.engine.transformer.BatchTransformer;
import java.awt.Insets;

/*
 * Renamed from zo
 */
public final class zo_1 {
    private vP AC = null;
    private akq_1 arn = null;
    private int aG = 0;
    private int aH = 0;
    private int fc = 0;
    private int fb = 0;
    private kx_1 aFh = kx_1.FR;
    private boolean AE = false;
    private boolean aFi = false;
    private boolean aFj = false;
    private EntitySprite aY;
    private eu_2 aFk = new eu_2();
    private static agu_0 aFl = new agu_0(0.0f, 0.0f, 1.0f);
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
        return this.fc;
    }

    public final void setHeight(int n2) {
        this.fc = n2;
    }

    public final int getWidth() {
        return this.fb;
    }

    public final void setWidth(int n2) {
        this.fb = n2;
    }

    public final kx_1 getShape() {
        return this.aFh;
    }

    public final void setShape(kx_1 kx_12) {
        this.aFh = kx_12;
    }

    public final void setPixmap(akq_1 akq_12) {
        this.arn = akq_12;
        if (this.arn != null) {
            this.AE = true;
        }
    }

    public final akq_1 getPixmap() {
        return this.arn;
    }

    public final boolean Gk() {
        return this.AE;
    }

    public void ad(float f) {
        avz avz2 = (avz)this.aY.aUM().aI(1);
        this.aFk.a(aFl, f);
        avz2.f(this.aFk);
        this.aY.aUM().b(1, avz2);
    }

    public final void setModulationColor(vP vP2) {
        if (vP2 == this.AC) {
            return;
        }
        if (vP2 != null) {
            this.aY.setColor(vP2.Cp(), vP2.Cq(), vP2.Cr(), vP2.getAlpha());
        } else {
            this.aY.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.AC = vP2;
    }

    public final vP getModulationColor() {
        return this.AC;
    }

    public final boolean Gl() {
        return this.aFi;
    }

    public final void setFlipHorizontaly(boolean bl2) {
        this.aFi = bl2;
    }

    public final boolean Gm() {
        return this.aFj;
    }

    public final void setFlipVerticaly(boolean bl2) {
        this.aFj = bl2;
    }

    public void a(agj_1 agj_12, Insets insets, Insets insets2, Insets insets3) {
        if (this.arn == null || this.arn.jI() == null) {
            this.aY.setVisible(false);
            return;
        }
        this.aY.setVisible(true);
        int n2 = insets.left + insets2.left + insets3.left;
        int n3 = insets.bottom + insets2.bottom + insets3.bottom;
        this.aY.x(n3 + this.fc + this.aH, n2 + this.aG);
        this.aY.setSize(this.fb, this.fc);
        if (this.AC != null) {
            this.aY.setColor(this.AC);
        } else {
            this.aY.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.arn.azR();
        this.aY.setTexture(this.arn.jI());
        avz avz2 = (avz)this.aY.aUM().aI(0);
        avz2.e(-this.fb / 2, -this.fc / 2, 0.0f);
        this.aY.aUM().b(0, avz2);
        avz2 = (avz)this.aY.aUM().aI(1);
        avz2.e(this.fb / 2, this.fc / 2, 0.0f);
        this.aY.aUM().b(1, avz2);
        if (this.aFi) {
            if (this.aFj) {
                this.aY.Hu().a(this.arn.Hz(), this.arn.Hx(), this.arn.Hy(), this.arn.Hw(), this.arn.getRotation());
            } else {
                this.aY.Hu().a(this.arn.Hy(), this.arn.Hx(), this.arn.Hz(), this.arn.Hw(), this.arn.getRotation());
            }
        } else if (this.aFj) {
            this.aY.Hu().a(this.arn.Hz(), this.arn.Hw(), this.arn.Hy(), this.arn.Hx(), this.arn.getRotation());
        } else {
            this.aY.Hu().a(this.arn.Hy(), this.arn.Hw(), this.arn.Hz(), this.arn.Hx(), this.arn.getRotation());
        }
    }

    public final void j() {
        this.arn = null;
        this.AC = null;
        this.aY.HF();
        this.aY = null;
    }

    public final void b() {
        assert (this.aY == null);
        this.aY = (EntitySprite)yW.FL().a(EntitySprite.it(), EntitySprite.class);
        GLGeometrySprite gLGeometrySprite = new GLGeometrySprite();
        this.aY.a(gLGeometrySprite);
        gLGeometrySprite.HF();
        BatchTransformer batchTransformer = this.aY.aUM();
        batchTransformer.a(new avz());
        batchTransformer.a(new avz());
    }

    public final Entity getEntity() {
        return this.aY;
    }

    public eu_2 Gn() {
        return this.aFk;
    }
}

