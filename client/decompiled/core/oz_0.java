/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.opengl.text.GLGeometryBackground;
import com.ankamagames.framework.graphics.engine.opengl.text.GLGeometryText;
import com.ankamagames.framework.graphics.engine.text.EntityText;
import com.ankamagames.framework.graphics.engine.text.GeometryBackground;

/*
 * Renamed from Oz
 */
public class oz_0 {
    public static final int bci = Integer.MAX_VALUE;
    public static final int azG = 3000;
    public static int bCe = 3000;
    private int IP;
    private int wg = bCe;
    private boolean bCf = false;
    private int bsW = 0;
    private int bsX = 0;
    private EntityText bCg = new EntityText();

    public static void hc(int n2) {
        bCe = n2;
    }

    public static int aby() {
        return bCe;
    }

    public oz_0(ma_1 ma_12) {
        GLGeometryText gLGeometryText = new GLGeometryText();
        GLGeometryBackground gLGeometryBackground = new GLGeometryBackground();
        this.bCg.a(gLGeometryText);
        this.bCg.a(gLGeometryBackground);
        this.bCg.a(ma_12);
        GeometryBackground geometryBackground = this.bCg.KW();
        rx_2 rx_22 = new rx_2();
        geometryBackground.a(rx_22.wZ(), rx_22.xa());
        geometryBackground.f(rx_22.xb());
        geometryBackground.e(rx_22.xc());
        geometryBackground.e(rx_22.wW(), rx_22.wX(), rx_22.wY(), rx_22.wV());
        gLGeometryText.HF();
        geometryBackground.HF();
    }

    public void a(float f, float f2, float f3, float f4) {
        this.bCg.KW().setColor(f, f2, f3, f4);
    }

    public vP getBackgroundColor() {
        return this.bCg.KW().getColor();
    }

    public void b(float f, float f2, float f3, float f4) {
        this.bCg.KW().b(f, f2, f3, f4);
    }

    public vP getBorderColor() {
        return this.bCg.KW().getBorderColor();
    }

    public int getDuration() {
        return this.wg;
    }

    public void setDuration(int n2) {
        this.wg = n2;
    }

    public void setOffset(int n2, int n3) {
        this.bsW = n2;
        this.bsX = n3;
    }

    public int getXOffset() {
        return this.bsW;
    }

    public int getYOffset() {
        return this.bsX;
    }

    protected float xL() {
        return this.bsW;
    }

    protected float xM() {
        return this.bsX;
    }

    public void setVisible(boolean bl2) {
        if (!bl2 && this.bCg.getText() != null && !this.bCg.getText().equals("")) {
            this.bCf = false;
            this.IP = 0;
        }
        this.bCg.setVisible(bl2);
    }

    public void bI(int n2) {
        if (this.bCg.getText() == null) {
            return;
        }
        if (!this.bCg.isVisible()) {
            this.bCf = false;
            return;
        }
        if (this.wg == Integer.MAX_VALUE) {
            return;
        }
        this.IP += n2;
        if (!this.bCf) {
            this.IP = 0;
            this.bCf = true;
        }
        if (this.IP >= this.wg) {
            this.setVisible(false);
            this.bCf = false;
            return;
        }
    }

    public final EntityText ap() {
        return this.bCg;
    }

    public final String getText() {
        return this.bCg.getText();
    }

    public final void setText(String string) {
        this.bCg.setText(string);
    }

    public final void setColor(float f, float f2, float f3, float f4) {
        this.bCg.setColor(f, f2, f3, f4);
    }

    public final void a(ma_1 ma_12) {
        this.bCg.a(ma_12);
    }

    public final void r(float f, float f2) {
        this.bCg.a(new agu_0(f, f2, 0.0f));
    }

    public final void setBorderWidth(float f) {
        this.bCg.KW().setBorderWidth(f);
    }

    public final void setMaxWidth(int n2) {
        this.bCg.setMaxWidth(n2);
    }
}

