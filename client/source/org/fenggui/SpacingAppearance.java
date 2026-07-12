/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Dimension;
/*     */ import org.fenggui.util.Spacing;
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
/*     */ public abstract class SpacingAppearance
/*     */   implements IAppearance, IOStreamSaveable
/*     */ {
/*  73 */   private Spacing margin = Spacing.ZERO_SPACING;
/*  74 */   private Spacing border = Spacing.ZERO_SPACING;
/*  75 */   private Spacing padding = Spacing.ZERO_SPACING;
/*  76 */   private IWidget widget = null;
/*     */ 
/*     */   
/*     */   public SpacingAppearance(IWidget w) {
/*  80 */     this.widget = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Spacing getBorder() {
/*  89 */     return this.border;
/*     */   }
/*     */ 
/*     */   
/*     */   public IWidget getWidget() {
/*  94 */     return this.widget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorder(Spacing border) {
/* 103 */     if (border == null) border = Spacing.ZERO_SPACING;
/*     */     
/* 105 */     this.border = border;
/* 106 */     getWidget().updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Spacing getMargin() {
/* 111 */     return this.margin;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMargin(Spacing margin) {
/* 117 */     if (margin == null) margin = Spacing.ZERO_SPACING;
/*     */     
/* 119 */     this.margin = margin;
/* 120 */     getWidget().updateMinSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Spacing getPadding() {
/* 126 */     return this.padding;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPadding(Spacing padding) {
/* 132 */     if (padding == null) padding = Spacing.ZERO_SPACING;
/*     */     
/* 134 */     this.padding = padding;
/* 135 */     getWidget().updateMinSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Dimension getContentMinSizeHint();
/*     */   
/*     */   public final Dimension getMinSizeHint() {
/* 142 */     Dimension contentSize = getContentMinSizeHint();
/*     */     
/* 144 */     if (contentSize == null) return new Dimension(10, 10);
/*     */     
/* 146 */     return new Dimension(
/* 147 */         contentSize.getWidth() + 
/* 148 */         this.border.getLeftPlusRight() + 
/* 149 */         this.margin.getLeftPlusRight() + 
/* 150 */         this.padding.getLeftPlusRight(), 
/*     */         
/* 152 */         contentSize.getHeight() + 
/* 153 */         this.border.getBottomPlusTop() + 
/* 154 */         this.margin.getBottomPlusTop() + 
/* 155 */         this.padding.getBottomPlusTop());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void paintContent(Graphics paramGraphics, IOpenGL paramIOpenGL);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, IOpenGL gl) {
/* 167 */     int offsetX = this.margin.getLeft() + this.border.getLeft() + this.padding.getLeft();
/* 168 */     int offsetY = this.margin.getBottom() + this.border.getBottom() + this.padding.getBottom();
/*     */     
/* 170 */     g.translate(offsetX, offsetY);
/*     */     
/* 172 */     paintContent(g, gl);
/*     */     
/* 174 */     g.translate(-offsetX, -offsetY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContentWidth() {
/* 183 */     return this.widget.getSize().getWidth() - 
/* 184 */       this.border.getLeftPlusRight() - 
/* 185 */       this.margin.getLeftPlusRight() - 
/* 186 */       this.padding.getLeftPlusRight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContentHeight() {
/* 195 */     return this.widget.getSize().getHeight() - 
/* 196 */       this.border.getBottomPlusTop() - 
/* 197 */       this.margin.getBottomPlusTop() - 
/* 198 */       this.padding.getBottomPlusTop();
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
/*     */   public final boolean insideMargin(int pX, int pY) {
/* 212 */     pX -= this.margin.getLeft();
/* 213 */     pY -= this.margin.getBottom();
/*     */     
/* 215 */     int innerWidth = getContentWidth();
/* 216 */     int innerHeight = getContentHeight();
/*     */     
/* 218 */     if (pX >= 0 && 
/* 219 */       pX < innerWidth + this.padding.getLeft() + this.padding.getRight() + getBorder().getLeft() + getBorder().getRight() && 
/* 220 */       pY >= 0 && 
/* 221 */       pY < innerHeight + this.padding.getBottom() + this.padding.getTop() + getBorder().getTop() + getBorder().getBottom()) return true;
/*     */     
/*     */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBottomMargins() {
/* 231 */     return this.margin.getBottom() + getBorder().getBottom() + this.padding.getBottom();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTopMargins() {
/* 239 */     return this.margin.getTop() + getBorder().getTop() + this.padding.getTop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLeftMargins() {
/* 248 */     return this.margin.getLeft() + getBorder().getLeft() + this.padding.getLeft();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRightMargins() {
/* 258 */     return this.margin.getRight() + getBorder().getRight() + this.padding.getRight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContentMinWidth() {
/* 263 */     return getWidget().getMinSize().getWidth() - getLeftMargins() - getRightMargins();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContentMinHeight() {
/* 268 */     return getWidget().getMinSize().getWidth() - getTopMargins() - getBottomMargins();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 274 */     String s = "";
/* 275 */     s = String.valueOf(s) + "content size         : " + getContentWidth() + ", " + getContentHeight() + "\n";
/* 276 */     s = String.valueOf(s) + "content min size hint: " + getContentMinSizeHint() + "\n";
/* 277 */     s = String.valueOf(s) + "padding              : " + getPadding() + "\n";
/* 278 */     s = String.valueOf(s) + "border               : " + getBorder() + "\n";
/* 279 */     s = String.valueOf(s) + "margin               : " + getMargin();
/* 280 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 286 */     this.border = (Spacing)stream.processChild("Border", (IOStreamSaveable)this.border, (IOStreamSaveable)Spacing.ZERO_SPACING, Spacing.class);
/* 287 */     this.margin = (Spacing)stream.processChild("Margin", (IOStreamSaveable)this.margin, (IOStreamSaveable)Spacing.ZERO_SPACING, Spacing.class);
/* 288 */     this.padding = (Spacing)stream.processChild("Padding", (IOStreamSaveable)this.padding, (IOStreamSaveable)Spacing.ZERO_SPACING, Spacing.class);
/*     */   }
/*     */   
/*     */   public String getUniqueName() {
/* 292 */     return "--generate-name--";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\SpacingAppearance.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */