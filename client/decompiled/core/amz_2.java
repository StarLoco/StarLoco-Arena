/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.opengl.text.GLGeometryBackground;
import com.ankamagames.framework.graphics.engine.opengl.text.GLGeometryText;
import com.ankamagames.framework.graphics.engine.text.EntityText;

/*
 * Renamed from amz
 */
public class amz_2 {
    private aln_1 cHz = null;
    private int bsW;
    private int bsX;
    private EntityText bCg = new EntityText();

    public amz_2(ma_1 ma_12, String string) {
        GLGeometryBackground gLGeometryBackground = new GLGeometryBackground();
        GLGeometryText gLGeometryText = new GLGeometryText();
        this.bCg.a(gLGeometryBackground);
        this.bCg.a(gLGeometryText);
        this.bCg.a(ma_12);
        this.bCg.setText(string);
        gLGeometryBackground.HF();
        gLGeometryText.HF();
    }

    public amz_2(ma_1 ma_12, boolean bl2, String string) {
        GLGeometryBackground gLGeometryBackground = new GLGeometryBackground();
        GLGeometryText gLGeometryText = new GLGeometryText();
        this.bCg.a(gLGeometryBackground);
        this.bCg.a(gLGeometryText);
        this.bCg.a(ma_12);
        this.bCg.setText(string);
        gLGeometryBackground.HF();
        gLGeometryText.HF();
    }

    public aln_1 Ek() {
        return this.cHz;
    }

    public void c(aln_1 aln_12) {
        this.cHz = aln_12;
    }

    public int getXOffset() {
        return this.bsW;
    }

    public void setXOffset(int n2) {
        this.bsW = n2;
    }

    public int getYOffset() {
        return this.bsX;
    }

    public void setYOffset(int n2) {
        this.bsX = n2;
    }

    public double getWorldX() {
        if (this.cHz != null) {
            return this.cHz.getWorldX();
        }
        return 0.0;
    }

    public double getWorldY() {
        if (this.cHz != null) {
            return this.cHz.getWorldY();
        }
        return 0.0;
    }

    public double getAltitude() {
        if (this.cHz != null) {
            return this.cHz.getAltitude();
        }
        return 0.0;
    }

    public EntityText ap() {
        return this.bCg;
    }

    public void setText(String string) {
        this.bCg.setText(string);
    }

    public void setVisible(boolean bl2) {
        this.bCg.setVisible(bl2);
    }

    public boolean isVisible() {
        return this.bCg.isVisible();
    }

    public boolean El() {
        return false;
    }
}

