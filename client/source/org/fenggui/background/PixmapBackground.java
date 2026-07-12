/*     */ package org.fenggui.background;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.Pixmap;
/*     */ import org.fenggui.util.Color;
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
/*     */ public class PixmapBackground
/*     */   extends Background
/*     */ {
/*  59 */   private static final Color DEFAULT_BLENDING_COLOR = Color.WHITE;
/*  60 */   private Color blendingColor = DEFAULT_BLENDING_COLOR;
/*     */   private boolean scaled = false;
/*  62 */   private Pixmap center = null;
/*  63 */   private Pixmap topLeft = null;
/*  64 */   private Pixmap top = null;
/*  65 */   private Pixmap topRight = null;
/*  66 */   private Pixmap right = null;
/*  67 */   private Pixmap bottomLeft = null;
/*  68 */   private Pixmap bottom = null;
/*  69 */   private Pixmap bottomRight = null;
/*  70 */   private Pixmap left = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixmapBackground(Pixmap center) {
/*  79 */     this(center, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public PixmapBackground(InputOnlyStream stream) throws IOException, IOStreamException {
/*  84 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixmapBackground() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixmapBackground(Pixmap center, boolean scaled) {
/* 104 */     this(center, null, null, null, null, null, null, null, null, scaled);
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
/*     */   public PixmapBackground(Pixmap center, Pixmap topLeft, Pixmap top, Pixmap topRight, Pixmap right, Pixmap bottomRight, Pixmap bottom, Pixmap bottomLeft, Pixmap left, boolean scaled) {
/* 134 */     this.center = center;
/* 135 */     this.topLeft = topLeft;
/* 136 */     this.top = top;
/* 137 */     this.topRight = topRight;
/* 138 */     this.right = right;
/* 139 */     this.bottomRight = bottomRight;
/* 140 */     this.bottom = bottom;
/* 141 */     this.bottomLeft = bottomLeft;
/* 142 */     this.left = left;
/*     */     
/* 144 */     this.scaled = scaled;
/*     */     
/* 146 */     if (center == null) {
/* 147 */       throw new IllegalArgumentException("center == null");
/*     */     }
/* 149 */     if (topLeft != null) checkIntegrity();
/*     */   
/*     */   }
/*     */   
/*     */   private void checkIntegrity() {
/* 154 */     if (this.bottomLeft.getHeight() != this.bottom.getHeight()) {
/* 155 */       throw new IllegalArgumentException("bottomLeft.getHeight() != bottom.getHeight()");
/*     */     }
/* 157 */     if (this.bottom.getHeight() != this.bottomRight.getHeight()) {
/* 158 */       throw new IllegalArgumentException("bottom.getHeight() != bottomRight.getHeight()");
/*     */     }
/* 160 */     if (this.bottomLeft.getWidth() != this.left.getWidth()) {
/* 161 */       throw new IllegalArgumentException("bottomLeft.getWidth() != left.getWidth()");
/*     */     }
/* 163 */     if (this.left.getWidth() != this.topLeft.getWidth()) {
/* 164 */       throw new IllegalArgumentException("left.getWidth() != topLeft.getWidth()");
/*     */     }
/* 166 */     if (this.topLeft.getHeight() != this.top.getHeight()) {
/* 167 */       throw new IllegalArgumentException("topLeft.getHeight() != top.getHeight()");
/*     */     }
/* 169 */     if (this.top.getHeight() != this.topRight.getHeight()) {
/* 170 */       throw new IllegalArgumentException("top.getHeight() != topRight.getHeight()");
/*     */     }
/* 172 */     if (this.topRight.getWidth() != this.right.getWidth()) {
/* 173 */       throw new IllegalArgumentException("topRight.getWidth() != right.getWidth()");
/*     */     }
/* 175 */     if (this.right.getWidth() != this.bottomRight.getWidth()) {
/* 176 */       throw new IllegalArgumentException("right.getWidth() != bottomRight.getWidth()");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/* 184 */     g.setColor(this.blendingColor);
/*     */ 
/*     */     
/* 187 */     if (this.topLeft == null) {
/*     */       
/* 189 */       if (this.scaled)
/*     */       {
/* 191 */         g.drawScaledImage(this.center, localX, localY, width, height);
/*     */       }
/*     */       else
/*     */       {
/* 195 */         g.drawImage(this.center, localX + width / 2 - this.center.getWidth() / 2, localY + height / 2 - this.center.getHeight() / 2);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 200 */       g.drawImage(this.bottomLeft, localX, localY);
/* 201 */       g.drawScaledImage(this.bottom, localX + this.bottomLeft.getWidth(), localY, width - this.left.getWidth() - this.right.getWidth(), this.bottom.getHeight());
/* 202 */       g.drawImage(this.bottomRight, localX + width - this.bottomRight.getWidth(), localY);
/*     */       
/* 204 */       g.drawScaledImage(this.left, localX, localY + this.bottomLeft.getHeight(), this.left.getWidth(), height - this.top.getHeight() - this.bottom.getHeight());
/*     */       
/* 206 */       g.drawScaledImage(this.right, localX + width - this.right.getWidth(), localY + this.bottomRight.getHeight(), this.right.getWidth(), height - this.topRight.getHeight() - this.bottomRight.getHeight());
/*     */       
/* 208 */       g.drawImage(this.topLeft, localX, localY + height - this.top.getHeight());
/* 209 */       g.drawScaledImage(this.top, localX + this.topLeft.getWidth(), localY + height - this.top.getHeight(), width - this.topRight.getWidth() - this.topLeft.getWidth(), this.topLeft.getHeight());
/* 210 */       g.drawImage(this.topRight, localX + width - this.topRight.getWidth(), localY + height - this.topRight.getHeight());
/*     */ 
/*     */       
/* 213 */       if (this.scaled) {
/*     */         
/* 215 */         g.drawScaledImage(this.center, localX + this.left.getWidth(), localY + this.bottomLeft.getHeight(), width - this.right.getWidth() - this.left.getWidth(), height - this.top.getHeight() - this.bottom.getHeight());
/*     */       }
/*     */       else {
/*     */         
/* 219 */         g.drawImage(this.center, localX + width / 2 - this.center.getWidth() / 2, localY + height / 2 - this.center.getHeight() / 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getBlendingColor() {
/* 228 */     return this.blendingColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlendingColor(Color blendingColor) {
/* 233 */     if (blendingColor == null) throw new IllegalArgumentException("blendingColor == null"); 
/* 234 */     this.blendingColor = blendingColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isScaled() {
/* 239 */     return this.scaled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScaled(boolean scaled) {
/* 244 */     this.scaled = scaled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 252 */     this.blendingColor = (Color)stream.processChild("BlendingColor", (IOStreamSaveable)this.blendingColor, (IOStreamSaveable)DEFAULT_BLENDING_COLOR, Color.class);
/* 253 */     this.scaled = stream.processAttribute("scaled", this.scaled);
/*     */     
/* 255 */     this.center = (Pixmap)stream.processChild("CenterPixmap", (IOStreamSaveable)this.center, Pixmap.class);
/*     */     
/* 257 */     this.left = (Pixmap)stream.processChild("LeftPixmap", (IOStreamSaveable)this.left, null, Pixmap.class);
/* 258 */     this.right = (Pixmap)stream.processChild("RightPixmap", (IOStreamSaveable)this.right, null, Pixmap.class);
/* 259 */     this.top = (Pixmap)stream.processChild("TopPixmap", (IOStreamSaveable)this.top, null, Pixmap.class);
/* 260 */     this.topLeft = (Pixmap)stream.processChild("TopLeftPixmap", (IOStreamSaveable)this.topLeft, null, Pixmap.class);
/* 261 */     this.topRight = (Pixmap)stream.processChild("TopRightPixmap", (IOStreamSaveable)this.topRight, null, Pixmap.class);
/* 262 */     this.bottom = (Pixmap)stream.processChild("BottomPixmap", (IOStreamSaveable)this.bottom, null, Pixmap.class);
/* 263 */     this.bottomLeft = (Pixmap)stream.processChild("BottomLeftPixmap", (IOStreamSaveable)this.bottomLeft, null, Pixmap.class);
/* 264 */     this.bottomRight = (Pixmap)stream.processChild("BottomRightPixmap", (IOStreamSaveable)this.bottomRight, null, Pixmap.class);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\background\PixmapBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */