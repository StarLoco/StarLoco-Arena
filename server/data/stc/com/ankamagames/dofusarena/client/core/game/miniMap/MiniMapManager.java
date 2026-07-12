/*     */ package com.ankamagames.dofusarena.client.core.game.miniMap;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.property.PropertiesProvider;
/*     */ import com.ankamagames.xulor.util.DisplayableMapPoint;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ public class MiniMapManager
/*     */ {
/*  21 */   private static final MiniMapManager m_instance = new MiniMapManager();
/*     */   
/*  23 */   private final HashMap<Long, DisplayableMapPoint> m_points = new HashMap();
/*  24 */   private double m_xCenter = 0.0D;
/*  25 */   private double m_yCenter = 0.0D;
/*     */   
/*     */ 
/*     */ 
/*     */   public static MiniMapManager getInstance()
/*     */   {
/*  31 */     return m_instance;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private MiniMapManager()
/*     */   {
/*  39 */     updateProperty();
/*  40 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("miniMap.zoom", Integer.valueOf(0));
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
/*     */   public void addPoint(long referenceId, double worldX, double worldY, Object value, String texturePath, float[] color)
/*     */   {
/*  53 */     DisplayableMapPoint mapPoint = (DisplayableMapPoint)this.m_points.get(Long.valueOf(referenceId));
/*  54 */     if (mapPoint != null) {
/*  55 */       mapPoint.setIsoX(worldX);
/*  56 */       mapPoint.setIsoY(worldY);
/*  57 */       mapPoint.setTexturePath(texturePath);
/*  58 */       mapPoint.setColor(color);
/*     */     } else {
/*  60 */       this.m_points.put(Long.valueOf(referenceId), new DisplayableMapPoint(worldX, worldY, value, texturePath, color));
/*     */     }
/*  62 */     updateProperty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removePoint(long referenceId)
/*     */   {
/*  71 */     this.m_points.remove(Long.valueOf(referenceId));
/*  72 */     updateProperty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setXCenter(double xCenter)
/*     */   {
/*  81 */     this.m_xCenter = xCenter;
/*  82 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("miniMap.xCenter", Double.valueOf(this.m_xCenter));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setYCenter(double yCenter)
/*     */   {
/*  91 */     this.m_yCenter = yCenter;
/*  92 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("miniMap.yCenter", Double.valueOf(this.m_yCenter));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void updateProperty()
/*     */   {
/*  99 */     ArrayList<DisplayableMapPoint> points = new ArrayList();
/* 100 */     for (DisplayableMapPoint mapPoint : this.m_points.values()) {
/* 101 */       points.add(mapPoint);
/*     */     }
/* 103 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("miniMap.points", points.toArray(new DisplayableMapPoint[0]));
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\game\miniMap\MiniMapManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */