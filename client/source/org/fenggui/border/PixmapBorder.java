/*     */ package org.fenggui.border;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
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
/*     */ public class PixmapBorder
/*     */   extends Border
/*     */ {
/*     */   public static final int TOP_LEFT = 0;
/*     */   public static final int LEFT = 1;
/*     */   public static final int BOTTOM_LEFT = 2;
/*     */   public static final int BOTTOM = 3;
/*     */   public static final int BOTTOM_RIGHT = 4;
/*     */   public static final int RIGHT = 5;
/*     */   public static final int TOP_RIGHT = 6;
/*     */   public static final int TOP = 7;
/*  55 */   private Pixmap[] tex = new Pixmap[8];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Color modulationColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PixmapBorder(InputOnlyStream stream) throws IOException, IOStreamException {
/*  66 */     this.modulationColor = Color.WHITE; process((InputOutputStream)stream); } public PixmapBorder(Pixmap[] array) throws IllegalArgumentException { this.modulationColor = Color.WHITE;
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
/*  92 */     if (array.length == 8) {
/*     */       
/*  94 */       setSpacing(array[7].getHeight(), array[1].getWidth(), array[5].getWidth(), array[3].getHeight());
/*     */     } else {
/*  96 */       throw new IllegalArgumentException("PixmapBorder takes 8 pixmaps, not " + array.length);
/*     */     } 
/*  98 */     this.tex = array; }
/*     */   
/*     */   public Color getModulationColor() {
/*     */     return this.modulationColor;
/*     */   }
/*     */   
/*     */   public void setModulationColor(Color modulationColor) {
/*     */     this.modulationColor = modulationColor;
/*     */   }
/*     */   
/*     */   public PixmapBorder(List<Pixmap> list) throws IllegalArgumentException {
/* 109 */     this(list.<Pixmap>toArray(new Pixmap[list.size()]));
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
/*     */   public PixmapBorder(Pixmap leftEdge, Pixmap rightEdge, Pixmap topEdge, Pixmap bottomEdge, Pixmap upperLeftCorner, Pixmap upperRightCorner, Pixmap lowerLeftCorner, Pixmap lowerRightCorner) {
/* 134 */     this(new Pixmap[] { upperLeftCorner, leftEdge, lowerLeftCorner, bottomEdge, lowerRightCorner, rightEdge, upperRightCorner, topEdge });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/* 140 */     g.setColor(this.modulationColor);
/*     */     
/* 142 */     g.drawScaledImage(this.tex[1], 
/* 143 */         localX, localY + getBottom(), 
/* 144 */         getLeft(), height - getBottom() - getTop());
/*     */     
/* 146 */     g.drawScaledImage(this.tex[3], 
/* 147 */         localX + this.tex[2].getWidth(), localY, 
/* 148 */         width - getLeft() - getRight(), getBottom());
/*     */     
/* 150 */     g.drawScaledImage(this.tex[7], 
/* 151 */         localX + getLeft(), localY + height - getTop(), 
/* 152 */         width - getLeft() - getRight(), getTop());
/*     */     
/* 154 */     g.drawScaledImage(this.tex[5], 
/* 155 */         localX + width - getRight(), localY + getBottom(), 
/* 156 */         getRight(), height - getBottom() - getTop());
/*     */ 
/*     */     
/* 159 */     g.drawImage(this.tex[0], localX, localY + height - getTop());
/* 160 */     g.drawImage(this.tex[2], localX, localY);
/* 161 */     g.drawImage(this.tex[6], localX + width - getRight(), localY + height - getTop());
/* 162 */     g.drawImage(this.tex[4], localX + width - getRight(), localY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 168 */     super.process(stream);
/*     */     
/* 170 */     this.tex[0] = (Pixmap)stream.processChild("TopLeftPixmap", (IOStreamSaveable)this.tex[0], Pixmap.class);
/* 171 */     this.tex[1] = (Pixmap)stream.processChild("LeftEdgePixmap", (IOStreamSaveable)this.tex[1], Pixmap.class);
/* 172 */     this.tex[2] = (Pixmap)stream.processChild("BottomLeftPixmap", (IOStreamSaveable)this.tex[2], Pixmap.class);
/* 173 */     this.tex[3] = (Pixmap)stream.processChild("BottomEdgePixmap", (IOStreamSaveable)this.tex[3], Pixmap.class);
/* 174 */     this.tex[4] = (Pixmap)stream.processChild("BottomRightPixmap", (IOStreamSaveable)this.tex[4], Pixmap.class);
/* 175 */     this.tex[5] = (Pixmap)stream.processChild("RightEdgePixmap", (IOStreamSaveable)this.tex[5], Pixmap.class);
/* 176 */     this.tex[6] = (Pixmap)stream.processChild("TopRightPixmap", (IOStreamSaveable)this.tex[6], Pixmap.class);
/* 177 */     this.tex[7] = (Pixmap)stream.processChild("TopEdgePixmap", (IOStreamSaveable)this.tex[7], Pixmap.class);
/*     */     
/* 179 */     setSpacing(this.tex[7].getHeight(), this.tex[1].getWidth(), this.tex[5].getWidth(), this.tex[3].getHeight());
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\border\PixmapBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */