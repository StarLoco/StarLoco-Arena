/*     */ package com.ankamagames.graphics.isometric;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Ortho2DScrollCamera;
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
/*     */ public class IsoCamera
/*     */   extends Ortho2DScrollCamera
/*     */   implements IsoWorldTarget
/*     */ {
/*     */   public static final int TOP_SCREEN_CELL_HEIGHT_BONUS = 5;
/*     */   public static final int BOTTOM_SCREEN_CELL_HEIGHT_BONUS = 20;
/*     */   private static final int OFFSET_PADDING = 15;
/*     */   private static final double OFFSET_PADDING_STOP_LIMIT = 0.2D;
/*     */   private static final double OFFSET_PADDING_ZOOM_LIMIT = 0.01D;
/*     */   private static final float DEFAULT_ZOOM_MIN = 0.9F;
/*     */   private static final float DEFAULT_ZOOM_MAX = 1.9F;
/*     */   private double m_worldX;
/*     */   private double m_worldY;
/*     */   private double m_altitude;
/*     */   private IsoWorldTarget m_trackingTarget;
/*  33 */   private double m_desiredZoomFactor = 1.0D;
/*  34 */   private double m_zoomFactor = 1.0D;
/*  35 */   private float m_zoomFactorMin = 0.9F;
/*  36 */   private float m_zoomFactorMax = 1.9F;
/*     */   
/*     */   private IsoWorldScene m_scene;
/*     */   
/*  40 */   private int m_bottomScreenCellHeightBonus = 20;
/*  41 */   private int m_topScreenCellHeightBonus = 5;
/*     */ 
/*     */   
/*     */   private double m_previousTargetWorldX;
/*     */ 
/*     */   
/*     */   private double m_previousTargetWorldY;
/*     */ 
/*     */   
/*     */   private double m_previousTargetAltitude;
/*     */ 
/*     */   
/*     */   private double m_cameraExactIsoWorldX;
/*     */ 
/*     */   
/*     */   private double m_cameraExactIsoWorldY;
/*     */   
/*     */   private double m_cameraDeltaScreenX;
/*     */   
/*     */   private double m_cameraDeltaScreenY;
/*     */   
/*     */   private boolean m_cameraParmetersChanged;
/*     */   
/*     */   private int m_heightCellCount;
/*     */   
/*     */   private int m_widthCellCount;
/*     */   
/*     */   private int m_isoLocalX;
/*     */   
/*     */   private int m_isoLocalY;
/*     */   
/*     */   private int m_isoOffsetX;
/*     */   
/*     */   private int m_isoOffsetY;
/*     */   
/*     */   private int m_centerScreenIsoWorldX;
/*     */   
/*     */   private int m_centerScreenIsoWorldY;
/*     */   
/*     */   private double m_deltaIsoWorldX;
/*     */   
/*     */   private double m_deltaIsoWorldY;
/*     */ 
/*     */   
/*     */   public IsoCamera() {}
/*     */ 
/*     */   
/*     */   public IsoCamera(double worldX, double worldY, double altitude, IsoWorldScene scene) {
/*  89 */     this.m_worldX = worldX;
/*  90 */     this.m_worldY = worldY;
/*  91 */     this.m_altitude = altitude;
/*  92 */     this.m_scene = scene;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(long realTime, int frameCount) {
/* 102 */     boolean bForceUpdate = false;
/*     */     
/* 104 */     if (this.m_trackingTarget == null) {
/*     */       return;
/*     */     }
/* 107 */     if (this.m_scene == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 112 */     if (this.m_previousTargetWorldX != Math.round(this.m_trackingTarget.getWorldX()) || this.m_previousTargetWorldY != Math.round(this.m_trackingTarget.getWorldY()) || 
/* 113 */       this.m_previousTargetAltitude != Math.round(this.m_trackingTarget.getAltitude()))
/*     */     {
/* 115 */       bForceUpdate = true;
/*     */     }
/*     */     
/* 118 */     double currentIsoAltitude = this.m_trackingTarget.getAltitude() * this.m_scene.getElevationUnit() / this.m_scene.getCellHeight();
/*     */ 
/*     */     
/* 121 */     double destExactX = this.m_trackingTarget.getWorldX() - currentIsoAltitude;
/* 122 */     double destExactY = this.m_trackingTarget.getWorldY() - currentIsoAltitude;
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (Math.abs(destExactX - this.m_cameraExactIsoWorldX) > 0.2D || Math.abs(destExactY - this.m_cameraExactIsoWorldY) > 0.2D) {
/* 127 */       this.m_cameraExactIsoWorldX += (destExactX - this.m_cameraExactIsoWorldX) / 15.0D;
/* 128 */       this.m_cameraExactIsoWorldY += (destExactY - this.m_cameraExactIsoWorldY) / 15.0D;
/*     */     } 
/*     */     
/* 131 */     this.m_centerScreenIsoWorldX = (int)Math.floor(this.m_cameraExactIsoWorldX);
/* 132 */     this.m_centerScreenIsoWorldY = (int)Math.floor(this.m_cameraExactIsoWorldY);
/*     */     
/* 134 */     this.m_deltaIsoWorldX = this.m_cameraExactIsoWorldX - this.m_centerScreenIsoWorldX;
/* 135 */     this.m_deltaIsoWorldY = this.m_cameraExactIsoWorldY - this.m_centerScreenIsoWorldY;
/*     */ 
/*     */     
/* 138 */     if (Math.abs(this.m_desiredZoomFactor - this.m_zoomFactor) > 0.01D) {
/* 139 */       this.m_zoomFactor += (this.m_desiredZoomFactor - this.m_zoomFactor) / 15.0D;
/*     */       
/* 141 */       this.m_scene.setScaleFactor((float)this.m_zoomFactor);
/*     */       
/* 143 */       int widthCellCount = (int)Math.ceil(this.m_scene.getFrustumWidth() / this.m_scene.getCellWidth() / this.m_zoomFactor) + 2;
/* 144 */       int heightCellCount = (int)Math.ceil(this.m_scene.getFrustumHeight() / this.m_scene.getCellHeight() / this.m_zoomFactor);
/*     */       
/* 146 */       if (heightCellCount != this.m_heightCellCount || widthCellCount != this.m_widthCellCount) {
/* 147 */         this.m_heightCellCount = heightCellCount;
/* 148 */         this.m_widthCellCount = widthCellCount;
/*     */         
/* 150 */         bForceUpdate = true;
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     this.m_isoOffsetX = -(this.m_heightCellCount + this.m_widthCellCount) / 2 - 5;
/* 155 */     this.m_isoOffsetY = -(this.m_heightCellCount - this.m_widthCellCount) / 2 - 5;
/*     */     
/* 157 */     this.m_isoLocalX = this.m_isoOffsetX;
/* 158 */     this.m_isoLocalY = this.m_isoOffsetY;
/*     */ 
/*     */     
/* 161 */     this.m_cameraDeltaScreenX = this.m_scene.isoToScreenX(this.m_deltaIsoWorldX, this.m_deltaIsoWorldY);
/* 162 */     this.m_cameraDeltaScreenY = this.m_scene.isoToScreenY(this.m_deltaIsoWorldX, this.m_deltaIsoWorldY);
/*     */     
/* 164 */     scrollX(-this.m_cameraDeltaScreenX);
/* 165 */     scrollY(-this.m_cameraDeltaScreenY);
/*     */ 
/*     */     
/* 168 */     float frustumWidth = this.m_scene.getFrustumWidth() / this.m_scene.getScaleFactor();
/* 169 */     float frustumHeight = this.m_scene.getFrustumHeight() / this.m_scene.getScaleFactor();
/* 170 */     float left = (float)this.m_cameraDeltaScreenX - frustumWidth * 0.5F;
/* 171 */     float bottom = (float)this.m_cameraDeltaScreenY - frustumHeight * 0.5F;
/* 172 */     this.m_scene.updateBoundsScreenRect(left, bottom, frustumWidth, frustumHeight);
/*     */     
/* 174 */     if (this.m_centerScreenIsoWorldX == getPosX() && this.m_centerScreenIsoWorldY == getPosY() && isInitialized() && !bForceUpdate) {
/* 175 */       this.m_cameraParmetersChanged = false;
/*     */     } else {
/* 177 */       setPosX(this.m_centerScreenIsoWorldX);
/* 178 */       setPosY(this.m_centerScreenIsoWorldY);
/* 179 */       this.m_cameraParmetersChanged = true;
/*     */     } 
/*     */     
/* 182 */     this.m_previousTargetWorldX = Math.round(this.m_trackingTarget.getWorldX());
/* 183 */     this.m_previousTargetWorldY = Math.round(this.m_trackingTarget.getWorldY());
/* 184 */     this.m_previousTargetAltitude = Math.round(this.m_trackingTarget.getAltitude());
/*     */   }
/*     */ 
/*     */   
/*     */   public void alignOnTrackingTarget() {
/* 189 */     if (this.m_trackingTarget != null) {
/* 190 */       this.m_cameraExactIsoWorldX = this.m_trackingTarget.getWorldX();
/* 191 */       this.m_cameraExactIsoWorldY = this.m_trackingTarget.getWorldY();
/* 192 */       this.m_cameraParmetersChanged = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getDesiredZoomFactor() {
/* 197 */     return this.m_desiredZoomFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDesiredZoomFactor(double desiredZoomFactor) {
/* 202 */     if (desiredZoomFactor < this.m_zoomFactorMin) {
/* 203 */       desiredZoomFactor = this.m_zoomFactorMin;
/* 204 */     } else if (desiredZoomFactor > this.m_zoomFactorMax) {
/* 205 */       desiredZoomFactor = this.m_zoomFactorMax;
/*     */     } 
/* 207 */     this.m_desiredZoomFactor = desiredZoomFactor;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setZoomFactor(double zoomFactor) {
/* 212 */     this.m_zoomFactor = zoomFactor;
/*     */   }
/*     */   
/*     */   public double getZoomFactor() {
/* 216 */     return this.m_zoomFactor;
/*     */   }
/*     */   
/*     */   public double getCameraExactIsoWorldX() {
/* 220 */     return this.m_cameraExactIsoWorldX;
/*     */   }
/*     */   
/*     */   public double getCameraExactIsoWorldY() {
/* 224 */     return this.m_cameraExactIsoWorldY;
/*     */   }
/*     */   
/*     */   public double getCameraDeltaScreenX() {
/* 228 */     return this.m_cameraDeltaScreenX;
/*     */   }
/*     */   
/*     */   public double getCameraDeltaScreenY() {
/* 232 */     return this.m_cameraDeltaScreenY;
/*     */   }
/*     */   
/*     */   public void setCameraParmetersChanged(boolean cameraParmetersChanged) {
/* 236 */     this.m_cameraParmetersChanged = cameraParmetersChanged;
/*     */   }
/*     */   
/*     */   public boolean isCameraParametersChanged() {
/* 240 */     return this.m_cameraParmetersChanged;
/*     */   }
/*     */   
/*     */   public int getHeightCellCount() {
/* 244 */     return this.m_heightCellCount;
/*     */   }
/*     */   
/*     */   public int getWidthCellCount() {
/* 248 */     return this.m_widthCellCount;
/*     */   }
/*     */   
/*     */   public int getIsoLocalX() {
/* 252 */     return this.m_isoLocalX;
/*     */   }
/*     */   
/*     */   public int getIsoLocalY() {
/* 256 */     return this.m_isoLocalY;
/*     */   }
/*     */   
/*     */   public int getIsoOffsetX() {
/* 260 */     return this.m_isoOffsetX;
/*     */   }
/*     */   
/*     */   public int getIsoOffsetY() {
/* 264 */     return this.m_isoOffsetY;
/*     */   }
/*     */   
/*     */   public int getCenterScreenIsoWorldX() {
/* 268 */     return this.m_centerScreenIsoWorldX;
/*     */   }
/*     */   
/*     */   public int getCenterScreenIsoWorldY() {
/* 272 */     return this.m_centerScreenIsoWorldY;
/*     */   }
/*     */   
/*     */   public double getDeltaIsoWorldX() {
/* 276 */     return this.m_deltaIsoWorldX;
/*     */   }
/*     */   
/*     */   public double getDeltaIsoWorldY() {
/* 280 */     return this.m_deltaIsoWorldY;
/*     */   }
/*     */   
/*     */   public int getTopScreenCellHeightBonus() {
/* 284 */     return this.m_topScreenCellHeightBonus;
/*     */   }
/*     */   
/*     */   public int getBottomScreenCellHeightBonus() {
/* 288 */     return this.m_bottomScreenCellHeightBonus;
/*     */   }
/*     */   
/*     */   public int getCellCountInView() {
/* 292 */     return this.m_widthCellCount * (this.m_heightCellCount * 2 + this.m_bottomScreenCellHeightBonus + 5);
/*     */   }
/*     */   
/*     */   public void setBottomScreenCellHeightBonus(int bottomScreenCellHeightBonus) {
/* 296 */     this.m_bottomScreenCellHeightBonus = bottomScreenCellHeightBonus;
/*     */   }
/*     */   
/*     */   public void setWidthCellCount(int widthCellCount) {
/* 300 */     this.m_widthCellCount = widthCellCount;
/*     */   }
/*     */   
/*     */   public void setHeightCellCount(int heightCellCount) {
/* 304 */     this.m_heightCellCount = heightCellCount;
/*     */   }
/*     */   
/*     */   public float getZoomFactorMin() {
/* 308 */     return this.m_zoomFactorMin;
/*     */   }
/*     */   
/*     */   public void setZoomFactorMin(float zoomFactorMin) {
/* 312 */     this.m_zoomFactorMin = zoomFactorMin;
/*     */   }
/*     */   
/*     */   public float getZoomFactorMax() {
/* 316 */     return this.m_zoomFactorMax;
/*     */   }
/*     */   
/*     */   public void setZoomFactorMax(float zoomFactorMax) {
/* 320 */     this.m_zoomFactorMax = zoomFactorMax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IsoWorldTarget getTrackingTarget() {
/* 329 */     return this.m_trackingTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTrackingTarget(IsoWorldTarget trackingTarget) {
/* 338 */     this.m_trackingTarget = trackingTarget;
/* 339 */     this.m_cameraParmetersChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAltitude() {
/* 348 */     return this.m_altitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldCellX() {
/* 357 */     return (int)Math.floor(this.m_worldX);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWorldCellY() {
/* 366 */     return (int)Math.floor(this.m_worldY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWorldX() {
/* 375 */     return this.m_worldX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getWorldY() {
/* 384 */     return this.m_worldY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAltitude(double altitude) {
/* 393 */     this.m_altitude = altitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldX(double worldX) {
/* 402 */     this.m_worldX = worldX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldY(double worldY) {
/* 411 */     this.m_worldY = worldY;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\IsoCamera.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */