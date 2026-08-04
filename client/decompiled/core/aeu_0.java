/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.opengl.text.GLGeometryBackground;
import com.ankamagames.framework.graphics.engine.opengl.text.GLGeometryText;
import com.ankamagames.framework.graphics.engine.text.EntityText;
import java.awt.Dimension;
import java.awt.Insets;

/*
 * Renamed from aEu
 */
public class aeu_0
extends aei_2 {
    private atf_0 dzO = new atf_0();
    private aas_0 dzP = new aas_0();
    private EntityText bCg;
    private boolean bUh = true;
    private int aG;
    private int aH;
    private int fb;
    private int fc;

    public aeu_0() {
        super.setInsets(new Insets(this.dzO.wY(), this.dzO.wW(), this.dzO.wV(), this.dzO.wX()));
    }

    public void setSparkAngle(float f) {
        this.dzO.setSparkAngle(f);
        this.aPU();
    }

    public void setDisplaySpark(boolean bl2) {
        this.dzO.dW(bl2);
        this.aPU();
    }

    public void a(Dimension dimension, Insets insets, Insets insets2, Insets insets3) {
        this.aG = insets.left;
        this.aH = insets.bottom;
        this.fb = dimension.width - insets.left - insets.right;
        this.fc = dimension.height - insets.bottom - insets.top;
        this.bCg.a(new agu_0(this.aG, this.aH, 0.0f));
        this.bCg.setMinWidth(this.fb - (insets2.left + insets2.right));
        this.bCg.fa(this.fc - (insets2.bottom + insets2.top));
    }

    private void aPU() {
        this.bCg.KW().a(this.dzO.wZ(), this.dzO.xa());
    }

    public Entity getEntity() {
        return this.bCg;
    }

    public void j() {
        this.bCg.HF();
        this.bCg = null;
    }

    public void b() {
        assert (this.bCg == null);
        this.bCg = new EntityText();
        GLGeometryText gLGeometryText = new GLGeometryText();
        GLGeometryBackground gLGeometryBackground = new GLGeometryBackground();
        this.bCg.a(gLGeometryText);
        this.bCg.a(gLGeometryBackground);
        gLGeometryBackground.a(this.dzO.wZ(), this.dzO.xa());
        gLGeometryBackground.f(this.dzO.xb());
        gLGeometryBackground.e(this.dzO.xc());
        gLGeometryBackground.e(this.dzO.wW(), this.dzO.wX(), this.dzO.wY(), this.dzO.wV());
        gLGeometryText.HF();
        gLGeometryBackground.HF();
    }

    public void setInsets(Insets insets) {
    }
}

