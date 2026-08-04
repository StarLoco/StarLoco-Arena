/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public abstract class ayb
extends qs_2 {
    protected final mk_1 dkx;
    private float dky;
    private float dkz;
    private float dkA;
    private boolean dkB;
    private final EntitySprite dkC;

    public ayb(mk_1 mk_12) {
        this.dkx = mk_12;
        this.dky = 25.0f;
        this.dkz = 0.0f;
        this.dkA = 2.0f;
        this.dkB = false;
        this.dkC = new EntitySprite();
        this.dkC.a(new GLGeometrySprite());
        this.dkC.setColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public void init(GLAutoDrawable gLAutoDrawable) {
        super.init(gLAutoDrawable);
        this.dkx.Zi();
    }

    public void h(GL gL) {
        if (!yb_2.amk().aml() && this.aeu) {
            gL.glClearColor(this.aet.Cp(), this.aet.Cq(), this.aet.Cr(), this.aet.getAlpha());
            this.aeu = false;
        }
        if (this.dkz <= 0.0f) {
            super.h(gL);
            return;
        }
        gL.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        int n2 = (int)(this.dkz * this.bIA / 100.0f);
        super.h(gL);
        db_2 db_22 = arX.cQT.iE();
        db_22.c(Matrix44.bEn);
        this.dkC.x(-this.bIA / 2.0f + (float)(n2 / 2), -this.bIz / 2.0f);
        this.dkC.setSize((int)this.bIz, n2 / 2);
        this.dkC.a(db_22);
        this.dkC.x(this.bIA / 2.0f, -this.bIz / 2.0f);
        this.dkC.setSize((int)this.bIz, n2 / 2);
        this.dkC.a(db_22);
        gL.glClearColor(this.aet.Cp(), this.aet.Cq(), this.aet.Cr(), this.aet.getAlpha());
    }

    public void bI(int n2) {
        super.bI(n2);
        if (this.dkB) {
            this.dkz += this.dky * this.dkA * (float)n2 / 1000.0f;
            if (this.dkz > this.dky) {
                this.dkz = this.dky;
            }
        } else {
            this.dkz -= this.dky * this.dkA * (float)n2 / 1000.0f;
            if (this.dkz < 0.0f) {
                this.dkz = 0.0f;
            }
        }
    }

    public final void a(aag_2 aag_22) {
        YR yR = this.vn();
        yR.bT(aag_22.getScreenWidth(), aag_22.getScreenHeight());
        yR.l(yR.oZ());
    }

    public void bp(float f) {
        this.dky = f;
    }

    public void bq(float f) {
        this.dkz = f;
    }

    public void br(float f) {
        this.dkA = f;
    }

    public void ex(boolean bl2) {
        this.dkB = bl2;
    }
}

