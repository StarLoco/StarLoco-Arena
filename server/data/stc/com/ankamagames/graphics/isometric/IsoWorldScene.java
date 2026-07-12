/*     */ package com.ankamagames.graphics.isometric;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IsoWorldScene
/*     */   extends Scene
/*     */ {
/*     */   public static final double DEFAULT_CELL_WIDTH = 86.0D;
/*     */   public static final double DEFAULT_CELL_HEIGHT = 43.0D;
/*     */   public static final double DEFAULT_ELEVATION_UNIT = 10.0D;
/*  21 */   protected double m_cellWidth = 86.0D;
/*  22 */   protected double m_cellHeight = 43.0D;
/*  23 */   protected double m_elevationUnit = 10.0D;
/*     */   
/*     */ 
/*     */ 
/*     */   protected IsoCamera m_isoCamera;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public IsoWorldScene()
/*     */   {
/*  34 */     initializeCamera();
/*     */   }
/*     */   
/*     */   public double getCellWidth() {
/*  38 */     return this.m_cellWidth;
/*     */   }
/*     */   
/*     */   public void setCellWidth(double cellWidth) {
/*  42 */     this.m_cellWidth = cellWidth;
/*     */   }
/*     */   
/*     */   public double getCellHeight() {
/*  46 */     return this.m_cellHeight;
/*     */   }
/*     */   
/*     */   public void setCellHeight(double cellHeight) {
/*  50 */     this.m_cellHeight = cellHeight;
/*     */   }
/*     */   
/*     */   public double getElevationUnit() {
/*  54 */     return this.m_elevationUnit;
/*     */   }
/*     */   
/*     */   public void setElevationUnit(double elevationUnit) {
/*  58 */     this.m_elevationUnit = elevationUnit;
/*     */   }
/*     */   
/*     */   public IsoCamera getIsoCamera() {
/*  62 */     return this.m_isoCamera;
/*     */   }
/*     */   
/*     */   public IsoWorldTarget getCameraTarget() {
/*  66 */     return this.m_isoCamera.getTrackingTarget();
/*     */   }
/*     */   
/*     */   public void setCameraTarget(IsoWorldTarget cameraTarget) {
/*  70 */     this.m_isoCamera.setTrackingTarget(cameraTarget);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void alignCameraOnTrackingTarget()
/*     */   {
/*  77 */     this.m_isoCamera.alignOnTrackingTarget();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getDesiredZoomFactor()
/*     */   {
/*  84 */     if (this.m_isoCamera != null) {
/*  85 */       return this.m_isoCamera.getDesiredZoomFactor();
/*     */     }
/*  87 */     return 1.0D;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setDesiredZoomFactor(double desiredZoomFactor)
/*     */   {
/*  94 */     if (this.m_isoCamera != null) {
/*  95 */       this.m_isoCamera.setDesiredZoomFactor(desiredZoomFactor);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected void initializeCamera()
/*     */   {
/* 102 */     this.m_isoCamera = new IsoCamera(0.0D, 0.0D, 0.0D, this);
/* 103 */     setCamera(this.m_isoCamera);
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
/*     */   public double isoToScreenX(double isoLocalX, double isoLocalY)
/*     */   {
/* 118 */     return (isoLocalX - isoLocalY) * (this.m_cellWidth / 2.0D);
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
/*     */   public double isoToScreenY(double isoLocalX, double isoLocalY)
/*     */   {
/* 133 */     return -(isoLocalX + isoLocalY) * (this.m_cellHeight / 2.0D);
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
/*     */   public double screenToIsoX(double screenX, double screenY)
/*     */   {
/* 148 */     double w = this.m_cellWidth / 2.0D;
/* 149 */     double h = this.m_cellHeight / 2.0D;
/*     */     
/* 151 */     double sy = screenY + h;
/*     */     
/* 153 */     return (sy / h - screenX / w) / 2.0D + screenX / w;
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
/*     */   public double screenToIsoY(double screenX, double screenY)
/*     */   {
/* 168 */     double w = this.m_cellWidth / 2.0D;
/* 169 */     double h = this.m_cellHeight / 2.0D;
/*     */     
/* 171 */     double sy = screenY + h;
/*     */     
/* 173 */     return (sy / h - screenX / w) / 2.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\IsoWorldScene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */