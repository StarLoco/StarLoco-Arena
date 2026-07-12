/*     */ package com.ankamagames.framework.graphics.opengl.base;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.GLMatrix;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.IdentityMatrix;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Ortho2DProjection;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Scaling2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.render.ViewPort;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import javax.media.opengl.GL;
/*     */ import javax.media.opengl.GLAutoDrawable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Scene
/*     */   extends Mesh
/*     */ {
/*     */   protected GLMatrix m_camera;
/*     */   private boolean m_instancesInitialized;
/*     */   private boolean m_loaded;
/*     */   protected float m_frustumWidth;
/*     */   protected float m_frustumHeight;
/*     */   protected boolean m_frustumCentered;
/*     */   protected Rectangle2D m_boundsScreenRect;
/*     */   private boolean m_usingZSorting;
/*  34 */   private Scaling2D m_scalingMatrix = new Scaling2D(1.0F, 1.0F);
/*     */   
/*     */   public Scene() {
/*  37 */     this.m_camera = null;
/*  38 */     this.m_instancesInitialized = false;
/*     */     
/*  40 */     this.m_boundsScreenRect = new Rectangle2D.Float();
/*  41 */     this.m_frustumWidth = 1024.0F;
/*  42 */     this.m_frustumHeight = 768.0F;
/*  43 */     this.m_frustumCentered = true;
/*     */     
/*  45 */     this.m_usingZSorting = false;
/*     */     
/*  47 */     pushMatrixBack(IdentityMatrix.getInstance(), 5890);
/*  48 */     pushMatrixBack(IdentityMatrix.getInstance(), 5888);
/*     */     
/*  50 */     recomputeViewPort();
/*     */     
/*  52 */     setVisibilityInheritance(false);
/*  53 */     setVisible(false);
/*     */   }
/*     */   
/*     */   public void setScaleFactor(float factor) {
/*  57 */     this.m_scalingMatrix.setX(factor);
/*  58 */     this.m_scalingMatrix.setY(factor);
/*  59 */     recomputeViewPort();
/*     */   }
/*     */   
/*     */   public float getScaleFactor() {
/*  63 */     return this.m_scalingMatrix.getX();
/*     */   }
/*     */   
/*     */   protected void recomputeViewPort() {
/*  67 */     clearMatrices(5889);
/*  68 */     pushMatrixBack(IdentityMatrix.getInstance(), 5889);
/*     */     
/*  70 */     pushMatrixBack((GLMatrix)new Ortho2DProjection(this.m_frustumWidth, this.m_frustumHeight, this.m_frustumCentered), 5889);
/*     */     
/*  72 */     pushMatrixBack((GLMatrix)this.m_scalingMatrix, 5889);
/*     */     
/*  74 */     setViewPort(new ViewPort(0.0D, 0.0D, this.m_frustumWidth, this.m_frustumHeight));
/*     */     
/*  76 */     float left = 0.0F;
/*  77 */     float bottom = 0.0F;
/*  78 */     float width = this.m_frustumWidth / this.m_scalingMatrix.getX();
/*  79 */     float height = this.m_frustumHeight / this.m_scalingMatrix.getY();
/*     */     
/*  81 */     if (this.m_frustumCentered) {
/*  82 */       left = -width * 0.5F;
/*  83 */       bottom = -height * 0.5F;
/*     */     } 
/*     */ 
/*     */     
/*  87 */     updateBoundsScreenRect(left, bottom, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninitialize() {
/*  95 */     super.uninitialize();
/*  96 */     removeAllChilds();
/*  97 */     this.m_instancesInitialized = false;
/*  98 */     this.m_loaded = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(GLAutoDrawable glAutoDrawable) {
/* 107 */     setInstancesInitialized(true);
/* 108 */     setLoaded(true);
/*     */   }
/*     */   
/*     */   public boolean isInstancesInitialized() {
/* 112 */     return this.m_instancesInitialized;
/*     */   }
/*     */   
/*     */   public void setInstancesInitialized(boolean instancesInitialized) {
/* 116 */     this.m_instancesInitialized = instancesInitialized;
/* 117 */     this.m_loaded = false;
/*     */   }
/*     */   
/*     */   public boolean isLoaded() {
/* 121 */     return this.m_loaded;
/*     */   }
/*     */   
/*     */   public void setLoaded(boolean loaded) {
/* 125 */     if (this.m_instancesInitialized) {
/* 126 */       this.m_loaded = loaded;
/*     */     }
/*     */   }
/*     */   
/*     */   public float getFrustumWidth() {
/* 131 */     return this.m_frustumWidth;
/*     */   }
/*     */   
/*     */   public float getFrustumHeight() {
/* 135 */     return this.m_frustumHeight;
/*     */   }
/*     */   
/*     */   public void setFrustumSize(float frustumWidth, float frustumHeight) {
/* 139 */     this.m_frustumWidth = frustumWidth;
/* 140 */     this.m_frustumHeight = frustumHeight;
/* 141 */     recomputeViewPort();
/*     */   }
/*     */   
/*     */   public boolean isFrustumCentered() {
/* 145 */     return this.m_frustumCentered;
/*     */   }
/*     */   
/*     */   public void setFrustumCentered(boolean frustumCentered) {
/* 149 */     this.m_frustumCentered = frustumCentered;
/* 150 */     recomputeViewPort();
/*     */   }
/*     */   
/*     */   public boolean isUsingZSorting() {
/* 154 */     return this.m_usingZSorting;
/*     */   }
/*     */   
/*     */   public void setUsingZSorting(boolean usingZSorting) {
/* 158 */     this.m_usingZSorting = usingZSorting;
/*     */   }
/*     */   
/*     */   public GLMatrix getCamera() {
/* 162 */     return this.m_camera;
/*     */   }
/*     */   
/*     */   public void setCamera(GLMatrix camera) {
/* 166 */     this.m_camera = camera;
/*     */     
/* 168 */     clearMatrices(5888);
/* 169 */     pushMatrixBack(IdentityMatrix.getInstance(), 5888);
/* 170 */     pushMatrixBack(this.m_camera, 5888);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void display(GL gl) {
/* 179 */     if (this.m_loaded) {
/* 180 */       ViewPort vp = getViewPort();
/* 181 */       int x = (int)vp.getX();
/* 182 */       int y = (int)vp.getY();
/* 183 */       int w = (int)vp.getWidth();
/* 184 */       int h = (int)vp.getHeight();
/*     */       
/* 186 */       int[] oldViewport = new int[4];
/*     */       
/* 188 */       gl.glGetIntegerv(2978, oldViewport, 0);
/*     */       
/* 190 */       gl.glViewport(x, y, w, h);
/* 191 */       gl.glScissor(x, y, w, h);
/*     */       
/* 193 */       super.display(gl);
/*     */       
/* 195 */       gl.glViewport(oldViewport[0], oldViewport[1], oldViewport[2], oldViewport[3]);
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
/*     */   public void updateBoundsScreenRect(float left, float bottom, float width, float height) {
/* 209 */     this.m_boundsScreenRect.setRect(left, bottom, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle2D getBoundsScreenRect() {
/* 216 */     return this.m_boundsScreenRect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 226 */     return String.valueOf(getTotalChildrenCount()) + " meshs";
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\Scene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */