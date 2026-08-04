/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class uu
implements bE {
    public static uu aqx = new uu();
    private static Color aqy = new Color(0.2f, 0.2f, 0.2f, 1.0f);

    public void draw(Graphics2D graphics2D, String string, int n2, int n3) {
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(aqy);
        graphics2D.drawString(string, ++n2 + 1, ++n3);
        graphics2D.drawString(string, n2 + 1, n3 + 1);
        graphics2D.drawString(string, n2, n3 + 1);
        graphics2D.drawString(string, n2 - 1, n3);
        graphics2D.drawString(string, n2 - 1, n3 - 1);
        graphics2D.drawString(string, n2, n3 - 1);
        graphics2D.drawString(string, n2 + 1, n3 - 1);
        graphics2D.drawString(string, n2 - 1, n3 + 1);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(string, n2, n3);
    }

    public void drawGlyphVector(Graphics2D graphics2D, GlyphVector glyphVector, int n2, int n3) {
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setColor(aqy);
        graphics2D.drawGlyphVector(glyphVector, ++n2 + 1, ++n3);
        graphics2D.drawGlyphVector(glyphVector, n2 + 1, n3 + 1);
        graphics2D.drawGlyphVector(glyphVector, n2, n3 + 1);
        graphics2D.drawGlyphVector(glyphVector, n2 - 1, n3);
        graphics2D.drawGlyphVector(glyphVector, n2 - 1, n3 - 1);
        graphics2D.drawGlyphVector(glyphVector, n2, n3 - 1);
        graphics2D.drawGlyphVector(glyphVector, n2 + 1, n3 - 1);
        graphics2D.drawGlyphVector(glyphVector, n2 - 1, n3 + 1);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawGlyphVector(glyphVector, n2, n3);
    }

    public boolean intensityOnly() {
        return false;
    }

    public Rectangle2D getBounds(String string, Font font, FontRenderContext fontRenderContext) {
        GlyphVector glyphVector = font.createGlyphVector(fontRenderContext, string);
        Rectangle rectangle = glyphVector.getPixelBounds(fontRenderContext, 0.0f, 0.0f);
        ((Rectangle2D)rectangle).setRect(((RectangularShape)rectangle).getX() - 1.0, ((RectangularShape)rectangle).getY() - 1.0, ((RectangularShape)rectangle).getWidth() + 2.0, ((RectangularShape)rectangle).getHeight() + 2.0);
        return rectangle;
    }

    public Rectangle2D getBounds(CharSequence charSequence, Font font, FontRenderContext fontRenderContext) {
        GlyphVector glyphVector = font.createGlyphVector(fontRenderContext, ((Object)charSequence).toString());
        Rectangle rectangle = glyphVector.getPixelBounds(fontRenderContext, 0.0f, 0.0f);
        ((Rectangle2D)rectangle).setRect(((RectangularShape)rectangle).getX() - 1.0, ((RectangularShape)rectangle).getY() - 1.0, ((RectangularShape)rectangle).getWidth() + 2.0, ((RectangularShape)rectangle).getHeight() + 2.0);
        return rectangle;
    }

    public Rectangle2D getBounds(GlyphVector glyphVector, FontRenderContext fontRenderContext) {
        Rectangle rectangle = glyphVector.getPixelBounds(fontRenderContext, 0.0f, 0.0f);
        ((Rectangle2D)rectangle).setRect(((RectangularShape)rectangle).getX() - 1.0, ((RectangularShape)rectangle).getY() - 1.0, ((RectangularShape)rectangle).getWidth() + 2.0, ((RectangularShape)rectangle).getHeight() + 2.0);
        return rectangle;
    }
}

