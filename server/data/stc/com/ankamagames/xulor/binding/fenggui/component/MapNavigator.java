/*     */ package com.ankamagames.xulor.binding.fenggui.component;
/*     */ 
/*     */ import com.ankamagames.framework.graphics.opengl.TextureManager;
/*     */ import com.ankamagames.framework.graphics.opengl.base.BaseTexture;
/*     */ import com.ankamagames.framework.graphics.opengl.base.impl.HitTestableMesh2D;
/*     */ import com.ankamagames.framework.graphics.opengl.base.matrices.transformation2D.Ortho2DScrollCamera;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePostRenderStates;
/*     */ import com.ankamagames.framework.graphics.opengl.base.states.DefaultScenePreRenderStates;
/*     */ import com.ankamagames.graphics.isometric.IsoWorldScene;
/*     */ import com.ankamagames.graphics.isometric.text.BackgroundedText.BackgroundedTextHotPointPosition;
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiConstant;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.event.IMouseClickListener;
/*     */ import com.ankamagames.xulor.event.MapPointClickEvent;
/*     */ import com.ankamagames.xulor.template.IComponent;
/*     */ import com.ankamagames.xulor.util.DisplayableMapPoint;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Vector;
/*     */ import javax.media.opengl.GL;
/*     */ import org.fenggui.DecoratorAppearance;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.event.mouse.MouseReleasedEvent;
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
/*     */ public class MapNavigator
/*     */   extends SceneCanvas
/*     */ {
/*     */   private double m_isoXCenter;
/*     */   private double m_isoYCenter;
/*     */   private boolean m_isoMap;
/*  49 */   private double m_zoom = 1.0D;
/*  50 */   private double m_maxZoom = 1.0D;
/*  51 */   private double m_minZoom = 1.0D;
/*  52 */   private double m_zoomScale = 1.0D;
/*  53 */   private BackgroundedText.BackgroundedTextHotPointPosition m_tooltipHotPoint = BackgroundedText.BackgroundedTextHotPointPosition.SOUTH;
/*  54 */   private MapShape m_mapShape = MapShape.RECTANGLE;
/*     */   
/*     */   private Vector<IMouseClickListener> m_mcl;
/*     */   
/*     */   private HitTestableMesh2D m_pointPressed;
/*     */   private DisplayableMapPoint[] m_items;
/*  60 */   private final ArrayList<HitTestableMesh2D> m_meshes = new ArrayList();
/*  61 */   private final HashMap<String, BaseTexture> m_textures = new HashMap();
/*     */   private IsoWorldScene m_scene;
/*     */   
/*     */   public static enum MapShape
/*     */   {
/*  66 */     RECTANGLE,  CIRCLE,  SQUARE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public MapNavigator()
/*     */   {
/*  74 */     setNonBlocking(false);
/*     */     
/*  76 */     this.m_scene = new IsoWorldScene()
/*     */     {
/*     */ 
/*     */ 
/*     */       public void process(long realTime, int frameCount)
/*     */       {
/*     */ 
/*     */ 
/*  84 */         removeAllChilds();
/*     */         
/*  86 */         double xDeltaCenter = isoToScreenX(MapNavigator.this.m_isoXCenter, MapNavigator.this.m_isoYCenter);
/*  87 */         double yDeltaCenter = isoToScreenY(MapNavigator.this.m_isoXCenter, MapNavigator.this.m_isoYCenter);
/*  88 */         if (MapNavigator.this.m_items != null) {
/*  89 */           for (int i = 0; i < MapNavigator.this.m_items.length; i++) {
/*  90 */             if (MapNavigator.this.m_items[i] != null) {
/*  91 */               DisplayableMapPoint displayableMapPoint = MapNavigator.this.m_items[i];
/*     */               
/*  93 */               double xScreenItem = isoToScreenX(displayableMapPoint.getIsoX(), displayableMapPoint.getIsoY()) - xDeltaCenter;
/*  94 */               double yScreenItem = isoToScreenY(displayableMapPoint.getIsoX(), displayableMapPoint.getIsoY()) - yDeltaCenter;
/*     */               
/*  96 */               HitTestableMesh2D point = (HitTestableMesh2D)MapNavigator.this.m_meshes.get(i);
/*     */               
/*  98 */               if (MapNavigator.this.haveToDisplay(xScreenItem, yScreenItem)) {
/*  99 */                 float width = 3.0F;float height = 3.0F;
/* 100 */                 BaseTexture texture = MapNavigator.this.getMeshTexture(displayableMapPoint.getTexturePath());
/* 101 */                 if (texture != null) {
/* 102 */                   width = texture.getImageWidth();
/* 103 */                   height = texture.getImageHeight();
/* 104 */                   point.setTexture(texture);
/* 105 */                   point.computeTextureCoordinate();
/*     */                 }
/* 107 */                 point.setScreenPosition((float)xScreenItem - width / 2.0F, (float)yScreenItem + height / 2.0F);
/* 108 */                 float[] color = displayableMapPoint.getColor();
/* 109 */                 if ((color != null) && (color.length == 4)) {
/* 110 */                   point.setColor(color[0], color[1], color[2], color[3]);
/*     */                 }
/* 112 */                 point.setWidth(width);
/* 113 */                 point.setHeight(height);
/* 114 */                 addChild(point);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 119 */         super.process(realTime, frameCount);
/*     */       }
/*     */       
/* 122 */     };
/* 123 */     this.m_scene.setCellWidth(86.0D * this.m_zoom);
/* 124 */     this.m_scene.setCellHeight(43.0D * this.m_zoom);
/*     */     
/* 126 */     this.m_scene.setPreRenderStates(new MapNavigatorScenePreRenderStates(null));
/* 127 */     this.m_scene.setPostRenderStates(new MapNavigatorScenePostRenderStates(null));
/*     */     
/* 129 */     this.m_scene.setInstancesInitialized(true);
/* 130 */     this.m_scene.setLoaded(true);
/* 131 */     this.m_scene.setCamera(new Ortho2DScrollCamera());
/*     */     
/*     */ 
/* 134 */     setScene(this.m_scene);
/*     */   }
/*     */   
/*     */   public double getIsoXCenter() {
/* 138 */     return this.m_isoXCenter;
/*     */   }
/*     */   
/*     */   public void setIsoXCenter(double isoXCenter) {
/* 142 */     this.m_isoXCenter = isoXCenter;
/*     */   }
/*     */   
/*     */   public double getIsoYCenter() {
/* 146 */     return this.m_isoYCenter;
/*     */   }
/*     */   
/*     */   public void setIsoYCenter(double isoYCenter) {
/* 150 */     this.m_isoYCenter = isoYCenter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getZoom()
/*     */   {
/* 157 */     return this.m_zoom;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getMinZoom()
/*     */   {
/* 164 */     return this.m_minZoom;
/*     */   }
/*     */   
/*     */   public void setMinZoom(double minZoom) {
/* 168 */     if ((minZoom > 0.0D) && (minZoom <= 1.0D)) {
/* 169 */       this.m_minZoom = minZoom;
/* 170 */       setZoom();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getMaxZoom()
/*     */   {
/* 178 */     return this.m_maxZoom;
/*     */   }
/*     */   
/*     */   public void setMaxZoom(double maxZoom) {
/* 182 */     if ((maxZoom > 0.0D) && (maxZoom <= 1.0D)) {
/* 183 */       this.m_maxZoom = maxZoom;
/* 184 */       setZoom();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public double getZoomScale()
/*     */   {
/* 192 */     return this.m_zoomScale;
/*     */   }
/*     */   
/*     */   public void setZoomScale(double zoomScale) {
/* 196 */     if ((zoomScale >= 0.0D) && (zoomScale <= 1.0D)) {
/* 197 */       this.m_zoomScale = zoomScale;
/* 198 */       setZoom();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMapShape(MapShape shape)
/*     */   {
/* 208 */     this.m_mapShape = shape;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MapShape getMapShape()
/*     */   {
/* 215 */     return this.m_mapShape;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public BackgroundedText.BackgroundedTextHotPointPosition getTooltipHotPoint()
/*     */   {
/* 223 */     return this.m_tooltipHotPoint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTooltipHotPoint(BackgroundedText.BackgroundedTextHotPointPosition tooltipHotPoint)
/*     */   {
/* 232 */     this.m_tooltipHotPoint = tooltipHotPoint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void setZoom()
/*     */   {
/* 239 */     this.m_zoom = (this.m_minZoom + (this.m_maxZoom - this.m_minZoom) * this.m_zoomScale);
/* 240 */     this.m_scene.setCellWidth(86.0D * this.m_zoom);
/*     */     
/* 242 */     this.m_scene.setCellHeight(43.0D * this.m_zoom * (this.m_isoMap ? 1 : 2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isIsoMap()
/*     */   {
/* 249 */     return this.m_isoMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setIsoMap(boolean isoMap)
/*     */   {
/* 258 */     this.m_isoMap = isoMap;
/* 259 */     setZoom();
/*     */   }
/*     */   
/*     */   public void setMouseClickListener(Vector<IMouseClickListener> mcl)
/*     */   {
/* 264 */     this.m_mcl = mcl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DisplayableMapPoint[] getItems()
/*     */   {
/* 271 */     return this.m_items;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setItems(DisplayableMapPoint[] items)
/*     */   {
/* 280 */     this.m_items = items;
/*     */     
/*     */ 
/* 283 */     int startIndex = items != null ? items.length : 0;
/* 284 */     for (int i = startIndex; i < this.m_meshes.size(); i++) {
/* 285 */       ((HitTestableMesh2D)this.m_meshes.get(i)).release();
/* 286 */       this.m_meshes.remove(i);
/*     */     }
/*     */     
/*     */ 
/* 290 */     for (int i = this.m_meshes.size(); i < startIndex; i++) {
/* 291 */       this.m_meshes.add(HitTestableMesh2D.getNewHitTestableMesh2D());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private BaseTexture getMeshTexture(String path)
/*     */   {
/* 302 */     BaseTexture texture = (BaseTexture)this.m_textures.get(path);
/* 303 */     if (texture == null) {
/* 304 */       texture = new BaseTexture();
/*     */       try {
/* 306 */         texture.setTexture(TextureManager.createRawTextureFromFile(path));
/* 307 */         this.m_textures.put(path, texture);
/*     */       } catch (Exception e) {
/* 309 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 312 */     return texture;
/*     */   }
/*     */   
/*     */   private class MapNavigatorScenePreRenderStates extends DefaultScenePreRenderStates { private MapNavigatorScenePreRenderStates() {}
/*     */     
/* 317 */     public void setup(GL gl) { super.setup(gl);
/* 318 */       gl.glEnable(5890);
/*     */     }
/*     */   }
/*     */   
/*     */   private class MapNavigatorScenePostRenderStates extends DefaultScenePostRenderStates { private MapNavigatorScenePostRenderStates() {}
/*     */     
/* 324 */     public void setup(GL gl) { super.setup(gl);
/* 325 */       gl.glDisable(5890);
/*     */       
/* 327 */       gl.glBlendFunc(770, 771);
/* 328 */       gl.glEnable(3042);
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseMoved(int i, int i1)
/*     */   {
/* 334 */     int xScene = i - getDisplayX() - getAppearance().getLeftMargins() - getAppearance().getContentWidth() / 2;
/* 335 */     int yScene = i1 - getDisplayY() - getAppearance().getBottomMargins() - getAppearance().getContentHeight() / 2;
/* 336 */     boolean tooltip = false;
/* 337 */     int j = 0;
/* 338 */     for (HitTestableMesh2D mesh : this.m_meshes) {
/* 339 */       if ((haveToDisplay(xScene, yScene)) && (mesh.hitTest(xScene, yScene))) {
/* 340 */         int tooltipX = (int)mesh.getPosX() + getDisplayX() + getAppearance().getLeftMargins() + getAppearance().getContentWidth() / 2;
/* 341 */         int tooltipY = (int)mesh.getPosY() + getDisplayY() + getAppearance().getBottomMargins() + getAppearance().getContentHeight() / 2;
/* 342 */         int xOffset = 0;int yOffset = 0;
/* 343 */         switch (this.m_tooltipHotPoint) {
/*     */         case NORTH_WEST: 
/* 345 */           yOffset = -(int)mesh.getHeight();
/* 346 */           break;
/*     */         case SOUTH: 
/* 348 */           xOffset = (int)mesh.getWidth() / 2;
/* 349 */           yOffset = -(int)mesh.getHeight();
/* 350 */           break;
/*     */         case SOUTH_EAST: 
/* 352 */           xOffset = (int)mesh.getWidth();
/* 353 */           yOffset = -(int)mesh.getHeight();
/* 354 */           break;
/*     */         case WEST: 
/* 356 */           yOffset = -(int)mesh.getHeight() / 2;
/* 357 */           break;
/*     */         case NORTH: 
/* 359 */           xOffset = (int)mesh.getWidth() / 2;
/* 360 */           break;
/*     */         case EAST: 
/* 362 */           xOffset = (int)mesh.getWidth();
/* 363 */           break;
/*     */         case SOUTH_WEST: 
/* 365 */           yOffset = -(int)mesh.getHeight() / 2;
/*     */         }
/*     */         
/* 368 */         Xulor.getInstance().setTooltipHotPointPosition(this.m_tooltipHotPoint);
/* 369 */         Xulor.getInstance().showTooltip(this.m_items[j].getValue().toString(), tooltipX, tooltipY, Integer.MAX_VALUE, xOffset, yOffset);
/*     */         
/* 371 */         tooltip = true;
/*     */       }
/* 373 */       j++;
/*     */     }
/* 375 */     if (!tooltip) {
/* 376 */       Xulor.getInstance().hideTooltip();
/*     */     }
/*     */   }
/*     */   
/*     */   public void mousePressed(MousePressedEvent mousePressedEvent)
/*     */   {
/* 382 */     int xScene = mousePressedEvent.getDisplayX() - getDisplayX() - getAppearance().getLeftMargins() - getAppearance().getContentWidth() / 2;
/* 383 */     int yScene = mousePressedEvent.getDisplayY() - getDisplayY() - getAppearance().getBottomMargins() - getAppearance().getContentHeight() / 2;
/*     */     
/* 385 */     for (HitTestableMesh2D mesh : this.m_meshes) {
/* 386 */       if ((haveToDisplay(xScene, yScene)) && (mesh.hitTest(xScene, yScene))) {
/* 387 */         this.m_pointPressed = mesh;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseReleased(MouseReleasedEvent mousePressedEvent)
/*     */   {
/* 394 */     int xScene = mousePressedEvent.getDisplayX() - getDisplayX() - getAppearance().getLeftMargins() - getAppearance().getContentWidth() / 2;
/* 395 */     int yScene = mousePressedEvent.getDisplayY() - getDisplayY() - getAppearance().getBottomMargins() - getAppearance().getContentHeight() / 2;
/* 396 */     if ((this.m_pointPressed != null) && (haveToDisplay(this.m_pointPressed.getPosX(), this.m_pointPressed.getPosY())) && (this.m_pointPressed.hitTest(xScene, yScene))) {
/* 397 */       for (IMouseClickListener mcl : this.m_mcl) {
/* 398 */         mcl.run(new MapPointClickEvent((IComponent)Xulor.getInstance().getEnvironment().getElementByWidget(this), 
/* 399 */           this.m_items[this.m_meshes.indexOf(this.m_pointPressed)].getValue(), 
/* 400 */           mousePressedEvent.getDisplayX(), 
/* 401 */           mousePressedEvent.getDisplayY(), 1, 
/* 402 */           FengguiConstant.toXulorMouseButtons(mousePressedEvent.getButton())));
/*     */       }
/*     */     } else {
/* 405 */       this.m_pointPressed = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean haveToDisplay(double x, double y)
/*     */   {
/* 416 */     float radius = Math.min(getAppearance().getContentWidth() / 2, getAppearance().getContentHeight() / 2);
/* 417 */     switch (this.m_mapShape) {
/*     */     case CIRCLE: 
/* 419 */       return true;
/*     */     case SQUARE: 
/* 421 */       return (radius > x) && (radius > y);
/*     */     case RECTANGLE: 
/* 423 */       return radius > Math.sqrt(x * x + y * y);
/*     */     }
/* 425 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\component\MapNavigator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */