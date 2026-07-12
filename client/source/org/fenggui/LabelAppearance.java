/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.layout.Alignment;
/*     */ import org.fenggui.render.DirectTextRenderer;
/*     */ import org.fenggui.render.Font;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.render.ITextRenderer;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.util.Color;
/*     */ import org.fenggui.util.Dimension;
/*     */ 
/*     */ public class LabelAppearance
/*     */   extends DecoratorAppearance
/*     */   implements ITextAppearance
/*     */ {
/*  21 */   private Color textColor = Color.BLACK;
/*  22 */   private ILabel label = null;
/*  23 */   private int gap = 5;
/*  24 */   private Alignment alignment = Alignment.LEFT;
/*  25 */   private ITextRenderer textRenderer = (ITextRenderer)new DirectTextRenderer();
/*     */ 
/*     */   
/*     */   public LabelAppearance(ILabel w) {
/*  29 */     super(w);
/*  30 */     this.label = w;
/*     */   }
/*     */ 
/*     */   
/*     */   public Alignment getAlignment() {
/*  35 */     return this.alignment;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlignment(Alignment alignment) {
/*  40 */     this.alignment = alignment;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGap() {
/*  45 */     return this.gap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGap(int gap) {
/*  50 */     this.gap = gap;
/*     */   }
/*     */ 
/*     */   
/*     */   public Font getFont() {
/*  55 */     return this.textRenderer.getFont();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/*  60 */     this.textRenderer.setFont(font);
/*  61 */     this.label.updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color getTextColor() {
/*  66 */     return this.textColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextColor(Color textColor) {
/*  71 */     this.textColor = textColor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getContentMinSizeHint() {
/*  77 */     int width = 0;
/*  78 */     int height = 0;
/*     */     
/*  80 */     String text = this.textRenderer.getText();
/*  81 */     Pixmap pixmap = this.label.getPixmap();
/*     */     
/*  83 */     if (text != null && text.length() > 0) {
/*     */       
/*  85 */       width = this.textRenderer.getWidth();
/*  86 */       height = this.textRenderer.getHeight();
/*     */     } 
/*     */     
/*  89 */     if (pixmap != null) {
/*     */       
/*  91 */       width += pixmap.getWidth();
/*  92 */       if (text != null && text.length() > 0) width += this.gap; 
/*  93 */       height = Math.max(pixmap.getHeight(), height);
/*     */     } 
/*     */     
/*  96 */     return new Dimension(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dimension getContentSizeHint(Font font, int gap, String text, Pixmap pixmap) {
/* 131 */     int width = 0;
/* 132 */     int height = 0;
/*     */     
/* 134 */     if (text != null && text.length() > 0) {
/*     */       
/* 136 */       width = font.getWidth(text);
/* 137 */       height = font.getHeight();
/*     */     } 
/*     */     
/* 140 */     if (pixmap != null) {
/*     */       
/* 142 */       width += pixmap.getWidth();
/* 143 */       if (text != null && text.length() > 0) width += gap; 
/* 144 */       height = Math.max(pixmap.getHeight(), height);
/*     */     } 
/*     */     
/* 147 */     return new Dimension(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paintContent(Graphics g, IOpenGL gl) {
/* 155 */     int x = 0;
/* 156 */     int y = 0;
/* 157 */     int width = 0;
/* 158 */     int height = 0;
/*     */     
/* 160 */     Pixmap pixmap = this.label.getPixmap();
/* 161 */     String text = this.label.getText();
/*     */     
/* 163 */     int contentWidth = getContentWidth();
/* 164 */     int contentHeight = getContentHeight();
/*     */     
/* 166 */     if (pixmap != null) {
/*     */       
/* 168 */       width = pixmap.getWidth();
/* 169 */       height = pixmap.getHeight();
/* 170 */       if (text != null && text.length() > 0) width += this.gap;
/*     */     
/* 172 */     } else if (text == null) {
/*     */       return;
/* 174 */     }  if (text != null) {
/*     */       
/* 176 */       width += this.textRenderer.getWidth();
/* 177 */       height = Math.max(height, this.textRenderer.getHeight());
/*     */     } 
/*     */     
/* 180 */     x = this.alignment.alignX(contentWidth, width);
/*     */     
/* 182 */     if (pixmap != null) {
/*     */       
/* 184 */       g.setColor(Color.WHITE);
/* 185 */       y = this.alignment.alignY(contentHeight, pixmap.getHeight());
/* 186 */       g.drawImage(pixmap, x, y);
/* 187 */       x += pixmap.getWidth() + this.gap;
/*     */     } 
/*     */     
/* 190 */     if (text != null && text.length() > 0) {
/*     */ 
/*     */       
/* 193 */       if (this.textColor != null) g.setColor(this.textColor); 
/* 194 */       y = this.alignment.alignY(contentHeight, this.textRenderer.getHeight());
/*     */       
/* 196 */       this.textRenderer.render(x, y, g, gl);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void paint(Graphics g, Font font, Alignment alignment, Color color, int gap, Pixmap pixmap, String text, int contentWidth, int contentHeight) {
/* 217 */     int x = 0;
/* 218 */     int y = 0;
/* 219 */     int width = 0;
/* 220 */     int height = 0;
/*     */     
/* 222 */     if (pixmap != null) {
/*     */       
/* 224 */       width = pixmap.getWidth();
/* 225 */       height = pixmap.getHeight();
/* 226 */       if (text != null && text.length() > 0) width += gap;
/*     */     
/* 228 */     } else if (text == null) {
/*     */       return;
/* 230 */     }  if (text != null) {
/*     */       
/* 232 */       width += font.getWidth(text);
/* 233 */       height = Math.max(height, font.getHeight());
/*     */     } 
/*     */     
/* 236 */     x = alignment.alignX(contentWidth, width);
/*     */     
/* 238 */     if (pixmap != null) {
/*     */       
/* 240 */       g.setColor(Color.WHITE);
/* 241 */       y = alignment.alignY(contentHeight, pixmap.getHeight());
/* 242 */       g.drawImage(pixmap, x, y);
/* 243 */       x += pixmap.getWidth() + gap;
/*     */     } 
/*     */     
/* 246 */     if (text != null && text.length() > 0) {
/*     */       
/* 248 */       g.setFont(font);
/* 249 */       if (color != null) g.setColor(color); 
/* 250 */       y = alignment.alignY(contentHeight, font.getHeight());
/* 251 */       g.drawString(text, x, y);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 260 */     super.process(stream);
/*     */     
/* 262 */     this.gap = stream.processAttribute("gap", this.gap, 5);
/* 263 */     this.alignment = (Alignment)stream.processEnum("alignment", (Enum)this.alignment, (Enum)Alignment.LEFT, Alignment.class, Alignment.STORAGE_FORMAT);
/*     */     
/* 265 */     if (stream.isInputStream()) {
/* 266 */       setFont((Font)stream.processChild("Font", (IOStreamSaveable)getFont(), (IOStreamSaveable)Font.getDefaultFont(), Font.class));
/*     */     }
/* 268 */     this.textColor = (Color)stream.processChild("Color", (IOStreamSaveable)this.textColor, (IOStreamSaveable)Color.BLACK, Color.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public ITextRenderer getTextRenderer() {
/* 273 */     return this.textRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextRenderer(ITextRenderer textRenderer) {
/* 278 */     textRenderer.setFont(this.textRenderer.getFont());
/* 279 */     textRenderer.setText(this.textRenderer.getText());
/* 280 */     this.textRenderer = textRenderer;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\LabelAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */