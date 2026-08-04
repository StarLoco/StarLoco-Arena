/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

public interface bE {
    public boolean intensityOnly();

    public Rectangle2D getBounds(String var1, Font var2, FontRenderContext var3);

    public Rectangle2D getBounds(CharSequence var1, Font var2, FontRenderContext var3);

    public Rectangle2D getBounds(GlyphVector var1, FontRenderContext var2);

    public void draw(Graphics2D var1, String var2, int var3, int var4);

    public void drawGlyphVector(Graphics2D var1, GlyphVector var2, int var3, int var4);
}

