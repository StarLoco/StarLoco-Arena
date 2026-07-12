/*     */ package org.fenggui.background;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
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
/*     */ public class GradientBackground
/*     */   extends Background
/*     */ {
/*  46 */   private Color lowerLeft = Color.BLUE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private Color lowerRight = Color.RED;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private Color upperRight = Color.YELLOW;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private Color upperLeft = Color.WHITE;
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientBackground() {
/*  66 */     this(Color.DARK_GRAY, Color.LIGHT_GRAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientBackground(Color top, Color bottom) {
/*  77 */     this(bottom, bottom, top, top);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public GradientBackground(InputOnlyStream stream) throws IOException, IOStreamException {
/*  83 */     process((InputOutputStream)stream);
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
/*     */   public GradientBackground(Color lowerLeft, Color lowerRight, Color upperRight, Color upperLeft) {
/*  96 */     this.lowerLeft = lowerLeft;
/*  97 */     this.lowerRight = lowerRight;
/*  98 */     this.upperRight = upperRight;
/*  99 */     this.upperLeft = upperLeft;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor1() {
/* 105 */     return this.lowerLeft;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor1(Color color1) {
/* 111 */     this.lowerLeft = color1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor2() {
/* 117 */     return this.lowerRight;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor2(Color color2) {
/* 123 */     this.lowerRight = color2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor3() {
/* 129 */     return this.upperRight;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor3(Color color3) {
/* 135 */     this.upperRight = color3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor4() {
/* 141 */     return this.upperLeft;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor4(Color color4) {
/* 147 */     this.upperLeft = color4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics b, int localX, int localY, int width, int height) {
/* 156 */     b.drawBlendedFilledRect(localX, localY, 
/* 157 */         width, height, this.lowerLeft, this.lowerRight, this.upperRight, this.upperLeft);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 166 */     super.process(stream);
/* 167 */     this.upperLeft = (Color)stream.processChild("TopLeftColor", (IOStreamSaveable)this.upperLeft, Color.class);
/* 168 */     this.upperRight = (Color)stream.processChild("TopRightColor", (IOStreamSaveable)this.upperRight, Color.class);
/* 169 */     this.lowerLeft = (Color)stream.processChild("BottomLeftColor", (IOStreamSaveable)this.lowerLeft, Color.class);
/* 170 */     this.lowerRight = (Color)stream.processChild("BottomRightColor", (IOStreamSaveable)this.lowerRight, Color.class);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\background\GradientBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */