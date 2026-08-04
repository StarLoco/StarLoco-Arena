/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.impl.packrect.Rect;
import com.sun.opengl.util.j2d.TextureRenderer;
import com.sun.opengl.util.texture.TextureCoords;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/*
 * Renamed from aKM
 */
class akm_1 {
    private int dyS;
    private int dTT;
    private agb_1 dTU;
    private float advance;
    private GlyphVector dTV;
    private Rect dTW;
    private String dyR;
    private boolean dTX;
    final /* synthetic */ afg_0 bKu;

    public akm_1(afg_0 afg_02, int n2, int n3, float f, GlyphVector glyphVector, agb_1 agb_12) {
        this.bKu = afg_02;
        this.dyS = n2;
        this.dTT = n3;
        this.advance = f;
        this.dTV = glyphVector;
        this.dTU = agb_12;
    }

    public akm_1(afg_0 afg_02, String string, boolean bl2) {
        this.bKu = afg_02;
        this.dyR = string;
        this.dTX = bl2;
    }

    public int getUnicodeID() {
        return this.dyS;
    }

    public int getGlyphCode() {
        return this.dTT;
    }

    public float getAdvance() {
        return this.advance;
    }

    public float b(float f, float f2, float f3, float f4, float f5) {
        if (this.dyR != null) {
            afg_0.a(this.bKu, this.dyR, f, f2, f3, f4, f5);
            if (!this.dTX) {
                return 0.0f;
            }
            GlyphVector glyphVector = afg_0.s(this.bKu).createGlyphVector(this.bKu.getFontRenderContext(), this.dyR);
            float f6 = 0.0f;
            for (int j = 0; j < glyphVector.getNumGlyphs(); ++j) {
                f6 += glyphVector.getGlyphMetrics(j).getAdvance();
            }
            return f6;
        }
        if (this.dTW == null) {
            this.aVM();
        }
        try {
            if (this.bKu.dFZ == null) {
                this.bKu.dFZ = new axl_0(this.bKu);
            }
            TextureRenderer textureRenderer = afg_0.t(this.bKu);
            TextureCoords textureCoords = textureRenderer.getTexture().getImageTexCoords();
            float f7 = textureCoords.right();
            float f8 = textureCoords.bottom();
            Rect rect = this.dTW;
            adh_2 adh_22 = (adh_2)rect.getUserData();
            adh_22.aPE();
            Rectangle2D rectangle2D = adh_22.aPC();
            float f9 = f - f5 * (float)adh_22.aPA();
            float f10 = f3 - f5 * ((float)rectangle2D.getHeight() - (float)adh_22.aPB());
            int n2 = rect.x() + (adh_22.aPz().x - adh_22.aPA());
            int n3 = textureRenderer.getHeight() - rect.y() - (int)rectangle2D.getHeight() - (adh_22.aPz().y - adh_22.aPB());
            int n4 = (int)rectangle2D.getWidth();
            int n5 = (int)rectangle2D.getHeight();
            float f11 = f7 * (float)n2 / (float)textureRenderer.getWidth();
            float f12 = f8 * (1.0f - (float)n3 / (float)textureRenderer.getHeight());
            float f13 = f7 * (float)(n2 + n4) / (float)textureRenderer.getWidth();
            float f14 = f8 * (1.0f - (float)(n3 + n5) / (float)textureRenderer.getHeight());
            this.bKu.dFZ.glTexCoord2f(f11, f12);
            this.bKu.dFZ.glVertex3f(f9, f10, f4);
            this.bKu.dFZ.glTexCoord2f(f13, f12);
            this.bKu.dFZ.glVertex3f(f9 + (float)n4 * f5, f10, f4);
            this.bKu.dFZ.glTexCoord2f(f13, f14);
            this.bKu.dFZ.glVertex3f(f9 + (float)n4 * f5, f10 + (float)n5 * f5, f4);
            this.bKu.dFZ.glTexCoord2f(f11, f14);
            this.bKu.dFZ.glVertex3f(f9, f10 + (float)n5 * f5, f4);
        }
        catch (Exception exception) {
            afg_0.a.error((Object)"Exception", (Throwable)exception);
        }
        return this.advance;
    }

    public void clear() {
        this.dTW = null;
    }

    private void aVM() {
        GlyphVector glyphVector = this.aVN();
        Rectangle2D rectangle2D = afg_0.c(afg_0.a(this.bKu).getBounds(glyphVector, this.bKu.getFontRenderContext()));
        Rectangle2D rectangle2D2 = afg_0.a(this.bKu, rectangle2D);
        Point point = new Point((int)(-rectangle2D2.getMinX()), (int)(-rectangle2D2.getMinY()));
        Rect rect = new Rect(0, 0, (int)rectangle2D2.getWidth(), (int)rectangle2D2.getHeight(), new adh_2(null, point, rectangle2D, this.dyS));
        afg_0.f(this.bKu).add(rect);
        this.dTW = rect;
        Graphics2D graphics2D = afg_0.u(this.bKu);
        int n2 = rect.x() + point.x;
        int n3 = rect.y() + point.y;
        graphics2D.setComposite(AlphaComposite.Clear);
        graphics2D.fillRect(rect.x(), rect.y(), rect.w(), rect.h());
        graphics2D.setComposite(AlphaComposite.Src);
        afg_0.a(this.bKu).drawGlyphVector(graphics2D, glyphVector, n2, n3);
        afg_0.t(this.bKu).markDirty(rect.x(), rect.y(), rect.w(), rect.h());
        this.dTU.a(this);
    }

    private GlyphVector aVN() {
        GlyphVector glyphVector = this.dTV;
        if (glyphVector != null) {
            this.dTV = null;
            return glyphVector;
        }
        afg_0.v((afg_0)this.bKu)[0] = (char)this.dyS;
        return afg_0.s(this.bKu).createGlyphVector(this.bKu.getFontRenderContext(), afg_0.v(this.bKu));
    }
}

