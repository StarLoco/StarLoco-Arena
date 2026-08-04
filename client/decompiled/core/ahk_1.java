/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

/*
 * Renamed from ahk
 */
public class ahk_1
implements bE {
    public boolean intensityOnly() {
        return true;
    }

    public Rectangle2D getBounds(CharSequence charSequence, Font font, FontRenderContext fontRenderContext) {
        return this.getBounds(font.createGlyphVector(fontRenderContext, new acr_0(charSequence)), fontRenderContext);
    }

    public Rectangle2D getBounds(String string, Font font, FontRenderContext fontRenderContext) {
        return this.getBounds(font.createGlyphVector(fontRenderContext, string), fontRenderContext);
    }

    public Rectangle2D getBounds(GlyphVector glyphVector, FontRenderContext fontRenderContext) {
        return glyphVector.getVisualBounds();
    }

    public void drawGlyphVector(Graphics2D graphics2D, GlyphVector glyphVector, int n2, int n3) {
        graphics2D.drawGlyphVector(glyphVector, n2, n3);
    }

    public void draw(Graphics2D graphics2D, String string, int n2, int n3) {
        graphics2D.drawString(string, n2, n3);
    }
}

