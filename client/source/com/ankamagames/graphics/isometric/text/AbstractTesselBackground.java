/*     */ package com.ankamagames.graphics.isometric.text;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.Renderer;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.glu.GLU;
/*     */ import javax.media.opengl.glu.GLUtessellator;
/*     */ import javax.media.opengl.glu.GLUtessellatorCallback;
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
/*     */ public abstract class AbstractTesselBackground
/*     */   implements DrawedBackground
/*     */ {
/*     */   class TesselCallBack
/*     */     implements GLUtessellatorCallback
/*     */   {
/*     */     private GL m_gl;
/*     */     private GLU m_glu;
/*     */     
/*     */     public TesselCallBack(GL gl, GLU glu) {
/*  32 */       this.m_gl = gl;
/*  33 */       this.m_glu = glu;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void begin(int type) {
/*  42 */       this.m_gl.glBegin(type);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void beginData(int type, Object polygonData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void combine(double[] coords, Object[] data, float[] weight, Object[] outData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void combineData(double[] coords, Object[] data, float[] weight, Object[] outData, Object polygionData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void edgeFlag(boolean boundaryEdge) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void edgeFlagData(boolean boundaryEdge, Object polygonData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void end() {
/*  96 */       this.m_gl.glEnd();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void endData(Object polygonData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void error(int errnum) {
/* 113 */       String estring = this.m_glu.gluErrorString(errnum);
/* 114 */       System.err.println("Tessellation Error: " + estring);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void errorData(int errnum, Object polygonData) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void vertex(Object vertexData) {
/* 132 */       double[] pointer = (double[])vertexData;
/* 133 */       this.m_gl.glVertex2dv(pointer, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void vertexData(Object vertexData, Object polygonData) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 147 */   private TesselCallBack m_tesselCallback = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   private double[][] m_verticesAdjustment = null;
/* 153 */   private double[][] m_verticesWidthAndHeight = null;
/* 154 */   private double[][] m_vertices = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 159 */   public static final float[] DEFAULT_BACKGROUND_COLOR = new float[] { 1.0F, 1.0F, 1.0F, 0.7F };
/* 160 */   private float[] m_backgroundColor4f = DEFAULT_BACKGROUND_COLOR;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static final float[] DEFAULT_BORDER_COLOR = new float[] { 0.06F, 0.04F, 0.03F, 0.4F };
/* 166 */   private float[] m_borderColor4f = DEFAULT_BORDER_COLOR;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgoundColor(float r, float g, float b, float a) {
/* 177 */     this.m_backgroundColor4f = new float[] { r, g, b, a };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getBackgroundColor() {
/* 186 */     return this.m_backgroundColor4f;
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
/*     */   public void setBorderColor(float r, float g, float b, float a) {
/* 198 */     this.m_borderColor4f = new float[] { r, g, b, a };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getBorderColor() {
/* 207 */     return this.m_borderColor4f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerticesAdjustment(double[][] verticesAdjustment) {
/* 214 */     this.m_verticesAdjustment = verticesAdjustment;
/* 215 */     this.m_vertices = new double[this.m_verticesAdjustment.length][3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVerticesWidthAndHeight(double[][] verticesWidthAndHeight) {
/* 222 */     this.m_verticesWidthAndHeight = verticesWidthAndHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBubbleBackground(GL gl, float x, float y, float width, float height) {
/* 233 */     gl.glDisable(3553);
/*     */     
/* 235 */     computeVertices(x, y, width, height);
/*     */     
/* 237 */     GLU glu = Renderer.getGlu();
/* 238 */     if (this.m_tesselCallback == null) {
/* 239 */       this.m_tesselCallback = new TesselCallBack(gl, glu);
/*     */     }
/*     */     
/* 242 */     GLUtessellator tobj = glu.gluNewTess();
/*     */     
/* 244 */     glu.gluTessCallback(tobj, 100101, this.m_tesselCallback);
/* 245 */     glu.gluTessCallback(tobj, 100100, this.m_tesselCallback);
/* 246 */     glu.gluTessCallback(tobj, 100102, this.m_tesselCallback);
/* 247 */     glu.gluTessCallback(tobj, 100103, this.m_tesselCallback);
/*     */ 
/*     */     
/* 250 */     gl.glColor4fv(this.m_backgroundColor4f, 0);
/* 251 */     gl.glShadeModel(7425);
/* 252 */     glu.gluTessProperty(tobj, 100140, 100132.0D);
/* 253 */     glu.gluTessBeginPolygon(tobj, null);
/* 254 */     glu.gluTessBeginContour(tobj); byte b; int i; double[][] arrayOfDouble;
/* 255 */     for (i = (arrayOfDouble = this.m_vertices).length, b = 0; b < i; ) { double[] vertex = arrayOfDouble[b];
/* 256 */       glu.gluTessVertex(tobj, vertex, 0, vertex); b++; }
/*     */     
/* 258 */     glu.gluTessEndContour(tobj);
/* 259 */     glu.gluTessEndPolygon(tobj);
/*     */ 
/*     */     
/* 262 */     gl.glEnable(2848);
/* 263 */     gl.glLineWidth(1.5F);
/* 264 */     gl.glColor4fv(this.m_borderColor4f, 0);
/* 265 */     gl.glBegin(2);
/* 266 */     for (i = (arrayOfDouble = this.m_vertices).length, b = 0; b < i; ) { double[] vertex = arrayOfDouble[b];
/* 267 */       gl.glVertex2dv(vertex, 0); b++; }
/*     */     
/* 269 */     gl.glEnd();
/*     */     
/* 271 */     gl.glEnable(3553);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeVertices(float x, float y, float width, float height) {
/* 281 */     if (this.m_vertices == null || this.m_verticesAdjustment == null || this.m_verticesWidthAndHeight == null) {
/* 282 */       initializeVertices();
/*     */     }
/*     */     try {
/* 285 */       for (int i = 0; i < this.m_vertices.length; i++) {
/* 286 */         this.m_vertices[i][0] = this.m_verticesWidthAndHeight[i][0] * width + this.m_verticesAdjustment[i][0] + x;
/* 287 */         this.m_vertices[i][1] = this.m_verticesWidthAndHeight[i][1] * height + this.m_verticesAdjustment[i][1] + y;
/*     */       } 
/* 289 */     } catch (Exception exception) {}
/*     */   }
/*     */   
/*     */   protected abstract void initializeVertices();
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\text\AbstractTesselBackground.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */