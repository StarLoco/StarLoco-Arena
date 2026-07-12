/*     */ package org.fenggui.border;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.io.DefaultElementName;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
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
/*     */ @DefaultElementName("BevelBorder")
/*     */ public class BevelBorder
/*     */   extends Border
/*     */ {
/*  40 */   private Color elevated = Color.LIGHT_GRAY;
/*  41 */   private Color lowered = Color.DARK_GRAY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BevelBorder() {
/*  50 */     setSpacing(1, 1, 1, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BevelBorder(Color elevated, Color lowered) {
/*  60 */     this.elevated = elevated;
/*  61 */     this.lowered = lowered;
/*  62 */     setSpacing(1, 1, 1, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/*  69 */     IOpenGL gl = g.getOpenGL();
/*     */     
/*  71 */     g.setColor(this.lowered);
/*     */     
/*  73 */     int globalX = localX + g.getTranslation().getX();
/*  74 */     int globalY = localY + g.getTranslation().getY();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     if (getLeft() != 1 && getLeft() > 0) gl.lineWidth(getLeft());
/*     */     
/*  81 */     gl.startLines();
/*     */ 
/*     */     
/*  84 */     if (getLeft() > 0) {
/*     */       
/*  86 */       gl.vertex((globalX + getLeft() / 2), (globalY + getBottom()));
/*  87 */       gl.vertex((globalX + getLeft() / 2), (globalY - getBottom() + height));
/*     */     } 
/*     */     
/*  90 */     g.setColor(this.elevated);
/*     */ 
/*     */     
/*  93 */     if (getBottom() > 0) {
/*     */       
/*  95 */       if (getBottom() != getLeft()) changeLineWidth(gl, getBottom());
/*     */       
/*  97 */       gl.vertex(globalX, (globalY + getBottom() / 2));
/*  98 */       gl.vertex((globalX - getLeft() + width + getRight()), (globalY + getBottom() / 2));
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (getRight() > 0) {
/*     */       
/* 104 */       if (getRight() != getBottom()) changeLineWidth(gl, getRight());
/*     */       
/* 106 */       gl.vertex((globalX - getLeft() + width + getRight() / 2), (globalY + getBottom()));
/* 107 */       gl.vertex((globalX - getLeft() + width + getRight() / 2), (globalY - getBottom() + height));
/*     */     } 
/*     */     
/* 110 */     g.setColor(this.lowered);
/*     */ 
/*     */     
/* 113 */     if (getTop() > 0) {
/*     */       
/* 115 */       if (getTop() != getRight()) changeLineWidth(gl, getTop());
/*     */       
/* 117 */       gl.vertex(globalX, (globalY - getBottom() + height + getTop() / 2));
/* 118 */       gl.vertex((globalX - getLeft() + width + getRight()), (globalY - getBottom() + height + getTop() / 2));
/*     */     } 
/* 120 */     gl.end();
/* 121 */     gl.lineWidth(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void changeLineWidth(IOpenGL gl, int width) {
/* 126 */     gl.end();
/* 127 */     gl.lineWidth(width);
/* 128 */     gl.startLines();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 134 */     super.process(stream);
/*     */     
/* 136 */     this.elevated = (Color)stream.processChild("ElevatedColor", (IOStreamSaveable)this.elevated, Color.class);
/* 137 */     this.lowered = (Color)stream.processChild("LoweredColor", (IOStreamSaveable)this.lowered, Color.class);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\border\BevelBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */