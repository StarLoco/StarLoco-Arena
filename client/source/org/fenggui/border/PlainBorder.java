/*     */ package org.fenggui.border;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.fenggui.Span;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.IOStreamSaveable;
/*     */ import org.fenggui.io.InputOnlyStream;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Graphics;
/*     */ import org.fenggui.render.IOpenGL;
/*     */ import org.fenggui.util.Color;
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
/*     */ public class PlainBorder
/*     */   extends Border
/*     */ {
/*  44 */   private Color color = Color.BLUE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlainBorder(Color c, int lineWidth) {
/*  55 */     this(lineWidth, lineWidth, lineWidth, lineWidth, c, true, Span.BORDER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlainBorder() {
/*  65 */     setSpacing(1, 1, 1, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlainBorder(InputOnlyStream stream) throws IOException, IOStreamException {
/*  70 */     process((InputOutputStream)stream);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlainBorder(int top, int left, int right, int bottom) {
/*  75 */     this(top, left, right, bottom, Color.BLACK, true, Span.BORDER);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlainBorder(int top, int left, int right, int bottom, Color color, boolean enabled, Span span) {
/*  80 */     setSpacing(top, left, right, bottom);
/*  81 */     this.color = color;
/*  82 */     setEnabled(enabled);
/*  83 */     setSpan(span);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlainBorder(Spacing s) {
/*  88 */     setSpacing(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlainBorder(Spacing s, Color c) {
/*  93 */     this.color = c;
/*  94 */     setSpacing(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlainBorder(Color c) {
/* 104 */     this(1, 1, 1, 1, c, true, Span.BORDER);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlainBorder(Color c, boolean enabled) {
/* 109 */     this(1, 1, 1, 1, c, enabled, Span.BORDER);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlainBorder(Color c, Span span) {
/* 114 */     this(1, 1, 1, 1, c, true, span);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color getColor() {
/* 123 */     return this.color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(Color color) {
/* 131 */     this.color = color;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void changeLineWidth(IOpenGL gl, int width) {
/* 137 */     gl.end();
/* 138 */     gl.lineWidth(width);
/* 139 */     gl.startLines();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(Graphics g, int localX, int localY, int width, int height) {
/* 147 */     IOpenGL gl = g.getOpenGL();
/* 148 */     gl.color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), this.color.getAlpha());
/*     */     
/* 150 */     int globalX = localX + g.getTranslation().getX();
/* 151 */     int globalY = localY + g.getTranslation().getY();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     if (getLeft() != 1 && getLeft() > 0) gl.lineWidth(getLeft());
/*     */     
/* 158 */     gl.startLines();
/*     */ 
/*     */     
/* 161 */     if (getLeft() > 0) {
/*     */       
/* 163 */       gl.vertex((globalX + getLeft() / 2), (globalY + getBottom()));
/* 164 */       gl.vertex((globalX + getLeft() / 2), (globalY + height - getTop()));
/*     */     } 
/*     */ 
/*     */     
/* 168 */     if (getBottom() > 0) {
/*     */       
/* 170 */       if (getBottom() != getLeft()) changeLineWidth(gl, getBottom());
/*     */       
/* 172 */       gl.vertex(globalX, (globalY + getBottom() / 2));
/* 173 */       gl.vertex((globalX + width), (globalY + getBottom() / 2));
/*     */     } 
/*     */ 
/*     */     
/* 177 */     if (getRight() > 0) {
/*     */       
/* 179 */       if (getRight() != getBottom()) changeLineWidth(gl, getRight());
/*     */       
/* 181 */       gl.vertex((globalX + width - (getRight() + 1) / 2), (globalY + getBottom()));
/* 182 */       gl.vertex((globalX + width - (getRight() + 1) / 2), (globalY + height - getTop()));
/*     */     } 
/*     */ 
/*     */     
/* 186 */     if (getTop() > 0) {
/*     */       
/* 188 */       if (getTop() != getRight()) changeLineWidth(gl, getTop());
/*     */       
/* 190 */       gl.vertex(globalX, (globalY + height - (getTop() + 1) / 2));
/* 191 */       gl.vertex((globalX + width), (globalY + height - (getTop() + 1) / 2));
/*     */     } 
/* 193 */     gl.end();
/* 194 */     gl.lineWidth(1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 200 */     super.process(stream);
/*     */     
/* 202 */     if (getTop() == 0 && getLeft() == 0 && getBottom() == 0 && getRight() == 0)
/*     */     {
/* 204 */       setSpacing(1, 1);
/*     */     }
/*     */     
/* 207 */     this.color = (Color)stream.processChild("Color", (IOStreamSaveable)this.color, (IOStreamSaveable)Color.BLACK, Color.class);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\border\PlainBorder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */