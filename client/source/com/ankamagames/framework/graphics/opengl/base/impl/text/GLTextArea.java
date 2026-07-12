/*     */ package com.ankamagames.framework.graphics.opengl.base.impl.text;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*     */ import com.sun.opengl.util.j2d.TextRenderer;
/*     */ import java.awt.Font;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.LineBreakMeasurer;
/*     */ import java.awt.font.TextAttribute;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.text.AttributedString;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.media.opengl.GL;
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
/*     */ public class GLTextArea
/*     */   extends Mesh
/*     */ {
/*  35 */   public static final Font DEFAULT_FONT = new Font("SansSerif", 0, 12);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final float INFINIT_MAX_WIDTH = -1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PARAGRAPH_SEPARATOR = "\n";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int EXTRA_LINE_SPACING = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Font m_font;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean m_antialised;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private String m_text = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final float[] DEFAULT_TEXT_COLOR = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
/*  71 */   private float[] m_color4f = DEFAULT_TEXT_COLOR;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private float m_maxWidth = -1.0F;
/*  77 */   private float m_minWidth = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   private float m_x = 0.0F;
/*  83 */   private float m_y = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private List<String> m_lines = new ArrayList<String>();
/*  89 */   private int m_lineSpacing = 0;
/*     */   
/*     */   private TextRenderer m_renderer;
/*     */   
/*     */   private boolean m_needToReflow = true;
/*     */   
/*     */   private boolean m_needToSetColor = true;
/*  96 */   private float[] m_screenBounds = new float[16];
/*  97 */   private int[] m_viewport = new int[4];
/*     */   
/*  99 */   private int m_viewportWidth = 0;
/* 100 */   private int m_viewportHeight = 0;
/* 101 */   private float m_viewportCenterX = 0.0F;
/* 102 */   private float m_viewportCenterY = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   private float m_scaleFactorX = 1.0F;
/* 108 */   private float m_scaleFactorY = 1.0F;
/*     */ 
/*     */ 
/*     */   
/*     */   private float m_textWidth;
/*     */ 
/*     */   
/*     */   private float m_textHeight;
/*     */ 
/*     */ 
/*     */   
/*     */   public GLTextArea() {
/* 120 */     this(DEFAULT_FONT, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GLTextArea(Font font) {
/* 129 */     this(font, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GLTextArea(Font font, boolean antialiased) {
/* 139 */     this.m_font = font;
/* 140 */     this.m_antialised = antialiased;
/* 141 */     regenerateTextRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAntialised() {
/* 148 */     return this.m_antialised;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAntialised(boolean antialised) {
/* 156 */     this.m_antialised = antialised;
/* 157 */     regenerateTextRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getFont() {
/* 164 */     return this.m_font;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 172 */     this.m_font = font;
/* 173 */     regenerateTextRenderer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxWidth() {
/* 180 */     return this.m_maxWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxWidth(float maxWidth) {
/* 188 */     this.m_maxWidth = maxWidth;
/* 189 */     this.m_needToReflow = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinWidth() {
/* 196 */     return this.m_minWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinWidth(float minWidth) {
/* 203 */     this.m_minWidth = minWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 210 */     return this.m_text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 218 */     this.m_text = text;
/* 219 */     this.m_needToReflow = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(float r, float g, float b, float a) {
/* 230 */     this.m_color4f = new float[] { r, g, b, a };
/* 231 */     this.m_needToSetColor = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getColor() {
/* 238 */     return this.m_color4f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/* 245 */     return this.m_x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setX(float x) {
/* 253 */     this.m_x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/* 260 */     return this.m_y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(float y) {
/* 268 */     this.m_y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(float x, float y) {
/* 278 */     setX(x);
/* 279 */     setY(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScaleFactorX() {
/* 286 */     return this.m_scaleFactorX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScaleFactorY() {
/* 293 */     return this.m_scaleFactorY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTextHeight() {
/* 300 */     return this.m_textHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTextWidth() {
/* 307 */     return this.m_textWidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processGeometry(GL gl) {
/* 317 */     if (this.m_needToReflow) {
/* 318 */       reflow();
/* 319 */       this.m_needToReflow = false;
/*     */     } 
/* 321 */     if (this.m_needToSetColor) {
/* 322 */       this.m_renderer.setColor(this.m_color4f[0], this.m_color4f[1], this.m_color4f[2], this.m_color4f[3]);
/* 323 */       this.m_needToSetColor = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void calculateOnScreenCoordinates(GL gl) {
/* 334 */     gl.glGetFloatv(2983, this.m_screenBounds, 0);
/*     */ 
/*     */     
/* 337 */     gl.glGetIntegerv(2978, this.m_viewport, 0);
/* 338 */     this.m_viewportWidth = this.m_viewport[2] - this.m_viewport[0];
/* 339 */     this.m_viewportHeight = this.m_viewport[3] - this.m_viewport[1];
/*     */ 
/*     */     
/* 342 */     this.m_viewportCenterX = this.m_viewportWidth / 2.0F;
/* 343 */     this.m_viewportCenterY = this.m_viewportHeight / 2.0F;
/*     */ 
/*     */     
/* 346 */     this.m_scaleFactorX = (this.m_screenBounds[0] + this.m_screenBounds[4]) * this.m_viewportCenterX;
/* 347 */     this.m_scaleFactorY = (this.m_screenBounds[1] + this.m_screenBounds[5]) * this.m_viewportCenterY;
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
/*     */   public void drawPrimitives(GL gl) {
/* 359 */     this.m_renderer.beginRendering(this.m_viewportWidth, this.m_viewportHeight);
/* 360 */     gl.glMatrixMode(5888);
/* 361 */     gl.glLoadIdentity();
/* 362 */     gl.glTranslatef(getX() * this.m_scaleFactorX + this.m_viewportCenterX, getY() * this.m_scaleFactorY - this.m_lineSpacing + this.m_viewportCenterY, 0.0F);
/* 363 */     for (int i = this.m_lines.size() - 1; i >= 0; i--) {
/* 364 */       String line = this.m_lines.get(i);
/* 365 */       if (line != null) {
/* 366 */         gl.glTranslatef(0.0F, this.m_lineSpacing, 0.0F);
/* 367 */         this.m_renderer.draw(line, 0, 0);
/*     */       } 
/*     */     } 
/* 370 */     this.m_renderer.endRendering();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void regenerateTextRenderer() {
/* 379 */     this.m_renderer = new TextRenderer(this.m_font, this.m_antialised, true);
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
/*     */   private void reflow() {
/* 391 */     String[] paragraphs = this.m_text.split("\n");
/*     */ 
/*     */     
/* 394 */     this.m_lines.clear();
/* 395 */     this.m_lineSpacing = 0;
/* 396 */     int numLines = 0;
/* 397 */     float realMaxWidth = 0.0F;
/*     */ 
/*     */     
/* 400 */     FontRenderContext frc = this.m_renderer.getFontRenderContext(); byte b; int i; String[] arrayOfString1;
/* 401 */     for (i = (arrayOfString1 = paragraphs).length, b = 0; b < i; ) { String paragraph = arrayOfString1[b];
/* 402 */       if (paragraph.length() != 0) {
/* 403 */         Map<Object, Object> attrs = new HashMap<Object, Object>();
/* 404 */         attrs.put(TextAttribute.FONT, this.m_renderer.getFont());
/* 405 */         AttributedString str = new AttributedString(paragraph, (Map)attrs);
/* 406 */         LineBreakMeasurer measurer = new LineBreakMeasurer(str.getIterator(), frc);
/* 407 */         int curPos = 0;
/* 408 */         while (measurer.getPosition() < paragraph.length()) {
/* 409 */           int nextPos = (this.m_maxWidth == -1.0F) ? paragraph.length() : measurer.nextOffset(this.m_maxWidth);
/* 410 */           String line = paragraph.substring(curPos, nextPos);
/* 411 */           Rectangle2D bounds = this.m_renderer.getBounds(line);
/* 412 */           this.m_lines.add(line);
/* 413 */           this.m_lineSpacing += (int)bounds.getHeight();
/* 414 */           numLines++;
/* 415 */           realMaxWidth = Math.max(realMaxWidth, (float)bounds.getWidth());
/* 416 */           curPos = nextPos;
/* 417 */           measurer.setPosition(curPos);
/*     */         } 
/*     */ 
/*     */         
/* 421 */         this.m_lines.add(null);
/*     */       } 
/*     */       
/*     */       b++; }
/*     */     
/* 426 */     this.m_lineSpacing = (int)(this.m_lineSpacing / numLines) + 2;
/*     */ 
/*     */     
/* 429 */     this.m_textWidth = Math.max(this.m_minWidth, realMaxWidth);
/* 430 */     this.m_textHeight = (this.m_lineSpacing * numLines);
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\impl\text\GLTextArea.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */