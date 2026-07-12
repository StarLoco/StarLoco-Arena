/*     */ package com.ankamagames.baseImpl.graphics.alea.display;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.Mesh2D;
/*     */ import com.ankamagames.framework.kernel.core.common.MonitoredPool;
/*     */ import com.ankamagames.framework.kernel.core.common.ObjectFactory;
/*     */ import com.ankamagames.framework.kernel.core.common.Poolable;
/*     */ import com.ankamagames.framework.kernel.core.maths.Point3;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightMesh;
/*     */ import com.ankamagames.graphics.isometric.highlight.HighLightedElement;
/*     */ import com.ankamagames.graphics.isometric.highlight.UniqueHandleReference;
/*     */ import com.ankamagames.graphics.isometric.lines.Segment;
/*     */ import com.ankamagames.graphics.isometric.lines.SegmentMesh;
/*     */ import org.apache.commons.pool.ObjectPool;
/*     */ import org.apache.commons.pool.PoolableObjectFactory;
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
/*     */ public class DisplayedElement
/*     */   implements HighLightedElement, Poolable
/*     */ {
/*  30 */   private static final ObjectPool m_staticPool = (ObjectPool)new MonitoredPool((PoolableObjectFactory)new ObjectFactory<DisplayedElement>() { public DisplayedElement makeObject() {
/*  31 */           return new DisplayedElement();
/*     */         } }
/*     */     );
/*     */ 
/*     */   
/*     */   private DisplayedCell m_displayedCell;
/*     */   
/*     */   private WorldElement m_worldElement;
/*     */   
/*     */   private Mesh2D m_mesh;
/*     */   
/*     */   private float m_screenX;
/*     */   
/*     */   private float m_screenY;
/*     */   private float m_screenTopY;
/*     */   private float m_zOrder;
/*     */   private int m_level;
/*     */   private float m_altitude;
/*     */   private float m_brightness;
/*     */   private float[] m_teint;
/*     */   private int m_state;
/*     */   private boolean m_visible = true;
/*     */   private double m_distanceFromTopToMouse;
/*     */   
/*     */   public void setDisplayedCell(DisplayedCell displayedCell) {
/*  56 */     this.m_displayedCell = displayedCell;
/*     */   }
/*     */   
/*     */   public void setWorldElement(WorldElement worldElement) {
/*  60 */     this.m_worldElement = worldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisplayedCell getDisplayedCell() {
/*  67 */     return this.m_displayedCell;
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
/*     */   public void setScreenPosition(float screenX, float screenY, float screenTopY) {
/*  79 */     this.m_screenX = screenX;
/*  80 */     this.m_screenY = screenY;
/*  81 */     this.m_screenTopY = screenTopY;
/*     */   }
/*     */ 
/*     */   
/*     */   public Point3 getCoordinates() {
/*  86 */     return this.m_worldElement.getCoordinates();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldElement getWorldElement() {
/*  93 */     return this.m_worldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mesh2D getMesh() {
/* 100 */     return this.m_mesh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMesh(Mesh2D mesh) {
/* 108 */     this.m_mesh = mesh;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScreenX() {
/* 115 */     return this.m_screenX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScreenY() {
/* 122 */     return this.m_screenY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getScreenTopY() {
/* 129 */     return this.m_screenTopY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setZOrder(float zOrder) {
/* 136 */     this.m_zOrder = zOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getZOrder() {
/* 143 */     return this.m_zOrder;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 147 */     return this.m_visible;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 151 */     this.m_visible = visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAltitude() {
/* 158 */     return this.m_altitude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAltitude(float height) {
/* 166 */     this.m_altitude = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 173 */     return this.m_level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(int level) {
/* 180 */     this.m_level = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness() {
/* 187 */     return this.m_brightness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBrightness(float light) {
/* 195 */     this.m_brightness = light;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getTeint() {
/* 202 */     return this.m_teint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTeint(float[] teint) {
/* 210 */     this.m_teint = teint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/* 217 */     return this.m_state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(int state) {
/* 225 */     this.m_state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDistanceFromTopToMouse() {
/* 232 */     return this.m_distanceFromTopToMouse;
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
/*     */   public boolean rectHitTest(double x, double y) {
/* 244 */     if (this.m_mesh != null) {
/* 245 */       double left = (this.m_screenX - this.m_mesh.getHotX());
/* 246 */       double right = left + this.m_mesh.getWidth();
/* 247 */       if (x >= left && x <= right) {
/* 248 */         double top = (this.m_screenY + this.m_mesh.getHotY());
/* 249 */         double bottom = top - this.m_mesh.getHeight();
/* 250 */         if (y >= bottom && y <= top) {
/* 251 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 255 */     return false;
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
/*     */   public boolean fineHitTest(double x, double y, double minAlphaLevel) {
/* 270 */     if (this.m_mesh != null) {
/* 271 */       BaseTexture texture = this.m_mesh.getTexture();
/* 272 */       if (texture == null) {
/* 273 */         return false;
/*     */       }
/* 275 */       int imgX = (int)(x - this.m_screenX + this.m_mesh.getHotX());
/* 276 */       int imgY = (int)((this.m_screenY + this.m_mesh.getHotY()) - y);
/*     */       
/* 278 */       return (texture.getAlpha(imgX, imgY) >= minAlphaLevel);
/*     */     } 
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculateDistanceFromTopToMouse(double mouseX, double mouseY) {
/* 290 */     this.m_distanceFromTopToMouse = Math.sqrt(Math.pow(mouseX - this.m_screenX, 2.0D) + Math.pow(mouseY - this.m_screenTopY, 2.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UniqueHandleReference getLayerReference() {
/* 299 */     return (UniqueHandleReference)getWorldElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformHighLightMesh(HighLightMesh mesh, int elevationUnit) {
/* 309 */     if (mesh == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 314 */     byte slope = getWorldElement().getSlope();
/*     */ 
/*     */     
/* 317 */     double height = getWorldElement().getVisualHeight();
/* 318 */     float heightPx = (float)height * elevationUnit;
/*     */ 
/*     */     
/* 321 */     float screenX = getScreenX();
/* 322 */     float screenY = (slope == 0) ? getScreenTopY() : getScreenY();
/*     */ 
/*     */     
/* 325 */     mesh.setZOrder(getZOrder() + 1.0E-6F);
/*     */ 
/*     */     
/* 328 */     float size = mesh.getSize();
/*     */     
/* 330 */     mesh.setVertices(
/* 331 */         new float[] {
/* 332 */           -size, 
/* 333 */           0.0F, 
/* 334 */           0.0F, 
/* 335 */           size
/*     */         },
/* 337 */         new float[] {
/* 338 */           ((slope & 0x1) == 1) ? heightPx : 0.0F, 
/* 339 */           -size / 2.0F + (((slope & 0x8) == 8) ? heightPx : 0.0F), 
/* 340 */           size / 2.0F + (((slope & 0x2) == 2) ? heightPx : 0.0F), 
/* 341 */           ((slope & 0x4) == 4) ? heightPx : 0.0F
/*     */         });
/*     */ 
/*     */     
/* 345 */     mesh.setScreenPosition(screenX, screenY);
/* 346 */     mesh.computeTextureCoordinate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformLineMesh(SegmentMesh mesh, Segment segment, double cellWidth, double cellHeight, int elevationUnit, float frustumHeight) {
/* 352 */     byte slope = getWorldElement().getSlope();
/*     */     
/* 354 */     float halfCellHeight = (float)cellHeight * 0.5F;
/*     */ 
/*     */     
/* 357 */     float screenX = getScreenX();
/* 358 */     float screenY = (slope == 0) ? getScreenTopY() : getScreenY();
/*     */ 
/*     */     
/* 361 */     mesh.setZOrder(getZOrder() + 1.0E-6F + halfCellHeight / frustumHeight);
/*     */     
/* 363 */     mesh.setStart(screenX, screenY);
/*     */     
/* 365 */     screenX = (float)(screenX + (segment.getX() - segment.getY()) * cellWidth * 0.5D);
/* 366 */     screenY += (-segment.getY() - segment.getX()) * halfCellHeight + (segment.getZ() * elevationUnit);
/*     */     
/* 368 */     if (segment.isBehind()) {
/* 369 */       float alpha = 75.0F / (100.0F - (4 * segment.getZ()));
/* 370 */       if (alpha < 0.05F)
/* 371 */         alpha = 0.05F; 
/* 372 */       mesh.setAlpha(alpha);
/*     */     } 
/*     */     
/* 375 */     mesh.setEnd(screenX, screenY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DisplayedElement checkOut() {
/*     */     try {
/* 384 */       return (DisplayedElement)m_staticPool.borrowObject();
/* 385 */     } catch (Exception e) {
/* 386 */       e.printStackTrace();
/*     */ 
/*     */       
/* 389 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/*     */     try {
/* 398 */       m_staticPool.returnObject(this);
/* 399 */     } catch (Exception e) {
/* 400 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckOut() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/* 414 */     this.m_mesh = null;
/* 415 */     this.m_worldElement = null;
/* 416 */     this.m_displayedCell = null;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\display\DisplayedElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */