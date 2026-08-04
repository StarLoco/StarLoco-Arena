/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import com.ankamagames.framework.graphics.engine.transformer.BatchTransformer;
import java.awt.Insets;
import org.apache.log4j.Logger;

/*
 * Renamed from SM
 */
public final class sm_2 {
    protected static Logger a = Logger.getLogger(sm_2.class);
    private vP AC = null;
    private og_1 bLN = null;
    private int aG = 0;
    private int aH = 0;
    private int bLO = 0;
    private int bLP = 0;
    private int bLQ = 0;
    private int bLR = 0;
    private boolean bLS = true;
    private kx_1 aFh = kx_1.FR;
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
        return this.bLP;
    }

    public final void setHeight(int n2) {
        this.bLP = n2;
        this.bLS = true;
        if (this.bLN.abB() != null) {
            this.bLN.abB().da(n2);
        }
    }

    public final int getWidth() {
        return this.bLO;
    }

    public final void setWidth(int n2) {
        this.bLO = n2;
        this.bLS = true;
        if (this.bLN.abB() != null) {
            this.bLN.abB().cZ(n2);
        }
    }

    public final kx_1 getShape() {
        return this.aFh;
    }

    public final void setShape(kx_1 kx_12) {
        this.aFh = kx_12;
    }

    public final void a(og_1 og_12) {
        this.bLN = og_12;
    }

    public final og_1 afo() {
        return this.bLN;
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
        if (this.aY == null) {
            return;
        }
        if (this.bLN == null || this.bLN.jI() == null) {
            this.aY.setVisible(false);
            return;
        }
        this.aY.setVisible(true);
        this.bLN.abB().cZ(this.bLO);
        this.bLN.abB().da(this.bLP);
        int n2 = this.bLN.abB().wy();
        int n3 = this.bLN.abB().wz();
        if (n2 < this.bLO && n3 < this.bLP) {
            float f = (float)n2 / (float)n3;
            if (f > 1.0f) {
                n3 = Math.round((float)n3 * ((float)this.bLO / (float)n2));
                n2 = this.bLO;
            } else {
                n2 = Math.round((float)n2 * ((float)this.bLP / (float)n3));
                n3 = this.bLP;
            }
        }
        this.bLQ = n2;
        this.bLR = n3;
        this.bLS = false;
        int n4 = insets.left + insets2.left + insets3.left;
        int n5 = insets.bottom + insets2.bottom + insets3.bottom;
        this.aY.x(n5 + n3 + this.aH + (this.bLP - this.bLR) / 2, n4 + this.aG + (this.bLO - this.bLQ) / 2);
        this.aY.setSize(n2, n3);
        if (this.AC != null) {
            this.aY.setColor(this.AC);
        } else {
            this.aY.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.aY.setTexture(this.bLN.jI());
        avz avz2 = (avz)this.aY.aUM().aI(0);
        avz2.e(-this.bLO / 2, -this.bLP / 2, 0.0f);
        this.aY.aUM().b(0, avz2);
        avz2 = (avz)this.aY.aUM().aI(1);
        avz2.e(this.bLO / 2, this.bLP / 2, 0.0f);
        this.aY.aUM().b(1, avz2);
        if (this.aFi) {
            if (this.aFj) {
                this.aY.Hu().a(this.bLN.Hz(), this.bLN.Hx(), this.bLN.Hy(), this.bLN.Hw(), xd_1.azj);
            } else {
                this.aY.Hu().a(this.bLN.Hy(), this.bLN.Hx(), this.bLN.Hz(), this.bLN.Hw(), xd_1.azj);
            }
        } else if (this.aFj) {
            this.aY.Hu().a(this.bLN.Hz(), this.bLN.Hw(), this.bLN.Hy(), this.bLN.Hx(), xd_1.azj);
        } else {
            this.aY.Hu().a(this.bLN.Hy(), this.bLN.Hw(), this.bLN.Hz(), this.bLN.Hx(), xd_1.azj);
        }
    }

    public final void j() {
        this.bLN = null;
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

